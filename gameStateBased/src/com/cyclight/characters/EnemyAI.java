package com.cyclight.characters;

public class EnemyAI{
	
	enum AIType
	{
		wander, statue, ghost
	}
	private boolean active;
	AIType type;
	
	public EnemyAI(AIType enemyType)
	{
		active=false;
		type=enemyType;
	}
	
	public void doStuff( boolean facing){		
		switch (type)
		{
		case wander:
			wanderActivites(active, facing);
			break;
		case statue:
			statueActivities(active, facing);
			break;
		case ghost:
			ghostActivities(active, facing);
			break;
		}
	}

	
	private void ghostActivities(boolean active, boolean facing) {
		
		if(active){
			//active
			
		}else {
			//move facing
		
		}
	}

	private void statueActivities(boolean active, boolean facing) {
		if(active){
			//active
			
		}else {
			//move facing
		
		}
	}

	private void wanderActivites(boolean active, boolean facing) {
		if(active){
			//active
			
			
			
		}else {
			//move facing
		
		}	
	}
	
	/**
	 * getter for active
	 * @return active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * setter for active
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public AIType getType() {
		return type;
	}
	
	
}