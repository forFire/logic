package com.capcare.harbor.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.capcare.harbor.model.Device;
import com.capcare.harbor.model.FireAlarmEntity;
import com.capcare.harbor.protocol.FireAlarm;

@Repository
public class SpatialDao {

	private static Logger logger = LoggerFactory.getLogger(SpatialDao.class);
	
	@Resource
	@Qualifier("sessionFactoryMaster")
	private SessionFactory sessionFactory;
	
	/*public List<Map<String,Object>> queryBuffByDeviceId(Device device,boolean flag){
		String sql = "select * from ("+
					"select ST_IsEmpty(ST_Intersection(ST_Buffer(point("+device.getLng()+","+device.getLat()+"),"+(device.getRadius()!=null?device.getRadius():10)*1000+"),point(f_lng,f_lat))) f_points,f_sn as sn from ("+
					"select a.*,CASE WHEN b.f_sn is null then 0 ELSE 1 END f_alarm_flag from (SELECT * from b_device b where b.f_lat is not null and b.f_lng is not null and b.f_layer = (SELECT f_layer from b_device  WHERE f_sn = '"+device.getSn()+"') and f_type not in ('131','70','132','133')) a "+
					"left join "+
					"(select f_sn from b_fire_alarm where f_sn in ("+
					"select f_sn from b_device device where device.f_layer = (SELECT f_layer from b_device  WHERE f_sn = '"+device.getSn()+"')) and f_type = 'in002' group by f_sn ) b "+
					"on a.f_sn = b.f_sn "+
					") c where 1=1 ";
					if(flag){
						sql+=" and f_alarm_flag=1 ";
					}
					sql+=") d where 1=1 and f_points is not null";
					
		//List<BigInteger> list = 
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}*/
	
	public List<Map<String,Object>> queryBuffDevice(Device device){
		String sql = "select * from ("+
				"select ST_IsEmpty(ST_Intersection(ST_Buffer(point("+device.getLng()+","+device.getLat()+"),"+(device.getRadius()!=null?device.getRadius():10)*1000+"),point(f_lng,f_lat))) f_points,f_sn as sn from ("+
				"select a.*,CASE WHEN b.f_sn is null then 0 ELSE 1 END f_alarm_flag from (SELECT * from b_device b where b.f_lat is not null and b.f_lng is not null and b.f_layer = (SELECT f_layer from b_device  WHERE f_sn = '"+device.getSn()+"') and f_type not in ('131','70','132','133')) a "+
				"left join "+
				"(select f_sn from b_fire_alarm where f_sn in ("+
				"select f_sn from b_device device where device.f_layer = (SELECT f_layer from b_device  WHERE f_sn = '"+device.getSn()+"')) and f_type = 'in002' group by f_sn ) b "+
				"on a.f_sn = b.f_sn "+
				") c where 1=1 ) d where 1=1 and f_points is not null";					
		//List<BigInteger> list = 
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<Map<String,Object>> queryBuffAlarmDevice(Device device,FireAlarmEntity alarm){
		String sql = "select * from ("+
					"select ST_IsEmpty(ST_Intersection(ST_Buffer(point("+device.getLng()+","+device.getLat()+"),"+(device.getRadius()!=null?device.getRadius():10)*1000+"),point(f_lng,f_lat))) f_points,f_sn as sn from ("+
					"select a.*,CASE WHEN b.f_sn is null then 0 ELSE 1 END f_alarm_flag from (SELECT * from b_device b where b.f_lat is not null and b.f_lng is not null and b.f_layer = (SELECT f_layer from b_device  WHERE f_sn = '"+device.getSn()+"') and f_type not in ('131','70','132','133')) a "+
					"left join "+
					"(select f_sn from b_fire_alarm where f_sn in ("+
					"select f_sn from b_device device where device.f_layer = (SELECT f_layer from b_device  WHERE f_sn = '"+device.getSn()+"')) and f_type = 'in002' and f_status =0 ";
					
					Integer bufferTime = device.getBufferTime();
					if(bufferTime!=null){
						if(bufferTime>0){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date alarmTime = alarm.getAlarmTime();
							sql+=" and TIMESTAMPDIFF(SECOND,'"+sdf.format(alarmTime)+"',DATE_FORMAT(f_alarm_time,'%Y-%m-%d %H:%i:%s'))< "+bufferTime*60 +" ";
						}
					} 
					sql+=" group by f_sn ) b on a.f_sn = b.f_sn ) c where 1=1 and f_alarm_flag=1 ) d where 1=1 and f_points is not null";
					
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
}
