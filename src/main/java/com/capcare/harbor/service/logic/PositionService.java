package com.capcare.harbor.service.logic;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import module.util.DistanceUtil;
import module.util.NumUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.AlarmDao;
import com.capcare.harbor.dao.DeviceDao;
import com.capcare.harbor.dao.InstructDao;
import com.capcare.harbor.dao.SpotDao;
import com.capcare.harbor.model.Device;
import com.capcare.harbor.model.Spot;
import com.capcare.harbor.protocol.Alarm;
import com.capcare.harbor.protocol.AlarmType;
import com.capcare.harbor.protocol.Position;
import com.capcare.harbor.service.cache.DeviceCache;
import com.capcare.harbor.service.cache.PositionCache;
import com.capcare.harbor.service.location.LocationService;
import com.capcare.harbor.service.push.PushService;
import com.capcare.harbor.util.GeoMapUtil;

/**
 * @author fyq
 */
@Component
public class PositionService {

	private static Logger logger = LoggerFactory.getLogger(PositionService.class);

	/** 抖动 间隔 1000 * 60 * 1分钟 */
	public static final int INTERVAL = 60000;

	/** 抖动 阈值:0.000001 */
	public static final Double THRESHOLD = 0.000001;

	@Resource
	private SpotDao spotDao;
	@Resource
	private PushService pushService;
	@Resource
	private PositionCache positionCache;
	@Resource
	private InstructDao instructDao;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private AlarmDao alarmDao;
	@Resource
	private PetService petService;
	@Resource
	private AlarmService alarmService;
	@Resource
	private PolygonFenceService polygonFenceService;
	@Resource
	private DeviceCache deviceCache;
	@Resource
	private LocationService locationService;

