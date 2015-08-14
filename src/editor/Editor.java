package editor;

import game.World;
import gui.Panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controllers.Camera;

public class Editor {

	//TODO all
	
	//GUI
	private JFrame mainEditor;
	private JFrame mapEditor;
	private Panel worldPanel;
	
	//Data
	private World world;
	
	public static void startEditor(){
		Editor e=new Editor();
	}
	
	
	private Editor(){
		initWorld();
		initGUI();
	}
	
	private void initWorld(){
		world=new World();
	}
	
	private void initGUI(){
		mainEditor=new JFrame("Obopo World Editor");
		mainEditor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		initMenuBar();
		initPanels();
		initRoomView();
		
		mainEditor.pack();
		mainEditor.setVisible(true);
		
		//TODO init mapEditor?
	}
	
	private void initMenuBar(){
		JMenuBar menuBar=new JMenuBar();

		JMenu menuFile=new JMenu("File");
		JMenu menuEdit=new JMenu("Edit");
		JMenu menuHelp=new JMenu("Help");
		JButton menuAbout=new JButton("About");

		JMenuItem miCreate=new JMenuItem("Create new world");
		JMenuItem miSave=new JMenuItem("Save world");
		JMenuItem miLoad=new JMenuItem("Load world");
		JMenuItem miExit=new JMenuItem("Exit");
		
		miCreate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO create world
			}
		});
		
		miSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO save world
			}
		});
		
		miLoad.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO load world
			}
		});
		
		miExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO check: save world?
				System.exit(0);
			}
		});
		menuFile.add(miCreate);
		menuFile.add(miSave);
		menuFile.add(miLoad);
		menuFile.addSeparator();
		menuFile.add(miExit);
		
		//TODO menuEdit
		
		//TODO menuHelp
		
		menuAbout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(null, "Obopo Editor", "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuHelp);
		menuBar.add(menuAbout);
		mainEditor.setJMenuBar(menuBar);
	}
	
	private void initPanels(){
		JPanel contentPane=new JPanel(new BorderLayout());
		
		JTabbedPane objectSelect=new JTabbedPane();
		JTabbedPane roomView=new JTabbedPane();
		JTabbedPane objectEdit=new JTabbedPane();
		
		JPanel bottomPanel=new JPanel(new BorderLayout());
		JPanel quickEdit=new JPanel(new GridLayout(2,3));//TODO what quick edit info?
		JPanel controlPanel=new JPanel(new GridLayout(1,2));
		
		JButton mapBtn=new JButton("Open map editor");
		JLabel controlInfo=new JLabel("INFO");

		controlPanel.add(mapBtn);
		controlPanel.add(controlInfo);

		bottomPanel.add(controlPanel, BorderLayout.WEST);
		bottomPanel.add(quickEdit, BorderLayout.CENTER);

		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		contentPane.add(objectSelect, BorderLayout.WEST);
		contentPane.add(objectEdit, BorderLayout.EAST);
		contentPane.add(roomView, BorderLayout.CENTER);
		
		mainEditor.setContentPane(contentPane);
	}
	
	private void initRoomView(){
		Camera cam=new Camera(world,0,0);
		
		//TODO create layers
		//TODO roomView
	}
}
