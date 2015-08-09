package game;

import geometry.Geometry;
import gui.ImageData;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import action.Ability;
import action.Buff;

public abstract class MovingEntity extends Entity{

	private static final long serialVersionUID = 2000L;
	
	public static int toAngle(int dir){
		switch(dir){
		case 0:
			return 90;
		case 1:
			return 0;
		case 2:
			return 270;
		case 3:
			return 180;
		}
		return 0;
	}
	
	public static int toDir(int angle){
		if(angle >=45 && angle <135){
			return 0;
		}else if( angle >=135 && angle <225){
			return 3;
		}else if(angle >=225 && angle <315){
			return 2;
		}else{
			return 0;
		}
	}
	
	public static int[] toVec(int dir){
		switch(dir){
		case 0:
			return new int[]{0,-1};
		case 1:
			return new int[]{1,0};
		case 2:
			return new int[]{0,1};
		case 3:
			return new int[]{-1,0};
		}
		return new int[]{0,0};
	}
	
	public static final int AMP=2;
	
	protected int dir;
	protected double vx,vy,xNext,yNext;
	protected double v,baseV;
	protected double lastDis=Double.MAX_VALUE;
	protected int maxHealth,health;
	protected boolean vulnerable;
	protected boolean canMove,imageDir,hasMoved;
	protected List<Ability> abilities;
	protected List<Buff> buffs;
	protected List<Spell> spells; 
	
	protected double alpha;
	protected double rotSpeed;
	
	//1st
	protected MovingEntity(ImageData iData, int iType, double x, double y, double v, int maxHealth, double alpha, double rotSpeed, int dir, boolean imageDir, boolean solid) {
		super(iData, iType, x,y,solid);
		this.dir=dir;
		vx=0;
		vy=0;
		xNext=x;
		yNext=y;
		this.v=v;
		baseV=v;
		this.maxHealth=maxHealth;
		health=maxHealth;
		terminated=health<=0;
		this.imageDir=imageDir;
		this.alpha=alpha;
		this.rotSpeed=rotSpeed;
		
		spells=new LinkedList<Spell>();
		abilities=new LinkedList<Ability>();
		buffs=new LinkedList<Buff>();
		
		canMove=true;
		hasMoved=true;
		vulnerable=true;
		
		initAbilities();
		init();
	}
	
	//2nd
	protected MovingEntity(ImageData iData, int iType, double x, double y, double v, int maxHealth,  double rotSpeed, int dir, boolean imageDir, boolean solid) {
		this(iData, iType, x, y, v, maxHealth, rotSpeed, dir, 0, imageDir, solid);//1st
	}
	
	//3rd
	protected MovingEntity(ImageData iData, int iType, double x, double y, double v, int maxHealth, int dir, boolean imageDir, boolean solid) {
		this(iData, iType, x, y, v, maxHealth, 0, 5,dir, imageDir, solid);//1st
	}
	
	//4th
	protected MovingEntity(ImageData iData, int iType, double x, double y, double v, int maxHealth, int dir, boolean imageDir) {
		this(iData, iType, x, y, v, maxHealth, dir, imageDir, true);//3rd
	}
	
	//5th
	protected MovingEntity(ImageData iData, int iType, double x, double y, double v, double rotSpeed) {
		this(iData, iType, x, y, v, 10, rotSpeed,0, false, true);//2nd
	}
	
	
	public int getNextX(){
		return (int)Math.round(xNext);
	}
	
	public int getNextY(){
		return (int)Math.round(yNext);
	}
	
	public double getBaseV(){
		return baseV;
	}
	
	public List<Spell> getSpells(){
		return spells;
	}
	
	public int getDirection(){
		return dir;
	}
	
	public void setCanMove(boolean canMove){
		this.canMove=canMove;
	}
	
	public List<Ability> getAbilities(){
		return abilities;
	}
	
	public List<Buff> getBuffs(){
		return buffs;
	}

	public void setDirection(int newD){
		if(canMove){
			dir=newD;
			if(imageDir){
				iType=dir;
			}
		}
	}
	
	public void resetV(){
		this.v=baseV;
	}
	
	public void setV(double v){
		this.v=v;
	}
	
	public void setVulnerable(boolean vulnerable){
		this.vulnerable=vulnerable;
	}
	
	public void multiplyV(double mult){
		v=v*mult;
	}
	
	public void addSpell(Spell spell){
		spells.add(spell);
	}
	
	public void addBuff(Buff buff){
		buffs.add(buff);
	}
	
	public void addHealth(int add){
		if(vulnerable){
			health+=add;
			if(health >maxHealth){
				health=maxHealth;
			}else if(health <=0){
				health=0;
				terminated=true;
			}
		}
	}
	
