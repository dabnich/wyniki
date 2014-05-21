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
import javax.swing.JDialog;
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

		//userPanel();
		
		wyborPliku();
		
		//zawody.zawodnicyZpliku("txt.txt");
		//show();
		
	}
	static String plik = null;
	static JFrame window;
	

	public static void wyborPliku(){
		
		JPanel panel = new JPanel(new BorderLayout());
		final JFrame frame = new JFrame("userPanel");
		final JLabel label = new JLabel("Wczytaj plik z zawodnikami");
		label.setFont(new Font("Arial", 3, 25));
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
		
		panel.add(label, BorderLayout.NORTH);
		panel.add(fileCh);
		
		frame.setContentPane(panel);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setSize(700,500);
		frame.setVisible(true);
		
		
		
	}
	
	
	
	final static zawody zawody = new zawody();
	final static String columnNames[] = { "nr", "imie", "nazwisko", "team", "czas", "lap", "poz"};
	
	
	
	static void init(){
		zawody.ustawCSV("przejazdy__"+zawody.pomiar.getDateStart()+".txt");
		//zawody.zawodnicyZpliku("zawodnicy.txt");
		System.out.print(new Date().toLocaleString());

		
		/*
		zawody.addZawodnik(2, "Robert", "Banach", "Hotel 4 Brzozy");
		zawody.addZawodnik(1, "Bartosz", "Banach", "Hotel 4 Brzozy");
	
		zawody.addZawodnik(26, "Micha�", "Rzeczycki", "Old School MTB Team");
		
		zawody.addZawodnik(110, "Micha�", "D�browski", "Cyklo Team");
		zawody.addZawodnik(3, "Krzysztof", "Witek", "Nexus Team");
		zawody.addZawodnik(5, "Mateusz", "Tygielski", "Nexus Team");
		zawody.addZawodnik(45, "Tomasz", "Juchniewicz", "Renault Eco 2 Team");
		zawody.addZawodnik(8, "Robert", "Biedunkiewicz", "");
		zawody.addZawodnik(13, "Wojtek", "Wo�niak", "Hotel 4 Brzozy");
		zawody.addZawodnik(15, "�ukasz", "Derheld", "Trek Gdynia");
		zawody.addZawodnik(17, "Adam", "�wieca", "Cyklo Team");
		zawody.addZawodnik(132, "Piotr", "Habaj", "GR3miasto");
		zawody.addZawodnik(23, "Marcin", "Leszczy�ski", "");
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
				zawody.resetuj();
				//zawody.exportWyniki();
			}
			
		});
		controlPanel.add(resetButton);
		
		controlPanel.add(JTimerLabel.getInstance());
		
		JPanel controlPanelDown = new JPanel(new FlowLayout());
		JButton eksportButton = new JButton("eksportuj wyniki");
		eksportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//zawody.resetuj();
				String a = zawody.exportWyniki();
				final JDialog dialog = new JDialog(window, "title");
				JPanel panel = new JPanel();
				JLabel label; 
				if(a!=null){
					label = new JLabel("Wyniki zosta�y wyeksportowane do pliku: "+a);
				}
				else{
					label = new JLabel("B��d: wyniki nie zosta�y wyeksportowane do pliku!");
				}
				panel.add(label);
				JButton okButton = new JButton("ok");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
					}
				});
				panel.add(okButton);
				dialog.setContentPane(panel);
				dialog.setVisible(true);
				dialog.setLocation(300, 300);
				dialog.setSize(550, 70);
			}
			
		});
		controlPanelDown.add(eksportButton);
		
		JPanel controlPanelAll = new JPanel(new GridLayout(2,1));
		controlPanelAll.add(controlPanel);
		controlPanelAll.add(controlPanelDown);
		
		
		

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
		else topPanelDown.setLayout(new GridLayout(rowsButtons, 15));
		
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
		
		topPanel.setLayout(new BorderLayout());
		topPanel.add(topPanelUp, BorderLayout.NORTH);
		topPanel.add(topPanelDown, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		final JLabel labelPrzejazdy = new JLabel("tabela przejazd�w");
		final JLabel labelWyniki = new JLabel("tabela wynik�w");
		final JScrollPane scroll = new JScrollPane(table);
		final JScrollPane scrollSort = new JScrollPane(tableSort);
		JPanel panelPrzejazdy = new JPanel(new BorderLayout());
		JPanel panelWyniki = new JPanel(new BorderLayout());
		panelPrzejazdy.add(labelPrzejazdy, BorderLayout.NORTH);
		panelPrzejazdy.add(scroll);
		panelWyniki.add(labelWyniki, BorderLayout.NORTH);
		panelWyniki.add(scrollSort);
		centerPanel.add(panelPrzejazdy);
		centerPanel.add(panelWyniki);

		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(30, 30));
		content.add(topPanel, BorderLayout.NORTH);
		//content.add(new JPanel(), BorderLayout.EAST);
		content.add(centerPanel, BorderLayout.CENTER);
		//content.add(new JPanel(), BorderLayout.WEST);
		content.add(controlPanelAll, BorderLayout.SOUTH);
		
		
		
		window = new JFrame("wyniki by Michal Dabrowski");
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
