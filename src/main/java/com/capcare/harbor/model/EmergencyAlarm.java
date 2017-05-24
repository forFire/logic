package com.capcare.harbor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *报警实体类 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "b_emergency_alarm")
public class EmergencyAlarm extends IdEntity implements Serializable {

	/**
	 *设备序列号 
	 */
	@Column(name = "f_sn")
	private String sn;
	
	/**
	 *缓冲池设备序列号 
	 */
	@Column(name = "f_cascade_sn")
	private String cascadeSn;
	
	/**
	 *报警时间 
	 */
	@Column(name = "f_deal_time")
	private Date dealTime;
	
	/**
	 * 报警创建时间 
	 */
	@Column(name = "f_create_time")
	private Date createTime;
	
	/**
	 * 处理状态
	 */
	@Column(name = "f_status")
	private Integer status;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCascadeSn() {
		return cascadeSn;
	}

	public void setCascadeSn(String cascadeSn) {
		this.cascadeSn = cascadeSn;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
