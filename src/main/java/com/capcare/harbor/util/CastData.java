package com.capcare.harbor.util;

/**
 * This Class Function Description:推送内容， 目前之定义类型、数量，需要其他内容，可进行添加扩展
 * @author   张万胜
 * @version  2016年5月13日 下午5:17:43
 * @since    JDK 1.7
 */
public class CastData {
	
	//推送类型：报警、隐患--Constants.CAST_DANGER;Constants.CAST_ALARM
	private String type;
	
	//数量
	private int num;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	
}
