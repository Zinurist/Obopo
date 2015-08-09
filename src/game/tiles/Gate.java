package game.tiles;

import game.Tile;
import gui.ImageData;

public class Gate extends Tile{
	
	private static final long serialVersionUID = 107L;
	
	private ImageData off,on;
	private int offType,onType;
	
	public Gate(boolean solid, ImageData off, int offType) {
		super(ImageData.Gate, 0, solid);
		this.off=off;
		this.offType=offType;
		this.on=iData;
		this.onType=iType;
	}
	
	public void toggle(){
		setSolid(!solid);
		if(solid){
			iData=on;
			iType=onType;
		}else{
			iData=off;
			iType=offType;
		}
	}
	
	
}
