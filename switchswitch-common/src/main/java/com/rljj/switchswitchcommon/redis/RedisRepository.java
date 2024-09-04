package com.rljj.switchswitchcommon.redis;

import java.util.concurrent.TimeUnit;

public interface RedisRepository {
    void set(String key, String value);
    void setWithExpire(String key, String value, long expire, TimeUnit timeUnit);
    String get(String key);
    void delete(String key);
}
