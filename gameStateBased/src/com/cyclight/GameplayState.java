/**
 * 
 * This is the gameplay 'state'. 
 * It's where all the game stuff is going on.
 * 
 * 
 */


package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

	int stateID = -1;

	Animation movingUp, movingDown, movingLeft, movingRight, movingRightSwingingSword;
	private BlockMap map;
	private Player player;
	boolean debug = false;

	int startX = 850;
	int startY = 510;
	int projectileSpeed=(int) (2.75*2);
	ArrayList<Enemy> activeEnemyList;
	ArrayList<Enemy> enemyList;
	Image backgroundImage;
	collisionHandler cHandler;
	
	GameplayState(int stateID) {
		this.stateID = stateID;
	}

	
	public GameplayState(String title) {
		super();
	}

	
	//
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)  {

		try
		{
			backgroundImage = new Image("res/Bgrnd.png");
			
			player = new Player(startX, startY, new Animation());
			loadEnemies();
			
			map = new BlockMap("res/level/map.tmx");
			cHandler = collisionHandler.getInstance();
			cHandler.setMap(map);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

	}

	private void loadEnemies() throws SlickException {
		activeEnemyList = new ArrayList<Enemy>();
		enemyList = new ArrayList<Enemy>();
		
		crocWalker crocEnemy = new crocWalker();
		enemyList.add(crocEnemy);
	}
	


	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		
		g.drawImage(backgroundImage, 0, 0);
		//Render the map
		map.getTiledMap().render(0, 0, 1);
		
		//draw the character
	//	g.drawAnimation(player.getAnimation(), player.getXPos(), player.getYPos());
		
		g.drawImage(player.getSprite(), player.getXPos(),player.getYPos());
		//draw the projectiles on screen
		for(Projectile shot: player.getProjectileList())
		{
			g.draw(shot.projShape);	
		}
		
		for(Enemy enemy : activeEnemyList)
		{	
			g.drawImage(enemy.getSprite(), enemy.getXPos(), enemy.getYPos());
		}
		
		
//		if(player.getWeapon().isActive())
		{
			//draw that weapon!
			//And animate it and stuff.
		}
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (debug)
		{
	/*		System.out.println("Map rows:" + map.getBlocks().size());
			System.out.println("Min X:" + player.getHitbox().getMinX());
			System.out.println("Min Y:" + player.getHitbox().getMinY());
			System.out.println("Max X:" + player.getHitbox().getMaxX());
			System.out.println("Max Y:" + player.getHitbox().getMaxY());
			System.out.println(player.getJumpCounter());*/
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				System.out.println(gc.getInput().getMouseX() + "," + gc.getInput().getMouseY());
			}	
		}

		//-------------------  DIRECTIONAL INPUT -------------------------//
		player.onUpdate(gc.getInput());		

		//------------------ OTHER KEYBOARD INPUT ---------------------//
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE))
		{
			sbg.enterState(stateBasedGameTest.MAINMENUSTATE);
		}
		if(gc.getInput().isKeyDown(Input.KEY_Y))
		{
			debug=true;
		}
		if(gc.getInput().isKeyDown(Input.KEY_N))
		{
			debug=false;
		}
		handleProjectile(gc);
		//handleWeapon(gc);
		
		
		//------------------ Do stuff with enemies -------------------//
		if(gc.getInput().isKeyPressed((Input.KEY_1)))
		{
			
		}
		
	
		for(Enemy enemy: activeEnemyList)
		{
			enemy.onUpdate();
		}
		
	}

/*
	//Weapon unimplemented at this time
	private void handleWeapon(GameContainer gc) {
		player.getWeapon();
	}

	*/
	/**
	 * See if you're supposed to be firing projectiles, if so fire, then animate all projectiles
	 * @param gc
	 * @throws SlickException
	 */
	private void handleProjectile(GameContainer gc) throws SlickException {
		if(gc.getInput().isKeyPressed(Input.KEY_C))
		{
			fireShot();
		}
		
		//For each existing projectile
		ArrayList<Projectile> projList = player.getProjectileList();
		for(int i=0; i < projList.size();i++)
		{
			Projectile shot = projList.get(i);
			if(shot.projDir)//move right
			{
				shot.projShape.setX(shot.projShape.getMinX()+shot.projSpeed);
			}
			else //move left
			{
				shot.projShape.setX(shot.projShape.getMinX()-shot.projSpeed);
			}
	
			if (cHandler.collidingWithBlocks(shot.projShape))
			{ //if it hit anything remove the shot
				player.removeProjectile(i);
			}
	
		}
	}
	
	/**
	 * Fires the shot
	 */
	private void fireShot() {
		if(player.getNumProjectiles()<player.getMaxProjectiles())
		{
			player.addProjectile(player.getFacing(), projectileSpeed);
		}
	}



/**
 * Given a shape (assumed a rectangle) hb (hitbox), 
 * return an array of the blocks that the corners collide with 
 * 
 * @param hb
 * @return ArrayList<Block> blocksCollidedWith
 */
	public ArrayList<Block> getCollisionBlockList(Shape hb)
	{
		ArrayList<Block> bList = new ArrayList<Block>();

		Coordinates [] coords= new Coordinates[4];
		
		coords[0] = new Coordinates((float)Math.floor(hb.getMinX()/32), (float)Math.floor(hb.getMinY()/32));
		coords[1] = new Coordinates((float)Math.floor(hb.getMaxX()/32), (float)Math.floor(hb.getMinY()/32));
		coords[2] = new Coordinates((float)Math.floor(hb.getMaxX()/32), (float)Math.floor(hb.getMaxY()/32));
		coords[3] = new Coordinates((float)Math.floor(hb.getMinX()/32), (float)Math.floor(hb.getMaxY()/32));
		
		//For each corner of the block see what block it's in
		for(int i=0; i <4; i++)
		{
			Block e = map.get((int) coords[i].x, (int) coords[i].y);
			bList.add(e);
		}
		
		return bList;
	}

	// IMPORTANT - if auto-gen'd by eclipse this is set to 0. You have to keep it at -1 or you'll
	// get some stupid error that makes no sense.
	@Override
	public int getID() {
		return stateID;
	}

}