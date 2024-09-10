package com.ing.brokercore.services;

import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.repositories.CustomerRepository;
import com.ing.brokercore.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ing.brokercore.utils.BusinessException.CUSTOMER_NOT_FOUND;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    /**
     * This method finds customer by id
     * @param customerId
     * @return
     */
    public Customer getCustomerById(Long customerId) throws Exception {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new BusinessException(CUSTOMER_NOT_FOUND)
        );
    }
}
