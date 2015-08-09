package gui;

import game.Entity;
import game.World;
import geometry.Polygon;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import controllers.Camera;

public class DebugLayer extends Layer {

	public DebugLayer(World world, Camera cam) {
		super(world, cam);
	}

	@Override
	public void draw(Graphics g) {
		List<Entity> ents=world.getCurrentRoom().getAllEntities();
		g.setColor(new Color(200,150,0,170));
		
		int[][] points;
		for(Entity e:ents){
			for(Polygon p: e.getHitbox().getPolygons()){
				points=cam.translatePolygon(p);
				g.fillPolygon(points[0], points[1], p.getNumPoints());
			}
		}
	}

}
