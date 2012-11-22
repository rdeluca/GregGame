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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.cyclight.characters.Enemy;
import com.cyclight.characters.Player;
import com.cyclight.characters.crocWalker;

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

			//			loadEnemies();
			activeEnemyList = new ArrayList<Enemy>();
			
			
			map = new BlockMap("res/level/ArenaMap.tmx");
			cHandler = collisionHandler.getInstance();
			cHandler.setMap(map);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
/*
	private void loadEnemies() throws SlickException {
		activeEnemyList = new ArrayList<Enemy>();
		enemyList = new ArrayList<Enemy>();
		
		crocWalker crocEnemy = new crocWalker();
		enemyList.add(crocEnemy);
	}
	*/


	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(backgroundImage, 0, 0);
		//Render the map
		map.getTiledMap().render(0, 0, 1);
		
		//draw the character
		//	g.drawAnimation(player.getAnimation(), player.getXPos(), player.getYPos());
		
		//we should probably have a player.render method that takes gc, sbg, and g as args, then draws the player
		//it would also call the weapon's render method
		g.drawImage(player.getSprite(), player.getXPos(),player.getYPos());
		//draw the projectiles on screen
		for(Projectile shot: player.getProjectileList())
		{
			g.draw(shot.projShape);	
		}
		
		if(activeEnemyList != null)
		{
			for(Enemy enemy : activeEnemyList)
			{	
				g.drawImage(enemy.getSprite(), enemy.getXPos(), enemy.getYPos());
			}
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
		if(gc.getInput().isKeyPressed(Input.KEY_C))
		{
			player.weaponAttack();
		}
		
		

		//-------------------  PLAYER INPUT -------------------------//
		player.onUpdate(gc.getInput(), delta);		
				
		
		
		//-------------------  ENEMY UPDATE -------------------//
		if(gc.getInput().isKeyPressed((Input.KEY_1)))
		{
			Enemy enemy = new crocWalker();
			
			enemy.setCoords(gc.getInput().getMouseX(), gc.getInput().getMouseY());
			
			if(cHandler.collidingWithBlocks(enemy.getHitbox()))
			{
				//if Colliding don't add it
				
			}
			else
			{ 
				//Not Colliding add em in
				activeEnemyList.add(enemy);
			}
			
			/*	
			Enemy crocEnemy = new Enemy(gc.getInput().getMouseX(), gc.getInput().getMouseY(), enemy.getHitbox(), enemy.getAnimation(), enemy.getSpriteSheet(), enemy.getAI());
			if(entityCollisionWith(crocEnemy.getHitbox()).equals(blockTypes.open))
			{
				System.out.println((crocEnemy.getHitbox().getCenterX()));
				activeEnemyList.add(crocEnemy);
			}*/
		}
		
		if(activeEnemyList!=null)
		{
			for(Enemy enemy: activeEnemyList)
			{
				enemy.onUpdate(gc.getInput(), delta);
			}
		}
	
		
		//------------------ Other passage of time things ------------//
		handleProjectiles(gc, delta);
		player.getWeapon().update(delta);
		
		

	}




	/**
	 * See if you're supposed to be firing projectiles, if so fire, then animate all projectiles
	 * @param gc
	 * @throws SlickException
	 */
	private void handleProjectiles(GameContainer gc, int delta) throws SlickException {
		//For each existing projectile
		ArrayList<Projectile> projList = player.getProjectileList();
		for(int i=0; i < projList.size();i++)
		{
			Projectile shot = projList.get(i);
			shot.update(delta);

	
			if (cHandler.collidingWithBlocks(shot.projShape))
			{ //if it hit anything remove the shot
				projList.remove(i);
			}
			else
			{
				//TODO: Implement better way of collision checking on enemies?
				OUTER:for(int j=0; j<activeEnemyList.size();j++)
				{
					Enemy enemy= activeEnemyList.get(j);
					if(enemy.getHitbox().intersects(shot.projShape))
					{
						activeEnemyList.remove(j);
						if(shot.getPiercing()<=1)
						{
							projList.remove(i);
							break OUTER;
						}
						else
						{
							shot.setPiercing(shot.getPiercing()-1);
						}
					}
				}
				
			}
		}
	}

	// IMPORTANT - if auto-gen'd by eclipse this is set to 0. You have to keep it at -1 or you'll
	// get some stupid error that makes no sense.
	@Override
	public int getID() {
		return stateID;
	}

}