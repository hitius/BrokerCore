package com.ing.brokercore;

import com.ing.brokercore.controllers.OrderController;
import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    public void testListOrders() throws Exception {
        // Setup
        Long customerId = 1L;
        Date startDate = new Date();
        Date endDate = new Date();
        List<Orders> mockOrderList = new ArrayList<>();

        // Execute
        ResponseEntity<List<Orders>> response = orderController.listOrders(customerId,startDate,endDate);

        // Verify
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testPlaceOrder() throws Exception {
        // Setup
        Long customerId = 1L;
        String assetName = "AssetName";
        String type = "BUY";
        Double size = 10.0;
        Double price = 100.0;
        Orders mockOrder = new Orders();
        when(orderService.createOrder(customerId, assetName, type, size, price)).thenReturn(mockOrder);

        // Execute
        ResponseEntity<Orders> response = orderController.createOrder(customerId, assetName, type, size, price);

        // Verify
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrder, response.getBody());
    }
}
