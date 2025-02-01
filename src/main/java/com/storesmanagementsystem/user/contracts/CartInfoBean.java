package com.storesmanagementsystem.user.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartInfoBean {

	private Integer itemId;

	private String role;

	private UserInfoBean user;

	private Integer itemProductId;

	private Integer quantity;

	private ProductInfoBean product;

	private DealerProductInfoBean dealerProd;

}