	//MOVING FUNCTIONS
	
	public void goForward(double multiplier){
		goDir(dir,v,multiplier);
	}
	
	public void goForward(double multiplier, double v){
		goDir(dir,v,multiplier);
	}
	
	public void goForward(double multiplier, double v, int dir){
		goDir(dir,v,multiplier);
	}
	
	public void goTowards(double px, double py){
		double dx=px-pos[0];
		double dy=py-pos[1];
		double d=Math.sqrt(dx*dx+dy*dy);
		if(d>=lastDis){
			pos[0]=xNext=px;
			pos[1]=yNext=py;
			vx=0;
			vy=0;
			lastDis=Double.MAX_VALUE;
		}else{
			vx+=v*(dx/d);
			vy+=v*(dy/d);
			lastDis=d;
		}
	}
	
	public void goDir(int dir,double v, double multiplier){
		switch(dir){
		case 0:
			vy+=-v*multiplier;
			break;
		case 1:
			vx+=v*multiplier;
			break;
		case 2:
			vy+=v*multiplier;
			break;
		case 3:
			vx+=-v*multiplier;
			break;
		}
	}
	
	//ROTATION, WARNING: colision?!
	/**
	 * Rotates entity, so that it looks at point (px/py).
	 * @param px x-coor
	 * @param py y-coor
	 */
	public void lookAt(double px, double py){
		double dx=px-hitbox.getMiddleX();
		double dy=py-hitbox.getMiddleY();
		double alpha=Math.atan(dy/dx);
		rotate(alpha-this.alpha);
	}
	
	/**
	 * Rotating using rotation-speed
	 * @param time time passed in ms
	 */
	public void rotate(long time){
		rotate(rotSpeed*(time/1000.0));
	}
	
	/**
	 * Used for rotation which ignores time.
	 * @param alpha change of angle in radians
	 */
	public void rotate(double alpha){
		this.alpha+=alpha;
		hitbox.rotate(alpha);
		//image rotation TODO own image?
	}
	
	//TICK FUNCTIONS
	
	public void calcNextPosition(long time){
		xNext=pos[0]+vx*(double)time/1000.0;
		yNext=pos[1]+vy*(double)time/1000.0;
	}
	
	public void updatePosition(){
		//hasMoved=x!=xNext || y!=yNext;
		hasMoved=!(Math.abs((pos[0]-xNext) + (pos[1]-yNext)) <0.00000000000000000001);
		pos[0]=xNext;
		pos[1]=yNext;
	}
	
	//outdated
	public boolean correctPosition(Room r){
		int x1=getNextX()/Tile.TILE_WIDTH;
		int y1=getNextY()/Tile.TILE_WIDTH;
		int x2=(getWidth()+getNextX()-1)/Tile.TILE_WIDTH;
		int y2=(getHeight()+getNextY()-1)/Tile.TILE_WIDTH;
		
		boolean error=false;
		
		if(xNext<0){//xNext is used (not x1) because of rounding stuff (xNext=-0,5->x1=0 >no problem detected!)
			xNext=0;
			x1=0;
			x2=getWidth()/Tile.TILE_WIDTH;
			error=true;
		}else if(x2>=r.getWidth()){
			xNext=(r.getWidth()*Tile.TILE_WIDTH)-getWidth();
			x1=getNextX()/Tile.TILE_WIDTH;
			x2=(getWidth()+getNextX()-1)/Tile.TILE_WIDTH;
			error=true;
		}
		
		
		if(yNext<0){
			yNext=0;
			y1=0;
			y2=getHeight()/Tile.TILE_WIDTH;
			error=true;
		}else if(y2>=r.getHeight()){
			yNext=(r.getHeight()*Tile.TILE_WIDTH)-getHeight();
			y1=getNextY()/Tile.TILE_WIDTH;
			y2=(getHeight()+getNextY()-1)/Tile.TILE_WIDTH;
			error=true;
		}
		
		
		if(xNext-pos[0]>0){
			if(r.isSolid(x2, y1) || r.isSolid(x2, y2)){//right side
				xNext=x2*Tile.TILE_WIDTH-getWidth();
				error=true;
			}
		}else if(xNext-pos[0]<0){
			if(r.isSolid(x1, y1) || r.isSolid(x1, y2)){//left side
				xNext=(x1+1)*Tile.TILE_WIDTH;
				error=true;
			}
		}else if(yNext-pos[1]>0){//not else if-> need to update x1,x2,y1,y2 if error in x movement
			if(r.isSolid(x1, y2) || r.isSolid(x2, y2)){//down side
				yNext=y2*Tile.TILE_WIDTH-getHeight();
				error=true;
			}
		}else if(yNext-pos[1]<0){
			if(r.isSolid(x1, y1) || r.isSolid(x2, y1)){//up side
				yNext=(y1+1)*Tile.TILE_WIDTH;
				error=true;
			}
		}


		return error;
	}
	
