package main.java.Creature;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import main.java.BattleField.Field;

public abstract class Creature implements Runnable{
	protected String name;
	protected int posX;
	protected int posY;
	protected int blood = 10;
	protected Field field;
	protected boolean isAlive;
	protected int regionStartX;
	protected int regionEndX;
	protected int regionStartY;
	protected int regionEndY;
	protected String filename = "";
	
	public Creature(Field field, String name, int posX, int posY, 
			int regionStartX, int regionEndX, int regionStartY, int regionEndY) {
		this.field = field;
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		isAlive = true;
		this.regionStartX = regionStartX;
		this.regionEndX = regionEndX;
		this.regionStartY = regionStartY;
		this.regionEndY = regionEndY;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public void run() {
		while (true) {
			Random random = new Random();
			int regionX = random.nextInt(regionEndX - regionStartX) + regionStartX;
			int regionY = random.nextInt(regionEndY - regionStartY) + regionStartY;
			synchronized (Field.class) {
				if (!field.isEmpty(regionX, regionY)) {
					continue;
				}
				field.removeCreature(this);
				posX = regionX;
				posY = regionY;
				savePosition();
				field.placeCreature(this);
				break;
			}
		}
	}
	
	public void savePosition() {
		if (filename.equals("")) {
			return;
		}
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true));
			printWriter.println(this.getName() + " " + this.getPosX() + " " + this.getPosY());
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AttackFlame attack() {
		Random random = new Random();
		int select = 0;
		if (isGood()) {
			select = random.nextInt(9);
		} else {
			select = random.nextInt(8);
		}
		if (select > 6) {
			AttackFlame flame;
			if (isGood()) {
				flame =  new AttackFlame(posX, posY, 1);
				
			} else {
				flame = new AttackFlame(posX, posY, -1);
			}
			try {
				PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true));
				printWriter.println("Flame" + " " + flame.getPosX() + " " + flame.getPosY() + " " + flame.getDirection());
				printWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return flame;
		}
		return null;
	}
	
	public abstract boolean isGood();
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void die() {
		isAlive = false;
	}
	
	public void decreaseBlood() {
		blood -= 10;
		if (blood <= 0) {
			die();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
