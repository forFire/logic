package com.capcare.harbor.vo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SupportedAlarm {

	private static Map<Integer, Set<Integer>> supportAlarm = new HashMap<Integer, Set<Integer>>();
	
	static{
		Set<Integer> carSet = new HashSet<Integer>();
		
		supportAlarm.put(1, carSet);
		
		Set<Integer> petSet = new HashSet<Integer>();
		
		supportAlarm.put(2, petSet);
		
		Set<Integer> personSet = new HashSet<Integer>();
		
		supportAlarm.put(3, personSet);
	}
	
	public static boolean isSupportAlarm(int deviceType, int alarmType){
		if(supportAlarm.containsKey(deviceType)){
			return supportAlarm.get(deviceType).contains(alarmType);
		}
		return false;
	}
}