	public void step(long time,Room r){
		for(Ability a:abilities){
			a.step(time);
		}
		Buff b;
		for(int i=0; i<buffs.size();i++){
			b=buffs.get(i);
			b.step(time);
			if(b.hasStopped()){
				buffs.remove(i);
				i--;
			}
		}
		
		turn(time,r);
		if(canMove){
			calcNextPosition(time);
			if(solid){//only can collide with solid walls if the object is solid->ghost are real
				boolean error=true;
				while(error){
					error=correctPosition(r);
				}
			}
			updatePosition();
		}
		
		
		/*
		for(MovingEntity e:spells){
			e.step(time,r);
		}*/
	}
	
	public void postFix(){
		vx=0;
		vy=0;
	}
	
	public abstract void turn(long time,Room r);
	public abstract void initAbilities();
	public abstract void init();
	
	public void reinit(){
		spells=new LinkedList<Spell>();
		init();
	}

	public void emptySpells() {
		spells=new LinkedList<Spell>();
	}
	
	public void cast(Spell spell){
		double x=this.pos[0] + getWidth()/2.0;
		double y=this.pos[1] + getHeight()/2.0;
		
		spell.setDirection(dir);
		BufferedImage simg=spell.getImg();
		switch(dir){
		case 0:
			y-=getHeight()/2.0 + simg.getHeight();
			x-=simg.getWidth()/2.0;
			break;
		case 1:
			y-=simg.getHeight()/2.0;
			x+=getWidth()/2.0;
			break;
		case 2:
			y+=getHeight()/2.0;
			x-=simg.getWidth()/2.0;
			break;
		case 3:
			y-=simg.getHeight()/2.0;
			x-=getWidth()/2.0 + simg.getWidth();
			break;
		}
		spell.setX(x);
		spell.setY(y);
		spell.setCaster(this);
		addSpell(spell);
	}

	/**
	 * Returns true, if this ME collides with given Entity e.
	 * @param e Entity e
	 * @return true->collision
	 */
	public boolean collisionCheck(Entity e){
		return Geometry.collision(this.hitbox, e.hitbox)!=null;
	}
	
	/**
	 * Returns true, if this ME collides with given Entity e. Also moves this ME, so that it doesn't collide with Entity e anymore.
	 * This method ignores solid-values of ME/e, these are handled in the collision-method of the room.
	 * @param e Entity e
	 * @param firstTime
	 * @return true->collision
	 */
	public boolean collisionHandling(Entity e, boolean firstTime){		
		double[][] colData;
		colData=Geometry.collision(this.hitbox, e.hitbox);
		if(colData!=null){
			if(vx==0 && vy==0 && e instanceof MovingEntity){
				((MovingEntity)e).collisionCorrection(colData);
			}else{
				collisionCorrection(colData);
			}
			//if(firstTime){
			//	this.collisionHandling(e,false);
			//}
			return true;
		}	
		return false;
	}
	
	public void collisionCorrection(double[][] colData ){
		//TODO this stuff
		
		//System.out.println(Arrays.toString(colData[0])+" "+Arrays.toString(colData[1]));
		double[] mvec=new double[]{0,0};
		double dx=Math.abs(colData[0][0]-colData[1][0])+0.1;
		double dy=Math.abs(colData[0][1]-colData[1][1])+0.1;
		
		double scaler=vx+vy;
		double importanceX=dx*(vx/scaler);
		double importanceY=dy*(vy/scaler);
		
		if(importanceX>importanceY){
			//mx=vx* dx/ Math.abs(vx)
			mvec[0]=vx* dx/ Math.abs(vx);
			//vy/vx=my/mx
			//my=mx*vy/vx
			mvec[1]=mvec[0]*vy/vx;
		}else{
			//my=vy* dy/ Math.abs(vy)
			mvec[1]=vy* dy/ Math.abs(vy);
			//vx/vy=mx/my
			//mx=my*vx/vy
			mvec[0]=mvec[1]*vx/vy;
		}

		pos[0]-=mvec[0];
		pos[1]-=mvec[1];
		//System.out.println("mvec0:"+mvec[0]+" mvec1:"+mvec[1]);
		//System.out.println("vx:"+vx+" vy:"+vy);
		//System.out.println("dx:"+dx+" dy:"+dy);
	}
}
