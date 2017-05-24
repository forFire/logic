package com.capcare.harbor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 中控室实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dict_control_room")
public class DictControlRoom extends IdEntity implements Serializable {

	/**
	 * 中控室编码
	 */
	@Column(name = "f_code")
	private String code;
	/**
	 * 中控室名称
	 */
	@Column(name = "f_name")
	private String name;
	/**
	 * 所属企业
	 */
	@Column(name = "f_org_id")
	private Integer orgId;
	/**
	 * 中控室地址
	 */
	@Column(name = "f_address")
	private String address;
	/**
	 * 经度
	 */
	@Column(name = "f_lng")
	private Double lng;
	/**
	 * 纬度
	 */
	@Column(name = "f_lat")
	private Double lat;
	/**
	 * 状态
	 */
	@Column(name = "f_status")
	private Integer status;
	
	/**
	 * 设备最后通信时间
	 */
	@Column(name = "f_last_time")
	private Date lastTime;
	
	
	/**
	 * 机构名称 (非映射属性)
	 */
	@Transient
	private String orgName;
	/**
	 * 树级别标志 (非映射属性)
	 */
	@Transient
	private String orgLevel;
	
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public DictControlRoom() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	
	
}
