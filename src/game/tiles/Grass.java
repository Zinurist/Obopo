package game.tiles;

import game.Tile;
import gui.ImageData;

public class Grass extends Tile {

	private static final long serialVersionUID = 102L;
	
	public Grass() {
		super(ImageData.Ground, 1, false);
	}

}
