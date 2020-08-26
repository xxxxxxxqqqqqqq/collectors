package com.xxxxxxxqqqqqqq.collectors.controller;

import com.xxxxxxxqqqqqqq.collectors.vo.LoginBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @PostMapping(value = "/login", produces = "application/x-protobuf")
    public LoginBuilder.LoginResponse login(@RequestBody LoginBuilder.LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String pwd = loginRequest.getPwd();
        //
        if (!"111".equals(username) || !"111".equals(pwd)) {
            throw new RuntimeException("error");
        }

        LoginBuilder.LoginResponse.Builder builder = LoginBuilder.LoginResponse.newBuilder();
        builder.setUsername(username);
        builder.setAccessToken("1234567890000000");
        return builder.build();
    }

}
