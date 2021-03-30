package com.oddprints.prodigi;

import com.oddprints.prodigi.pojos.CreateOrderResponse;
import com.oddprints.prodigi.pojos.Order;
import io.netty.handler.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

public class Prodigi {
    private static final Logger log = LoggerFactory.getLogger(Prodigi.class);
    private HttpClient httpClient;
    private WebClient webClient;

    public Prodigi(final String apiKey) {
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

    public CreateOrderResponse createOrder(Order order) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(
                uriBuilder -> uriBuilder.pathSegment("Orders").build());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(BodyInserters.fromValue(order));

        try {
            return headersSpec.retrieve()
                    .bodyToMono(CreateOrderResponse.class).block();
        } catch (WebClientResponseException e) {
            log.error("response = " + e.getResponseBodyAsString());
            return null;
        }
    }
}
