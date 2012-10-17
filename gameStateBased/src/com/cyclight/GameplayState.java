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
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.cyclight.Block.blockTypes;
import com.cyclight.characters.Enemy;
import com.cyclight.characters.Player;
import com.cyclight.characters.crocWalker;

public class GameplayState extends BasicGameState {

	int stateID = -1;
	
	Animation movingUp, movingDown, movingLeft, movingRight, movingRightSwingingSword;
	private BlockMap map;
	private Player player;
	boolean debug = false;
	int charHeight=18;
	int charWidth=32;
	int startX = 842;
	int startY = 507;
	float verticalSpeed =0.3f;   //Hardcoded but doesn't really mean anything
	float horizontalSpeed = 0.18f; //Hardcoded but doesn't really mean anything
	float gravity = verticalSpeed;
	float projectileSpeed= 2.75f*horizontalSpeed;
	ArrayList<Enemy> activeEnemyList;
	ArrayList<Enemy> enemyList;
	Image backgroundImage;
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
			Image gregImage = new Image("res/gregSheet.png");
			
			//If I were to ever to animate the sprite rather than have a static block this would be useful

			SpriteSheet sheet = new SpriteSheet(gregImage, charHeight, charWidth);
			//Create a 'hitbox' polygon
			Polygon playerPolygon = new Polygon(new float[] { startX, startY, startX + charHeight, startY, startX + charHeight,
					startY + charWidth, startX, startY + charWidth});
			
			player = new Player(startX, startY, playerPolygon, new Animation(), sheet);
			loadEnemies();
			
			
			map = new BlockMap("res/level/map.tmx");
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
		
		int i=0;

