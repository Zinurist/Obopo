package game;

import game.movingentities.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class World implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Map<Integer,Room> rooms;//all rooms mapped by their id
	private Room currentRoom;
	private Player player;
	
	private int width,height;
	
	public Room getRoom(int roomId){
		return rooms.get(roomId);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public World(){
		rooms=new HashMap<Integer,Room>();
		
		player=new Player(400, 300, 2);
		rooms.put(0, new Room(0,player));
		rooms.put(1, new Room(1,player,"Sand"));

		setCurrentRoom(0);
	}

	public Room getStartRoom() {
		return rooms.get(0);//should be 0
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public void setCurrentRoom(int index){
		currentRoom=rooms.get(index);
		width=currentRoom.getWidth()*Tile.TILE_WIDTH;//rooms are rectangles
		height=currentRoom.getHeight()*Tile.TILE_WIDTH;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public void step(long time) {
		int nextRoom=currentRoom.step(time);
		if(nextRoom!=-1){
			setCurrentRoom(nextRoom);
			currentRoom.init();//calling init on room change
		}
	}

	public boolean load(String file){
		return true;//totally not true
	}
	
	public boolean save(String file){
		return false;
	}
}
