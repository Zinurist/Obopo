package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.World;
import controllers.Camera;

public class BackgroundLayer extends Layer {

	public BackgroundLayer(World world, Camera cam) {
		super(world, cam);
	}

	@Override
	public void draw(Graphics g) {
		BufferedImage img=world.getCurrentRoom().getBackground();
		if(img!=null){
			g.drawImage(img,0,0,cam.getFrameWidth(),cam.getFrameHeight(),null);
		}
	}

}
