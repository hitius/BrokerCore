package com.ing.brokercore.utils;


import com.ing.brokercore.utils.annotations.ValidCurrency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequest {

    @NotNull(message = "Customer ID cannot be empty")
    private Long customerId;

    @NotBlank(message = "Asset name cannot be blank")
    @ValidCurrency(message = "Only TRY is allowed as asset")
    private String assetName;

    @NotBlank(message = "OrderSide cannot be blank")
    private String orderSide;

    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    @NotNull(message = "Size cannot be empty")
    private Double size;

    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    @NotNull(message = "Price cannot be empty")
    private Double price;

}
