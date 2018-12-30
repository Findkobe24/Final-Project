package main.java.BattleField;

import main.java.Creature.Creature;

public class Cell {
	private Creature creature;
	
	public Cell() {
		creature = null;
	}

	public boolean isEmpty() {
		return creature == null;
	}
	
	public void clear() {
		if (creature == null)
			return;
		creature = null;
	}
	
	public boolean placeCreature(Creature creature) {
		if (this.creature != null) {
			return false;
		}
		this.creature = creature;
		return true;
	}
	
	public Creature getCreature() {
		return creature;
	}
	
	public int getCreaturePosX() {
		if (creature == null) {
			return -1;
		}
		return creature.getPosX();
	}
	
	public int getCreaturePosY() {
		if (creature == null) {
			return -1;
		}
		return creature.getPosY();
	}
	
	public static void main(String[] args) {
		
	}

}
