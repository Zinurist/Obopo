package action;

import java.util.List;

/**
 * SkillSlots manages the player's abilities and provides an easy interface to activate them. It also allows for skill placement by the player.
 */
public class SkillSlots {

	private Ability[] slots;//the slots
	
	public SkillSlots(int numSlots, List<Ability> ablist){
		slots=new Ability[numSlots];
		
		for(int i=0; i<numSlots;i++){
			slots[i]=new EmptyAbility();//filling with empty abilities
		}
		
		for(int i=0; i< numSlots && i<ablist.size(); i++){
			add(ablist.get(i),i);
		}
	}
	
	public Ability[] getAbilities(){
		return slots;
	}
	
	public void add(Ability ab,int slot){//no duplicate skills are allowed, TODO maybe change that
		slots[slot]=ab;
		
		for(int i=0; i<slots.length;i++){
			if(i!=slot && slots[i].equals(ab)){//IMPORTANT: only add abilities using this function, since it only looks for 1 other instance of this ability
				slots[i]=new EmptyAbility();
				break;
			}
		}
	}
	
	public void remove(int slot){
		slots[slot]=new EmptyAbility();
	}
	
	public boolean activate(int slot){
		return slots[slot].activate();
	}
}
