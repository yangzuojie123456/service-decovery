package com.miapc.ipudong.repository;

import com.miapc.ipudong.Application;
import com.miapc.ipudong.constant.Gender;
import com.miapc.ipudong.constant.UserStatus;
import com.miapc.ipudong.domain.User;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by wangwei on 2016/10/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    @Rollback(true)
    @Ignore
    public void saveUserTest(){
        //String nickName, String idCard, String address, Gender gender, String email, String mobile, String password, String status
        User user=new User("Lucy","441481199301080870 ","上海市浦东新区郭守敬路12345号", Gender.FEMALE,"email@mail.com", "1388888888","234sadfa", UserStatus.VALID);
        user=userRepository.save(user);
        Assert.assertNotNull(user.getId());
        System.out.println(user.toString());
    }
    @Test
    public void findUserTest(){
        User user=userRepository.findOne(5L);
        Assert.assertNotNull(user);
        System.out.println(user.toString());
    }
}
