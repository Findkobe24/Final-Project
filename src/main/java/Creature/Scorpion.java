package main.java.Creature;

import main.java.BattleField.Field;

public class Scorpion extends Creature {
	public Scorpion(Field field, String name, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, name, posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}

	public Scorpion(Field field, int posX, int posY,
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, "Scorpion", posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}
	
	@Override 
	public boolean isGood() {
		return false;
	}
	
	public static void main(String[] args) {
		
	}

}
