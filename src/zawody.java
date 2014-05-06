import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;


public class zawody {


	ArrayList <zawodnik> zawodnicy = new ArrayList <zawodnik>();
	ArrayList <przejazd> przejazdy = new ArrayList <przejazd>();
	long startTime;

	public zawody(){
		Date czas = new Date();
		startTime = czas.getTime();
	}
	
	boolean addZawodnik(int nr, String imie, String nazwisko, String team){
		try{
			zawodnicy.add(new zawodnik(nr, imie, nazwisko, team));
			
			return true;
		}
		catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	/*
	boolean addPrzejazd(zawodnik zawodnik, int czas){
		try{
			//int okrazenie = selectIleOkrazen(zawodnik)+1;
			int okrazenie = getOkrazenia(zawodnik);
			przejazdy.add(new przejazd(zawodnik, czas, okrazenie+1));
			
			return true;
		}
		catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	*/
	
	boolean addPrzejazd(zawodnik zawodnik){
		try{
			//int okrazenie = selectIleOkrazen(zawodnik)+1;
			int okrazenie = getOkrazenia(zawodnik);
			int czas = (int) (new Date().getTime()-startTime);
			przejazdy.add(new przejazd(zawodnik, czas, okrazenie+1));
			
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
		for(int i=0; i<przejazdy.size(); i++){
			String nr, imie, nazwisko, team, czas, okrazenie;
			nr = Integer.toString(przejazdy.get(i).zawodnik.nr);
			imie = przejazdy.get(i).zawodnik.imie;
			nazwisko = przejazdy.get(i).zawodnik.nazwisko;
			team = przejazdy.get(i).zawodnik.team;
			czas = Integer.toString(przejazdy.get(i).czas);
			okrazenie = Integer.toString(przejazdy.get(i).okrazenie);
			table[i] = new String[] {nr, imie, nazwisko, team, czas, okrazenie};
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
