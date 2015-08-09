package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EmptyWindow extends JFrame{
	
	private JPanel p;
	
	public EmptyWindow(JPanel panel, boolean moveable){
		setUndecorated(true);
		setAlwaysOnTop(true);
		setBackground(new Color(0, true));
		
		p=panel;		
		
		if(moveable){
			p.addMouseMotionListener(new MouseMotionListener() {
				private int xOld,yOld;
				@Override
				public void mouseMoved(MouseEvent me) {
					xOld=me.getXOnScreen();
					yOld=me.getYOnScreen();
				}
				@Override
				public void mouseDragged(MouseEvent me) {
					if(me.isControlDown()){
						addPos(me.getXOnScreen()-xOld,me.getYOnScreen()-yOld);
						xOld=me.getXOnScreen();
						yOld=me.getYOnScreen();
					}
				}
			});
		}
		
		setContentPane(p);
		pack();
		
		setVisible(true);
	}
	
	public void addPos(int xc, int yc){
		setLocation(getLocationOnScreen().x+xc, getLocationOnScreen().y+yc);
	}
}
