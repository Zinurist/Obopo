package controllers;

import game.Entity;
import game.MovingEntity;
import game.World;
import game.movingentities.Player;
import geometry.Polygon;

public class Camera {

	private World world;
	private Player player;
	private MovingEntity follow;
	private int frameWidth,frameHeight;
	private boolean followME;
	private int xOffset,yOffset;
	private double horizontalZoom,verticalZoom;
	
	public Camera(World world,int frameWidth, int frameHeight) {
		this.world=world;
		this.player=world.getLink();
		this.frameWidth=frameWidth;
		this.frameHeight=frameHeight;
		xOffset=0;
		yOffset=0;
		horizontalZoom=3.0;
		verticalZoom=3.0;
		
		follow(player);
	}

	public void update() {
		if(followME){
			centerOn(follow.getX()+follow.getWidth()/2,follow.getY()+follow.getHeight()/2);
		}
	}
	
	public void follow(MovingEntity me){
		setFollow(me);
		setFollowME(true);
	}
	
	public void setFollow(MovingEntity follow){
		this.follow=follow;
	}
	
	public void setFollowME(boolean followME){ 
		this.followME=followME;
	}
	
	public void centerOn(int x, int y){
		//xoffset+width/2=x
		xOffset=(int)Math.round(x*horizontalZoom-frameWidth/2);
		yOffset=(int)Math.round(y*verticalZoom-frameHeight/2);
		
		if(xOffset<0){
			xOffset=0;
		}else if(xOffset>getWidthOnScreen(world.getWidth())-frameWidth){
			xOffset=getWidthOnScreen(world.getWidth())-frameWidth;
		}
		
		if(yOffset<0){
			yOffset=0;
		}else if(yOffset>getHeightOnScreen(world.getHeight())-frameHeight){
			yOffset=getHeightOnScreen(world.getHeight())-frameHeight;
		}
		
	}
	
	public int getXOffset(){
		return xOffset;
	}
	
	public int getYOffset(){
		return yOffset;
	}
	
	public int getXOnScreen(int x){
		return (int)Math.round(x*horizontalZoom-xOffset);
	}
	
	public int getYOnScreen(int y){
		return (int)Math.round(y*verticalZoom-yOffset);
	}
	
	public int getXOnScreen(Entity e){
		//e.getX already rounds x, however, if e.getRealX is used, the character seems to "shake"
		return (int)Math.round(e.getX()*horizontalZoom-xOffset);
	}
	
	public int getYOnScreen(Entity e){
		return (int)Math.round(e.getY()*verticalZoom-yOffset);
	}
	
	public int getXOnScreen(Entity e,double xExtra){
		return (int)Math.round((e.getX()+xExtra)*horizontalZoom-xOffset);
	}
	
	public int getYOnScreen(Entity e,double yExtra){
		return (int)Math.round((e.getY()+yExtra)*verticalZoom-yOffset);
	}
	
	public int getWidthOnScreen(double width){
		return (int) Math.round(width*horizontalZoom);
	}
	
	public int getHeightOnScreen(double height){
		return (int) Math.round(height*verticalZoom);
	}
	
	public int getWidthOnScreen(Entity e){
		return (int) Math.round(e.getWidth()*horizontalZoom);
	}
	
	public int getHeightOnScreen(Entity e){
		return (int) Math.round(e.getHeight()*verticalZoom);
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public int[][] translatePolygon(Polygon p) {
		int num=p.getNumPoints();
		int[][] points=p.getPoints();
		double[] offset=p.getOffset();
		
		int[][] transPoints=new int[2][num];
		
		for(int i=0; i<num;i++){
			transPoints[0][i]=getXOnScreen(points[0][i]+(int)Math.round(offset[0]));
			transPoints[1][i]=getYOnScreen(points[1][i]+(int)Math.round(offset[1]));
		}
		return transPoints;
	}

	
}
