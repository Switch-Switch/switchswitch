package com.rljj.switchswitchcommon.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class RedisRepositoryImpl implements RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    public RedisRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void set(String key, String value) {
        valueOperations.set(key, value);
    }

    @Override
    public void setWithExpire(String key, String value, long expire, TimeUnit timeUnit) {
        valueOperations.set(key, value);
        redisTemplate.expire(key, expire, timeUnit);
    }

    @Override
    public String get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void delete(String key) {
        valueOperations.getAndDelete(key);
    }
}
