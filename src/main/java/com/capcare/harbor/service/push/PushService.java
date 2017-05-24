package com.capcare.harbor.service.push;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.DeviceDao;
import com.capcare.harbor.dao.UserDeviceDao;
import com.capcare.harbor.model.Device;
import com.capcare.harbor.protocol.Alarm;
import com.capcare.harbor.protocol.Position;
import com.capcare.harbor.protocol.PushMsg;
import com.capcare.harbor.protocol.SmsAlarm;
import com.capcare.harbor.service.cache.DeviceCache;
import com.capcare.harbor.service.cache.IosTokenCache;
import com.capcare.harbor.service.cache.UserCache;
import com.capcare.harbor.service.push.ios.ApnsServiceUtil;

/**
 * @author fyq
 */
@Component
public class PushService {
	private static Logger logger = LoggerFactory.getLogger(PushService.class);

	@Resource
	private UserDeviceDao userDeviceDao;
	
	@Resource
	private DeviceDao deviceDao;
	
	@Resource
	private DeviceCache deviceCache;

	@Resource
	private UserCache userCache;
	
	@Resource
	private IosTokenCache iosTokenCache;

	@Resource
	private JmsTemplate jmsTemplate;

	public void pushAlarm(Alarm entity, String appName) {
		String method = "[pushAlarm]";
		if(appName == null){
			appName = "M2616_BD";
		}
		
		//取绑定该设备的用户
		List<Long> userIds = userDeviceDao.findUserIdBySn(entity.getDeviceSn());
		logger.info(method+"[userIds_bind_to_device]sn="+entity.getDeviceSn()+",userIds="+userIds+",appName="+appName);
		for (Long userId : userIds) {
			//根据userId+appName，取在线的duid
			Set<Object> duidSet = userCache.getOnlinePhone(String.valueOf(userId), appName);
			logger.info(method+"[online_phone_of_user]sn="+entity.getDeviceSn()+",userId="+userId+",duidSet="+duidSet);
			
			Set<String> duidTokenSet = new HashSet<String>();
			if(duidSet != null && duidSet.size()>0){
				Set<String> mqSet = new HashSet<String>();
				for (Object duid : duidSet) {
					//根据userId+appName+duid， 取对应的mq
					String mq = userCache.getPhoneMq(String.valueOf(userId), appName, (String)duid);
					//logger.error("find mq:userId="+userId+",appName="+appName+",duid"+duid+",mq:"+mq+", alarm:"+entity);
					mqSet.add(mq);		
					
					String ios_token = userCache.getIosTokenMapping((String)duid);
					if(ios_token != null && !"".equals(ios_token)){
						duidTokenSet.add(ios_token);
					}
				}
				if(mqSet != null && mqSet.size()>0){
					for(String mq : mqSet){
						sendMq(MsgBuilder.buildFromAlarm(entity, userId, appName), mq);
					}
				}else{
					logger.error(method+"no mq find:userId="+userId+",appName="+appName+",entity="+entity);
				}
			}			
			//logger.info(method+"[ios_token_for_duid]sn="+entity.getDeviceSn()+",duidSet="+duidSet+",duidTokenSet="+duidTokenSet);
			// ios begin
			Set<Object> iosTokenSet = iosTokenCache.getIosTokenSet(String.valueOf(userId));
			//logger.info(method+"[ios_token_for_user]sn="+entity.getDeviceSn()+",userId="+userId+",iosTokenSet="+iosTokenSet);
			if (null != iosTokenSet){
				iosTokenSet.removeAll(duidTokenSet);
				logger.info(method+"[ios_token_after_filter]sn="+entity.getDeviceSn()+",userId="+userId+",iosTokenSet="+iosTokenSet);
				for (Object iosToken : iosTokenSet) {
					try{
						ApnsServiceUtil.pushAlarm((String)iosToken, entity);
					}catch(Exception e){
						logger.error("push alarm to APN failed!alarm:"+entity);
					}
				}
			}
			// ios end
		}
		//发送短信
		Device device = deviceCache.getDevice(entity.getDeviceSn());
		if(device == null){
			device = deviceDao.get(entity.getDeviceSn());
		}
		/*if(device.getEnableSmsAlarm() != null && device.getEnableSmsAlarm() == 1 && device.getContact() != null && !"".equals(device.getContact())){
			String mq = "sms_alarm_push_to_device";
			int type = entity.getType();
			String einfo = "" ;
			if(type == 1){
				einfo = "outFence";
			}else if(type == 2){
				einfo = "inFence";
			}else if (type == 3){
				einfo = "lowPower";
			}else if(type == 4){
				einfo = "overspeed";
			}else if(type == 5){
				einfo = "sos";
			}else if(type == 6){
				einfo = "move";
			}else if(type == 7){
				einfo = "trad";			
			}else{
				einfo = "alarm";
			}
			String msg = device.getSn()+einfo;

			if(device.getType() == 1 || device.getType() == 3){		
				msg = "#360#"+device.getContact()+"#"+msg+"#0000##";
			}else{
				msg = "*HQ,"+entity.getDeviceSn()+",360,"+device.getContact()+","+msg+",0000#";
			}
			
			SmsAlarm smsAlarm = new SmsAlarm();
			smsAlarm.setDeviceSn(entity.getDeviceSn());
			smsAlarm.setContent(msg);
			jmsTemplate.send(mq, smsAlarm);
			logger.info("toMq:"+mq+",Param:" + smsAlarm);
		}*/
	}

