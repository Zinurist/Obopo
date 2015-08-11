package game;

import gui.ImageData;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * The base data structure of objects in the game world. Tiles are usually simply images, but can be solid or non-solid blocks in the game world.
 */
public abstract class Tile implements Serializable{

	private static final long serialVersionUID = 100L;
	
	public static final int TILE_WIDTH=30;//default width of tiles
	
	protected ImageData iData;//image data reference
	protected int iType;//type, meaning direction etc.
	protected boolean solid;//solid = false -> entities can pass through this block
	
	protected Tile(ImageData iData,int iType,boolean solid){
		this.iData=iData;
		this.iType=iType;
		this.solid=solid;
	}
	
	//getter/setter:
	
	protected void setSolid(boolean solid){
		this.solid=solid;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public BufferedImage getImg(){
		return iData.getImg(iType);
	}
	
	public void setIData(ImageData iData){
		this.iData=iData;
	}
	
	public void setIType(int iType){
		this.iType=iType;
	}
	
}
