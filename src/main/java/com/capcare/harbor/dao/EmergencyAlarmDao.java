package com.capcare.harbor.dao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.EmergencyAlarm;

import module.orm.BaseDao;

@Component
public class EmergencyAlarmDao extends BaseDao<EmergencyAlarm, Integer> {

	private static Logger logger = LoggerFactory.getLogger(EmergencyAlarm.class);
	
	@Autowired
	public EmergencyAlarmDao (@Qualifier("sessionFactoryMaster") SessionFactory session) {
	    super ();
	    this.setSessionFactory (session);
	    this.setSessionFactoryMaster (session);
	}
	
	public void saveEmergencyAlarm(EmergencyAlarm emergencyAlarm){
		logger.info("save in database :{}",emergencyAlarm );
		this.save(emergencyAlarm);
	}
}
