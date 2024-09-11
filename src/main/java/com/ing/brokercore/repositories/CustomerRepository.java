package com.ing.brokercore.repositories;

import com.ing.brokercore.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername( String username);
}
