package com.cyclight;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class Block {
	public Polygon poly;
	private int tileID;
	private blockTypes type;
	/* Again coordinates are in this order:
		1----2
		|	 |
		4----3
	                 		 {x1,y1, x2,y2, x3,y3  x4,y4}  */
	private float square[] = { 1,1,  33,1,  33,33, 1,33 };        // square shaped
	private float botSquare[] = { 1, 11, 33, 11, 33, 33, 1, 33 }; // bothalf square
	private float topSquare[] = { 1, 1, 33, 1, 33, 22, 1, 22 };   // tophalf square
	private float rightDiagonal[] = { 33, 1,  33, 33, 1, 33 };   // tophalf square
	private float leftDiagonal[] = { 1, 1, 33, 33, 1, 33 };   // tophalf square
	
	//Block types... I only have used open and close so far. But cmon, fire and water.
	public enum blockTypes{
		open, closed, water, fire
	}
	
	/**
	 * Create the Block
	 * 
	 * @param x - xCoord of the block
	 * @param y - yCoord of the block
	 * @param tID - tiled's tileID
	 */
	public Block(int x, int y, int tID) {
		tileID=tID;
		setBlockType(tID);
		setBlockCoords(x,y);
	}

	/**
	 * sets the blockType to whatever blockType it is. Right now the only one that isn't 'open'
	 * is 'closed' so case 6-whatever will be other types. Like fire and water.
	 * @param tID
	 */
	public void setBlockType(int tID) {
		switch(tileID)
		{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				type = blockTypes.closed;
				break;
		}
		
	}
	
	/**
	 * sets the block's coordinates to comply with the block type
	 * that is if it is anything but a whole block it'll have a different
	 * shape, and this will fix that.
	 * 
	 * @param x - x position of the upper left coordinate
	 * @param y - y position of the upper left coordinate
	 * 
	 */
	public void setBlockCoords(int x, int y)
	{
		float [] coordinates=null;

		//Here's where Tiled comes into play, I have my tile set in this order:
		//square block, halfblock with tophalf open, halfblock with bothalf open,
		//Right diagonal piece, left diagonal piece, default - it's just a square
		switch(tileID){			
			case 1:
				coordinates=square;
				break;
			case 2:
				coordinates=botSquare;
				break;
			case 3:
				coordinates=topSquare;
				break;
			case 4:
				coordinates=rightDiagonal;
				break;
			case 5: 
				coordinates=leftDiagonal;
				break;
			default:
				coordinates=square;
				break;
		}

		//Set coordinates for the polygon, based on what type of block it is
		if(tileID==4||tileID==5)
		{
			//It's a right triangle block!
			poly = new Polygon(new float[]{
					x+coordinates[0], y+coordinates[1], //Top 
					x+coordinates[2], y+coordinates[3], //bottom right
					x+coordinates[4], y+coordinates[5], //bottom left
	        });   
	
		}
		else {
			poly = new Polygon(new float[]{
					x+coordinates[0], y+coordinates[1], //upper left
					x+coordinates[2], y+coordinates[3], //upper right
					x+coordinates[4], y+coordinates[5], //bottom right
					x+coordinates[6], y+coordinates[7], //bottom left
	        });  
		}
	}


	public blockTypes getBlockType(){
		return type;
	}
	
	public Polygon getPoly(){
		return poly;
	}
	
	public int getTileID()
	{
		return tileID;
	}
	
	public void update(int delta) {
	}

	public void draw(Graphics g) {
		g.draw(poly);
	}
}