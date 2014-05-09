import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.naming.spi.DirStateFactory.Result;


public class zawody {


	ArrayList <zawodnik> zawodnicy = new ArrayList <zawodnik>();
	ArrayList <przejazd> przejazdy = new ArrayList <przejazd>();
	ArrayList <przejazd> wyniki = new ArrayList<przejazd>();
	pomiar pomiar;

	// 0 - pomiar nie wystartowal
	// 1 - pomiar wstrzymany, ale wczesniej wystartowal
	// 2 - pomiar aktywny
	

	public zawody(){
		pomiar = new pomiar();
	}
	
	void startuj(){
		pomiar.startuj();
	}
	
	void zatrzymaj(){
		pomiar.zatrzymaj();
	}
	
	void resetuj(){
		if(pomiar.status==1) pomiar.resetuj();
	}
	
	int getTime(){
		return pomiar.getInt();
	}
	
	public String getTimeToString(){
		return pomiar.getString();
	}
	
	boolean addZawodnik(int nr, String imie, String nazwisko, String team){
		try{
			//zawodnicy.add(new zawodnik(nr, imie, nazwisko, team));
			int startI=0;
			boolean breakLoop = false;
			for(int i=0; i<zawodnicy.size(); i++){
				startI=i;
				if(nr==zawodnicy.get(i).nr) return false; //kontrola czy nr juz istnieje
				if(nr < zawodnicy.get(i).nr){ 
					breakLoop = true;
					break;
				}
			}
			ArrayList <zawodnik> zawodnicyTmp = new ArrayList<zawodnik>();
			//if(!breakLoop) startI+=1;
			if(startI>0){
				if(breakLoop){  
					
					for(int i=0; i<startI; i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
					zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team));
					for(int i=startI; i<zawodnicy.size(); i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
				}
				else{
					for(int i=0; i<zawodnicy.size(); i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
					zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team));
				}

			}
			else{
				zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team));
				for(int i=startI; i<zawodnicy.size(); i++){
					zawodnicyTmp.add(zawodnicy.get(i));
				}
			}
			zawodnicy = zawodnicyTmp;
			return true;
		}
		catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	boolean addZawodnik(ArrayList<String> list){
		try{
			//zawodnicy.add(new zawodnik(nr, imie, nazwisko, team));
			int nr = Integer.parseInt(list.get(0));
			String imie = list.get(1);
			String nazwisko = list.get(2);
			String team="";
			if(list.size()>3){
				if(list.get(3).length()>0){
					team = list.get(3);
				}
			}
			int startI=0;
			boolean breakLoop = false;
			for(int i=0; i<zawodnicy.size(); i++){
				startI=i;
				if(nr==zawodnicy.get(i).nr) return false; //kontrola czy nr juz istnieje
				if(nr < zawodnicy.get(i).nr){ 
					breakLoop = true;
					break;
				}
			}
			ArrayList <zawodnik> zawodnicyTmp = new ArrayList<zawodnik>();
			//if(!breakLoop) startI+=1;
			if(startI>0 || !breakLoop){
				if(breakLoop){  
					
					for(int i=0; i<startI; i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
					zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team));
					for(int i=startI; i<zawodnicy.size(); i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
				}
				else{
					for(int i=0; i<zawodnicy.size(); i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
					zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team));
				}

			}
			else{
				zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team));
				for(int i=startI; i<zawodnicy.size(); i++){
					zawodnicyTmp.add(zawodnicy.get(i));
				}
			}
			zawodnicy = zawodnicyTmp;
			return true;
		}
		catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	
	ArrayList<String> addPrzejazd(zawodnik zawodnik){
		try{
			int okrazenie = getOkrazenia(zawodnik)+1;
			int czas = pomiar.getInt();
			
			ArrayList <przejazd> wynikiTmp = new ArrayList<przejazd>();
			
			boolean breakLoop = false;
			int startI=0;
			for(int i=0; i<wyniki.size(); i++){
				startI = i;
				if(okrazenie > wyniki.get(i).okrazenie){
					breakLoop = true;
					break;
				}
			}
			
			int poz=0;
			int nPoz=1;
			if(startI>0 || !breakLoop){
				if(breakLoop){
					for(int i=0; i<startI; i++){
						przejazd tmpWynik = wyniki.get(i);
						tmpWynik.ustawPozycje(nPoz);
						wynikiTmp.add(tmpWynik);
						nPoz++;
					}
					wynikiTmp.add(new przejazd(zawodnik, czas, okrazenie, nPoz));
					poz = nPoz;
					nPoz++;
					for(int i=startI; i<wyniki.size(); i++){
						if(zawodnik.nr != wyniki.get(i).zawodnik.nr) {
							przejazd tmpWynik = wyniki.get(i);
							tmpWynik.ustawPozycje(nPoz);
							wynikiTmp.add(tmpWynik);
							nPoz++;
						}
					}
				}
				else{
					for(int i=0; i<wyniki.size(); i++){
						przejazd tmpWynik = wyniki.get(i);
						tmpWynik.ustawPozycje(nPoz);
						wynikiTmp.add(tmpWynik);
						nPoz++;
					}

					wynikiTmp.add(new przejazd(zawodnik, czas, okrazenie, nPoz));
					poz = nPoz;
				}
			}
			else{
				wynikiTmp.add(new przejazd(zawodnik, czas, okrazenie, nPoz));
				poz = nPoz;
				nPoz++;
				for(int i=startI; i<wyniki.size(); i++){
					if(zawodnik.nr != wyniki.get(i).zawodnik.nr){ 
						przejazd tmpWynik = wyniki.get(i);
						tmpWynik.ustawPozycje(nPoz);
						wynikiTmp.add(tmpWynik);
						nPoz++;
					}
				}
			}
			wyniki = wynikiTmp;
			przejazdy.add(new przejazd(zawodnik, czas, okrazenie, poz));
			//return true;
			ArrayList<String> lista = new ArrayList<String>();
			lista.add(Integer.toString(czas));
			lista.add(Integer.toString(zawodnik.nr));
			lista.add(zawodnik.imie);
			lista.add(zawodnik.nazwisko);
			lista.add(Integer.toString(okrazenie));
			lista.add(Integer.toString(poz));
			return lista;
		}
		catch(Exception e){
			e.getMessage();
			return null;
		}
	}
	
	private int addWynik(przejazd przejazd){
		
			int okrazenie = przejazd.okrazenie;
			//int czas = pomiar.getInt();
			
			ArrayList <przejazd> wynikiTmp = new ArrayList<przejazd>();
			
			boolean breakLoop = false;
			int startI=0;
			for(int i=0; i<wyniki.size(); i++){
				startI = i;
				if(okrazenie > wyniki.get(i).okrazenie){
					breakLoop = true;
					break;
				}
			}
			
			int poz=0;
			int nPoz=1;
			if(startI>0 || !breakLoop){
				if(breakLoop){
					for(int i=0; i<startI; i++){
						przejazd tmpWynik = wyniki.get(i);
						tmpWynik.ustawPozycje(nPoz);
						wynikiTmp.add(tmpWynik);
						nPoz++;
					}
					wynikiTmp.add(przejazd);
					poz = nPoz;
					nPoz++;
					for(int i=startI; i<wyniki.size(); i++){
						if(przejazd.zawodnik.nr != wyniki.get(i).zawodnik.nr) {
							przejazd tmpWynik = wyniki.get(i);
							tmpWynik.ustawPozycje(nPoz);
							wynikiTmp.add(tmpWynik);
							nPoz++;
						}
					}
				}
				else{
					for(int i=0; i<wyniki.size(); i++){
						przejazd tmpWynik = wyniki.get(i);
						tmpWynik.ustawPozycje(nPoz);
						wynikiTmp.add(tmpWynik);
						nPoz++;
					}

					wynikiTmp.add(przejazd);
					poz = nPoz;
				}
			}
			else{
				wynikiTmp.add(przejazd);
				poz = nPoz;
				nPoz++;
				for(int i=startI; i<wyniki.size(); i++){
					if(przejazd.zawodnik.nr != wyniki.get(i).zawodnik.nr){ 
						przejazd tmpWynik = wyniki.get(i);
						tmpWynik.ustawPozycje(nPoz);
						wynikiTmp.add(tmpWynik);
						nPoz++;
					}
				}
			}
			
			wyniki = wynikiTmp;
			return poz;
	}
	
	
	public String[][] getZawodnicyTable(){
		String[][] table = new String [zawodnicy.size()][4];
		for(int i=0; i<zawodnicy.size(); i++){
			String imie, nazwisko, nr, team;
			nr = Integer.toString(zawodnicy.get(i).nr);
			imie = zawodnicy.get(i).imie;
			nazwisko = zawodnicy.get(i).nazwisko;
			team = zawodnicy.get(i).team;
			table[i] = new String[] {nr, imie, nazwisko, team};
		}
		return table;
	}
	
	public String[][] getPrzejazdyTable(){
		String[][] table = new String[przejazdy.size()][6];
		int n=0;
		for(int i=przejazdy.size()-1; i>=0; i--){
			String nr, imie, nazwisko, team, czas, okrazenie, pozycja;
			nr = Integer.toString(przejazdy.get(i).zawodnik.nr);
			imie = przejazdy.get(i).zawodnik.imie;
			nazwisko = przejazdy.get(i).zawodnik.nazwisko;
			team = przejazdy.get(i).zawodnik.team;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.S");
			TimeZone zone = TimeZone.getTimeZone("GMT-0");
			format.setTimeZone(zone);
			czas = format.format(przejazdy.get(i).czas);
			//format.setTimeZone("GMT-1");
			czas = format.format(przejazdy.get(i).czas);
			okrazenie = Integer.toString(przejazdy.get(i).okrazenie);
			pozycja = Integer.toString(przejazdy.get(i).pozycja);
			table[n++] = new String[] {nr, imie, nazwisko, team, czas, okrazenie, pozycja};
		}
		return table;
	}
	
	public String[][] getWynikiTable(){
		String[][] table = new String[wyniki.size()][6];
		for(int i=0; i<wyniki.size(); i++){
			String pozycja, nr, imie, nazwisko, team, czas, okrazenie;
			nr = Integer.toString(wyniki.get(i).zawodnik.nr);
			imie = wyniki.get(i).zawodnik.imie;
			nazwisko = wyniki.get(i).zawodnik.nazwisko;
			team = wyniki.get(i).zawodnik.team;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			TimeZone zone = TimeZone.getTimeZone("GMT-0");
			format.setTimeZone(zone);
			czas = format.format(wyniki.get(i).czas);
			//format.setTimeZone("GMT-1");
			czas = format.format(wyniki.get(i).czas);
			//czas = Integer.toString(przejazdySort.get(i).czas);
			okrazenie = Integer.toString(wyniki.get(i).okrazenie);
			pozycja = Integer.toString(wyniki.get(i).pozycja);
			table[i] = new String[] {nr, imie, nazwisko, team, czas, okrazenie, pozycja};
		}
		return table;
	}

	public void deleteLastPrzejazd(){
		int nr=przejazdy.get(przejazdy.size()-1).zawodnik.nr;
		int okr=przejazdy.get(przejazdy.size()-1).okrazenie;
		ArrayList<przejazd> wynikiTmp = new ArrayList<przejazd>();
		for(int i=0; i<wyniki.size(); i++){
			if(wyniki.get(i).zawodnik.nr==nr && wyniki.get(i).okrazenie==okr){
				
			}
			else{
				wynikiTmp.add(wyniki.get(i));
			}
		}
		wyniki = wynikiTmp;
		przejazdy.remove(przejazdy.size()-1);
		for(int i=przejazdy.size()-1; i>=0; i--){
			if(przejazdy.get(i).zawodnik.nr == nr){
				addWynik(przejazdy.get(i));
				break;
			}
		}
		
		
	}
		
		
	
	
	public String[] getLastPrzejazd(){
		String nr, imie, nazwisko, team, czas, okrazenie;
		nr = Integer.toString(przejazdy.get(przejazdy.size()-1).zawodnik.nr);
		imie = przejazdy.get(przejazdy.size()-1).zawodnik.imie;
		nazwisko = przejazdy.get(przejazdy.size()-1).zawodnik.nazwisko;
		team = przejazdy.get(przejazdy.size()-1).zawodnik.team;
		czas = Integer.toString(przejazdy.get(przejazdy.size()-1).czas);
		okrazenie = Integer.toString(przejazdy.get(przejazdy.size()-1).okrazenie);
		return new String[]{nr, imie, nazwisko, team, czas, okrazenie};
	}
	
	public zawodnik getZawodnikById(int nr){
		for(int i=0; i<zawodnicy.size(); i++){
			if(zawodnicy.get(i).nr==nr) return zawodnicy.get(i);
		}
		return null;
	}
	
	public int getOkrazenia(zawodnik zawodnik){
		for(int i=przejazdy.size()-1; i>=0; i--){
			if(przejazdy.get(i).zawodnik.nr==zawodnik.nr) return przejazdy.get(i).okrazenie;
		}
		return 0;
	}

	
}
