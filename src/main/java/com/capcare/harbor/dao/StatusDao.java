package com.capcare.harbor.dao;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.Status;

@Component
@Scope("singleton")
public class StatusDao extends BaseDao<Status, Long> {
	
	private static Logger logger = LoggerFactory.getLogger(StatusDao.class);
	
    @Autowired
    public StatusDao (@Qualifier("sessionFactoryBeidou") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
        this.setSessionFactoryMaster (session);
    }

	public void saveStatus(Status status) {
		
		logger.info("save in database :{}", status);
		this.saveOrUpdate(status);
	}
}
