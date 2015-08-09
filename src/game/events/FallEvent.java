package game.events;

import game.Event;
import controllers.Game;

public class FallEvent extends Event{

	private static final long serialVersionUID = 4004L;
	
	private int roomId;
	private double belowX,belowY;
	
	public FallEvent(double x, double y, int width, int height, Game game, int roomId, double belowX, double belowY) {
		super(x, y, width, height, game);
		this.roomId=roomId;
		this.belowX=belowX;
		this.belowY=belowY;
	}

	@Override
	public void start() {
		game.getWorld().setCurrentRoom(roomId);
		player.setPosition(player.getX()-pos[0]+belowX,player.getY()-pos[1]+belowY);
	}

	@Override
	public void stop() {
		
	}
}
