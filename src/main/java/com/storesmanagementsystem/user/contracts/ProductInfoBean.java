package com.storesmanagementsystem.user.contracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString.Exclude;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoBean {

	private Integer productId;

	private String productName;

	private Double productCost;

	private String description;

	private String imageUrl;

	private Integer quantity;

	private String manufacturerName;

	private UserInfoBean users;
}
