package com.miapc.ipudong.shiro;

import com.miapc.ipudong.domain.SessionEntity;
import com.miapc.ipudong.domain.User;
import com.miapc.ipudong.repository.SessionEntityRepository;
import com.miapc.ipudong.repository.UserRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangwei on 2016/11/7.
 */
@Component
public class MemcacheSessionDAO extends EnterpriseCacheSessionDAO {
    @Autowired
    private SessionEntityRepository sessionEntityRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Serializable create(Session session) {
        Serializable cookie = super.create(session);
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setCookie(cookie.toString());
        sessionEntity.setUserId((String) session.getAttribute("userId"));
        sessionEntity.setSession((SimpleSession) session);
        sessionEntityRepository.save(sessionEntity);
        return cookie;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        super.update(session);
        SessionEntity entity = getEntity(session.getId());
        if (entity != null) {
            entity.setSession((SimpleSession) session);
            sessionEntityRepository.save(entity);
        }
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session session = null;

        try {
            session = super.readSession(sessionId);
        } catch (Exception e) {

        }

        // 如果session已经被删除，则从数据库中查询session
        if (session == null) {
            SessionEntity entity = getEntity(session.getId());
            if (entity != null) {
                session = (Session) entity.getSession();
            }

        } else {
            // 如果session没有被删除，则判断session是否过期
            if (isExpire(session)) {
                // session过期
                User user = getUser(sessionId);
                if (user != null) {
                    // 如果该用户是APP用户（user不为空说明就是），则判断session是否过期，如果过期则修改最后访问时间
                    ((SimpleSession) session).setLastAccessTime(new Date());
                }
            }
        }
        return session;
    }

    private User getUser(Serializable sessionId) {
        SessionEntity entity = getEntity(sessionId);
        if (entity != null) {
            User user = userRepository.findOne(Long.valueOf(entity.getUserId()));
            return user;
        }
        return null;
    }

    @Override
    public void delete(Session session) {
        super.delete(session);
    }

    private SessionEntity getEntity(Serializable sessionId) {
        return sessionEntityRepository.findByCookie(sessionId.toString());
    }

    private boolean isExpire(Session session) {
        return false;
    }
}
