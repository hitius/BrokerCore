package com.ing.brokercore.services;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.repositories.AssetRepository;
import com.ing.brokercore.utils.BusinessException;
import com.ing.brokercore.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.ing.brokercore.utils.BusinessException.*;
import static com.ing.brokercore.utils.Constants.REGEX_IBAN;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    CustomerService customerService;

    /**
     * gives asset list for customer
     * @param customerId
     * @param assetName
     * @return
     * @throws Exception
     */
    public List<Asset> getListAssetsForCustomer( Long customerId, Optional<String> assetName) throws BusinessException {
        List<Asset> assetList = new ArrayList<Asset>();

        // lets check customer
        Customer customer = customerService.getCustomerById(customerId);

        if (assetName.isPresent()) {
            assetList = assetRepository.findAllByCustomerAndAssetName(customer, assetName.get());
        } else {
            assetList = assetRepository.findAllByCustomer(customer);
        }

        if (assetList.isEmpty()) {
            throw new BusinessException(CUSTOMER_DOES_NOT_HAVE_ANY_ASSET);
        }

        return assetList;
    }

    /**
     * withdraw money
     * @param customerId
     * @param amount
     * @param iban
     * @return
     * @throws Exception
     */
    @Transactional
    public Asset withdrawMoney(Long customerId, Double amount, String iban) throws BusinessException {
        Asset asset = findByCustomerIdAndAssetName(customerId, Constants.TRY);

        validationsForWithdrawMoney(asset, amount, iban);

        Boolean isSuccess = someMoneyTransferMethod(asset, amount, iban);
        if (!isSuccess) {
            throw new BusinessException(PROBLEM_ON_MONEY_TRANSFER);
        }

        asset.setSize(asset.getSize() - amount);
        asset.setUsableSize(asset.getUsableSize() - amount);
        return assetRepository.save(asset);
    }

    /**
     *  validations
     * @param asset
     * @param amount
     * @param iban
     * @throws BusinessException
     */
    private void validationsForWithdrawMoney(Asset asset, Double amount, String iban) throws BusinessException {
        if (amount <= 0) {
            throw new BusinessException(NEGATIVE_AMOUNT);
        }

        if (!Pattern.matches(REGEX_IBAN, iban)) {
            throw new BusinessException(INVALID_IBAN);
        }

        if (asset.getUsableSize() < amount) {
            throw new BusinessException(INSUFFICIENT_FUNDS);
        }
    }

    /**
     * deposit money
     * @param customerId
     * @param amount
     * @return
     * @throws Exception
     */
    @Transactional
    public Asset depositMoney(Long customerId, Double amount) throws BusinessException {
        Asset asset = findByCustomerIdAndAssetName(customerId, Constants.TRY);

        asset.setSize(asset.getSize() + amount);
        asset.setUsableSize(asset.getUsableSize() + amount);
        return assetRepository.save(asset);
    }

    /**
     *
     * @param customerId
     * @param assetName
     * @return
     * @throws BusinessException
     */
    public Asset findByCustomerIdAndAssetName(Long customerId, String assetName) throws BusinessException {
        return assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
                .orElseThrow(() -> new BusinessException(ASSET_NOT_FOUND));
    }

    /**
     *
     * @param asset
     * @param amount
     * @param iban
     * @return
     */
    private Boolean someMoneyTransferMethod(Asset asset, Double amount, String iban) {
        // do some tranfer
        return true;
    }
}
