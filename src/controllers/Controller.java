package controllers;

import java.util.List;

import game.World;
import gui.Layer;

public abstract class Controller {

	protected List<Layer> layers;
	
	protected Game game;
	protected World world;
	protected Camera cam;
	
	protected Controller(Game game,World world,List<Layer> layers,Camera cam){
		this.game=game;
		this.world=world;
		this.layers=layers;
		this.cam=cam;
	}

	public abstract void input(int[] keys,long time);
	public abstract void init();

	public List<Layer> getLayers() {
		return layers;
	}
	
}
