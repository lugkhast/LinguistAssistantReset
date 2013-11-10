package controller;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.TransferHandler;

import controller.listener.editsemantics.DnDListener;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.editsemantics.ComponentPaletteScrollPane;

public class DnDController {

	DisplayScreen display;
	ComponentPaletteScrollPane cpScrollPane;
	
	public DnDController(DisplayScreen display, ComponentPaletteScrollPane cpScrollPane){
		this.display = display;
		this.cpScrollPane = cpScrollPane;
		addListenerForButtonsInCompPalette();
	}
	
	private void addListenerForButtonsInCompPalette() {
		cpScrollPane.addListenersForAllButtons(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                JButton button = (JButton)e.getSource();
                TransferHandler handle = button.getTransferHandler();
                handle.exportAsDrag(button, e, TransferHandler.COPY);
            }
        });
	}
}
