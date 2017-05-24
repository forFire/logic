package com.capcare.harbor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 采集设备字典表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dict_equipment")
public class DictEquipment extends IdEntity implements Serializable {

	/**
	 * 中控室编码
	 */
	@Column(name = "f_control_room_code")
	private String controlRoomcode;
	
	/**
	 * 采集设备名称
	 */
	@Column(name = "f_name")
	private String name;
	
	/**
	 * 设备编码
	 */
	@Column(name = "f_code")
	private String code;
	
	/**
	 * 备注
	 */
	@Column(name = "f_memo")
	private Integer memo;
	
	/**
	 * 类型
	 */
	@Column(name = "f_type")
	private Integer type;
	
	/**
	 * 最后通信时间
	 */
	@Column(name = "f_last_time")
	private Date lastTime;

	public String getControlRoomcode() {
		return controlRoomcode;
	}

	public void setControlRoomcode(String controlRoomcode) {
		this.controlRoomcode = controlRoomcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getMemo() {
		return memo;
	}

	public void setMemo(Integer memo) {
		this.memo = memo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
}
