package game.tiles;

import game.Tile;
import gui.ImageData;


public class Air extends Tile{

	private static final long serialVersionUID = 101L;
	
	protected Air() {
		super(ImageData.Empty, 0, false);
	}

}
