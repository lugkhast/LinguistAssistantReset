package controller.listener.grammardev.editsemantics;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import components.Component;
import components.Phrase;
import controller.listener.grammardev.SelectComponentActionListener;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.InputXMLDocumentPanel;

public class InputXMLDocPanelDNDListener extends DropTargetAdapter{

	private InputXMLDocumentPanel parentDocPanel;
	public InputXMLDocPanelDNDListener(InputXMLDocumentPanel parentDocPanel){
		this.parentDocPanel = parentDocPanel;
	}
	
	public void drop(DropTargetDropEvent arg0) {
		//System.out.println(arg0.getSource()+" "+arg0.getLocation());
		InputXMLDocumentPanel panel = (InputXMLDocumentPanel)((DropTarget)arg0.getSource()).getComponent(); // panel kung saan dinrop yung button
		Transferable t = arg0.getTransferable();
		DataFlavor[] d = t.getTransferDataFlavors();
		String buttonName;
		try {
			buttonName = (String)t.getTransferData(d[0]);
			//System.out.println("BUTTON "+ buttonName);
			int index = panel.determineInsertIndex(arg0.getLocation());
			Component newComponent = Component.createInstance(buttonName);
			panel.getXMLDocument().addClauseAt(index, newComponent);
			ComponentPanel newPanel = ComponentPanel.CreateInstance(newComponent, parentDocPanel);
			newPanel.setSelectListener(panel.getSelectListener());
			
			panel.addSentencePanelAt(index, newPanel);
			parentDocPanel.adjustPositioning();

			MouseEvent me = new MouseEvent(newPanel, 0,0,0,100,100,1,false);
			for(MouseListener ml: newPanel.getMouseListeners()){
				ml.mousePressed(me);
			}
			
		} catch (UnsupportedFlavorException e) {}
		  catch (IOException e) {}
	
	}	
}
