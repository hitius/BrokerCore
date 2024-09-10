package com.ing.brokercore.controllers;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * lists all assets for customer, assetName is optional
     * @param customerId
     * @param assetName
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public ResponseEntity<List<Asset>> listAssets(@RequestParam Long customerId,
                                                  @RequestParam Optional<String> assetName) throws Exception {

        List<Asset> assetList = assetService.getListAssetsForCustomer( customerId, assetName);
        return ResponseEntity.ok(assetList);
    }

    /**
     * deposit money
     * @param customerId
     * @param amount
     * @return
     * @throws Exception
     */
    @PostMapping("/depositMoney")
    public ResponseEntity<Asset> depositMoney(@RequestParam Long customerId,
                                                 @RequestParam Double amount) throws Exception {

        Asset asset = assetService.depositMoney(customerId, amount);
        return ResponseEntity.ok(asset);
    }

    /**
     * withdraw money from customer and transfers to bank account via IBAN
     * @param customerId
     * @param amount
     * @param iban
     * @return
     * @throws Exception
     */
    @PostMapping("/withdrawMoney")
    public ResponseEntity<Asset> withdrawMoney(@RequestParam Long customerId,
                                               @RequestParam Double amount,
                                               @RequestParam String iban) throws Exception {

        Asset asset = assetService.withdrawMoney(customerId, amount, iban);
        return ResponseEntity.ok(asset);
    }

}
