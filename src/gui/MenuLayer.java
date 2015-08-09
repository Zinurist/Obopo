package gui;

import game.World;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import controllers.Camera;
import controllers.MenuController;

public class MenuLayer extends Layer{

	private MenuController menu;
	private Font font;
	
	public MenuLayer(World world,Camera cam) {
		super(world,cam);
		this.menu=null;
		font=new Font("18thCentury", Font.PLAIN,30);
		
	}
	
	public void setMenuController(MenuController menu){
		this.menu=menu;
	}


	@Override
	public void draw(Graphics g) {
		if(menu==null){
			//TODO error
			System.out.println("error");
		}else{
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.fillRect(40,40,cam.getFrameWidth()-80,cam.getFrameHeight()-80);
			
			g.setColor(Color.WHITE);
			int cursor=menu.getCursor();
			int count=80;
			int counter=0;
			for(String s: menu.getItems()){
				if(counter==cursor) {
					g.setColor(Color.YELLOW);
					g.drawString(">"+s, 65, count);
					g.setColor(Color.WHITE);
				}else{
					g.drawString(s, 70, count);
				}
				count+=40;
				counter++;
			}
		}
	}

}
