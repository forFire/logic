package com.capcare.harbor.dao;

import java.util.ArrayList;
import java.util.List;

import module.orm.BaseSqlDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capcare.harbor.model.DictControlRoom;

@Repository
public class DictControlRoomDao   extends BaseSqlDao<DictControlRoom, Long> { 
	private static Logger logger = LoggerFactory.getLogger(DictControlRoomDao.class);
	
	public void updateDictControlRoom(DictControlRoom dictControlRoom) {
		logger.info("=--------------------------");
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE dict_control_room room");
		sb.append("  SET room.f_last_time =?   where room.f_code=? ");
		List<Object> ls = new ArrayList<Object>();
		ls.add(dictControlRoom.getLastTime());
		ls.add(toString(dictControlRoom.getCode(),6));
		
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
