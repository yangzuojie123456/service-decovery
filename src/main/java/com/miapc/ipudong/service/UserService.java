package com.miapc.ipudong.service;

import com.miapc.ipudong.domain.User;
import com.miapc.ipudong.repository.UserRepository;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangwei on 2016/10/31.
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "login_user", key = "#loginName")
    public User findByUserName(String loginName) {
        return userRepository.findByUserName(loginName);
    }
}
