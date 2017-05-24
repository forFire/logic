package com.capcare.harbor.dao;

import module.orm.BaseDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.Cell;

@Component
@Scope("singleton")
public class CellDao extends BaseDao<Cell, String> {

    @Autowired
    public CellDao   (@Qualifier("sessionFactoryMaster") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
        this.setSessionFactoryMaster (session);
    }
	
	public Cell getCell(int mnc, int lac, int cellId) {
		String hql = "from Cell where mnc=? and lac=? and ci=?";
		return getHql(hql, mnc, lac, cellId);
	}
	
}
