/**
 * Projectiles have a shape, direction and speed
 * 
 */

package com.cyclight;

import org.newdawn.slick.geom.Shape;

public class Projectile{
	
	Shape projShape;
	boolean projDir;
	protected float xSpeed; //positive is right
	protected float ySpeed; //positive is down
	int numPierce;
	//damage?
	
	/**
	 * 
	 * @param shape shape of the projectile
	 * @param direction direction of the shooter
	 * @param speed Horizontal speed of the projectile, where positive is in the direction indicated by direction
	 */
	public Projectile(Shape shape, boolean direction, float speed, int pierce)
	{
		projShape=shape;
		this.xSpeed=speed*(direction?1:-1);
		this.ySpeed=0;
		projDir=direction;
		numPierce=pierce;
	}
	
	public Projectile(Shape shape, boolean direction, float xSpeed, float ySpeed, int pierce)
	{
		projShape=shape;
		this.xSpeed=xSpeed*(direction?1:-1);
		this.ySpeed=ySpeed;
		projDir=direction;
		numPierce=pierce;
	}
	
	public void update(int delta)
	{
		projShape.setX(projShape.getMinX()+xSpeed*delta);
		projShape.setY(projShape.getMinY()+ySpeed*delta);
	}

	/**
	 * Returns "isPiercing"
	 * If shot should disappear after hitting an enemy, shot is not "piercing"
	 * 
	 * @return isPiercing
	 * 
	 */
	public int getPiercing() {

		return numPierce;
	}
	
	public void setPiercing(int i)
	{
		numPierce = i;
	}
	
}