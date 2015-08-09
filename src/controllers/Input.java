package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener{

	private int[] keyChain;
	private int[] input;
	
	public Input(){
		keyChain=new int[]{-1,-1,-1,-1};
		input=new int[]{-1,-1,-1};
	}
	
	public int[] readInput(){
		int[] rem=new int[]{input[0],input[1],input[2]};
		input[2]=-1;
		return rem;
	}
	
	private void toTop(int dir){
		if(keyChain[0]!=dir){
			int[] nkey=new int[]{dir,-1,-1,-1};
			int count=0;
			for(int i=1; i<4;i++){
				if(keyChain[count]!=dir){
					nkey[i]=keyChain[count];
				}
				count++;
			}
			keyChain=nkey;
		}
		input[0]=keyChain[0];
		input[1]=keyChain[1];
	}
	
	private void remove(int dir){
		int index=3;
		for(int i=0; i<4;i++){
			if(keyChain[i]==dir){
				keyChain[i]=-1;
				index=i;
			}
		}
		for(int i=index; i<3;i++){
			keyChain[i]=keyChain[i+1];
		}

		input[0]=keyChain[0];
		input[1]=keyChain[1];
	}

	
	@Override
	public void keyPressed(KeyEvent ke) {
		switch(ke.getKeyCode()){
		case KeyEvent.VK_UP:
			toTop(0);
			break;
		case KeyEvent.VK_RIGHT:
			toTop(1);
			break;
		case KeyEvent.VK_DOWN:
			toTop(2);
			break;
		case KeyEvent.VK_LEFT:
			toTop(3);
			break;
			
		case KeyEvent.VK_F3://not in typed!!
			input[2]=666;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		switch(ke.getKeyCode()){
		case KeyEvent.VK_UP:
			remove(0);
			break;
		case KeyEvent.VK_RIGHT:
			remove(1);
			break;
		case KeyEvent.VK_DOWN:
			remove(2);
			break;
		case KeyEvent.VK_LEFT:
			remove(3);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		switch(ke.getKeyChar()){
		case KeyEvent.VK_ESCAPE:
			input[2]=10;
			break;
		case KeyEvent.VK_ENTER:
			input[2]=11;
			break;
		case KeyEvent.VK_1:
			input[2]=4;
			break;
		case KeyEvent.VK_2:
			input[2]=5;
			break;
		case KeyEvent.VK_3:
			input[2]=6;
			break;
		case KeyEvent.VK_4:
			input[2]=7;
			break;
		}
	}

}
