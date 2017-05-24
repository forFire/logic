package com.capcare.harbor.service.logic;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.StatusDao;
import com.capcare.harbor.model.Status;
import com.capcare.harbor.protocol.Conductor_Status;

@Component
public class StatusService {
	private static Logger logger = LoggerFactory.getLogger(StatusService.class);

	@Resource
	private StatusDao statusDao;


//	private static int mills = 3 * 60 * 1000;

	/**
	 * 保存指挥机状态
	 */
	public void saveStatus(Conductor_Status status) {
		
		logger.info("");
		Status statusModel = convertToStatus(status);
		statusDao.saveStatus(statusModel);
	}
	
	public Status convertToStatus(Conductor_Status status){
		
		Status statusModel = new Status();
		statusModel.setAccMode(status.getAccMode());
		statusModel.setBean1(status.getBean1());
		statusModel.setBean2(status.getBean2());
		statusModel.setBean3(status.getBean3());
		statusModel.setBean4(status.getBean4());
		statusModel.setBean5(status.getBean5());
		statusModel.setBean6(status.getBean6());
		statusModel.setBean7(status.getBean7());
		statusModel.setBean8(status.getBean8());
		statusModel.setBean9(status.getBean9());
		statusModel.setBean10(status.getBean10());
		
		return statusModel;
	}

}
