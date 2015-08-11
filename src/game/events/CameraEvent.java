package game.events;

import game.Event;
import game.spells.CameraMovement;
import controllers.Game;

public class CameraEvent extends Event{

	private static final long serialVersionUID = 4002L;
	
	//creates a CameraMovement Object (=dummy), and makes the camera follow it
	
	private CameraMovement cm;
	
	public CameraEvent(double x, double y, int width, int height,double startX, double startY, double toX, double toY, double v, Game game) {
		super(x, y, width, height,game);
		cm=new CameraMovement(startX,startY,toX, toY, v,this);
	}

	@Override
	public void start() {
		setTerminated(true);//event gets removed from the game
		game.getCam().follow(cm);
		player.setCanMove(false);//player cant move
		player.setIgnoreInputs(true);//no input processing, mainly used by RoomControllers
		player.cast(cm);//start the camera movement as a spell
	}

	@Override
	public void stop() {
		game.getCam().follow(player);
		player.setCanMove(true);
		player.setIgnoreInputs(false);
	}	

}
