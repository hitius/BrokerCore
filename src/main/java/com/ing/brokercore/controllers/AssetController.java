package com.ing.brokercore.controllers;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/list")
    public ResponseEntity<List<Asset>> listAssets(@RequestParam Long customerId,
                                                  @RequestParam Optional<String> assetName) throws Exception {

        List<Asset> assetList = assetService.getListAssetsForCustomer( customerId, assetName);
        return ResponseEntity.ok(assetList);
    }

}
