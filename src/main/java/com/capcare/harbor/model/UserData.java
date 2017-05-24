package com.capcare.harbor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 指挥机数据
 */
@Entity
@Table(name = "rd_conductor_userdata")
public class UserData implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1013669179415468432L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "accmode")
	private String accMode;//类型
	
	@Column(name = "sn")
	private String conductor_sn;
	
	@Column(name = "address")
	private String conductor_addr;//地址
	
	@Column(name = "frequentness")
	private String frequentness;//频度
	
	@Column(name = "type")
	private String conductor_type;//类型
	
	@Column(name = "level")
	private String conductor_level;//通信等级
	
	@Column(name = "encryption")
	private String encryption;//是否加密
	
	@Column(name = "bean")
	private String conductor_bean;//入站波束
	
	@Column(name = "userno")
	private String user_no;//所辖用户数
	
	@Column(name = "create_date")
	private String create_date;
	
	//get set
	public String getAccMode() {
		return accMode;
	}
	public void setAccMode(String accMode) {
		this.accMode = accMode;
	}
	public String getConductor_sn() {
		return conductor_sn;
	}
	public void setConductor_sn(String conductor_sn) {
		this.conductor_sn = conductor_sn;
	}
	public String getConductor_addr() {
		return conductor_addr;
	}
	public void setConductor_addr(String conductor_addr) {
		this.conductor_addr = conductor_addr;
	}
	public String getFrequentness() {
		return frequentness;
	}
	public void setFrequentness(String frequentness) {
		this.frequentness = frequentness;
	}
	public String getConductor_type() {
		return conductor_type;
	}
	public void setConductor_type(String conductor_type) {
		this.conductor_type = conductor_type;
	}
	public String getConductor_level() {
		return conductor_level;
	}
	public void setConductor_level(String conductor_level) {
		this.conductor_level = conductor_level;
	}
	public String getEncryption() {
		return encryption;
	}
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	public String getConductor_bean() {
		return conductor_bean;
	}
	public void setConductor_bean(String conductor_bean) {
		this.conductor_bean = conductor_bean;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	
//	@Override
//    public Message createMessage (Session session) throws JMSException {
//
//        ObjectMessage objectMessage = session.createObjectMessage (this);
//        return objectMessage;
//    }

//    @Override
//    public String toString () {
//
//        StringBuilder sb = new StringBuilder ();
//        sb.append ("Position[");
//        sb.append ("sn=");
//        sb.append (getDeviceSn ());
//        sb.append (",time=");
//        sb.append (getReceive ());
//        sb.append (",lng=");
//        sb.append (getLng ());
//        sb.append (",lat=");
//        sb.append (getLat ());
//        sb.append (",speed=");
//        sb.append (getSpeed ());
//        sb.append (",direction=");
//        sb.append (getDirection ());
//        sb.append (",mode=");
//        sb.append (getMode ());
//        sb.append (",acc=");
//        sb.append (getAccMode ());
//        sb.append (",433=");
//        sb.append (getMode433 ());
//        sb.append (",battery=");
//        sb.append (getBattery ());
//        sb.append (",steps=");
//        sb.append (getSteps ());
//        sb.append (",cell=");
//        sb.append (getCell ());
//        sb.append ("]");
//        return sb.toString ();
//    }
}
