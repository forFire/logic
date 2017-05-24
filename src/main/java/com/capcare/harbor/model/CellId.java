package com.capcare.harbor.model;

import javax.persistence.Column;

public class CellId implements java.io.Serializable {

	private static final long serialVersionUID = -4039289320002077046L;

	@Column(name = "mnc")
	private int mnc;

	@Column(name = "lac")
	private int lac;
	
	@Column(name = "ci")
	private int ci;

	
	
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CellId) {
			CellId pk = (CellId) obj;
			if (this.mnc==pk.getMnc() && this.lac==pk.getLac() && this.ci==pk.getCi()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}


}
