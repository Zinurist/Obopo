package action;

import game.MovingEntity;
import game.spells.Sword;
import gui.ImageData;

/**
 * Simple sword attack, that starts a sword-spell.
 * CD:500ms
 * AC:250ms
 */
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
		caster.setCanMove(false);//cant move during the attack
		sword=new Sword(0,0,0,caster);
		caster.cast(sword);
	}

	@Override
	protected void stop() {
		caster.setCanMove(true);//can move again
		sword.setTerminated(true);//remove the sword
	}

}
