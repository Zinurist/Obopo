package gui;

import game.Room;
import game.Tile;
import game.World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import controllers.Camera;

/**
 * The layer of the terrain, i.e. the base ground like sand, water and mountain, on which the player walks.
 * 
 * This layer is usually drawn on the background layer, or as the first layer.
 */
public class TerrainLayer extends Layer {
	
	public TerrainLayer(World world,Camera cam){
		super(world,cam);
	}
	
	@Override
	public void draw(Graphics g) {
		Room r=world.getCurrentRoom();
		BufferedImage img;
		int widthOnScreen=cam.getWidthOnScreen(Tile.TILE_WIDTH);
		int heightOnScreen=cam.getHeightOnScreen(Tile.TILE_WIDTH);
		
		for(int i=0; i<r.getWidth();i++){
			for(int j=0; j<r.getHeight();j++){
				//to draw the ground, the tile-array of the world is used
				img=r.getTileAt(i,j).getImg();
				if(img!=null){
					g.drawImage(img, cam.getXOnScreen(i*Tile.TILE_WIDTH), cam.getYOnScreen(j*Tile.TILE_WIDTH),widthOnScreen,heightOnScreen, null);
				}
			}
		}
	}

}
