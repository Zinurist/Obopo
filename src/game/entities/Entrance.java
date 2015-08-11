package game.entities;

import game.Entity;
import game.World;
import game.movingentities.Player;
import gui.ImageData;

public class Entrance extends Entity {

	private static final long serialVersionUID = 1001L;
	
	//Obsolete!
	//EntranceEvent should be used.
	//TODO remove this entity
	
	private int roomId;
	private World world;
	
	public Entrance(double x, double y,World world, int roomId) {
		super(ImageData.Empty, 0, x, y, false);
		this.world=world;
		this.roomId=roomId;
	}

	public int getRoomId(){
		return roomId;
	}

	@Override
	public void collidePlayer(Player player,int tickNr) {
		world.setCurrentRoom(roomId);//change room
	}
	
}
