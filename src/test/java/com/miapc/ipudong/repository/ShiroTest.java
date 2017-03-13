package com.miapc.ipudong.repository;

import com.miapc.ipudong.Application;
import com.miapc.ipudong.domain.Permission;
import com.miapc.ipudong.domain.Role;
import com.miapc.ipudong.domain.User;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by wangwei on 2016/11/1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class ShiroTest {
    @Autowired
    private DefaultPasswordService passwordService;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Ignore
    public void addTestUserAndPermission() {
        // define permissions
        final Permission p1 = new Permission();
        p1.setName("你好");
        p1.setPermissionCode("SAY_HI");
        permissionRepository.save(p1);
        final Permission p2 = new Permission();
        p2.setName("再见");
        p2.setPermissionCode("SAY_BYE");
        permissionRepository.save(p2);
        // define roles
        final Role roleAdmin = new Role();
        roleAdmin.setName("TEST");
        roleAdmin.getPermissions().add(p1);
        roleRepository.save(roleAdmin);
        // define user
        final User user = userRepository.findOne(5L);
        user.setPassword(passwordService.encryptPassword("123qwe"));
        user.getRoles().add(roleAdmin);
        userRepository.save(user);
    }
}
