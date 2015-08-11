package action;

import game.MovingEntity;

import java.awt.image.BufferedImage;

/**
 * A buff is a TimedAbility which acts more like a spell, in that it activates upon its creation. It can also be a permanent buff.
 */
public abstract class Buff extends TimedAbility{

	protected boolean permanent,hasStopped,secret;//TODO make own class for permanent buffs, find out, what secret/hasStopped is
	
	public Buff(int buffId, String name, MovingEntity caster, BufferedImage image,long activeTime,boolean secret) {
		super(buffId+1000, name, caster, image, 0, activeTime);
		hasStopped=false;
		permanent=activeTime==0;
		this.secret=secret;
		activate();
	}
	
	public Buff(int buffId, String name, MovingEntity caster, BufferedImage image,long activeTime) {
		super(buffId+1000, name, caster, image, 0, activeTime);
		hasStopped=false;
		permanent=activeTime==0;
		secret=false;
		activate();
	}
	
	public Buff(int buffId, String name, MovingEntity caster, BufferedImage image) {
		super(buffId+1000, name, caster, image, 0, 0);
		hasStopped=false;
		permanent=true;
		secret=false;
		activate();
	}
	
	public boolean hasStopped(){
		return hasStopped;
	}
	
	public boolean isSecret(){
		return secret;
	}
	
	@Override
	public void step(long time){
		addTime(time);
		setActivated(ac<actime  || permanent);
		if(isActivated()){
			action();
		}else{
			hasStopped=true;
		}
	}

	public boolean isPermamnent() {
		return permanent;
	}

}
