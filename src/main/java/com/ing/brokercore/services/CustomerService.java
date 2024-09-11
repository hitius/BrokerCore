package com.ing.brokercore.services;

import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.repositories.CustomerRepository;
import com.ing.brokercore.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ing.brokercore.utils.BusinessException.CUSTOMER_NOT_FOUND;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method finds customer by id
     * @param customerId
     * @return
     */
    public Customer getCustomerById(Long customerId) throws BusinessException {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new BusinessException(CUSTOMER_NOT_FOUND)
        );
    }

    /**
     *
     * @param customer
     * @return
     */
    public Customer savePassword(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    /**
     *
     * @param username
     * @return
     * @throws BusinessException
     */
    public Customer getProfile(String username) throws BusinessException {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(BusinessException.USER_NOT_FOUND));
    }
}
