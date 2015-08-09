package game.entities;

import game.Entity;
import game.movingentities.Player;
import gui.ImageData;

public class Key extends Entity {

	private static final long serialVersionUID = 1003L;
	
	protected Key(int x, int y) {
		super(ImageData.Key, 0, x, y,false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collideLink(Player player,int tickNr) {
		player.addKey(this);
		terminated=true;
	}

}
