package org.formation.model.stateless;

import java.io.Serializable;

public class Conducteur implements Serializable {

	private static final long serialVersionUID = 1L;
	private int age;
	private int anciennete;
	private int incidents;
	
	public Conducteur() {
		super();
	}
	public Conducteur(int age, int anciennete, int incidents ) {
		this.age = age;
		this.anciennete = anciennete;
		this.incidents = incidents;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getAnciennete() {
		return anciennete;
	}
	public void setAnciennete(int anciennete) {
		this.anciennete = anciennete;
	}
	public int getIncidents() {
		return incidents;
	}
	public void setIncidents(int incidents) {
		this.incidents = incidents;
	}
	
	
}
