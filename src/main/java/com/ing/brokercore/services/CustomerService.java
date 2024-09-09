package com.ing.brokercore.services;

import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    /**
     *
     * @param customerId
     * @param amount
     * @return
     * @throws Exception
     */
    public Customer depositMoney(Long customerId, Double amount) throws Exception {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new Exception("Customer not found")
        );

        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);

        return customer;
    }

    /**
     *
     * @param customerId
     * @param amount
     * @param iban
     * @return
     * @throws Exception
     */
    public Customer withdrawMoney(Long customerId, Double amount, String iban) throws Exception {

        Customer customer = getCustomerById(customerId);

        if (customer.getBalance() < amount) {
            throw new Exception("Insufficient funds");
        }

        Boolean isSuccess = someMoneyTransferMethod(customer, amount, iban);
        if (!isSuccess) {
            throw new Exception("Problem on money transfer");
        }

        customer.setBalance(customer.getBalance() - amount);
        customerRepository.save(customer);
        return customer;
    }

    /**
     *
     * @param customer
     * @param amount
     * @param iban
     * @return
     */
    private Boolean someMoneyTransferMethod(Customer customer, Double amount, String iban) {
        // do some tranfer
        return true;
    }

    /**
     * This method finds customer by id
     * @param customerId
     * @return
     */
    public Customer getCustomerById(Long customerId) throws Exception {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new Exception("Customer not found")
        );
    }
}
