package com.oddprints.prodigi;

import com.oddprints.prodigi.pojos.CreateOrderResponse;
import com.oddprints.prodigi.pojos.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ProdigiTest {

    @Test
    void can_create_order() {
        String apiKey = System.getenv("PRODIGI_API_KEY_SANDBOX");
        Order o = new Order();
        Prodigi p = new Prodigi(apiKey);
        CreateOrderResponse response = p.createOrder(o);
        assertEquals("InProgress", response.getOrder().getStatus().getStage());
    }

    @Test
    void must_supply_apikey() {
        try {
            new Prodigi("");
            new Prodigi(null);
            fail(); //  should have thrown
        } catch (IllegalArgumentException e) {
        }
    }
}
