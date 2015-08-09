package controllers;

import game.Tile;
import game.World;
import gui.AbilityLayer;
import gui.BackgroundLayer;
import gui.DebugLayer;
import gui.EntityLayer;
import gui.ImageData;
import gui.Layer;
import gui.MenuLayer;
import gui.Panel;
import gui.TerrainLayer;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Game extends Thread{

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
	
	public void setActiveController(Controller activeController){
		this.activeController=activeController;
		activeController.init();
	}
	
	private Game(){
		initWorld();
		initControllersAndLayers();
		initGUI();
	}
	
	private void initWorld() {
		world=new World(this);
	}
	
	private void initControllersAndLayers(){
		cam=new Camera(world,30*Tile.TILE_WIDTH,20*Tile.TILE_WIDTH);
		
		BackgroundLayer background=new BackgroundLayer(world,cam);
		TerrainLayer terrain=new TerrainLayer(world,cam);
		EntityLayer entity=new EntityLayer(world,cam);
		AbilityLayer ability=new AbilityLayer(world,cam);
		MenuLayer menu=new MenuLayer(world,cam);
		DebugLayer debug=new DebugLayer(world,cam);

		LinkedList<Layer> roomLayers=new LinkedList<Layer>();
		LinkedList<Layer> menuLayers=new LinkedList<Layer>();

		roomLayers.add(background);
		roomLayers.add(terrain);
		roomLayers.add(entity);
		roomLayers.add(ability);
		
		menuLayers.add(background);
		menuLayers.add(terrain);
		menuLayers.add(entity);
		menuLayers.add(menu);
		
		roomCont=new RoomController(this,world,roomLayers,cam,debug);
		menuCont=new GameMenuController(this,world,menuLayers,cam,menu);
		
		
		activeController=roomCont;
		this.inputCont=new Input();
		
	}
	
	private void initGUI() {
		panel=new Panel(this);
		panel.setPreferredSize(new Dimension(cam.getFrameWidth(),cam.getFrameHeight()));

		//frame=new EmptyWindow(panel, true);
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setContentPane(panel);
		frame.addKeyListener(inputCont);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	@Override
	public void run(){
		running=true;
		long timeOld=System.currentTimeMillis();
		long timeNew=0;
		long dif=0;
		long neededDif=20;
		int stepCount=0;
		while(running){
			timeNew=System.currentTimeMillis();
			dif+=timeNew-timeOld;
			timeOld=System.currentTimeMillis();
			
			if(dif>=neededDif){
				dif-=neededDif;
				step(neededDif);
				stepCount++;
				if(stepCount==5){
					ImageData.animate();
					stepCount=0;
				}
			}
			
			panel.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}
	
	
	public void step(long time) {
		activeController.input(inputCont.readInput(),time);
		cam.update();
	}

	public Camera getCam() {
		return cam;
	}
	
}
