package org.formation.model.stateful;

public class Fire {

	private Room room;

	public Fire(Room room) {
		this.room = room;
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Fire [room=" + room + "]";
	}
	
	
}
