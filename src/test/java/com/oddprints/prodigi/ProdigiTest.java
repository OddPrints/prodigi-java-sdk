package com.oddprints.prodigi;

import com.oddprints.prodigi.pojos.CreateOrderResponse;
import com.oddprints.prodigi.pojos.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProdigiTest {

    @Test
    void canCreateOrder() {
        String apiKey = System.getenv("PRODIGI_API_KEY_SANDBOX");
        Order o = new Order();
        Prodigi p = new Prodigi(apiKey);
        CreateOrderResponse response = p.createOrder(o);
        assertEquals("InProgress", response.getOrder().getStatus().getStage());
    }
}
