package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class WGun implements Weapon {
			
	private String weaponName= "V-Bow";
	private Shape weaponHitbox;
	private Animation weaponAnimation;
	private final int delay = 300;
	private int currentDelay;
	private int numPierce=1;
	private final float projectileSpeed = .5f;
	private final float projectileYSpeed = .1f;
	//damage?
//			private boolean active;
	//Tween movement;
	
	public WGun(Shape shape, SpriteSheet sheet)
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
		Circle shot, shot2, shot3; 
		if(direction)
			{
			shot = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
			shot2 = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
			shot3 = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
			}
		else
			{
			shot = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
			shot2 = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
			shot3 = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
			}
		projList.add(new Projectile(shot, direction, projectileSpeed, -projectileYSpeed, numPierce)); //up
		projList.add(new Projectile(shot2, direction, projectileSpeed, projectileYSpeed, numPierce)); //down
		projList.add(new Projectile(shot3, direction, projectileSpeed, 0, numPierce)); //forward
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
		return numPierce;
	}
}
