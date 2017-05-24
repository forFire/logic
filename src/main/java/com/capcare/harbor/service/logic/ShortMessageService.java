package com.capcare.harbor.service.logic;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.ShortMessageDao;
import com.capcare.harbor.model.ShortMessage;

@Component
public class ShortMessageService {

	@Resource
	private ShortMessageDao shortMessageDao;
	
	public void save(ShortMessage shortMessage) {
		int l = shortMessageDao.save(shortMessage);
		System.out.println(l);
	}

	public ShortMessage findById(long id) {
		return shortMessageDao.get(id);
	}

	public void delete(long id) {
		shortMessageDao.delete(id);
	}

	public List<ShortMessage> findNotSendSuccess(String sendHandsetId) {
		List<ShortMessage> notSendSuccesses = shortMessageDao.findNotSendSuccess(sendHandsetId);
		return notSendSuccesses;
	}

}
