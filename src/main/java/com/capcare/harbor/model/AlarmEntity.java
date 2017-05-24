package com.capcare.harbor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "b_fire_alarm")
public class AlarmEntity extends IdEntity implements Serializable {

	private static final long serialVersionUID = 8917163414864536685L;

	@Column(name = "f_alarm_time")
	private Long time;
	
	@Column(name = "f_create_time")
	private Long createTime;

	@Column(name = "f_type")
	private Integer type;// 告警类型

	@Column(name = "f_status_person")
	private int statusPerson = 0;

	@Column(name = "f_status_system")
	private int statusSystem = 1;

	@Column(name = "f_status")
	private int status = 0;

	@Column(name = "f_sn")
	private String sn;

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getStatusPerson() {
		return statusPerson;
	}

	public void setStatusPerson(int statusPerson) {
		this.statusPerson = statusPerson;
	}

	public int getStatusSystem() {
		return statusSystem;
	}

	public void setStatusSystem(int statusSystem) {
		this.statusSystem = statusSystem;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	// ************************************************
	
	
}
