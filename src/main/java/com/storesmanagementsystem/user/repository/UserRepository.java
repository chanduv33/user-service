package com.storesmanagementsystem.user.repository;

import com.storesmanagementsystem.user.domain.UserDetails;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDetails,Integer> {

    UserDetails findByUsername(String username);

    UserDetails findByMobileNumber(Long mobileNumber);

    List<UserDetails> findUserDetailsByRoleContaining(String role);

    List<UserDetails> findByRole(String roleManufacturer);
}
