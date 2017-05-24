package com.capcare.harbor.dao;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.UserData;

@Component
@Scope("singleton")
public class UserDataDao extends BaseDao<UserData, Long> {

    @Autowired
    public UserDataDao (@Qualifier("sessionFactoryBeidou") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
        this.setSessionFactoryMaster (session);
    }

	public void saveUserData(UserData userData) {
		
		this.saveOrUpdate(userData);
	}
}
