package controllers;

import game.World;
import gui.Layer;
import gui.MenuLayer;

import java.util.List;

public class GameMenuController extends MenuController {

	public GameMenuController(Game game, World world, List<Layer> layers,Camera cam, MenuLayer layer) {
		super(game, world, layers, cam, layer, new String[]{"Continue","Load","Save","Quit with saving","Quit without saving"});
	}

	@Override
	public Controller executeMenu() {
		switch(cursor){
		case 0:
			game.setActiveController(game.getRoomCont());
			break;
		case 1:
			if(!world.load("")){
				//fail
			}
			break;
		case 2:
			if(!world.save("")){
				//fail
			}
			break;
		case 3:
			if(world.save("")){
				System.exit(0);
			}else{
				//fail
			}
			break;
		case 4:
			System.exit(0);
			break;
		}
		return this;
	}
}
