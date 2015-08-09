package game;

import game.entities.HealthPotion;
import game.movingentities.Player;
import game.movingentities.Octopus;
import game.tiles.Beach;
import game.tiles.Grass;
import game.tiles.Mountain;
import game.tiles.Sand;
import game.tiles.Water;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import controllers.Game;

public class Room implements Serializable{

	private static final long serialVersionUID = 10L;
	
	private BufferedImage background;
	
	private Tile[][] field;
	private int[] nextRooms;
	

	private final int id;
	private int width, height;
	

	private List<MovingEntity> movies;
	private List<Entity> items;
	private List<Spell> spells;
	private Player player;
	private int tickNr;
	
	public Room(Game game,int id,Player player){
		this.id=id;
		background=null;
		width=30;
		height=20;
		field=new Tile[width][height];
		for(int i=0; i<30;i++){
			for(int j=0; j<20;j++){
				if(j>16){
					field[i][j]=new Mountain();
				}else if(i>10 && j>10){
					field[i][j]=new Grass();
				}else if(i>=5 && j>=5){
					if(j==5 && i==5){
						field[i][j]=new Beach(7);
					}else if(j==5){
						field[i][j]=new Beach(0);
					}else if(i==5){
						field[i][j]=new Beach(6);
					}else{
						field[i][j]=new Sand();
					}
				}else{
					field[i][j]=new Water();
				}
			}
		}
		field[14][14]=new Mountain();
		
		
		items=new ArrayList<Entity>();
		movies=new ArrayList<MovingEntity>();
		spells=new LinkedList<Spell>();
		this.player=player;
		
		nextRooms=new int[4];
		nextRooms[1]=1;
		nextRooms[0]=-1;
		nextRooms[2]=-1;
		nextRooms[3]=-1;
		
		//movies.add(new Octopus(400,400,0));
		items.add(new HealthPotion(400,350));
		//items.add(new CameraEvent(500,500,10,10,600,600,200,200,100.0,game));
		
		init();
	}
	
