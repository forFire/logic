package com.capcare.harbor.service.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.DeviceDao;
import com.capcare.harbor.dao.FireAlarmDao;
import com.capcare.harbor.dao.OrgControlRoomDao;
import com.capcare.harbor.dao.OrgDao;
import com.capcare.harbor.dao.UserDao;
import com.capcare.harbor.model.Device;
import com.capcare.harbor.model.Org;
import com.capcare.harbor.model.OrgControlRoom;
import com.capcare.harbor.model.User;
import com.capcare.harbor.service.cache.AlarmCache;
import com.capcare.harbor.util.CastData;
import com.capcare.harbor.util.PushUtil;
import com.capcare.harbor.util.UserUtil;

@Component
public class PushAlarmToApp {
	private static Logger logger = LoggerFactory.getLogger(PushAlarmToApp.class);
	
	@Resource
	private FireAlarmDao fireAlarmDao;

	@Autowired
	AlarmCache alarmCache;

	@Resource
	private DeviceDao deviceDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	OrgControlRoomDao orgControlRoomDao;
	
	@Autowired
	OrgDao orgDao;
	
	@Autowired
	UserUtil userUtil;
	
	
	
	/**
	 * 推送用户 1设备中控室部门 2 企业下领导
	 *  0室内 1室外
	 * 室外没有中控室 只需要发给企业用户
	 * 
	 * 注意：推送给父用户，每个用户接收到的报警数不一致
	 */
	public void pushAlarm(Device device,int flag) {
		//获取企业
		Org org = orgDao.findUserByOrgId(device.getOrgId());
		int num = 0;
		//部门用户
		List<User> users = new ArrayList<User>();
		//父部门id
		List<String> orgLists = new ArrayList<String>();
		//室内
		if(flag == 0){
			//根据中控室查中控室部门表
		    List<OrgControlRoom> rooms = orgControlRoomDao.getOrgSByControRoomCode(device.getRoomCode());
		    logger.info("device.getRoomCode()============"+device.getRoomCode());
		    //2循环取出部门  
			for(OrgControlRoom room:rooms){
				//此部门用户
//				users = userDao.findUserByOrgId(room.getOrgId());
				//此部门下报警数
				//部门下中控室
//				List<OrgControlRoom> rs = orgControlRoomDao.getRoomsByOrgId(room.getOrgId());
//				报警数
//				num = findNumByRoomCode(rs);
//				推送给部门用户
//				boolean a =  sentToApp(device,users,org.getName(),0,num);
//				logger.info("推送结果a-----------------------------"+a);
				
				//推送用户===当前用户和父部门用户 
				List<String> orgList = orgDao.getParentList(room.getOrgId());
				orgLists.addAll(orgList);
				
			 }
			
			//3推送当前用户和父部门用户 
			//去重
			Set<String> set = new HashSet<String>();
			for(String orgIds : orgLists){
				String str[] = orgIds.split(","); 
				for(String s : str ){
					//过滤企业机构--根据企业id查报警数
					Org org1 = orgDao.findUserByOrgId(Integer.valueOf(s));
					if(org1.getParentId() == 0){
						continue;
					}
					set.add(s);
				}
			}
			
			//遍历当前用户和父部门用户 
			Iterator<String> it = set.iterator();  
			while (it.hasNext()) {  
			    String str = it.next(); 
			  	//此部门用户
				users = userDao.findUserByOrgId(Integer.valueOf(str));
				//此部门下报警数
				//部门下中控室
//				List<OrgControlRoom> rs = orgControlRoomDao.getRoomsByOrgId(Integer.valueOf(str));
				//报警数
				num = findNumByRoomCode(users);
				//推送给当前用户和父部门用户
				boolean a =  sentToApp(device,users,org.getName(),0,num);
				logger.info("推送结果1a-----------------------------"+a);
			} 
			
		}
		
		//获取企业
		//4直接挂在企业下的用户
		List<User> users1 = userDao.findDealAlarmByOrgId(device.getOrgId());
		//获取企业
		//推送给企业用户
		boolean b =	sentToApp(device,users1,org.getName(),1,1);
		logger.info("推送结果2b-----------------------------"+b );
		
	}
	
	public void pushAlarm(Device device,String casecadeSn ,int flag) {
		//获取企业
		Org org = orgDao.findUserByOrgId(device.getOrgId());
		int num = 0;
		//部门用户
		List<User> users = new ArrayList<User>();
		//父部门id
		List<String> orgLists = new ArrayList<String>();
		//室内
		if(flag == 0){
			//根据中控室查中控室部门表
		    List<OrgControlRoom> rooms = orgControlRoomDao.getOrgSByControRoomCode(device.getRoomCode());
		    logger.info("device.getRoomCode()============"+device.getRoomCode());
		    //2循环取出部门  
			for(OrgControlRoom room:rooms){
				//推送用户===当前用户和父部门用户 
				List<String> orgList = orgDao.getParentList(room.getOrgId());
				orgLists.addAll(orgList);
			 }
			
			//3推送当前用户和父部门用户 
			//去重
			Set<String> set = new HashSet<String>();
			for(String orgIds : orgLists){
				String str[] = orgIds.split(","); 
				for(String s : str ){
					//过滤企业机构--根据企业id查报警数
					Org org1 = orgDao.findUserByOrgId(Integer.valueOf(s));
					if(org1.getParentId() == 0){
						continue;
					}
					set.add(s);
				}
			}
			
			//遍历当前用户和父部门用户 
			Iterator<String> it = set.iterator();  
			while (it.hasNext()) {  
			    String str = it.next(); 
			  	//此部门用户
				users = userDao.findUserByOrgId(Integer.valueOf(str));
				//此部门下报警数
				//报警数
				num = findSuspectedNumByRoomCode(users);
				//推送给当前用户和父部门用户
				boolean a =  sentSuspectedAlarm(device,users,num);
				logger.info("推送结果1a-----------------------------"+a);
			} 
			
		}
		
		//获取企业
		//4直接挂在企业下的用户
		Integer orgId = device.getOrgId();
		List<User> users1 = userDao.findDealAlarmByOrgId(orgId);
		//获取企业
		//推送给企业用户
		boolean b =	sentSuspectedAlarm(device,users1,alarmCache.getOrgInSuspectedAlarmNum(orgId));
		logger.info("推送结果2b-----------------------------"+b );
		
	}
	
