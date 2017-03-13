package com.miapc.ipudong.rest;

import com.miapc.ipudong.cache.MemcacheKey;
import com.miapc.ipudong.constant.Constant;
import com.miapc.ipudong.domain.User;
import com.miapc.ipudong.repository.UserRepository;
import com.miapc.ipudong.service.UserService;
import com.miapc.ipudong.shiro.ShiroMemCacheManager;
import com.miapc.ipudong.utils.SecurityUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by wangwei on 2016/10/31.
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ShiroMemCacheManager shiroMemCacheManager;
    @Value("${system.domain}")
    private String domain;

    /**
     * Authenticate string.
     *
     * @param credentials the credentials
     * @return the string
     */
    @RequestMapping(value = "/login", method = POST)
    public
    @ResponseBody
    String authenticate(@RequestBody final UsernamePasswordToken credentials, HttpServletRequest request, HttpServletResponse response) {
        log.debug("Authenticating {}", credentials.getUsername());
        final Subject subject = SecurityUtils.getSubject();
        JSONObject result = new JSONObject();
        String userName = credentials.getUsername();
        try {
            subject.login(credentials);
            if (subject.isAuthenticated()) {
                User user = userService.findByUserName(credentials.getUsername());
                if (user != null) {
                    String userTokenHash = generateUserToken(request, user);
                    shiroMemCacheManager.getCache(MemcacheKey.MEMCACHE_KEY_USER_TOKEN).put(userTokenHash, credentials);
                    Cookie cookie = new Cookie(Constant.USER_CENTER_TOKEN, userTokenHash);
                    cookie.setDomain(domain);
                    cookie.setMaxAge(3600);
                    response.addCookie(cookie);
                    return JSONObject.valueToString(user);
                } else {
                    result.append("error", "1");
                    result.append("message", userName + "未知账户");
                }
            }
        } catch (UnknownAccountException uae) {
            result.append("error", "1");
            result.append("message", userName + "未知账户");
        } catch (IncorrectCredentialsException ice) {
            result.append("error", "1");
            result.append("message", userName + "用户名或密码错误");
        } catch (LockedAccountException lae) {
            result.append("error", "1");
            result.append("message", userName + "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            result.append("error", "1");
            result.append("message", userName + "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            result.append("error", "1");
            result.append("message", userName + "用户名或密码错误");
        }
        return result.toString();
    }

    private String generateUserToken(HttpServletRequest request, User user) {
        String host = request.getHeader("host");
        String userAgent = request.getHeader("user-agent");
        String userToken = SecurityUtil.generateToken("USERID=" + user.getId() + ";DETATIME=" + System.currentTimeMillis() + ";HOST=" + host + ";USERAGENT=" + userAgent);
        String userTokenHash = SecurityUtil.generateShortUuid(userToken);
        log.debug("userToken:" + userToken);
        log.debug("userTokenHash:" + userTokenHash);
        shiroMemCacheManager.getCache(MemcacheKey.MEMCACHE_KEY_USER_HASH_TOKEN).put(userTokenHash, userToken);
        return userTokenHash;
    }


}
