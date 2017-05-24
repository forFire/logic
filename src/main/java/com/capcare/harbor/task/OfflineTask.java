package com.capcare.harbor.task;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.DeviceDao;
import com.capcare.harbor.protocol.Position;
import com.capcare.harbor.service.cache.DeviceCache;
import com.capcare.harbor.service.cache.PositionCache;
import com.capcare.harbor.service.push.PushService;
import com.capcare.harbor.util.Helper;

@Component
public class OfflineTask{

	private static Logger logger = LoggerFactory.getLogger(OfflineTask.class);
	
	public static final Long offline_time_pet = Long.valueOf(Helper
			.get("offline_time_pet"));

	@Resource
	private PositionCache positionCache;
	@Resource
	private PushService pushService;
	@Resource
	private DeviceCache deviceCache;
	@Resource
	private DeviceDao deviceDao;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String POSITION = "position";

	public void doWork() {
		updatePosition();
	}

	public void updatePosition() {
//		Set<Object> device_sns = redisTemplate.opsForHash().keys(POSITION);
//		for (Object obj : device_sns) {
//			String deviceSn = obj.toString();
//			Position position = positionCache.getPosition(deviceSn);
//			long systime = position.getSystime();
//			long currTime = System.currentTimeMillis();
//			
//			if ((currTime - systime) > offline_time_pet) {
//				if(position.getStatus()==1){
//					position.setStatus(2);
//					position.setSpeed(0D);
//					positionCache.setPosition(deviceSn, position);
//					pushService.pushPosition(position);
//					logger.info("设备状态设为离线,sn=" + deviceSn);
//				}
//			}
//		}
	}
}
