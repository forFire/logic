package com.capcare.harbor.service.push.ios;

import java.io.InputStream;
import java.util.Properties;

import module.util.AbsolutePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capcare.harbor.protocol.Alarm;
import com.capcare.harbor.protocol.Position;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.PayloadBuilder;

/**
 * @author fyq
 */
public class ApnsServiceUtil {

	private static Logger logger = LoggerFactory.getLogger(ApnsServiceUtil.class);

	private static String path = AbsolutePath.absolutePath("").getPath();

	//private static ApnsService service = APNS.newService().withCert(path + "aps_production.p12", "capcare").withProductionDestination().build();
	private static ApnsService service;
	private static String environment;
	
	static{
		Properties prop = new Properties();
        InputStream in = ApnsServiceUtil.class.getResourceAsStream("/application.properties");
        try{
        	prop.load(in);
        	in.close();
        }catch(Exception e){
        	logger.error("ApnsServiceUtil", e);
        }
        
        String isSandbox = prop.getProperty("ios.apn.sandbox");
        ApnsServiceBuilder  builder= APNS.newService();
    	builder.asPool(10);
    	builder.asQueued();
        if(isSandbox != null && "true".equals(isSandbox)){
        	environment = "sandbox";
        	service =builder.withCert(path + "aps_developer.p12", "123456").withSandboxDestination().build();
        }else{
        	environment = "production";
        	service = APNS.newService().withCert(path + "aps_production.p12", "capcare").withProductionDestination().build();
        }
	}
	
	private static ApnsService getApnsService() {
		return service;
	}
	
	

	// -----------------------------------------------------------------------
	public static String alarmPayload(Alarm form) {
		PayloadBuilder payloadBuilder = APNS.newPayload();
		payloadBuilder.sound("default");
		// payloadBuilder.badge(4);
		payloadBuilder.alertBody(form.getInfo());

		String[] strs = AlarmToArray.toArray(form);

		payloadBuilder.customField("alarm", strs);
		// payloadBuilder.customField("alarm", form);
		return payloadBuilder.build();
	}

	public static void pushAlarm(String token, Alarm form) {
		String payload = alarmPayload(form);
		logger.info("[" + environment + "]"+payload.length() + "------iosTokenPush:{" + token + "}------" + payload);
		
		try{
			ApnsNotification apnsNotification = getApnsService().push(token, payload);
			if (apnsNotification != null){
				logger.info("msg_identifier:" + apnsNotification.getIdentifier());
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
	}

	// -----------------------------------------------------------------------
	public static String positionPayload(Position form) {
		PayloadBuilder payloadBuilder = APNS.newPayload();

		String[] strs = PositionToArray.toArray(form);

		payloadBuilder.customField("position", strs);
		// payloadBuilder.customField("position", form);
		return payloadBuilder.build();
	}

	public static void pushPosition(String token, Position form) {
		String payload = positionPayload(form);
		try{
			getApnsService().push(token, payload);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("[" + environment + "]"+payload.length() + "------iosTokenPush:{" + token + "}------" + payload);
	}

	// -----------------------------------------------------------------------
	public static void main(String[] args) throws Exception {
		// 松松848848302692dab4aef59dd3ade0f210f535a5ae2f8293816c9694554193778a
		// 培焕9a2c19f8f701d2352a0f0c470ce5b65d2045d61ae089310e2e9cdfc33373dd7c
		// 文博e77a543e9aeb0557c2c93e9774474ea786b8d869497cc15e3e97a9698494ed7a
		String token = "9d3499a51a7e306b56e962f897047c1fe76b87d0c722b9f06ad7e4f589627789";//志海
		
		// Alarm alarm = new Alarm();
		// alarm.setInfo("移动告警");
		// alarm.setDeviceSn("abc");
		// pushAlarm(token, alarm);

		Position position = new Position();
		position.setDeviceSn("abc_");
		position.setLat(1D);
		position.setLng(2D);
		pushPosition(token, position);
	}
}
