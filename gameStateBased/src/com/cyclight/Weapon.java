/**
 * Mostly unimplimented, for equipping different weapons that aren't a projectile attack
 * 
 */

package com.cyclight;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

public class Weapon {
	
	private Shape weaponHitbox;
	private Animation weaponAnimation;
	private boolean active;
	//Tween movement;
	
	public Weapon(Shape shape, SpriteSheet sheet)
	{
		active=false;
		weaponHitbox=shape;
		for (int frame = 0; frame < 3; frame++)
		{
			weaponAnimation.addFrame(sheet.getSprite(0, 0), 150);
		}
	}
	
	public Shape getWeaponHitbox()
	{
		return weaponHitbox;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void activate()
	{
		active=true;
	}
}
