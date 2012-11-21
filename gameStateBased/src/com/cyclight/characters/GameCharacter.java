/**
 * This is a parent class for all character things 'Enemy's and 'Player's
 * All the stuff that both those classes will share because that's good software development practices.
 */

package com.cyclight.characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;


public abstract class GameCharacter {


	Animation animation = null;
	Polygon hitbox = null;

	public float getXPos() {
		return hitbox.getX();
	}

	public float getYPos() {
		return hitbox.getY();
	}

	public Polygon getHitbox() {
		return hitbox;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setXPos(float x) {
		hitbox.setLocation(x, getYPos());
	}

	public void setYPos(float y) {
		hitbox.setLocation(getXPos(), y);
	}

	public void setHitbox(Polygon hitbox) {
		this.hitbox = hitbox;
	}
	
	/**
	 * Creates a hitbox at given coordinates 
	 * and of given size
	 * 
	 * @param x coord
	 * @param y coord
	 * @param width
	 * @param height 
	 */
	public void setHitbox(float x, float y, float width, float height)
	{
		Polygon pp = new Polygon(new float[] { x, y, x + height, y, x + height,
				y + width, x, y + width});
		this.hitbox=pp;
	}
	

	
	public abstract Image getSprite();

	public abstract void onUpdate(Input input, int delta);
}