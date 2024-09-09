package com.ing.brokercore.services;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    CustomerService customerService;

    public List<Asset> getListAssetsForCustomer( Long customerId, Optional<String> assetName) throws Exception {
        List<Asset> assetList = new ArrayList<Asset>();

        // lets check customer
        Customer customer = customerService.getCustomerById(customerId);

        if (assetName.isPresent()) {
            assetList = assetRepository.findAllByCustomerAndAssetName(customer, assetName.get());
        } else {
            assetList = assetRepository.findAllByCustomer(customer);
        }


        if (assetList.isEmpty()) {
            throw new Exception("Asset List empty for customer: " + customerId);
        }

        return assetList;
    }
}
