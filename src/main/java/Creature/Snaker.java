package main.java.Creature;

import main.java.BattleField.Field;

public class Snaker extends Creature {
	public Snaker(Field field, String name, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, name, posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}
	
	public Snaker(Field field, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, "Snaker", posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}

	@Override 
	public boolean isGood() {
		return false;
	}

	public static void main(String[] args) {
	}

}
