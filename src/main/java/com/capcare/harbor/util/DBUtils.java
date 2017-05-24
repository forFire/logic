package com.capcare.harbor.util;

import org.apache.commons.lang3.StringUtils;

public class DBUtils {

	public static String getSpotTableName(String deviceSn) throws Exception {

		if (!StringUtils.isEmpty(deviceSn) && deviceSn.length() > 2) {
			String suffix = deviceSn.substring(deviceSn.length() - 2,
					deviceSn.length());
			return format(suffix);
		} else {
			throw new Exception("f_device_sn is ileagal");
		}
	}

	private static String format(String suffix) {
		int s = Integer.parseInt(suffix);
		String str = String.format("%02d", s);
		return str;
	}
}