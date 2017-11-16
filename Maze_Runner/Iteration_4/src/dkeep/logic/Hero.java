package dkeep.logic;

public class Hero extends Character {
	
	private boolean isArmed = false;
	
	public Hero(int x, int y, char symbol) {
		super(x, y, symbol);
	}
	
	public void setIsArmed(boolean b){
		isArmed =b;
	}
	
	public boolean getIsArmed(){
		return isArmed;
	}
}
