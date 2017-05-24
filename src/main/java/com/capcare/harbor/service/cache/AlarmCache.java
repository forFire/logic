package com.capcare.harbor.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlarmCache {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	private static final String ALARM_IN = "alarm_in";
	private static final String SUSPECTED_ALARM_IN = "suspected_alarm_in";
	private static final String ALARM_OUT = "alarm_out";
	private static final String ALARM_LAYER = "layer_";
	private static final String ALARM_FLOOR = "floor_";
	private static final String ALARM_ROOM = "room_";
	private static final String ALARM_ORG = "org_";
	private static final String ALARM_LAYER_MAN = "layer_m_";
	private static final String ALARM_FLOOR_MAN = "floor_m_";
	private static final String ALARM_ROOM_MAN = "room_m_";
	private static final String ALARM_ORG_MAN = "org_m_";
	
	private static final String DANGER_IN ="danger_in_";
	private static final String DANGER_IN_TOTAL ="total";
	private static final String DANGER_IN_NO_ASSIGN ="noassign";
	private static final String DANGER_IN_ORG ="org_";
	private static final String DANGER_OUT ="danger_out";
	
	private static final String ALARM_TIME = "alarmTime";
	
	
	public void setAlarmTime(String sn, String date) {
		redisTemplate.opsForHash().put(ALARM_TIME, sn, date);
	}

	public String getAlarmTime(String sn) {
		Object obj = redisTemplate.opsForHash().get(ALARM_TIME, sn);
		if (obj != null) {
			return obj.toString();
		} else {
			return null;
		}

	}
	
	
	/**
	 * 室内增减报警
	 * @param layerId
	 * @param floorId
	 * @param roomCode
	 * @param orgId
	 * @param num  增加1 减少 -1
	 */
	public void incrementInAlarm(Integer layerId, Integer floorId, String roomCode, Integer orgId, int num){
//		redisTemplate.multi();
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_LAYER + layerId, num);
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_FLOOR + floorId, num);
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_ROOM + roomCode, num);
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_ORG + orgId, num);
//		redisTemplate.exec();
	}
	
	public void incrementInSuspectAlarm(Integer layerId, Integer floorId, String roomCode, Integer orgId, int num){
//		redisTemplate.multi();
		redisTemplate.opsForHash().increment(SUSPECTED_ALARM_IN, ALARM_LAYER + layerId, num);
		redisTemplate.opsForHash().increment(SUSPECTED_ALARM_IN, ALARM_FLOOR + floorId, num);
		redisTemplate.opsForHash().increment(SUSPECTED_ALARM_IN, ALARM_ROOM + roomCode, num);
		redisTemplate.opsForHash().increment(SUSPECTED_ALARM_IN, ALARM_ORG + orgId, num);
//		redisTemplate.exec();
	}
	
	/**
	 * 人工处理报警
	 * @param layerId
	 * @param floorId
	 * @param roomCode
	 * @param orgId
	 * @param num
	 */
	public void manDealInAlarm(Integer layerId, Integer floorId, String roomCode, Integer orgId, int num){
//		redisTemplate.multi();
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_LAYER_MAN + layerId, num);
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_FLOOR_MAN + floorId, num);
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_ROOM_MAN + roomCode, num);
		redisTemplate.opsForHash().increment(ALARM_IN, ALARM_ORG_MAN + orgId, num);
