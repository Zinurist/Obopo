package game.entities;

import game.Entity;
import game.movingentities.Player;
import gui.ImageData;

public class Key extends Entity {

	private static final long serialVersionUID = 1003L;
	
	//TODO key id which can be linked to specific doors and chests
	//TODO doors/chests which need keys
	
	protected Key(int x, int y) {
		super(ImageData.Key, 0, x, y,false);
	}

	@Override
	public void collideLink(Player player,int tickNr) {
		player.addKey(this);
		terminated=true;
	}

}
