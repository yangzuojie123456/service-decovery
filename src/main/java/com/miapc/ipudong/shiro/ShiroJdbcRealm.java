package com.miapc.ipudong.shiro;

import com.miapc.ipudong.domain.Role;
import com.miapc.ipudong.domain.User;
import com.miapc.ipudong.repository.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangwei on 2016/10/31.
 */
@Component
public class ShiroJdbcRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroJdbcRealm.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Instantiates a new Shiro jdbc realm.
     */
    public ShiroJdbcRealm() {
        setName("ShiroJdbcRealm");
        setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String) super.getAvailablePrincipal(principals);
        //到数据库查是否有此对象
        User user = userRepository.findByUserName(loginName);
        if (user != null) {
            SimpleAuthorizationInfo info = getAuthorizationInfo(user);


            return info;
        }
        return null;
    }

    private SimpleAuthorizationInfo getAuthorizationInfo(User user) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(user.getRolesName());
        List<Role> roleList = user.getRoles();
        for (Role role : roleList) {
            info.addStringPermissions(role.getPermissionsCode());
        }
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        final UsernamePasswordToken credentials = (UsernamePasswordToken) token;
        final String username = credentials.getUsername();
        if (username == null || credentials.getPassword() == null) {
            throw new AuthenticationException("username or password not provided");
        }
        final User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new AuthenticationException("Account does not exist ");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword().toCharArray(),
                ByteSource.Util.bytes(username), getName());
        return authenticationInfo;

    }
}
