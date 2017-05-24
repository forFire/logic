package com.capcare.harbor.service.location;

//经纬度转平面坐标model
public class PlaneCoordinate {

	private double x;// lng
	private double y; // lat

	/**
	 * ?
	 */
	public PlaneCoordinate() {
		super();
	}

	/**
	 * ?* @param x ?* @param y ?
	 */
	public PlaneCoordinate(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * ?* @return 获得 x ?
	 */
	public double getX() {
		return x;
	}

	/**
	 * ?* @param x 设置 x ?
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * ?* @return 获得 y ?
	 */
	public double getY() {
		return y;
	}

	/**
	 * ?* @param y 设置 y ?
	 */
	public void setY(double y) {
		this.y = y;
	}

}
