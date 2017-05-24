package com.capcare.harbor.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.Device;

/**
 * @author fyq
 */
@Component
@Scope("singleton")
public class DeviceDao extends BaseDao<Device, String> {
	private static Logger logger = LoggerFactory.getLogger(DeviceDao.class);
	@Autowired
	public DeviceDao(@Qualifier("sessionFactoryMaster") SessionFactory session) {

		super();
		this.setSessionFactory(session);
		this.setSessionFactoryMaster(session);
	}

	public int getDeviceType(String deviceSn) {
		String hql = "select type from Device ud where sn=?";
		return getHql(hql, deviceSn);
	}

	public String getAppName(String deviceSn) {
		String hql = "select appName from Device ud where sn=?";
		return getHql(hql, deviceSn);
	}

	public Device getDeviceBySn(String sn) {
		String hql = "from Device ud where sn=?";
		return getHql(hql, sn);
	}

	public Device findDeviceBySn(String sn) {
		String hql = "From Device where sn = '" + sn + "'";
		List<Device> lists =  find(hql);
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}
	
	
	public void updateDeviceTime(String sn,Date time) {
		logger.info("=---------------updateDevisdfas11-----------"+sn+time);
		Device device = findDeviceBySn(sn);
		
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		System.out.print(dateFormater.format(date));
		
		if(device != null){
			
			device.setLastTime(dateFormater.format(date));
			logger.info("=---------------lastTime-----------"+device.getLastTime());
			this.saveOrUpdate(device);
			
		}
	
	}
	

}
