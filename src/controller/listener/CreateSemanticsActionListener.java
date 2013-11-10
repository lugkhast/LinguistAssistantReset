package controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import view.grammardevelopment.MainFrame;

public class CreateSemanticsActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		MainFrame.getInstance().setPanel(new JPanel());
	}
}
