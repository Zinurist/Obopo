package game;

import controllers.Game;
import game.movingentities.Player;
import gui.ImageData;

public abstract class Event extends Entity{

	private static final long serialVersionUID = 4000L;
	
	protected int width,height;
	protected Game game;
	protected Player player;
	
	protected Event(double x, double y, int width, int height,Game game) {
		super(ImageData.Empty, 0, x, y,false);
		this.width=width;
		this.height=height;
		this.game=game;
		player=null;
	}
	
	@Override
	public int getWidth(){
		return width;
	}
	
	@Override
	public int getHeight(){
		return height;
	}
	
	@Override
	public void collideLink(Player player,int tickNr) {
		this.player=player;
		start();
	}

	public abstract void start();
	public abstract void stop();
	
}
