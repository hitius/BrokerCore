package com.ing.brokercore.repositories;

import com.ing.brokercore.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByCustomerIdAndCreateDateBetween(Long customerId, Date startDate, Date endDate);
}