		System.out.println("Enemy List Size:"+activeEnemyList.size());
		for(Enemy enemy : activeEnemyList)
		{	
			g.drawImage(enemy.getSprite(), enemy.getXPos(), enemy.getYPos());
			System.out.println(enemy.getSprite() +","+ enemy.getXPos()+","+ enemy.getYPos());
		}
		
		
//		if(player.getWeapon().isActive())
		{
			//draw that weapon!
			//And animate it and stuff.
		}
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		float delay = 0;
		if (debug)
		{
			System.out.println("Map rows:" + map.getBlocks().size());
			System.out.println("Min X:" + player.getHitbox().getMinX());
			System.out.println("Min Y:" + player.getHitbox().getMinY());
			System.out.println("Max X:" + player.getHitbox().getMaxX());
			System.out.println("Max Y:" + player.getHitbox().getMaxY());
			System.out.println(player.getJumpCounter());
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				System.out.println(gc.getInput().getMouseX() + "," + gc.getInput().getMouseY());
			}	
		}


		//-------------------  DIRECTIONAL INPUT -------------------------//
		handleMovement(gc, delta);
		


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
		handleProjectile(gc, delta);
		//handleWeapon(gc);
		
		
		//------------------ Do stuff with enemies -------------------//
		if(gc.getInput().isKeyPressed((Input.KEY_1)))
		{
			Enemy enemy = enemyList.get(0);
			
			Enemy crocEnemy = new Enemy(gc.getInput().getMouseX(), gc.getInput().getMouseY(), enemy.getHitbox(), enemy.getAnimation(), enemy.getSpriteSheet(), enemy.getAI());
			if(entityCollisionWith(crocEnemy.getHitbox()).equals(blockTypes.open))
			{
				System.out.println((crocEnemy.getHitbox().getCenterX()));
				activeEnemyList.add(crocEnemy);
			}
		}
		
	/*	
		for(Enemy enemy: enemies)
		{
			enemy.checkActive(player.getXPos(), player.getYPos());
		}
		*/
	}


	//Weapon unimplemented at this time
	private void handleWeapon(GameContainer gc) {
		player.getWeapon();
	}

	
	/**
	 * See if you're supposed to be firing projectiles, if so fire, then animate all projectiles
	 * @param gc
	 * @throws SlickException
	 */
	private void handleProjectile(GameContainer gc, int delta) throws SlickException {
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
				shot.projShape.setX(shot.projShape.getMinX()+shot.projSpeed*delta);
			}
			else //move left
			{
				shot.projShape.setX(shot.projShape.getMinX()-shot.projSpeed*delta);
			}
	
			if(!entityCollisionWith(shot.projShape).equals(blockTypes.open))
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
	 * For character movement.
	 * Figure out direction and handle collisions.
	 *  
	 * @param gc
	 * @throws SlickException
	 */
	private void handleMovement(GameContainer gc, int delta) throws SlickException {
		
		float horizontalSpeed = this.horizontalSpeed*delta;
		float verticalSpeed = this.verticalSpeed*delta;
		float gravity = this.gravity*delta;
		
		if (gc.getInput().isKeyDown(Input.KEY_LEFT))
		{	
			player.setFacing(false);
			moveLeft(horizontalSpeed);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				moveRight(horizontalSpeed);
				iterateDirection(horizontalSpeed, "left");
			}
		}
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT))
		{

			player.setFacing(true);
			moveRight(horizontalSpeed);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				moveLeft(horizontalSpeed);
				iterateDirection(horizontalSpeed, "right");
			}
		}
		if (gc.getInput().isKeyDown(Input.KEY_Z))
		{
			if( player.isGrounded())
			{ 
				//Not jumping, start jump
				player.setJumping(true);
				player.setGrounded(false);
				
				moveUp(verticalSpeed);
				player.jumpCountdown(1);
				
				if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
				{
					player.setJumping(false);
					moveDown(verticalSpeed);
					iterateDirection(verticalSpeed, "up");
				}
			}
			else if(player.isJumping() && !player.isGrounded())
			{//In the air, jumping
				moveUp(verticalSpeed);
				player.jumpCountdown(1);
				if(player.getJumpCounter()<=0)
				{
					player.setJumping(false);
				}
				if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
				{
					player.setJumping(false);
					moveDown(verticalSpeed);
					iterateDirection(verticalSpeed, "up");
				}					
			}
			else if(!player.isJumping() && !player.isGrounded())
			{	
				//Falling 
				//Let gravity do its thing.
			}
		}
		else if(player.isJumping())
		{
			player.setJumping(false);
		}
		if( !player.isGrounded() && !player.isJumping())
		{
			//Mid-air but not holding UP/JUMP
			moveDown(gravity);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				moveUp(gravity);
				if(testDirection("down"))
					player.setGrounded(true);
				iterateDirection(gravity, "down");
			}
		}
		if(player.isGrounded()) 
		{ 
			//If not grounded and in the air - then fall
			moveDown(gravity);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				moveUp(gravity);
				if(testDirection("down"))
					player.setGrounded(true);
				iterateDirection(gravity, "down");
			}
		}
		if (gc.getInput().isKeyDown(Input.KEY_DOWN))
		{
			//This should crouch or something at some time.
		}		
	}


	private void iterateDirection(float speed, String direction) throws SlickException{
		speed-=1;
	
		if(speed<=0)
		{
			if(direction.equals("down"))
				player.setGrounded(true);
			return;
		}
		
		if(direction.equals("down"))
		{
			moveDown(speed);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				moveUp(speed);
				iterateDirection(speed, direction);
			}
		}
		else if(direction.equals("up"))
		{

			moveUp(speed);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				moveDown(speed);
				iterateDirection(speed, direction);
			}
		}
		else if(direction.equals("right"))
		{
			moveRight(speed);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				if(entityCollisionWith(player.getHitbox()).equals(blockTypes.closed))
				
				moveLeft(speed);
				iterateDirection(speed, direction);
			}
		}
		else if(direction.equals("left"))
		{
			moveLeft(speed);
			if (!entityCollisionWith(player.getHitbox()).equals(blockTypes.open))
			{
				moveRight(speed);
				iterateDirection(speed, direction);
			}
		}
		return;
	}

	/**
	 * Moves character down 'speed' units
	 * @param speed
	 */
	private void moveDown(float speed) {
		float pY = player.getYPos();		
		pY+=speed;
		player.setYPos(pY);
	}

	/**
	 * Moves character up 'speed' units
	 * @param speed
	 */
	private void moveUp(float speed){
		float pY = player.getYPos();
		pY-=speed;
		player.setYPos(pY);
	}
	

	/**
	 * Moves character left 'speed' units
	 * @param speed
	 */
	private void moveLeft(float speed){
		float pX = player.getXPos();
		pX-=speed;
		player.setXPos(pX);
	}

	/**
	 * Moves character right 'speed' units
	 * @param speed
	 */
	private void moveRight(float speed){
		float pX = player.getXPos();
		pX+=speed;
		player.setXPos(pX);
		
	}
	
