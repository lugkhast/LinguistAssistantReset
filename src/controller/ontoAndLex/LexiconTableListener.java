package controller.ontoAndLex;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import view.ontoandlex.LexiconPanel;
import view.ontoandlex.LexiconTable;

import lexicon.Lexicon;
import lexicon.LexiconList;
import managers.LexiconManager;

public class LexiconTableListener implements TableModelListener{
	
	LexiconPanel lexPanel;
	LexiconList lexList;
	LexiconTable lexTable;
	int currMode;
	
	public LexiconTableListener(LexiconPanel lexPanel){
		this.lexPanel = lexPanel;
		this.lexTable = lexPanel.getTable();
		this.currMode = lexPanel.getCurrMode();
	}
	
	public void tableChanged(TableModelEvent tme) {
		DefaultTableModel model = (DefaultTableModel)lexTable.getModel();
		int row = tme.getFirstRow();
		int column = tme.getColumn();
		String colName = lexTable.getColumnName(column);
		lexList = LexiconManager.getInstance().getLexiconList(lexPanel.getCodeFromSelectedPOS());
		Lexicon lexicon = null ;
		switch(currMode){
			case LexiconPanel.TABLE_STEM:
							//System.out.println("Stem "+row + " "+ column);
							if(row>=0 && column>=0){
								lexicon = lexList.getLexiconList().get(row);
								if(column == 0 ){
									if(((String)model.getValueAt(row, column)).equals("")){
										JOptionPane.showMessageDialog(null,"String cannot be blank!","ERROR",JOptionPane.ERROR_MESSAGE);
										 model.setValueAt(lexicon.getName(), row, column);
									}
									else if(LexiconPanel.isLexiconExist((String)model.getValueAt(row, column),row)){
										JOptionPane.showMessageDialog(null,"No Duplicates Allowed!","ERROR",JOptionPane.ERROR_MESSAGE);
										model.setValueAt(lexicon.getName(), row, column);
									}
									else{
										lexicon.setName((String)model.getValueAt(row, column));
									}
									
								}
								
								else if (column == 1){
									if(((String)model.getValueAt(row, column)).equals("")){
										JOptionPane.showMessageDialog(null,"String cannot be blank!","ERROR",JOptionPane.ERROR_MESSAGE);
										 model.setValueAt(lexicon.getGloss(), row, column);
									}
									
									else
									lexicon.setGloss((String)model.getValueAt(row, column));
								}
								
								else if (column == 2){
									lexicon.setComments((String)model.getValueAt(row, column));
								}
								
								else if (column == 3){
									lexicon.setSampleSentence((String)model.getValueAt(row, column));
								}
							}
							break;
			case LexiconPanel.TABLE_FEATURE:
							//System.out.println("Feature "+row + " "+ column);
							if(row>=0 && column>=0){
								lexicon = lexList.getLexiconList().get(row);
								if(column == 0 ){
									if(((String)model.getValueAt(row, column)).equals("")){
										JOptionPane.showMessageDialog(null,"String cannot be blank!","ERROR",JOptionPane.ERROR_MESSAGE);
										 model.setValueAt(lexicon.getName(), row, column);
									}
									else if(LexiconPanel.isLexiconExist((String)model.getValueAt(row, column),row)){
										JOptionPane.showMessageDialog(null,"No Duplicates Allowed!","ERROR",JOptionPane.ERROR_MESSAGE);
										model.setValueAt(lexicon.getName(), row, column);
									}
									else
									lexicon.setName((String)model.getValueAt(row, column));
								}
								else if (column == 1){
									if(((String)model.getValueAt(row, column)).equals("")){
										JOptionPane.showMessageDialog(null,"String cannot be blank!","ERROR",JOptionPane.ERROR_MESSAGE);
										 model.setValueAt(lexicon.getGloss(), row, column);
									}
									else
									lexicon.setGloss((String)model.getValueAt(row, column));
								}
								else if(column>1){
									String featName =(String) lexTable.getColumnModel().getColumn(column).getHeaderValue();
									lexicon.setFeature(featName, (String)model.getValueAt(row, column));
								}
									
							}
							break;
			case LexiconPanel.TABLE_FORM:
							//System.out.println("Form "+row + " "+ column);
							if(row>=0 && column>=0){
								lexicon = lexList.getLexiconList().get(row);
								if(column == 0 ){
									if(((String)model.getValueAt(row, column)).equals("")){
										JOptionPane.showMessageDialog(null,"String cannot be blank!","ERROR",JOptionPane.ERROR_MESSAGE);
										 model.setValueAt(lexicon.getName(), row, column);
									}
									else if(LexiconPanel.isLexiconExist((String)model.getValueAt(row, column),row)){
										JOptionPane.showMessageDialog(null,"No Duplicates Allowed!","ERROR",JOptionPane.ERROR_MESSAGE);
										model.setValueAt(lexicon.getName(), row, column);
									}
									else
									lexicon.setName((String)model.getValueAt(row, column));
								}
								else if (column == 1){
									if(((String)model.getValueAt(row, column)).equals("")){
										JOptionPane.showMessageDialog(null,"String cannot be blank!","ERROR",JOptionPane.ERROR_MESSAGE);
										 model.setValueAt(lexicon.getGloss(), row, column);
									}
									else
									lexicon.setGloss((String)model.getValueAt(row, column));
								}
								else if(column>1){
									String formName =(String) lexTable.getColumnModel().getColumn(column).getHeaderValue();
									lexicon.setForm(formName, (String)model.getValueAt(row, column));
								}
									
							}
							break;
		}
	
	}

}
