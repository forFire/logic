package com.capcare.harbor.dao;

import java.util.ArrayList;
import java.util.List;

import module.orm.BaseSqlDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capcare.harbor.model.DictEquipment;

@Repository
public class DictEquiptmentDao   extends BaseSqlDao<DictEquipment, Long> { 
	private static Logger logger = LoggerFactory.getLogger(DictEquiptmentDao.class);
	
	public void updateDictEquiptment(DictEquipment dictEquipment) {
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE dict_equipment equipment");
		sb.append(" SET equipment.f_last_time =?   where equipment.f_code=? ");
		List<Object> ls = new ArrayList<Object>();
		ls.add(dictEquipment.getLastTime());
		
		//城安6位  利达12位
		ls.add(dictEquipment.getCode());
		
		logger.info("=----------updateDictEquiptment（）----------------"+dictEquipment.getLastTime()+dictEquipment.getCode());
		
		this.save(sb.toString(), ls);
		
	}
	
	/**
	 * 补齐长度
	 * @param value
	 * @param size
	 * @return
	 */
	public static String toString(String value, int size) {
		while (value.length() < size) {
			value = "0" + value;
		}

		if (value.length() > size) {
			value = value.substring(0, size);
		}
		return value;
	}
	
}
