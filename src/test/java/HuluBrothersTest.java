package test.java;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import main.java.Creature.Creature;
import main.java.Manipulator.Manipulator;
import main.java.BattleField.Field;

class SamePlaceException extends Exception {
	private static final long serialVersionUID = 1L;
	private int posX;
	private int posY;
	
	public SamePlaceException(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
}

public class HuluBrothersTest {
	@Test
	public void testMakeMovement() throws SamePlaceException, InterruptedException {
		Manipulator manipulator = new Manipulator(4, 24, 14);
		manipulator.setLeftFormation(1);
		manipulator.setRightFormation(0);
		Creature[] creatures = manipulator.getCreatures();
		for (int i = 0; i < 10; i++) {
			manipulator.makeMovement();
		}
		for (Creature creature : creatures) {
			for (Creature creature2 : creatures) {
				if (creature != creature2 && creature.getPosX() == creature2.getPosX() && 
						creature.getPosY() == creature2.getPosY()) {
					System.out.print(creature.getName() + " " + creature.getPosX() + " " + creature.getPosY());
					System.out.print(creature2.getName() + " " + creature2.getPosX() + " " + creature2.getPosY());
					throw new SamePlaceException(creature.getPosX(), creature.getPosY());
				}
			}
		}
	}
	
	@Test 
	public void testField() {
		Field field = new Field(24, 14);
		Manipulator manipulator = new Manipulator(4, 24, 14);
		manipulator.setLeftFormation(1);
		manipulator.setRightFormation(0);
		field.placeCreatures(manipulator.getCreatures());
		Creature[] creatures = manipulator.getCreatures();
		for (Creature creature : creatures) {
			assertEquals(false, field.isEmpty(creature.getPosX(), creature.getPosY()));
			field.clear(creature.getPosX(), creature.getPosY());
		}
		for (int i = 0; i < field.getLenX(); i++) {
			for (int j = 0; j < field.getLenY(); j++) {
				assertEquals(true, field.isEmpty(i, j));
			}
		}
	}
	
}















