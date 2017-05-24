package com.capcare.harbor.dao;

import java.io.Serializable;
import java.util.List;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.ShortMessage;

@Component
@Scope("singleton")
public class ShortMessageDao extends BaseDao<ShortMessage, Serializable> {
	
	private final String SELECT_NOT_SEND_SUCCESS = "from ShortMessage s where s.sendHandsetId = ? and s.status = ?";

	@Autowired
	public ShortMessageDao (@Qualifier("sessionFactoryBeidou") SessionFactory session){

        super ();
        this.setSessionFactory (session);
        this.setSessionFactoryMaster (session);
	}

	public List<ShortMessage> findNotSendSuccess(String sendHandsetId) {
		List<ShortMessage> notShortMessages = this.find(SELECT_NOT_SEND_SUCCESS, sendHandsetId,ShortMessage.getDEFAULT_SEND_NOT_SUCCESS_STATUS());
		return notShortMessages;
	}
}
