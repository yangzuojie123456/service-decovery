package com.miapc.ipudong.cache;

/**
 * Created by wangwei on 15/5/7.
 */
public class CacheKeyGenarator {

    /**
     * Genarate cache key.
     *
     * @param keys the keys
     * @return the string
     */
    public static String genarateCacheKey(Object... keys)
    {
        StringBuilder key=new StringBuilder();
        for(Object str:keys){
            key.append("_"+str);
        }
        return key.toString();
    }
}
