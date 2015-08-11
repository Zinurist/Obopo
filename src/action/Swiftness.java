package action;

import game.MovingEntity;
import gui.ImageData;

/**
 * Makes the caster twice as fast as before.
 * CD:15s
 * AC:10s
 */
public class Swiftness extends TimedAbility {
	//TODO maybe change to buff
	public Swiftness(MovingEntity caster) {
		super(2,"Swiftness",caster,ImageData.Dash.getImg(0),15000,10000);
	}

	@Override
	protected void action() {
		
	}

	@Override
	protected void start() {
		caster.multiplyV(2.0);
	}

	@Override
	protected void stop() {
		caster.resetV();
	}
}
