package com.capcare.harbor.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "sys_user")
public class User extends IdEntity implements Serializable {

	@Column(name = "f_user_name")
	private String userName;
	@Column(name = "f_password")
	private String password;
	@Column(name = "f_real_name")
	private String realName;
	@Column(name = "f_sex")
	private Integer sex;
	@Column(name = "f_org_id")
	private Integer orgId;
	@Column(name = "f_phone")
	private String phone;
	@Column(name = "f_address")
	private String address;
	@Column(name = "f_memo")
	private String memo;
	@Column(name = "f_status")
	private Integer status;
	@Column(name = "f_creator")
	private Integer creator;
	@Column(name = "f_create_time")
	private Date createTime;
	@Column(name = "f_fire_switch")
	private Integer fireSwitch;
	@Column(name = "f_error_switch")
	private Integer errorSwitch;
	@Column(name = "f_danger_switch")
	private Integer dangerSwitch;
	@Column(name = "f_water_switch")
	private Integer waterSwitch;
	@Column(name = "f_app_switch")
	private Integer appSwitch;
	@Column(name = "f_type")
	private Integer type;	//0 企业用户，1来访用户
	@Transient
	private Integer appFlag; //app分配责任部门 1 有 0无
	@Transient
	private String groupNames; //访客  所属组
	
	@Transient
	private String token;

	@Transient
	private String orgName;
	
	
	@Transient
	private String orgAddress;

	@Transient
	private Integer meneId;
	
	@Transient
	private String meneName;
	
	@Transient
	private Integer groupId;
	
	public User() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Integer getFireSwitch() {
		return fireSwitch;
	}

	public void setFireSwitch(Integer fireSwitch) {
		this.fireSwitch = fireSwitch;
	}

	public Integer getErrorSwitch() {
		return errorSwitch;
	}

	public void setErrorSwitch(Integer errorSwitch) {
		this.errorSwitch = errorSwitch;
	}

	public Integer getDangerSwitch() {
		return dangerSwitch;
	}

	public void setDangerSwitch(Integer dangerSwitch) {
		this.dangerSwitch = dangerSwitch;
	}

	public Integer getWaterSwitch() {
		return waterSwitch;
	}

	public void setWaterSwitch(Integer waterSwitch) {
		this.waterSwitch = waterSwitch;
	}

	public Integer getAppSwitch() {
		return appSwitch;
	}

	public void setAppSwitch(Integer appSwitch) {
		this.appSwitch = appSwitch;
	}


	public Integer getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(Integer appFlag) {
		this.appFlag = appFlag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}


	public Integer getMeneId() {
		return meneId;
	}

	public void setMeneId(Integer meneId) {
		this.meneId = meneId;
	}

	public String getMeneName() {
		return meneName;
	}

	public void setMeneName(String meneName) {
		this.meneName = meneName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}


	
}
