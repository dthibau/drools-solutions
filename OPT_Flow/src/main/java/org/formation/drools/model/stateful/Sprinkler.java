package org.formation.drools.model.stateful;

public class Sprinkler {
	private Room room;
	private boolean on = false;
	public Sprinkler(Room room) {
		super();
		this.room = room;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	@Override
	public String toString() {
		return "Sprinkler on : "+on+", room: "+room;
	}
	
}
