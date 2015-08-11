package controllers;

import game.World;
import game.movingentities.Player;
import gui.DebugLayer;
import gui.Layer;

import java.util.List;

/**
 * The standard game controller, represents normal gameplay.
 */
public class RoomController extends Controller {
	
	//debug mode draws hitboxes
	private boolean debug;
	private DebugLayer debugL;
	
	//DebugLayer is not part of the layers-list!
	public RoomController(Game game,World world,List<Layer> layers,Camera cam, DebugLayer debugL){
		super(game,world,layers,cam);
		debug=false;
		this.debugL=debugL;
	}
	
	/**
	 * Used to toggle debug-mode. Called when the player presses 'F3'.
	 */
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
		Player player=world.getPlayer();
		
		//input format:
		//keys[0] = last key pressed /direction
		//keys[1] = key queue /direction  (ex: player presses up then right -> up is in keys[1])
		//keys[2] = ability and game controlling stuff
		
		//used by e.g. Events (see CameraEvent)
		if(!player.getIgnoreInputs()){
			//0, 1, 2, 3 = up, right, down, left
			if(keys[0] >=0 && keys[0] <= 3){
				player.setMoveInput(true);
				player.setDirection(keys[0]);
			}else{
				player.setMoveInput(false);
			}
			
			//4-7 for abilities (4->'1', 5->'2' etc.)
			if(keys[2]>=4 && keys[2]<=7){
				player.setActiveAbility(keys[2]-4);//-4, b/c we count from 0-3 for abilities
			}else{
				switch(keys[2]){
				case 10://ESC
					//TODO getting menuCont from game-object is stooopid
					game.setActiveController(game.getMenuCont());//esc->open menu
					break;
				case 666://F3 Debug Mode
					toggleDebug();//debug, see above
					break;
				}
			}
		}
		
		//updating world
		world.step(time);
	}

	@Override
	public void init() {
		//nothin to do 'ere
	}

	
}
