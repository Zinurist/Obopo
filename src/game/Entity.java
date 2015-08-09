package game;

import game.movingentities.Player;
import geometry.Hitbox;
import geometry.Polygon;
import gui.ImageData;

public abstract class Entity extends Tile {

	private static final long serialVersionUID = 1000L;
	
	private static int ID_COUNTER=0;
	
	protected double[] pos;
	protected boolean terminated;
	protected final int id;
	//private double maxRadius;
	protected Hitbox hitbox;
	
	public void setX(double x){
		pos[0]=x;
	}
	
	public void setY(double y){
		pos[1]=y;
	}
	
	public void setPosition(double x, double y){
		pos[0]=x;
		pos[1]=y;
	}
	
	public void setTerminated(boolean terminated){
		this.terminated=terminated;
	}
	
	public void setHitbox(Hitbox box){
		hitbox=box;
		hitbox.setOffset(pos);
	}
	
	public int getX(){
		return (int)Math.round(pos[0]);
	}
	
	public int getY(){
		return (int)Math.round(pos[1]);
	}
	
	public double getRealX(){
		return pos[0];
	}
	
	public double getRealY(){
		return pos[1];
	}
	
	public double getMiddleX(){
		return hitbox.getTranslatedMiddleX();
	}
	
	public double getMiddleY(){
		return hitbox.getTranslatedMiddleY();
	}
	
	public int getX2(){
		return getX()+getWidth()-1;
	}
	
	public int getY2(){
		return getY()+getHeight()-1;
	}
	
	public int getWidth(){
		return getImg().getWidth();
	}
	
	public int getHeight(){
		return getImg().getHeight();
	}
	
	public Hitbox getHitbox(){
		return hitbox;
	}
	
	public boolean isTerminated(){
		return terminated;
	}
	
	@Override
	public int hashCode(){
		return id;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Entity){
			return this.id == ((Entity)o).id;
		}
		
		return false;
	}
	
	protected Entity(ImageData iData, int iType, double x, double y,boolean solid){
		this(new Hitbox(new Polygon(iData.getImg(iType))),iData,iType,x,y,solid);
	}
	
	protected Entity(Hitbox hitbox,ImageData iData, int iType, double x, double y,boolean solid){
		super(iData,iType,solid);
		pos=new double[2];
		pos[0]=x;
		pos[1]=y;
		terminated=false;
		id=ID_COUNTER++;
		
		setHitbox(hitbox);
	}

	public abstract void collideLink(Player player,int tickNr);
	
}
