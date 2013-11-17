package view.rules;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.InputXMLDocumentPanel;
import view.grammardevelopment.editsemantics.ComponentPaletteScrollPane;
import view.grammardevelopment.editsemantics.CreationRightPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StructuralAdjustmentPanel extends JPanel {
	//JPanel actionPanel;
	
	
	DisplayScreen inputScreen;

	CreationRightPanel rightPanel;
	InputXMLDocumentPanel inputXMLPanel;
	
	public static void main (String args[])
	{
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		
		Font oldLabelFont = UIManager.getFont("Label.font");
		UIManager.put("Label.font", oldLabelFont.deriveFont(Font.PLAIN,(float)14));
		
		StructuralAdjustmentPanel sap = new StructuralAdjustmentPanel();
		JFrame frame = new JFrame();
		frame.getContentPane().add(sap);
		frame.setVisible(true);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, (int) screenSize.getWidth(), (int)screenSize.getHeight());
	}
	
	//take parameter Rule which has input and output blah
	public StructuralAdjustmentPanel() 
	{
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("421px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("right:513px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("292px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(151dlu;default)"),
				RowSpec.decode("64px"),}));
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(Color.WHITE);
		add(inputPanel, "2, 2, fill, fill");
		inputPanel.setLayout(new MigLayout("", "[grow]", "[][220.00,grow]"));
		
		JLabel lblInputStructure = new JLabel("Input Structure");
		inputPanel.add(lblInputStructure, "cell 0 0");
		
		inputScreen = new DisplayScreen();
		inputPanel.add(inputScreen, "cell 0 1,grow");
		
		JPanel actionsPanel = new JPanel();
		add(actionsPanel, "4, 2, 1, 3, right, fill");
		actionsPanel.setLayout(new MigLayout("", "[532.00px]", "[91px,grow]"));
		rightPanel = new CreationRightPanel();
		actionsPanel.add(rightPanel, "cell 0 0,alignx right,growy");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, "2, 4, fill, fill");
		panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblOutputStructure = new JLabel("Output Structure");
		panel.add(lblOutputStructure, "cell 0 0");
		
		DisplayScreen displayScreen = new DisplayScreen();
		panel.add(displayScreen, "cell 0 1,grow");
		
		JPanel buttonsPanel = new JPanel();
		add(buttonsPanel, "4, 5, default, fill");
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnOk = new JButton("OK");
		buttonsPanel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		buttonsPanel.add(btnCancel);
		
		
		initializeComponents();
		
//		addListenersToCompPalette();
	}
	
	
	//parameters: InputXMLDocumentPanel input, InputXMLDocumentPanel output
	public void setDisplay()
	{
		//call DisplayScreen.display()
	}
	
	public void initializeComponents()
	{
		
	}
	
//	private void addListenersToCompPalette() {
//		rightPanel.addListenersForAllButtons(new MouseAdapter(){
//            public void mousePressed(MouseEvent e){
//                JButton button = (JButton)e.getSource();
//                TransferHandler handle = button.getTransferHandler();
//                handle.exportAsDrag(button, e, TransferHandler.COPY);
//            }
//        });
//	}

}
