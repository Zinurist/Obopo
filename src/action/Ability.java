package action;

import game.MovingEntity;
import gui.ImageData;

import java.awt.image.BufferedImage;

/**
 * A Representation of Abilities with cooldowns.
 * Abilities are often connect with spells, in that they create a spell. They also have a caster, just like spells.
 */
public abstract class Ability {

	
	private long cd,cdtime;//cooldown counter and max time
	private boolean activated;
	private final String name;
	private final int id;
	private BufferedImage img,transparentImg;
	protected MovingEntity caster;
	
	public Ability(int id,String name,MovingEntity caster,  BufferedImage image, long cooldownTime){
		this.caster=caster;
		this.id=id;
		this.name=name;
		this.cdtime=cooldownTime;
		cd=cdtime;
		
		//transparent image is displayed when in cooldown, its created at startup (TODO better than that)
		img=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
		transparentImg=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(image, 0, 0, null);
		ImageData.TRANSPARENT.filter(img,transparentImg);

		activated=false;
	}
	
	public void setCoolDownTime(long cooldownTime){
		cdtime=cooldownTime;
	}//can dynamically change
	
	protected void setActivated(boolean activate){
		if(activated != activate){
			if(activate){
				start();
			}else{
				stop();
			}
		}
		
		activated=activate;
	}
	
	public long getRestSeconds(){
		return ((long)(cdtime-cd)/1000)+1;//+1, to round up
	}
	
	public BufferedImage getImg(){
		return img;
	}
	
	public String getName(){
		return name;
	}
	
	protected void addTime(long time){
		if(cd<cdtime){
			cd+=time;
		}
	}
	
	public boolean onCooldown(){
		return cd<cdtime;
	}
	
	public boolean isActivated(){
		return activated;
	}
	
	public boolean activate(){
		if(cd>=cdtime){
			cd=0;
			setActivated(true);
			return true;
		}
		return false;
	}
	
	public void step(long time){
		addTime(time);
		if(activated){//-> step 1: activate, next step 2: deactivate
			action();
			setActivated(false);
		}
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Ability){
			Ability a=(Ability)o;
			return id!=-1 && id==a.id;
		}
		return false;
	}
	
	protected abstract void action();//called in a step
	protected abstract void start();//called when activated
	protected abstract void stop();//called when deactivated, happens in the next step
	

	public double getRestPercentageCD() {//used for bars
		return ((double)(cdtime-cd)/(double)cdtime);
	}

	public BufferedImage getTransparentImg() {
		return transparentImg;
	}
	
}
