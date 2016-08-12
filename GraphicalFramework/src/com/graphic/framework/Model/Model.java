package com.graphic.framework.Model;

import javax.swing.JButton;

public class Model {
	private static String[] compLibCII = {"CII_TIMER", "CII_MUX", "CII_TRIGGER", "CII_ADDER",
		  "CII_x1" ,"CII_x2" ,"CII_x3" ,"CII_x4" ,"CII_x5", "CII_x6" ,"CII_x7" ,"CII_x8" ,"CII_x9" ,"CII_x10",
		  "CII_x11" ,"CII_x12" ,"CII_x13" ,"CII_x14" ,"CII_x15", "CII_x16" ,"CII_x17" ,"CII_x18" ,"CII_x19" ,"CII_x20"};
	
	private static JButton[] buttons = {new JButton("CII_1"), new JButton("CII_2")};
	
	public String[] getDataString() {
		return compLibCII;
	}
	
	public JButton[] getDataButton() {
		return buttons;
	}

}
