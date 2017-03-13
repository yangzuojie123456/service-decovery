package com.miapc.ipudong.rest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangwei on 2016/10/14.
 */
@RestController
@EnableDiscoveryClient
@RequestMapping("rest")
public class DemoResource {
    /**
     * Sya hi string.
     *
     * @param name the name
     * @return the string
     */
    @RequestMapping(value = "/sayHi", method = RequestMethod.GET)
    @RequiresRoles("TEST")
    public String syaHi(@RequestParam String name) {
        return "Hello " + name;
    }

    /**
     * Sya bye string.
     *
     * @param name the name
     * @return the string
     */
    @RequestMapping(value = "/sayBye", method = RequestMethod.GET)
    @RequiresPermissions("SAY_BYE")
    public String syaBye(@RequestParam String name) {
        return "Bye " + name;
    }
}
