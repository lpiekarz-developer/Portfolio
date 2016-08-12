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
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class CII_Objectv2 {
	
	/*
	 * CII key-words, used in CII-definition-file for description of a system
	 */
	
	private static String[] accessModesRegCII = {"IR", "RC", "RO", "RW", "WO"};
	private static String[] accessModes = {"publ", "priv"};
	private static String[] interfacesCII = {"area", "bits", "glue", "word"};
	private static String[] accessModesCII = {"decl", "init", "prot"};
	private static String[] primitives = {"int", "log", "sel", "str", "bin", "hex", "cmp", 
								   "int[]", "log[]", "sel[]", "str[]", "bin[]", "hex[]", "cmp[]"};
	private String[] componentTypesCII = {"decl", "cons"};
	
	
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

	public CII_Objectv2() {
		CIIObjectFrame = new JFrame("CII_OBJECT");
		CIIObjectFrame.setBounds(100, 100, 800, 550);
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

	static class Tab extends JPanel {

		protected JTable CIIpreview;
		private JScrollPane scroll;
		private DefaultTableModel tableModel;
		private static final int ROWS = 0;
		private ArrayList<String> dataForRow;
		private int dataSize;
		
		Tab(int row, int col) {
			setLayout(new BorderLayout(0, 0));
			CIIpreview = new JTable(row, col);			
			scroll = new JScrollPane(CIIpreview);
			add(scroll, BorderLayout.NORTH);
		}
		
		Tab(String[] headers) {
			dataSize = headers.length;
			setLayout(new BorderLayout(0, 0));			
			CIIpreview = new JTable(new DefaultTableModel(headers,ROWS));
			tableModel = (DefaultTableModel) CIIpreview.getModel();
			dataForRow = new ArrayList<String>();
			scroll = new JScrollPane(CIIpreview);
			add(scroll, BorderLayout.NORTH);
		}
		
		public static int getDefaultRows() {
			return ROWS;
		}
		
		public DefaultTableModel getDefModel() {
			return tableModel;
		}
		
		public int rowsNumber() {
			return tableModel.getRowCount();
		}
		
		public void updateTable() {
			TableCellEditor tce = CIIpreview.getCellEditor();
			if(tce != null){
				tce.stopCellEditing();
			}
			CIIpreview.clearSelection();
		}
		
		public void clearTable() {
			updateTable();
			int numRows = tableModel.getRowCount();
			for(int r = 0; r < numRows; r++) {
				tableModel.removeRow(0);
			}
		}
		
		public void addRow(String data) {
			dataForRow.add(data);
			if(dataForRow.size() == dataSize) {
				tableModel.addRow(dataForRow.toArray());
				dataForRow.clear();
			}
		}
	}

	static class ParameterTab extends Tab {

		private JPanel settingsParamPanel;
		private JComboBox<String> paramsCIIList;
		private JComboBox<String> primitivesCIIList;
		private JComboBox<String> paramsCIIListTable;
		private JComboBox<String> primitivesCIIListTable;
		private JTextField paramName, paramValue;
		private JButton addButton;
		private JLabel LBLparamName, LBLparamValue;
		private static String[] paramFeatures = {"Type", "Param Name", "Value Type", "Value"};
		private int defRows, leftRows;
		private DefaultTableModel defParamModel;
		private ArrayList<String> paramDataStructure;

		ParameterTab() {
			/*
			 * Establishing data model
			 */
			super(paramFeatures);
			defRows = Tab.getDefaultRows();
			leftRows = defRows;
			defParamModel = getDefModel();
			paramDataStructure = new ArrayList<String>(); 
			/*
			 * Establishing swing components 
			 */			
			paramsCIIList = new JComboBox<String>();
			primitivesCIIList = new JComboBox<String>();
			paramsCIIListTable = new JComboBox<String>();
			primitivesCIIListTable = new JComboBox<String>();
			paramName = new JTextField(10);
			paramValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLparamName = new JLabel("PARAM_NAME:");
			LBLparamValue = new JLabel("PARAM_VALUE:");
			settingsParamPanel = new JPanel();
			settingsParamPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String param : accessModesCII) {
				paramsCIIList.addItem(param);
				paramsCIIListTable.addItem(param);
			}
			
			for(String param : primitives) {
				primitivesCIIList.addItem(param);
				primitivesCIIListTable.addItem(param);
			}	
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//CIIpreview.append("Parameter:");
					addParamLine();
				}
			});
			
			TableColumn paramType = CIIpreview.getColumnModel().getColumn(0);
			paramType.setCellEditor(new DefaultCellEditor(paramsCIIListTable));
			
			TableColumn valueType = CIIpreview.getColumnModel().getColumn(2);
			valueType.setCellEditor(new DefaultCellEditor(primitivesCIIListTable));
			
			
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
		
		private void addParamLine() {

			Object[] rowParamData = {paramsCIIList.getSelectedItem().toString(), paramName.getText(), primitivesCIIList.getSelectedItem().toString(), paramValue.getText()};
			if(leftRows > 0) {
				defParamModel.insertRow((defRows - leftRows), rowParamData);
				defParamModel.removeRow(defRows);
				leftRows--;
			} else {
				defParamModel.addRow(rowParamData);
			}
		}
		
		private void getParamDataForSave() {
			
			updateTable();
			paramDataStructure.clear();
			
			for(int r = 0; r < rowsNumber(); r++) {
				// Adding parameter type
				String line_ptype = "P_Type:" + CIIpreview.getValueAt(r, 0).toString() + "\n";
				// Adding parameter name
				String line_pname = "P_Name:" + CIIpreview.getValueAt(r, 1).toString() + "\n";
				// Adding value type
				String line_pvtype = "P_VType:" + CIIpreview.getValueAt(r, 2).toString() + "\n";
				//Adding value
				String line_pvalue = "P_Value:" + CIIpreview.getValueAt(r, 3).toString() + "\n";
				
				paramDataStructure.add(line_ptype);
				paramDataStructure.add(line_pname);
				paramDataStructure.add(line_pvtype);
				paramDataStructure.add(line_pvalue);
			}
		}
		
		private void getParamDataForCIIDef() {
			
			updateTable();
			paramDataStructure.clear();
			
			String line = "";
			
			for(int r = 0; r < rowsNumber(); r++) {
				//Adding CII header
				line = "Parameter:";				
				// Adding parameter type
				line += CIIpreview.getValueAt(r, 0).toString();
				// Adding parameter name
				line += " " + CIIpreview.getValueAt(r, 1).toString();
				// Adding value type
				line += " :" + CIIpreview.getValueAt(r, 2).toString();
				//Adding value
				line += " = " + CIIpreview.getValueAt(r, 3).toString() + ";" + "\n";
				
				paramDataStructure.add(line);
			}
		}
		
		public ArrayList<String> getDataStructure(String command) {
			paramDataStructure.clear();
			if(command.equals("save"))
				getParamDataForSave();
			if(command.equals("def"))
				getParamDataForCIIDef();
			return paramDataStructure;
		}
		
	}


	static class InterfaceTab extends Tab {
		
		private JPanel settingsInterfPanel;
		private JComboBox<String> interfacesCIIList;
		private JComboBox<String> accessModesRegCIIList;
		private JComboBox<String> interfacesCIIListTable;
		private JComboBox<String> accessModesRegCIIListTable;
		private JTextField interfName;
		private JTable interfValues;
		private JButton addButton;
		private JLabel LBLinterfName, LBLinterfValue;
		private static String[] interfFeatures = {"Type", "Interface Name", "Register Mode", "Value"};
		private int defRows, leftRows;
		private DefaultTableModel defInterfModel;
		private ArrayList<String> interfDataStructure;
		
		InterfaceTab() {
			super(interfFeatures);
			defRows = Tab.getDefaultRows();
			leftRows = defRows;
			defInterfModel = getDefModel();
			interfDataStructure = new ArrayList<String>(); 
			
			interfacesCIIList = new JComboBox<String>();
			accessModesRegCIIList = new JComboBox<String>();
			interfacesCIIListTable = new JComboBox<String>();
			accessModesRegCIIListTable = new JComboBox<String>();
			interfName = new JTextField(10);
			interfValues = new JTable(3,1);
			addButton = new JButton("Add");
			LBLinterfName = new JLabel("INTERFACE_NAME:");
			LBLinterfValue = new JLabel("VALUES:");
			settingsInterfPanel = new JPanel();
			settingsInterfPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String interf : interfacesCII) {
				interfacesCIIList.addItem(interf);
				interfacesCIIListTable.addItem(interf);
			}
			
			for(String accModReg : accessModesRegCII) {
				accessModesRegCIIList.addItem(accModReg);
				accessModesRegCIIListTable.addItem(accModReg);
			}	
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addInterfLine();
				}
			});
			
			TableColumn interfType = CIIpreview.getColumnModel().getColumn(0);
			interfType.setCellEditor(new DefaultCellEditor(interfacesCIIListTable));
			
			TableColumn interfAccessModReg = CIIpreview.getColumnModel().getColumn(2);
			interfAccessModReg.setCellEditor(new DefaultCellEditor(accessModesRegCIIListTable));
			
			
			
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
		
		private void addInterfLine() {
			
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
			
			/*CIIpreview.append("Interface:" + interfacesCIIList.getSelectedItem().toString() +
					" " + interfName.getText() + " :" + accessModesRegCIIList.getSelectedItem().toString() + 
					"[" + interfVal + "];" + '\n');*/
			
			Object[] rowInterfData = {interfacesCIIList.getSelectedItem().toString(), interfName.getText(), accessModesRegCIIList.getSelectedItem().toString(), interfVal};
			if(leftRows > 0) {
				defInterfModel.insertRow((defRows - leftRows), rowInterfData);
				defInterfModel.removeRow(defRows);
				leftRows--;
			} else {
				defInterfModel.addRow(rowInterfData);
			}
		}

		private void getInterfDataForSave() {

			updateTable();
			interfDataStructure.clear();

			for(int r = 0; r < rowsNumber(); r++) {
				String line_itype = "I_Type:" + CIIpreview.getValueAt(r, 0).toString() + "\n";
				String line_iname = "I_Name:" + CIIpreview.getValueAt(r, 1).toString() + "\n";
				String line_ivtype = "I_AccessRegMod:" + CIIpreview.getValueAt(r, 2).toString() + "\n";
				String line_ivalue = "I_Value:" + CIIpreview.getValueAt(r, 3).toString() + "\n";

				interfDataStructure.add(line_itype);
				interfDataStructure.add(line_iname);
				interfDataStructure.add(line_ivtype);
				interfDataStructure.add(line_ivalue);
			}
		}

		private void getInterfDataForCIIDef() {

			updateTable();
			interfDataStructure.clear();

			String line = "";

			for(int r = 0; r < rowsNumber(); r++) {
				line = "Interface:";				
				line += CIIpreview.getValueAt(r, 0).toString();
				line += " " + CIIpreview.getValueAt(r, 1).toString();
				line += " :" + CIIpreview.getValueAt(r, 2).toString();
				line += "[" + CIIpreview.getValueAt(r, 3).toString() + "];" + "\n";

				interfDataStructure.add(line);
			}
		}
		
		public ArrayList<String> getDataStructure(String command) {
			interfDataStructure.clear();
			if(command.equals("save"))
				getInterfDataForSave();
			if(command.equals("def"))
				getInterfDataForCIIDef();
			return interfDataStructure;
		}
	}
	
	static class ConstantTab extends Tab {
		private JTextField constName;
		private JTextField constValue;
		private JLabel LBLconstName;
		private JLabel LBLconstValue;
		private JComboBox<String> accessModesList;
		private JComboBox<String> primitivesCIIList;
		private JComboBox<String> accessModesListTable;
		private JComboBox<String> primitivesCIIListTable;
		private JPanel settingsConstPanel;
		private JButton addButton;
		private static String[] constFeatures = {"Access Mode", "Const Name", "Value Type", "Value"};
		private int defRows, leftRows;
		private DefaultTableModel defConstModel;
		private ArrayList<String> constDataStructure;
		
		ConstantTab() {
			super(constFeatures);
			defRows = Tab.getDefaultRows();
			leftRows = defRows;
			defConstModel = getDefModel();
			constDataStructure = new ArrayList<String>();
			
			accessModesList = new JComboBox<String>();
			primitivesCIIList = new JComboBox<String>();
			accessModesListTable = new JComboBox<String>();
			primitivesCIIListTable = new JComboBox<String>();			
			constName = new JTextField(10);
			constValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLconstName = new JLabel("CONST_NAME:");
			LBLconstValue = new JLabel("CONST_VALUE:");
			settingsConstPanel = new JPanel();
			settingsConstPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String mode : accessModes) {
				accessModesList.addItem(mode);
				accessModesListTable.addItem(mode);
			}
			
			for(String param : primitives) {
				primitivesCIIList.addItem(param);
				primitivesCIIListTable.addItem(param);
			}	
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addConstLine();
				}
			});
			
			TableColumn constAccessMode = CIIpreview.getColumnModel().getColumn(0);
			constAccessMode.setCellEditor(new DefaultCellEditor(accessModesListTable));
			
			TableColumn constValueType = CIIpreview.getColumnModel().getColumn(2);
			constValueType.setCellEditor(new DefaultCellEditor(primitivesCIIListTable));
			
			
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
			
			Object[] rowConstData = {accessModesList.getSelectedItem().toString(), constName.getText(), primitivesCIIList.getSelectedItem().toString(), constValue.getText()};
			if(leftRows > 0) {
				defConstModel.insertRow((defRows - leftRows), rowConstData);
				defConstModel.removeRow(defRows);
				leftRows--;
			} else {
				defConstModel.addRow(rowConstData);
			}
		}
		
		private void getConstDataForSave() {

			updateTable();
			constDataStructure.clear();

			for(int r = 0; r < rowsNumber(); r++) {
				String line_cmode = "Con_Mode:" + CIIpreview.getValueAt(r, 0).toString() + "\n";
				String line_cname = "Con_Name:" + CIIpreview.getValueAt(r, 1).toString() + "\n";
				String line_cvtype = "Con_VType:" + CIIpreview.getValueAt(r, 2).toString() + "\n";
				String line_cvalue = "Con_Value:" + CIIpreview.getValueAt(r, 3).toString() + "\n";

				constDataStructure.add(line_cmode);
				constDataStructure.add(line_cname);
				constDataStructure.add(line_cvtype);
				constDataStructure.add(line_cvalue);
			}
		}
		
		private void getConstDataForCIIDef() {

			updateTable();
			constDataStructure.clear();

			String line = "";

			for(int r = 0; r < rowsNumber(); r++) {
				line = "Constant :";				
				line += CIIpreview.getValueAt(r, 0).toString();
				line += " " + CIIpreview.getValueAt(r, 1).toString();
				line += " :" + CIIpreview.getValueAt(r, 2).toString();
				line += " = " + CIIpreview.getValueAt(r, 3).toString() + ";" + "\n";

				constDataStructure.add(line);
			}
		}
		
		public ArrayList<String> getDataStructure(String command) {
			constDataStructure.clear();
			if(command.equals("save"))
				getConstDataForSave();
			if(command.equals("def"))
				getConstDataForCIIDef();
			return constDataStructure;
		}
	}
	
	static class SymblistTab extends Tab {
		private JTextField symblistName;
		private JTextField symblistValue;
		private JLabel LBLsymblistName;
		private JLabel LBLsymblistValue;
		private JComboBox<String> accessModesList;
		private JComboBox<String> accessModesListTable;
		private JPanel settingsSymbListPanel;
		private JButton addButton;
		private static String[] symblistFeatures = {"Access Mode", "Symblist Name", "Values"};
		private int defRows, leftRows;
		private DefaultTableModel defSymblistModel;
		private ArrayList<String> symblistDataStructure;
		
		SymblistTab() {
			super(symblistFeatures);
			defRows = Tab.getDefaultRows();
			leftRows = defRows;
			defSymblistModel = getDefModel();
			symblistDataStructure = new ArrayList<String>();
			
			accessModesList = new JComboBox<String>();
			accessModesListTable = new JComboBox<String>();
			symblistName = new JTextField(10);
			symblistValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLsymblistName = new JLabel("LIST_NAME:");
			LBLsymblistValue = new JLabel("LIST_VALUE:");
			settingsSymbListPanel = new JPanel();
			settingsSymbListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String mode : accessModes) {
				accessModesList.addItem(mode);
				accessModesListTable.addItem(mode);
			}
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addSymblistLine();
				}
			});
			
			TableColumn symblistMode = CIIpreview.getColumnModel().getColumn(0);
			symblistMode.setCellEditor(new DefaultCellEditor(accessModesListTable));
			
			
			settingsSymbListPanel.add(accessModesList);
			settingsSymbListPanel.add(LBLsymblistName);
			settingsSymbListPanel.add(symblistName);
			settingsSymbListPanel.add(LBLsymblistValue);
			settingsSymbListPanel.add(symblistValue);
			settingsSymbListPanel.add(addButton);
			
			add(settingsSymbListPanel, BorderLayout.CENTER);
		}
		
		void addSymblistLine() {
			/*CIIpreview.append("Symblist :" + accessModesList.getSelectedItem().toString() + 
							  " " + symblistName.getText() + " = " + "{" + symblistValue.getText() + "};" + "\n");*/
			Object[] rowSymblistData = {accessModesList.getSelectedItem().toString(), symblistName.getText(), symblistValue.getText()};
			if(leftRows > 0) {
				defSymblistModel.insertRow((defRows - leftRows), rowSymblistData);
				defSymblistModel.removeRow(defRows);
				leftRows--;
			} else {
				defSymblistModel.addRow(rowSymblistData);
			}
		}
		
		private void getSymblistDataForSave() {

			updateTable();
			symblistDataStructure.clear();

			for(int r = 0; r < rowsNumber(); r++) {
				String line_smode = "S_Mode:" + CIIpreview.getValueAt(r, 0).toString() + "\n";
				String line_sname = "S_Name:" + CIIpreview.getValueAt(r, 1).toString() + "\n";
				String line_svalue = "S_Value:" + CIIpreview.getValueAt(r, 2).toString() + "\n";

				symblistDataStructure.add(line_smode);
				symblistDataStructure.add(line_sname);
				symblistDataStructure.add(line_svalue);
			}
		}
		
		private void getSymblistDataForCIIDef() {

			updateTable();
			symblistDataStructure.clear();

			String line = "";

			for(int r = 0; r < rowsNumber(); r++) {
				line = "Symblist :";				
				line += CIIpreview.getValueAt(r, 0).toString();
				line += " " + CIIpreview.getValueAt(r, 1).toString();
				line += " = {" + CIIpreview.getValueAt(r, 2).toString() + "};" + "\n";

				symblistDataStructure.add(line);
			}
		}
		
		public ArrayList<String> getDataStructure(String command) {
			symblistDataStructure.clear();
			if(command.equals("save"))
				getSymblistDataForSave();
			if(command.equals("def"))
				getSymblistDataForCIIDef();
			return symblistDataStructure;
		}
	}
	
	static class FunctionTab extends Tab {
		private JTextField functionName;
		private JTextField functionValue;
		private JLabel LBLfunctionName;
		private JLabel LBLfunctionValue;
		private JComboBox<String> accessModesList;
		private JComboBox<String> accessModesListTable;
		private JPanel settingsFunctionPanel;
		private JButton addButton;
		private static String[] functionFeatures = {"Access Mode", "Function Name", "Function Body"};
		private int defRows, leftRows;
		private DefaultTableModel defFunctionModel;
		private ArrayList<String> functionDataStructure;
		
		FunctionTab() {
			super(functionFeatures);
			defRows = Tab.getDefaultRows();
			leftRows = defRows;
			defFunctionModel = getDefModel();
			functionDataStructure = new ArrayList<String>();
			
			accessModesList = new JComboBox<String>();
			accessModesListTable = new JComboBox<String>();
			functionName = new JTextField(10);
			functionValue = new JTextField(10);
			addButton = new JButton("Add");
			LBLfunctionName = new JLabel("FUNC_NAME:");
			LBLfunctionValue = new JLabel("FUNC_VALUE:");
			settingsFunctionPanel = new JPanel();
			settingsFunctionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			for(String mode : accessModes) {
				accessModesList.addItem(mode);
				accessModesListTable.addItem(mode);
			}
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addFunctionLine();
				}
			});
			

			TableColumn functionMode = CIIpreview.getColumnModel().getColumn(0);
			functionMode.setCellEditor(new DefaultCellEditor(accessModesListTable));
			
			
			settingsFunctionPanel.add(accessModesList);
			settingsFunctionPanel.add(LBLfunctionName);
			settingsFunctionPanel.add(functionName);
			settingsFunctionPanel.add(LBLfunctionValue);
			settingsFunctionPanel.add(functionValue);
			settingsFunctionPanel.add(addButton);
			
			add(settingsFunctionPanel, BorderLayout.CENTER);
		}
		
		void addFunctionLine() {
			/*CIIpreview.append("Function :" + accessModesList.getSelectedItem().toString() + 
							  " " + functionName.getText() + " = (" + functionValue.getText() + ");" + "\n");*/
			Object[] rowFunctionData = {accessModesList.getSelectedItem().toString(), functionName.getText(), functionValue.getText()};
			if(leftRows > 0) {
				defFunctionModel.insertRow((defRows - leftRows), rowFunctionData);
				defFunctionModel.removeRow(defRows);
				leftRows--;
			} else {
				defFunctionModel.addRow(rowFunctionData);
			}		
		}
		
		private void getFunctionDataForSave() {

			updateTable();
			functionDataStructure.clear();

			for(int r = 0; r < rowsNumber(); r++) {
				String line_fmode = "F_Mode:" + CIIpreview.getValueAt(r, 0).toString() + "\n";
				String line_fname = "F_Name:" + CIIpreview.getValueAt(r, 1).toString() + "\n";
				String line_fvalue = "F_Value:" + CIIpreview.getValueAt(r, 2).toString() + "\n";

				functionDataStructure.add(line_fmode);
				functionDataStructure.add(line_fname);
				functionDataStructure.add(line_fvalue);
			}
		}
		
		private void getFunctionDataForCIIDef() {

			updateTable();
			functionDataStructure.clear();

			String line = "";

			for(int r = 0; r < rowsNumber(); r++) {
				line = "Function :";				
				line += CIIpreview.getValueAt(r, 0).toString();
				line += " " + CIIpreview.getValueAt(r, 1).toString();
				line += " = (" + CIIpreview.getValueAt(r, 2).toString() + ");" + "\n";

				functionDataStructure.add(line);
			}
		}
		
		public ArrayList<String> getDataStructure(String command) {
			functionDataStructure.clear();
			if(command.equals("save"))
				getFunctionDataForSave();
			if(command.equals("def"))
				getFunctionDataForCIIDef();
			return functionDataStructure;
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
			super(0,0);
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
		Scanner sc = new Scanner(f_);
		String tempLine = "";
		int tempOccurComponent, tempOccurConstant, tempOccurInterface, 
			tempOccurParameter, tempOccurSymblist, tempOccurFunction;
		
		parameterTab.clearTable();
		interfaceTab.clearTable();
		constantTab.clearTable();
		symblistTab.clearTable();
		functionTab.clearTable();
		
		while(sc.hasNextLine()) {
			tempLine = sc.nextLine();
			tempOccurComponent = tempLine.indexOf("C_");
			tempOccurConstant = tempLine.indexOf("Con_");
			tempOccurInterface = tempLine.indexOf("I_");
			tempOccurParameter = tempLine.indexOf("P_");
			tempOccurSymblist = tempLine.indexOf("S_");
			tempOccurFunction = tempLine.indexOf("F_");
			
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
				if(tempLine.indexOf("Con_Mode:") == 0)
					constantTab.addRow(tempLine.substring(9));
				if(tempLine.indexOf("Con_Name:") == 0)
					constantTab.addRow(tempLine.substring(9));
				if(tempLine.indexOf("Con_VType:") == 0)
					constantTab.addRow(tempLine.substring(10));
				if(tempLine.indexOf("Con_Value:") == 0)
					constantTab.addRow(tempLine.substring(10));
			}

			if(tempOccurInterface == 0) {
				if(tempLine.indexOf("I_Type:") == 0)
					interfaceTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("I_Name:") == 0)
					interfaceTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("I_AccessRegMod:") == 0)
					interfaceTab.addRow(tempLine.substring(15));
				if(tempLine.indexOf("I_Value:") == 0)
					interfaceTab.addRow(tempLine.substring(8));
			}

			if(tempOccurParameter == 0) {
				if(tempLine.indexOf("P_Type:") == 0)
					parameterTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("P_Name:") == 0)
					parameterTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("P_VType:") == 0)
					parameterTab.addRow(tempLine.substring(8));
				if(tempLine.indexOf("P_Value:") == 0)
					parameterTab.addRow(tempLine.substring(8));
			}

			if(tempOccurSymblist == 0) {
				if(tempLine.indexOf("S_Mode:") == 0)
					symblistTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("S_Name:") == 0)
					symblistTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("S_Value:") == 0)
					symblistTab.addRow(tempLine.substring(8));
			}

			if(tempOccurFunction == 0) {
				if(tempLine.indexOf("F_Mode:") == 0)
					functionTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("F_Name:") == 0)
					functionTab.addRow(tempLine.substring(7));
				if(tempLine.indexOf("F_Value:") == 0)
					functionTab.addRow(tempLine.substring(8));
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
			
			for(String line : parameterTab.getDataStructure("save")) {
				pout.print(line);
			}
			for(String line : interfaceTab.getDataStructure("save")) {
				pout.print(line);
			}
			for(String line : constantTab.getDataStructure("save")) {
				pout.print(line);
			}
			for(String line : symblistTab.getDataStructure("save")) {
				pout.print(line);
			}
			for(String line : functionTab.getDataStructure("save")) {
				pout.print(line);
			}
						
			pout.close();
			
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
			
			if(parameterTab.getDataStructure("def").size() > 0) {
				for(String line : parameterTab.getDataStructure("def")) {
					pout.print(line);
				}
				pout.println();
			}
			if(interfaceTab.getDataStructure("def").size() > 0) {
				for(String line : interfaceTab.getDataStructure("def")) {
					pout.print(line);
				}
				pout.println();
			}
			if(constantTab.getDataStructure("def").size() > 0) {
				for(String line : constantTab.getDataStructure("def")) {
					pout.print(line);
				}
				pout.println();
			}
			if(symblistTab.getDataStructure("def").size() > 0) {
				for(String line : symblistTab.getDataStructure("def")) {
					pout.print(line);
				}
				pout.println();
			}
			if(functionTab.getDataStructure("def").size() > 0) {
				for(String line : functionTab.getDataStructure("def")) {
					pout.print(line);
				}
			}

			pout.println("}");
			pout.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
