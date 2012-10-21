/**
 * Projectiles have a shape, direction and speed
 * 
 */

package com.cyclight;

import org.newdawn.slick.geom.Shape;

public class Projectile{
	
	Shape projShape;
	boolean projDir;
	float xSpeed; //positive is right
	float ySpeed;
	//damage?
	
	/**
	 * 
	 * @param shape shape of the projectile
	 * @param direction direction of the shooter
	 * @param speed Horizontal speed of the projectile, where positive is in the direction indicated by direction
	 */
	public Projectile(Shape shape, boolean direction, float speed)
	{
		projShape=shape;
		this.xSpeed=speed*(direction?1:-1);
		this.ySpeed=0;
		projDir=direction;
	}
	
	public Projectile(Shape shape, boolean direction, float xSpeed, float ySpeed)
	{
		projShape=shape;
		this.xSpeed=xSpeed*(direction?1:-1);
		this.ySpeed=ySpeed;
		projDir=direction;
	}
	
	public void update(int delta)
	{
		projShape.setX(projShape.getMinX()+xSpeed*delta);
		projShape.setY(projShape.getMinY()+ySpeed*delta);
	}
}