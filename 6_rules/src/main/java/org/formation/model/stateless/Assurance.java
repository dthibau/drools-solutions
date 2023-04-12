package org.formation.model.stateless;

import java.io.Serializable;

public class Assurance implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double prixBase;
	private Double remise = 0d;
	
	
	public Double getPrixBase() {
		return prixBase;
	}
	public void setPrixBase(Double prixBase) {
		this.prixBase = prixBase;
	}
	public Double getRemise() {
		return remise;
	}
	public void setRemise(Double remise) {
		this.remise = remise;
	}
	
	public double getPrix() {
		return prixBase != null ? prixBase - remise*prixBase/100 : -1;
	}
	
}
