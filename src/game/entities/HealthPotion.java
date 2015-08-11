package game.entities;

import game.Entity;
import game.movingentities.Player;
import gui.ImageData;

public class HealthPotion extends Entity {

	private static final long serialVersionUID = 1002L;
	
	//TODO different potions -> abstract Potion, subclasses of Potion
	
	public HealthPotion(double x, double y) {
		super(ImageData.HealthPotion, 0, x, y,false);
		
	}

	@Override
	public void collidePlayer(Player player,int tickNr) {
		player.addHealth(1);//maybe more, if health-system changes
		setTerminated(true);
	}

}
