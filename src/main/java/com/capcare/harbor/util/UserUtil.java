package com.capcare.harbor.util;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import com.capcare.harbor.model.User;
import com.capcare.harbor.service.cache.UserCache;
import com.capcare.harbor.service.logic.PushAlarmToApp;
@Component
public class UserUtil {
	private static Logger logger = LoggerFactory.getLogger(UserUtil.class);
	@Autowired
	UserCache userCache;
	
	public  String getDeviceToken(List<User> users){
		String deviceTokens = "";
		String userId = "";
		for (User u : users) {
			String deviceToken = userCache.getDeviceToken(u.getId()+"");
			if(!StringUtils.isEmpty(deviceToken)){
				//获取deviceToken对应的用户
				userId = userCache.getUserByDeviceToken(deviceToken);
				//判断推送用户对应的deviceToken对应的是否是该用户
				if((u.getId()+"").equals(userId)){
					logger.info("deviceToken11111================="+deviceToken);
					deviceTokens += deviceToken + ","; 
				}
			}else{
				//用于排查推送问题
				System.out.println("id为："+u.getId()+"的用户deviceToken不存在");
			}
		}
		if (deviceTokens.length() > 0) {
			deviceTokens = deviceTokens.substring(0, deviceTokens.length() - 1);
			logger.info("deviceToken11111================="+deviceTokens);
		}
		return deviceTokens;
	}

	
}
