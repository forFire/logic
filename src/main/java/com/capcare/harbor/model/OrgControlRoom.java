package com.capcare.harbor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "sys_org_control_room")
public class OrgControlRoom extends IdEntity implements Serializable {

	@Column(name = "f_org_id")
	private Integer orgId;
	@Column(name = "f_room_code")
	private String roomCode;

	public OrgControlRoom() {

	}
	
	public OrgControlRoom(Integer orgId,String roomCode){
		this.orgId = orgId;
		this.roomCode = roomCode;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	

}
