package com.miapc.ipudong.cache;

/**
 * The Interface MemcacheKey.
 */
public interface MemcacheKey {
    /*  记录用户token short hash*/
    public static final String MEMCACHE_KEY_USER_TOKEN = "memcache_key_user_token";
    /*  记录用户token short hash与token值*/
    public static final String MEMCACHE_KEY_USER_HASH_TOKEN = "memcache_key_user_hash_token";
}
