/**
 * 
 */
package com.capcare.harbor.model;

import java.io.Serializable;

public class PetFence implements Serializable {

	private static final long serialVersionUID = 7604181064077690955L;
	// 围栏开关，默认关
	private Integer fenceSwitch = 2;
	// 报警状态，默认非报警状态0；1进围栏报警；2出围栏报警
	private Integer status = 0;

	public Integer getFenceSwitch() {
		return fenceSwitch;
	}

	public void setFenceSwitch(Integer fenceSwitch) {
		this.fenceSwitch = fenceSwitch;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

