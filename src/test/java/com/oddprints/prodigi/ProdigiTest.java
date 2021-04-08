package com.oddprints.prodigi;

import com.oddprints.prodigi.pojos.OrderResponse;
import com.oddprints.prodigi.pojos.Order;
import com.oddprints.prodigi.pojos.Status;
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
        assertEquals(Status.Stage.InProgress, response.getOrder().getStatus().getStage());
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
        Order order = new Order();
        order.getRecipient().setName("bloggs");
        OrderResponse response = prodigi.createOrder(order);

        String id = response.getOrder().getId();
        Order fetchedOrder = prodigi.getOrder(id).getOrder();

        assertEquals("bloggs", fetchedOrder.getRecipient().getName());
        assertEquals(id, fetchedOrder.getId());
    }

    @Test
    public void cannot_fetch_invalid_order() {
        try {
            prodigi.getOrder("blah").getOrder();
            fail("should have thrown");
        } catch (ProdigiError e) {
            assertEquals(400, e.getCode());
        }
    }

    @Test
    public void can_find_most_recent_order_by_fetching_1_order() {
        Order order = new Order();
        OrderResponse response = prodigi.createOrder(order);
        String id = response.getOrder().getId();

        Order fetched = prodigi.getOrders(1, 0).getOrders().get(0);

        assertEquals(id, fetched.getId());
    }

    @Test
    public void can_create_order_and_get_status() {
        Order order = new Order();
        OrderResponse response = prodigi.createOrder(order);
        assertEquals(Status.Stage.InProgress, response.getOrder().getStatus().getStage());
    }
}
