package controllers;

import java.util.List;

import game.World;
import gui.Layer;
import gui.MenuLayer;

/**
 * A MenuController represents a game menu. This class implements selection of menus using a simple cursor.
 */
public abstract class MenuController extends Controller{
	
	private String[] items;
	protected int cursor;
	//this MenuLayer should be the same in the list, an extra reference is used for easier access
	private MenuLayer layer;
	
	//time counter, used to make sure, pressing 'up' doesn't scroll through the menu too fast
	//'up'/'down' is processed currently every 100ms, see TIME_LIMIT
	private long timeC;
	private static final long TIME_LIMIT=100;
	
	public MenuController(Game game,World world,List<Layer> layers,Camera cam,MenuLayer layer,String[] items){
		super(game,world,layers,cam);
		this.items=items;
		cursor=0;
		this.layer=layer;
		timeC=0;
	}
	
	public int getNumOfItems(){
		return items.length;
	}
	
	public int getCursor(){
		return cursor;
	}
	
	public String[] getItems(){
		return items;
	}

	@Override
	public void input(int[] keys,long time) {
		timeC+=time;
		
		switch(keys[2]){
		case 10://ESC
			game.setActiveController(game.getRoomCont());
			break;
		case 11://Enter
			executeMenu();
			break;
		}
		
		if(timeC>=TIME_LIMIT){
			switch(keys[0]){
			case 2://down	
				if(cursor+1<items.length){
					cursor++;
				}else{
					cursor=0;
				}
				timeC=0;
				break;
			case 0://up
				if(cursor>=1){
					cursor--;
				}else{
					cursor=items.length-1;
				}
				timeC=0;
				break;
			}
		}
	}
	
	@Override
	public void init() {
		layer.setMenuController(this);
	}
	
	/**
	 * The method called then a player selects a menu item and presses 'Enter'.
	 * @return the new controller, not currently used!
	 */
	public abstract Controller executeMenu();

}
