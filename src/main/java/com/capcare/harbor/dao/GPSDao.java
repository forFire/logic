package com.capcare.harbor.dao;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.GPSData;

@Component
@Scope("singleton")
public class GPSDao extends BaseDao<GPSData, Long> {
	
	private static Logger logger = LoggerFactory.getLogger(GPSDao.class);
	
    @Autowired
    public GPSDao (@Qualifier("sessionFactoryBeidou") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
        this.setSessionFactoryMaster (session);
    }

	public void saveUserData(GPSData gps) {
		
		logger.info("save in database :{}",gps );
		this.save(gps);
	}
}
