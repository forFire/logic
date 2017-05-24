package com.capcare.harbor.service.logic;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.UserDataDao;
import com.capcare.harbor.model.UserData;
import com.capcare.harbor.protocol.Conductor_UserData;
import com.capcare.harbor.service.cache.PositionCache;
import com.capcare.harbor.service.push.PushService;

@Component
public class UserDataService {
	private static Logger logger = LoggerFactory.getLogger(UserDataService.class);
	@Resource
	private PushService pushService;

	@Resource
	private PositionCache positionCache;

	@Resource
	private UserDataDao userDataDao;


//	private static int mills = 3 * 60 * 1000;

	/**
	 * 保存指挥机状态
	 */
	public void saveUserData(Conductor_UserData userData) {
		
		logger.info("");
		UserData uData = convertToUserData(userData);
		userDataDao.saveUserData(uData);
		
		
	}
	
	public UserData convertToUserData(Conductor_UserData userData){
		
		UserData uData = new UserData();
		uData.setAccMode(userData.getAccMode());
		uData.setConductor_sn(userData.getConductor_sn());
		uData.setConductor_addr(userData.getConductor_addr());
		uData.setFrequentness(userData.getFrequentness());
		uData.setConductor_type(userData.getConductor_type());
		uData.setConductor_level(userData.getConductor_level());
		uData.setEncryption(userData.getEncryption());
		uData.setConductor_bean(userData.getConductor_bean());
		uData.setUser_no(userData.getUser_no());
		Date date = new Date();
		String dateTime = convertToDateTime(date);
		uData.setCreate_date(dateTime);
		
		return uData;
	}
	
	public String convertToDateTime(Date date){
		
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String dateTime = adf.format(date);
		return dateTime;
	}
}
