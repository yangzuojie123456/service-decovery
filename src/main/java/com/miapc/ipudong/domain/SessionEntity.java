package com.miapc.ipudong.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wangwei on 2016/11/7.
 */
@Entity
@Table(name = "session_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Where(clause = "deleted = 0")
public class SessionEntity extends BaseBean {
    private String cookie;
    private String userId;
    private Serializable session;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Serializable getSession() {
        return session;
    }

    public void setSession(Serializable session) {
        this.session = session;
    }
}
