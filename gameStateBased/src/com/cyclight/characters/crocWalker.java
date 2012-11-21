package com.cyclight.characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

import com.cyclight.characters.EnemyAI.AIType;



public class crocWalker extends Enemy {
	
	static int spriteHeight=18;
	static int spriteWidth=32;
	int wanderDistance=32;
	int distanceWalked=wanderDistance;
	
	public crocWalker() throws SlickException {
		//int x, int y, Polygon pp, Animation pa, SpriteSheet sheet, EnemyAI ai

		super(-50, -50,  new Polygon(new float[] { 0, 0, 0 + spriteHeight, 0, 0 + spriteHeight, 0 + spriteWidth, 0, 0 + spriteWidth }), 
				new Animation(), new SpriteSheet(new Image("res/crocSheet.png"), spriteHeight, spriteWidth), new EnemyAI(AIType.wander));
	}
	
	public void onUpdate() 
	{
		move();
	}

	private void move() {
		if(getDistanceWalked()>0)
		{
			
		}
		else
		{
			setFacing(!getFacing());
		}
		
	}
	
	private int getDistanceWalked()
	{
		return distanceWalked;
	}
}