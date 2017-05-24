package com.capcare.harbor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "sys_org")
public class Org extends IdEntity implements Serializable {

	@Column(name = "f_name")
	private String name;
	@Column(name = "f_address")
	private String address;
	@Column(name = "f_memo")
	private String memo;
	@Column(name = "f_status")
	private Integer status;
	@Column(name = "f_parent_id")
	private Integer parentId;
	@Column(name = "f_creator")
	private Integer creator;
	@Column(name = "f_create_time")
	private Date createTime;
	
	@Transient
	private String controlRoomNames;

	public Org() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getControlRoomNames() {
		return controlRoomNames;
	}

	public void setControlRoomNames(String controlRoomNames) {
		this.controlRoomNames = controlRoomNames;
	}

}
