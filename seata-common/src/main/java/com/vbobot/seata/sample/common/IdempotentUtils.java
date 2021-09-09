package com.vbobot.seata.sample.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

/**
 * @author Bobo
 * @date 2021/9/9
 */
public class IdempotentUtils {

    static final Cache<String, Integer> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public static boolean handled(String xid) {
        final Integer ifPresent = cache.getIfPresent(xid);
        if (ifPresent != null) {
            return true;
        }

        cache.put(xid, 1);
        return false;
    }
}
