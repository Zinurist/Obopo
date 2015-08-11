package controllers;

import game.World;
import gui.Layer;
import gui.MenuLayer;

import java.util.List;

/**
 * The standard menu opened when the player presses 'ESC' during gameplay.
 */
public class GameMenuController extends MenuController {

	public GameMenuController(Game game, World world, List<Layer> layers,Camera cam, MenuLayer layer) {
		super(game, world, layers, cam, layer, new String[]{"Continue","Load","Save","Quit with saving","Quit without saving"});
	}

	@Override
	public Controller executeMenu() {
		//TODO more menuitems and savegame-location/name, maybe use FileMenuController
		switch(cursor){
		case 0://Continue
			game.setActiveController(game.getRoomCont());
			break;
		case 1://Load
			if(!world.load("")){
				//fail
			}
			break;
		case 2://Save
			if(!world.save("")){
				//fail
			}
			break;
		case 3://Quit with saving
			if(world.save("")){
				System.exit(0);
			}else{
				//fail
			}
			break;
		case 4://Quit without saving
			System.exit(0);
			break;
		}
		return this;
	}
}
