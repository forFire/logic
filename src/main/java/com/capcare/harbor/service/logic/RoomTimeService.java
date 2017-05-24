package com.capcare.harbor.service.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.DictControlRoomDao;
import com.capcare.harbor.model.DictControlRoom;
/**
 * 中控室（城安设备）最后心跳时间
 */	
@Component
public class RoomTimeService {
	private static Logger logger = LoggerFactory.getLogger(RoomTimeService.class);
	
	@Autowired
	DictControlRoomDao dictControlRoomDao;

	
	/**
	 * 中控室时间
	 */
	public void updateRoomTime(DictControlRoom dictControlRoom) {
		logger.info("心跳信息:"+ dictControlRoom.getCode());
		dictControlRoomDao.updateDictControlRoom(dictControlRoom);
		
	}
	
	
}
