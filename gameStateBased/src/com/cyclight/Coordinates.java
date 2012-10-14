/**
 * Coordinates x and y.
 * Created so I could create an "intersects" method to see if a pair of coordinates
 * intersects a given Block
 * 
 */

package com.cyclight;

public class Coordinates
{
	public float x,y;
	public Coordinates(float xCoord, float yCoord) {
		x=xCoord;
		y=yCoord;
	}
	
	/**
	 * Sees if Coordinates x and y intersect given block blockA
	 * @param blockA
	 * @return boolean result - Whether the coordinates intersect the block
	 */
	public boolean intersects(Block blockA)
	{
		boolean result=blockA.poly.contains(x, y);
		return result;

	}
}