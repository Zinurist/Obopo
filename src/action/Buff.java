package action;

import game.MovingEntity;

import java.awt.image.BufferedImage;

public abstract class Buff extends TimedAbility{

	protected boolean permanent,hasStopped,secret;
	
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
