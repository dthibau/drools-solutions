package org.formation.model.stateless;

public class Assurance {
	private Double prixBase;
	private Double remise = 0d;
	
	public Double getPrixBase() {
		return prixBase;
	}
	public void setPrixBase(Double prixBase) {
		this.prixBase = prixBase;
		System.out.println("Assurance prixBase = "+this.prixBase);
	}
	public Double getRemise() {
		return remise;
	}
	public void setRemise(Double remise) {
		this.remise = remise;
	}
	public double getPrix(){
		return prixBase != null ? prixBase - remise*prixBase/100 : -1;
	}
}
