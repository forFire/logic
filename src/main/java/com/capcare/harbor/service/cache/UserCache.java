package com.capcare.harbor.service.cache;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserCache {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String USER_PHONE = "phone";
	private static final String USER_LANGUAGE = "user_language";
	private static final String USER_PHONE_MQ = "phone_mq";
	private static final String BACKEND = "backend";
	private static final String IOS_TOKEN_MAPPING = "ios_token_mapping";
	private static final String USER_DEVICE_TOKEN = "user_device_token";
	private static final String DEVICE_TOKEN_USER = "device_token_user";
	
	
	public Set<Object> getOnlinePhone(String userId, String appName){
		return redisTemplate.opsForSet().members(USER_PHONE+":"+userId+":"+appName);		
	}
	
	public String getUserLanguage(String userId){
		return (String)redisTemplate.opsForHash().get(USER_LANGUAGE, userId);
	}
	
	public void setUserLanguage(String userId, String language){
		redisTemplate.opsForHash().put(USER_LANGUAGE, userId, language);
	}
	
	public Set<Object> getBackendPhone(String userId, String appName){
		return redisTemplate.opsForSet().members(BACKEND+":"+userId+":"+appName);
	}
	
	public String getPhoneMq(String userId, String appName, String duid){
		return (String)redisTemplate.opsForHash().get(USER_PHONE_MQ, userId+":"+appName+":"+duid);	
	}
	
	public String getIosTokenMapping(String duid){
		return (String)redisTemplate.opsForHash().get(IOS_TOKEN_MAPPING, duid);	
	}
	
	public void setDeviceToken(String userId, String deviceToken){
		redisTemplate.opsForHash().put(USER_DEVICE_TOKEN, userId, deviceToken);
		redisTemplate.opsForHash().put(DEVICE_TOKEN_USER, deviceToken, userId);
	}
	
	public String getDeviceToken(String userId){
		return (String)redisTemplate.opsForHash().get(USER_DEVICE_TOKEN, userId);
	}
	
	public String getUserByDeviceToken(String deviceToken){
		return (String)redisTemplate.opsForHash().get(DEVICE_TOKEN_USER, deviceToken);
	}
}
