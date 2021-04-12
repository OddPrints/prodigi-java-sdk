package com.oddprints.prodigi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.oddprints.prodigi.pojos.*;
import io.netty.handler.logging.LogLevel;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

public class Prodigi {
    private static final Logger log = LoggerFactory.getLogger(Prodigi.class);
    private HttpClient httpClient;
    private WebClient webClient;
    private static String API_VERSION = "v4.0";

    public Prodigi(final Environment environment, final String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "No api key supplied. Try adding PRODIGI_API_KEY_SANDBOX to environment");
        }
        httpClient =
                HttpClient.create()
                        .wiretap(
                                this.getClass().getCanonicalName(),
                                LogLevel.INFO,
                                AdvancedByteBufFormat.TEXTUAL);

        webClient =
                WebClient.builder()
                        .baseUrl(environment.url)
                        .defaultHeader("X-API-Key", apiKey)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }

    public OrderResponse createOrder(Order order) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.post();
        WebClient.RequestBodySpec bodySpec =
                uriSpec.uri(uriBuilder -> uriBuilder.pathSegment("orders").build());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(BodyInserters.fromValue(order));

        try {
            return headersSpec.retrieve().bodyToMono(OrderResponse.class).block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            return null;
        }
    }

    public OrderResponse getOrder(String id) {
        return getOrderResponseCache.get(id);
    }

    private OrderResponse getOrderResponseImpl(String id) {
        Mono<OrderResponse> mono =
                webClient.get().uri("/orders/{id}", id).retrieve().bodyToMono(OrderResponse.class);
        try {
            return mono.block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            throw new ProdigiError(e.getResponseBodyAsString(), e.getRawStatusCode());
        }
    }

    public String getRawOrderResponse(String id) {
        return getRawOrderResponse(id, false);
    }

    public String getRawOrderResponse(String id, boolean asYaml) {
        Mono<String> mono =
                webClient.get().uri("/orders/{id}", id).retrieve().bodyToMono(String.class);

        String json = "";
        try {
            json = mono.block();
            ObjectMapper mapper = new ObjectMapper();

            if (asYaml) {
                JsonNode jsonNodeTree = new ObjectMapper().readTree(json);
                // save it as YAML
                String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
                return jsonAsYaml;
            } else {
                Object jsonObject = mapper.readValue(json, Object.class);
                String prettyJson =
                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
                return prettyJson;
            }
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            throw new ProdigiError(e.getResponseBodyAsString(), e.getRawStatusCode());
        } catch (JsonProcessingException e) {
            log.error("response = " + json);
            throw new RuntimeException(e);
        }
    }

    public OrdersResponse getOrders(int top, int skip) {
        Mono<OrdersResponse> mono =
                webClient
                        .get()
                        .uri("/orders")
                        .attribute("top", top)
                        .attribute("skip", skip)
                        .retrieve()
                        .bodyToMono(OrdersResponse.class);

        try {
            return mono.block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            throw new ProdigiError(e.getResponseBodyAsString(), e.getRawStatusCode());
        }
    }

    public boolean cancelOrder(String id) {
        Mono<OrderResponse> mono =
                webClient
                        .post()
                        .uri("/orders/{id}/actions/cancel", id)
                        .retrieve()
                        .bodyToMono(OrderResponse.class);
        try {
            boolean response = mono.block().getOutcome().equalsIgnoreCase("cancelled");
            getActionsResponseCache.invalidate(id);
            getOrderResponseCache.invalidate(id);
            return response;
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            return false;
        }
    }

    public boolean updateRecipient(String id, Recipient recipient) {
        Mono<OrderResponse> mono =
                webClient
                        .post()
                        .uri("/orders/{id}/actions/updateRecipient", id)
                        .bodyValue(recipient)
                        .retrieve()
                        .bodyToMono(OrderResponse.class);

        try {
            boolean response = mono.block().getOutcome().equalsIgnoreCase("updated");
            getActionsResponseCache.invalidate(id);
            getOrderResponseCache.invalidate(id);
            return response;
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            return false;
        }
    }

    public boolean canCancel(String id) {
        ActionsResponse actionsResponse = getActionsResponseCache.get(id);
        return actionsResponse.getCancel().getIsAvailable().equalsIgnoreCase("Yes");
    }

    public boolean canChangeRecipientDetails(String id) {
        ActionsResponse actionsResponse = getActionsResponseCache.get(id);
        return actionsResponse.getChangeRecipientDetails().getIsAvailable().equalsIgnoreCase("Yes");
    }

    private LoadingCache<String, ActionsResponse> getActionsResponseCache =
            Caffeine.newBuilder()
                    .expireAfterWrite(1, TimeUnit.MINUTES)
                    .maximumSize(100)
                    .build(k -> getActionsResponseImpl(k));

    private LoadingCache<String, OrderResponse> getOrderResponseCache =
            Caffeine.newBuilder()
                    .expireAfterWrite(1, TimeUnit.MINUTES)
                    .maximumSize(100)
                    .build(k -> getOrderResponseImpl(k));

    private ActionsResponse getActionsResponseImpl(String id) {
        Mono<ActionsResponse> mono =
                webClient
                        .get()
                        .uri("/orders/{id}/actions", id)
                        .retrieve()
                        .bodyToMono(ActionsResponse.class);

        try {
            return mono.block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            return null;
        }
    }

    public enum Environment {
        LIVE("https://api.prodigi.com/" + API_VERSION),
        SANDBOX("https://api.sandbox.prodigi.com/" + API_VERSION);

        private String url;

        Environment(String url) {
            this.url = url;
        }
    }
}