/**
 *	   Returns a boolean. True if it collides, false otherwise.
 *		1----2
 *		|	 |
 *		4----3
 *		used to detect which side is colliding
 * 	
 * @param dir
 * @return result
 * @throws SlickException
 */
	public boolean testDirection(String dir) throws SlickException{
		boolean[] boolList = directionalCollision();
		boolean result=false;
		switch(dir)
		{
			case "up":
				if(boolList[0] || boolList[1])
					result=true;
				break;
			case "down":
				if(boolList[2] || boolList[3])
					result=true;
				break;			
			case "left":
				if(boolList[0] || boolList[3])
					result=true;
				break;			
			case "right":
				if(boolList[1] || boolList[2])
					result=true;
				break;		
		}
		return result;
	}
	
	/**
	 *   Used to return an array of four booleans to detect which direction 
	 *   collision is coming from perhaps to detect directional knockback.
 	 * 
	 * 
	 * @return collidedBlocks
	 * @throws SlickException
	 */
	public boolean[] directionalCollision() throws SlickException{

		ArrayList<Block> blocks= getCollisionBlockList(player.getHitbox());
		boolean [] collidedBlocks = new boolean [4];
		
		for (int i=0; i<blocks.size(); i++)
		{
			Block block = blocks.get(i);
			if (block.getTileID() == 1 )
			{
				collidedBlocks[i]=true;
			}				
			if(block.getTileID() == 2)
			{
				float x = player.getHitbox().getMinX();
				float y = player.getHitbox().getMaxY();
				Coordinates c1 = new Coordinates(x,y);
				
				float x2 = player.getHitbox().getMaxX();
				float y2 = player.getHitbox().getMaxY();
				Coordinates c2 = new Coordinates(x2,y2);	

				if(c1.intersects(block)||c2.intersects(block))
				{
					collidedBlocks[i]=true;
				}		
			}
			
			if(block.getTileID() == 3)
			{		
				float x = player.getHitbox().getMinX();
				float y = player.getHitbox().getMinY();
				Coordinates c1 = new Coordinates(x,y);
				
				float x2 = player.getHitbox().getMaxX();
				float y2 = player.getHitbox().getMinY();
				Coordinates c2 = new Coordinates(x2,y2);	
								
				if(c1.intersects(block)||c2.intersects(block))
				{
					collidedBlocks[i]=true;
				}
			}
			if(collidedBlocks.equals(null))
				collidedBlocks[i]=false;
		}
		return collidedBlocks;		

	}
	
	/**
	 * 
	 * Returns "blockType.closed" if given shape (hitbox) is colliding with a block anywhere
	 * and "blockType.open" if there's no collision.
	 * 
	 * Used for bullet collision. Enemy collision not implemented, 
	 * just block collision, but should be a simple add on
	 * 
	 * @param hb
	 * @return blockTypes bt - type of block that is being collided with
	 * @throws SlickException
	 */
	public blockTypes entityCollisionWith(Shape hb) throws SlickException {
		boolean collision=false;
		
		ArrayList<Block> blocks= getCollisionBlockList(hb);
		//blockTypes bType = null;
		for (Block block : blocks)
		{
			if (block.getTileID() == 1 )
			{
				if(hb.intersects(block.getPoly()))
				{
					collision= true;
				}
			}				
			if(block.getTileID() == 2)
			{				
				if(hb.intersects(block.getPoly()))
					collision= true;
			}	
			if(block.getTileID() == 3)
			{
				if(hb.intersects(block.getPoly()))
				collision= true;
			}
			if(block.getTileID() == 4)
			{
				if(hb.intersects(block.getPoly()))
				collision= true;
			}
			if(block.getTileID() == 5)
			{
				if(hb.intersects(block.getPoly()))
				collision= true;
			}
		}
		if(collision)
			return blockTypes.closed;
		return blockTypes.open;
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