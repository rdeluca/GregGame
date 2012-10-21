/**
 * 
 * Player class. This is where I store all the stuff about the player.
 * 
 * 
 */

package com.cyclight.characters;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;

import com.cyclight.BasicGun;
import com.cyclight.Projectile;
import com.cyclight.VGun;
import com.cyclight.Weapon;

public class Player extends GameCharacter {

	//Not really used, but I assume at some point the player might be on-fire
	//or slowed/inhibited by water?
	enum playerStatus{
		none, fire, water
	}
	
	private playerStatus status;
	private boolean jumping=false;
	private boolean grounded=false;
	private boolean facing=true; //False left, true right    Could probably do this better...
	int maxJump=40;
	int jumpCounter=maxJump;
	float health = 100;
	SpriteSheet spriteSheet;
	ArrayList<Projectile> projectileList= new ArrayList<Projectile>();
	private int currentWeapon;
	//Weapons currently unimplemented
	private ArrayList<Weapon> weaponList;
	float projectileSpeed= 2.75f*0.18f;
	
	/**
	 * Create a 'Player'
	 * @param x 	- Starting xPos
	 * @param y 	- Starting yPos
	 * @param pp	- Player's hitbox polygon
	 * @param pa	- Player's animation
	 * @param sheet	- Player's spriteSheet
	 */
	public Player(int x, int y, Polygon pp, Animation pa, SpriteSheet sheet) {
		xPos = x;
		yPos = y;
		hitbox = pp;
		animation = pa;
		animation.setAutoUpdate(true);
		status = playerStatus.none;
/*		for (int frame = 0; frame < 3; frame++)
		{
			animation.addFrame(sheet.getSprite(0, 0), 150);
		}*/
		weaponList = new ArrayList<Weapon>();
		weaponList.add(new VGun(null, null)); //Change this line to switch weapons
		currentWeapon = 0;
		spriteSheet=sheet;
		
		
	}
	
	/*
	 * Is character jumping?
	 * @return boolean jumping
	 */
	public boolean isJumping()
	{
		return jumping;
	}
	
	/**
	 * Sets jumping to given state
	 * 
	 * @param jump 
	 */
	public void setJumping(boolean jump)
	{
		if(!jump)
		{
			setJumpCounter(0);
		}
		jumping=jump;
	}
	
	/**
	 * Is character grounded?
	 * @return
	 */
	public boolean isGrounded()
	{
		return grounded;
	}
	
	/**
	 * Sets grounded to given state
	 * if setting to true, reset the jump counter
	 * 
	 * @param grounded 
	 */
	public void setGrounded(boolean g)
	{
		if(g)
		{
			setJumpCounter(maxJump);
		}
		grounded=g;			

	}
	
	/**
	 * Change status to given playerStatus
	 * @param sc
	 */
	public void statusChange(playerStatus sc)
	{
		status=sc;
	}
	
	/**
	 * Gets playerStatus
	 * @return playerStatus status
	 */
	public playerStatus getStatus()
	{
		return status;
	}

	/**
	 * Lowers jumpCounter while you're jumping so you can't infinitely fly upwards
	 */
	public void jumpCountdown(int jcount) {
		jumpCounter-=jcount;
	}
	
	/**
	 * Gets jumpCounter
	 * @return jumpCounter
	 */
	public int getJumpCounter()
	{
		return jumpCounter;
	}
	
	/**
	 * Sets jumpCounter to given jCount
	 * @param jCount
	 */
	public void setJumpCounter(int jCount)
	{
		jumpCounter=jCount;
	}
	
	
	/**
	 * Adds a projectile with given speed and direction variables
	 * to the player's projectileList
	 * 
	 * @param direction
	 * @param projectileSpeed
	 */
	public void addProjectile(boolean direction, float projectileSpeed) {
		
	}
	
	/**
	 * If able, adds a projectile with the weapon's properties
	 * to the player's projectileList
	 */
	public void weaponAttack() {
		Weapon curWeapon = getWeapon();
		if(canAct() && curWeapon.canAttack())
		{
			addProjectile(getFacing(), projectileSpeed);
			curWeapon.attack(projectileList, hitbox, facing);
		}
	}

	/**
	 * Sets player's facing to given boolean
	 * @param b
	 */
	public void setFacing(boolean b) {
		facing=b;
		
	}
	
	/**
	 * Returns facing
	 * @return facing
	 */
	public boolean getFacing()
	{
		return facing;
	}

	/**
	 * Returns projectileList
	 * @return projectileList
	 */
	public ArrayList<Projectile> getProjectileList() {
		return projectileList;
	}

	/**
	 * Returns currently equipped weapon
	 * @return Weapon
	 */
	public Weapon getWeapon() {
		return weaponList.get(currentWeapon);	
	}

	public Image getSprite() {
		if(facing)
		{
			return spriteSheet.getSprite(0, 0);
		}
		else
		{
			
			return spriteSheet.getSprite(1, 0);
		}
	}
	
	/**
	 * Indicates whether the player is ready to do something.
	 * Might return false if the player is stunned or too busy swinging a weapon.
	 * @return True iff the player can do something.
	 */
	public boolean canAct()
	{
		return true;
	}
}