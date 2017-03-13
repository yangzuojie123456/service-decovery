package com.miapc.ipudong.repository;

import com.miapc.ipudong.domain.SessionEntity;
import com.miapc.ipudong.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Created by wangwei on 2016/10/29.
 */
@Repository
public interface SessionEntityRepository extends BaseRepository<SessionEntity, Long> {
   public SessionEntity findByCookie(String session);
}
