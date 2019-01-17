package com.live.user.service.impl;

import com.live.user.mapper.UserMapper;
import com.live.user.model.User;
import com.live.user.model.UserExample;
import com.live.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserInfo(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public int updateUserLastLoginTime(String loginName, Date userLastLoginTime) {
        User user = new User();
        user.setUserLastLoginTime(userLastLoginTime);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        return userMapper.updateByExampleSelective(user, example);
    }

    @Override
    public int register(User user) throws DataAccessException {
        return userMapper.insertSelective(user);
    }

    @Override
    /**
     * 1-用户名；2-手机号；3-邮箱
     */
    public long registerUserCheck(Integer key, String value) {
        User user = new User();
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (key == 1) {
            criteria.andLoginNameEqualTo(value);
        } else if (key == 2) {
            criteria.andUserTelEqualTo(value);
        } else if (key == 3) {
            criteria.andUserEmailEqualTo(value);
        }
        return userMapper.countByExample(example);
    }
}
