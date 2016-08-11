package com.sf.sfpp.web.user.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 5/12/16.
 */
public class RedisSessionDao extends CachingSessionDAO {


    private SessionIdGenerator sessionIdGenerator;

    private RedisManager redisManager;

    private long redisSessionTimeOut = 60 * 10; //seconds

    private String activeSessionsCacheName = "redis_session_cacheName:";



    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = session.getId();
        if(sessionId == null){
            sessionId = sessionIdGenerator.generateId(session);
        }

        String key = activeSessionsCacheName +sessionId;
        redisManager.setObject(key,session,redisSessionTimeOut, TimeUnit.SECONDS);

        return sessionId;
    }


    @Override
    protected Session doReadSession(Serializable sessionId) {
        String key = activeSessionsCacheName +sessionId;

        Session session = redisManager.getObject(key,Session.class);
        return session;
    }


    @Override
    protected void doUpdate(Session session) {
        Serializable sessionId = session.getId();
        if(sessionId == null){
            sessionId = sessionIdGenerator.generateId(session);
        }

        String key = activeSessionsCacheName +sessionId;
        redisManager.setObject(key,session,redisSessionTimeOut, TimeUnit.SECONDS);

    }



    @Override
    protected void doDelete(Session session) {
            Serializable sessionId = session.getId();
            String key = activeSessionsCacheName +sessionId;
            redisManager.deleteObject(key);

    }



    public SessionIdGenerator getSessionIdGenerator() {
        return sessionIdGenerator;
    }

    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }


    public String getActiveSessionsCacheName() {
        return activeSessionsCacheName;
    }

    public void setActiveSessionsCacheName(String activeSessionsCacheName) {
        this.activeSessionsCacheName = activeSessionsCacheName;
    }

    public long getRedisSessionTimeOut() {
        return redisSessionTimeOut;
    }

    public void setRedisSessionTimeOut(long redisSessionTimeOut) {
        this.redisSessionTimeOut = redisSessionTimeOut;
    }
}
