/**
 * MainMenuState
 * 
 * This is the state for the main menu
 * Makes clickable menu button things 
 * Mostly taken from a tutorial
 * 
 */

package com.cyclight;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

	int stateID = -1;

	Image background = null;
	Image startGameOption = null;
	Image exitOption = null;

	float scaleStep = 0.0001f;
	private static int titleX = 112;
	private static int titleY = 84;
	private static int menuX = titleX + 210;
	private static int menuY = titleY + 325;
	private static int menuOffset = 100;
	float startGameScale = 1;
	float exitScale = 1;

	MainMenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// load the menu images
		background = new Image("res/menu.png");
		Image menuOptions = new Image("res/menuoptions.png");

		// Image (startX, startY, height, width)
		startGameOption = menuOptions.getSubImage(0, 0, 377, 71);
		exitOption = menuOptions.getSubImage(0, 71, 377, 71);

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		// render the background
		background.draw(112, 84);

		// Draw menu
		startGameOption.draw(menuX, menuY, startGameScale);
		exitOption.draw(menuX - menuOffset, menuY + menuOffset, exitScale);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		Input input = gc.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		boolean insideStartGame = false;
		boolean insideExit = false;

		insideStartGame = collidesWith(mouseX, mouseY, menuX, menuY, startGameOption.getWidth(),
				startGameOption.getHeight());
		insideExit = collidesWith(mouseX, mouseY, menuX + menuOffset, menuY + menuOffset, exitOption.getWidth(),
				exitOption.getHeight());

		if (insideStartGame)
		{
			if (startGameScale < 1.05f)
				startGameScale += (scaleStep * delta);

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				sbg.enterState(stateBasedGameTest.GAMEPLAYSTATE);
			}
		}
		else
		{
			if (startGameScale > 1.0f)
				startGameScale -= scaleStep * delta;
		}

		if (insideExit)
		{
			if (exitScale < 1.05f)
				exitScale += scaleStep * delta;

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				gc.exit();
		}
		else
		{
			if (exitScale > 1.0f)
				exitScale -= scaleStep * delta;
		}

	}

	public boolean collidesWith(int mouseX, int mouseY, int menuX, int menuY, int width, int height) {

		boolean collision = false;
		if ((mouseX >= menuX && mouseX <= menuX + width) && (mouseY >= menuY && mouseY <= menuY + height))
			collision = true;
		return collision;

	}

}