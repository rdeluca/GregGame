/**
 * Mostly unimplimented, for equipping different weapons that aren't a projectile attack
 * 
 */

package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class PiercingGun implements Weapon {
	
	private String weaponName= "crossbow";
	private Shape weaponHitbox;
	private Animation weaponAnimation;
	private final int delay = 300;
	private int currentDelay;
	private int numPiercing;
	
	private final float projectileSpeed = .5f;
	//damage?
//	private boolean active;
	//Tween movement;
	
	public PiercingGun(Shape shape, SpriteSheet sheet)
	{
		numPiercing=3;
		currentDelay = 0;
//		active=false;
		//weaponHitbox=shape;
		for (int frame = 0; frame < 3; frame++)
		{
		//	weaponAnimation.addFrame(sheet.getSprite(0, 0), 150);
		}
	}
	
	public Boolean canAttack()
	{
		return currentDelay <= 0;
	}
	
	public int attack(ArrayList<Projectile> projList, Polygon hitbox, boolean direction)
	{
		currentDelay = delay;
		Circle shot; 
		if(direction)
			shot = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
		else
			shot = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
		projList.add(new Projectile(shot, direction, projectileSpeed, numPiercing));
		return 0;
	}
	
	public Shape getWeaponHitbox()
	{
		return weaponHitbox;
	}
	
	public void update(int delta)
	{
		if(currentDelay > 0)
			currentDelay -= delta;
	}

	@Override
	public String getName() {
		return weaponName;
	}

	@Override
	public int getPiercing() {
		// TODO Auto-generated method stub
		return numPiercing;
	}
}