	public boolean sentToApp(Device device,List<User> users,String orgName,int flag,int num) {
		PushUtil push = new PushUtil();
		CastData cast = new CastData();
		cast.setType(Constants.CAST_ALARM);
		// 设置推送数量
		if(flag == 1){
			//企业领导根据orgId查
			if(users.size() > 0){
				cast.setNum(findNumByUserId(users.get(0)));
			}else{
				cast.setNum(0);
			}
			
		}else{
			//部门根据roomCode查
//			List<OrgControlRoom> rooms = orgControlRoomDao.getRoomSByControRoomCode(device.getRoomCode());
			cast.setNum(num);
			logger.info("num================="+num);
		}
		
		logger.info("users================="+users.size());
		for(User u : users){
			logger.info("用户名称1================="+u.getRealName());
		}
		
		// 获取推用户deviceToken
		String deviceTokens = userUtil.getDeviceToken(users);
		
		//用户存在进行消息推送，否则不进行消息推送
		if (deviceTokens.length() > 0) {
			logger.info("deviceTokens.length()================"+deviceTokens.length());
			return push.sendListcast(deviceTokens, cast,Constants.CAST_ALARM,num);
			
		}else{
			return false;
		}
		
	}
	
	public boolean sentSuspectedAlarm(Device device,List<User> users,int num) {
		PushUtil push = new PushUtil();
		CastData cast = new CastData();
		cast.setType(Constants.CAST_SUSPECTED);
		cast.setNum(num);
		logger.info("疑似火警num================="+num);
		
		logger.info("users================="+users.size());
		for(User u : users){
			logger.info("用户名称1================="+u.getRealName());
		}
		
		// 获取推用户deviceToken
		String deviceTokens = userUtil.getDeviceToken(users);
		
		//用户存在进行消息推送，否则不进行消息推送
		if (deviceTokens.length() > 0) {
			logger.info("deviceTokens.length()================"+deviceTokens.length());
			return push.sendListcast(deviceTokens, cast,Constants.CAST_ALARM,num);
		}else{
			return false;
		}
	}
	
	/**
	 * 推送消息 报警数===报警总数--人工处理数 子子孙孙
	 * 1：机构直接根据org 查
	 */
	public int findNumByUserId(User user) {
		int inNum =0;
		inNum = alarmCache.getOrgInAlarmNum(user.getOrgId())+alarmCache.getOutAlarmNum(user.getOrgId())-alarmCache.getOrgManInAlarmNum(String.valueOf(user.getOrgId()));
		logger.info("机构报警数================="+inNum);
		return inNum;
		
		
	}
	

	/**
	 * 推送消息 报警数===报警总数--人工处理数 子子孙孙
	 * 1：查出子子孙子机构
	 * 2：遍历子子孙孙查询中控室
	 */
	public int findNumByRoomCode(List<User> users) {
		int inNum =0;
		Set<String> set = new HashSet<String>();
		for(User  r1: users){
			
			List<String> orgList = orgDao.getChildList(r1.getOrgId());
			for(String s : orgList){
				String [] str = s.split(",");
				for(String ss :str){
					
					//过滤企业机构--根据企业id查报警数
					Org org = orgDao.findUserByOrgId(Integer.valueOf(ss));
					if(org.getParentId() == 0){
						continue;
					}
					//根据机构查中控室
					List<OrgControlRoom> rs = orgControlRoomDao.getRoomsByOrgId(Integer.valueOf(ss));
					for(OrgControlRoom r : rs){
						set.add(r.getRoomCode());
					}
				}
			}
		}
		
		Iterator<String> it = set.iterator();  
		while (it.hasNext()) {  
			String str = it.next(); 
			inNum += alarmCache.getControlRoomInAlarm(str)-alarmCache.getControlRoomManInAlarm(str);
		}
		return inNum;
	}
	
	public int findSuspectedNumByRoomCode(List<User> users) {
		int inNum =0;
		Set<String> set = new HashSet<String>();
		for(User  r1: users){
			
			List<String> orgList = orgDao.getChildList(r1.getOrgId());
			for(String s : orgList){
				String [] str = s.split(",");
				for(String ss :str){
					
					//过滤企业机构--根据企业id查报警数
					Org org = orgDao.findUserByOrgId(Integer.valueOf(ss));
					if(org.getParentId() == 0){
						continue;
					}
					//根据机构查中控室
					List<OrgControlRoom> rs = orgControlRoomDao.getRoomsByOrgId(Integer.valueOf(ss));
					for(OrgControlRoom r : rs){
						set.add(r.getRoomCode());
					}
				}
			}
		}
		
		Iterator<String> it = set.iterator();  
		while (it.hasNext()) {  
			String str = it.next(); 
			inNum += alarmCache.getControlRoomInSuspectAlarm(str);
		}
		return inNum;
	}
	
	
}
