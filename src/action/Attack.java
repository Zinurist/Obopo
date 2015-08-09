package action;

import game.MovingEntity;
import game.spells.Sword;
import gui.ImageData;

public class Attack extends TimedAbility{
	
	private Sword sword;
	
	public Attack(MovingEntity caster) {
		super(0,"Normal Attack",caster,ImageData.Attack.getImg(0),500,250);
	}

	@Override
	protected void action() {
		
	}

	@Override
	protected void start() {
		caster.setCanMove(false);
		sword=new Sword(0,0,0,caster);
		caster.cast(sword);
	}

	@Override
	protected void stop() {
		caster.setCanMove(true);
		sword.setTerminated(true);
	}

}
