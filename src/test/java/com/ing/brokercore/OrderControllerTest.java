package com.ing.brokercore;

import com.ing.brokercore.controllers.OrderController;
import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.enums.OrderSide;
import com.ing.brokercore.services.OrderService;
import com.ing.brokercore.utils.OrderRequest;
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
        OrderSide orderSide = OrderSide.BUY;
        Double size = 10.0;
        Double price = 100.0;
        Orders mockOrder = new Orders();
        when(orderService.createOrder(customerId, assetName, orderSide, size, price)).thenReturn(mockOrder);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(customerId);
        orderRequest.setAssetName(assetName);
        orderRequest.setOrderSide(orderSide.toString());
        orderRequest.setSize(size);
        orderRequest.setPrice(price);

        // Execute
        ResponseEntity<Orders> response = orderController.createOrder(orderRequest);

        // Verify
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrder, response.getBody());
    }
}
