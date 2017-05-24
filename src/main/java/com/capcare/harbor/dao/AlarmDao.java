package com.capcare.harbor.dao;

import java.util.List;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.AlarmEntity;
import com.capcare.harbor.model.FireAlarmEntity;

@Component
@Scope("singleton")
public class AlarmDao extends BaseDao<FireAlarmEntity, Long> {
	
    @Autowired
    public AlarmDao (@Qualifier("sessionFactoryMaster") SessionFactory session) {
        super ();
        this.setSessionFactory (session);
        this.setSessionFactoryMaster (session);
    }

	public AlarmEntity getLatestAlarm(String deviceSn, int type) {
		String hql = "from AlarmEntity ud where deviceSn=? and type=? order by time desc limit";
		List<AlarmEntity> list = find(hql, deviceSn, type);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	
}
