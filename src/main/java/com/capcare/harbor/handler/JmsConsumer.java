package com.capcare.harbor.handler;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import module.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.DictEquipment;
import com.capcare.harbor.model.ShortMessage;
import com.capcare.harbor.protocol.Alarm;
import com.capcare.harbor.protocol.Conductor_GPS;
import com.capcare.harbor.protocol.Conductor_Status;
import com.capcare.harbor.protocol.Conductor_UserData;
import com.capcare.harbor.protocol.DeviceOperation;
import com.capcare.harbor.protocol.EquipmentTime;
import com.capcare.harbor.protocol.FireAlarm;
import com.capcare.harbor.protocol.Position;
import com.capcare.harbor.service.logic.AlarmService;
import com.capcare.harbor.service.logic.DeviceOperationService;
import com.capcare.harbor.service.logic.EquipmentTimeService;
import com.capcare.harbor.service.logic.FireAlarmService;
import com.capcare.harbor.service.logic.GPSService;
import com.capcare.harbor.service.logic.PositionService;
import com.capcare.harbor.service.logic.ShortMessageService;
import com.capcare.harbor.service.logic.StatusService;
import com.capcare.harbor.service.logic.UserDataService;

/**
 * @author fss
 */
@Component
public class JmsConsumer implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

	
	@Resource
	private AlarmService alarmService;
	
	@Resource
	private PositionService positionService;
	
	@Resource
	private UserDataService userDataService;

	@Resource
	private ShortMessageService sMessageService;
	
	@Resource
	private GPSService GPSService;
	
	@Resource
	private StatusService statusService;
	
	@Resource
	private FireAlarmService fireAlarmService;
	
	@Resource
	private  EquipmentTimeService equipmentTimeService;
	
	
	@Resource
	private DeviceOperationService deviceOperationService;
	
	
	@SuppressWarnings("static-access")
	@Override
	public void onMessage(Message message) {
		
		logger.info("message==================="+message);
		
		if (message instanceof ObjectMessage) {
			ObjectMessage msg = (ObjectMessage) message;
			try {
				Object obj = msg.getObject();
				logger.info("obj==================="+obj);
				if(obj instanceof Position){
					Position position = (Position)obj;
					logger.info("MQ_Consumer:" + position);
					positionService.savePosition(position);
				}else if(obj instanceof Alarm){
					logger.info("message1==================="+message);
					Alarm alarm = (Alarm)obj;
					logger.info("MQ_Consumer:" + alarm);
					alarmService.saveAlarm(alarm);
				}else if (obj instanceof ShortMessage) {
//					ShortMessage receiveShortMessage = (ShortMessage) obj;
//					sMessageService.save(receiveShortMessage);
				}else if(obj instanceof Conductor_UserData){
//					Conductor_UserData userData = (Conductor_UserData)obj;
//					logger.info("send userData to logic success!");
//					logger.info("MQ_Consumer:" + userData);
//					userDataService.saveUserData(userData);
				}else if(obj instanceof Conductor_GPS){
//					Conductor_GPS gps = (Conductor_GPS)obj;
//					logger.info("MQ_Consumer:" + gps);
//					GPSService.saveGPS(gps);
				}else if(obj instanceof Conductor_Status){
//					Conductor_Status status = (Conductor_Status)obj;
//					logger.info("MQ_Consumer:", status);
//					statusService.saveStatus(status);
				}	
				// 2建筑消防设施部件运行状态 40字节---火警和故障  故障恢复
				else if(obj instanceof FireAlarm){
					FireAlarm fireAlarm = (FireAlarm)obj;
					logger.info("设备编码:", fireAlarm.getSn()+fireAlarm.getCreateTime());
					fireAlarmService.saveAlarm(fireAlarm);
				}	
				// 建筑消防设施操作信息 复位
				else if(obj instanceof DeviceOperation){
					DeviceOperation deviceOperation = (DeviceOperation)obj;
					logger.info("系统类型:"+ deviceOperation.getSysType()+"操作类型:"+deviceOperation.getOperationFlag());
					//复位操作 某种类型的设备复位
					deviceOperationService.updateAlarmFire(deviceOperation);
				}else if(obj instanceof EquipmentTime){
					
					EquipmentTime equipmentTime = (EquipmentTime)obj;
					logger.info("心跳信息:"+ equipmentTime.getEquipmentCode()+equipmentTime.getTime());
					DictEquipment dictEquipment = new DictEquipment();
					
					if(equipmentTime.getEquipmentCode() !=null && equipmentTime.getTime() != null){
						dictEquipment.setCode(equipmentTime.getEquipmentCode());
						DateUtil d = new DateUtil();
						dictEquipment.setLastTime(d.strDate(equipmentTime.getTime(),"yyyyMMddHHmmss"));
						equipmentTimeService.updateEquipmentTime(dictEquipment);
					}
					
				}else{
					logger.info("其他情况===");
				}	
				
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
}
