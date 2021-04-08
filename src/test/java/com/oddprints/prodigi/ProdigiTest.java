package com.oddprints.prodigi;

import com.oddprints.prodigi.pojos.OrderResponse;
import com.oddprints.prodigi.pojos.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ProdigiTest {

    private String apiKey = System.getenv("PRODIGI_API_KEY_SANDBOX");
    private Prodigi prodigi;

    @BeforeEach
    public void setup() {
        prodigi = new Prodigi(apiKey);
    }

    @Test
    void can_create_order() {
        Order o = new Order();
        Prodigi p = new Prodigi(apiKey);
        OrderResponse response = p.createOrder(o);
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

    @Test
    public void can_create_and_fetch_order_id() {
        Order o = new Order();
        o.getRecipient().setName("bloggs");
        OrderResponse response = prodigi.createOrder(o);

        String id = response.getOrder().getId();
        Order fetchedOrder = prodigi.getOrder(id).getOrder();

        assertEquals("bloggs", fetchedOrder.getRecipient().getName());
        assertEquals(id, fetchedOrder.getId());
    }
}
