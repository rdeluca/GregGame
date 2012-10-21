package com.cyclight;

import java.util.ArrayList;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public interface Weapon {

	/**
	 * Indicates whether the current weapon is ready to attack.
	 * Usually checks to make sure the weapon is finished cooling down and nothing else.
	 * @return true iff an attack can be made.
	 */
	public Boolean canAttack();
	
	/**
	 * Makes an attack with the weapon by returning a projectile that the player can add to their projectile list.
	 * Likely also makes the weapon go to a state in which it requires cooling down.
	 * Could also start up any relevant animations for the weapon itself.
	 * @param hitbox The hitbox of the player.
	 * @param direction The direction of the player.
	 * @return Some sort of identifier that indicates an animation for the player to enter (not used)
	 */
	public int attack(ArrayList<Projectile> projList, Polygon hitbox, boolean direction);
	
	
	public Shape getWeaponHitbox();
	
	/**
	 * Lets the weapon know time has passed.
	 * Usually advances the weapon's cooldown.
	 * @param delta time passed in milliseconds.
	 */
	public void update(int delta);
	
}