	public Room(Game game,int id,Player player, String string) {
		this.id=id;
		background=null;
		width=30;
		height=20;
		field=new Tile[width][height];
		for(int i=0; i<30;i++){
			for(int j=0; j<20;j++){
				if(j>16){
					field[i][j]=new Mountain();
				}else if(j>10){
					field[i][j]=new Grass();
				}else if(j>=5){
					if(j==5){
						field[i][j]=new Beach(0);
					}else{
						field[i][j]=new Sand();
					}
				}else{
					field[i][j]=new Water();
				}
			}
		}
		items=new ArrayList<Entity>();
		movies=new ArrayList<MovingEntity>();
		spells=new LinkedList<Spell>();
		this.player=player;
		
		nextRooms=new int[4];
		nextRooms[1]=-1;
		nextRooms[0]=-1;
		nextRooms[2]=-1;
		nextRooms[3]=0;
		
		movies.add(new Octopus(400,400,0));
		
		init();
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean isSolid(int x, int y){
		return field[x][y].isSolid();
	}
	
	public List<Spell> getAllSpells(){
		//List<Spell> all=new ArrayList<Spell>(spells.size()+player.getSpells().size());
		List<Spell> all=new LinkedList<Spell>();
		all.addAll(spells);
		all.addAll(player.getSpells());
		return all;
	}
	
	public List<Entity> getAllEntities(){
		//List<Entity> all=new ArrayList<Entity>(items.size()+movies.size()+1);
		List<Entity> all=new LinkedList<Entity>();
		all.addAll(items);
		all.addAll(spells);
		all.addAll(player.getSpells());
		all.addAll(movies);
		all.add(player);
		return all;
	}
	
	public List<MovingEntity> getAllMovingEntities(){
		//List<Entity> all=new ArrayList<Entity>(items.size()+movies.size()+1);
		List<MovingEntity> all=new LinkedList<MovingEntity>();
		all.addAll(spells);
		all.addAll(movies);
		all.add(player);
		return all;
	}
	
	public Tile getTileAt(int x, int y){
		return field[x][y];
	}
	
	private void collisionHandling(int tickNr){
		//movingentities:
		//1.link
		//2.movies
		//3.spells
		
		//entities:
		//1.link
		//2.movies
		//3.spells
		//4.items
		
		
		
		//player x entities
		boolean collision=false;
		for(Entity e:movies){
			if(e.isSolid()){
				collision=player.collisionHandling(e,true);
			}else{
				collision=player.collisionCheck(e);
			}
			
			if(collision){
				e.collideLink(player, tickNr);
			}
		}
		for(Spell sp:spells){
			if(sp.isSolid()){
				collision=player.collisionHandling(sp,true);
			}else{
				collision=player.collisionCheck(sp);
			}
			
			if(collision){
				sp.collideLink(player,tickNr);
			}
		}
		for(Entity e:items){
			if(e.isSolid()){
				collision=player.collisionHandling(e,true);
			}else{
				collision=player.collisionCheck(e);
			}
			
			if(collision){
				e.collideLink(player, tickNr);
			}
		}
		
		int moviesL=movies.size();
		int spellsL=player.getSpells().size();
		int itemsL=items.size();
		MovingEntity me;
		Entity e;
		Spell sp;
		
		//movies x entities/player+checked_movies
		for(int i=0; i<moviesL; i++){
			me=movies.get(i);
			if(me.isSolid()){
				for(int k=i+1; k<moviesL; k++){
					e=movies.get(k);
					if(e.isSolid()){
						me.collisionHandling(e,true);
					}
				}
				for(int k=0; k<spellsL; k++){
					sp=player.getSpells().get(k);
					if(sp.isSolid()){
						collision=me.collisionHandling(sp,true);
					}else{
						collision=me.collisionCheck(sp);
					}
					
					if(collision){
						sp.collideEntity(me, tickNr);
					}
				}
				for(int k=0; k<itemsL; k++){
					e=items.get(k);
					if(e.isSolid()){
						me.collisionHandling(e,true);
					}
				}
			}
		}
		
	}

	private void updateLists(){
		//1.items
		//2.enemies
		//3.enemy-spells
		//4.link-spells
		for(int i=0; i<items.size();i++){
			if(items.get(i).isTerminated()){
				items.remove(i);
				i--;
			}
		}
		
		for(int i=0; i<movies.size();i++){
			if(movies.get(i).isTerminated()){
				movies.remove(i);
				i--;
			}
		}
		
		for(int i=0; i<spells.size();i++){
			if(spells.get(i).isTerminated()){
				spells.remove(i);
				i--;
			}
		}
		
		for(int j=0; j<player.getSpells().size();j++){
			if(player.getSpells().get(j).isTerminated()){
				player.getSpells().remove(j);
				j--;
			}
		}
	}
	
	public void postFix(){
		for(MovingEntity e:spells){
			e.postFix();
		}
		for(MovingEntity e:movies){
			e.postFix();
		}
		player.postFix();
		for(MovingEntity e:player.getSpells()){
			e.postFix();
		}
	}
	
	public int step(long time) {
		tickNr++;
		
		for(MovingEntity e: movies){
			e.step(time,this);
			spells.addAll(e.getSpells());
			e.emptySpells();
		}
		player.step(time,this);
		
		for(Spell s:spells){
			s.step(time,this);
		}
		for(Spell s:player.getSpells()){
			s.step(time,this);
		}
		
		
		
		
		collisionHandling(tickNr);
		
		updateLists();
		
		postFix();
		
		//next Room
		if(player.getX()==0 && nextRooms[3]>=0){
			player.setX(width*Tile.TILE_WIDTH-player.getWidth()-1);
			return nextRooms[3];
		}else if(player.getX2()==width*Tile.TILE_WIDTH-1 && nextRooms[1]>=0){
			player.setX(1);
			return nextRooms[1];
		}else if(player.getY()==0 && nextRooms[0]>=0){
			player.setY(height*Tile.TILE_WIDTH-player.getHeight()-1);
			return nextRooms[0];
		}else if(player.getY2()==height*Tile.TILE_WIDTH-1 && nextRooms[2]>=0){
			player.setY(1);
			return nextRooms[2];
		}
		
		return -1;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Room){
			return this.id==((Room)o).id;
		}
		
		return false;
	}
	
	@Override
	public int hashCode(){
		return id;
	}

	public void init() {
		tickNr=0;
		spells=new LinkedList<Spell>();
		for(MovingEntity e:movies){
			e.reinit();
		}
	}

	public BufferedImage getBackground() {
		return background;
	}
}
