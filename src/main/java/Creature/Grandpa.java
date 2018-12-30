package main.java.Creature;

import main.java.BattleField.Field;

public class Grandpa extends Creature {
	public Grandpa(Field field, String name, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, name, posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}

	public Grandpa(Field field, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, "Grandpa", posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}
	
	@Override 
	public boolean isGood() {
		return true;
	}
	
	public static void main(String[] args) {
		
	}

}
