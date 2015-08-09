package game.tiles;

import game.Tile;
import gui.ImageData;

public class Water extends Tile{

	private static final long serialVersionUID = 105L;
	
	public Water() {
		super(ImageData.Water, 0, true);
	}

}
