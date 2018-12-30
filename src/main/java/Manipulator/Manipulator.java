package main.java.Manipulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
import main.java.BattleField.Field;
import main.java.Creature.*;
import javafx.util.Pair;

/*
 * 保存的数据的格式：
 * 
 */
public class Manipulator {
	Creature[] creatures;
	ArrayList<AttackFlame> flames;
	Field field;
	boolean isReplay = false;
	String filename;
	ArrayList<Pair<String, Pair<Integer, Integer>>> record = new ArrayList<Pair<String, Pair<Integer, Integer>>>();
	ArrayList<Pair<Pair<Integer, Integer>, Integer>> storedFlames = new ArrayList<>();
	
	public Manipulator(int littleMonstersNum, int fieldLenX, int fieldLenY) {
		field = new Field(fieldLenX, fieldLenY);
		creatures = new Creature[7 + 1 + 1 + 1 + littleMonstersNum];
		String[] colorOfHuluBrothers = {"Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Purple"};
		for (int i = 0; i < 7; i++) {
			creatures[i] = new HuluBrothers(field, colorOfHuluBrothers[i], -1, -1, 2, 6, 4, 11);
		}
		creatures[7] = new Grandpa(field, -1, -1, 2, 6, 4, 11);
		for (int i = 0; i < littleMonstersNum; i++) {
			creatures[8 + i] = new LittleMonster(field, "LittleMonster" + (8 + i), -1, -1, 18, 22, 4, 11);
		}
		creatures[8 + littleMonstersNum] = new Snaker(field, -1, -1, 18, 22, 4, 11);
		creatures[9 + littleMonstersNum] = new Scorpion(field, -1, -1, 18, 22, 4, 11);
		flames = new ArrayList<AttackFlame>();
	}
	
	public void setLeftFormation(int index) {
		// 雁行
		if (index == 0) {
			int startX = 7;
			int startY = (field.getLenY() - 8) / 2;
			for (int i = 0; i < 8; i++) {
				creatures[i].setPosition(startX - i, startY + i);
			}
		} else if (index == 1) { //锋矢
			int startY = (field.getLenY() - 6) / 2;
			creatures[0].setPosition(1, startY + 1);
			creatures[1].setPosition(3, startY + 1);
			for (int i = 2; i < 8; i++) {
				creatures[i].setPosition(2, startY + i - 2);
			}
		}
	}
	
	public void setRightFormation(int index) {
		if (index == 0) {
			int numberOfMonsters = creatures.length - 8;
			int startX = field.getLenX() - numberOfMonsters - 1;
			int startY = (field.getLenY() - numberOfMonsters) / 2;
			for (int i = 0; i < numberOfMonsters; i++) {
				creatures[8 + i].setPosition(startX + i, startY + i);
			}
		}
	}
	
	public Creature[] getCreatures() {
		return creatures;
	}
	
	public ArrayList<AttackFlame> getFlames() {
		return flames;
	}
	
	
	public void setReplay(String filename) {
		isReplay = true;
		setFilename(filename);
		Scanner in;
		try {
			in = new Scanner(new FileInputStream(filename));
			int count = 0;
			while (in.hasNext()) {
				String name = in.next();
				int posX = in.nextInt();
				int posY = in.nextInt();
				if (name.equals("Flame")) {
					storedFlames.add(new Pair<>(new Pair<>(posX, posY), in.nextInt()));
				} else {
					count++;
					record.add(new Pair<>(name, new Pair<>(posX, posY)));
				}
				if (count % creatures.length == 0) {
					storedFlames.add(new Pair<>(new Pair<>(0, 0), 1));
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Field getField() {
		return field;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
		for (Creature creature : creatures) {
			creature.setFilename(filename);
		}
	}
	
	public void makeMovement() {
		if (!isReplay) {
			for (Creature creature : creatures) {
				if (creature.isAlive()) {
					new Thread(creature).start();
					AttackFlame flame = creature.attack();
					if (flame != null) {
						flames.add(flame);
					}
				}
			}
		} else {
			for (int i = 0; i < creatures.length; i++) {
				if (record.size() == 0) {
					break;
				}
				String name = record.get(0).getKey();
				int posX = record.get(0).getValue().getKey();
				int posY = record.get(0).getValue().getValue();
				for (Creature creature : creatures) {
					if (creature.getName().equals(name)) {
						if (creature.isAlive()) {
							creature.setPosition(posX, posY);
							field.placeCreature(creature);
						}
						break;
					}
				}
				record.remove(0);
			}
			while (true) {
				if (storedFlames.size() == 0) {
					break;
				}
				if (storedFlames.get(0).getKey().getKey() == 0) {
					storedFlames.remove(0);
					break;
				}
				AttackFlame flame = new AttackFlame(storedFlames.get(0).getKey().getKey(), storedFlames.get(0).getKey().getValue(), storedFlames.get(0).getValue());
				flames.add(flame);
				storedFlames.remove(0);
			}
		}
	}
	
	public void flameMove() {
		for (int i = 0; i < flames.size(); i++) {
			flames.get(i).move();
			if (flames.get(i).getPosX() < 0 || flames.get(i).getPosY() < 3 || flames.get(i).getPosX() >= field.getLenX() 
					|| flames.get(i).getPosY() > 12) {
				flames.remove(i);
			}
		}
		for (int i = 0; i < flames.size(); i++) {
			int x = flames.get(i).getPosX();
			int y = flames.get(i).getPosY();
			synchronized (Field.class) {
				if (!field.isEmpty(x, y)) {
					if (flames.get(i).getDirection() < 0 && field.getCreature(x, y).isGood() || 
							flames.get(i).getDirection() > 0 && !field.getCreature(x, y).isGood()) {
						if (field.getCreature(x, y).isAlive()) {
							field.getCreature(x, y).decreaseBlood();
							flames.remove(i);
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
	}

}
