package main.java.BattleField;

import main.java.Creature.Creature;

public class Field {
	Cell[][] cells;
	
	public Field() {
		cells = null;
	}
	
	public Field(int lenX, int lenY) {
		cells = new Cell[lenY][];
		for (int i = 0; i < lenY; i++) {
			cells[i] = new Cell[lenX];
		}
		for (int row = 0; row < lenY; row++) {
			for (int column = 0; column < lenX; column++) {
				cells[row][column] = new Cell();
			}
		}
	}
	
	public int getLenX() {
		if (cells == null) {
			return 0;
		}
		return cells[0].length;
	}
	
	public int getLenY() {
		return cells.length;
	}
	
	public void clear(int posX, int posY) {
		cells[posY][posX].clear();
	}
	
	public boolean isEmpty(int posX, int posY) {
		if (posX < 0 || posY < 0 || posX >= getLenX() || posY >= getLenY()) {
			return false;
		}
		return cells[posY][posX].isEmpty();
	}
	
	public Creature getCreature(int posX, int posY) {
		if (posX < 0 || posX >= getLenX() || posY < 0 || posY >= getLenY()) {
			return null;
		}
		return cells[posY][posX].getCreature();
	}
	
	public void placeCreature(Creature creature) {
		if (creature.getPosX() < 0 || creature.getPosY() < 0 || creature.getPosX() >= getLenX() || creature.getPosY() >= getLenY()) {
			return;
		}
		cells[creature.getPosY()][creature.getPosX()].placeCreature(creature);
	}
	
	public void removeCreature(Creature creature) {
		if (creature.getPosX() < 0 || creature.getPosY() < 0 || creature.getPosX() >= getLenX() || creature.getPosY() >= getLenY()) {
			return;
		}
		cells[creature.getPosY()][creature.getPosX()].clear();
	}
	
	public void placeCreatures(Creature[] creatures) {
		int lenX = getLenX();
		int lenY = getLenY();
		for (int row = 0; row < lenY; row++) {
			for (int column = 0; column < lenX; column++) {
				cells[row][column].clear();;
			}
		}
		for (Creature creature : creatures) {
			if (creature.getPosX() < 0 || creature.getPosY() < 0 || creature.getPosX() >= getLenX() || creature.getPosY() >= getLenY()) {
				continue;
			}
			cells[creature.getPosY()][creature.getPosX()].placeCreature(creature);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
