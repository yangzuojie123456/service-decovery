package com.miapc.ipudong.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Created by wangwei on 2016/11/1.
 */
@ControllerAdvice(basePackages = "com.miapc.ipudong.rest")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    /**
     * Instantiates a new Jsonp advice.
     */
    public JsonpAdvice() {
        super("callback");
    }

}

