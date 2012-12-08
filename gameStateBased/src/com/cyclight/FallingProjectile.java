package com.cyclight;

import org.newdawn.slick.geom.Shape;

public class FallingProjectile extends Projectile {

	public FallingProjectile(Shape shape, boolean direction, float speed,
			int pierce) {
		super(shape, direction, speed, pierce);
	}

	public FallingProjectile(Shape shape, boolean direction, float xSpeed,
			float ySpeed, int pierce) {
		super(shape, direction, xSpeed, ySpeed, pierce);
	}
	
	@Override
	public void update(int delta)
	{
		super.update(delta);
		ySpeed += delta*0.001f;
	}

}
