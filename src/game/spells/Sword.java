package game.spells;

import game.MovingEntity;
import game.Room;
import game.Spell;
import game.movingentities.Player;
import gui.ImageData;

public class Sword extends Spell{

	private static final long serialVersionUID = 3002L;
	
	//simple sword attack
	
	//TODO swing vs stab
	//TODO ->animation
	public Sword(double x, double y,int dir,MovingEntity caster) {
		super(ImageData.Sword, dir, x, y, 0, dir, 1, true,caster);
	}

	@Override
	public void collideEntity(MovingEntity e,int tickNr) {
		if(stepCounter==1){//only in the first step, or else duplicate damage will be dealt
			e.addHealth(-1);//different damage? attack stats/buffs?
		}
	}

	@Override
	public void turn(long time, Room r) {}

	@Override
	public void initAbilities() {}

	@Override
	public void collidePlayer(Player player,int tickNr) {}

	@Override
	public void init() {}

}
