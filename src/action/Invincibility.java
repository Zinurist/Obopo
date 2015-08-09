package action;

import game.MovingEntity;
import gui.ImageData;

public class Invincibility extends Buff {
	
	public Invincibility(MovingEntity caster,long activeTime) {
		super(100, "Invincibility", caster, ImageData.InvincibilityStar.getImg(0), activeTime);
	}

	@Override
	protected void action() {
		
	}

	@Override
	protected void start() {
		caster.setVulnerable(false);
	}

	@Override
	protected void stop() {
		caster.setVulnerable(true);
	}

}
