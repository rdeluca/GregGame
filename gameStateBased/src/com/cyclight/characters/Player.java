/** 
 * Player class. This is where I store all the stuff about the 
 * player
 * 
 */

package com.cyclight.characters;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import com.cyclight.*;

public class Player extends GameCharacter {

	//Not really used, but I assume at some point the player might be on-fire
	//or slowed/inhibited by water?
	enum playerStatus{
		none, fire, water
	}
	
	private playerStatus status;
	private boolean jumping=false;
	private boolean grounded=false;
	private boolean facing=true; //False left, true right    Could probably do this better...

	PlayerInfo playerInfo;
	
	int maxJump=40;
	int jumpCounter=maxJump;
	float health = 100;
	SpriteSheet spriteSheet;
	ArrayList<Projectile> projectileList= new ArrayList<Projectile>();
	private int numProjectiles=projectileList.size();
	private int maxProjectiles=3;
	private int currentWeapon;

	int charHeight = 18;
	int charWidth = 32;
	float verticalSpeed =0.3f;   //Hardcoded but doesn't really mean anything
	float horizontalSpeed = 0.18f; //Hardcoded but doesn't really mean anything
	float gravity = verticalSpeed;

	
	collisionHandler collisionHandler;

	
	//Weapons currently unimplemented
	private ArrayList<Weapon> weaponList;
	float projectileSpeed= 2.75f*0.18f;
	
