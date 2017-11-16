package dkeep.logic;

public class Hero extends Character {
	
	private boolean isArmed = false;
	
	/**
	 * Class constructor
	 */
	public Hero(int x, int y, char symbol) {
		super(x, y, symbol);
	}

	/**
	 * Function that sets if the hero is armed or not
	 * @param b Boolean to set isArmed
	 */
	public void setIsArmed(boolean b){
		isArmed =b;
	}
	
	/**
	 * @return A boolean saying if the hero is armed or not
	 */
	public boolean getIsArmed(){
		return isArmed;
	}
}
