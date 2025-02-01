package com.storesmanagementsystem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storesmanagementsystem.user.contracts.Address;
import com.storesmanagementsystem.user.contracts.UserInfoBean;
import com.storesmanagementsystem.user.domain.AddressVerifier;
import com.storesmanagementsystem.user.domain.UserDetails;
import com.storesmanagementsystem.user.enums.AddressType;
import com.storesmanagementsystem.user.enums.Roles;
import com.storesmanagementsystem.user.exceptions.DetailsNotFoundException;
import com.storesmanagementsystem.user.exceptions.EmailAlreadyExistsException;
import com.storesmanagementsystem.user.exceptions.MobileNumberAlreadyExistsException;
import com.storesmanagementsystem.user.repository.AddressRepository;
import com.storesmanagementsystem.user.repository.AddressVerifierRepository;
import com.storesmanagementsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private AddressRepository addressRepository;

    private AddressVerifierRepository addressVerifierRepository;

    private PasswordEncoder encoder;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder,
                           AddressRepository addressRepository,
                           AddressVerifierRepository addressVerifierRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.addressRepository = addressRepository;
        this.addressVerifierRepository = addressVerifierRepository;
    }

    @Override
    public UserInfoBean register(UserInfoBean user) {

        UserDetails findByUsername = userRepository.findByUsername(user.getUsername());
        if (null == findByUsername) {
            UserDetails findByMobileNumber = userRepository.findByMobileNumber(user.getMobileNumber());
            if (null == findByMobileNumber) {
                UserDetails usr = new UserDetails();
                usr.setName(user.getName());
                usr.setUsername(user.getUsername());
                usr.setMobileNumber(user.getMobileNumber());
                usr.setRole(getRole(user.getRole()));
                usr.setPassword(encoder.encode(user.getPassword()));
                UserDetails userDetails = userRepository.save(usr);
                return mapper.convertValue(userDetails, UserInfoBean.class);
            } else {
                throw new MobileNumberAlreadyExistsException();
            }
        } else {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }
    }

    @Override
    public boolean updatePassword(UserInfoBean bean) {

        Optional<UserDetails> byId = userRepository.findById(bean.getId());
        if (byId.isPresent()) {
            UserDetails user = byId.get();
            user.setPassword(encoder.encode(bean.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserInfoBean getUser(Integer id) {
        Optional<UserDetails> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            UserDetails userDetails = byId.get();
            UserInfoBean userInfoBean = mapper.convertValue(userDetails, UserInfoBean.class);
            if (null != userDetails.getAddresses() && !userDetails.getAddresses().isEmpty()) {
                userDetails.getAddresses().stream().forEach(add -> userInfoBean.getAddress().add(mapper.convertValue(add, Address.class)));
                return userInfoBean;
            }
            return userInfoBean;
        } else {
            throw new DetailsNotFoundException();
        }
    }

    @Override
    public List<UserInfoBean> getUsers(String role) {
        List<UserDetails> userDetailsByRoleContaining = userRepository.findUserDetailsByRoleContaining(role);
        if (!userDetailsByRoleContaining.isEmpty()) {
            return userDetailsByRoleContaining.stream().map(u -> {
                UserInfoBean userInfoBean = mapper.convertValue(u, UserInfoBean.class);
                if (null != u.getAddresses() && !u.getAddresses().isEmpty()) {
                    u.getAddresses().stream().forEach(add -> userInfoBean.getAddress().add(mapper.convertValue(add, Address.class)));
                    return userInfoBean;
                }
                return userInfoBean;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public UserInfoBean getUserByPhonenNum(Long mobileNum) {
        UserDetails byMobileNumber = userRepository.findByMobileNumber(mobileNum);
        if (null != byMobileNumber) {
            return mapper.convertValue(byMobileNumber, UserInfoBean.class);
        } else {
            throw new DetailsNotFoundException();
        }
    }

    @Override
    public Boolean update(UserInfoBean userInfoBean, int id) {
        Optional<UserDetails> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            if (null != userInfoBean.getAddress() && !userInfoBean.getAddress().isEmpty()) {
                UserDetails userDetails = byId.get();
                for (Address address :
                        userInfoBean.getAddress()) {
                    AddressVerifier byPincode = null;
                    boolean isAddressValid = false;
                    String addressType = null;
                    if (null != address.getPincode()) {
                        byPincode = addressVerifierRepository.findByPincode(address.getPincode());
                        if (null != byPincode) {
                            isAddressValid = true;
                        } else {
                            throw new IllegalArgumentException("Please provide valid pincode");
                        }
                    } else {
                        throw new IllegalArgumentException("Please provide valid pincode");
                    }

                    if (null != address.getAddressType()) {
                        if (address.getAddressType().equals(AddressType.SHIPPING.name())) {
                            addressType = AddressType.SHIPPING.name();
                        } else if (address.getAddressType().equals(AddressType.BILLING.name())) {
                            addressType = AddressType.BILLING.name();
                        }
                    } else {
                        addressType = AddressType.SHIPPING.name();
                    }

                    if (isAddressValid) {
                        if (null != address.getId()) {
                            Optional<com.storesmanagementsystem.user.domain.Address> addressByid = addressRepository.findById(address.getId());
                            if (addressByid.isPresent()) {
                                com.storesmanagementsystem.user.domain.Address addrss = addressByid.get();
                                addrss.setAddressLine1(address.getAddressLine1());
                                addrss.setAddressLine2(address.getAddressLine2());
                                addrss.setCity(byPincode.getCity());
                                addrss.setState(byPincode.getState());
                                addrss.setStreet(address.getStreet());
                                addrss.setCountry(byPincode.getCountry());
                                addrss.setPincode(byPincode.getPincode());
                                addrss.setAddressType(addressType);
                                addressRepository.save(addrss);
                                return true;
                            }
                        } else {
                            com.storesmanagementsystem.user.domain.Address addrss = new com.storesmanagementsystem.user.domain.Address();
                            addrss.setAddressLine1(address.getAddressLine1());
                            addrss.setAddressLine2(address.getAddressLine2());
                            addrss.setCity(byPincode.getCity());
                            addrss.setState(byPincode.getState());
                            addrss.setStreet(address.getStreet());
                            addrss.setCountry(byPincode.getCountry());
                            addrss.setPincode(byPincode.getPincode());
                            addrss.setAddressType(addressType);
                            addrss.setUser(userDetails);
                            userDetails.getAddresses().add(addrss);
                            userRepository.save(userDetails);
                            return true;
                        }
                    }
                }
            }
        } else {
            throw new DetailsNotFoundException();
        }
        return false;
    }

    @Override
    public List<UserInfoBean> getAllManufacturersDetails() {
        List<UserDetails> roleManufacturer = userRepository.findByRole("ROLE_MANUFACTURER");
        if (!roleManufacturer.isEmpty()) {
            return roleManufacturer.stream().map(m-> mapper.convertValue(m,UserInfoBean.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean removeManufacturer(int userId) {
        Optional<UserDetails> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        } else {
            throw new DetailsNotFoundException();
        }
    }

    private String getRole(String role) {
        String roleT = "ROLE_";
        if (role.equalsIgnoreCase(Roles.ADMIN.name())) {
            return roleT + Roles.ADMIN.name();
        } else if (role.equalsIgnoreCase(Roles.DEALER.name())) {
            return roleT + Roles.DEALER.name();
        } else if (role.equalsIgnoreCase(Roles.MANUFACTURER.name())) {
            return roleT + Roles.MANUFACTURER.name();
        } else if (role.equalsIgnoreCase(Roles.CUSTOMER.name())) {
            return roleT + Roles.CUSTOMER.name();
        }
        return null;
    }
}
