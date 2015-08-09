package game.movingentities;

import game.MovingEntity;
import game.Room;
import gui.ImageData;

import java.util.Random;

import action.MoveForward;
import action.RockThrow;
import action.TimedAbility;

public class Octopus extends MovingEntity{

	private static final long serialVersionUID = 2002L;
	
	public Octopus(int x, int y, int dir) {
		super(ImageData.Octopus, 0, x, y,100.0,3,dir,true);
	}

	@Override
	public void turn(long time, Room r) {
		Random rng=new Random();
		
		if(rng.nextDouble()<0.01){
			setDirection(rng.nextInt(4));
		}
		
		if(!abilities.get(0).isActivated()){
			if(rng.nextDouble()<0.01){
				abilities.get(0).activate();
			}else{
				((TimedAbility)abilities.get(0)).setActiveTime(rng.nextInt(1000)+1500);
			}
		}
		
		if(rng.nextDouble()<0.01){
			abilities.get(1).activate();
		}
		
	}

	@Override
	public void initAbilities() {
		abilities.add(new MoveForward(this));
		abilities.add(new RockThrow(this));
	}

	@Override
	public void collideLink(Player player,int tickNr) {
		player.addHealth(-1);
	}

	@Override
	public void init() {
		
	}

}
