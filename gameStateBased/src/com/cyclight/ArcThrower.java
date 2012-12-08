package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class ArcThrower implements Weapon {

	private String weaponName= "ArcThrower";
	private Shape weaponHitbox;
	private Animation weaponAnimation;
	private final int delay = 500;
	private final float projectileSpeed = 0.4f;
	private final float projectileYSpeed = 0.2f;
	private int currentDelay;
	private int numPiercing;
	
	public ArcThrower(Shape shape, SpriteSheet sheet)
	{
		numPiercing=0;
		currentDelay = 0;
//		active=false;
		//weaponHitbox=shape;
		for (int frame = 0; frame < 3; frame++)
		{
		//	weaponAnimation.addFrame(sheet.getSprite(0, 0), 150);
		}
	}
	
	@Override
	public Boolean canAttack() {
		return currentDelay <= 0;
	}

	@Override
	public int attack(ArrayList<Projectile> projList, Polygon hitbox,
			boolean direction) {
		currentDelay = delay;
		Circle shot; 
		if(direction)
			shot = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
		else
			shot = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
		projList.add(new FallingProjectile(shot, direction, projectileSpeed, -projectileYSpeed, 0)); //up
		return 0;
	}

	@Override
	public Shape getWeaponHitbox() {
		return weaponHitbox;
	}

	@Override
	public void update(int delta) {
		if(currentDelay > 0)
			currentDelay -= delta;
	}

	@Override
	public int getPiercing() {
		return 0;
	}

	@Override
	public String getName() {
		return weaponName;
	}

}