	/**
	 * Create a 'Player'
	 * @param x 	- Starting xPos
	 * @param y 	- Starting yPos
	 * @param pp	- Player's hitbox polygon
	 * @param pa	- Player's animation
	 * @param sheet	- Player's spriteSheet
	 */
	public Player(int startX, int startY, Animation pa) {
		
		//If I were to ever to animate the sprite rather than have a static block this would be useful
		Image gregImage = null;
		try {
			// gregImage = new Image("res/gregSheet.png");
			gregImage = new Image("res/square.png");

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SpriteSheet sheet = new SpriteSheet(gregImage, charHeight, charWidth);
		//Create a 'hitbox' polygon
		hitbox = new Polygon(new float[] { 0, 0, charHeight, 0,
				charHeight, charWidth, 0, charWidth});
		
		hitbox.setX(startX);
		hitbox.setY(startY);
		animation = pa;
		animation.setAutoUpdate(true);
		status = playerStatus.none;
/*		for (int frame = 0; frame < 3; frame++)
		{
			animation.addFrame(sheet.getSprite(0, 0), 150);
		}*/
		weaponList = new ArrayList<Weapon>();
		weaponList.add(new BasicGun(null, null));
		weaponList.add(new PiercingGun(null, null));
		weaponList.add(new WGun(null, null));
		weaponList.add(new ArcThrower(null,null));

		
		currentWeapon = 0;
		spriteSheet=sheet;

		collisionHandler = com.cyclight.collisionHandler.getInstance();
		

	}
	
	/*
	 * Is character jumping?
	 * @return boolean jumping
	 */
	public boolean isJumping()
	{
		return jumping;
	}
	
	/**
	 * Sets jumping to given state
	 * 
	 * @param jump 
	 */
	public void setJumping(boolean jump)
	{
		if(!jump)
		{
			setJumpCounter(0);
		}
		jumping=jump;
	}
	
	/**
	 * Is character grounded?
	 * @return
	 */
	public boolean getGrounded()
	{
		return grounded;
	}
	
	/**
	 * Sets grounded to given state
	 * if setting to true, reset the jump counter
	 * 
	 * @param grounded 
	 */
	public void setGrounded(boolean g)
	{
		if(g)
		{
			setJumpCounter(maxJump);
		}
		grounded=g;			

	}
	
	/**
	 * Change status to given playerStatus
	 * @param sc
	 */
	public void statusChange(playerStatus sc)
	{
		status=sc;
	}
	
	/**
	 * Gets playerStatus
	 * @return playerStatus status
	 */
	public playerStatus getStatus()
	{
		return status;
	}

	/**
	 * Lowers jumpCounter while you're jumping so you can't infinitely fly upwards
	 */
	public void jumpCountdown(int jcount) {
		jumpCounter-=jcount;
	}
	
	/**
	 * Gets jumpCounter
	 * @return jumpCounter
	 */
	public int getJumpCounter()
	{
		return jumpCounter;
	}
	
	/**
	 * Sets jumpCounter to given jCount
	 * @param jCount
	 */
	public void setJumpCounter(int jCount)
	{
		jumpCounter=jCount;
	}
	
	
	/**
	 * Adds a projectile with given speed and direction variables
	 * to the player's projectileList
	 * 
	 * @param direction
	 * @param projectileSpeed
	 */
	public void addProjectile(boolean direction, float projectileSpeed) {
		Circle shot; 
		if(direction)
			shot = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
		else
			shot = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
		projectileList.add(new Projectile(shot, direction, projectileSpeed, 1));
		numProjectiles=projectileList.size();
	}
	
 	/**
	 * If able, adds a projectile with the weapon's properties
	 * to the player's projectileList
 	 */
	public void weaponAttack() {
		Weapon curWeapon = getWeapon();
		if(canAct() && curWeapon.canAttack())
		{
			curWeapon.attack(projectileList, hitbox, facing);
		}
	}
	
	/**
	 * Sets player's facing to given boolean
	 * @param b
	 */
	public void setFacing(boolean b) {
		facing=b;
		
	}
	
	/**
	 * Returns facing
	 * @return facing
	 */
	public boolean getFacing()
	{
		return facing;
	}

	/**
	 * Returns number of projectiles
	 * @return numProjectiles
	 */
	public int getNumProjectiles() {
		return numProjectiles;
	}

	/**
	 * Returns projectileList
	 * @return projectileList
	 */
	public ArrayList<Projectile> getProjectileList() {
		return projectileList;
	}
	
	/**
	 * Returns maxProjectiles
	 * @return maxProjectiles
	 */
	public int getMaxProjectiles()
	{
		return maxProjectiles;
	}

	/**
	 * Returns currently equipped weapon
	 * @return Weapon
	 */
	public Weapon getWeapon() {
		return weaponList.get(currentWeapon);	
	}

	public Image getSprite() {
		if(facing)
		{
			return spriteSheet.getSprite(0, 0);
		}
		else
		{
			
			return spriteSheet.getSprite(1, 0);
		}
	}
	
	/**
	 * Indicates whether the player is ready to do something.
	 * Might return false if the player is stunned or too busy swinging a weapon.
	 * @return True iff the player can do something.
	 */
	 public boolean canAct()
	 {
		//ToDo: Change When Stun Implemented
		return true;
	}
	
	/**
	 * For character movement.
	 * Figure out direction and handle collisions.
	 * @param delta 
	 * @param arrayList 
	 *  
	 * @param Input - the Game Container's input.
	 * @throws SlickException
	 */
	private void handleMovement(Input input, int delta) {
		float horizontalSpeed = this.horizontalSpeed*delta;
		float verticalSpeed = this.verticalSpeed*delta;
		float gravity = this.gravity*delta;
		
		
		
		if (input.isKeyDown(Input.KEY_LEFT))
		{	
			setFacing(false);
			if (!collisionHandler.checkCollisionDirection(horizontalSpeed, "left", this.getHitbox()))
			{
				collisionHandler.move(hitbox, "left", horizontalSpeed);
			}
			else
			{
				collisionHandler.closeGap(hitbox, "left");
			}

		}
		if (input.isKeyDown(Input.KEY_RIGHT))
		{
			setFacing(true);
			if (!collisionHandler.checkCollisionDirection(horizontalSpeed, "right", this.getHitbox()))
			{
				collisionHandler.move(hitbox, "right", horizontalSpeed);
			}
			else
			{
				collisionHandler.closeGap(hitbox, "right");
			}
		}
		if (input.isKeyDown(Input.KEY_Z))
		{
			if(getGrounded())
			{ 
				//Not jumping, start jump

				setGrounded(false);
				jumpCountdown(1);		

				if (!collisionHandler.checkCollisionDirection(verticalSpeed, "up", this.getHitbox()))
				{
					setJumping(true);
					collisionHandler.move(hitbox, "up", verticalSpeed);
				}
				else
				{
					setJumping(false);					
					collisionHandler.closeGap(hitbox, "up");
				}

			}
			else if(isJumping() && !getGrounded())
			{
				//In the air, jumping
				jumpCountdown(1);	
					
				if (!collisionHandler.checkCollisionDirection(verticalSpeed, "up", this.getHitbox()))
				{
					if(getJumpCounter()<=0)
					{
						setJumping(false);
					}
					collisionHandler.move(hitbox, "up", verticalSpeed);
				}
				else
				{
					setJumping(false);					
					collisionHandler.closeGap(hitbox, "up");
				}
			}
			else if(!isJumping() && !getGrounded())
			{
				//Falling 
				//Let gravity do its thing.
			}
		}
		else if(isJumping())
		{
			setJumping(false);
		}

		// GRAVITY!
		if( !getGrounded() && !isJumping())
		{
			// Mid-air and not holding JUMP
			if (!collisionHandler.checkCollisionDirection(gravity, "down", this.getHitbox()))
			{
				collisionHandler.move(hitbox, "down", gravity);
			}
			else
			{
				collisionHandler.closeGap(hitbox, "down");
				setGrounded(true);				
			}
		}
		if(getGrounded()) 
		{ 
			if (!collisionHandler.checkCollisionDirection(gravity, "down", this.getHitbox()))
			{
				// If 'grounded' and in the air - then fall more
				collisionHandler.move(hitbox, "down", gravity);
				setGrounded(false);
			}
			else
			{
				collisionHandler.closeGap(hitbox, "down");
				setGrounded(true);
			}
		}
		if (input.isKeyDown(Input.KEY_DOWN))
		{
			//This should crouch or something at some time.
		}
	}

	@Override
	public void onUpdate(Input input, int delta) {
		// TODO Auto-generated method stub
		handleMovement(input, delta);
		
		weaponChange(input);
	}

	private void weaponChange(Input input) {
		if(input.isKeyPressed(Input.KEY_LSHIFT))
		{
			if(currentWeapon<weaponList.size()-1)
			{
				currentWeapon++;
			}
			else
			{
				currentWeapon=0;
			}
		}
	}
	
}