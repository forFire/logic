package com.capcare.harbor.protocol;



/**
 * @author capcare
 */
public enum AlarmType {
	
	/** 未识别报警  */
	UNDIFINE(-1, "未识别报警","UNDIFINE"),
	

	LPWRPE(2, "水压设备低电","fire002"),
	
	COVER(3, "遮挡报警","out001"),
	
	PRESSURE(4, "水压异常报警","fire001"),
	
	SLANT(5, "倾斜（移动）报警","out002"),

	
	LPWRCE(7, "井盖设备低电","out004"),

	OVERTIME(8, "蓝牙异常","out003"),
	
	
	OBDERROR(100, "OBD故障","OBD error");
	
	
	
	private int num;

	private String info;
	private String eninfo;

	private AlarmType(int num, String info,String eninfo) {
		this.num = num;
		this.info = info;
		this.eninfo=eninfo;
	}

	public int getNum() {
		return this.num;
	}

	public String getInfo() {
		return this.info;
	}

	public String getEninfo() {
		return eninfo;
	}
	
	public static AlarmType getByNum(int num){
		AlarmType[] types = AlarmType.values();
		for(AlarmType type : types){
			if(type.getNum() == num){
				return type;
			}
		}
		return UNDIFINE;
	}
}
