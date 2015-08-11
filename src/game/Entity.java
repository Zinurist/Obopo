package game;

import game.movingentities.Player;
import geometry.Hitbox;
import geometry.Polygon;
import gui.ImageData;

/**
 * An Entity is a tile, which can have a variable size, has a hitbox can collide with other entities.
 */
public abstract class Entity extends Tile {

	private static final long serialVersionUID = 1000L;
	
	private static int ID_COUNTER=0;//counter for IDs, every entity has a unique ID, used as hash
	
	protected double[] pos;//[0]=x, [1]=y
	protected boolean terminated;//if true, this entity will be removed from the game in the next(?) update-step
	protected final int id;
	protected Hitbox hitbox;
	
	//getter/setter:
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
	
	//TODO add simpler constructors
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

	/**
	 * This is called when this entity collides with the player. These type of collision are usually handled differently than other collisions.
	 * Important: This function is called by the Room, collision is therefore also checked by the room.
	 * @param player the player
	 * @param tickNr number of tick/step, can be used to prevent double-activation in 2 following steps (see Spell)
	 */
	public abstract void collidePlayer(Player player,int tickNr);
	
}
