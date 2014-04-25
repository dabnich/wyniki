import javax.swing.JOptionPane;

import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.TableModel;

import java.util.*;


public class app implements ActionListener{

	private static class appDisplay extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		    g.drawString( "Hello World!", 300, 30 );
		}
	}
	
	
	public static void main(String[] args) {
		final ArrayList<Integer> numeryLista = new ArrayList<Integer>();
		Date czas = new Date();
		final long startTime = czas.getTime();
		//--------------------------------------------------------//
		

		final JTextField numerText = new JTextField(3);
		numerText.setBackground(new Color(200,200,10));
		//appDisplay displayPanel = new appDisplay();
		
		final JTextArea numeryArea = new JTextArea(10, 10);
		numeryArea.setRows(5);
		numeryArea.setColumns(3);
		//numeryArea.setLocation(150, 50);
		numeryArea.setBackground(new Color(200, 200, 150));
		
		final JTextArea nazwyArea = new JTextArea(10, 10);
		nazwyArea.setRows(5);
		//nazwyArea.setLocation(150, 50);
		nazwyArea.setBackground(new Color(200, 200, 150));
		
		/*
		final JTable numeryTable = new JTable(100, 2);
		TableModel dataModel = new AbstractTableModel() {
			public int getColumnCount() { return 10; }
			public int getRowCount() { return 10;}
			public Object getValueAt(int row, int col) { return new Integer(row*col); }
		};
		numeryTable.setM
		*/
		
		
		
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
					nazwyArea.setText(Long.toString((new Date().getTime()-startTime)/1000));
					
				}
				numerText.setText("");
				
			}
		});
		//ButtonHandler listener = new ButtonHandler();

		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(numerText);
		topPanel.add(okButton);
		
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(3));
		centerPanel.add(numeryArea);
		centerPanel.add(nazwyArea);
		final JScrollPane scroll = new JScrollPane(centerPanel);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(30, 30));
		content.add(topPanel, BorderLayout.NORTH);
		//content.add(new JPanel(), BorderLayout.EAST);
		content.add(scroll, BorderLayout.CENTER);
		//content.add(new JPanel(), BorderLayout.WEST);
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
