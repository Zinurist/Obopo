package game.movingentities;

import game.MovingEntity;
import game.Room;
import game.entities.Key;
import geometry.Hitbox;
import geometry.Polygon;
import gui.ImageData;
import action.Ability;
import action.Attack;
import action.Dash;
import action.Invincibility;
import action.RockThrow;
import action.SkillSlots;
import action.Swiftness;

public class Player extends MovingEntity{

	private static final long serialVersionUID = 2001L;
	
	private int activeAbility;
	private boolean moveInput,ignoreInputs;//used for cutscenes etc. TODO change this system: change the controller to CutsceneController instead!
	private SkillSlots skills;//uses skillslot
	
	public Player(int x, int y,int dir) {
		super(ImageData.Player, dir, x, y,120.0,5, dir,true);
		this.dir=dir;
		activeAbility=-1;
		skills=new SkillSlots(4,abilities);
		moveInput=false;
		ignoreInputs=false;

		setHitbox(new Hitbox(new Polygon(new int[][]{{0,getImg().getWidth(),0},{0,0,getImg().getHeight()}})));
		/*int h=getImg().getHeight();
		int w=getImg().getHeight();
		int h2=h/2;
		int w2=w/2;
		setHitbox(new Hitbox(new Polygon(new int[][]{{w2,w,w2,0},{0,h2,h,h2}})));*/
	}
	
	public Ability[] getSkills(){
		return skills.getAbilities();
	}
	
	public boolean getIgnoreInputs(){
		return ignoreInputs;
	}
	
	public void setActiveAbility(int activeAbility){
		this.activeAbility=activeAbility;
	}

	public void setMoveInput(boolean moveInput){
		this.moveInput=moveInput;
	}
	
	public void setIgnoreInputs(boolean ignoreInputs) {
		this.ignoreInputs=ignoreInputs;
	}

	@Override
	public void turn(long time, Room r) {
		if(activeAbility!=-1){
			skills.activate(activeAbility);
			activeAbility=-1;
		}
		
		if(moveInput){//cant always move, e.g. crippled/frozen
			goForward(1);
		}
		
		//lookAt(500,500);
		
	}

	@Override
	public void initAbilities() {
		abilities.add(new Attack(this));
		abilities.add(new Dash(this));
		abilities.add(new Swiftness(this));
		abilities.add(new RockThrow(this));
		abilities.add(new Invincibility(this,1000));
	}
	
	@Override
	public void addHealth(int add){
		super.addHealth(add);
		if(vulnerable && add<0){
			addBuff(new Invincibility(this,1000));
		}
	}

	public void addKey(Key key) {
		// TODO handle keys
		
	}

	@Override
	public void collidePlayer(Player player,int tickNr) {
		System.out.println("m8, sth's wrong");
	}

	@Override
	public void init() {
		
	}

	public double getHealthRatio() {//used for health bar
		return (double)health/maxHealth;
	}
}
