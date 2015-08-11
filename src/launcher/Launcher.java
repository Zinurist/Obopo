package launcher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.Game;
import editor.Editor;

/**
 * Launcher of the game.
 * ->Play
 * ->Level Editor
 * ->Exit
 */
public class Launcher extends JFrame{

	//generated
	private static final long serialVersionUID = -2058422064307705756L;

	public static void main(String[] args) {
		Launcher l=new Launcher();
		l.setVisible(true);
	}
	
	public Launcher(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Launcher");
		setResizable(false);
		
		JPanel contentPane=new JPanel(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(300,300));
		
		JPanel pBtn=new JPanel(new GridLayout(3,0));
		
		JButton btnGame=new JButton("Play");
		btnGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.startGame();
				dispose();
			}
		});
		
		JButton btnEditor=new JButton("Level Editor");
		btnEditor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.startEditor();
				dispose();
			}
		});
		
		JButton btnExit=new JButton("Exit");
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
		JLabel lbl=new JLabel("Obopo");

		pBtn.add(btnGame);
		pBtn.add(btnEditor);
		pBtn.add(btnExit);
		contentPane.add(lbl,BorderLayout.NORTH);
		contentPane.add(pBtn,BorderLayout.CENTER);
		setContentPane(contentPane);
		pack();
	}
	
}
