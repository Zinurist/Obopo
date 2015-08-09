package game;

import gui.ImageData;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Tile implements Serializable{

	private static final long serialVersionUID = 100L;
	
	public static final int TILE_WIDTH=30;
	
	protected ImageData iData;
	protected int iType;
	protected boolean solid;
	
	protected Tile(ImageData iData,int iType,boolean solid){
		this.iData=iData;
		this.iType=iType;
		this.solid=solid;
	}
	
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
