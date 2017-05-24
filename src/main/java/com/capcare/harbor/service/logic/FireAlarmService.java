package com.capcare.harbor.service.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.DeviceDao;
import com.capcare.harbor.dao.EmergencyAlarmDao;
import com.capcare.harbor.dao.FireAlarmDao;
import com.capcare.harbor.dao.OrgControlRoomDao;
import com.capcare.harbor.dao.OrgDao;
import com.capcare.harbor.dao.SpatialDao;
import com.capcare.harbor.dao.UserDao;
import com.capcare.harbor.model.Device;
import com.capcare.harbor.model.EmergencyAlarm;
import com.capcare.harbor.model.FireAlarmEntity;
import com.capcare.harbor.protocol.FireAlarm;
import com.capcare.harbor.service.cache.AlarmCache;

@Component
public class FireAlarmService {
	private static Logger logger = LoggerFactory.getLogger(FireAlarmService.class);
	@Resource
	PushAlarmToApp pushAlarmToApp;
	
	@Resource
	private FireAlarmDao fireAlarmDao;

	@Autowired
	AlarmCache alarmCache;

	@Resource
	private DeviceDao deviceDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	OrgControlRoomDao orgControlRoomDao;
	
	@Autowired
	OrgDao orgDao;
	
	@Resource
	private SpatialDao spatialDaoImpl;
	
	@Resource
	private EmergencyAlarmDao emergencyAlarmDao;
	
	/**
	 * 设备上传告警
	 */
	public void saveAlarm(FireAlarm fireAlarm) {
		
		
		logger.info("设备上传告警=======================" + fireAlarm.getSn());
		
		
		Device device = deviceDao.getDeviceBySn(fireAlarm.getSn());

		if ("1002".equalsIgnoreCase(fireAlarm.getType())) {

			logger.info("故障恢复=======================" + fireAlarm.getType());
			fireAlarmDao.updateStatus(convert(fireAlarm));

			// 室内故障和报警编码
		} else if ("in001".equalsIgnoreCase(fireAlarm.getType()) || "in002".equalsIgnoreCase(fireAlarm.getType())) {
			logger.info("sn=======================" + fireAlarm.getSn());
			String str = alarmCache.getAlarmTime(fireAlarm.getSn());

			if (str == null || str == "" || str.length() == 0) {
				alarmCache.setAlarmTime(fireAlarm.getSn(), String.valueOf(fireAlarm.getAlarmTime()));
			} else {
				alarmCache.setAlarmTime(fireAlarm.getSn(), fireAlarm.getAlarmTime());
				long l = DateUtil.timeDiff(fireAlarm.getAlarmTime(), str);
				
				if(l <300){
					 logger.info("时间差====================================================>"+l);
				      return ;
				}
			}

			// 经纬度不为空 设备已绑定
			if (device != null && device.getLat() != null && device.getLng() != null) {
				
				FireAlarmEntity alarm = convert(fireAlarm);
				Integer l = (Integer) fireAlarmDao.savePK(alarm);
				
				//缓冲区半径，判断是否需要记性缓冲区分析
				Double radius = device.getRadius();
				if(radius!=null&&radius>0){
					/**
					 * 生成新的预警
					 * modify by wangxinx
					 * 2017-03-03
					 */
					List<Map<String,Object>> alarmList = spatialDaoImpl.queryBuffAlarmDevice(device,alarm);
					List<Map<String,Object>> pointList = spatialDaoImpl.queryBuffDevice(device);
					
					//int alarmCount = spatialDaoImpl.queryBuffByDeviceId(device,true);
					//int pointCount = spatialDaoImpl.queryBuffByDeviceId(device,false);
					
					int alarmCount = alarmList.size();
					int pointCount = pointList.size();
					
					//至少查出1个报警点
					if(alarmCount>1){
						if((alarmCount-1)/(pointCount*1.0-1)>=0.5){
							//触发报警
							String snStr = "";
							Map<String,Object> result = null;
							for(int i=0;i<alarmList.size();i++){
								result = alarmList.get(i);
								if(!result.get("sn").toString().equals(device.getSn())){
									snStr += ",";
									snStr += result.get("sn").toString();
								}
							}
	
							if(snStr.length()>0){
								snStr = snStr.substring(1, snStr.length());
							}
							
							EmergencyAlarm emergencyAlarm = new EmergencyAlarm();
							
							emergencyAlarm.setCascadeSn(snStr);
							emergencyAlarm.setSn(device.getSn());
							emergencyAlarm.setStatus(0);
							
							emergencyAlarmDao.save(emergencyAlarm);
							
							//添加缓存
							alarmCache.incrementInSuspectAlarm(device.getLayer(), device.getFloorId(), device.getRoomCode(), device.getOrgId(), 1);
							//推送消息
							pushAlarmToApp.pushAlarm(device, emergencyAlarm.getCascadeSn(), 0);
						}
					}
				}
				
				logger.info("保存开始=========>设备号==" + device.getSn() + "楼层==" + device.getLayer() + "楼宇==" + device.getFloorId() + "" + device.getRoomCode() + "" + device.getOrgId());
				if (l.intValue() > 0) {
					alarmCache.incrementInAlarm(device.getLayer(), device.getFloorId(), device.getRoomCode(), device.getOrgId(), 1);
					/**
					 * 推送用户 1设备中控室部门 2 企业下领导
					 * 0室内 1室外
					 * 室外没有中控室 只需要发给企业用户
					 */
					pushAlarmToApp.pushAlarm(device,0);
					
				}
				if (l == 0) {
					logger.info("保存到数据库失败=====" + fireAlarm.getSn() + "====" + l + fireAlarm.getType());
				}
			}

		} else {
			logger.info("其他=======================" + fireAlarm.getType());
		}
	}

	private FireAlarmEntity convert(FireAlarm alarm) {
		logger.info("alarm====" + alarm);
		FireAlarmEntity fireAlarm = new FireAlarmEntity();
		fireAlarm.setSn(alarm.getSn());
		logger.info("alarm.getSn()====" + alarm.getSn());
		// 入库时间
		// fireAlarm.setCreateTime(new Timestamp(System.currentTimeMillis()));
		// 报警时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			fireAlarm.setCreateTime(sdf.parse(sdf.format(date)));
			
			date = sdf.parse(alarm.getAlarmTime());
			fireAlarm.setAlarmTime(date);
		} catch (ParseException e) {
			logger.info("报警时间转换失败====");
			e.printStackTrace();
		}
		fireAlarm.setStatusPerson(0);
		fireAlarm.setStatusSystem(0);
		fireAlarm.setStatus(0);
		fireAlarm.setType(alarm.getType());
		
		
		
		return fireAlarm;
	}


}
