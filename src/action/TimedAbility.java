package action;

import game.MovingEntity;

import java.awt.image.BufferedImage;

/**
 * An Extension of Ability: TimedAbilities can also have a casting time. Behaves like an Ability, expect it deactivates after a set amount of time instead of the next step.
 */
public abstract class TimedAbility extends Ability {

	protected long ac,actime;//counter and max active time
	
	public TimedAbility(int id,String name,MovingEntity caster,  BufferedImage image, long cooldownTime,long activeTime) {
		super(id,name,caster,image,cooldownTime);
		actime=activeTime;
		ac=actime;
	}
	
	public void setActiveTime(long activeTime){
		actime=activeTime;
	}
	
	@Override
	public void addTime(long time){
		super.addTime(time);
		if(ac<actime){
			ac+=time;
		}
	}
	
	@Override
	public boolean activate(){
		if(super.activate()){
			ac=0;
			return true;
		}
		return false;
	}
	
	@Override
	public void step(long time){
		addTime(time);
		setActivated(ac<actime);
		if(isActivated()){
			action();
		}
	}
	
	public double getRestPercentageAC() {//used for active bar
		return ((double)(actime-ac)/(double)actime);
	}

}
