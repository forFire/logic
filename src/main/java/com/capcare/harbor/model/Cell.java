package com.capcare.harbor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "cellinfo")
@IdClass(value = CellId.class)
public class Cell implements Serializable {

	private static final long serialVersionUID = -2639680569543322880L;

	@Id
	@Column(name = "mnc")
	private int mnc;

	@Id
	@Column(name = "lac")
	private int lac;

	@Id
	@Column(name = "ci")
	private int ci;
	
	@Column(name = "lat")
	private double lat;
	
	@Column(name = "lon")
	private double lng;

	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
