package com.miapc.ipudong.configuration;

import javax.servlet.DispatcherType;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.miapc.ipudong.cache.MemcachedCacheManager;
import com.miapc.ipudong.shiro.MemcacheSessionDAO;
import com.miapc.ipudong.shiro.ShiroJdbcRealm;
import com.miapc.ipudong.shiro.ShiroMemCacheManager;
import com.miapc.ipudong.shiro.ShiroMemCookie;

/**
 * Created by wangwei on 2016/10/30.
 */
@Configuration
@EnableConfigurationProperties
public class ShiroConfiguration {

    /**
     * Filter registration bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

    /**
     * Shiro filter shiro filter factory bean.
     *
     * @return shiro filter factory bean
     * @see org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
//        bean.setLoginUrl("login");
//        bean.setUnauthorizedUrl("/unauthor");

//        Map<String, Filter> filters = Maps.newHashMap();
//        filters.put("anon", new AnonymousFilter());
//        bean.setFilters(filters);
//
//        Map<String, String> chains = Maps.newHashMap();
//        chains.put("/login", "anon");
//        chains.put("/unauthor", "anon");
//        chains.put("/health", "anon");
//        chains.put("/profile/**", "anon");
//        chains.put("/logout", "logout");
//        chains.put("/base/**", "anon");
//        chains.put("/css/**", "anon");
//        chains.put("/layer/**", "anon");
//        //chains.put("/rest/**", "anon");
//        chains.put("/rest/**", "authc,perms");
//        bean.setFilterChainDefinitionMap(chains);
        return bean;
    }

    /**
     * Security manager default web security manager.
     *
     * @return the default web security manager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        final DefaultWebSecurityManager securityManager
                = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * Session manager default web session manager.
     *
     * @return the default web session manager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        final DefaultWebSessionManager sessionManager
                = new DefaultWebSessionManager();
        sessionManager.setCacheManager(shiroMemCacheManager());
        sessionManager.setGlobalSessionTimeout(3600000);
        sessionManager.setDeleteInvalidSessions(true);
        Cookie cookie = new ShiroMemCookie();
//        cookie.setDomain(domain);
        cookie.setName("SJSESSION");
        cookie.setMaxAge(3600);
        //cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        //sessionManager.setSessionDAO(sessionDao());
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDao() {
        return new MemcacheSessionDAO();
    }

    /**
     * Shiro mem cache manager cache manager.
     *
     * @return the cache manager
     */
    @Bean(name = "shiroMemCacheManager")
    public CacheManager shiroMemCacheManager() {
        return new ShiroMemCacheManager(memcachedCacheManager());
    }

    /**
     * Memcached cache manager memcached cache manager.
     *
     * @return the memcached cache manager
     */
    @Bean(name = "cacheManager")
    public MemcachedCacheManager memcachedCacheManager() {
        return new MemcachedCacheManager();
    }

    /**
     * Realm shiro jdbc realm.
     *
     * @return the shiro jdbc realm
     */
    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroJdbcRealm realm() {
        final ShiroJdbcRealm realm = new ShiroJdbcRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        realm.setCacheManager(shiroMemCacheManager());
        return realm;
    }

    /**
     * Credentials matcher password matcher.
     *
     * @return the password matcher
     */
    @Bean(name = "credentialsMatcher")
    public PasswordMatcher credentialsMatcher() {
        final PasswordMatcher credentialsMatcher = new PasswordMatcher();
        credentialsMatcher.setPasswordService(passwordService());
        return credentialsMatcher;
    }

    //    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        return new DefaultAdvisorAutoProxyCreator();
//    }
    @Bean
    @DependsOn("securityManager")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    /**
     * Password service default password service.
     *
     * @return the default password service
     */
    @Bean(name = "passwordService")
    public DefaultPasswordService passwordService() {
        return new DefaultPasswordService();
    }

    /**
     * Lifecycle bean post processor lifecycle bean post processor.
     *
     * @return the lifecycle bean post processor
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
