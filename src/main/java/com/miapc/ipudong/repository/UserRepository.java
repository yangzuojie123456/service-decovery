package com.miapc.ipudong.repository;

import com.miapc.ipudong.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * Created by wangwei on 2016/10/29.
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    /**
     * Find by user name user.
     *
     * @param loginName the login name
     * @return the user
     */
    public User findByUserName(String loginName);
}
