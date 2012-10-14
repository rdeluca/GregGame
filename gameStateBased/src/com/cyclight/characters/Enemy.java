package com.cyclight.characters;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;

import com.cyclight.Projectile;
import com.cyclight.Weapon;

public class Enemy extends GameCharacter {

	enum enemyStatus{
		none, fire, water
	}
	
	 EnemyAI AItype;
	private enemyStatus status;
	private boolean jumping=false;
	private boolean grounded=false;
	private boolean facing=false; //False left, true right
	int maxJump=40;
	int jumpCounter=maxJump;
	float health = 100;
	SpriteSheet spriteSheet;
	
	ArrayList<Projectile> projectileList= new ArrayList<Projectile>();
	private int numProjectiles=projectileList.size();
	private int maxProjectiles=3;
	private int currentWeapon;
	private ArrayList<Weapon> weaponList;
	
	public Enemy(int x, int y, Polygon pp, Animation pa, SpriteSheet sheet, EnemyAI ai ) {
		xPos = x;
		yPos = y;
		hitbox = pp;
		animation = pa;
		animation.setAutoUpdate(true);
		status = enemyStatus.none;
		//for (int frame = 0; frame < 3; frame++)
		{
		//	animation.addFrame(sheet.getSprite(0, 0), 150);
		}
		weaponList = new ArrayList<Weapon>();
		AItype=ai;
		spriteSheet=sheet;
		
	}
	
	public boolean isJumping()
	{
		return jumping;
	}
	
	public void setJumping(boolean jump)
	{
		if(!jump)
		{
			setJumpCounter(0);
		}
		jumping=jump;
	}
	
	public boolean isGrounded()
	{
		return grounded;
	}
	
	public void setGrounded(boolean g)
	{
		if(g)
		{
			setJumpCounter(maxJump);
		}
		grounded=g;			

	}
	
	public void statusChange(enemyStatus sc)
	{
		status=sc;
	}
	
	public enemyStatus getStatus()
	{
		return status;
	}

	public void jumpCountdown(int jcount) {
		jumpCounter-=jcount;
	}
	
	public int getJumpCounter()
	{
		return jumpCounter;
	}
	
	public void setJumpCounter(int jcount)
	{
		jumpCounter=jcount;
	}
	
	public void setXPos(float x)
	{
		this.xPos=x;
	}
	
	public void setYPos(float y)
	{
		this.yPos=y;
	}
	
	public void setCoords(float x, float y) {
		this.xPos=x;
		this.yPos=y;
	}
	
	public void addProjectile(boolean direction, int speed) {
		Circle shot; 
		if(direction)
			shot = new Circle(hitbox.getMaxX(), hitbox.getCenterY(), 5);
		else
			shot = new Circle(hitbox.getMinX(), hitbox.getCenterY(), 5);
		projectileList.add(new Projectile(shot, direction, speed));
		numProjectiles=projectileList.size();
	}
	
	public void removeProjectile(int numProj)
	{
		projectileList.remove(numProj);
		numProjectiles=projectileList.size();
	}

	public void setFacing(boolean b) {
		facing=b;
	}
	
	public boolean getFacing()
	{
		return facing;
	}

	public int getNumProjectiles() {
		return numProjectiles;
	}

	public ArrayList<Projectile> getProjectileList() {
		return projectileList;
	}
	
	public int getMaxProjectiles()
	{
		return maxProjectiles;
	}

	public Weapon getWeapon() {
		return weaponList.get(currentWeapon);	
	}

	public void checkActive(float PlayerXPos, float PlayerYPos) {
		boolean activate = false;
		switch (this.AItype.type)
		{
		case wander:
			float height = this.hitbox.getHeight();
			float max = this.getYPos()+height;
			float min = this.getYPos()-31;
			
			if(this.facing){
				// true right  E->     M
				if(PlayerYPos<max && PlayerYPos>min)
				{
						AItype.setActive(true);
				}
				else {
					
				}
				

			}
			else {//false left
				if(this.getXPos()>PlayerXPos)
				{
					
				}				
			}
			
			break;
		case statue:
			break;
		case ghost:
			break;
		}	
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

	public SpriteSheet getSpriteSheet() {
		// TODO Auto-generated method stub
		return spriteSheet;
	}

	public EnemyAI getAI() {
		// TODO Auto-generated method stub
		return AItype;
	}
}