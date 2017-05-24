package com.capcare.harbor.service.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.DictEquiptmentDao;
import com.capcare.harbor.model.DictEquipment;
/**
 * 中控室（城安设备）最后心跳时间
 */	
@Component
public class EquipmentTimeService {
	private static Logger logger = LoggerFactory.getLogger(EquipmentTimeService.class);
	
	@Autowired
	DictEquiptmentDao dictEquiptmentDao;
	
	/**
	 * 采集设备时间
	 */
	public void updateEquipmentTime(DictEquipment dictEquipment) {
		logger.info("心跳信息:"+ dictEquipment.getCode());
		dictEquiptmentDao.updateDictEquiptment(dictEquipment);
		
	}
	
	
}
