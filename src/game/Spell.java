package game;

import gui.ImageData;

public abstract class Spell extends MovingEntity {

	private static final long serialVersionUID = 3000L;
	
	protected MovingEntity caster;
	protected long stepCounter;
	
	protected Spell(ImageData iData, int iType, double x, double y,double v,int dir,int uses,boolean imageDir, MovingEntity caster) {
		super(iData, iType, x, y,v, uses, dir, imageDir);
		this.caster=caster;
		stepCounter=0;
	}
	
	protected Spell(ImageData iData, int iType, double x, double y,double v,int dir,int uses,boolean imageDir) {
		super(iData, iType, x, y,v, uses, dir, imageDir);
		this.caster=null;
		stepCounter=0;
	}
	
	protected Spell(ImageData iData, int iType, double x, double y,double v,int dir,int uses,boolean imageDir, boolean solid,MovingEntity caster) {
		super(iData, iType, x, y,v, uses, dir, imageDir,solid);
		this.caster=caster;
		stepCounter=0;
	}
	
	protected Spell(ImageData iData, int iType, double x, double y,double v,int dir,int uses,boolean imageDir,boolean solid) {
		super(iData, iType, x, y,v, uses, dir, imageDir,solid);
		this.caster=null;
		stepCounter=0;
	}
	
	public void setCaster(MovingEntity caster){
		this.caster=caster;
	}
	
	public abstract void collideEntity(MovingEntity e,int tickNr);
	//owner?

	@Override
	public void step(long time,Room r){
		super.step(time,r);
		stepCounter++;
	}
	
}
