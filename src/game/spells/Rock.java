package game.spells;

import game.MovingEntity;
import game.Room;
import game.Spell;
import game.movingentities.Player;
import gui.ImageData;

public class Rock extends Spell{

	private static final long serialVersionUID = 3003L;
	
	//simple rock spell, creates a flying rock
	
	public Rock(double x, double y, double v,int dir, MovingEntity caster) {
		super(ImageData.Rock, 0, x, y, v, dir, 1,false, caster);
	}

	@Override
	public void collideEntity(MovingEntity e,int tickNr) {
		e.addHealth(-1);
		addHealth(-1);
	}

	@Override
	public void turn(long time, Room r) {
		goForward(1);
		if(!hasMoved){
			terminated=true;
		}
	}

	@Override
	public void initAbilities() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void collidePlayer(Player player,int tickNr) {
		player.addHealth(-1);
		addHealth(-1);
	}

}
