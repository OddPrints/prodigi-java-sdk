package com.oddprints.prodigi;

import com.oddprints.prodigi.pojos.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

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
        Order o = testOrder();
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
        Order order = testOrder();
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
        Order order = testOrder();
        OrderResponse response = prodigi.createOrder(order);
        String id = response.getOrder().getId();

        Order fetched = prodigi.getOrders(1, 0).getOrders().get(0);

        assertEquals(id, fetched.getId());
    }

    @Test
    public void can_create_order_and_get_status() {
        Order order = testOrder();
        OrderResponse response = prodigi.createOrder(order);
        assertEquals(Status.Stage.InProgress, response.getOrder().getStatus().getStage());
    }

    private Order testOrder() {
        return new Order.Builder(Order.ShippingMethod.Standard, new Recipient())
                .addImage("https://www.oddprints.com/images/header-dogcat.jpg", "GLOBAL-PHO-4x6", 1)
                .build();
    }

    @Test
    public void can_create_order_with_photos() {
        Order order = new Order.Builder(Order.ShippingMethod.Standard, new Recipient())
                .addImage("https://www.oddprints.com/images/header-dogcat.jpg", "GLOBAL-PHO-4x6", 1)
                .addImage("https://www.oddprints.com/images/header-dogcat.jpg", "GLOBAL-PHO-4x6-PRO", 2)
                .build();
        OrderResponse response = prodigi.createOrder(order);
        assertEquals(Status.Stage.InProgress, response.getOrder().getStatus().getStage());
    }

}
