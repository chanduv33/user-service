package com.storesmanagementsystem.user.controller;

import com.storesmanagementsystem.user.contracts.CommonResponse;
import com.storesmanagementsystem.user.contracts.UserInfoBean;
import com.storesmanagementsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(path = "/User/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> register(@RequestBody UserInfoBean user) {
        CommonResponse resp = new CommonResponse();
        UserInfoBean userInfo = service.register(user);
        if (null != userInfo) {
            resp.setUser(userInfo);
            return ResponseEntity.ok().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(path = "/User/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getUserById(@PathVariable("id") Integer id) {
        CommonResponse resp = new CommonResponse();
        UserInfoBean userInfo = service.getUser(id);
        if (null != userInfo) {
            resp.setUser(userInfo);
            return ResponseEntity.ok().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(path = "/Users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getUsers(@RequestParam("role") String role) {
        CommonResponse resp = new CommonResponse();
        List<UserInfoBean> userInfo = service.getUsers(role);
        if (null != userInfo) {
            resp.setUsers(userInfo);
            return ResponseEntity.ok().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(path = "/User", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getUserByPhone(@RequestParam("phone") Long mobileNum) {
        CommonResponse resp = new CommonResponse();
        UserInfoBean userInfo = service.getUserByPhonenNum(mobileNum);
        if (null != userInfo) {
            resp.setUser(userInfo);
            return ResponseEntity.ok().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }



    @PutMapping(path = "/User/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> update(@RequestBody UserInfoBean user, @PathVariable("id") int id) {
        CommonResponse resp = new CommonResponse();
        boolean updated = service.update(user,id);
        if (updated) {
            return ResponseEntity.ok().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }

    @DeleteMapping(path = "/User/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> deleteUserById(@PathVariable("id") int id) {
        CommonResponse resp = new CommonResponse();
        boolean deleted = service.removeManufacturer(id);
        if (deleted) {
            return ResponseEntity.ok().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }


}
