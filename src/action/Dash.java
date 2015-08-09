package action;

import game.MovingEntity;
import gui.ImageData;

public class Dash extends Ability{

	public Dash(MovingEntity caster) {
		super(1,"Dash",caster,ImageData.Dash.getImg(0),3000);
	}

	@Override
	protected void action() {
		
	}

	@Override
	protected void start() {
		caster.goForward(20,caster.getBaseV());
	}

	@Override
	protected void stop() {
		
	}

}
