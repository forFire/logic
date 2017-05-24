/**
 * 
 */
package com.capcare.harbor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fss
 * 
 */
@Entity
@Table(name = "us_pet_sport")
public class PetSport implements Serializable {

	private static final long serialVersionUID = -3261367063305632658L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "f_id")
	private Long id;

	@Column(name = "f_device_sn")
	private String deviceSn;

	@Column(name = "f_receive")
	private Long receive;
	
	@Column(name = "f_systime")
	private Long sysTime;

	@Column(name = "f_steps")
	private Integer steps = 0;
	
	@Column(name = "f_speed")
	private Double speed;
	
	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Long getReceive() {
		return receive;
	}

	public void setReceive(Long receive) {
		this.receive = receive;
	}

	public Long getSysTime() {
		return sysTime;
	}

	public void setSysTime(Long sysTime) {
		this.sysTime = sysTime;
	}

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PetSport[");
		sb.append("sn=");
		sb.append(getDeviceSn());
		sb.append(",receive=");
		sb.append(getReceive());
		sb.append(",systime=");
		sb.append(getSysTime());
		sb.append(",speed=");
		sb.append(getSpeed());
		sb.append(",steps=");
		sb.append(getSteps());
		sb.append("]");
		return sb.toString();
	}
}