//		redisTemplate.exec();
	}
	
	/**
	 * 获得企业室外报警
	 * @param orgId
	 * @return
	 */
	
	public Integer getOrgInAlarmNum(Integer id){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_ORG + id);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	public Integer getOrgInSuspectedAlarmNum(Integer id){
		Object num = redisTemplate.opsForHash().get(SUSPECTED_ALARM_IN, ALARM_ORG + id);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	/**
	 * 获得企业室内人工处理报警
	 * 手机端显示的报警数==报警总数--人工处理报警数
	 * @param orgId
	 * @return
	 */
	public Integer getOrgManInAlarmNum(String id){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_ORG_MAN + id);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	public Integer getControlRoomManInAlarm(String code){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_ROOM_MAN + code);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	public Integer getFloorManInAlarm(String id){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_FLOOR_MAN + id);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	public Integer getFloorLayerManInAlarm(String id){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_LAYER_MAN + id);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	/**
	 * 获得企业室外报警
	 * @param orgId
	 * @return
	 */
	public Integer getOutAlarmNum(Integer orgId){
		Object num = redisTemplate.opsForHash().get(ALARM_OUT, String.valueOf(orgId));
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	
	
	public Integer getControlRoomInAlarm(String code){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_ROOM + code);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	public Integer getControlRoomInSuspectAlarm(String code){
		Object num = redisTemplate.opsForHash().get(SUSPECTED_ALARM_IN, ALARM_ROOM + code);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	public Integer getFloorInAlarm(Integer id){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_FLOOR + id);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	
	public Integer getFloorLayerInAlarm(Integer id){
		Object num = redisTemplate.opsForHash().get(ALARM_IN, ALARM_LAYER + id);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
	/**
	 * 增加室内隐患
	 * @param floorId
	 */
	public void incrementInDanger(Integer floorId){
//		redisTemplate.multi();
		redisTemplate.opsForHash().increment(DANGER_IN + floorId, DANGER_IN_TOTAL, 1);
		redisTemplate.opsForHash().increment(DANGER_IN + floorId, DANGER_IN_NO_ASSIGN, 1);
//		redisTemplate.exec();
	}
	/**
	 * 分配室内隐患
	 * @param floorId
	 * @param orgId
	 */
	public void assginInDanger(Integer floorId, Integer orgId){
//		redisTemplate.multi();
		redisTemplate.opsForHash().increment(DANGER_IN + floorId, DANGER_IN_NO_ASSIGN, -1);
		redisTemplate.opsForHash().increment(DANGER_IN + floorId, DANGER_IN_ORG + orgId, 1);
//		redisTemplate.exec();
	}
	/**
	 * 解决隐患
	 * @param floorId
	 * @param orgId
	 */
	public void dealInDanger(Integer floorId, Integer orgId){
//		redisTemplate.multi();
		redisTemplate.opsForHash().increment(DANGER_IN + floorId, DANGER_IN_TOTAL, -1);
		redisTemplate.opsForHash().increment(DANGER_IN + floorId, DANGER_IN_ORG + orgId, -1);
//		redisTemplate.exec();
	}
	/**
	 * 获得部门室内隐患数
	 * @param floorId
	 * @param orgId
	 * @param type 0没有指派权限， 1有指派权限
	 * @return
	 */
	public Integer getInTotalDanger(Integer floorId, Integer orgId, Integer type){
		Object noAssgin =null, orgNum=null;
		if(type == Constants.STATUS_0){
			noAssgin= redisTemplate.opsForHash().get(DANGER_IN + floorId, DANGER_IN_ORG + orgId);
			if(noAssgin == null)
				return 0;
			return Integer.parseInt(noAssgin.toString());
		}else if(type == Constants.STATUS_1){
			noAssgin = redisTemplate.opsForHash().get(DANGER_IN + floorId, DANGER_IN_NO_ASSIGN);
			orgNum= redisTemplate.opsForHash().get(DANGER_IN + floorId, DANGER_IN_ORG + orgId);
			int na = noAssgin ==null ?0:Integer.parseInt(noAssgin.toString());
			int on = orgNum ==null ?0:Integer.parseInt(orgNum.toString());
			return na +on;
		}
		return 0;
	}
	
	
	/**
	 * 增减室外报警
	 * @param orgId
	 * @param num
	 */
	public void incrementOutAlarm(String orgId, int num){
		redisTemplate.opsForHash().increment(ALARM_OUT, orgId, num);
	}
	
	
	/**
	 * 增减室外隐患
	 * @param orgId
	 * @param num
	 */
	public void incrementOutDanger(String orgId, int num){
		redisTemplate.opsForHash().increment(DANGER_OUT, orgId, num);
	}
	/**
	 * 获得室外隐患
	 * @param orgId
	 * @return
	 */
	public Integer getOutDangerNum(String orgId){
		Object num = redisTemplate.opsForHash().get(DANGER_OUT, orgId);
		if(num == null)
			return 0;
		return Integer.parseInt(num.toString());
	}
}
