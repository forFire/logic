package com.capcare.harbor.service.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import module.util.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capcare.harbor.protocol.Alarm;
import com.capcare.harbor.protocol.Position;
import com.capcare.harbor.protocol.PushMsg;

public class MsgBuilder {

	private static Logger logger = LoggerFactory.getLogger(MsgBuilder.class);
	public static PushMsg buildFromPosition(Position position, Long userId, String appName) {

		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 10);
		cmdObj.put("ret", 1);
		String[] strArray = new String[10];
		strArray[0] = position.getLng() == null ? "" : position.getLng().toString();
		strArray[1] = position.getLat() == null ? "" : position.getLat().toString();
		strArray[2] = position.getReceive() == null ? "" : position.getReceive().toString();
		strArray[3] = position.getMode() == null ? "" : position.getMode();
		strArray[4] = position.getDeviceSn() == null ? "" : position.getDeviceSn();
		strArray[5] = position.getBattery() == null ? "" : position.getBattery().toString();
		strArray[6] = position.getSpeed() == null ? "" : position.getSpeed().toString();
		strArray[7] = position.getDirection() == null ? "" : position.getDirection().toString();
		strArray[8] = position.getStamp() == null ? "" : position.getStamp().toString();
		strArray[9] = position.getStatus() == null ? "" : position.getStatus().toString();
		cmdObj.put("device_statuss", strArray);
		
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		cmdList.add(cmdObj);
		
		Map<String, Object> push = new HashMap<String,Object>();
		push.put("protocol", cmdList);
		String rs = "";
		try {
			rs = JsonUtils.getJson(push);
		} catch (Exception e) {
			logger.error("",e);
		}
		rs = "<push>" + rs + "</push>";
		
		PushMsg pushMsg = new PushMsg(userId, appName, rs);
		return pushMsg;
	}

	public static PushMsg buildFromAlarm(Alarm alarm, Long userId, String appName) {
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 9);
		cmdObj.put("ret", 1);
		List<Alarm> alarms = new ArrayList<Alarm>();
		alarms.add(alarm);
		cmdObj.put("alarms", alarms);
		
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		cmdList.add(cmdObj);
		
		Map<String, Object> push = new HashMap<String,Object>();
		push.put("protocol", cmdList);
		String rs = "";
		try {
			rs = JsonUtils.getJson(push);
		} catch (Exception e) {
			logger.error("",e);
		}
		rs = "<push>" + rs + "</push>";
		PushMsg pushMsg = new PushMsg(userId, appName, rs);
		return pushMsg;
	}
	
}
