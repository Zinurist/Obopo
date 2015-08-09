package gui;

import game.Entity;
import game.World;
import game.movingentities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import controllers.Camera;

public class EntityLayer extends Layer {
	
	public EntityLayer(World world,Camera cam){
		super(world,cam);
	}
	
	
	@Override
	public void draw(Graphics g) {
		List<Entity> enti=world.getCurrentRoom().getAllEntities();
		
		for(Entity e:enti){
			g.drawImage(e.getImg(), cam.getXOnScreen(e), cam.getYOnScreen(e), cam.getWidthOnScreen(e),cam.getHeightOnScreen(e), null);
		}
		
		Player player=world.getLink();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(cam.getXOnScreen(player), cam.getYOnScreen(player,-10), cam.getWidthOnScreen(player), cam.getHeightOnScreen(5));
		g.setColor(Color.GREEN);
		g.fillRect(cam.getXOnScreen(player), cam.getYOnScreen(player,-10), cam.getWidthOnScreen(player.getWidth()*player.getHealthRatio()), cam.getHeightOnScreen(5));
		g.setColor(Color.BLACK);
		g.drawRect(cam.getXOnScreen(player)-1, cam.getYOnScreen(player,-10)-1, cam.getWidthOnScreen(player)+1, cam.getHeightOnScreen(5)+1);
	}

}
