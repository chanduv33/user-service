package com.storesmanagementsystem.user.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
	private List<UserInfoBean> users;
	private List<ProductInfoBean> products;
	private List<OrderDetails> orders;
	private OrderDetails order;
	private UserInfoBean user;
	private DealerProductInfoBean dealerProd;
	private List<DealerProductInfoBean> dealerProds;
	private List<CartInfoBean> items;
	private List<Error> errors;
}
