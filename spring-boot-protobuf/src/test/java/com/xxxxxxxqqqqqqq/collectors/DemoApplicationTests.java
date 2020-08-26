package com.xxxxxxxqqqqqqq.collectors;

import com.xxxxxxxqqqqqqq.collectors.vo.LoginBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DemoApplicationTests {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8080/login");
        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
        urlc.setDoInput(true);
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        urlc.setRequestProperty("Accept", "application/x-protobuf");
        urlc.setRequestProperty("Content-Type","application/x-protobuf");

        LoginBuilder.LoginRequest loginRequest = LoginBuilder.LoginRequest.newBuilder().setUsername("111").setPwd("111").build();
        loginRequest.writeTo(urlc.getOutputStream());

        InputStream urlcInputStream = urlc.getInputStream();
        LoginBuilder.LoginResponse build = LoginBuilder.LoginResponse.newBuilder().mergeFrom(urlcInputStream).build();

    }

}