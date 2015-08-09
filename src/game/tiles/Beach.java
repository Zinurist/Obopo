package game.tiles;

import game.Tile;
import gui.ImageData;

public class Beach extends Tile {

	private static final long serialVersionUID = 103L;
	
	public Beach(int dir) {
		super(ImageData.Beach, dir, false);
	}

}
