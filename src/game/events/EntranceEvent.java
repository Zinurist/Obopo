package game.events;

import game.Event;
import controllers.Game;


public class EntranceEvent extends Event{

	private static final long serialVersionUID = 4003L;
	
	//like entrance-entity, but more flexible
	
	private int roomId;
	private double enterX,enterY;
	
	//TODO alternative constructor/event: enterX/enterY depending on player position -> replaces FallEvent
	public EntranceEvent(double x, double y, int width, int height, Game game, int roomId, double enterX, double enterY) {
		super(x, y, width, height, game);
		this.roomId=roomId;
		this.enterX=enterX;
		this.enterY=enterY;
	}

	@Override
	public void start() {
		game.getWorld().setCurrentRoom(roomId);
		player.setPosition(enterX,enterY);
	}

	@Override
	public void stop() {
		
	}

}
