package com.capcare.harbor.service.push.ios;

import com.capcare.harbor.protocol.Position;

/**
 * @author fyq
 */
public class PositionToArray {

	public static String[] toArray(Position po) {
		Object lng = po.getLng();
		Object lat = po.getLat();
		Object receive = po.getReceive();
		Object mode = po.getMode();
		Object deviceSn = po.getDeviceSn();
		Object battery = po.getBattery();
		Object speed = po.getSpeed();
		Object direction = po.getDirection();
		Object stamp = po.getStamp();
		Object status = po.getStatus();
		Object stayed = po.getStayed();
		Object alarm = po.getAlarm();
		Object flow = po.getFlow();
		Object info = po.getInfo();
		String[] strArray = new String[14];
		strArray[0] = lng == null ? "" : lng.toString();
		strArray[1] = lat == null ? "" : lat.toString();
		strArray[2] = receive == null ? "" : receive.toString();
		strArray[3] = mode == null ? "" : mode.toString();
		strArray[4] = deviceSn == null ? "" : deviceSn.toString();
		strArray[5] = battery == null ? "" : battery.toString();
		strArray[6] = speed == null ? "" : speed.toString();
		strArray[7] = direction == null ? "" : direction.toString();
		strArray[8] = stamp == null ? "" : stamp.toString();
		strArray[9] = status == null ? "" : status.toString();
		strArray[10] = stayed == null ? "" : stayed.toString();
		strArray[11] = alarm == null ? "" : alarm.toString();
		strArray[12] = flow == null ? "" : flow.toString();
		strArray[13] = info == null ? "" : info.toString();
		return strArray;
	}

}
