package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class collisionHandler
{
	private BlockMap map;

	public collisionHandler()
	{
	}

	public collisionHandler(BlockMap blockMap)
	{
		map=blockMap;
	}
	

	/** 
	* With a given input of direction, a speed and the hitbox to test against,
	* this method checks if the given hitbox has collision at the given speed
	* 
	* @return 
	**/
	public boolean checkCollisionDirection(float spd, String direction, Polygon hitbox)
	{
		boolean result = false;
		Polygon testPoly = new Polygon(hitbox.getPoints());

		switch (direction) {

		case "down":
			testPoly.setY(testPoly.getY() + spd);
			break;
		case "up":
			testPoly.setY(testPoly.getY() - spd);
			break;
		case "left":
			testPoly.setX(testPoly.getX() - spd);
			break;
		case "right":
			testPoly.setX(testPoly.getX() + spd);
			break;
		}

		result = collidingWithBlocks(testPoly);

		return result;
	}
	

	/**
	 *   Used to return an array of four booleans to detect which direction 
	 *   collision is coming from perhaps to detect directional knockback.
 	 * 
	 * @return collidedBlocks
	 * @throws SlickException
	 */
	public boolean[] directionalCollision(Shape hitbox)
	{
		ArrayList<Block> blocks = getCollisionBlockList(hitbox);
		boolean[] collidedBlocks = { false, false, false, false };
		
		for (int i=0; i<blocks.size(); i++)
		{
			Block block = blocks.get(i);
			if (block.getTileID() == 1 )
			{
				collidedBlocks[i]=true;
			}				
			else if (block.getTileID() == 2 || block.getTileID() == 3)
			{
				float x = hitbox.getMinX();
				float y = hitbox.getMinY();
				Coordinates c1 = new Coordinates(x,y);
				
				float x2 = hitbox.getMaxX();
				float y2 = hitbox.getMinY();
				Coordinates c2 = new Coordinates(x2,y2);	
								
				if(c1.intersects(block)||c2.intersects(block))
				{
					collidedBlocks[i]=true;
				}
			}
		}
		return collidedBlocks;		

	}
	
	 /** 
	 * Used to return an array of four booleans to detect which direction
	 * collision is coming from perhaps to detect directional knockback.
	 * 
	 * 
	 * @return collidedBlocks
	 */
	public  ArrayList<Block> getCollisionBlockList(Shape hb)
	{
		ArrayList<Block> bList = new ArrayList<Block>();

		Coordinates [] coords= new Coordinates[4];

		coords[0] = new Coordinates((float)Math.floor(hb.getMinX()/32), (float)Math.floor(hb.getMinY()/32));
		coords[1] = new Coordinates((float)Math.floor(hb.getMaxX()/32), (float)Math.floor(hb.getMinY()/32));
		coords[2] = new Coordinates((float)Math.floor(hb.getMaxX()/32), (float)Math.floor(hb.getMaxY()/32));
		coords[3] = new Coordinates((float)Math.floor(hb.getMinX()/32), (float)Math.floor(hb.getMaxY()/32));
		
		//For each corner of the block see what block it's in
		for(int i=0; i <4; i++)
		{
			Block e = map.get((int) coords[i].x, (int) coords[i].y);
			bList.add(e);
		}
		
		return bList;
	}
	
	
	private static final collisionHandler instance = new collisionHandler();
 
    public static collisionHandler getInstance() {
        return instance;
    }
    
    public BlockMap getMap() {
		return map;
	}
    
    public void setMap(BlockMap Bmap) {
		map = Bmap;
	}

    /**
     * Checks to see if given shape is colliding with blocks
     * 
     * @param projShape - Hitbox to check collision against
     * 
     * @return collision 
     */
	public boolean collidingWithBlocks(Shape projShape) {
		boolean collision = false;

		boolean[] boolList = directionalCollision(projShape);

		for (boolean bool : boolList) 
		{
			if (bool)
				collision = true;
		}

		return collision;
	}

	/**
	 * 
	 * @param speed
	 * @param direction
	 * @return true if direction is "down"
	 * @throws SlickException
	 */
	public boolean closeGap(Shape hitbox, String direction) {

		Shape testHitbox = new Polygon(hitbox.getPoints());
		int moveNum = -1;

		while (!collidingWithBlocks(testHitbox)) {
			moveNum++;
			move(testHitbox, direction, 1);
		}

		move(hitbox, direction, moveNum);

		// TODO: Make it so that grounded is calculated independently rather
		// than here
		if (direction.equals("down"))
			return true;
		return false;

	}

	/**
	 * Moves hitbox "direction" direction "speed" units
	 * 
	 * @param direction
	 *            - String up, down, left, or right
	 * @param speed
	 */
	public void move(Shape hitbox, String direction, float speed) {
		switch (direction.toLowerCase()) {
		case "down":
			hitbox.setY(hitbox.getY() + speed);
			break;
		case "up":
			hitbox.setY(hitbox.getY() - speed);
			break;
		case "left":
			hitbox.setX(hitbox.getX() - speed);
			break;
		case "right":
			hitbox.setX(hitbox.getX() + speed);
			break;
		default:
			throw (new Error("Not a direction"));
		}
	}

}