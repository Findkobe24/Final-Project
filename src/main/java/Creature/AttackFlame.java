package main.java.Creature;


public class AttackFlame { 
	private int posX;
	private int posY;
	private int direction;
	
	public AttackFlame(int posX, int posY, int direction) {
		this.posX = posX;
		this.posY = posY;
		this.direction = direction;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void move() {
		if (direction > 0) {
			posX += 1;
		} else {
			posX -= 1;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
