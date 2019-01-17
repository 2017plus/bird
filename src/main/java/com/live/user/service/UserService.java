package com.live.user.service;

import com.live.user.model.User;
import org.springframework.dao.DataAccessException;

import java.util.Date;

public interface UserService {
    User queryUserInfo (String username);

    int updateUserLastLoginTime(String loginName, Date userLastLoginTime);

    int register (User user) throws DataAccessException;

    long registerUserCheck(Integer key,String value);
}
