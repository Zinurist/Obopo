package gui;

import game.World;
import game.movingentities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import action.Ability;
import action.Buff;
import controllers.Camera;

/**
 * Draws information on the skills/abilities and (de)buffs of the player.
 * 
 * This layer should be drawn on the Entity Layer.
 */
public class AbilityLayer extends Layer {

	public AbilityLayer(World world,Camera cam){
		super(world,cam);
	}

	//no zoom, permanent shit
	@Override
	public void draw(Graphics g) {
		Player player=world.getPlayer();
		
		int count=5;
		BufferedImage img;
		//drawing abilities, using transparent image and cooldown bar, if it is in cooldown
		for(Ability a : player.getSkills()){
			if(a.onCooldown()){
				img=a.getTransparentImg();
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(count, (cam.getFrameHeight()-15), img.getWidth(), 10);
				g.setColor(Color.CYAN);
				g.fillRect(count, (cam.getFrameHeight()-15),(int)Math.round(img.getWidth()*a.getRestPercentageCD()), 10);
			}else{
				img=a.getImg();
			}
			
			g.drawImage(img, count, cam.getFrameHeight()-15-img.getHeight(), img.getWidth(), img.getHeight(), null);
			count+= 5+img.getWidth();
		}
		
		//draw buffs
		count=5;
		for(Buff b : player.getBuffs()){
			if(!b.isSecret()){
				img=b.getImg();
				
				if(! b.isPermamnent()){
					g.setColor(Color.DARK_GRAY);
					g.fillRect(count, 5+img.getHeight(), img.getWidth(), 10);
					g.setColor(Color.CYAN);
					g.fillRect(count, 5+img.getHeight(), (int)Math.round(img.getWidth()*b.getRestPercentageAC()), 10);
				}
				
				g.drawImage(img, count, (5), img.getWidth(), img.getHeight(), null);
				count+= 5+img.getWidth();
			}
		}
	}
	
	/*
	 * Zoom version: zooms icons of abilities etc. depending on camera's zoom
	 * 
	@Override
	public void draw(Graphics g) {
		Player player=world.getLink();
		
		int count=5;
		BufferedImage img;
		for(Ability a : player.getSkills()){
			if(a.onCooldown()){
				img=a.getTransparentImg();
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(count, (cam.getFrameHeight()-5), cam.getWidthOnScreen(img.getWidth()), cam.getHeightOnScreen(10));
				g.setColor(Color.CYAN);
				g.fillRect(count, (cam.getFrameHeight()-5),cam.getWidthOnScreen(img.getWidth()*a.getRestPercentageCD()), cam.getHeightOnScreen(10));
			}else{
				img=a.getImg();
			}
			
			g.drawImage(img, count, (cam.getFrameHeight()-5-cam.getHeightOnScreen(img.getHeight())), cam.getWidthOnScreen(img.getWidth()), cam.getHeightOnScreen(img.getHeight()), null);
			count+= 5+cam.getWidthOnScreen(img.getWidth());
		}
		
		count=5;
		for(Buff b : player.getBuffs()){
			if(!b.isSecret()){
				img=b.getImg();
				
				if(! b.isPermamnent()){
					g.setColor(Color.DARK_GRAY);
					g.fillRect(count, 5+cam.getHeightOnScreen(img.getHeight()), cam.getWidthOnScreen(img.getWidth()), cam.getHeightOnScreen(10));
					g.setColor(Color.CYAN);
					g.fillRect(count, 5+cam.getHeightOnScreen(img.getHeight()), cam.getWidthOnScreen(img.getWidth()*b.getRestPercentageAC()), cam.getHeightOnScreen(10));
				}
				
				g.drawImage(img, count, (5), cam.getWidthOnScreen(img.getWidth()), cam.getHeightOnScreen(img.getHeight()), null);
				count+= 5+cam.getWidthOnScreen(img.getWidth());
			}
		}
	}
	*/
}
