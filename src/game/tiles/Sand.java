package game.tiles;

import game.Tile;
import gui.ImageData;

public class Sand extends Tile {

	private static final long serialVersionUID = 106L;
	
	public Sand() {
		super(ImageData.Ground, 2, false);
	}

}
