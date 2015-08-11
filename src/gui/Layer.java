package gui;

import game.World;

import java.awt.Graphics;

import controllers.Camera;

/**
 * A Layer is a gui element which draws a specific part of the game/window.
 * 
 * Usual layer order:
 * Background
 * Terrain
 * Entity
 * Ability
 * Debug
 * Menu
 * 
 * TODO layers:
 * Message Layer, Map Layer (->Ability Layer?)
 */
public abstract class Layer {

	//data which may be needed to draw information
	protected World world;
	protected Camera cam;
	
	protected Layer(World world,Camera cam){
		this.world=world;
		this.cam=cam;
	}
	
	/**
	 * This method is called at all repaint-updates and draws this layer.
	 * @param g Graphics-object from the JPanel
	 */
	public abstract void draw(Graphics g);
	
}
