/**
 * PauseScreenState
 * 
 * This is the state for the 
 * Makes clickable menu button things 
 * Mostly taken from a tutorial
 * 
 */

package com.cyclight;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseScreenState extends BasicGameState {

	int stateID = -1;

	Image background = null;
	Image startGameOption = null;
	Image exitOption = null;

    PlayerInfo playerInfo = null;
	
	float startGameScale = 1;
	float exitScale = 1;
	Font font;
	
	
	PauseScreenState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// load the pause menu images
		background = new Image("res/blueBackground.png");
		Image menuOptions = new Image("res/menuoptions.png");

		// Image (startX, startY, height, width)
		startGameOption = menuOptions.getSubImage(0, 0, 377, 71);
		exitOption = menuOptions.getSubImage(0, 71, 377, 71);
		
		// Image (startX, startY, height, width)
		startGameOption = menuOptions.getSubImage(0, 0, 377, 71);
		exitOption = menuOptions.getSubImage(0, 71, 377, 71);
        font = new Font("Verdana", Font.BOLD, 20);

	}

    @SuppressWarnings("deprecation")
	public void render(GameContainer gc, StateBasedGame arg1, Graphics arg2) throws SlickException {
        // render the background
        background.draw(0, 0);
 

        // Draw Highscores
        int index = 1;
        int posY = 300;

        ArrayList<Weapon> playerInfoList = playerInfo.getWeaponList();
 
        for(Weapon weapon : playerInfoList )
        {
        	//UnicodeFont t= new UnicodeFont(font, size, true, false);
  

   //     	t.drawString(20, posY, " " + ( index < playerInfoList.size() ? "0" + index : "" + index) + "  ." + score, Color.orange);
        	//System.out.println(( index < playerInfoList.size() ? "0" + index : "" + index) );
        	
    		for(int i=0; i<playerInfoList.size();i++)
        	{

        	}
        	
        	index++;
            posY += 20;
        }
    }

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		Input input = gc.getInput();


	}

	public boolean collidesWith(int mouseX, int mouseY, int menuX, int menuY, int width, int height) {

		boolean collision = false;
		if ((mouseX >= menuX && mouseX <= menuX + width) && (mouseY >= menuY && mouseY <= menuY + height))
			collision = true;
		return collision;

	}

}