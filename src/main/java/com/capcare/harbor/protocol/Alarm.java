package com.capcare.harbor.protocol;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class Alarm implements MessageCreator, Serializable {

	private static final long serialVersionUID = -552307985513791681L;

	private Long id;

	private Long time;

	private Long systime;

	private Integer read;// 1已读 2未读

	private Integer type;// 告警类型

	private String info;

	private String addr = "";

	private String addr_en = "";

	private String deviceSn;

	private Double speed;

	/** 定位方式,如:AGPS:V GPS:A */
	private String mode;

	/** <ACC端口>的有效状态（1：已点火；0：未点火） */
	private int accMode;

	/** 方向 */
	private Float direction;

	private Double lng;

	private Double lat;

	private Integer battery;

	private String cell;
	
	private int mode433;
	// ************************************************
	
	public Integer getBattery() {
		return battery;
	}

	public int getMode433() {
		return mode433;
	}

	public void setMode433(int mode433) {
		this.mode433 = mode433;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Float getDirection() {
		return direction;
	}

	public void setDirection(Float direction) {
		this.direction = direction;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getAccMode() {
		return accMode;
	}

	public void setAccMode(int accMode) {
		this.accMode = accMode;
	}

	public Long getSystime() {
		return systime;
	}

	public void setSystime(Long systime) {
		this.systime = systime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRead() {
		return read;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getAddr_en() {
		return addr_en;
	}

	public void setAddr_en(String addr_en) {
		this.addr_en = addr_en;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		ObjectMessage objectMessage = session.createObjectMessage(this);
		return objectMessage;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Alarm[");
		sb.append("sn=");
		sb.append(getDeviceSn());
		sb.append(",time=");
		sb.append(getTime());
		sb.append(",info=");
		sb.append(getInfo());
		sb.append(",type=");
		sb.append(getType());
		sb.append(",lng=");
		sb.append(getLng());
		sb.append(",lat=");
		sb.append(getLat());
		sb.append(",speed=");
		sb.append(getSpeed());
		sb.append(",direction=");
		sb.append(getDirection());
		sb.append(",mode=");
		sb.append(getMode());
		sb.append(",433=");
		sb.append(getMode433());
		sb.append(",acc=");
		sb.append(getAccMode());
		sb.append(",cell=");
		sb.append(getCell());
		sb.append("]");
		return sb.toString();
	}
}
