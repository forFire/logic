package com.capcare.harbor.vo;


public enum InstructType {
	
	/** 设备自动上传 */
	AutoUp(0, "设备自动上传"),
	
	/** 设置围栏 */
	SetFence(1, "设置围栏"),

	/** 设置上传间隔 */
	SetTick(2, "设置上传间隔"),

	/** 超速 最大值-超速开/关 */
	SetSpeed(3, "超速 最大值-超速开/关"),

	/** 设置sos号码 */
	SetSos(4,"设置sos号码"),

	/** 重启 */
	Reboot(5,"重启"),

	/** 恢复出厂 */
	Restore(6, "恢复出厂"),

	/** 围栏开关 */
	SetFenceSwitch(7, "围栏开关"),
	
	/** 移动开关 */
	SetMoveSwitch(8, "移动开关"),
	
	/** 设置围栏状态 */
	SetFenceStatus(9, "设置围栏状态"),
	
	/**清除告警 */
	ClearAlarm(10, "清除告警");
	

	private int num;

	private String info;


	private InstructType(int num, String info) {
		this.num = num;
		this.info = info;
	}

	public int getNum() {
		return this.num;
	}

	public String getInfo() {
		return this.info;
	}

}
