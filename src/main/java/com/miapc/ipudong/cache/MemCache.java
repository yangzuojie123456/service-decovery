package com.miapc.ipudong.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * Created by wangwei on 15/5/6.
 */
public class MemCache {
    
	/** The log. */
    private static Logger log = LoggerFactory.getLogger(MemCache.class);

    /**
     * the KEY_SET_NAME
     */
    private final static String KEY_SET_NAME = "keySet"; 
    
    /** The name. */
    private final String name;
    
    /** The expire. */
    private final int expire;
    
    /** The memcached client. */
    private final MemcachedClient memcachedClient;

    /**
     * Instantiates a new mem cache.
     *
     * @param name            the name
     * @param expire          the expire
     * @param memcachedClient the memcached client
     */
    public MemCache(String name, int expire, MemcachedClient memcachedClient) {
        this.name = name;
        this.expire = expire;
        this.memcachedClient = memcachedClient;
    }

    /**
     * Gets the.
     *
     * @param key the key
     * @return the object
     */
    public Object get(String key) {
        Object value = null;
        try {
        	key = this.getKey(key);
        	if(getKeySet().contains(key)){
        		value = memcachedClient.get(key);
        	}
        } catch (TimeoutException e) {
            log.warn("获取 Memcached 缓存超时", e);
        } catch (InterruptedException e) {
            log.warn("获取 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("获取 Memcached 缓存错误", e);
        }
        return value;
    }

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, Object value) {
        if (value == null)
            return;
        try {
            key = this.getKey(key);
            memcachedClient.setWithNoReply(key, expire, value);
            saveKeyToKeySet(key);
        } catch (InterruptedException e) {
            log.warn("更新 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("更新 Memcached 缓存错误", e);
        }
    }

    /**
     * Clear.
     */
    public void clear() {
        for (String key : getKeySet()) {
            try {
                memcachedClient.deleteWithNoReply(key);
                getKeySet().clear();
            } catch (InterruptedException e) {
                log.warn("删除 Memcached 缓存被中断", e);
            } catch (MemcachedException e) {
                log.warn("删除 Memcached 缓存错误", e);
            }
        }
    }

    /**
     * Delete.
     *
     * @param key the key
     */
    public void delete(String key) {
        try {
        	key = this.getKey(key);
        	if(getKeySet().contains(key)){
	            memcachedClient.deleteWithNoReply(key);
        	}
        } catch (InterruptedException e) {
            log.warn("删除 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("删除 Memcached 缓存错误", e);
        }
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    public Object[] getValues() {
        List result = new ArrayList();
        for (String key : getKeySet()) {
            result.add(this.get(key));
        }
        return result.toArray();
    }

    /**
     * <p>保存key到keyset</p>
     *
     * @param key the key
     * @author xiao.miao
     * @Date 2016年3月24日 下午5:35:30
     */
    public void saveKeyToKeySet(String key) {
		Set<String> keySet = getKeySet();
		keySet.add(key);
		try {
			memcachedClient.setWithNoReply(name + KEY_SET_NAME , 86400, keySet);
		} catch (InterruptedException e) {
            log.warn("更新 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("更新 Memcached 缓存错误", e);
        }
	}

    /**
     * Gets the keySet.
     *
     * @return the keySet
     */
    public Set<String> getKeySet() {
		Set<String> keySet = new HashSet<String>();
		try {
            Set<String>	memkeySet =  memcachedClient.get(name + KEY_SET_NAME);
            if(memkeySet!=null){
                return memkeySet;
            }
        } catch (TimeoutException e) {
            log.warn("获取 Memcached 缓存超时{}", e);
        } catch (InterruptedException e) {
            log.warn("获取 Memcached 缓存被中断{}", e);
        } catch (MemcachedException e) {
            log.warn("获取 Memcached 缓存错误{}", e);
        }
		return keySet;
	}
	
    /**
     * Gets the key.
     *
     * @param key the key
     * @return the key
     */
    private String getKey(String key) {
        return name + "_" + key;
    }

    /**
     * Size.
     *
     * @return the int
     */
    public int size() {
        return getKeySet().size();
    }
	
}

