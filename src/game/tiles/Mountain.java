package game.tiles;

import game.Tile;
import gui.ImageData;

public class Mountain extends Tile {

	private static final long serialVersionUID = 104L;
	
	public Mountain() {
		super(ImageData.Hills, 0, true);
	}

}
