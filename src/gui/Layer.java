package gui;

import game.World;

import java.awt.Graphics;

import controllers.Camera;

public abstract class Layer {

	
	protected World world;
	protected Camera cam;
	
	protected Layer(World world,Camera cam){
		this.world=world;
		this.cam=cam;
	}
	
	public abstract void draw(Graphics g);
	
}
