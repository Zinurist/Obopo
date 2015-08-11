package action;

import game.MovingEntity;
import gui.ImageData;

/**
 * Simple move forward spell, which moves the entity forward for a set amount of time. Used to move enemies.
 * CD:1500ms
 * AC:1500ms, although these values are often changed to random values
 */
public class MoveForward extends TimedAbility{

	public MoveForward(MovingEntity caster) {
		super(5, "Move Forward", caster, ImageData.EmptyAbility.getImg(0), 1500, 1500);
	}

	@Override
	protected void action() {
		caster.goForward(1);
	}

	@Override
	protected void start() {
		
	}

	@Override
	protected void stop() {
		
	}

}
