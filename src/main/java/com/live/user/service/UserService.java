package com.live.user.service;

import com.live.user.model.User;

import java.util.Date;

public interface UserService {
    User queryUserInfo (String username);

    int updateUserLastLoginTime(String loginName, Date userLastLoginTime);
}
