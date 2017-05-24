package com.capcare.harbor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 指挥机gps信息
 */
@Entity
@Table(name = "rd_conductor_gpsdata")
public class GPSData implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4932981310134314989L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "accmode")
	private String accMode;//类型
	
	@Column(name = "satelliteno")
	private String satellite_no;//卫星数

	//get set
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccMode() {
		return accMode;
	}

	public void setAccMode(String accMode) {
		this.accMode = accMode;
	}

	public String getSatellite_no() {
		return satellite_no;
	}

	public void setSatellite_no(String satellite_no) {
		this.satellite_no = satellite_no;
	}
}
