package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class VGun implements Weapon {
			
	private Shape weaponHitbox;
	private Animation weaponAnimation;
	private final int delay = 300;
	private int currentDelay;
	
	private final float projectileSpeed = .5f;
	private final float projectileYSpeed = .1f;
	//damage?
//			private boolean active;
	//Tween movement;
	
	public VGun(Shape shape, SpriteSheet sheet)
	{
		currentDelay = 0;
//				active=false;
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
		Circle shot, shot2; 
		if(direction)
			{
			shot = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
			shot2 = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
			}
		else
			{
			shot = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
			shot2 = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
			}
		projList.add(new Projectile(shot, direction, projectileSpeed, -projectileYSpeed));
		projList.add(new Projectile(shot2, direction, projectileSpeed, projectileYSpeed));
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
}
