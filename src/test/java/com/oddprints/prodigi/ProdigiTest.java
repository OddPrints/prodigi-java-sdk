package com.oddprints.prodigi;

import static com.oddprints.prodigi.pojos.CountryCode.*;
import static org.junit.jupiter.api.Assertions.*;

import com.oddprints.prodigi.pojos.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProdigiTest {

    private String apiKey = System.getenv("PRODIGI_API_KEY_SANDBOX");
    private Prodigi prodigi;
    private static URL dummyUrl;

    static {
        try {
            dummyUrl = new URL("https://www.oddprints.com/images/header-dogcat.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        prodigi = new Prodigi(Prodigi.Environment.SANDBOX, apiKey);
    }

    @AfterAll
    public static void cleanup() {
        String apiKey = System.getenv("PRODIGI_API_KEY_SANDBOX");
        Prodigi prodigi = new Prodigi(Prodigi.Environment.SANDBOX, apiKey);
        List<Order> fetchedOrders = prodigi.getOrders(100, 0).getOrders();
        for (Order o : fetchedOrders) {
            prodigi.cancelOrder(o.getId());
        }
    }

    private Recipient dummyRecipient() {
        return dummyRecipient(GB);
    }

    private Recipient dummyRecipient(CountryCode countryCode) {
        return new Recipient("Bob", new Address("line1", "line2", "90210", countryCode, "Bristol"));
    }

    private Order dummyOrder() {
        return dummyOrder(Order.ShippingMethod.Standard, dummyRecipient());
    }

    private Order dummyOrder(Order.ShippingMethod shippingMethod, Recipient recipient) {
        return new Order.Builder(shippingMethod, recipient)
                .addImage(dummyUrl, "GLOBAL-PHO-4x6", 1)
                .build();
    }

    @Test
    void can_create_order() {
        Order o = dummyOrder();
        Prodigi p = new Prodigi(Prodigi.Environment.SANDBOX, apiKey);
        OrderResponse response = p.createOrder(o);
        assertEquals(Status.Stage.InProgress, response.getOrder().getStatus().getStage());
    }

    @Test
    void must_supply_apikey() {
        try {
            new Prodigi(Prodigi.Environment.SANDBOX, "");
            new Prodigi(Prodigi.Environment.SANDBOX, null);
            fail(); //  should have thrown
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void can_create_and_fetch_order_id() {
        Order order = dummyOrder();
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
        Order order = dummyOrder();
        OrderResponse response = prodigi.createOrder(order);
        String id = response.getOrder().getId();

        Order fetched = prodigi.getOrders(1, 0).getOrders().get(0);

        assertEquals(id, fetched.getId());
    }

    @Test
    public void can_create_order_and_get_status() {
        Order order = dummyOrder();
        OrderResponse response = prodigi.createOrder(order);
        assertEquals(Status.Stage.InProgress, response.getOrder().getStatus().getStage());
    }

    @Test
    public void can_create_order_with_photos() {
        Order order =
                new Order.Builder(Order.ShippingMethod.Standard, dummyRecipient())
                        .addImage(dummyUrl, "GLOBAL-PHO-4x6", 1)
                        .addImage(dummyUrl, "GLOBAL-PHO-4x6-PRO", 2)
                        .build();
        OrderResponse response = prodigi.createOrder(order);
        assertEquals(Status.Stage.InProgress, response.getOrder().getStatus().getStage());
    }

    @Test
    public void can_create_order_with_phone_number() {
        Recipient recipient = dummyRecipient();
        recipient.setPhoneNumber("123456");
        Order order =
                new Order.Builder(Order.ShippingMethod.Standard, recipient)
                        .addImage(dummyUrl, "GLOBAL-PHO-4x6", 1)
                        .build();
        OrderResponse response = prodigi.createOrder(order);
        assertEquals("123456", response.getOrder().getRecipient().getPhoneNumber());
    }

    @Test
    public void can_cancel_order() {
        Order order = dummyOrder();

        OrderResponse response = prodigi.createOrder(order);
        boolean cancelled = prodigi.cancelOrder(response.getOrder().getId());
        assertTrue(cancelled);
    }

    @Test
    public void cannot_update_address_country() {
        Recipient recipientGB = dummyRecipient(GB);
        Order order =
                new Order.Builder(Order.ShippingMethod.Standard, recipientGB)
                        .addImage(dummyUrl, "GLOBAL-PHO-4x6", 1)
                        .build();
        OrderResponse response = prodigi.createOrder(order);
        Recipient recipientUS = dummyRecipient(US);
        boolean updated = prodigi.updateRecipient(response.getOrder().getId(), recipientUS);
        assertFalse(updated);
    }

    @Test
    public void can_update_address_name() {
        Order order = dummyOrder();
        OrderResponse response = prodigi.createOrder(order);
        Recipient brian = dummyRecipient();
        brian.setName("Brian");
        boolean updated = prodigi.updateRecipient(response.getOrder().getId(), brian);
        assertTrue(updated);
    }

    @Test
    public void can_check_if_address_is_updateable() {
        Order order = dummyOrder();
        OrderResponse response = prodigi.createOrder(order);
        Recipient brian = dummyRecipient();
        brian.setName("Brian");
        String orderId = response.getOrder().getId();
        boolean updateable = prodigi.canChangeRecipientDetails(orderId);
        assertTrue(updateable);

        prodigi.cancelOrder(orderId);
        boolean stillUpdateable = prodigi.canChangeRecipientDetails(orderId);
        assertFalse(stillUpdateable);
    }
}
