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
	long startTime;
	

	public zawody(){
		Date czas = new Date();
		startTime = czas.getTime();
	}
	
	void restart(){
		Date czas = new Date();
		startTime = czas.getTime();
	}
	
	int getTime(){
		return (int)(new Date().getTime()-startTime);
	}
	
	public String getTimeToString(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		TimeZone zone = TimeZone.getTimeZone("GMT-0");
		format.setTimeZone(zone);
		return format.format(new Date().getTime()-startTime);
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
	
	
	boolean addPrzejazd(zawodnik zawodnik){
		try{
			//int okrazenie = selectIleOkrazen(zawodnik)+1;
			int okrazenie = getOkrazenia(zawodnik)+1;
			int czas = (int) (new Date().getTime()-startTime);
			przejazdy.add(new przejazd(zawodnik, czas, okrazenie));
			
			//dodajemy wynik, wyszukiwanie odpowiedniego miejsca dla wyniku
			boolean breakLoop = false;
			int startI=0;
			for(int i=0; i<wyniki.size(); i++){
				startI = i;
				if(okrazenie > wyniki.get(i).okrazenie){
					breakLoop = true;
					break;
				}
			}
			
			ArrayList <przejazd> wynikiTmp = new ArrayList<przejazd>();
			
			if(startI>0){
				if(breakLoop){
					for(int i=0; i<startI; i++){
						wynikiTmp.add(wyniki.get(i));
					}
					wynikiTmp.add(new przejazd(zawodnik, czas, okrazenie));
					for(int i=startI; i<wyniki.size(); i++){
						if(zawodnik.nr != wyniki.get(i).zawodnik.nr) wynikiTmp.add(wyniki.get(i));
					}
				}
				else{
					for(int i=0; i<wyniki.size(); i++){
						wynikiTmp.add(wyniki.get(i));
					}
					wynikiTmp.add(new przejazd(zawodnik, czas, okrazenie));
				}
			}
			else{
				wynikiTmp.add(new przejazd(zawodnik, czas, okrazenie));
				for(int i=startI; i<wyniki.size(); i++){
					if(zawodnik.nr != wyniki.get(i).zawodnik.nr) wynikiTmp.add(wyniki.get(i));
				}
			}
			wyniki = wynikiTmp;
			return true;
		}
		catch(Exception e){
			e.getMessage();
			return false;
		}
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
			String nr, imie, nazwisko, team, czas, okrazenie;
			nr = Integer.toString(przejazdy.get(i).zawodnik.nr);
			imie = przejazdy.get(i).zawodnik.imie;
			nazwisko = przejazdy.get(i).zawodnik.nazwisko;
			team = przejazdy.get(i).zawodnik.team;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			TimeZone zone = TimeZone.getTimeZone("GMT-0");
			format.setTimeZone(zone);
			czas = format.format(przejazdy.get(i).czas);
			//format.setTimeZone("GMT-1");
			czas = format.format(przejazdy.get(i).czas);
			okrazenie = Integer.toString(przejazdy.get(i).okrazenie);
			table[n++] = new String[] {nr, imie, nazwisko, team, czas, okrazenie};
		}
		return table;
	}
	
	public String[][] getWynikiTable(){
		String[][] table = new String[wyniki.size()][6];
		for(int i=0; i<wyniki.size(); i++){
			String nr, imie, nazwisko, team, czas, okrazenie;
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
			table[i] = new String[] {nr, imie, nazwisko, team, czas, okrazenie};
		}
		return table;
	}
	

	public String[][] getWynikiSort(){
		//obliczamy max okrazen
		int okrMax=0;
		for(int i=0; i<przejazdy.size(); i++){
			if(przejazdy.get(i).okrazenie>okrMax){
				okrMax = przejazdy.get(i).okrazenie;
			}
		}
		ArrayList<przejazd> przejazdyFiltr = new ArrayList<przejazd>();
		
		
		for(int n=przejazdy.size()-1; n>=0; n--){
			boolean isset = false;
			for(int i=0; i<przejazdyFiltr.size(); i++){
				if(przejazdyFiltr.get(i).zawodnik.nr == przejazdy.get(n).zawodnik.nr){
					isset = true;
					break;
				}
			}
			if(!isset) przejazdyFiltr.add(przejazdy.get(n));
		}
		System.out.println("Filtr: ");
		for(int i=0; i<przejazdyFiltr.size(); i++){
			System.out.println(przejazdyFiltr.get(i).zawodnik.nr);
		}
		//sortujemy
		ArrayList<przejazd> przejazdySort = new ArrayList<przejazd>();
		int okr = okrMax;
		int lastSize = 0; 
		int size=0; 
		//sortujemy tabele dla kazdego okrazenia wg. czasu miedzy kluczami size, a lastSize
		
		while(okr>0){
			//dodajemy wyniki dla danego okrazenia
			lastSize = przejazdySort.size();
			for(int i=0; i<przejazdyFiltr.size(); i++){
				if(przejazdyFiltr.get(i).okrazenie==okr)przejazdySort.add(przejazdyFiltr.get(i));
			}
			size = przejazdySort.size();
			przejazd tmpPrzejazd;
			//sortujemy wyniki z danego okrazenia
			for(int n=0; n<(size-1)-lastSize; n++){	
				for(int i=lastSize; i<(size-1)-n; i++){
					tmpPrzejazd = przejazdySort.get(i);
					if(przejazdySort.get(i).czas > przejazdySort.get(i+1).czas){
						przejazdySort.set(i, przejazdySort.get(i+1));
						przejazdySort.set(i+1, tmpPrzejazd);
					}
				}
			}
			okr--;
		}
		
		
		String[][] table = new String[przejazdySort.size()][6];
		for(int i=0; i<przejazdySort.size(); i++){
			String nr, imie, nazwisko, team, czas, okrazenie;
			nr = Integer.toString(przejazdySort.get(i).zawodnik.nr);
			imie = przejazdySort.get(i).zawodnik.imie;
			nazwisko = przejazdySort.get(i).zawodnik.nazwisko;
			team = przejazdySort.get(i).zawodnik.team;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			TimeZone zone = TimeZone.getTimeZone("GMT-0");
			format.setTimeZone(zone);
			czas = format.format(przejazdy.get(i).czas);
			//format.setTimeZone("GMT-1");
			czas = format.format(przejazdySort.get(i).czas);
			//czas = Integer.toString(przejazdySort.get(i).czas);
			okrazenie = Integer.toString(przejazdySort.get(i).okrazenie);
			table[i] = new String[] {nr, imie, nazwisko, team, czas, okrazenie};
		}
		System.out.println("Wyjscie: ");
		for(int i=0; i<table.length; i++){
			System.out.println(table[i][0]);
		}
		return table;
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
