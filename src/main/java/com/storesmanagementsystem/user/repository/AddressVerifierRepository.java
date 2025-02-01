package com.storesmanagementsystem.user.repository;

import com.storesmanagementsystem.user.domain.AddressVerifier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressVerifierRepository extends JpaRepository<AddressVerifier,Integer> {
    AddressVerifier findByPincode(Integer pincode);
}
