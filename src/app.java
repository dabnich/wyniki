import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;


public class app implements ActionListener{

	private static class appDisplay extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		    g.drawString( "Hello World!", 300, 30 );
		}
	}
	

	
	public static void main(String[] args) {
		final ArrayList<Integer> numeryLista = new ArrayList<Integer>();
		//--------------------------------------------------------//
		
		
		final JTextField numerText = new JTextField(3);
		numerText.setBackground(new Color(200,200,10));
		//appDisplay displayPanel = new appDisplay();
		
		final JTextArea numeryArea = new JTextArea();
		numeryArea.setBackground(new Color(200, 200, 150));
		
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tmpNumery="";
				if(isInteger(numerText.getText())){
					numeryLista.add(Integer.parseInt(numerText.getText()));
					for(int i=0; i<numeryLista.size(); i++){
						tmpNumery += "\n"+numeryLista.get(i);
					}
					numeryArea.setText(tmpNumery);
				}
				numerText.setText("");
				
			}
		});
		//ButtonHandler listener = new ButtonHandler();

		
		
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(numerText);
		topPanel.add(okButton);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(topPanel, BorderLayout.NORTH);
		content.add(numeryArea, BorderLayout.CENTER);
		content.add(new JTextField(3), BorderLayout.SOUTH);
		//content.add(displayPanel, BorderLayout.CENTER);
		//content.add(okButton, BorderLayout.SOUTH);
		
		JFrame window = new JFrame("GUI Test");
		window.setContentPane(content);
		window.setSize(500,500);
		window.setLocation(100,100);
		window.setVisible(true);
	}

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
