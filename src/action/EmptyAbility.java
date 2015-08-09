package action;

import gui.ImageData;

public class EmptyAbility extends Ability {

	public EmptyAbility() {
		super(-1,"Empty",null,ImageData.EmptyAbility.getImg(0),0);
	}

	@Override
	protected void action() {
		
	}

	@Override
	protected void start() {
		
	}

	@Override
	protected void stop() {
		
	}

}
