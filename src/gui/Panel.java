package gui;


import java.awt.Graphics;

import javax.swing.JPanel;

import controllers.Game;


@SuppressWarnings("serial")
public class Panel extends JPanel {

	private Game game;
	
	public Panel(Game game){
		this.game=game;
	}
	
	@Override
	public void paintComponent(Graphics g){
		for(Layer l:game.getActiveController().getLayers()){
			l.draw(g);
		}
	}
}
