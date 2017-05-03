package bman;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.AppGameContainer;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;
import jig.sat.resource.*;
import jig.Vector;

public class BombermanGame extends BasicGame {
	public final int ScreenWidth;
	public final int ScreenHeight;

	//path to images
	public static final String RIGHT_RSC = "resource/right.png";
	public static final String LEFT_RSC = "resource/left.png";
	public static final String BOMB_RSC = "resource/bomb.png";
	public static final String ENEMY_RSC = "resource/dragon.png";
	public static final String WALL_RSC = "resource/wall.png";
	public static final String FIRE_RSC = "resource/fire.png";
	public static final String BREAKABLEWALL_RSC = "resource/BreakableWall.png";
	public static final String ENEMY2_RSC = "resource/dragon2.png";
	public static final String ENEMY3_RSC = "resource/ghost.png";
	public static final String POWER_RSC = "resource/powerup.png";
	public static final String GAMEOVER_RSC = "resource/gameOver.png";
	public static final String ENEMY4_RSC = "resource/ghost.png";
	public static final String ENEMY5_RSC = "resource/ghost.png";
	public static final String BORDER_RSC = "resource/border.png";
	public static final String LEVEL2_RSC = "resource/Level2.png";
	public static final String VICTORY_RSC = "resource/victory.png";

	/*
	public static final String RIGHT_RSC = "/Bomberman/src/bman/resource/Right.png";
	public static final String LEFT_RSC = "/Bomberman/src/bman/resource/Left.png";
	public static final String BOMB_RSC = "/Bomberman/src/bman/resource/bomb.png";
	public static final String ENEMY_RSC = "/Bomberman/src/bman/resource/dragon.png";
	public static final String WALL_RSC = "/Bomberman/src/bman/resource/wall.png";
	public static final String FIRE_RSC = "/Bomberman/src/bman/resource/fire.png";
	public static final String BREAKABLEWALL_RSC = "/Bomberman/src/bman/resource/BreakableWall.png";
	public static final String ENEMY2_RSC = "/Bomberman/src/bman/resource/dragon2.png";
	public static final String ENEMY3_RSC = "/Bomberman/src/bman/resource/guy.png";
	public static final String POWER_RSC = "/Bomberman/src/bman/resource/powerup.png";
	public static final String GAMEOVER_RSC = "/Bomberman/src/bman/resource/gameOver.png";
	 */
	//public static final String BORDER_RSC = "/Bomberman/src/bman/resource/border.png";
	int checked = 0;
	int arrayPos = 0;
	int temp = 0;
	int init = 0;
	int gridX = 0;
	int gridY = 0;
	int cheap = 0;
	int cheap1 = 0;
	int powerPosition =0;
	int initial = 0;
	int pathHelp = 0;
	int enemyDead = 0;
	
	int enemy2Dead = 0;
	int enemy3Dead = 0;
	int enemy4Dead = 0;
	int enemy5Dead = 0;
	int level2Counter = 0;
	int bombPosition = -1;
	int powerCounter = 0;
	int enter = 0;
	int powerup = 1;
	int lives = 1;
	int powerOnce = 0;

	// Entities
	Bomberman bomberman;
	Bomb bomb;
	Enemy enemy;
	Fire fire;
	Fire fire2;
	Fire fire3;
	Fire fire4;
	Fire fire5;
	Border border;
	GameOver gameOver;
	Level2 level2;
	Enemy2 enemy2;
	Enemy3 enemy3;
	Enemy4 enemy4;
	Enemy5 enemy5;
	Victory victory;
	Powerup power;

	int special = 0;
	int special2 = 0;
	int special3 = 0;
	int special4 = 0;

	int fireCounter=0;
	int fire2Counter=0;
	int fire3Counter=0;
	int fire4Counter=0;
	int fireCounter5 =0;

	int fireCounter1=0;
	int fire2Counter2=0;
	int fire3Counter3=0;
	int fire4Counter4=0;

	int bwall=0;
	int bwall2=0;
	int bwall3=0;
	int bwall4=0;

	int livePrint=0;


	float curPos = 0;// bomberman.getY();
	float idealPos =0;
	int release=0;

	float curPos1 = 0;// bomberman.getY();
	float idealPos1 =0;
	int release1=0;

	float curPos2 = 0;// bomberman.getY();
	float idealPos2 =0;
	int release2=0;


	float curPos3 = 0;// bomberman.getY();
	float idealPos3 =0;
	int release3=0;


	int yes = 0;

	Wall wall;
	Wall wall2;
	Wall wall3;
	Wall wall4;
	Wall wall5;
	Wall wall6;
	Wall wall7;
	Wall wall8;
	Wall wall9;
	Wall wall10;
	Wall wall11;
	Wall wall12;

