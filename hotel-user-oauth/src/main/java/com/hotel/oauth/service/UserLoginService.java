package com.hotel.oauth.service;

import com.hotel.oauth.util.AuthToken;

public interface UserLoginService {

    //登录
    AuthToken login(String username, String password, String clientId, String clientSecret, String grant_type) throws Exception;
}
