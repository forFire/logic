/**
 * 
 */
package com.capcare.harbor.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zf 
 * @since 20160426
 * 消防设施部件状态---设备报警表
 */
@Entity
@Table(name = "b_fire_alarm")
public class FireAlarmEntity extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5750717378546904884L;

	// 部件地址(探测器编码)---设备编码
	@Column(name = "f_sn")
	private String sn;
	
	// 报警时间 yyyyMMddhhssmm
	@Column(name = "f_alarm_time")
	private Date alarmTime;

	// 入库时间
	@Column(name = "f_create_time")
	private Date createTime;

	//报警类型
	@Column(name = "f_type")
	private String type;

	// 人工状态
	@Column(name = "f_status_person")
	private Integer statusPerson;

	// 系统状态
	@Column(name = "f_status_system")
	private Integer statusSystem;
	
	// 状态
	@Column(name = "f_status")
	private Integer status;
	
	
	
	

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatusPerson() {
		return statusPerson;
	}

	public void setStatusPerson(Integer statusPerson) {
		this.statusPerson = statusPerson;
	}

	public Integer getStatusSystem() {
		return statusSystem;
	}

	public void setStatusSystem(Integer statusSystem) {
		this.statusSystem = statusSystem;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
	
}
