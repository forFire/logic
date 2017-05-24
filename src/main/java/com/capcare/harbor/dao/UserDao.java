package com.capcare.harbor.dao;

import java.util.List;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.capcare.harbor.model.User;

@Repository
public class UserDao extends BaseDao<User, Long> {
//	private static Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	public UserDao(@Qualifier("sessionFactoryMaster") SessionFactory session) {

		super();
		this.setSessionFactory(session);
		this.setSessionFactoryMaster(session);
	}
	
	//根据orgId查用户
	public List<User> findUserByOrgId(Integer orgId){
		String hql = "FROM User WHERE orgId=? ";
		List<User> list = find(hql.toString(),orgId);
		return list;
	}
	
	/**
	 *直接挂在企业下面的用户可以处理室外报警
	 */
	public List<User> findDealAlarmByOrgId(Integer orgId){
//		Map<String,Object> params = new HashMap<String,Object>();
		String hql = "FROM User WHERE orgId =?";
		return find(hql.toString(),orgId);
	}
	
	
	public User findUserByUserId(Integer Id){
		String hql = "FROM User WHERE id=?";
		List<User> list =  find(hql, Id);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
}
