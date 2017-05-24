package com.capcare.harbor.service.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.AlarmDao;
import com.capcare.harbor.dao.DeviceDao;
import com.capcare.harbor.model.Device;
import com.capcare.harbor.model.FireAlarmEntity;
import com.capcare.harbor.protocol.Alarm;
import com.capcare.harbor.protocol.AlarmType;
import com.capcare.harbor.service.cache.AlarmCache;
import com.capcare.harbor.service.cache.PositionCache;
import com.capcare.harbor.service.push.PushService;

@Component
public class AlarmService {
	private static Logger logger = LoggerFactory.getLogger(AlarmService.class);
	@Resource
	private PushService pushService;
	@Resource
	PushAlarmToApp pushAlarmToApp;

	@Resource
	private PositionCache positionCache;

	@Resource
	private DeviceDao deviceDao;

	@Resource
	private AlarmDao alarmDao;

	@Autowired
	AlarmCache alarmCache;
	
	@Resource
	private PositionService positionService;

//	DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 设备上传告警
	 * @throws ParseException 
	 */
	public void saveAlarm(Alarm alarm) throws ParseException {
		logger.info("==========================11111111111=======");
//		HttpUtil.validateNull(new String[] { "device_sn" }, new String[] { alarm.getDeviceSn() });

		//Alarm lastAlarm = positionCache.getLastAlarm(alarm.getDeviceSn());

		// 如果和上一个告警是同一类型， 且间隔小于3分钟， 则不处理
		/*if (lastAlarm != null && isSameType(lastAlarm, alarm) && alarm.getType().intValue() != AlarmType.SOS.getNum()) {
			logger.info("alarm is same type:" + alarm.toString());
			return;
		}*/
		//Device device = deviceDao.getDeviceBySn (alarm.getDeviceSn ());
		//int deviceType = Integer.parseInt(device.getType ());
		/*if (!SupportedAlarm.isSupportAlarm(deviceType, alarm.getType())) {
			logger.info("alarm is not supported;alarm:" + alarm.toString() + "; deviceType:"
					+ deviceType);
			return;
		}*/
				
		// 经纬度 转换为 地址
/*		if (alarm.getLat() == 0 && alarm.getLng() == 0) {
			if(deviceType == 1) return;
			Position pos = positionCache.getPosition(alarm.getDeviceSn());
			if (Calendar.getInstance().getTimeInMillis() - pos.getReceive() < mills) {
				alarm.setLng(pos.getLng());
				alarm.setLat(pos.getLat());
			}
		}*/
		//String addr = GeoMapUtil.getAddrByBaidu(alarm.getLng(), alarm.getLat());
		//alarm.setAddr(addr);
//		alarm.setAddr_en(GeoMapUtil.getAddrByGoogle(alarm.getLng(), alarm.getLat()));

		
		save(alarm);

	}

	private long save(Alarm alarm) throws ParseException {

		Device device =	deviceDao.getDeviceBySn(alarm.getDeviceSn());
		
		
		String deviceSn = alarm.getDeviceSn();
		
		positionCache.setLastAlarm(deviceSn, alarm);
		
		logger.info("============alarmDao.save(convert(alarm))===================="+device);
		
		FireAlarmEntity fireAlarmEntity = convert(alarm);
		
		int alarmId = alarmDao.save(fireAlarmEntity);
		
		logger.info("============device.getOrgId()====================="+device.getOrgId());
		
		if(alarmId > 0){
			 if(device != null){
				 alarmCache.incrementOutAlarm(String.valueOf(device.getOrgId()), 1);
			 }
		}
		
		positionCache.addAlarmId(deviceSn, String.valueOf(alarmId));
		
		/**
		 * 推送用户 1设备中控室部门 2 企业下领导
		 * 0室内 1室外
         * 室外没有中控室 只需要发给企业用户
		 */
		pushAlarmToApp.pushAlarm(device,1);
		
		return alarmId;
	}

	private FireAlarmEntity convert(Alarm alarm) throws ParseException {
		
		FireAlarmEntity entity = new FireAlarmEntity();
		entity.setType(AlarmType.getByNum(alarm.getType()).getEninfo());
		entity.setStatus(0);
		entity.setStatusSystem(1);
		entity.setStatusPerson(0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(alarm.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		entity.setAlarmTime(dateFormater.parse(sdf.format(date)));
		entity.setCreateTime(dateFormater.parse(sdf.format(date)));
		
		entity.setSn(alarm.getDeviceSn());
		
		logger.info("============entity1===================="+entity.getSn()+entity.getAlarmTime()+entity.getCreateTime());
		
		return entity;
	}

	public static void main(String[] args) {
		System.out.println(AlarmType.getByNum(2).getEninfo());
	}
	
	
	
	
	
}
