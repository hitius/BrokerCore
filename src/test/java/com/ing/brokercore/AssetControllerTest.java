package com.ing.brokercore;

import com.ing.brokercore.controllers.AssetController;
import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.services.AssetService;
import com.ing.brokercore.utils.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AssetControllerTest {

    @InjectMocks
    private AssetController assetController;

    @Mock
    private AssetService assetService;

    @Test
    public void testListAssets() throws Exception {
        // Setup
        Long customerId = 1L;
        Optional<String> assetName = Optional.of(Constants.TRY);
        List<Asset> mockAssetList = new ArrayList<>();
        when(assetService.getListAssetsForCustomer(customerId, assetName)).thenReturn(mockAssetList);

        // Execute
        ResponseEntity<List<Asset>> response = assetController.listAssets(customerId, assetName);

        // Verify
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAssetList, response.getBody());
    }

    @Test
    public void testDepositMoney() throws Exception {
        // Setup
        Long customerId = 1L;
        Double amount = 100.0;
        Asset mockAsset = new Asset();
        when(assetService.depositMoney(customerId, amount)).thenReturn(mockAsset);

        // Execute
        ResponseEntity<Asset> response = assetController.depositMoney(customerId, amount);

        // Verify
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAsset, response.getBody());
    }

    @Test
    public void testWithdrawMoney() throws Exception {
        // Setup
        Long customerId = 1L;
        Double amount = 100.0;
        String iban = "IBAN123";
        Asset mockAsset = new Asset();
        when(assetService.withdrawMoney(customerId, amount, iban)).thenReturn(mockAsset);

        // Execute
        ResponseEntity<Asset> response = assetController.withdrawMoney(customerId, amount, iban);

        // Verify
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAsset, response.getBody());
    }
}
