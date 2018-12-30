package main.java.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.java.BattleField.Field;
import main.java.Creature.AttackFlame;
import main.java.Creature.Creature;
import main.java.Manipulator.Manipulator;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;


public class Main extends Application {
	private final int CREATURE_SIZE = 50;
	private final int WINDOW_WIDTH = 1200;
	private final int WINDOW_HEIGHT = 700;
	private int count = 0;
	private boolean start = false;
	private String filename = "record.txt";
	private PrintWriter printWriter = null;
	private boolean secondTime = false;
	private Manipulator manipulator;
	private Creature[] creatures;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			int columnAmount = WINDOW_WIDTH / CREATURE_SIZE;
		    int rowAmount = WINDOW_HEIGHT / CREATURE_SIZE;
		    for (int i = 0; i < columnAmount; i++) {
		        ColumnConstraints columnn = new ColumnConstraints(50);
		        root.getColumnConstraints().add(columnn);
		    }
		    for (int i = 0; i < rowAmount; i++) {
		        RowConstraints row = new RowConstraints(50);
		        root.getRowConstraints().add(row);
		    }
		    
			Image image = new Image("file:images/background.jpg", WINDOW_WIDTH, WINDOW_HEIGHT, false, true);
			BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, 
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
			root.setBackground(new Background(backgroundImage));
			Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Hulu Brothers");
			primaryStage.setResizable(false);
			
			manipulator = new Manipulator(4, columnAmount, rowAmount);
			manipulator.setLeftFormation(1);
			manipulator.setRightFormation(0);
			creatures = manipulator.getCreatures();
	
			for (Creature creature : creatures) {
				root.add(new ImageView(new Image("file:images/" + creature.getName().replaceAll("\\d+","") + ".png",
						CREATURE_SIZE, CREATURE_SIZE, false, true)), creature.getPosX(), creature.getPosY());
			}
			primaryStage.show();
	
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override 
				public void handle(KeyEvent e) {
					if ((e.getCode() != KeyCode.SPACE  && e.getCode() != KeyCode.L) || start == true) {
				        return;
				    } 
					if (e.getCode() == KeyCode.L) {
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Open Resource File");
						File file = fileChooser.showOpenDialog(primaryStage);
						filename = file.getPath();
						manipulator.setReplay(filename);
					} else {
						try {
							filename = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
							filename += ".txt";
							filename = "record/new_record/" + filename;
							manipulator.setFilename(filename);
							printWriter = new PrintWriter(new FileWriter(filename, true));
							for (Creature creature : creatures) {
								printWriter.println(creature.getName() + " " + creature.getPosX() + " " + creature.getPosY());
							}
							printWriter.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
					if (secondTime) {
						manipulator = new Manipulator(4, columnAmount, rowAmount);
						if (e.getCode() == KeyCode.L) {
							manipulator.setReplay(filename);
						} else {
							manipulator.setFilename(filename);
						}
						manipulator.setLeftFormation(1);
						manipulator.setRightFormation(0);
						creatures = manipulator.getCreatures();
						root.getChildren().clear(); 
						for (Creature creature : creatures) {
							root.add(new ImageView(new Image("file:images/" + creature.getName().replaceAll("\\d+","") + ".png",
									CREATURE_SIZE, CREATURE_SIZE, false, true)), creature.getPosX(), creature.getPosY());
						}
						primaryStage.show();
					}
					start = true;
					PauseTransition pause = new PauseTransition(Duration.seconds(0.05));
					pause.setOnFinished(event -> {
				        root.getChildren().clear(); 
				        count++;
				        if (count % 7 == 0) {
				        	manipulator.makeMovement();
				        }
				        manipulator.flameMove();
						for (Creature creature : creatures) {
							synchronized (Field.class) {
								if (!creature.isAlive())
									root.add(new ImageView(new Image("file:images/" + creature.getName().replaceAll("\\d+","") + "Die.png", 
											CREATURE_SIZE, CREATURE_SIZE, false, true)), creature.getPosX(), creature.getPosY());
								else 
									root.add(new ImageView(new Image("file:images/" + creature.getName().replaceAll("\\d+","") + ".png", 
											CREATURE_SIZE, CREATURE_SIZE, false, true)), creature.getPosX(), creature.getPosY());
							}
						}
						ArrayList<AttackFlame> flames = manipulator.getFlames();
						for (AttackFlame flame : flames) {
							if (flame.getDirection() > 0) {
								root.add(new ImageView(new Image("file:images/FlameGood.png", CREATURE_SIZE / 2, CREATURE_SIZE / 2, 
									false, true)), flame.getPosX(), flame.getPosY());
							} else {
								root.add(new ImageView(new Image("file:images/FlameBad.png", CREATURE_SIZE / 2, CREATURE_SIZE / 2, 
										false, true)), flame.getPosX(), flame.getPosY());
							}
						}
						primaryStage.show();
						
						boolean allGoodDead = true;
						boolean allBadDead = true;
						for (Creature creature : creatures) {
							if (creature.isAlive()) {
								if (creature.isGood()) {
									allGoodDead = false;
								} else {
									allBadDead = false;
								}
							}
						}
						if (!allGoodDead && !allBadDead) {
							pause.play();
						} else {
							root.getChildren().clear(); 
							for (Creature creature : creatures) {
								synchronized (Field.class) {
									if (!creature.isAlive())
										root.add(new ImageView(new Image("file:images/" + creature.getName().replaceAll("\\d+","") + "Die.png", 
												CREATURE_SIZE, CREATURE_SIZE, false, true)), creature.getPosX(), creature.getPosY());
									else 
										root.add(new ImageView(new Image("file:images/" + creature.getName().replaceAll("\\d+","") + ".png", 
												CREATURE_SIZE, CREATURE_SIZE, false, true)), creature.getPosX(), creature.getPosY());
								}
							}
							primaryStage.show();
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Game Over");
							alert.setHeaderText(null);
							if (allGoodDead) {
								alert.setContentText("Monsters Win....");
							} else {
								alert.setContentText("HuluBrothers Win !!!");
							}
							alert.show();
							start = false;
							secondTime = true;
						}
					});
				    pause.play();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
