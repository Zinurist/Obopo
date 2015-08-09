package action;

import game.MovingEntity;
import gui.ImageData;

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
