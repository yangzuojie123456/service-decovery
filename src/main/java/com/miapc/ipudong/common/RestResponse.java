package com.miapc.ipudong.common;

import com.miapc.ipudong.constant.DatePatten;
import com.miapc.ipudong.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangwei on 2016/10/28.
 *
 * @param <T> the type parameter
 */
public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = -3487283912072468917L;

    private Object errorMsg = ""; //错误信息

    private int code = 0;        //0-正常

    private T data = null;      //返回数据

    private Integer totle = 0;        //返回数据总记录数

    private String time = DateUtil.getDate(new Date(), DatePatten.YYYY_MM_DD_HH_MM);        //返回数据时间

    /**
     * Instantiates a new Rest response.
     */
    public RestResponse() {
    }

    /**
     * Instantiates a new Rest response.
     *
     * @param code the code
     * @param data the data
     */
    public RestResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    /**
     * Instantiates a new Rest response.
     *
     * @param code     the code
     * @param data     the data
     * @param errorMsg the error msg
     */
    public RestResponse(int code, T data, String errorMsg) {
        this.code = code;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Gets error msg.
     *
     * @return the error msg
     */
    public Object getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets error msg.
     *
     * @param errorMsg the error msg
     */
    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Gets totle.
     *
     * @return the totle
     */
    public Integer getTotle() {
        return totle;
    }

    /**
     * Sets totle.
     *
     * @param totle the totle
     */
    public void setTotle(Integer totle) {
        this.totle = totle;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }
}

