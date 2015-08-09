package gui;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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
	

	public static final RescaleOp TRANSPARENT=new RescaleOp(new float[]{1.0F,1.0F,1.0F,0.5F},new float[4],null);
	
	private static int Ani_Count=0;
	private static int Max_Ani_Count=1;
	public static void animate(){
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
	
	
	private BufferedImage[][] img;
	private int maxAni;
	
	private ImageData(String name, int numTypes, int numAnimations){
		img=new BufferedImage[numTypes][numAnimations];
		maxAni=numAnimations;
		
		for(int i=0; i<numTypes;i++){
			for(int j=0; j<numAnimations;j++){
				try {
					img[i][j]=ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/"+name+i+"_"+j+".png"));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, name+" "+i+"\n"+e.getMessage()+"\n"+e.getStackTrace(), "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		}
	}
	
	private ImageData(){
		img=new BufferedImage[1][1];
		maxAni=1;
		img[0][0]=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
	}
	
	public BufferedImage getImg(int type){
		return img[type][Ani_Count%maxAni];
	}
}
