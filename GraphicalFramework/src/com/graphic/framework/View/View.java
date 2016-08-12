package com.graphic.framework.View;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.graphic.framework.Model.Model;

public class View {
	
	private JFrame mainApp;
	private Container surface;
	private JPanel textArea;
	private JPanel paletteArea;
	private CreationArea creationArea;
	private Palette palette;
	private Model model;
	private static JMenu[] menus = {new JMenu("File"), new JMenu("Options"), new JMenu("Help")};
	private JMenuBar menuBar;
	
	private JTextField txf;
	
	public View() {		
		mainApp = new JFrame();
		mainApp.setTitle("CII Component Visualizer");
		mainApp.setSize(800,600);
		mainApp.setLocation(200,100);
		mainApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        surface = mainApp.getContentPane();
        
		textArea = new JPanel();
		paletteArea = new JPanel();
		creationArea = new CreationArea();
				
		model = new Model();
		palette = new Palette();
		txf = new JTextField(40);
		txf.setDragEnabled(true);
		
		paletteArea.add(palette);
		textArea.add(txf);
		
		menuBar = new JMenuBar();
		for(JMenu jm : menus) {
			menuBar.add(jm);
		}
		
		surface.add(textArea, BorderLayout.NORTH);
		surface.add(paletteArea, BorderLayout.EAST);
		surface.add(creationArea, BorderLayout.CENTER);
		
		mainApp.setJMenuBar(menuBar);
		mainApp.setVisible(true);
	}
	
	class Palette extends JList {
		
		Palette() {
			super(model.getDataString());
			setLayoutOrientation(JList.VERTICAL);
			setDragEnabled(true);
		}
	}
}

class CreationArea extends JPanel {
	CreationArea() {
		super();
		setEnabled(true);
	}
}

