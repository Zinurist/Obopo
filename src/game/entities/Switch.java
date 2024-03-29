package game.entities;

import java.util.LinkedList;
import java.util.List;

import game.Entity;
import game.movingentities.Player;
import game.tiles.Gate;
import gui.ImageData;

public class Switch extends Entity{

	private static final long serialVersionUID = 1004L;
	
	
	private int activationTick;//needed to prevent double activation
	private List<Gate> gates;//list of gates
	
	//construction of switches when loading a save game may be complicated
	protected Switch( double x, double y) {
		super(ImageData.Hills, 0, x, y, true);
		activationTick=0;
		gates=new LinkedList<Gate>();
	}
	
	public void removeGate(Gate g){
		gates.remove(g);
	}
	
	public void addGate(Gate g){
		gates.add(g);
	}

	@Override
	public void collidePlayer(Player player, int tickNr) {
		if(activationTick!=tickNr-1){
			for(Gate g:gates){
				g.toggle();
			}
		}
		
		activationTick=tickNr;
	}

}
