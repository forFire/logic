package com.capcare.harbor.service.cache;

import module.util.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.Device;

@Component
public class DeviceCache {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String ONLINE_DEVICE = "online_device";
	
	private static final String DEVICE = "device";
	
	private static final String DEVICE_SLEEP = "device_sleep";
	
	private static final String WATER_PRESS = "water_press";
		
	
	public void setDeviceSleep(String deviceSn, long status) {
		redisTemplate.opsForHash().put(DEVICE_SLEEP, deviceSn, String.valueOf(status));
	}
	
	public long getDeviceSleep(String deviceSn) {
		Object obj = redisTemplate.opsForHash().get(DEVICE_SLEEP, deviceSn);
		if(obj != null){
			return Long.valueOf((String)obj);
		}
		return 0;
	}
	
	public void addOnlineDevice(String deviceSn) {
		redisTemplate.opsForSet().add(ONLINE_DEVICE, deviceSn);
	}
	
	public void removeOnlineDevice(String deviceSn) {
		redisTemplate.opsForSet().remove(ONLINE_DEVICE, deviceSn);
	}
	
	public void setDevice(String deviceSn, Device device) {
		String json = JsonUtils.obj2Str(device);
		redisTemplate.opsForHash().put(DEVICE, deviceSn, json);
	}
	
	public Device getDevice(String deviceSn) {
		String json = (String)redisTemplate.opsForHash().get(DEVICE, deviceSn);
		if(json != null && !"".equals(json)){
			return JsonUtils.str2Obj(json, Device.class);
		}
		return null;
	}
	
	public void setWaterPress(String sn, String value){
		redisTemplate.opsForHash().put(WATER_PRESS, sn, value);
	}
}
