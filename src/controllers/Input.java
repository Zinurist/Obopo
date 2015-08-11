package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Input controller used for this game. Reads user input and parses it into a inputformat which other controllers understand.
 * The input controller is added as key listener to the main JFrame.
 * 
 * Format:
 * [0] - latest movement key, the "active" one (ex: Up, Down)
 * [1] - second latest movement key, may be used for diagonal moving 
 * [2] - ability/menu key, used for every other non-movement action (ex: ESC, Ability1)
 * 
 * Key-Mapping:
 * Up 		- 0
 * Right 	- 1
 * Down 	- 2
 * Left 	- 3
 * ESC 		- 10
 * Enter 	- 11
 * 1		- 4
 * 2 		- 5
 * 3 		- 6
 * 4 		- 7
 * F3 		- 666
 * 
 */
public class Input implements KeyListener{

	//this array holds a list of movement(!) keys in the order, in which they were pressed (latest key is at 0, etc.), released keys are removed from this list
	private int[] keyChain;
	//this array holds the input in the specified format
	private int[] input;
	
	public Input(){
		keyChain=new int[]{-1,-1,-1,-1};
		input=new int[]{-1,-1,-1};
	}
	
	/**
	 * Creates an array which holds the formatted input. The Ability key is reset after this, movement keys aren't.
	 * Called by Game-object.
	 * A new temporary array is used in order so safely remove the Ability key.
	 * @return input array
	 */
	public int[] readInput(){
		int[] rem=new int[]{input[0],input[1],input[2]};
		input[2]=-1;
		return rem;
	}
	
	/**
	 * Moves the direction key to the top of the key chain. This key is now the latest pressed key. Keys in the input-array are also updated.
	 * @param dir the new direction key
	 */
	private void toTop(int dir){
		if(keyChain[0]!=dir){
			//move-to-front algorithm
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
		//updating input-array
		input[0]=keyChain[0];
		input[1]=keyChain[1];
	}
	
	/**
	 * Removes the direction key from the key chain and updates the input-array.
	 * @param dir the direction key, which should be removed
	 */
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
		//TODO maybe move up/down to typed keys, to make navigation in menus feel more natural
		//TODO wasd, controller stick
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
		//typed keys, most of the ability/menu keys are here
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
