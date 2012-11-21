package com.cyclight.characters;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;

import com.cyclight.Projectile;
import com.cyclight.Weapon;
import com.cyclight.collisionHandler;


public abstract class Enemy extends GameCharacter {

	enum enemyStatus{
		none, fire, water
	}
	
	EnemyAI AItype;
	private enemyStatus status;
	private boolean jumping=false;
	private boolean grounded=false;
	private boolean facing=false; //False left, true right
	int maxJump=40;
	int jumpCounter=maxJump;
	float health = 100;
	SpriteSheet spriteSheet;
	
	ArrayList<Projectile> projectileList= new ArrayList<Projectile>();
	private int numProjectiles=projectileList.size();
	private int maxProjectiles=3;
	private int currentWeapon;
	private ArrayList<Weapon> weaponList;
	
	int charHeight = 18;
	int charWidth = 32;
	float verticalSpeed =0.3f;   //Hardcoded but doesn't really mean anything
	float horizontalSpeed = 0.18f; //Hardcoded but doesn't really mean anything
	float gravity = verticalSpeed;
	
	
	collisionHandler cHandler;
	
	
	public Enemy(int x, int y, Polygon pp, Animation pa, SpriteSheet sheet, EnemyAI ai ) {

		hitbox = pp;

		setXPos(x);
		setYPos(y);
		animation = pa;
		animation.setAutoUpdate(true);
		status = enemyStatus.none;
		//for (int frame = 0; frame < 3; frame++)
		{
		//	animation.addFrame(sheet.getSprite(0, 0), 150);
		}
		weaponList = new ArrayList<Weapon>();
		AItype=ai;
		spriteSheet=sheet;
		

		cHandler = collisionHandler.getInstance();
		
	}
	
	public boolean isJumping()
	{
		return jumping;
	}
	
	public void setJumping(boolean jump)
	{
		if(!jump)
		{
			setJumpCounter(0);
		}
		jumping=jump;
	}
	
	public boolean isGrounded()
	{
		return grounded;
	}
	
	public void setGrounded(boolean g)
	{
		if(g)
		{
			setJumpCounter(maxJump);
		}
		grounded=g;			

	}
	
	public void statusChange(enemyStatus sc)
	{
		status=sc;
	}
	
	public enemyStatus getStatus()
	{
		return status;
	}

	public void jumpCountdown(int jcount) {
		jumpCounter-=jcount;
	}
	
	public int getJumpCounter()
	{
		return jumpCounter;
	}
	
	public void setJumpCounter(int jcount)
	{
		jumpCounter=jcount;
	}
	

	public void setCoords(float x, float y) {
		setXPos(x);
		setYPos(y);
		// setHitbox(x, y, spriteSheet.getHeight(), spriteSheet.getWidth());
		
		
		
	}
	
	
	
	public void addProjectile(boolean direction, int speed) {
		Circle shot; 
		if(direction)
			shot = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
		else
			shot = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
		projectileList.add(new Projectile(shot, direction, speed));
		numProjectiles=projectileList.size();
	}
	
	public void removeProjectile(int numProj)
	{
		projectileList.remove(numProj);
		numProjectiles=projectileList.size();
	}

	public void setFacing(boolean b) {
		facing=b;
	}
	
	public boolean getFacing()
	{
		return facing;
	}

	public int getNumProjectiles() {
		return numProjectiles;
	}

	public ArrayList<Projectile> getProjectileList() {
		return projectileList;
	}
	
	public int getMaxProjectiles()
	{
		return maxProjectiles;
	}

	public Weapon getWeapon() {
		return weaponList.get(currentWeapon);	
	}

	public void checkActive(float PlayerXPos, float PlayerYPos) {
		
		switch (this.AItype.type)
		{
			case wander:
				float height = this.hitbox.getHeight();
				float max = this.getYPos()+height;
				float min = this.getYPos()-31;
				
				if(this.facing){
					// true right  E->     M
					if(PlayerYPos<max && PlayerYPos>min)
					{
							AItype.setActive(true);
					}
					else {
						
					}
					
				}
				else {//false left
					if(this.getXPos()>PlayerXPos)
					{	
					}				
				}
				
				break;
			case statue:
				break;
			case ghost:
				break;
		}	
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

	public SpriteSheet getSpriteSheet() {
		// TODO Auto-generated method stub
		return spriteSheet;
	}

	public EnemyAI getAI() {
		// TODO Auto-generated method stub
		return AItype;
	}

	@Override
	public void onUpdate(Input input, int delta) {
		// TODO Auto-generated method stub
		handleMovement(input, delta);
	}
	
	/**
	 * Is enemy grounded?
	 * 
	 * @return grounded
	 */
	public boolean getGrounded()
	{
		return grounded;
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
			if (!cHandler.checkCollisionDirection(horizontalSpeed, "left", this.getHitbox()))
			{
				cHandler.move(hitbox, "left", horizontalSpeed);
			}
			else
			{
				cHandler.closeGap(hitbox, "left");
			}

		}
		if (input.isKeyDown(Input.KEY_RIGHT))
		{
			setFacing(true);
			if (!cHandler.checkCollisionDirection(horizontalSpeed, "right", this.getHitbox()))
			{
				cHandler.move(hitbox, "right", horizontalSpeed);
			}
			else
			{
				cHandler.closeGap(hitbox, "right");
			}
		}
		if (input.isKeyDown(Input.KEY_Z))
		{
			if(getGrounded())
			{ 
				//Not jumping, start jump

				setGrounded(false);
				jumpCountdown(1);		

				if (!cHandler.checkCollisionDirection(verticalSpeed, "up", this.getHitbox()))
				{
					setJumping(true);
					cHandler.move(hitbox, "up", verticalSpeed);
				}
				else
				{
					setJumping(false);					
					cHandler.closeGap(hitbox, "up");
				}

			}
			else if(isJumping() && !getGrounded())
			{
				//In the air, jumping
				jumpCountdown(1);	
					
				if (!cHandler.checkCollisionDirection(verticalSpeed, "up", this.getHitbox()))
				{
					if(getJumpCounter()<=0)
					{
						setJumping(false);
					}
					cHandler.move(hitbox, "up", verticalSpeed);
				}
				else
				{
					setJumping(false);					
					cHandler.closeGap(hitbox, "up");
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
			if (!cHandler.checkCollisionDirection(gravity, "down", this.getHitbox()))
			{
				cHandler.move(hitbox, "down", gravity);
			}
			else
			{
				cHandler.closeGap(hitbox, "down");
				setGrounded(true);				
			}
		}
		if(getGrounded()) 
		{ 
			if (!cHandler.checkCollisionDirection(gravity, "down", this.getHitbox()))
			{
				// If 'grounded' and in the air - then fall more
				cHandler.move(hitbox, "down", gravity);
			}
			else
			{
				cHandler.closeGap(hitbox, "down");
				setGrounded(true);
			}
		}
		if (input.isKeyDown(Input.KEY_DOWN))
		{
			//This should crouch or something at some time.
		}
	}

}