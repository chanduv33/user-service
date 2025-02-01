package com.storesmanagementsystem.user.contracts;

import com.storesmanagementsystem.user.domain.UserDetails;
import com.storesmanagementsystem.user.enums.AddressType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class Address {

    private Integer id;

    private String addressLine1;

    private String addressLine2;

    private String street;

    private String city;

    private String state;

    private String country;

    private Integer pincode;

    private String addressType;
}
