package com.graphic.framework;

import java.awt.EventQueue;

import com.graphic.framework.Model.CII_Objectv3;
import com.graphic.framework.Model.CII_Objectv2;
import com.graphic.framework.View.View;

public class GraphicalFramework {
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				/*
				 * Launching the CII Graphical Framework
				 */
				
				View v = new View();
				//CII_Objectv2 ciiObj = new CII_Objectv2();
				
			}			
		});
	}
}
