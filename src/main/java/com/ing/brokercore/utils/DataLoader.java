package com.ing.brokercore.utils;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.enums.OrderSide;
import com.ing.brokercore.enums.OrderStatus;
import com.ing.brokercore.repositories.AssetRepository;
import com.ing.brokercore.repositories.CustomerRepository;
import com.ing.brokercore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws BusinessException {

        if (customerRepository.findByUsername("admin").isEmpty()) {
            Customer admin = new Customer();
            admin.setUsername("admin");
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("1"));
            admin.setEmail("admin@example.com");
            admin.setRole("ROLE_ADMIN");
            customerRepository.save(admin);

            Asset adminAsset = new Asset();
            adminAsset.setAssetName(Constants.TRY);
            adminAsset.setSize(10000.0);
            adminAsset.setUsableSize(10000.0);
            adminAsset.setCustomer(admin);
            assetRepository.save(adminAsset);

            Orders adminBuyOrder = new Orders();
            adminBuyOrder.setCustomer(admin);
            adminBuyOrder.setAssetName(Constants.TRY);
            adminBuyOrder.setOrderSide(OrderSide.BUY);
            adminBuyOrder.setSize(500.0);
            adminBuyOrder.setPrice(100.0);
            adminBuyOrder.setStatus(OrderStatus.PENDING);
            orderRepository.save(adminBuyOrder);

            Orders adminSellOrder = new Orders();
            adminSellOrder.setCustomer(admin);
            adminSellOrder.setAssetName(Constants.TRY);
            adminSellOrder.setOrderSide(OrderSide.SELL);
            adminSellOrder.setSize(500.0);
            adminSellOrder.setPrice(100.0);
            adminSellOrder.setStatus(OrderStatus.PENDING);
            orderRepository.save(adminSellOrder);
        }

        if (customerRepository.findByUsername("user").isEmpty()) {
            Customer user = new Customer();
            user.setUsername("user");
            user.setName("user");
            user.setPassword(passwordEncoder.encode("1"));
            user.setEmail("user@example.com");
            user.setRole("ROLE_USER");
            customerRepository.save(user);

            Asset userAsset = new Asset();
            userAsset.setAssetName(Constants.TRY);
            userAsset.setSize(5000.0);
            userAsset.setUsableSize(5000.0);
            userAsset.setCustomer(user);
            assetRepository.save(userAsset);

            Orders userBuyOrder = new Orders();
            userBuyOrder.setCustomer(user);
            userBuyOrder.setAssetName(Constants.TRY);
            userBuyOrder.setOrderSide(OrderSide.BUY);
            userBuyOrder.setSize(300.0);
            userBuyOrder.setPrice(100.0);
            userBuyOrder.setStatus(OrderStatus.PENDING);
            orderRepository.save(userBuyOrder);

            Orders userSellOrder = new Orders();
            userSellOrder.setCustomer(user);
            userSellOrder.setAssetName(Constants.TRY);
            userSellOrder.setOrderSide(OrderSide.SELL);
            userSellOrder.setSize(300.0);
            userSellOrder.setPrice(100.0);
            userSellOrder.setStatus(OrderStatus.PENDING);
            orderRepository.save(userSellOrder);
        }

        System.out.println("Admin and user with assets and orders created if not exists.");
    }
}
