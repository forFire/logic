package com.capcare.harbor.dao;

import java.util.List;

import module.orm.BaseDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.capcare.harbor.model.Org;

@Repository
public class OrgDao extends BaseDao<Org, Long> {
	@Autowired
	public OrgDao(@Qualifier("sessionFactoryMaster") SessionFactory session) {

		super();
		this.setSessionFactory(session);
		this.setSessionFactoryMaster(session);
	}
	
	public Org findUserByOrgId(Integer orgId) {
		String hql = "FROM Org WHERE id=?";
		List<Org> list = find(hql.toString(), orgId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<String> getParentList(Integer orgId){
		StringBuffer sql = new StringBuffer(" SELECT  ");
		sql.append("  getParentList("+orgId+")");
		Query query = getSession().createSQLQuery(sql.toString());
		return query.list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<String> getChildList(Integer orgId){
		StringBuffer sql = new StringBuffer(" SELECT  ");
		sql.append("  getChildList("+orgId+")");
		Query query = getSession().createSQLQuery(sql.toString());
		return query.list();
	}
	
	
}
