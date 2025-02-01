package com.storesmanagementsystem.user.contracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerProductInfoBean {

	private Integer dealersProductId;

	private Double sellingPrice;

	private Integer quantity;

	private Integer productId;

	private Integer minimumQuantity;

	private String imageUrl;

	private String productName;

	private String manufacturerName;

	private UserInfoBean user;

	private ProductInfoBean product;
}