	/**
	 * true表示不用管"程序前后台运行"都推
	 */
	public void pushPosition(Position position) {
		List<Long> userIds = userDeviceDao.findUserIdBySn(position.getDeviceSn());
		Device device = deviceCache.getDevice(position.getDeviceSn());
		String appName = null;
		if(device != null){
			appName = "M2616_BD";
		}else{
			appName = deviceDao.getAppName(position.getDeviceSn());
		}
		
		for (Long userId : userIds) {
			Set<Object> duidSet = userCache.getOnlinePhone(String.valueOf(userId), appName);
			Set<Object> backendDuidSet = userCache.getBackendPhone(String.valueOf(userId), appName);
			Set<String> duidTokenSet = new HashSet<String>();
			if (null != duidSet && backendDuidSet != null) {
				duidSet.removeAll(backendDuidSet);
			}
			if (null != duidSet) {
				for (Object duid : duidSet) {
					String mq = userCache.getPhoneMq(String.valueOf(userId), appName, (String)duid);
					sendMq(MsgBuilder.buildFromPosition(position, userId, appName), mq);
					String ios_token = userCache.getIosTokenMapping((String)duid);
					if(ios_token != null && !"".equals(ios_token)){
						duidTokenSet.add(ios_token);
					}
				}
			}
			//logger.info("[ios_token_for_duid]sn="+position.getDeviceSn()+",duidSet="+duidSet+",duidTokenSet="+duidTokenSet);
			// ios
			Set<Object> iosTokenSet = iosTokenCache.getIosTokenSet(String.valueOf(userId));
			if (null != iosTokenSet){
				iosTokenSet.removeAll(duidTokenSet);
				logger.info("[ios_token_after_filter]sn="+position.getDeviceSn()+",userId="+userId+",iosTokenSet="+iosTokenSet);
				for (Object iosToken : iosTokenSet) {
					if (backendDuidSet == null || !backendDuidSet.contains(iosToken)){// 未进入后台
						try{
							ApnsServiceUtil.pushPosition((String)iosToken, position);
						}catch(Exception e){
							logger.error(e.getMessage());
						}
					}
				}
			}
			// ios end
		}
	}
	
	public void sendMq(PushMsg pushMsg, String mq) {
		if (null != mq) {
			jmsTemplate.send(mq, pushMsg);
			logger.info("toMq:"+mq+",Param:" + pushMsg);
		} else
			logger.info("warn:过期" + pushMsg);
	}

}
