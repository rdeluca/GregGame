/**
 * This is the map. It's full of Blocks.
 * 
 */

package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {
	public static TiledMap tmap;
	public static int mapWidthPixels;
	public static int mapHeightPixels;
	public static ArrayList<ArrayList<Block>> entities;
	public static ArrayList<Block> xList;

	/**
	 * 
	 * This will create a map from a TiledMap file
	 * 
	 * @param ref - the map to create
	 * @throws SlickException - No idea, but sometimes stuff breaks and it wants to throw this.
	 */
	public BlockMap(String ref) throws SlickException {
	

		entities = new ArrayList<ArrayList<Block>>();
		xList  = new ArrayList<Block>();

		tmap = new TiledMap(ref);
		mapWidthPixels = tmap.getWidth() * tmap.getTileWidth();
		mapHeightPixels = tmap.getHeight() * tmap.getTileHeight();

		
		//Get all the blocks in a row (which is the columns of the map) and put them in xList
		//Then put each of those in the list entities so we can logically get any block
		
		for (int x = 0; x < tmap.getWidth(); x++)
		{
			xList  = new ArrayList<Block>();
			for (int y = 0; y < tmap.getHeight(); y++)
			{
				int tileID = tmap.getTileId(x, y, 0);
				
				if(tileID==1||tileID==2||tileID==3)
					xList.add( new Block(x * 32, y * 32, tileID));
				else {
					xList.add( new Block(x * 32, y * 32, tileID));
				}
			}
			entities.add(xList);
		}
	}
	
	//Returns the map
	public TiledMap getTiledMap()
	{
		return tmap;
	}

	/**
	 * Returns a list of the blocks that comprimise the map
	 * @return entities
	 */
	public ArrayList<ArrayList<Block>> getBlocks() {
		return entities;
	}
	
	/**
	 * Gets the block from its x and y coordinates in the given blockMap
	 * @param x
	 * @param y
	 * @return Block block
	 */
	public Block get(int x, int y){
		Block block = entities.get(x).get(y);
		return block;
	}
}