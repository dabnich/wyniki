import java.awt.BorderLayout;
import java.awt.FlowLayout;
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


public class app {

	
	
	public static void main(String[] args) {

		
		final zawody zawody = new zawody();
		zawody.addZawodnik(110, "Micha³", "D¹browski", "Cyklo Team");
		zawody.addZawodnik(26, "Micha³", "Rzeczycki", "Old School MTB Team");
		zawody.addZawodnik(1, "Bartosz", "Banach", "Hotel 4 Brzozy");
		final String columnNames[] = { "nr", "imie", "nazwisko", "team", "czas", "lap"};

		//String[][] lista = zawody.getZawodnicyTable();
		
		final DefaultTableModel model = new DefaultTableModel(zawody.getPrzejazdyTable(), columnNames);
	
		JTable table = new JTable(model);

		
		

		
		
		JPanel topPanelUp = new JPanel();
		topPanelUp.setLayout(new FlowLayout());
		final JScrollPane scrollPane = new JScrollPane();
		//topPanelUp.add(numerText);
		//topPanelUp.add(okButton);
		
		int rowsButtons = zawody.zawodnicy.size()/12;
		JPanel topPanelDown = new JPanel();
		if(zawody.zawodnicy.size()<12){ 
			topPanelDown.setLayout(new FlowLayout());
		}
		else topPanelDown.setLayout(new GridLayout(rowsButtons, 12));
		
		
		final ArrayList <JButton> buttonsList = new ArrayList<JButton>();
		
		for(int i=0; i<zawody.zawodnicy.size(); i++){
			final zawodnik tZaw = zawody.zawodnicy.get(i);
			JButton tmpButton = new JButton(Integer.toString(zawody.zawodnicy.get(i).nr));
			buttonsList.add(tmpButton);
			buttonsList.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//zawodnik tZaw = z.getZawodnikById(Integer.parseInt(buttonsList.get(buttonsList.size()-1).getText()));
					zawody.addPrzejazd(tZaw);
					model.addRow(zawody.getLastPrzejazd());
				}
			});
			topPanelDown.add(buttonsList.get(i));
		}
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(topPanelUp);
		topPanel.add(topPanelDown);
		// Create some data
		//String dataValues[][] = zawody.getZawodnicyTable();
		//String dataValues[][] = zawody.getPrzejazdyTable();
		
		//scrollPane.add(table)
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(30, 30));
		content.add(topPanel, BorderLayout.NORTH);
		//content.add(new JPanel(), BorderLayout.EAST);
		//content.add(scrollPane, BorderLayout.CENTER);
		content.add(table, BorderLayout.CENTER);
		//content.add(new JPanel(), BorderLayout.WEST);
		content.add(new JTextField(3), BorderLayout.SOUTH);
		
		
		JFrame window = new JFrame("GUI Test");
		window.setContentPane(content);
		window.setSize(800,600);
		window.setLocation(100,100);
		window.setVisible(true);

	}

}
