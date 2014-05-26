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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
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
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class app {
	
	public static void main(String[] args) {

		//userPanel();
		
		
		init();
		zawody.zawodnicyZpliku("lista.csv");
		zawody.importujPrzejazdy("przejazdy.txt");
		zawody.exportWyniki();
		//wyborPliku();
		
		
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
	final static String columnNames[] = { "nr", "imie", "nazwisko", "team", "plec", "kategoria", "miejscosc", "czas", "lap", "poz"};
	static String filtrKategoria = "/";
	static String filtrPlec = "/";
	static String filtrMiasto = "/";
	static String filtrTeam = "/";
	
	
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
					label = new JLabel("Wyniki zosta³y wyeksportowane do pliku: "+a);
				}
				else{
					label = new JLabel("B³¹d: wyniki nie zosta³y wyeksportowane do pliku!");
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
						modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
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
							modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
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
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
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
					//modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
					modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
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
		final JLabel labelPrzejazdy = new JLabel("tabela przejazdów");
		final JLabel labelWyniki = new JLabel("tabela wyników");
		final JLabel labelFiltry = new JLabel();
		final JScrollPane scroll = new JScrollPane(table);
		final JScrollPane scrollSort = new JScrollPane(tableSort);
		JPanel panelPrzejazdy = new JPanel(new BorderLayout());
		JPanel panelWyniki = new JPanel(new BorderLayout());
		panelPrzejazdy.add(labelPrzejazdy, BorderLayout.NORTH);
		panelPrzejazdy.add(scroll);
		panelWyniki.add(labelWyniki, BorderLayout.NORTH);
		panelWyniki.add(scrollSort);
		panelWyniki.add(labelFiltry, BorderLayout.SOUTH);
		centerPanel.add(panelPrzejazdy);
		centerPanel.add(panelWyniki);
        
		JMenu filtrMenu = new JMenu("Filtr wyników");
		JMenu filtrMenuPlec = new JMenu("plec");
		JMenu filtrMenuKategoria = new JMenu("kategoria");
		JMenu filtrMenuMiasto = new JMenu("miasto");
		JMenu filtrMenuTeam = new JMenu("team");
		
		final ArrayList<JRadioButtonMenuItem> katList = new ArrayList<JRadioButtonMenuItem>();
		
		ArrayList<String> kategorie = zawody.getKategorie();
		System.out.println("ile: "+kategorie.size());
		for(int i=0; i<kategorie.size(); i++){
			final String kat=kategorie.get(i);
			JRadioButtonMenuItem tmpRadio = new JRadioButtonMenuItem(kategorie.get(i));
			katList.add(tmpRadio);
			katList.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					filtrKategoria = kat;
					System.out.println(filtrKategoria);
					for(int n=0; n<katList.size(); n++){
						if(!katList.get(n).getText().equals(kat)) katList.get(n).setSelected(false);
					}
					while(modelSort.getRowCount()>0){
						modelSort.removeRow(0);
					}
					//modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
					modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
					String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
					txt = txt.replace("/", "");
					labelFiltry.setText(txt);				
				}
			});

			filtrMenuKategoria.add(katList.get(i));
		}
		JRadioButtonMenuItem tmpRadio = new JRadioButtonMenuItem("wszyscy");
		katList.add(tmpRadio);
		katList.get(katList.size()-1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filtrKategoria = "/";
				
				for(int n=0; n<katList.size(); n++){
					if(!katList.get(n).getText().equals("wszyscy")) katList.get(n).setSelected(false);
				}
				while(modelSort.getRowCount()>0){
					modelSort.removeRow(0);
				}
				//modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
				String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
				txt = txt.replace("/", "");
				labelFiltry.setText(txt);		
			}
		});
		filtrMenuKategoria.add(katList.get(katList.size()-1));
		
		
		
		final ArrayList<JRadioButtonMenuItem> plecList = new ArrayList<JRadioButtonMenuItem>();
		plecList.add(new JRadioButtonMenuItem("k"));
		plecList.get(plecList.size()-1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filtrPlec = "k";
				for(int i=0; i<plecList.size(); i++){
					if(!plecList.get(i).getText().equals(filtrPlec)) plecList.get(i).setSelected(false);
				}
				while(modelSort.getRowCount()>0){
					modelSort.removeRow(0);
				}
				//modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
				String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
				txt = txt.replace("/", "");
				labelFiltry.setText(txt);		
			}
		});
		plecList.add(new JRadioButtonMenuItem("m"));
		plecList.get(plecList.size()-1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filtrPlec = "m";
				for(int i=0; i<plecList.size(); i++){
					if(!plecList.get(i).getText().equals(filtrPlec)) plecList.get(i).setSelected(false);
				}
				while(modelSort.getRowCount()>0){
					modelSort.removeRow(0);
				}
				//modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
				String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
				txt = txt.replace("/", "");
				labelFiltry.setText(txt);		
			}
		});
		plecList.add(new JRadioButtonMenuItem("wszyscy"));
		plecList.get(plecList.size()-1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filtrPlec = "/";
				for(int i=0; i<plecList.size(); i++){
					if(!plecList.get(i).getText().equals("wszyscy")) plecList.get(i).setSelected(false);
				}
				while(modelSort.getRowCount()>0){
					modelSort.removeRow(0);
				}
				//modelSort.setDataVector(zawody.getWynikiTable(), columnNames);
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
				String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
				txt = txt.replace("/", "");
				labelFiltry.setText(txt);		
			}
		});
		for(int i=0; i<plecList.size(); i++){
			filtrMenuPlec.add(plecList.get(i));
		}
		
		
		
		final ArrayList<JRadioButtonMenuItem> miejscList = new ArrayList<JRadioButtonMenuItem>();
		ArrayList<String> miejscowosci = zawody.getMiejscowosci();
		for(int i=0; i<miejscowosci.size(); i++){
			final String tmpMiejsc = miejscowosci.get(i);
			tmpRadio = new JRadioButtonMenuItem(tmpMiejsc);
			miejscList.add(tmpRadio);
			miejscList.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					filtrMiasto = tmpMiejsc;
					for(int n=0; n<miejscList.size(); n++){
						if(!miejscList.get(n).getText().equals(tmpMiejsc)) miejscList.get(n).setSelected(false);
					}
					while(modelSort.getRowCount()>0){
						modelSort.removeRow(0);
					}
					modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
					String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
					txt = txt.replace("/", "");
					labelFiltry.setText(txt);		
				}
			});
			filtrMenuMiasto.add(miejscList.get(i));
		}
		
		tmpRadio = new JRadioButtonMenuItem("wszystkie miasta");
		miejscList.add(tmpRadio);
		miejscList.get(miejscList.size()-1).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filtrMiasto = "/";
				for(int n=0; n<miejscList.size(); n++){
					if(!miejscList.get(n).getText().equals("wszystkie miasta")) miejscList.get(n).setSelected(false);
				}
				while(modelSort.getRowCount()>0){
					modelSort.removeRow(0);
				}
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);				
				String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
				txt = txt.replace("/", "");
				labelFiltry.setText(txt);		
			}
		});
		filtrMenuMiasto.add(miejscList.get(miejscList.size()-1));
		
		
		final ArrayList<JRadioButtonMenuItem> teamList = new ArrayList<JRadioButtonMenuItem>();
		ArrayList<String> teamy = zawody.getTeamy();
		for(int i=0; i<teamy.size(); i++){
			final String tmpTeam = teamy.get(i);
			tmpRadio = new JRadioButtonMenuItem(tmpTeam);
			teamList.add(tmpRadio);
			teamList.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					filtrTeam = tmpTeam;
					for(int n=0; n<teamList.size(); n++){
						if(!teamList.get(n).getText().equals(tmpTeam)) teamList.get(n).setSelected(false);
					}
					while(modelSort.getRowCount()>0){
						modelSort.removeRow(0);
					}
					modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);
					String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
					txt = txt.replace("/", "");
					labelFiltry.setText(txt);		
				}
			});
			filtrMenuTeam.add(teamList.get(i));
		}
		
		tmpRadio = new JRadioButtonMenuItem("wszystkie teamy");
		teamList.add(tmpRadio);
		teamList.get(teamList.size()-1).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filtrTeam = "/";
				for(int n=0; n<teamList.size(); n++){
					if(!teamList.get(n).getText().equals("wszystkie teamy")) teamList.get(n).setSelected(false);
				}
				while(modelSort.getRowCount()>0){
					modelSort.removeRow(0);
				}
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);				
				String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
				txt = txt.replace("/", "");
				labelFiltry.setText(txt);		
			}
		});
		filtrMenuTeam.add(teamList.get(teamList.size()-1));
		
		filtrMenu.add(filtrMenuPlec);
		filtrMenu.add(filtrMenuKategoria);
		filtrMenu.add(filtrMenuMiasto);
		filtrMenu.add(filtrMenuTeam);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(filtrMenu);
		JMenuItem resetFiltrMenu = new JMenuItem("Resetuj filtry");
		resetFiltrMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filtrPlec="/";
				filtrKategoria="/";
				filtrMiasto="/";
				filtrTeam="/";
				while(modelSort.getRowCount()>0){
					modelSort.removeRow(0);
				}
				modelSort.setDataVector(zawody.getWynikiTable(filtrPlec, filtrKategoria, filtrMiasto, filtrTeam), columnNames);				
				String txt = "Filtry: "+filtrPlec+"     "+filtrKategoria+"     "+filtrMiasto+"     "+filtrTeam;
				txt = txt.replace("/", "");
				labelFiltry.setText(txt);		
				
			}
		});
		menuBar.add(resetFiltrMenu);
        
        



		
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
		window.setSize(800,600);
		//window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setVisible(true);
		window.setJMenuBar(menuBar);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
