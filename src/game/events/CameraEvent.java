package game.events;

import game.Event;
import game.spells.CameraMovement;
import controllers.Game;

public class CameraEvent extends Event{

	private static final long serialVersionUID = 4002L;
	
	
	private CameraMovement cm;
	
	public CameraEvent(double x, double y, int width, int height,double startX, double startY, double toX, double toY, double v, Game game) {
		super(x, y, width, height,game);
		cm=new CameraMovement(startX,startY,toX, toY, v,this);
	}

	@Override
	public void start() {
		setTerminated(true);
		game.getCam().follow(cm);
		player.setCanMove(false);
		player.setIgnoreInputs(true);
		player.cast(cm);
	}

	@Override
	public void stop() {
		game.getCam().follow(player);
		player.setCanMove(true);
		player.setIgnoreInputs(false);
	}	

}
