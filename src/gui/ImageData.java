package gui;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * This enum holds all image data. Image data functions as a signleton.
 * TODO redesign: not using enums, so that the game doesnt have to load all images at start
 */
public enum ImageData {

	Empty(),
	Hills("hill",1,1),
	Gate("hill",1,1),
	Player("player",4,2),
	Dragon("water",1,1),
	Key("water",1,1),
	Trap("water",1,1),
	Octopus("player",4,2),
	Water("water",1,4),
	Beach("beach",8,4),
	Entrance("water",1,1),
	Ground("ground",3,1),
	Dash("dash",1,1),
	Attack("attack",1,1),
	EmptyAbility("empty",1,1),
	Sword("sword",4,1),
	Rock("rock",1,1),
	RockSkill("rockskill",1,1),
	HealthPotion("hppotion",1,3),
	InvincibilityStar("star",1,1);
	
	//image operation, which makes an image more transparent TODO make images transparent beforehand, less calculating at program start
	public static final RescaleOp TRANSPARENT=new RescaleOp(new float[]{1.0F,1.0F,1.0F,0.5F},new float[4],null);
	
	private static int Ani_Count=0;//counter used for animation
	private static int Max_Ani_Count=1;//biggest number which has all animation numbers as factor
	public static void animate(){//called by the gameloop
		Ani_Count++;
		if(Ani_Count==Max_Ani_Count){
			Ani_Count=0;
		}
	}
	
	static{
		List<Integer> usedIntegers=new LinkedList<Integer>();
		for(ImageData d:ImageData.values()){
			if(!usedIntegers.contains(d.maxAni)){
				Max_Ani_Count*=d.maxAni;//->Max_Ani_Count always divisble by maxAni->no frame skipping
				usedIntegers.add(d.maxAni);
			}
		}
	}
	
	//animation data: animation frames and direction/types
	private BufferedImage[][] img;
	//max nubmer of animation frames
	private int maxAni;
	
	private ImageData(String name, int numTypes, int numAnimations){
		img=new BufferedImage[numTypes][numAnimations];
		maxAni=numAnimations;
		
		//reading the image files
		for(int i=0; i<numTypes;i++){
			for(int j=0; j<numAnimations;j++){
				try {
					img[i][j]=ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/"+name+i+"_"+j+".png"));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, name+" "+i+"\n"+e.getMessage()+"\n"+e.getStackTrace(), "Error 404", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		}
	}
	
	//used for empty images
	private ImageData(){
		img=new BufferedImage[1][1];
		maxAni=1;
		img[0][0]=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
	}
	
	//used to get the current animation image
	public BufferedImage getImg(int type){
		return img[type][Ani_Count%maxAni];
	}
}
