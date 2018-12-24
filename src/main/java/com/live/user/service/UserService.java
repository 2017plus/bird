package com.live.user.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageInfo;
import com.live.user.model.User;

import java.util.List;

public interface UserService {
    User queryUserInfo (String username);
}
