package com.cyclight.characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

import com.cyclight.characters.EnemyAI.AIType;



public class crocWalker extends Enemy {
	
	static int spriteHeight=18;
	static int spriteWidth=32;
	int wanderDistance=32;
	int distanceWalked=32;
	
	float verticalSpeed =0.32f;   //Hardcoded but doesn't really mean anything
	float horizontalSpeed = 0.10f; //Hardcoded but doesn't really mean anything
	float gravity = verticalSpeed;
	
	
	public crocWalker() throws SlickException {
		//int x, int y, Polygon pp, Animation pa, SpriteSheet sheet, EnemyAI ai

		super(-50, -50,  new Polygon(new float[] { 0, 0, 0 + spriteHeight, 0, 0 + spriteHeight, 0 + spriteWidth, 0, 0 + spriteWidth }), 
				new Animation(), new SpriteSheet(new Image("res/crocSheet.png"), spriteHeight, spriteWidth), new EnemyAI(AIType.wander));

		if(Math.random()<.49)
		{
			setFacing(true);
		}
		else
		{
			setFacing(false);
		}
	}
	

	private void move(float horizontalSpeed) {
	
		if(!getGrounded() || getDistanceWalked()>0 )
		{	
			if (getFacing())
			{	
				if (!cHandler.checkCollisionDirection(horizontalSpeed, "right", this.getHitbox()))
				{
					cHandler.move(hitbox, "right", horizontalSpeed);
				}
				else
				{
					cHandler.closeGap(hitbox, "right");
					setFacing(!getFacing());
				}
			}
			else
			{

				if (!cHandler.checkCollisionDirection(horizontalSpeed, "left", this.getHitbox()))
				{
					cHandler.move(hitbox, "left", horizontalSpeed);
				}
				else
				{
					cHandler.closeGap(hitbox, "left");
					setFacing(!getFacing());
				}
			}
		}
		else 
		{
			setFacing(!getFacing());
			setDistanceWalked(wanderDistance);
		}
		
		setDistanceWalked(getDistanceWalked()-1);
	}
	
	private void setDistanceWalked(int f) {
		distanceWalked=f;
	}


	private int getDistanceWalked()
	{
		return distanceWalked;
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
	@Override
	public void handleMovement(Input input, int delta) {
		{
			float horizontalSpeed = this.horizontalSpeed*delta;
			float verticalSpeed = this.verticalSpeed*delta;
			float gravity = this.gravity*delta;


			move(horizontalSpeed);
			
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

		}
		
	}
}