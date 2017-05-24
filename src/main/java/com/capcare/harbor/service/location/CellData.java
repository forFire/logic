package com.capcare.harbor.service.location;

public class CellData {

	private int mnc;
	private int lac;
	private int cell;
	private Double lng;
	private Double lat;
	private double rssi;
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
	public int getCell() {
		return cell;
	}
	public void setCell(int cell) {
		this.cell = cell;
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
	public double getRssi() {
		return rssi;
	}
	public void setRssi(double rssi) {
		this.rssi = rssi;
	}

}
