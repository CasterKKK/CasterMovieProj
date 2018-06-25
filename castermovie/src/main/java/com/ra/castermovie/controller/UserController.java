package com.ra.castermovie.controller;

import com.ra.castermovie.controller.vo.user.*;
import com.ra.castermovie.logic.UserLogic;
import com.ra.castermovie.logic.common.Result;
import com.ra.castermovie.model.User;
import com.ra.castermovie.model.common.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/castermovie/user")
public class UserController {
    @Autowired
    private UserLogic userLogic;

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<User> register(@RequestBody RegisterVO vo) {
        return userLogic.register(vo.getUsername(), vo.getPassword(), vo.getEmail());
    }

    @PostMapping(value = "cancelUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<User> cancelUser(@RequestBody CancelUserVO vo) {
        return userLogic.cancelUser(vo.getId());
    }

    @PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<User> update(@RequestBody UpdateVO vo) {
        return userLogic.update(vo.getEmail(), new User(vo.getEmail(), Condition.EXISTING, System.currentTimeMillis(), vo.getUsername(), vo.getPassword(), vo.getRole(), vo.getState(), vo.getPaid(), vo.getLevel(), vo.getPoint()));
    }

    @PostMapping(value = "recharge", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<User> recharge(@RequestBody RechargeVO vo) {
        return userLogic.recharge(vo.getId(), vo.getMoney());
    }

    @GetMapping(value = "getByJwt", consumes = MediaType.ALL_VALUE)
    Result<User> getByJwt(@RequestParam String jwt) {
        return userLogic.getByJwt(jwt);
    }

    @GetMapping(value = "findById", consumes = MediaType.ALL_VALUE)
    Result<User> findById(@RequestParam String userId) {
        return userLogic.findById(userId);
    }
}
