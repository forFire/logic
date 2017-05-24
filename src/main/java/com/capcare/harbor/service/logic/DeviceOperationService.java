package com.capcare.harbor.service.logic;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.capcare.harbor.dao.DeviceDao;
import com.capcare.harbor.dao.FireAlarmDao;
import com.capcare.harbor.dao.OrgControlRoomDao;
import com.capcare.harbor.dao.OrgDao;
import com.capcare.harbor.dao.UserDao;
import com.capcare.harbor.model.Device;
import com.capcare.harbor.model.FireAlarmEntity;
import com.capcare.harbor.protocol.DeviceOperation;
import com.capcare.harbor.service.cache.AlarmCache;
/**
 * 系统复位
 * 根据源地址查设备 对设备复位
 */	
@Component
public class DeviceOperationService {
	private static Logger logger = LoggerFactory.getLogger(DeviceOperationService.class);
	@Resource
	PushAlarmToApp pushAlarmToApp;
	
	@Autowired
	AlarmCache alarmCache;

	@Resource
	private DeviceDao deviceDao;
	
	@Resource
	private FireAlarmDao fireAlarmDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	OrgDao orgDao;
	
	
	@Autowired
	OrgControlRoomDao orgControlRoomDao;

	
	/**
	 * 对某种系统类型设备复位 只有室内室外没有复位
	 */
	public void updateAlarmFire(DeviceOperation deviceOperation) {
		
		//判断是复位标志
		if("0".equalsIgnoreCase(deviceOperation.getOperationFlag())){
			logger.info("对"+deviceOperation.getSysType()+"设备系统类型复位操作=======================源地址==="+deviceOperation.getPrimaryAddress());
			List<FireAlarmEntity> list = fireAlarmDao.findBySystemType(deviceOperation);
			for(FireAlarmEntity f : list){
				logger.info("设备编码======================="+f.getSn());
				Device device =	deviceDao.getDeviceBySn(f.getSn());
				
				//系统复位 ---只对室内报警复位
				/*
				 * update 20161013 
				 * web端添加全部复位功能 status 更新成1
				 * 所以这边需要加f.getStatus() != 1判断，如果为1不需要进行任何处理
				 **/
				if(device.getFlag() == 0 && f.getStatus() != 1){
					f.setStatusSystem(1);
					if (f.getStatusPerson() == 1) {
						f.setStatus(1);
						//报警总数减一
						alarmCache.incrementInAlarm(device.getLayer(), device.getFloorId(), device.getRoomCode(), device.getOrgId(), -1);
						 //人工修改报警减一保持手机端报警数不变==报警总数-人工处理数
						alarmCache.manDealInAlarm(device.getLayer(), device.getFloorId(), device.getRoomCode(), device.getOrgId(), -1);
					}
				    			
					fireAlarmDao.update(f);
			   }
			}
		}
	}
	
	
}
