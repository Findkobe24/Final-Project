package main.java.Creature;

import main.java.BattleField.Field;

public class LittleMonster extends Creature {
	public LittleMonster(Field field, String name, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, name, posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}

	public LittleMonster(Field field, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		super(field, "LittleMonster", posX, posY, regionStartX, regionEndX, regionStartY, regionEndY);
	}
	
	@Override 
	public boolean isGood() {
		return false;
	}
	
	public static void main(String[] args) {
		
	}

}
