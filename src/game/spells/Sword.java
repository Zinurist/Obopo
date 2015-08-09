package game.spells;

import game.MovingEntity;
import game.Room;
import game.Spell;
import game.movingentities.Player;
import gui.ImageData;

public class Sword extends Spell{

	private static final long serialVersionUID = 3002L;
	
	public Sword(double x, double y,int dir,MovingEntity caster) {
		super(ImageData.Sword, dir, x, y, 0, dir, 1, true,caster);
	}

	@Override
	public void collideEntity(MovingEntity e,int tickNr) {
		if(stepCounter==1){
			e.addHealth(-1);
		}
	}

	@Override
	public void turn(long time, Room r) {}

	@Override
	public void initAbilities() {}

	@Override
	public void collideLink(Player player,int tickNr) {}

	@Override
	public void init() {}

}
