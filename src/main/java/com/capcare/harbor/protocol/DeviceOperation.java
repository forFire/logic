/**
 * 
 */
package com.capcare.harbor.protocol;

import java.io.Serializable;

/**
 * @author zf
 */
public class DeviceOperation implements Serializable {

	private static final long serialVersionUID = 437948576038921614L;
	// 系统类型标志
	private String sysType;
	// 系统地址
	private String sysAddress;
	// 操作标志
	private String operationFlag;
	// 操作员编码
	private String operatorCode;
	// 时间
	private String time;
	// 设备编码=源地址+部件地址 所以复位是对编码包含源地址的设备进行复位
	private String primaryAddress;

	public String getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getSysAddress() {
		return sysAddress;
	}

	public void setSysAddress(String sysAddress) {
		this.sysAddress = sysAddress;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

}
