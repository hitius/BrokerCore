package com.ing.brokercore.repositories;

import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByCustomerIdAndCreateDateBetweenAndStatusIsNot(Long customerId, Date startDate, Date endDate, OrderStatus status);
}
