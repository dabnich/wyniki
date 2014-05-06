import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleConstants.FontConstants;


public class app {

	private static class display extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString( "Hello World!", 0 , 0);
		}
	}
	
	public static void main(String[] args) {


		
		final zawody zawody = new zawody();
		zawody.addZawodnik(110, "Micha³", "D¹browski", "Cyklo Team");
		zawody.addZawodnik(26, "Micha³", "Rzeczycki", "Old School MTB Team");
		zawody.addZawodnik(1, "Bartosz", "Banach", "Hotel 4 Brzozy");
		zawody.addZawodnik(2, "Robert", "Banach", "Hotel 4 Brzozy");
		zawody.addZawodnik(3, "Krzysztof", "Witek", "Nexus Team");
		zawody.addZawodnik(5, "Mateusz", "Tygielski", "Nexus Team");
		zawody.addZawodnik(45, "Tomasz", "Juchniewicz", "Renault Eco 2 Team");
		zawody.addZawodnik(8, "Robert", "Biedunkiewicz", "");
		zawody.addZawodnik(13, "Wojtek", "WoŸniak", "Hotel 4 Brzozy");
		zawody.addZawodnik(15, "£ukasz", "Derheld", "Trek Gdynia");
		zawody.addZawodnik(17, "Adam", "Œwieca", "Cyklo Team");
		zawody.addZawodnik(132, "Piotr", "Habaj", "GR3miasto");
		zawody.addZawodnik(23, "Marcin", "Leszczyñski", "");
		zawody.addZawodnik(27, "Patryk", "Sala", "");
		final String columnNames[] = { "nr", "imie", "nazwisko", "team", "czas", "lap"};

		//String[][] lista = zawody.getZawodnicyTable();
		
		final DefaultTableModel model = new DefaultTableModel(zawody.getPrzejazdyTable(), columnNames);
		final DefaultTableModel modelSort = new DefaultTableModel(zawody.getWynikiTable(), columnNames);
		
		
		JTable table = new JTable(model);
		JTable tableSort = new JTable(modelSort);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		//table.getColumnModel().getColumn(3).setWidth(30);
		
		JPanel topPanelUp = new JPanel();
		topPanelUp.setLayout(new FlowLayout());
		
		final JScrollPane scroll = new JScrollPane(table);
		final JScrollPane scrollSort = new JScrollPane(tableSort);
		
		//topPanelUp.add(numerText);
		//topPanelUp.add(okButton);
		
		int rowsButtons = zawody.zawodnicy.size()/15+1;
		JPanel topPanelDown = new JPanel();
		if(zawody.zawodnicy.size()<15){ 
			topPanelDown.setLayout(new FlowLayout());
		}
		else topPanelDown.setLayout(new GridLayout(rowsButtons, 12));
		
		
		final ArrayList <JButton> buttonsList = new ArrayList<JButton>();
		
		for(int i=0; i<zawody.zawodnicy.size(); i++){
			final zawodnik tZaw = zawody.zawodnicy.get(i);
			JButton tmpButton = new JButton(Integer.toString(zawody.zawodnicy.get(i).nr));
			tmpButton.setFont(new Font("Arial", Font.TYPE1_FONT, 11));
			buttonsList.add(tmpButton);
			buttonsList.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					zawody.addPrzejazd(tZaw);
					while(modelSort.getRowCount()>0){
						modelSort.removeRow(0);
					}
					modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
					model.addRow(zawody.getLastPrzejazd());
				}
			});
			topPanelDown.add(buttonsList.get(i));
		}
		
		JPanel topPanel = new JPanel();
		
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(topPanelUp);
		topPanel.add(topPanelDown);
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		centerPanel.add(scroll);
		centerPanel.add(scrollSort);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(30, 30));
		content.add(topPanel, BorderLayout.NORTH);
		//content.add(new JPanel(), BorderLayout.EAST);
		content.add(centerPanel, BorderLayout.CENTER);
		//content.add(new JPanel(), BorderLayout.WEST);
		//content.add(display, BorderLayout.SOUTH);
		
		
		JFrame window = new JFrame("GUI Test");
		
		window.setContentPane(content);
		window.setSize(800,600);
		window.setLocation(100,100);
		window.setVisible(true);

	}

}