	int secondsPassed = 0;
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		public void run(){
			secondsPassed++;
			System.out.println(secondsPassed);
		}
	};

	int seconds = 0;
	Timer timer2 = new Timer();
	TimerTask task2 = new TimerTask() {
		public void run(){
			seconds++;
			//System.out.println(secondsPassed);
		}
	};
	BreakableWall BreakableWall;
	BreakableWall BreakableWall2;
	BreakableWall BreakableWall3;
	BreakableWall BreakableWall4;

	int bounces = 0;

	public BombermanGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		timer2.scheduleAtFixedRate(task2, 1000, 1000);

	}

	//unique enemy tracking (ghost)
	private void pathFinding4(Enemy3 enemy3) {
		if (enemy3.getY() > bomberman.getY()){
			enemy3.setVelocity(new Vector (.0f, -.10f));
		}
		if (enemy3.getY() == bomberman.getY()){
			enemy3.setVelocity(new Vector (.0f, .10f));
		}

		if (enemy3.getX() > bomberman.getX()){
			enemy3.setVelocity(new Vector (-.10f, .0f));
		}

		if (enemy3.getX()  < bomberman.getX()){
			enemy3.setVelocity(new Vector (.10f, .0f));
		}
		if (enemy3.getY() < bomberman.getY()){
			enemy3.setVelocity(new Vector (.0f, .10f));
		}
		if (seconds % 3 == 1 && enemy3.getY() > 50){
			enemy3.setVelocity(new Vector (.0f, -.10f));
		}
		if(enemy3.getX() > 900){
			enemy3.setVelocity(new Vector (-.10f, .0f));
		}
	}
	//unique enemy tracking (ghost)
	private void pathFinding5(Enemy5 enemy5) {
		if (enemy5.getY() > bomberman.getY()){
			enemy5.setVelocity(new Vector (.0f, -.10f));
		}
		if (enemy5.getY() == bomberman.getY()){
			enemy5.setVelocity(new Vector (.0f, .10f));
		}
		if (enemy5.getX() > bomberman.getX()){
			enemy5.setVelocity(new Vector (-.10f, .0f));
		}
		if (enemy5.getX()  < bomberman.getX()){
			enemy5.setVelocity(new Vector (.10f, .0f));
		}
		if (enemy5.getY() < bomberman.getY()){
			enemy5.setVelocity(new Vector (.0f, .10f));
		}
		if (seconds % 3 == 1 && enemy5.getY() > 50){
			enemy5.setVelocity(new Vector (.0f, -.10f));
		}
		if(enemy5.getX() > 900){
			enemy5.setVelocity(new Vector (-.050f, .0f));
		}
		if (seconds % 11
				== 0 && enemy5.getY() < 500){
			enemy5.setVelocity(new Vector (.0f, .100f));

		}
	}


	//ghost
	private void pathFinding4(Enemy4 enemy4) {

		if (enemy4.getY() > bomberman.getY()){
			enemy4.setVelocity(new Vector (.0f, -.10f));
		}

		if (enemy4.getY() < bomberman.getY()){
			enemy4.setVelocity(new Vector (.0f, .10f));
		}
		if (enemy4.getX()  < bomberman.getX()){
			enemy4.setVelocity(new Vector (.10f, .0f));

		}
		if (enemy4.getX() > bomberman.getX()){
			enemy4.setVelocity(new Vector (-.10f, .0f));
		}
		if (seconds % 3 == 2 && enemy4.getY() > 50){
			enemy4.setVelocity(new Vector (.0f, -.050f));
		}
		if (seconds % 5 == 0 && enemy4.getY() < 500){
			enemy4.setVelocity(new Vector (.0f, .150f));
		}
		if(enemy3.getX() > 900){
			enemy3.setVelocity(new Vector (-.10f, .0f));

		}
	}
	
	public void pathFinding3(Enemy4 enemy4 ){
		float yPos = bomberman.getY();
		float yBad = enemy2.getY();
		int up =0;
		int i = 0;
		if (yBad < yPos){
			while( i < 75){
				up = up -1;
				enemy4.setY(bomberman.getY() -10);
				i++;
			}}
		else if (yBad > yPos){
			while( i < 75){
				up = up +1;
				enemy4.setY(bomberman.getY()+10);
				i++;
			}
			return;
		}

	}


	public void pathFinding2(Enemy2 enemy2 ){
		float yPos = bomberman.getY();
		float yBad = enemy2.getY();

		int up =0;
		int i = 0;
		if (yBad < yPos){
			while( i < 75){
				up = up -1;
				enemy2.setY(bomberman.getY() -10);
				i++;
			}}
		else if (yBad > yPos){
			while( i < 75){
				up = up +1;
				enemy2.setY(bomberman.getY()+10);
				i++;
			}
			return;
		}}




	public void pathFinding1(Enemy enemy ){
		float xBad = enemy.getX();
		float yBad = enemy.getY();
		int up =0;
		int i = 0;
		enemy.setVelocity(new Vector (0f,-.1f));
		if ( yBad < 50 ){
			enemy.setVelocity(new Vector (.0f,.0f));
			enemy.setVelocity(new Vector (-.1f,.0f));
		}

		if ( xBad < 60  ){
			enemy.setVelocity(new Vector (.0f,.0f));
			enemy.setVelocity(new Vector (.0f,.1f));
		}

		if ( yBad > 520 ){
			enemy.setVelocity(new Vector (.0f,.0f));
			enemy.setVelocity(new Vector (.1f,0f));
		}
		if ( xBad > 900 ){
			enemy.setVelocity(new Vector (.0f,.0f));
			enemy.setVelocity(new Vector (.00f,-.10f));
			//i = 1;
		}
		i++;
		if (enemy.getCoarseGrainedMaxY() > ScreenHeight
				|| enemy.getCoarseGrainedMinY() < 0) {
			enemy.bounce(0);
			enemy.setVelocity(new Vector (.00f,0f));
			enemy.setY(51);
			enemy.setX(851);
		}
	}


	public void pathFinding_2(Enemy2 enemy22 ){
		float xBad = enemy22.getX();
		float yBad = enemy22.getY();
		enemy22.setVelocity(new Vector (0f,-.1f));

		if ( yBad < 50 ){
			enemy22.setVelocity(new Vector (.0f,.0f));
			enemy22.setVelocity(new Vector (.1f,.0f));
		}

		if ( xBad < 60  ){
			enemy22.setVelocity(new Vector (.0f,.0f));
			enemy22.setVelocity(new Vector (.0f,-.1f));
		}

		if ( yBad > 520 ){
			enemy22.setVelocity(new Vector (.0f,.0f));
			enemy22.setVelocity(new Vector (-.10f,.0f));
		}
		if ( xBad > 900 ){//good
			enemy22.setVelocity(new Vector (.0f,.0f));
			enemy22.setVelocity(new Vector (.00f,.10f));
			//i = 1;
		}


		if (enemy22.getCoarseGrainedMaxY() > ScreenHeight
				) {

			enemy22.bounce(0);
			enemy22.setVelocity(new Vector (.00f,0f));


			enemy22.setY(521);
			enemy22.setX(900);

		}
		if (enemy22.getX() < 10 
				) {

			enemy22.bounce(0);
			enemy22.setVelocity(new Vector (.00f,0f));


			enemy22.setY(520);
			enemy22.setX(59);

		}
		if( enemy22.getCoarseGrainedMinY() < 0){
			enemy22.bounce(0);
			enemy22.setVelocity(new Vector (.00f,0f));


			enemy22.setY(50);
			enemy22.setX(61);
		}

	}



	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		bomberman.render(g);
		enemy.render(g);
		enemy2.render(g);
		power.render(g);
		wall.render(g);
		wall2.render(g);
		wall3.render(g);
		wall4.render(g);
		wall5.render(g);
		wall6.render(g);
		wall7.render(g);
		wall8.render(g);
		wall9.render(g);
		wall10.render(g);
		wall11.render(g);
		wall12.render(g);
		fire.render(g);
		fire2.render(g);
		fire3.render(g);
		fire4.render(g);
		fire5.render(g);
		bomb.render(g);
		BreakableWall.render(g);
		BreakableWall2.render(g);
		BreakableWall3.render(g);
		BreakableWall4.render(g);
		enemy3.render(g);
		enemy4.render(g);
		enemy5.render(g);
		gameOver.render(g);
		level2.render(g);
		victory.render(g);
		border.render(g);
		g.setBackground( Color.lightGray);
		if (livePrint == 1){
			g.drawString("lives: "+ lives, 10, 30);
		}
	}
	@Override
	public void init(GameContainer container) throws SlickException {
		ResourceManager.loadImage(RIGHT_RSC);
		ResourceManager.loadImage(LEFT_RSC);
		ResourceManager.loadImage(BOMB_RSC);
		ResourceManager.loadImage(ENEMY_RSC);
		ResourceManager.loadImage(WALL_RSC);
		ResourceManager.loadImage(FIRE_RSC);
		ResourceManager.loadImage(BREAKABLEWALL_RSC);
		ResourceManager.loadImage(BORDER_RSC);
		ResourceManager.loadImage(ENEMY2_RSC);
		ResourceManager.loadImage(ENEMY3_RSC);
		ResourceManager.loadImage(POWER_RSC);
		ResourceManager.loadImage(GAMEOVER_RSC);
		ResourceManager.loadImage(ENEMY4_RSC);
		ResourceManager.loadImage(ENEMY5_RSC);
		ResourceManager.loadImage(VICTORY_RSC);
		ResourceManager.loadImage(LEVEL2_RSC);
		level2 = new Level2(500,  400, .1f, .2f);
		victory = new Victory(500,  400, .1f, .2f);
		victory.destroy();
		border = new Border(500,  400, .1f, .2f);
		level2.destroy();
		gameOver = new GameOver(500,  400, .1f, .2f);
		gameOver.destroy();
		bomberman = new Bomberman(100,  50, .1f, .2f);
		bomb = new Bomb(ScreenWidth/2 , ScreenHeight/2 , .1f, .2f);
		bomb.destroy();
		enemy = new Enemy(900 , 510 , .1f, .2f);
		enemy.setVelocity(new Vector (0f,.0f) );
		enemy4 = new Enemy4(900 , 510 , .1f, .2f);
		enemy.setVelocity(new Vector (0f,.0f) );
		enemy2 = new Enemy2(500 , 200, .1f, .2f);
		enemy2.setVelocity(new Vector (0f,.0f) );
		fire = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
		fire.destroy();
		enemy3 = new Enemy3(100 , 400, .1f, .2f);
		enemy5 = new Enemy5(100 , 400, .1f, .2f);
		enemy5.destroy();
		//	enemy3.setVelocity(new Vector (0f,-.10f) );
		fire2 = new Fire(ScreenWidth*2 , ScreenHeight*3 , .1f, .2f);
		fire2.destroy();
		fire3 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
		fire3.destroy();
		fire4 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
		fire4.destroy();
		fire5 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
		fire5.destroy();
		//border = new Border(400 , 285 , .1f, .2f);
		wall = new Wall(200 , 125 , .1f, .2f);
		wall2 = new Wall(200 , 275 , .1f, .2f);
		wall3 = new Wall(200 , 425, .1f, .2f);
		wall4 = new Wall(400 , 125 , .1f, .2f);
		wall5 = new Wall(400 , 275 , .1f, .2f);
		wall6 = new Wall(400 , 425 , .1f, .2f);
		wall7 = new Wall(600 , 125 , .1f, .2f);
		wall8 = new Wall(600 , 275 , .1f, .2f);
		wall9 = new Wall(600 , 425 , .1f, .2f);
		wall10 = new Wall(800 , 125 , .1f, .2f);
		wall11 = new Wall(800 , 275 , .1f, .2f);
		wall12 = new Wall(800 , 425 , .1f, .2f);
		power = new Powerup(300 , 210 , .1f, .2f);
		//BreakableWall = new BreakableWall(200 , 200 , .1f, .2f);
		//BreakableWall2 = new BreakableWall(300 , 125 , .1f, .2f);
		//BreakableWall3 = new BreakableWall(500 , 425 , .1f, .2f);
		//BreakableWall4 = new BreakableWall(600 , 350 , .1f, .2f);
		BreakableWall = new BreakableWall(300 , 200 , .1f, .2f);
		BreakableWall2 = new BreakableWall(300 , 350 , .1f, .2f);
		BreakableWall3 = new BreakableWall(700 , 200 , .1f, .2f);
		BreakableWall4 = new BreakableWall(700 , 350 , .1f, .2f);

	}







	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		
		System.out.println(bomberman.getX());
		System.out.println(bomberman.getY());
		
		if (bomberman.getY() == 50){
			if (bomberman.getX() == 100){
				arrayPos = 0;
				gridX = 0;
				gridY = 0;
				temp = 0;
			}
		}
		
		if (bomberman.getY() == 126){
			if (bomberman.getX() == 100){
				arrayPos = 7;
				gridX = 0;
				gridY = 1;
			}
		}
		if (bomberman.getY() == 202){
			if (bomberman.getX() == 100){
				arrayPos = 14;
				gridX = 0;
				gridY = 0;
			}
		}
		if (bomberman.getY() == 278){
			if (bomberman.getX() == 100){
				arrayPos = 21;
				gridX = 0;
				gridY = 1;
			}
		}
		if (bomberman.getY() == 354){
			if (bomberman.getX() == 100){
				arrayPos = 28;
				gridX = 0;
				gridY = 0;
			}
		}
		if (bomberman.getY() == 430){
			if (bomberman.getX() == 100){
				arrayPos = 35;
				gridX = 0;
				gridY = 1;
			}
		}
	
	

		Input input = container.getInput();
		if (enter == 0){
			initialState(input);
		}
		if (input.isKeyPressed(Input.KEY_EQUALS)) {
			enemyDead = 1;
			enemy2Dead = 1;
			enemy3Dead =1;
			enemy4Dead = 1;
			level2Counter = 1; 

		}

		if (enemyDead +enemy2Dead+enemy3Dead+enemy4Dead == 4 && level2Counter != 2 ){
			level2(input);
		}
		if (enemyDead +enemy2Dead+enemy3Dead+enemy4Dead+ enemyDead == 5 && level2Counter == 2){
			victory = new Victory(500,  400, .1f, .2f);

		}

		pathFinding_2(enemy2);
		pathFinding1(enemy);
		pathFinding4(enemy4);
		pathFinding4(enemy3);
		pathFinding5(enemy5);

		if (lives == 0){
			gameOver = new GameOver(500,  400, .1f, .2f);
			lives = 1;
			enter =0;
		}


		if(fireCounter == 1){
			fire.destroy();
		}
		if(fire2Counter == 1){
			fire2.destroy();
		}
		if(fire3Counter == 1){
			fire3.destroy();
		}
		if(fire4Counter == 1){
			fire4.destroy();
		}
		if(fireCounter5 == 1){
			fire5.destroy();
		}


		if ((gridX) == 0 ){
			// CONTROLLS! 
			if (input.isKeyPressed(Input.KEY_UP)) {
				input.consumeEvent();
				input.clearKeyPressedRecord();
				input.disableKeyRepeat();
				input.clearControlPressedRecord();
				float up = bomberman.getY();
				int i = 0;
				if (bomberman.getY() > 100){
					temp = (arrayPos -7);
					System.out.println("array =" + arrayPos);
					System.out.println("temp=" +temp);
					if ((temp != 16 || special == 1) && (temp != 20 || special2 ==1) && (temp != 30|| special3 ==1) && (temp != 34|| special4 ==1)&& (temp != bombPosition)){
						if (gridY == 0){
							gridY = 1;
						}
						else {
							gridY = 0;
						}
						//while( i < 75){
						//up = up -1;
						//	bomberman.setY((up-1));
						//i++;
						//}
						yes =1;
						up = up +1;
						bomberman.setVelocity(new Vector (.0f,-1f));
						input.consumeEvent();
						input.clearKeyPressedRecord();
						input.disableKeyRepeat();
						input.clearControlPressedRecord();
						if(release1 == 0){
							release1 =1;
							curPos1 = bomberman.getY();
							idealPos1 = bomberman.getY() -76;
							System.out.println(curPos1);
							System.out.println(idealPos1);
							curPos1 = bomberman.getY(); //+ i;
							System.out.println(curPos1);

						}
						arrayPos = arrayPos -7;
					}
				}
			}

			curPos1 = bomberman.getY(); //+ i;

			if((curPos1 < idealPos1) ){
				release1 = 0;
				bomberman.setVelocity(new Vector (.0f,0f));
				if (bomberman.getY() < 600){
					bomberman.setY(idealPos1);
				}
			}

			if (input.isKeyPressed(Input.KEY_DOWN)) {
				temp = arrayPos +7;
				input.consumeEvent();
				input.clearKeyPressedRecord();
				input.disableKeyRepeat();
				input.clearControlPressedRecord();
				if ((temp != 16 || special == 1) && (temp != 20 || special2 ==1) && (temp != 30|| special3 ==1) && (temp != 34|| special4 ==1) &&(temp != bombPosition) ){
					float down = bomberman.getY();
					int i = 0;
					if (bomberman.getY() < 500){
						if (gridY == 0){
							gridY = 1;
						}
						else {
							gridY = 0;
							//bomberman.setVelocity(new Vector (0, -01f));
						}


						yes =1;
						down = down +1;
						bomberman.setVelocity(new Vector (.0f,1f));
						if(release == 0){
							release =1;
							curPos = bomberman.getY();
							idealPos = bomberman.getY() +76;
							System.out.println(curPos);
							System.out.println(idealPos);
							curPos = bomberman.getY(); //+ i;
							System.out.println(curPos);


						}
						arrayPos = arrayPos +7;

					}

				}
			}
		}

		curPos = bomberman.getY(); //+ i;

		if((curPos > idealPos) ){
			release = 0;
			bomberman.setVelocity(new Vector (.0f,0f));
			if (bomberman.getY() > 50){
				bomberman.setY(idealPos);
			}
		}


		if ((gridY) == 0 ){

			if (input.isKeyPressed(Input.KEY_LEFT)) {
				input.clearKeyPressedRecord();

				temp = arrayPos -1;
				if ((temp != 16 || special == 1) && (temp != 20 || special2 ==1) && (temp != 30|| special3 ==1) && (temp != 34|| special4 ==1) &&(temp != bombPosition)){
					input.consumeEvent();
					input.clearKeyPressedRecord();
					input.disableKeyRepeat();
					input.clearControlPressedRecord();
					bomberman.destroy();
					bomberman.createLeft();
					float left = bomberman.getX();
					int i = 0;
					if (bomberman.getX() > 100){
						if (gridX == 0){
							gridX = 1;
						}
						else {
							gridX = 0;
						}
						//int move = bomberman.setX((left-1));

						while( i < 100){
							left = left -1;
							bomberman.setX((left-1));
							i++;
						}

						//		bomberman.setVelocity(new Vector (-3.5f,0f));
						//		if(release3 == 0){
						//			release3 =1;
						//			curPos3 = bomberman.getX();
						//			idealPos3 = bomberman.getX() - 101;
						//			System.out.println(curPos3);
						//			System.out.println(idealPos3);
						//			curPos3 = bomberman.getX(); //+ i;
						//			System.out.println(curPos3);


					}
					arrayPos = arrayPos -1;

				}
			}


			if (input.isKeyPressed(Input.KEY_RIGHT)) {
				//input.consumeEvent();
				input.clearKeyPressedRecord();
				input.disableKeyRepeat();
				input.clearControlPressedRecord();
				temp = arrayPos +1;
				if ((temp != 16 || special == 1) && (temp != 20 || special2 ==1) && (temp != 30|| special3 ==1) && (temp != 34|| special4 ==1)&& (temp != bombPosition)){
					bomberman.destroy();
					bomberman.createRight();

					float up = bomberman.getX();
					int i = 0;
					if (bomberman.getX() < 901){
						if (gridX == 0){
							gridX = 1;
						}
						else {
							gridX = 0;
						}

						while( i < 100){
							up = up +1;
							bomberman.setX((up+1));
							i++;
						}
						yes = 1;
						//bomberman.setVelocity(new Vector (3.5f,0f));
						//if(release2 == 0){
						//release2 =1;
						//curPos2 = bomberman.getX();
						//idealPos2 = bomberman.getX() + 101
						;
						//	System.out.println(curPos2);
						//	System.out.println(idealPos2);
						//	curPos2 = bomberman.getX(); //+ i;
						///	System.out.println(curPos2);


					}


					arrayPos = arrayPos +1;

				}
			}

		}



		//	curPos2 = bomberman.getX(); //+ i;

		//	if((curPos2 > idealPos2) ){
		//	release2 = 0;
		//bomberman.setVelocity(new Vector (.0f,0f));
		//if (bomberman.getX() > 100){
		//	bomberman.setX(idealPos2);
		//	}
		//	}



		//	if (input.isKeyPressed(Input.KEY_X)) {
		if (secondsPassed > 3){
			if (cheap == 1){
				Vector pos = bomb.getPosition();
				bomb.destroy();
				bombPosition = -1;
				fire.createBomb();
				fire.setPosition(pos);
				fire.setVelocity(new Vector (0,0));
				fire.setVelocity( new Vector( .150f, 0f));

				fire2.createBomb();
				fire2.setPosition(pos);
				fire2.setVelocity(new Vector (0,0));
				fire2.setVelocity(new Vector (-0.150f,0f));


				fire3.createBomb();
				fire3.setPosition(pos);
				fire3.setVelocity(new Vector (0,0));
				fire3.setVelocity( new Vector( .0f, 0.150f));

				fire4.createBomb();
				fire4.setPosition(pos);
				fire4.setVelocity(new Vector (0,0));
				fire4.setVelocity(new Vector (0.0f,-0.150f));




				cheap = 0;
			}

		}
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			if (cheap == 0){
				bomb.createBomb();
				bombPosition = arrayPos;
				if (initial == 0){
					timer.scheduleAtFixedRate(task, 1000, 1000);
					initial = 1;
				}
				else{
					secondsPassed = 0;
				}
				Vector pos = bomberman.getPosition();
				bomb.setPosition(pos);
				fireCounter = 0;
				fire2Counter = 0;
				fire3Counter = 0;
				fire4Counter = 0;

				cheap = 1;
			}
		}
		if (input.isKeyPressed(Input.KEY_X) ) {
			if (cheap1 == 1){
				Vector pos = power.getPosition();
				power.destroy();
				bombPosition = -1;
				fire5.createBomb();
				fire5.setPosition(pos);
				fire5.setVelocity(new Vector (0,0));

				powerOnce = 1;
				cheap1 = 0;
			}

		}

		if (input.isKeyPressed(Input.KEY_C) && powerCounter == 1) {
			if (cheap1 == 0){
				if (powerOnce == 0){
				power.createBomb();
				//power.createBomb();

				powerPosition = arrayPos;
				//timer.scheduleAtFixedRate(task, 1000, 1000);
				Vector pos = bomberman.getPosition();
				power.setPosition(pos);
				fireCounter5 = 0;
				cheap1 = 1;
			}
		}
		}

		//////////// END CONTROLLS


		float xDirection = bomberman.getVelocity().getX();
		float yDirection = bomberman.getVelocity().getY();
		float xEnemy = enemy.getVelocity().getX();
		float yEnemy = enemy.getVelocity().getY();

		float xEnemy2 = enemy2.getVelocity().getX();
		float yEnemy2 = enemy2.getVelocity().getY();

		float xEnemy3 = enemy3.getVelocity().getX();
		float yEnemy3 = enemy3.getVelocity().getY();

		// SPECIAL CASES FOR CONTACT
		boolean bounced = false;
		if (bomberman.getCoarseGrainedMaxX() > ScreenWidth
				|| bomberman.getCoarseGrainedMinX() < 0) {
			bomberman.bounce(90);
			bounced = true;
		} else if (bomberman.getCoarseGrainedMaxY() > ScreenHeight
				|| bomberman.getCoarseGrainedMinY() < 0) {
			bomberman.bounce(0);
			bounced = true;
		}

		// boolean bounced = false;
		if (enemy.getCoarseGrainedMaxX() > ScreenWidth
				|| enemy.getCoarseGrainedMinX() < 0) {
			// enemy.bounce(90);
			bounced = true;
		} else if (enemy.getCoarseGrainedMaxY() > ScreenHeight
				|| enemy.getCoarseGrainedMinY() < 0) {
			enemy.bounce(0);
			bounced = true;
		}



		Vector direction =bomberman.getVelocity();

		if (enemy2.getCoarseGrainedMaxX() > ScreenWidth
				|| enemy2.getCoarseGrainedMinX() < 0) {
			//	enemy2.bounce(90);
			bounced = true;
		} else if (enemy2.getCoarseGrainedMaxY() > ScreenHeight
				|| enemy2.getCoarseGrainedMinY() < 0) {
			//	enemy2.bounce(0);
			bounced = true;
		}

		if (enemy3.getCoarseGrainedMaxX() > ScreenWidth
				|| enemy3.getCoarseGrainedMinX() < 0) {
			enemy3.bounce(90);
			bounced = true;
		} else if (enemy3.getCoarseGrainedMaxY() > ScreenHeight
				|| enemy3.getCoarseGrainedMinY() < 0) {
			enemy3.bounce(0);
			bounced = true;
		}
		if (enemy4.getCoarseGrainedMaxX() >900
				|| enemy4.getCoarseGrainedMinX() < 0) {
			//enemy4.bounce(90);
			bounced = true;
		} else if (enemy4.getCoarseGrainedMaxY() > ScreenHeight
				|| enemy4.getCoarseGrainedMinY() < 0) {
			enemy4.bounce(0);
			bounced = true;
		}
		if (enemy5.getCoarseGrainedMaxX() > ScreenWidth
				|| enemy5.getCoarseGrainedMinX() < 0) {
			enemy5.bounce(90);
			bounced = true;
		} else if (enemy5.getCoarseGrainedMaxY() > ScreenHeight
				|| enemy5.getCoarseGrainedMinY() < 0) {
			enemy5.bounce(0);
			bounced = true;
		}


		if(bomberman.collides(power) != null && powerCounter == 0){

			power.destroy();
			powerCounter =1;

		}

		if (xEnemy > 0) {
			if(enemy.collides(BreakableWall) != null && bwall != 1){

				enemy.bounce(90);

			}}
		else if (xEnemy < 0) {
			if(enemy.collides(BreakableWall) != null && bwall != 1){
				enemy.bounce(90);
			}}




		if (xEnemy > 0) {
			if(enemy.collides(BreakableWall2) != null && bwall2 != 1){
				enemy.bounce(90);

			}}
		else if (xEnemy < 0) {
			if(enemy.collides(BreakableWall2) != null&& bwall2 != 1){
				enemy.bounce(90);
			}}


		if (xEnemy > 0) {
			if(enemy.collides(BreakableWall3) != null){
				enemy.bounce(90);

			}}
		else if (xEnemy < 0) {
			if(enemy.collides(BreakableWall3) != null){
				enemy.bounce(90);
			}}


















		if (xEnemy2 > 0) {
			if(enemy2.collides(BreakableWall) != null && bwall != 1){

				enemy2.bounce(90);

			}}
		else if (xEnemy < 0) {
			if(enemy2.collides(BreakableWall) != null && bwall != 1){
				enemy2.bounce(90);
			}}




		if (xEnemy > 0) {
			if(enemy2.collides(BreakableWall2) != null && bwall2 != 1){
				enemy2.bounce(90);

			}}
		else if (xEnemy < 0) {
			if(enemy2.collides(BreakableWall2) != null&& bwall2 != 1){
				enemy2.bounce(90);
			}}


		if (xEnemy > 0) {
			if(enemy2.collides(BreakableWall3) != null){
				enemy2.bounce(90);

			}}
		else if (xEnemy2 < 0) {
			if(enemy2.collides(BreakableWall3) != null){
				enemy2.bounce(90);
			}}














		//////		
		//if ( fire2Counter == 0 || fire3Counter == 0 || fire4Counter == 0){
		if (bwall != 1){

			if(fire.collides(BreakableWall) != null && fireCounter != 1){

				fire.destroy();
				BreakableWall.destroy();
				fireCounter =1;
				special =1;
				bwall =1;
			}
			if(fire2.collides(BreakableWall) != null && fire2Counter != 1){
				fire2.destroy();
				BreakableWall.destroy();
				fire2Counter =1;
				special =1;
				bwall =1;

			}

			if(fire3.collides(BreakableWall) != null && fire3Counter != 1){
				fire3.destroy();
				BreakableWall.destroy();
				fire3Counter =1;
				special =1;
				bwall =1;

			}
			if(fire4.collides(BreakableWall) != null && fire4Counter != 1){
				fire4.destroy();
				BreakableWall.destroy();
				fire4Counter =1;
				special =1;
				bwall =1;

			}
		}
		/////////////////
		if (bwall3 != 1){
			if(fire.collides(BreakableWall3) != null && fireCounter != 1){
				fire.destroy();
				BreakableWall3.destroy();
				fireCounter =1;
				special2 =1;
				bwall3 =1;
			}
			if(fire2.collides(BreakableWall3) != null && fire2Counter != 1){
				fire2.destroy();
				BreakableWall3.destroy();
				fire2Counter =1;
				special2 =1;
				bwall3 =1;

			}

			if(fire3.collides(BreakableWall3) != null && fire3Counter != 1){
				fire3.destroy();
				BreakableWall3.destroy();
				fire3Counter =1;
				special2 =1;
				bwall3 =1;

			}
			if(fire4.collides(BreakableWall3) != null && fire4Counter != 1){
				fire4.destroy();
				BreakableWall3.destroy();
				fire4Counter =1;
				special2 =1;
				bwall3 =1;

			}

		}
		if (bwall2 != 1){

			if(fire.collides(BreakableWall2) != null && fireCounter != 1){
				fire.destroy();
				BreakableWall2.destroy();
				fireCounter =1;
				special3 =1;
				bwall2 =1;

			}
			if(fire2.collides(BreakableWall2) != null && fire2Counter != 1){
				fire2.destroy();
				BreakableWall2.destroy();
				fire2Counter =1;
				special3 =1;
				bwall2 =1;

			}

			if(fire3.collides(BreakableWall2) != null&& fire3Counter != 1){
				fire3.destroy();
				BreakableWall2.destroy();
				fire3Counter =1;
				special3 =1;
				bwall2 =1;

			}
			if(fire4.collides(BreakableWall2) != null && fire4Counter != 1){
				fire4.destroy();
				BreakableWall2.destroy();
				fire4Counter =1;
				special3 =1;
				bwall2 =1;

			}

		}

		if (bwall4 != 1){

			if(fire.collides(BreakableWall4) != null && fireCounter != 1){
				fire.destroy();
				BreakableWall4.destroy();
				fireCounter =1;
				special4 =1;
				bwall4 =1;

			}
			if(fire2.collides(BreakableWall4) != null && fire2Counter != 1){
				fire2.destroy();
				BreakableWall4.destroy();
				fire2Counter =1;
				special4 =1;
				bwall4 =1;

			}

			if(fire3.collides(BreakableWall4) != null&& fire3Counter != 1){
				fire3.destroy();
				BreakableWall4.destroy();
				fire3Counter =1;
				special4 =1;
				bwall4 =1;

			}
			if(fire4.collides(BreakableWall4) != null && fire3Counter != 1){
				fire4.destroy();
				BreakableWall4.destroy();
				fire4Counter =1;
				special4 =1;
				bwall4 =1;

			}

		}

		//		}






		if (enemy.getCoarseGrainedMaxY() > ScreenHeight
				|| enemy.getCoarseGrainedMinY() < 0) {
			//	enemy.bounce(0);
			bounced = true;
		}



		if (bounced) {
			bounces++;
		}

		if (bomberman.collides(enemy) != null){
			if (enemyDead == 0){

				lives =0;
			}
		}

		if (bomberman.collides(enemy2) != null){
			if (enemy2Dead == 0){

				lives = 0 ;
			}
		}

		if (bomberman.collides(enemy3) != null){
			if (enemy3Dead == 0){

				lives = 0;
			}
		}

		if (bomberman.collides(enemy4) != null){
			if (enemy4Dead == 0){

				lives =0;
			}
		}
		if (bomberman.collides(enemy5) != null ){
			if (enemy5Dead == 0){

				lives =0;
			}
		}

		if (fire5.collides(enemy5) != null){
			if (fireCounter5 != 1) {
				if(enemy5Dead == 0){
					enemy5Dead = 1;
					enemy5.destroy();
					fire5.destroy();
					fireCounter5 =1;
				}
			}
		}


		if (fire5.collides(enemy) != null){
			if (fireCounter5 != 1) {
				if(enemyDead == 0){
					enemyDead = 1;
					enemy.destroy();
					fire5.destroy();
					fireCounter5 =1;
				}
			}
		}

		if (fire5.collides(enemy2) != null){
			if (fireCounter5 != 1) {
				if(enemy2Dead == 0){
					enemy2Dead = 1;
					enemy2.destroy();
					fire5.destroy();
					fireCounter5 =1;
				}
			}
		}

		if (fire5.collides(enemy3) != null){
			if (fireCounter5 != 1) {
				if(enemy3Dead == 0){
					enemy3Dead = 1;
					enemy3.destroy();
					fire5.destroy();
					fireCounter5 =1;
				}
			}
		}


		if (fire5.collides(enemy4) != null){
			if (fireCounter5 != 1) {
				if(enemy4Dead == 0){
					enemy4Dead = 1;
					enemy4.destroy();
					fire5.destroy();
					fireCounter5 =1;
				}
			}
		}







		if (fire.collides(enemy) != null){
			if (fireCounter != 1) {
				if(enemyDead == 0){
					enemyDead = 1;
					enemy.destroy();
					fire.destroy();
					fireCounter =1;
				}
			}
		}

		if (fire.collides(enemy2) != null){
			if (fireCounter != 1) {
				if(enemy2Dead == 0){
					enemy2Dead = 1;
					enemy2.destroy();
					fire.destroy();
					fireCounter =1;
				}
			}
		}

		if (fire.collides(enemy3) != null){
			if (fireCounter != 1) {
				if(enemy3Dead == 0){
					enemy3Dead = 1;
					enemy3.destroy();
					fire.destroy();
					fireCounter =1;
				}
			}
		}
		if (fire.collides(enemy4) != null){
			if (fireCounter != 1) {
				if(enemy4Dead == 0){
					enemy4Dead = 1;
					enemy4.destroy();
					fire.destroy();
					fireCounter =1;
				}
			}
		}



		if (fire.collides(wall) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall2) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall3) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall4) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall5) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall6) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall7) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall8) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall9) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall10) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall11) != null){
			fireCounter = 1;
			fire.destroy();

		}
		if (fire.collides(wall12) != null){
			fireCounter = 1;
			fire.destroy();
		}

		if (fire3.collides(enemy5) != null){
			if (fire3Counter != 1){
				if(enemy5Dead == 0){

					enemy5Dead = 1;
					enemy5.destroy();
					fire3.destroy();
					fire3Counter =1;
				}
			}
		}


		if (fire3.collides(enemy) != null){
			if (fire3Counter != 1){
				if(enemyDead == 0){

					enemyDead = 1;
					enemy.destroy();
					fire3.destroy();
					fire3Counter =1;
				}
			}
		}
		if (fire3.collides(enemy2) != null){
			if (fire3Counter != 1){
				if(enemy2Dead == 0){

					enemy2Dead = 1;
					enemy2.destroy();
					fire3.destroy();
					fire3Counter =1;
				}
			}
		}
		if (fire3.collides(enemy3) != null){
			if (fire3Counter != 1){
				if(enemy3Dead == 0){

					enemy3Dead = 1;
					enemy3.destroy();
					fire3.destroy();
					fire3Counter =1;
				}
			}
		}
		if (fire3.collides(enemy4) != null){
			if (fireCounter != 1){
				if(enemy4Dead == 0){

					enemy4Dead = 1;
					enemy4.destroy();
					fire3.destroy();
					fire3Counter =1;
				}
			}
		}

		if (fire3.collides(wall) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall2) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall3) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall4) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall5) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall6) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall7) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall8) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall9) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall10) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall11) != null){
			fire3Counter = 1;
			fire3.destroy();

		}
		if (fire3.collides(wall12) != null){
			fire3Counter = 1;
			fire3.destroy();

		}


		if (fire2.collides(enemy) != null){
			if (fire2Counter != 1){
				if(enemyDead == 0){

					enemyDead = 1;
					enemy.destroy();
					fire2.destroy();
					fire2Counter =1;
				}
			}
		}

		if (fire2.collides(enemy5) != null){
			if (fire2Counter != 1){
				if(enemy5Dead == 0){

					enemy5Dead = 1;
					enemy5.destroy();
					fire2.destroy();
					fire2Counter =1;
				}
			}
		}



		if (fire2.collides(enemy2) != null){
			if (fire2Counter != 1){
				if(enemy2Dead == 0){

					enemy2Dead = 1;
					enemy2.destroy();
					fire2.destroy();
					fire2Counter =1;
				}
			}
		}
		if (fire2.collides(enemy3) != null){
			if (fire2Counter != 1){
				if(enemy3Dead == 0){

					enemy3Dead = 1;
					enemy3.destroy();
					fire2.destroy();
					fire2Counter =1;
				}
			}
		}
		if (fire2.collides(enemy4) != null){
			if (fire2Counter != 1){
				if(enemy4Dead == 0){

					enemy4Dead = 1;
					enemy4.destroy();
					fire2.destroy();
					fire2Counter =1;
				}
			}
		}


		if (fire2.collides(wall) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall2) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall3) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall4) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall5) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall6) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall7) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall8) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall9) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall10) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall11) != null){
			fire2Counter = 1;
			fire2.destroy();

		}
		if (fire2.collides(wall12) != null){
			fire2Counter = 1;
			fire2.destroy();

		}

		if (fire4.collides(enemy5) != null){
			if (fire4Counter != 1){
				if(enemy5Dead == 0){

					enemy5Dead = 1;
					enemy5.destroy();
					fire4.destroy();
					fire4Counter =1;
				}
			}
		}


		if (fire4.collides(enemy) != null){
			if (fire4Counter != 1){
				if(enemyDead == 0){

					enemyDead = 1;
					enemy.destroy();
					fire4.destroy();
					fire4Counter =1;
				}
			}
		}



		if (fire4.collides(enemy4) != null){
			if (fire4Counter != 1){
				if(enemy4Dead == 0){

					enemy4Dead = 1;
					enemy4.destroy();
					fire4.destroy();
					fire4Counter =1;
				}
			}
		}




		if (fire4.collides(enemy3) != null){
			if (fire4Counter != 1){
				if(enemy3Dead == 0){

					enemy3Dead = 1;
					enemy3.destroy();
					fire4.destroy();
					fire4Counter =1;
				}
			}
		}

		if (fire4.collides(enemy2) != null){
			if (fire4Counter != 1){
				if(enemy2Dead == 0){

					enemy2Dead = 1;
					enemy2.destroy();
					fire4.destroy();
					fire4Counter =1;
				}
			}
		}

		if (fire4.collides(wall) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall2) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall3) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall4) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall5) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall6) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall7) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall8) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall9) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall10) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall11) != null){
			fire4Counter = 1;
			fire4.destroy();

		}
		if (fire4.collides(wall12) != null){
			fire4Counter = 1;
			fire4.destroy();

		}




		if (fire.collides(bomberman) != null && fireCounter != 1){
			lives = 0;
			fire.destroy();
			fireCounter = 1;
		}
		if (fire2.collides(bomberman) != null&& fire2Counter != 1){
			lives = 0;
			fire2.destroy();
			fire2Counter = 1;
		}
		if (fire3.collides(bomberman) != null&& fire3Counter != 1){
			lives = 0;
			fire3.destroy();
			fire3Counter = 1;
		}
		if (fire4.collides(bomberman) != null&& fire4Counter != 1){
			lives = 0;
			fire4.destroy();
			fire4Counter = 1;
		}
		if (fire5.collides(bomberman) != null&& fireCounter5 != 1){
			lives = 0;
			fire5.destroy();
			fireCounter5 = 1;
		}

		bomberman.update(delta);
		bomb.update(delta);
		enemy.update(delta);
		enemy2.update(delta);
		enemy3.update(delta);
		enemy4.update(delta);
		enemy5.update(delta);

		fire.update(delta);
		fire2.update(delta);
		fire3.update(delta);
		fire4.update(delta);
		fire5.update(delta);
		gameOver.update(delta);


		BreakableWall.update(delta);
		BreakableWall2.update(delta);
		BreakableWall3.update(delta);
		BreakableWall4.update(delta);

		float x = bomberman.getVelocity().getX();
		float y = bomberman.getVelocity().getX();
		//bomberman.setVelocity(new Vector (0,0));


	}




	private void initialState(Input input) {
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			input.consumeEvent();
			input.clearKeyPressedRecord();
			input.disableKeyRepeat();
			input.clearControlPressedRecord();
			input.disableControllers();
			
			enemyDead =1;
			enemy2Dead =1;
			enemy3Dead =1;
			enemy4Dead =1;
			enemy5Dead =1;
			enemy.destroy();
			enemy2.destroy();
			enemy3.destroy();
			enemy4.destroy();
			enemy5.destroy();

			border.destroy();
			//level2 = new Level2(500,  400, .1f, .2f);

			level2.destroy();

			gameOver.destroy();
			bombPosition = -1;
		//	bomberman = new Bomberman(100,  50, .1f, .2f);
			bomberman.destroy();
			bomberman = new Bomberman(100,  50, .1f, .2f);
			bomberman.destroy();
			bomberman = new Bomberman(100,  50, .1f, .2f);

			bomb = new Bomb(ScreenWidth/2 , ScreenHeight/2 , .1f, .2f);
			bomb.destroy();
			enemy = new Enemy(900 , 510 , .1f, .2f);
			enemy.setVelocity(new Vector (0f,.0f) );

			enemy4 = new Enemy4(900 , 510 , .1f, .2f);
			enemy4.setVelocity(new Vector (0f,.0f) );
			enemy2 = new Enemy2(500 , 200, .1f, .2f);
			enemy2.setVelocity(new Vector (0f,.0f) );
			fire = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire.destroy();
			enemy5.destroy();

			enemy3 = new Enemy3(200 , 400, .1f, .2f);
			//enemy3.setVelocity(new Vector (0f,-.10f) );
			fire2 = new Fire(ScreenWidth*2 , ScreenHeight*3 , .1f, .2f);
			fire2.destroy();
			fire3 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire3.destroy();
			fire4 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire4.destroy();

			fire5 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire5.destroy();
			//border = new Border(400 , 285 , .1f, .2f);

			wall = new Wall(200 , 125 , .1f, .2f);
			wall2 = new Wall(200 , 275 , .1f, .2f);
			wall3 = new Wall(200 , 425, .1f, .2f);

			wall4 = new Wall(400 , 125 , .1f, .2f);
			wall5 = new Wall(400 , 275 , .1f, .2f);
			wall6 = new Wall(400 , 425 , .1f, .2f);

			wall7 = new Wall(600 , 125 , .1f, .2f);
			wall8 = new Wall(600 , 275 , .1f, .2f);
			wall9 = new Wall(600 , 425 , .1f, .2f);

			wall10 = new Wall(800 , 125 , .1f, .2f);
			wall11 = new Wall(800 , 275 , .1f, .2f);
			wall12 = new Wall(800 , 425 , .1f, .2f);

			power = new Powerup(300 , 210 , .1f, .2f);

			//BreakableWall = new BreakableWall(200 , 200 , .1f, .2f);
			//BreakableWall2 = new BreakableWall(300 , 125 , .1f, .2f);
			//BreakableWall3 = new BreakableWall(500 , 425 , .1f, .2f);
			//BreakableWall4 = new BreakableWall(600 , 350 , .1f, .2f);

			BreakableWall = new BreakableWall(300 , 200 , .1f, .2f);
			BreakableWall2 = new BreakableWall(300 , 350 , .1f, .2f);
			BreakableWall3 = new BreakableWall(700 , 200 , .1f, .2f);
			BreakableWall4 = new BreakableWall(700 , 350 , .1f, .2f);
			temp = 0;
			arrayPos = 0;
			gridX = 0;
			gridY = 0;
			cheap = 0;
			bwall = 0;
			bwall2 = 0;
			bwall3 = 0;
			bwall4 = 0;
			special = 0;
			special2 = 0;
			special3 = 0;
			special4 = 0;
			powerCounter =0 ;
			enemyDead = 0;
			enemy2Dead = 0;
			enemy3Dead = 0;
			enemy4Dead = 0;
			enemy5Dead = 1;
			powerOnce = 0;
			lives = 1;
			livePrint = 1;
			enter = 1;
		}
	}




	private void level2(Input input) {
		victory.destroy();
		level2.createBomb();
		level2 = new Level2(500,  400, .1f, .2f);
		if(input.isKeyPressed(Input.KEY_2)) {
			
			level2.destroy();
			level2Counter = 2;

			border.destroy();
			gameOver.destroy();
			//level2.destroy();
			bomberman.destroy();
			bomberman = new Bomberman(100,  50, .1f, .2f);
			bomberman.destroy();
			bomberman = new Bomberman(100,  50, .1f, .2f);
			bomb = new Bomb(ScreenWidth/2 , ScreenHeight/2 , .1f, .2f);
			bomb.destroy();
			enemy = new Enemy(900 , 510 , .1f, .2f);
			enemy.setVelocity(new Vector (0f,.0f) );

			enemy4 = new Enemy4(900 , 510 , .1f, .2f);
			enemy4.setVelocity(new Vector (0f,.0f) );


			enemy2 = new Enemy2(500 , 200, .1f, .2f);
			enemy2.setVelocity(new Vector (0f,.0f) );
			fire = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire.destroy();


			enemy3 = new Enemy3(100 , 400, .1f, .2f);
			enemy5 = new Enemy5(500 , 400, .1f, .2f);

			//enemy3.setVelocity(new Vector (0f,-.10f) );
			fire2 = new Fire(ScreenWidth*2 , ScreenHeight*3 , .1f, .2f);
			fire2.destroy();
			fire3 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire3.destroy();
			fire4 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire4.destroy();

			fire5 = new Fire(ScreenWidth*3 , ScreenHeight*3 , .1f, .2f);
			fire5.destroy();
			//border = new Border(400 , 285 , .1f, .2f);

			wall = new Wall(200 , 125 , .1f, .2f);
			wall2 = new Wall(200 , 275 , .1f, .2f);
			wall3 = new Wall(200 , 425, .1f, .2f);

			wall4 = new Wall(400 , 125 , .1f, .2f);
			wall5 = new Wall(400 , 275 , .1f, .2f);
			wall6 = new Wall(400 , 425 , .1f, .2f);

			wall7 = new Wall(600 , 125 , .1f, .2f);
			wall8 = new Wall(600 , 275 , .1f, .2f);
			wall9 = new Wall(600 , 425 , .1f, .2f);

			wall10 = new Wall(800 , 125 , .1f, .2f);
			wall11 = new Wall(800 , 275 , .1f, .2f);
			wall12 = new Wall(800 , 425 , .1f, .2f);

			power = new Powerup(700 , 350 , .1f, .2f);

			//BreakableWall = new BreakableWall(200 , 200 , .1f, .2f);
			//BreakableWall2 = new BreakableWall(300 , 125 , .1f, .2f);
			//BreakableWall3 = new BreakableWall(500 , 425 , .1f, .2f);
			//BreakableWall4 = new BreakableWall(600 , 350 , .1f, .2f);

			BreakableWall = new BreakableWall(300 , 200 , .1f, .2f);
			BreakableWall2 = new BreakableWall(300 , 350 , .1f, .2f);
			BreakableWall3 = new BreakableWall(700 , 200 , .1f, .2f);
			BreakableWall4 = new BreakableWall(700 , 350 , .1f, .2f);
			temp = 0;
			arrayPos = 0;
			gridX = 0;
			gridY = 0;
			cheap = 0;
			bwall = 0;
			bwall2 = 0;
			bwall3 = 0;
			bwall4 = 0;
			special = 0;
			special2 = 0;
			special3 = 0;
			special4 = 0;
			powerCounter =0 ;
			enemyDead = 0;
			enemy2Dead = 0;
			enemy3Dead = 0;
			enemy4Dead = 0;
			enemy5Dead = 0;
			powerOnce = 0;
			lives = 1;
			livePrint = 1;
			enter = 1;
		}

	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new BombermanGame("Bomberman!", 800, 570));
			app.setDisplayMode(1000, 570, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
		}
	}
}