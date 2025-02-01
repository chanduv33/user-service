package com.storesmanagementsystem.user.service;

import com.storesmanagementsystem.user.contracts.UserInfoBean;

import java.util.List;

public interface UserService {
    public UserInfoBean register(UserInfoBean userInfoBean);

    public boolean updatePassword(UserInfoBean bean);

    public UserInfoBean getUser(Integer id);

    public List<UserInfoBean> getUsers(String role);

    public UserInfoBean getUserByPhonenNum(Long mobileNum);

    public Boolean update(UserInfoBean userInfoBean,int id);

    public List<UserInfoBean> getAllManufacturersDetails();

    public boolean removeManufacturer(int userId);
}
