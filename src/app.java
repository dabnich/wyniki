import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
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
		

		//userPanel();
		wyborPliku();
		
		//show();
		
	}
	static String plik = null;
	

	public static void wyborPliku(){
		
		JPanel panel = new JPanel();
		final JFrame frame = new JFrame("userPanel");
		
		final JFileChooser fileCh = new JFileChooser();
		//fileCh.setCurrentDirectory(new File("C:/"));
		fileCh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.print(e.getSource().toString());
				File pl = fileCh.getSelectedFile();
				if(pl.toString().length()>0){
					zawody.zawodnicyZpliku(pl.toString());
					System.out.println(pl.getName());
					
					show();
				}
				frame.removeAll();
				frame.setVisible(false);
				
			}
		});
		
		panel.add(fileCh);
		
		frame.setContentPane(panel);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setSize(700,500);
		frame.setVisible(true);
		
		
		
	}
	
	
	
	final static zawody zawody = new zawody();
	final static String columnNames[] = { "nr", "imie", "nazwisko", "team", "czas", "lap", "poz"};
	
	
	static void userPanel(){
		
		JPanel panel = new JPanel();
		//panel.setLayout(new SpringLayout());
		
		JPanel addForm = new JPanel();
		JPanel addPanel = new JPanel();
		addForm.setLayout(new GridLayout(2,6, 10, 10));
		final JPanel explore = new JPanel();
		
		JLabel labelNr = new JLabel("numer");
		JLabel labelImie = new JLabel("imie");
		JLabel labelNazwisko = new JLabel("nazwisko");
		JLabel labelRok = new JLabel("rok urodzenia");
		JLabel labelTeam = new JLabel("team");
		
		final JTextField buttonNr = new JTextField();
		final JTextField buttonImie = new JTextField();
		final JTextField buttonNazwisko = new JTextField();
		JTextField buttonRok = new JTextField();
		final JTextField buttonTeam = new JTextField();
		
		JButton addButton = new JButton("zapisz");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					zawody.addZawodnik(Integer.parseInt(buttonNr.getText()), buttonImie.getText(), buttonNazwisko.getText(), buttonTeam.getText());
					explore.add(new JLabel(Integer.toString(zawody.zawodnicy.get(zawody.zawodnicy.size()).nr)));
					explore.add(new JLabel(zawody.zawodnicy.get(zawody.zawodnicy.size()).imie));
					explore.add(new JLabel(zawody.zawodnicy.get(zawody.zawodnicy.size()).nazwisko));					
					explore.add(new JLabel(zawody.zawodnicy.get(zawody.zawodnicy.size()).team));				
				System.out.print(zawody.zawodnicy.get(0).imie);	
				}
				catch(Exception a){
					a.getMessage();
				}
			}
		});
		
		addForm.add(labelNr);
		addForm.add(labelImie);
		addForm.add(labelNazwisko);
		addForm.add(labelRok);
		addForm.add(labelTeam);
		addForm.add(new JLabel());
		addForm.add(buttonNr);
		addForm.add(buttonImie);
		addForm.add(buttonNazwisko);
		addForm.add(buttonRok);
		addForm.add(buttonTeam);
		addForm.add(addButton);
		panel.add(addForm);
		panel.add(explore);
		
		JFrame frame = new JFrame("userPanel");
		frame.setContentPane(panel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		
	}
	
	static void init(){
		zawody.ustawCSV("przejazdy__"+zawody.pomiar.getDateStart()+".txt");
		//zawody.zawodnicyZpliku("zawodnicy.txt");
		System.out.print(new Date().toLocaleString());

		
	
		
		
		
		
		/*
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
		*/
	}
	static final int val=0;
	
	public static void show() {
		init();
		
		final DefaultTableModel model = new DefaultTableModel(zawody.getPrzejazdyTable(), columnNames);
		final DefaultTableModel modelSort = new DefaultTableModel(zawody.getWynikiTable(), columnNames);
		
		
		JTable table = new JTable(model);
		JTable tableSort = new JTable(modelSort);
		
		Dimension tableSize = table.getPreferredSize();
		
		//table.setPreferredSize(new Dimension(200, 100));
		System.out.print(tableSize.width);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(5).setPreferredWidth(10);
		//table.getColumnModel().getColumn(3).setWidth(30);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		final JLabel labelTime = new JLabel();
		
		JButton startButton = new JButton("start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zawody.startuj();
				
			}
		});
		controlPanel.add(startButton);
		
		JButton stopButton = new JButton("stop");
		stopButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				zawody.zatrzymaj();
			}
		});
		controlPanel.add(stopButton);
		
		JButton resetButton = new JButton("reset");
		resetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//zawody.resetuj();
				zawody.exportWyniki();
			}
			
		});
		controlPanel.add(resetButton);
		
		controlPanel.add(JTimerLabel.getInstance());

        final JTextField nrText = new JTextField(3);
        nrText.requestFocus();
		final JButton buttonText = new JButton("Dodaj przejazd");
		buttonText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int nr = Integer.parseInt(nrText.getText());
					if(zawody.getZawodnikById(nr) != null){
						zawody.addPrzejazd(zawody.getZawodnikById(nr));
						
						while(model.getRowCount()>0){
							model.removeRow(0);
						}
						model.setDataVector(zawody.getPrzejazdyTable(), columnNames);
						while(modelSort.getRowCount()>0){
							modelSort.removeRow(0);
						}
						modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
					}
					nrText.setText("");
				}
				catch(Exception ex){
					ex.getMessage();
				}
			}
		});
		
		nrText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10){
					try{
						int nr = Integer.parseInt(nrText.getText());
						if(zawody.getZawodnikById(nr) != null){
							zawody.addPrzejazd(zawody.getZawodnikById(nr));
							
							while(model.getRowCount()>0){
								model.removeRow(0);
							}
							model.setDataVector(zawody.getPrzejazdyTable(), columnNames);
							while(modelSort.getRowCount()>0){
								modelSort.removeRow(0);
							}
							modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
						}
						nrText.setText("");
					}
					catch(Exception ex){
						ex.getMessage();
					}
				}
			}
		});
		
		JButton backButton = new JButton("cofnij");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zawody.deleteLastPrzejazd();
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
		
		Timer t = new Timer(100, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
                String val = JTimerLabel.getInstance().getText();
                JTimerLabel.getInstance().setText(zawody.getTimeToString());
                labelTime.setText(String.valueOf(val));
                nrText.requestFocus();
				
			}
        	
        });
        t.start();
		
		
        JPanel topPanelUp = new JPanel();
        topPanelUp.add(nrText);
        topPanelUp.add(buttonText);
        topPanelUp.add(backButton);
		JPanel topPanel = new JPanel();
		
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(topPanelUp);
		topPanel.add(topPanelDown);
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		final JScrollPane scroll = new JScrollPane(table);
		final JScrollPane scrollSort = new JScrollPane(tableSort);
		centerPanel.add(scroll);
		centerPanel.add(scrollSort);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(30, 30));
		content.add(topPanel, BorderLayout.NORTH);
		//content.add(new JPanel(), BorderLayout.EAST);
		content.add(centerPanel, BorderLayout.CENTER);
		//content.add(new JPanel(), BorderLayout.WEST);
		content.add(controlPanel, BorderLayout.SOUTH);
		
		
		JFrame window = new JFrame("GUI Test");
		window.setContentPane(content);
		/*
		window.setSize(800,600);
		window.setLocation(100,100);
		*/
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