	// ******************************************
	/**
	 * 上传点
	 */
	public void savePosition(Position inPosition) {

		logger.info("inPosition==========================>"+inPosition);
		
		if (inPosition == null) {
			return;
		}
		deviceDao.updateDeviceTime(inPosition.getDeviceSn(), new Date());

		Device device = deviceCache.getDevice(inPosition.getDeviceSn());
		if (device == null) {
			device = deviceDao.getDeviceBySn(inPosition.getDeviceSn());
			deviceCache.setDevice(inPosition.getDeviceSn(), device);
		}
		DecimalFormat df = new java.text.DecimalFormat("#.##");
		String cell = inPosition.getCell();// 460001253b6cda_0_0,253b6b0b_52_0,253b6adb_19_0@||000,||000,||000,||000,||000|0|163
		String str[] = cell.split("\\|\\|");
		String data = str[5];
		String strs[] = data.split("\\|");
		
		String voltage = strs[1];
		String batterPower = strs[2];
		
//		logger.info("strs[]=========================>"+strs[0]+strs[1]+strs[2]);
		logger.info("voltage=========================>"+voltage);
		logger.info("batterPower=========================>"+batterPower);
		logger.info("inPosition.isFlag()==========================>"+inPosition.isFlag());
		logger.info("position.getBattery()=========================>"+inPosition.getBattery());
		
		//井盖低电
		if (inPosition.getBattery() <= 30) {//电量待修改
			Alarm alarm = convertToAlarm(inPosition, AlarmType.LPWRCE);
			try {
				alarmService.saveAlarm(alarm);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		 
		 if(inPosition.isFlag()){
			
			//水压低电
			if (Integer.parseInt(batterPower) <= 30) {//电量待修改
				Alarm alarm = convertToAlarm(inPosition, AlarmType.LPWRPE);
				try {
					alarmService.saveAlarm(alarm);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		
		   
			//状态位 为 40 41 不报警
		
			double pressure = Double.parseDouble(voltage) < 20 ? Double.parseDouble(voltage) / 200 : Double.parseDouble(voltage) / 260;// 水压
			String pressureStr = df.format(pressure);
			deviceCache.setWaterPress(inPosition.getDeviceSn(), pressureStr);
		
			double max = device.getMax();
			double min = device.getMin();
			
			logger.info("device.getMin()==========================>"+device.getMin()+"---"+device.getMax()+"---"+pressureStr);
			
			logger.info("Double.parseDouble(pressureStr)=========================>"+Double.parseDouble(pressureStr));

			
			if (Double.parseDouble(pressureStr) > max || Double.parseDouble(pressureStr) < min) {
				Alarm alarm = convertToAlarm(inPosition, AlarmType.PRESSURE);
				try {
					alarmService.saveAlarm(alarm);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		
		}
		

	}

	public static void main(String[] args) {
		
		
	}
	
	
	// private void changeToOnline(Position lastPosition, Position inPosition){
	// lastPosition.setSystime(inPosition.getSystime());
	// lastPosition.setStayed(getStayTime(lastPosition, inPosition));
	// lastPosition.setMode433(inPosition.getMode433());
	// // 如果原来为离线状态，推送位置
	// if (2 == lastPosition.getStatus()) {
	// lastPosition.setStatus(1);
	// // pushService.pushPosition(lastPosition);
	// }
	// positionCache.setPosition(inPosition.getDeviceSn(), lastPosition);
	// }

	private double getDistance(Position uploadPosition, Position lastPosition) {
		double distanceAdd = DistanceUtil.distance(uploadPosition.getLng(), uploadPosition.getLat(), lastPosition.getLng(), lastPosition.getLat());// 里程
		return NumUtil.round(distanceAdd / 1000, 1);
	}

	// /**
	// * 计算累积停留时间
	// */
	// private int getStayTime(Position lastPosition, Position uploadPosition) {
	// int add = (int) ((uploadPosition.getReceive() -
	// lastPosition.getReceive()) / 1000);
	//
	// if (add < 0) {
	// add = 0;
	// }
	//
	// // return lastPosition.getStayed() + add;
	// return add;
	// }
	//
	// /**
	// * 判断是否为重复点
	// */
	// private boolean isRepeat(Position lastPosition, Position uploadPosition)
	// {
	//
	// double lastLng = lastPosition.getLng();
	// double lastLat = lastPosition.getLat();
	// double lng = uploadPosition.getLng();
	// double lat = uploadPosition.getLat();
	// boolean lngTag = Math.abs(lng - lastLng) < THRESHOLD;
	// boolean latTag = Math.abs(lat - lastLat) < THRESHOLD;
	// return lngTag && latTag;
	// }

	/** 保存轨迹:{上传的属性:Position转为Spot} */
	private Spot convertToSpot(Position position) {
		Spot spot = new Spot();
		spot.setDeviceSn(position.getDeviceSn());
		spot.setReceive(position.getReceive());
		spot.setLng(position.getLng());
		spot.setLat(position.getLat());
		spot.setInfo(position.getInfo());
		spot.setDirection(position.getDirection());
		spot.setSpeed(position.getSpeed());
		spot.setMode(position.getMode());
		spot.setSystime(position.getSystime());
		spot.setStayed(position.getStayed());
		spot.setCell(position.getCell());
		spot.setAccMode(position.getAccMode());
		spot.setMode433(position.getMode433());
		return spot;
	}

	public Alarm convertToAlarm(Position position, AlarmType alarmType) {
		Alarm alarm = new Alarm();
		alarm.setDeviceSn(position.getDeviceSn());
		alarm.setLat(position.getLat());
		alarm.setLng(position.getLng());
		alarm.setRead(2);
		alarm.setSpeed(position.getSpeed());
		alarm.setTime(position.getReceive());
		alarm.setSystime(position.getSystime());
		String addr = GeoMapUtil.getAddrByBaidu(alarm.getLng(), alarm.getLat());
		alarm.setAddr(addr);

		alarm.setType(alarmType.getNum());
		alarm.setInfo(alarmType.getInfo());
		alarm.setAccMode(position.getAccMode());
		alarm.setMode(position.getMode());
		alarm.setCell(position.getCell());
		alarm.setBattery(position.getBattery());
		alarm.setMode433(position.getMode433());

		return alarm;
	}

//	public static void main(String[] args) {
//		String cell = "460001253b6cda_0_0,253b6b0b_52_0,253b6adb_19_0@||000,||000,||000,||000,||000|21|163";
//		DecimalFormat df = new java.text.DecimalFormat("#.##");
//		String str[] = cell.split("\\|\\|");
//		String data = str[5];
//		String strs[] = data.split("\\|");
//		String voltage = strs[1];
//		double pressure = Double.parseDouble(voltage) < 20 ? Double.parseDouble(voltage) / 200 : Double.parseDouble(voltage) / 260;// 水压
//		String pressureStr = df.format(pressure);
//		System.out.println(pressureStr);
//	}
}
