package action;

import java.util.List;

public class SkillSlots {

	private Ability[] slots;
	
	public SkillSlots(int numSlots, List<Ability> ablist){
		slots=new Ability[numSlots];
		
		for(int i=0; i<numSlots;i++){
			slots[i]=new EmptyAbility();
		}
		
		for(int i=0; i< numSlots && i<ablist.size(); i++){
			add(ablist.get(i),i);
		}
	}
	
	public Ability[] getAbilities(){
		return slots;
	}
	
	public void add(Ability ab,int slot){
		slots[slot]=ab;
		
		for(int i=0; i<slots.length;i++){
			if(i!=slot && slots[i].equals(ab)){
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
