/**
 * This is a parent class for all character things 'Enemy's and 'Player's
 * All the stuff that both those classes will share because that's good software development practices.
 */

package com.cyclight.characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

public abstract class GameCharacter {

	float xPos = 0;
	float yPos = 0;
	Animation animation = null;
	Polygon hitbox = null;

	public float getXPos() {
		return xPos;
	}

	public float getYPos() {
		return yPos;
	}

	public Polygon getHitbox() {
		return hitbox;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setXPos(float x) {
		hitbox.setX(x);
		xPos = x;
	}

	public void setYPos(float y) {
		hitbox.setY(y);
		yPos = y;
	}

	public void setHitbox(Polygon hitbox) {
		this.hitbox = hitbox;
	}
	
	public abstract Image getSprite();

}