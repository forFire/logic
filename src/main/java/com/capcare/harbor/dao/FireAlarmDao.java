package com.capcare.harbor.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.FireAlarmEntity;
import com.capcare.harbor.protocol.DeviceOperation;

import module.orm.BaseDao;

@Component
@Scope("singleton")
public class FireAlarmDao extends BaseDao<FireAlarmEntity, Integer> {
	private static Logger logger = LoggerFactory.getLogger(FireAlarmDao.class);
    @Autowired
    public FireAlarmDao (@Qualifier("sessionFactoryMaster") SessionFactory session) {
        super ();
        this.setSessionFactory (session);
        this.setSessionFactoryMaster (session);
    }
    
    /**
     *系统类型复位
     *param sn
     *状态为0 
     */
    public List<FireAlarmEntity> findBySystemType(DeviceOperation deviceOperation){
//    	String hql ="select f From FireAlarmEntity f , com.capcare.harbor.model.Device as d   where 1=1 and f.sn = d.sn and f.status='0' and d.system = '"+deviceOperation.getSysType()+"'and d.sn like'"+deviceOperation.getPrimaryAddress()+"%'";
    	String hql ="select f From FireAlarmEntity f , com.capcare.harbor.model.Device as d   where 1=1 and f.sn = d.sn and f.status='0' and d.sn like'"+deviceOperation.getPrimaryAddress()+"%'";
    	List<FireAlarmEntity> list = find(hql);
    	logger.info("================="+list.size());
		return list;
    }
    
     /**
      * 故障恢复
      * 类型是故障 0，且状态为未处理 0
      * param sn
      */
     public void updateStatus(FireAlarmEntity fireAlarm){
    	 String hql ="select f From FireAlarmEntity f where 1=1 and f.sn ='"+fireAlarm.getSn()+"' and f.type='0' and f.status='0' order by f.alarmTime desc ";
     	 List<FireAlarmEntity> list = find(hql);
    	 if(list.size() > 0){
    		 FireAlarmEntity f = list.get(0); 
    		 f.setStatus(1);
        	 f.setStatusPerson(1);
        	 f.setStatusSystem(1);
        	 update(f);
    	 }
    
     }
    

}
