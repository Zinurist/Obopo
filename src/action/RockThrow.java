package action;

import game.MovingEntity;
import game.spells.Rock;
import gui.ImageData;

public class RockThrow extends Ability {
	
	public RockThrow(MovingEntity caster) {
		super(13, "Throw Rocks", caster, ImageData.RockSkill.getImg(0), 2000);
	}

	@Override
	protected void action() {
		
	}

	@Override
	protected void start() {
		caster.cast(new Rock(0,0,140.0,0,caster));
	}

	@Override
	protected void stop() {
		
	}

}
