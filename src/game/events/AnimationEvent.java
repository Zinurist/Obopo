package game.events;

import game.Event;
import controllers.Game;

public class AnimationEvent extends Event{

	private static final long serialVersionUID = 4001L;
	
	protected AnimationEvent(double x, double y, int width, int height,Game game) {
		super(x, y, width, height, game);
		
	}

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		
	}

}
