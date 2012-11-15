package com.cyclight.test;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import com.cyclight.BlockMap;
import com.cyclight.collisionHandler;

public class testMovement {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() {

		BlockMap map = null;

		try {
			map = new BlockMap("res/level/testMap.tmx");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		collisionHandler cHandler = collisionHandler.getInstance();
		cHandler.setMap(map);


	}

}
