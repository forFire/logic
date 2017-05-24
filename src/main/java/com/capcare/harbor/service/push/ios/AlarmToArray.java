package com.capcare.harbor.service.push.ios;

import com.capcare.harbor.protocol.Alarm;

/**
 * @author fyq
 */
public class AlarmToArray {

	public static String[] toArray(Alarm po) {
		Object lng = po.getLng();
		Object lat = po.getLat();
		Object receive = po.getTime();
		Object type = po.getType();
		Object deviceSn = po.getDeviceSn();
		Object info = po.getInfo();
		Object speed = po.getSpeed();
		Object addr = po.getAddr();
		Object read = po.getRead();
		Object id = po.getId();
		Object battery = po.getBattery();
		String[] strArray = new String[11];
		strArray[0] = lng == null ? "" : lng.toString();
		strArray[1] = lat == null ? "" : lat.toString();
		strArray[2] = receive == null ? "" : receive.toString();
		strArray[3] = type == null ? "" : type.toString();
		strArray[4] = deviceSn == null ? "" : deviceSn.toString();
		strArray[5] = info == null ? "" : info.toString();
		strArray[6] = speed == null ? "" : speed.toString();
		strArray[7] = addr == null ? "" : addr.toString();
		strArray[8] = read == null ? "" : read.toString();
		strArray[9] = id == null ? "" : id.toString();
		strArray[10] = battery == null ? "" : battery.toString();
		return strArray;
	}

}
