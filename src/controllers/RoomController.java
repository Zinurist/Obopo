package controllers;

import game.World;
import game.movingentities.Player;
import gui.DebugLayer;
import gui.Layer;

import java.util.List;

public class RoomController extends Controller {
	
	private boolean debug;
	private DebugLayer debugL;
	
	public RoomController(Game game,World world,List<Layer> layers,Camera cam, DebugLayer debugL){
		super(game,world,layers,cam);
		debug=false;
		this.debugL=debugL;
	}
	
	private void toggleDebug(){
		debug=!debug;
		if(debug){
			layers.add(debugL);
		}else{
			layers.remove(debugL);
		}
			
	}

	@Override
	public void input(int[] keys,long time) {
		Player player=world.getLink();
		if(!player.getIgnoreInputs()){
			if(keys[0] >=0 && keys[0] <= 3){
				player.setMoveInput(true);
				player.setDirection(keys[0]);
			}else{
				player.setMoveInput(false);
			}
			
			
			if(keys[2]>=4 && keys[2]<=7){
				player.setActiveAbility(keys[2]-4);
			}else{
				switch(keys[2]){
				case 10://ESC
					game.setActiveController(game.getMenuCont());
					break;
				case 666://F3 Debug Mode
					toggleDebug();
					break;
				}
			}
		}
		
		world.step(time);
	}

	@Override
	public void init() {
		
	}

	
}
