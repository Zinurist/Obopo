package controllers;

import game.Tile;
import game.World;
import gui.AbilityLayer;
import gui.BackgroundLayer;
import gui.DebugLayer;
import gui.EmptyWindow;
import gui.EntityLayer;
import gui.ImageData;
import gui.Layer;
import gui.MenuLayer;
import gui.Panel;
import gui.TerrainLayer;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JFrame;

/**
 * Holds all data, controllers and gui elements of the game and manages them. Also handles the gameloop.
 */
public class Game extends Thread{

	//called by Launcher
	public static void startGame() {
		Game g=new Game();
		g.start();
	}
	
	//Controllers
	private RoomController roomCont;
	private MenuController menuCont;
	private Input inputCont;
	private Controller activeController;
	private Camera cam;
	
	//GUI
	private JFrame frame;
	private Panel panel;
	
	//Game data
	private World world;
	
	//state
	private boolean running;
	
	//-----------------GETTER-----------------
	
	public World getWorld(){
		return world;
	}
	
	public RoomController getRoomCont(){
		return roomCont;
	}
	
	public MenuController getMenuCont(){
		return menuCont;
	}
	
	public Controller getActiveController(){
		return activeController;
	}

	public Camera getCam() {
		return cam;
	}
	
	//-----------------SETTER-----------------
	
	public void setActiveController(Controller activeController){
		this.activeController=activeController;
		activeController.init();//init needs to be called
	}
	
	//----------------------------------------
	
	private Game(){
		initWorld();
		initControllersAndLayers();
		initGUI();
	}
	
	/**
	 * Creates the world object.
	 */
	private void initWorld() {
		world=new World(this);
	}
	
	/**
	 * Creates all controllers and layers and the camera. Also links them with the world (needs to exist already).
	 */
	private void initControllersAndLayers(){
		cam=new Camera(world,30*Tile.TILE_WIDTH,20*Tile.TILE_WIDTH);
		
		//layers, act as signeltons
		BackgroundLayer background=new BackgroundLayer(world,cam);
		TerrainLayer terrain=new TerrainLayer(world,cam);
		EntityLayer entity=new EntityLayer(world,cam);
		AbilityLayer ability=new AbilityLayer(world,cam);
		MenuLayer menu=new MenuLayer(world,cam);
		DebugLayer debug=new DebugLayer(world,cam);

		LinkedList<Layer> roomLayers=new LinkedList<Layer>();
		LinkedList<Layer> menuLayers=new LinkedList<Layer>();

		//graphical layers for normal gameplay
		roomLayers.add(background);
		roomLayers.add(terrain);
		roomLayers.add(entity);
		roomLayers.add(ability);
		
		//graphical layers for in-game menu
		menuLayers.add(background);
		menuLayers.add(terrain);
		menuLayers.add(entity);
		menuLayers.add(menu);
		
		//room and menu controller
		roomCont=new RoomController(this,world,roomLayers,cam,debug);
		menuCont=new GameMenuController(this,world,menuLayers,cam,menu);
		
		//starting with room controller (=normal gameplay)
		activeController=roomCont;
		
		//input controller to handle player input
		this.inputCont=new Input();
	}
	
	/**
	 * Creates GUI Elements, like the main window.
	 */
	private void initGUI() {
		panel=new Panel(this);
		panel.setPreferredSize(new Dimension(cam.getFrameWidth(),cam.getFrameHeight()));

		frame=new EmptyWindow(panel, true);
		//frame=new JFrame(); alternative: use normal frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//frame.setContentPane(panel);
		frame.addKeyListener(inputCont);//adding the input controller as keylistener
		frame.pack();
		
		frame.setVisible(true);
	}
	
	@Override
	public void run(){
		//gameloop
		running=true;
		
		//when dif = neededDif, the game is updated
		long timeOld=System.currentTimeMillis();
		long timeNew=0;
		long dif=0;
		long neededDif=20;//->update after 20ms
		int stepCount=0;//update-counter used for animation
		while(running){
			//calculating passed time
			timeNew=System.currentTimeMillis();
			dif+=timeNew-timeOld;
			timeOld=System.currentTimeMillis();
			
			if(dif>=neededDif){
				//->update
				//calling update-method with passed time
				step(dif);
				dif=0;
				stepCount++;
				if(stepCount==5){
					//After 5 steps (~100ms) -> animation update
					ImageData.animate();
					stepCount=0;
				}
			}
			
			//gui update
			panel.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Update method called by the game loop. Calls update-methods of other elements.
	 * @param time passed time since last update
	 */
	private void step(long time) {
		//calling steps:
		//roomcontroller.input	*1
		//->world.step			*1
		//-->room.step			*1
		//--->entities.step 	*x
		//---->this.turn	 	*1 (class dependent)
		//---->abilities.step 	*y
		//----->this.action 	*1 (class dependent)
		//---->buffs.step 		*z
		activeController.input(inputCont.readInput(),time);
		cam.update();
	}
	
}
