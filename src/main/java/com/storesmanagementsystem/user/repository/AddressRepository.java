package com.storesmanagementsystem.user.repository;

import com.storesmanagementsystem.user.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
