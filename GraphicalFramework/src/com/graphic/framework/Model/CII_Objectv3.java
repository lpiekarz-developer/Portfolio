package com.graphic.framework.Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class CII_Objectv3 {
	
	/*
	 * CII key-words, used in CII-definition-file for description of a system
	 */
	
	private String[] accessModesRegCII = {"IR", "RC", "RO", "RW", "WO"};
	private String[] accessModes = {"publ", "priv"};
	private String[] interfacesCII = {"area", "bits", "glue", "word"};
	private String[] accessModesCII = {"decl", "init", "prot"};
	private String[] primitives = {"int", "log", "sel", "str", "bin", "hex", "cmp", 
								   "int[]", "log[]", "sel[]", "str[]", "bin[]", "hex[]", "cmp[]"};
	private String[] componentTypesCII = {"decl", "cons"};
	private String[] compositeElements = {"Parameter", "Interface", "Component"};
	
	/*
	 * Swing components used for complete visualization 
	 */
	
	private JFrame CIIObjectFrame;
	private JTabbedPane CIICompElements;
	private ParameterTab parameterTab;
	private InterfaceTab interfaceTab;
	private ConstantTab constantTab;
	private SymblistTab symblistTab;
	private FunctionTab functionTab;
	private ComponentTab componentTab;
	private static String defFileCII= "", confFileCII="";

	public CII_Objectv3() {
		CIIObjectFrame = new JFrame("CII_OBJECT");
		CIIObjectFrame.setBounds(100, 100, 700, 400);
		CIIObjectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CIICompElements = new JTabbedPane(JTabbedPane.TOP);
		CIIObjectFrame.getContentPane().add(CIICompElements, BorderLayout.CENTER);

		parameterTab = new ParameterTab();
		interfaceTab = new InterfaceTab();
		constantTab = new ConstantTab();
		symblistTab = new SymblistTab();
		functionTab = new FunctionTab();
		componentTab = new ComponentTab();

		CIICompElements.addTab("Parameters", parameterTab);
		CIICompElements.addTab("Interfaces", interfaceTab);
		CIICompElements.addTab("Constants", constantTab);
		CIICompElements.addTab("Symblists", symblistTab);
		CIICompElements.addTab("Functions", functionTab);
		CIICompElements.addTab("Component", componentTab);

		CIIObjectFrame.setVisible(true);
	}



	class Tab extends JPanel {

		protected JTextArea CIIpreview;
		private JScrollPane scroll;

		Tab() {
			setLayout(new BorderLayout(0, 0));

			CIIpreview = new JTextArea();
			CIIpreview.setRows(15);
			
			scroll = new JScrollPane(CIIpreview);

			add(scroll, BorderLayout.NORTH);
		}

	}

	class ParameterTab extends Tab {

		private JPanel settingsParamPanel;
		private JComboBox<String> paramsCIIList;
		private JComboBox<String> primitivesCIIList;
		private JTextField paramName, paramValue;
		private JButton addButton;
		private JLabel LBLparamName, LBLparamValue;

		ParameterTab() {
			super();
			paramsCIIList = new JComboBox<String>();
			primitivesCIIList = new JComboBox<String>();
			paramName = new JTextField(10);
			paramValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLparamName = new JLabel("PARAM_NAME:");
			LBLparamValue = new JLabel("PARAM_VALUE:");
			settingsParamPanel = new JPanel();
			settingsParamPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String param : accessModesCII) {
				paramsCIIList.addItem(param);
			}
			
			for(String param : primitives) {
				primitivesCIIList.addItem(param);
			}	
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//CIIpreview.append("Parameter:");
					addParamLine();
				}
			});
			
			
			settingsParamPanel.add(paramsCIIList);
			settingsParamPanel.add(LBLparamName);
			settingsParamPanel.add(paramName);
			settingsParamPanel.add(primitivesCIIList);
			settingsParamPanel.add(LBLparamValue);
			settingsParamPanel.add(paramValue);
			settingsParamPanel.add(addButton);
			
			add(settingsParamPanel, BorderLayout.CENTER);
		}
		
		/**
		 * Method which add line of code defining CII parameters coherent with CII standard
		 */
		
		void addParamLine() {
			
			CIIpreview.append("Parameter:" + paramsCIIList.getSelectedItem().toString() +
					" " + paramName.getText() + " :" + primitivesCIIList.getSelectedItem().toString() + 
					" = " + paramValue.getText() + ";" + '\n');
		}
	}


	class InterfaceTab extends Tab {
		
		private JPanel settingsInterfPanel;
		private JComboBox<String> interfacesCIIList;
		private JComboBox<String> accessModesRegCIIList;
		private JTextField interfName;
		private JTable interfValues;
		private JButton addButton;
		private JLabel LBLinterfName, LBLinterfValue;
		
		InterfaceTab() {
			super();
			interfacesCIIList = new JComboBox<String>();
			accessModesRegCIIList = new JComboBox<String>();
			interfName = new JTextField(10);
			interfValues = new JTable(3,1);
			addButton = new JButton("Add");
			LBLinterfName = new JLabel("INTERFACE_NAME:");
			LBLinterfValue = new JLabel("VALUES:");
			settingsInterfPanel = new JPanel();
			settingsInterfPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String interf : interfacesCII) {
				interfacesCIIList.addItem(interf);
			}
			
			for(String accModReg : accessModesRegCII) {
				accessModesRegCIIList.addItem(accModReg);
			}	
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addInterfLine();
				}
			});
			
			
			settingsInterfPanel.add(interfacesCIIList);
			settingsInterfPanel.add(LBLinterfName);
			settingsInterfPanel.add(interfName);
			settingsInterfPanel.add(accessModesRegCIIList);
			settingsInterfPanel.add(LBLinterfValue);
			settingsInterfPanel.add(interfValues);
			settingsInterfPanel.add(addButton);
			
			add(settingsInterfPanel, BorderLayout.CENTER);
		}
		
		/**
		 * Method which add line of code defining CII interfaces coherent with CII standard
		 */
		
		void addInterfLine() {
			
			String interfVal = "";
			TableCellEditor tce = interfValues.getCellEditor();
			if(tce != null){
				tce.stopCellEditing();
			}
			interfValues.clearSelection();

			
			for(int i = 0; i < 3; i++) {
				if((interfValues.getValueAt(i, 0) != null) && i>0) {
					if(!interfValues.getValueAt(i, 0).toString().equals(""))
					interfVal += "; ";
				}
				
				if(interfValues.getValueAt(i, 0) != null)
				interfVal += interfValues.getValueAt(i, 0).toString();
				
			}
			
			CIIpreview.append("Interface:" + interfacesCIIList.getSelectedItem().toString() +
					" " + interfName.getText() + " :" + accessModesRegCIIList.getSelectedItem().toString() + 
					"[" + interfVal + "];" + '\n');
		}		
	}
	
	class ConstantTab extends Tab {
		private JTextField constName;
		private JTextField constValue;
		private JLabel LBLconstName;
		private JLabel LBLconstValue;
		private JComboBox<String> accessModesList;
		private JComboBox<String> primitivesCIIList;
		private JPanel settingsConstPanel;
		private JButton addButton;
		
		ConstantTab() {
			super();
			accessModesList = new JComboBox<String>();
			primitivesCIIList = new JComboBox<String>();
			constName = new JTextField(10);
			constValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLconstName = new JLabel("CONST_NAME:");
			LBLconstValue = new JLabel("CONST_VALUE:");
			settingsConstPanel = new JPanel();
			settingsConstPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String mode : accessModes) {
				accessModesList.addItem(mode);
			}
			
			for(String param : primitives) {
				primitivesCIIList.addItem(param);
			}	
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addConstLine();
				}
			});
			
			
			settingsConstPanel.add(accessModesList);
			settingsConstPanel.add(LBLconstName);
			settingsConstPanel.add(constName);
			settingsConstPanel.add(primitivesCIIList);
			settingsConstPanel.add(LBLconstValue);
			settingsConstPanel.add(constValue);
			settingsConstPanel.add(addButton);
			
			add(settingsConstPanel, BorderLayout.CENTER);
		}
		
		void addConstLine() {
			CIIpreview.append("Constant :" + accessModesList.getSelectedItem().toString() + 
							  " " + constName.getText() + " :" + primitivesCIIList.getSelectedItem().toString() + 
							  " = " + constValue.getText() + ";" + "\n");
		}
	}
	
	class SymblistTab extends Tab {
		private JTextField symblistName;
		private JTextField symblistValue;
		private JLabel LBLsymblistName;
		private JLabel LBLsymblistValue;
		private JComboBox<String> accessModesList;
		private JPanel settingsSymbListPanel;
		private JButton addButton;
		
		SymblistTab() {
			super();
			accessModesList = new JComboBox<String>();
			symblistName = new JTextField(10);
			symblistValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLsymblistName = new JLabel("LIST_NAME:");
			LBLsymblistValue = new JLabel("LIST_VALUE:");
			settingsSymbListPanel = new JPanel();
			settingsSymbListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String mode : accessModes) {
				accessModesList.addItem(mode);
			}
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addSymblistLine();
				}
			});
			
			
			settingsSymbListPanel.add(accessModesList);
			settingsSymbListPanel.add(LBLsymblistName);
			settingsSymbListPanel.add(symblistName);
			settingsSymbListPanel.add(LBLsymblistValue);
			settingsSymbListPanel.add(symblistValue);
			settingsSymbListPanel.add(addButton);
			
			add(settingsSymbListPanel, BorderLayout.CENTER);
		}
		
		void addSymblistLine() {
			CIIpreview.append("Symblist :" + accessModesList.getSelectedItem().toString() + 
							  " " + symblistName.getText() + " = " + "{" + symblistValue.getText() + "};" + "\n");
		}
	}
	
	class FunctionTab extends Tab {
		private JTextField functionName;
		private JTextField functionValue;
		private JLabel LBLfunctionName;
		private JLabel LBLfunctionValue;
		private JComboBox<String> accessModesList;
		private JPanel settingsFunctionPanel;
		private JButton addButton;
		
		FunctionTab() {
			super();
			accessModesList = new JComboBox<String>();
			functionName = new JTextField(10);
			functionValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLfunctionName = new JLabel("FUNC_NAME:");
			LBLfunctionValue = new JLabel("FUNC_VALUE:");
			settingsFunctionPanel = new JPanel();
			settingsFunctionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String mode : accessModes) {
				accessModesList.addItem(mode);
			}
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addFunctionLine();
				}
			});
			
			
			settingsFunctionPanel.add(accessModesList);
			settingsFunctionPanel.add(LBLfunctionName);
			settingsFunctionPanel.add(functionName);
			settingsFunctionPanel.add(LBLfunctionValue);
			settingsFunctionPanel.add(functionValue);
			settingsFunctionPanel.add(addButton);
			
			add(settingsFunctionPanel, BorderLayout.CENTER);
		}
		
		void addFunctionLine() {
			CIIpreview.append("Function :" + accessModesList.getSelectedItem().toString() + 
							  " " + functionName.getText() + " = (" + functionValue.getText() + ");" + "\n");
		}
	}
	
	class ComponentTab extends Tab {
		
		private JTextField compName;
		private JLabel LBLcompName, LBLcompValues;
		private JComboBox<String> compTypesList;
		private JTable compValues;
		private String compValStr;
		private JPanel settingsCompPanel;
		private JButton genButton, saveButton, loadButton;
		
		ComponentTab() {
			super();
			compName = new JTextField(10);
			compValues = new JTable(2,1);
			LBLcompName = new JLabel("COMPONENT NAME:");
			LBLcompValues = new JLabel("COMPONENT VALUES:");
			compTypesList = new JComboBox<String>();
			genButton = new JButton("Generate Def File");
			saveButton = new JButton("Save");
			loadButton = new JButton("Load");
			compValStr = new String("");
			settingsCompPanel = new JPanel();
			settingsCompPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String type : componentTypesCII) {
				compTypesList.addItem(type);
			}
			
			compTypesList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(compTypesList.getSelectedItem().toString() == "cons") {
						compValues.setValueAt(null, 0, 0);
						compValues.setValueAt(null, 1, 0);
						compValues.setEnabled(false);
						compValues.setBackground(Color.LIGHT_GRAY);
					}
					if(compTypesList.getSelectedItem().toString() == "decl") {
						compValues.setEnabled(true);
						compValues.setBackground(Color.WHITE);
					}
						
				}
				
			});
			
			genButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fetchDataFromTable();
					generate_CIIDefinitionFile();
				}
			});
			
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fetchDataFromTable();
					save_GCIIConfigurationFile();
				}
			});
			
			loadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						JFileChooser fc = new JFileChooser();
						int retVal = fc.showOpenDialog(null);
						if(retVal == JFileChooser.APPROVE_OPTION){
							load_GCIIConfigurationFile(fc.getSelectedFile());
						}						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			settingsCompPanel.add(compTypesList);
			settingsCompPanel.add(LBLcompName);
			settingsCompPanel.add(compName);
			settingsCompPanel.add(LBLcompValues);
			settingsCompPanel.add(compValues);
			settingsCompPanel.add(genButton);
			settingsCompPanel.add(saveButton);
			settingsCompPanel.add(loadButton);
			
			add(settingsCompPanel, BorderLayout.CENTER);
		}
		
		void fetchDataFromTable() {
			
			TableCellEditor tce = compValues.getCellEditor();
			if(tce != null){
				tce.stopCellEditing();
			}
			compValues.clearSelection();
			
			if(compValues.getValueAt(0, 0) != null && compValues.getValueAt(1, 0) != null) {
				compValStr = "[";
				compValStr += compValues.getValueAt(0, 0).toString();
				compValStr += ";";
				compValStr += compValues.getValueAt(1, 0).toString();
				compValStr += "]";
			}
		}
	}
	
	void load_GCIIConfigurationFile(File f_) throws IOException {
		boolean f_par, f_int, f_con, f_sym, f_fun, f_com;
		Scanner sc = new Scanner(f_);
		String tempLine = "";
		int tempOccurComponent, tempOccurConstant, tempOccurInterface, 
			tempOccurParameter, tempOccurSymblist, tempOccurFunction;
		
		while(sc.hasNextLine()) {
			tempLine = sc.nextLine();
			tempOccurComponent = tempLine.indexOf("C_");
			tempOccurConstant = tempLine.indexOf("Cons");
			tempOccurInterface = tempLine.indexOf("Inte");
			tempOccurParameter = tempLine.indexOf("Para");
			tempOccurSymblist = tempLine.indexOf("Symb");
			tempOccurFunction = tempLine.indexOf("Func");
			
			if(tempOccurComponent == 0) {
				if(tempLine.indexOf("C_Name:") == 0)
				componentTab.compName.setText(tempLine.substring(7));
				if(tempLine.indexOf("C_Type:") == 0)
				componentTab.compTypesList.setSelectedItem(tempLine.substring(7));
				if(tempLine.indexOf("C_Values:") == 0) {
					int markerA = tempLine.indexOf(";");
					int markerB = tempLine.indexOf("]");
					if(markerA > 0 && markerB > 0) {
						componentTab.compValues.setValueAt(tempLine.substring(10,markerA),0,0);
						componentTab.compValues.setValueAt(tempLine.substring(markerA + 1,markerB),1,0);
					}
				}				
			}

			if(tempOccurConstant == 0) {
				constantTab.CIIpreview.append(tempLine + "\n");
			}

			if(tempOccurInterface == 0) {
				interfaceTab.CIIpreview.append(tempLine + "\n");
			}

			if(tempOccurParameter == 0) {
				parameterTab.CIIpreview.append(tempLine + "\n");
			}

			if(tempOccurSymblist == 0) {
				symblistTab.CIIpreview.append(tempLine + "\n");
			}

			if(tempOccurFunction == 0) {
				functionTab.CIIpreview.append(tempLine + "\n");
			}			
		}
		
		sc.close();
	}
	
	void save_GCIIConfigurationFile() {

		confFileCII = System.getProperty("user.dir") + "/" + 
					  componentTab.compName.getText() + "_gcii.conf";

		try {
			PrintWriter pout = new PrintWriter(confFileCII);
			
			/*
			 * Saving data from each section to configuration file
			 */
			pout.println("C_Type:" + componentTab.compTypesList.getSelectedItem().toString());
			pout.println("C_Name:" + componentTab.compName.getText());
			pout.println("C_Values:" + componentTab.compValStr);
			parameterTab.CIIpreview.write(pout);
			interfaceTab.CIIpreview.write(pout);
			constantTab.CIIpreview.write(pout);
			symblistTab.CIIpreview.write(pout);
			functionTab.CIIpreview.write(pout);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	void generate_CIIDefinitionFile() {
		defFileCII = System.getProperty("user.dir") + "/" + 
					 componentTab.compName.getText() + "_def.cii";

		try {
			PrintWriter pout = new PrintWriter(defFileCII);
			/*
			 * Creating CII definition file:
			 */
			
			pout.println("Component:" + 
						 componentTab.compTypesList.getSelectedItem().toString() + 
						 " { " + componentTab.compName.getText() + 
						 " " + componentTab.compValStr);
			
			/*
			 * Add characteristic sections to CII definition file
			 */
			
			pout.println();
			if(parameterTab.CIIpreview.getLineCount() > 1) {
				parameterTab.CIIpreview.write(pout);			
				pout.println();
			}
			if(interfaceTab.CIIpreview.getLineCount() > 1) {
				interfaceTab.CIIpreview.write(pout);
				pout.println();
			}
			if(constantTab.CIIpreview.getLineCount() > 1) {
				constantTab.CIIpreview.write(pout);
				pout.println();
			}
			if(symblistTab.CIIpreview.getLineCount() > 1) {
				symblistTab.CIIpreview.write(pout);
				pout.println();
			}
			if(functionTab.CIIpreview.getLineCount() > 1) {
				functionTab.CIIpreview.write(pout);
				pout.println();
			}
			

			pout.println("}");
			pout.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
