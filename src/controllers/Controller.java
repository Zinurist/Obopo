package controllers;

import java.util.List;

import game.World;
import gui.Layer;

/**
 * Controller which handles user input and changes the gameworld/camera.
 */
public abstract class Controller {

	//graphical layers to draw
	protected List<Layer> layers;
	
	//game data
	protected Game game;
	protected World world;
	protected Camera cam;
	
	protected Controller(Game game,World world,List<Layer> layers,Camera cam){
		this.game=game;
		this.world=world;
		this.layers=layers;
		this.cam=cam;
	}

	/**
	 * Called to process user input.
	 * @param keys input generated from the input controller
	 * @param time passed time since last update
	 */
	public abstract void input(int[] keys,long time);
	
	/**
	 * Init-method called, when this Controller becomes the new active controller for the game. This method can be empty.
	 */
	public abstract void init();

	public List<Layer> getLayers() {
		return layers;
	}
	
}
