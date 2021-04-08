package com.oddprints.prodigi;

import com.oddprints.prodigi.pojos.OrderResponse;
import com.oddprints.prodigi.pojos.Order;
import com.oddprints.prodigi.pojos.OrdersResponse;
import io.netty.handler.logging.LogLevel;
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

    public Prodigi(final String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("No api key supplied. Try adding PRODIGI_API_KEY_SANDBOX to environment");
        }
        httpClient = HttpClient
                .create()
                .wiretap(this.getClass().getCanonicalName(),
                        LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL);

        webClient = WebClient.builder()
                .baseUrl("https://api.sandbox.prodigi.com/v4.0")
                .defaultHeader("X-API-Key", apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public OrderResponse createOrder(Order order) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(
                uriBuilder -> uriBuilder.pathSegment("Orders").build());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(BodyInserters.fromValue(order));

        try {
            return headersSpec.retrieve()
                    .bodyToMono(OrderResponse.class).block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            return null;
        }
    }

    public OrderResponse getOrder(String id) {
        Mono<OrderResponse> orderMono = webClient.get()
                .uri("/Orders/{id}", id)
                .retrieve()
                .bodyToMono(OrderResponse.class);

        try {
            return orderMono.block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            throw new ProdigiError(e.getResponseBodyAsString(), e.getRawStatusCode());
        }
    }

    public OrdersResponse getOrders(int top, int skip) {
        Mono<OrdersResponse> orderMono = webClient.get()
                .uri("/Orders")
                .attribute("top", top)
                .attribute("skip", skip)
                .retrieve()
                .bodyToMono(OrdersResponse.class);

        try {
            return orderMono.block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            throw new ProdigiError(e.getResponseBodyAsString(), e.getRawStatusCode());
        }
    }
}
