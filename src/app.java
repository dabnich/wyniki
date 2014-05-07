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
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleConstants.FontConstants;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;





public class app {
	public static void main(String[] args) {
        /*SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
            	show();
            }
        });*/
		show();
		
	}
	
	final static zawody zawody = new zawody();
	final static String columnNames[] = { "nr", "imie", "nazwisko", "team", "czas", "lap"};
	
	static void init(){
		
		zawody.addZawodnik(2, "Robert", "Banach", "Hotel 4 Brzozy");
		zawody.addZawodnik(1, "Bartosz", "Banach", "Hotel 4 Brzozy");
	
		zawody.addZawodnik(26, "Micha³", "Rzeczycki", "Old School MTB Team");
		
		zawody.addZawodnik(110, "Micha³", "D¹browski", "Cyklo Team");
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
	}
	static final int val=0;
	
	public static void show() {
		init();
		
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
		final JLabel labelTime = new JLabel();
		JButton startButton = new JButton("start");
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zawody.restart();
				
			}
		});
		topPanelUp.add(startButton);			
		topPanelUp.add(JTimerLabel.getInstance());
		/*
        Timer t = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int val = Integer.valueOf(JTimerLabel.getInstance().getText());
                JTimerLabel.getInstance().setText(String.valueOf(++val));
                b.setText(String.valueOf(++val));
            }
        });
        t.start();
        */
        Timer t = new Timer(100, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
                String val = JTimerLabel.getInstance().getText();
                JTimerLabel.getInstance().setText(zawody.getTimeToString());
                labelTime.setText(String.valueOf(val));
				
			}
        	
        });
        t.start();
		
		
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
					while(model.getRowCount()>0){
						model.removeRow(0);
					}
					model.setDataVector(zawody.getPrzejazdyTable(), columnNames);
					while(modelSort.getRowCount()>0){
						modelSort.removeRow(0);
					}
					modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
					
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
	
    private static final class JTimerLabel extends JLabel{
        private static JTimerLabel INSTANCE;

        private JTimerLabel(){
            super(String.valueOf(0));
            setFont(new Font("Courier New", Font.BOLD,  18));
        }

        public static final JTimerLabel getInstance(){
            if(INSTANCE == null){
                INSTANCE = new JTimerLabel();
            }

            return INSTANCE;
        }
    }

}
