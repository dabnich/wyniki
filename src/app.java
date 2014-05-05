import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
		final zawody z = new zawody();
		z.createTables();
		z.addZawodnik(4, "Michal", "Dabrowski", "Cyklo");

		ArrayList<zawodnik> zawodnicy = z.selectZawodnicy();
		zawodnicy = z.selectZawodnicy();
		//zawodnicy = z.selectZawodnicy();
		System.out.println(zawodnicy.get(0).nr);
		
		
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
		
		final JTextArea teamyArea = new JTextArea(10, 10);
		teamyArea.setRows(5);
		//nazwyArea.setLocation(150, 50);
		teamyArea.setBackground(new Color(200, 200, 150));
		
		final JTextArea czasyArea = new JTextArea(10, 10);
		czasyArea.setRows(5);
		//nazwyArea.setLocation(150, 50);
		czasyArea.setBackground(new Color(200, 200, 150));

		final JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tmpNumery="";
				String tmpNazwy="";
				String tmpCzasy="";
				String tmpTeamy="";
				if(isInteger(numerText.getText())){
					int a = (int) (new Date().getTime()-startTime)/1000;
					zawodnik tZaw = z.getZawodnikById(Integer.parseInt(numerText.getText()));
					z.addPrzejazd(tZaw, a);
					for(int i=0; i<z.przejazdy.size(); i++){
						tmpNumery += "\n"+Integer.toString(z.przejazdy.get(i).zawodnik.nr);
						tmpNazwy += "\n"+z.przejazdy.get(i).zawodnik.imie+" "+z.przejazdy.get(i).zawodnik.nazwisko;
						tmpTeamy += "\n"+z.przejazdy.get(i).zawodnik.team;
						tmpCzasy += "\n"+Integer.toString(z.przejazdy.get(i).czas);
					}
					numeryArea.setText(tmpNumery);
					nazwyArea.setText(tmpNazwy);
					teamyArea.setText(tmpTeamy);
					czasyArea.setText(tmpCzasy);
					
				}
				numerText.setText("");
				
			}
		});
		//ButtonHandler listener = new ButtonHandler();

		
		

		
		JPanel topPanelUp = new JPanel();
		topPanelUp.setLayout(new FlowLayout());
		topPanelUp.add(numerText);
		topPanelUp.add(okButton);
		
		int rowsButtons = zawodnicy.size()/12+1;
		JPanel topPanelDown = new JPanel();
		topPanelDown.setLayout(new GridLayout(rowsButtons, 12));
		final ArrayList <JButton> buttonsList = new ArrayList<JButton>();
		//JButton[] nrButtons = new JButton[zawodnicy.size()];
		
		for(int i=0; i<zawodnicy.size(); i++){
			buttonsList.add(new JButton( Integer.toString(zawodnicy.get(i).nr)));
			buttonsList.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String tmpNumery="";
					String tmpNazwy="";
					String tmpCzasy="";
					String tmpTeamy="";
					if(isInteger(buttonsList.get(buttonsList.size()-1).getText())){
						int a = (int) (new Date().getTime()-startTime)/1000;
						zawodnik tZaw = z.getZawodnikById(Integer.parseInt(buttonsList.get(buttonsList.size()-1).getText()));
						z.addPrzejazd(tZaw, a);
						for(int i=0; i<z.przejazdy.size(); i++){
							tmpNumery += "\n"+Integer.toString(z.przejazdy.get(i).zawodnik.nr);
							tmpNazwy += "\n"+z.przejazdy.get(i).zawodnik.imie+" "+z.przejazdy.get(i).zawodnik.nazwisko;
							tmpTeamy += "\n"+z.przejazdy.get(i).zawodnik.team;
							tmpCzasy += "\n"+Integer.toString(z.przejazdy.get(i).czas);
						}
						numeryArea.setText(tmpNumery);
						nazwyArea.setText(tmpNazwy);
						teamyArea.setText(tmpTeamy);
						czasyArea.setText(tmpCzasy);
					}
				}
			});
			topPanelDown.add(buttonsList.get(i));
		}
		

		

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(topPanelUp);
		topPanel.add(topPanelDown);
		
		
		

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(20));
		centerPanel.add(numeryArea);
		centerPanel.add(nazwyArea);
		centerPanel.add(teamyArea);
		centerPanel.add(czasyArea);
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
