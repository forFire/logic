package com.capcare.harbor.dao;

import java.util.ArrayList;
import java.util.List;

import module.orm.BaseSqlDao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capcare.harbor.model.Spot;
import com.capcare.harbor.util.DBUtils;
import com.capcare.harbor.util.Helper;
import com.capcare.harbor.util.TableUtils;

@Repository
public class SpotDao  extends BaseSqlDao<Spot, Long> {

	private Logger log = LoggerFactory.getLogger(getClass());

	private String tableName;

    @Autowired
    public SpotDao  (@Qualifier("sessionFactoryMaster") SessionFactory session) {

        super ();
        this.setSessionFactorySpot (session);
        this.tableName = TableUtils.getName(Spot.class);
    }


	private String getTableName(String deviceSn) {
		String tableName = this.tableName;
		if (Helper.isDividedTable()) {// 分表
			try {
				tableName += DBUtils.getSpotTableName(deviceSn);
			} catch (Exception e) {
				log.error(" divide table failed !!! ", e);
			}
		}
		return tableName;

	}

	@Transactional
	public void saveOrUpdate(Spot en) {
		if (!Helper.isDividedTable()) {
			super.saveOrUpdate(en);
		} else {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO  ")
						.append(this.getTableName(en.getDeviceSn()))
						.append(" ( f_device_sn , f_receive , f_direction , f_distance , f_info , f_lat ,")
						.append(" f_lng , f_mode , f_speed , f_stayed, f_systime , f_acc , f_rfid , f_cell )")
						.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
						.append(" ON DUPLICATE KEY UPDATE ")
						.append(" f_direction = ?, f_distance = ?, f_info = ?, f_lat = ?,")
						.append(" f_lng = ?, f_mode = ?, f_speed = ?, f_stayed= ?, f_systime = ?, f_acc = ?, f_rfid = ?, f_cell = ?");

				List<Object> ls = new ArrayList<Object>();
				ls.add(en.getDeviceSn());
				ls.add(en.getReceive());
				ls.add(en.getDirection());
				ls.add(en.getDistance());
				ls.add(en.getInfo());
				ls.add(en.getLat());
				ls.add(en.getLng());
				ls.add(en.getMode());
				ls.add(en.getSpeed());
				ls.add(en.getStayed());
				ls.add(en.getSystime());
				ls.add(en.getAccMode());
				ls.add(en.getMode433());
				ls.add(en.getCell());

				ls.add(en.getDirection());
				ls.add(en.getDistance());
				ls.add(en.getInfo());
				ls.add(en.getLat());
				ls.add(en.getLng());
				ls.add(en.getMode());
				ls.add(en.getSpeed());
				ls.add(en.getStayed());
				ls.add(en.getSystime());
				ls.add(en.getAccMode());
				ls.add(en.getMode433());
				ls.add(en.getCell());

				save(sb.toString(), ls);

			} catch (Exception e) {
				log.error(" sql insert failed !!! ", e);
			}

		}
	}

	@Transactional
	public void updateStayTime(int stayed, String deviceSn, Long time) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" UPDATE ");
			sb.append(getTableName(deviceSn));
			sb.append(" s SET s.f_stayed=? WHERE s.f_device_sn =? and s.f_receive=? ");

			List<Object> ls = new ArrayList<Object>();
			ls.add(stayed);
			ls.add(deviceSn);
			ls.add(time);

			save(sb.toString(), ls);

		} catch (Exception e) {
			log.error(" sql update failed !!! ", e);
		}

	}

	public List<Spot> listByTime(String deviceSn, long start) {

		StringBuilder sb = new StringBuilder();
		sb.append(" select * from ");
		sb.append(getTableName(deviceSn));
		sb.append(" s where s.f_device_sn=? and s.f_receive>? order by s.f_receive asc ");

		List<Object> ls = new ArrayList<Object>();

		return query(sb.toString(), ls);
	}
}
