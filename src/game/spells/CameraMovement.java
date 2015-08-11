package game.spells;

import game.Event;
import game.MovingEntity;
import game.Room;
import game.Spell;
import game.movingentities.Player;
import gui.ImageData;


public class CameraMovement extends Spell{

	private static final long serialVersionUID = 3001L;
	
	//This spell is used by CameraEvent. Can also be used as moveTo-dummy for "EntityMoveEvent"
	
	private double toX,toY;
	private Event camEvent;
	
	//TODO maybe make movement time dependent on something different than the to-point
	public CameraMovement( double x, double y,double toX, double toY,double v,Event camEvent) {
		super(ImageData.Empty, 0, x, y, v,1, 1, false,false);
		this.toX=toX;
		this.toY=toY;
		this.camEvent=camEvent;
	}

	@Override
	public void turn(long time, Room r) {
		goTowards(toX, toY);
		if(!hasMoved){//stop when the point has been reached
			terminated=true;
			camEvent.stop();
		}
	}
	
	@Override
	public void initAbilities() {}

	@Override
	public void init() {}

	@Override
	public void collideLink(Player player,int tickNr) {}

	@Override
	public void collideEntity(MovingEntity e,int tickNr) {}

}
