/**
 * Projectiles have a shape, direction and speed
 * 
 */

package com.cyclight;

import org.newdawn.slick.geom.Shape;

public class Projectile{
	
	Shape projShape;
	boolean projDir;
	float projSpeed;
	
	public Projectile(Shape shape, boolean direction, float speed)
	{
		projShape=shape;
		projSpeed=speed;
		projDir=direction;
	}
}