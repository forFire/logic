package com.capcare.harbor.dao;

import java.util.List;

import module.orm.BaseDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.capcare.harbor.model.OrgControlRoom;

@Repository
public class OrgControlRoomDao extends BaseDao<OrgControlRoom, Long> {

	@Autowired
	public OrgControlRoomDao(@Qualifier("sessionFactoryMaster") SessionFactory session) {

		super();
		this.setSessionFactory(session);
		this.setSessionFactoryMaster(session);
	}
	
	/**
	 *根据中控室编码查企业 
	 */
	public List<OrgControlRoom> getOrgSByControRoomCode(String roomCode) {
		StringBuffer hql = new StringBuffer(" From OrgControlRoom room where 1=1 and roomCode = ? ");
		return find(hql.toString(),roomCode);
		
	}
	
	
	/**
	 *根据OrgId查询企业
	 */
	public List<OrgControlRoom> getRoomsByOrgId(Integer orgId) {
		StringBuffer hql = new StringBuffer(" From OrgControlRoom room where 1=1 and orgId = ? ");
		return find(hql.toString(),orgId);
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<OrgControlRoom> getRoomSByControRoomCode(String roomCode) {
		StringBuffer hql = new StringBuffer(" select f_room_code roomCode,f_org_id orgId,f_id id from sys_org_control_room  where f_org_id in (select f_org_id from sys_org_control_room where f_room_code = '"+roomCode+"')") ;
	
		Query query = getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.aliasToBean(OrgControlRoom.class));
		return query.list();
	}

	
}
