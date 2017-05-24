package com.capcare.harbor.service.logic;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.GPSDao;
import com.capcare.harbor.model.GPSData;
import com.capcare.harbor.protocol.Conductor_GPS;

@Component
public class GPSService {
	private static Logger logger = LoggerFactory.getLogger(GPSService.class);

	@Resource
	private GPSDao GPSDao;


//	private static int mills = 3 * 60 * 1000;

	/**
	 * 保存指挥机状态
	 */
	public void saveGPS(Conductor_GPS gps) {
		
		logger.info("");
		GPSData gpsData = convertToModelGPS(gps);
		GPSDao.saveUserData(gpsData);
	}
	
	public GPSData convertToModelGPS(Conductor_GPS gps){
		
		GPSData gpsData = new GPSData();
		gpsData.setAccMode(gps.getAccMode());
		gpsData.setSatellite_no(gps.getSatellite_no());
		return gpsData;
	}

}
