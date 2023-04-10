package org.formation.model;

public class Driver {

	private int age, incidents, seniority;

	public Driver(int age, int seniority, int incidents ) {
		this.age = age;
		this.seniority = seniority;
		this.incidents = incidents;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getIncidents() {
		return incidents;
	}

	public void setIncidents(int incidents) {
		this.incidents = incidents;
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}
	
}
