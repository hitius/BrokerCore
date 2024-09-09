package com.ing.brokercore.repositories;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByCustomerAndAssetName(Customer customer, String assetName);

    List<Asset> findAllByCustomer(Customer customer);

    List<Asset> findAllByCustomerAndAssetName(Customer customer, String assetName);
}
