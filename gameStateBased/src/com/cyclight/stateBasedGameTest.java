/**
 * Runs the game, this is the main class
 * 
 */

package com.cyclight;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class stateBasedGameTest extends StateBasedGame {
		
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int PAUSESCREENSTATE =2;

	public stateBasedGameTest() {
		super("Cyclight");
	}

	public static void main(String[] args) throws SlickException {
		int maxFPS = 60;


		AppGameContainer app = new AppGameContainer(new stateBasedGameTest());
	      app.setTargetFrameRate(maxFPS);
	  	app.setVSync(true);
		app.setDisplayMode(1024, 768, false);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.addState(new PauseScreenState(PAUSESCREENSTATE));
	}
}