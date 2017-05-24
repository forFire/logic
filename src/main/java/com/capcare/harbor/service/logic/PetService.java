/**
 * 
 */
package com.capcare.harbor.service.logic;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.AlarmDao;
import com.capcare.harbor.dao.PetSportDao;
import com.capcare.harbor.model.AlarmEntity;
import com.capcare.harbor.model.Device;
import com.capcare.harbor.model.Fence;
import com.capcare.harbor.model.PetFence;
import com.capcare.harbor.model.PetSport;
import com.capcare.harbor.model.SpotVo;
import com.capcare.harbor.protocol.Alarm;
import com.capcare.harbor.protocol.AlarmType;
import com.capcare.harbor.protocol.Position;
import com.capcare.harbor.service.cache.PetFenceCache;
import com.capcare.harbor.service.cache.PositionCache;
import com.capcare.harbor.service.push.PushService;
import com.capcare.harbor.util.AlarmUtil;
import com.capcare.harbor.util.GeoMapUtil;

@Component
public class PetService {
	private static Logger logger = LoggerFactory.getLogger(PetService.class);

	private static Map<Integer, AlarmType> map = new HashMap<Integer, AlarmType>();
	@Resource
	private PushService pushService;
	@Resource
	private PetSportDao petSportDao;
	@Resource
	private PetFenceCache petFenceCache;
	@Resource
	private AlarmDao alarmDao;
	@Resource
	private PositionCache positionCache;

	public void handlePet(Position position, Position lastPosition, Device device) {
//		// 处理设备时间出错的问题，阈值10分钟
//		if (Math.abs((position.getReceive() - System.currentTimeMillis())) > 600000
//				&& lastPosition != null && position.getSteps() > lastPosition.getSteps())
//			position.setReceive(position.getSystime());
		// 保存运动量
		/*petSportDao.save(convertToPetSport(position));
		// 判断是否产生围栏报警
		SpotVo vo = new SpotVo();
		vo.setLng(position.getLng());
		vo.setLat(position.getLat());
		PetFence petFence = petFenceCache.getPetFence(position.getDeviceSn());
		if (petFence == null) {
			petFence = new PetFence();
			petFence.setFenceSwitch(device.getFenceSwitch());
			petFenceCache.setPetFence(position.getDeviceSn(), petFence);
		}
		Fence fence = device.getFence();
		if (fence == null || device.getFenceSwitch() == 2)
			return;
		Integer out = fence.getOut();
		Fence buffer = AlarmUtil.getBufferFence(fence);
		AlarmType alarmType = AlarmType.BNDOUT;
		Integer current = 2;
		Integer status = petFence.getStatus();
		if (AlarmUtil.isInFence(vo, buffer)) {
			alarmType = AlarmType.BNDIN;
			current = 1;
		}
		if (!status.equals(current) && map.get(out).equals(alarmType)) {
			status = current;
			Alarm alarm = new Alarm();
			alarm.setDeviceSn(position.getDeviceSn());
			alarm.setLat(position.getLat());
			alarm.setLng(position.getLng());
			alarm.setRead(2);
			alarm.setSpeed(position.getSpeed());
			alarm.setTime(position.getReceive());
			alarm.setSystime(position.getSystime());
			// 经纬度 转换为 百度地图地址
			String addr = GeoMapUtil.getAddrByBaidu(alarm.getLng(), alarm.getLat());
			alarm.setAddr(addr);
			alarm.setType(alarmType.getNum());
			alarm.setInfo(alarmType.getInfo());
			alarm.setAccMode(position.getAccMode());
			alarm.setMode(position.getMode());
			alarm.setBattery(position.getBattery());
			alarm.setMode433(position.getMode433());
			alarm.setCell(position.getCell());
			positionCache.setLastAlarm(device.getSn(), alarm);
			long alarmId = alarmDao.save(convert(alarm));
			positionCache.addAlarmId(device.getSn(), String.valueOf(alarmId));
			if (alarmId > 0) {
				// 发送告警信息 到推送中心
				alarm.setId(alarmId);
				String appName = device.getAppName();
				pushService.pushAlarm(alarm, appName);
			} else {
				logger.error("no alarmId:" + alarm.toString());
			}
		}
		petFence.setStatus(current);
		petFenceCache.setPetFence(position.getDeviceSn(), petFence);*/
	}

	private PetSport convertToPetSport(Position position) {
		PetSport petSport = new PetSport();
		petSport.setDeviceSn(position.getDeviceSn());
		petSport.setReceive(position.getReceive());
		petSport.setSpeed(position.getSpeed());
		petSport.setSysTime(position.getSystime());
		petSport.setSteps(position.getSteps());
		return petSport;
	}

	private AlarmEntity convert(Alarm alarm) {
		AlarmEntity entity = new AlarmEntity();
		entity.setType(alarm.getType());
		entity.setTime(alarm.getTime());
		entity.setCreateTime(alarm.getTime());
		entity.setSn(alarm.getDeviceSn());
		return entity;
	}
}
