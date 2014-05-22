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
	ArrayList <okrazenie> okrazenia = new ArrayList<okrazenie>();
	pomiar pomiar;
	csv plik;

	// 0 - pomiar nie wystartowal
	// 1 - pomiar wstrzymany, ale wczesniej wystartowal
	// 2 - pomiar aktywny
	

	public zawody(){
		pomiar = new pomiar();
	}
	
	public void ustawCSV(String nazwaPliku){
		plik = new csv(nazwaPliku);
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
	
	private String timeToString(int time){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		TimeZone zone = TimeZone.getTimeZone("GMT-0");
		format.setTimeZone(zone);
		return format.format(time);
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
	
	int zawodnicyZpliku(String plik){
		csv plikZawodnicy = new csv(plik);
		ArrayList<ArrayList<String>> listaZawodnicy = new ArrayList<ArrayList<String>>();
		listaZawodnicy = plikZawodnicy.getToList();
		if(listaZawodnicy==null) return 0;
		int size=0;
		for(int i=0; i<listaZawodnicy.size(); i++){
			if(addZawodnik(listaZawodnicy.get(i))) size++;
		}
		return size;
		
	}
	
	boolean addZawodnik(ArrayList<String> list){
		try{
			//zawodnicy.add(new zawodnik(nr, imie, nazwisko, team));
			int nr = Integer.parseInt(list.get(0));
			String imie = list.get(1);
			String nazwisko = list.get(2);
			String team="";
			String plec="m";
			String kategoria = "elita";
			String miejscowosc = null;
			if(list.size()>3){
				if(list.get(3).length()>0){
					team = list.get(3);
				}
			}
			if(list.size()>4){
				if(list.get(4).length()>0){
					if(list.get(4).charAt(0) == 'k' || list.get(4).charAt(0)=='m'){
						plec = list.get(4);
					}
				}
			}
			if(list.size()>5){
				if(list.get(5).length()>0){
					kategoria = list.get(5);
				}
			}
			if(list.size()>6){
				if(list.get(6).length()>0){
					miejscowosc = list.get(6);
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
					zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team, plec, kategoria, miejscowosc));
					for(int i=startI; i<zawodnicy.size(); i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
				}
				else{
					for(int i=0; i<zawodnicy.size(); i++){
						zawodnicyTmp.add(zawodnicy.get(i));
					}
					zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team, plec, kategoria, miejscowosc));
				}

			}
			else{
				zawodnicyTmp.add(new zawodnik(nr, imie, nazwisko, team, plec, kategoria, miejscowosc));
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
					wynikiTmp.add(new przejazd(zawodnik, czas, nPoz, okrazenie));
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

					wynikiTmp.add(new przejazd(zawodnik, czas, nPoz, okrazenie));
					poz = nPoz;
				}
			}
			else{
				wynikiTmp.add(new przejazd(zawodnik, czas, nPoz, okrazenie));
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
			przejazdy.add(new przejazd(zawodnik, czas, poz, okrazenie));
			
			int czasOkr=czas;
			if(okrazenie>1){
				for(int i=przejazdy.size()-2; i>=0; i--){
					if(przejazdy.get(i).zawodnik.nr==zawodnik.nr){ 
						czasOkr=czas-przejazdy.get(i).czas;
						break;
					}
				}
				
			}
			okrazenia.add(new okrazenie(zawodnik, okrazenie, czasOkr, przejazdy.size()-1));
			System.out.println(okrazenia.get(okrazenia.size()-1).czas);
			
			//return true;
			ArrayList<String> lista = new ArrayList<String>();
			lista.add(Integer.toString(czas));
			lista.add(Integer.toString(zawodnik.nr));
			lista.add(zawodnik.imie);
			lista.add(zawodnik.nazwisko);
			lista.add(zawodnik.team);
			lista.add(zawodnik.plec);
			lista.add(zawodnik.kategoria);
			lista.add(zawodnik.miejscowosc);
			lista.add(Integer.toString(okrazenie));
			lista.add(Integer.toString(poz));
			dodajPrzejazdCSV(lista);
			// gdy zwracala arrayList  - return lista; 
			return true;
		}
		catch(Exception e){
			e.getMessage();
			// gdy zwracala arrayList  - return null 
			return false;
		}
	}
	
	private ArrayList<przejazd> getFiltrWynikiList(String filtrPlec, String filtrKategoria, String filtrMiejscowosc, String filtrTeam){
		ArrayList<przejazd> filtrWyniki = new ArrayList<przejazd>();
		String plec = filtrPlec;
		String kategoria = filtrKategoria;
		String miejscowosc = filtrMiejscowosc;
		String team = filtrTeam;

		for(int i=0; i<wyniki.size(); i++){
			plec = filtrPlec;
			kategoria = filtrKategoria;
			miejscowosc = filtrMiejscowosc;
			team = filtrTeam;
			if(filtrPlec=="a") plec = wyniki.get(i).zawodnik.plec;
			if(filtrKategoria=="a") kategoria = wyniki.get(i).zawodnik.kategoria;
			if(filtrMiejscowosc=="a") miejscowosc = wyniki.get(i).zawodnik.miejscowosc;
			if(filtrTeam=="a") team = wyniki.get(i).zawodnik.team;
			System.out.println("Dane: "+plec+","+kategoria+","+miejscowosc+","+team);
			if(wyniki.get(i).zawodnik.plec==plec) System.out.println("plecOK");
			if(wyniki.get(i).zawodnik.kategoria==kategoria) System.out.println("kategoriaOK");
			if(wyniki.get(i).zawodnik.miejscowosc==miejscowosc) System.out.println("miejscowoscOK");

			if(wyniki.get(i).zawodnik.plec.equals(plec) && wyniki.get(i).zawodnik.kategoria.equals(kategoria) && wyniki.get(i).zawodnik.miejscowosc.equals(miejscowosc) && wyniki.get(i).zawodnik.team.equals(team)){
				System.out.println("kua");
				filtrWyniki.add(wyniki.get(i));
			}
		}
		return filtrWyniki;
	}
	
	/*
	
	private ArrayList<przejazd> getFiltrWynikiList(String filtrPlec){
		ArrayList<przejazd> filtrWyniki = new ArrayList<przejazd>();
		String plec = "m";
		String plec2 = "m";
		

		for(int i=0; i<wyniki.size(); i++){
			//plec = filtrPlec;
			//if(filtrPlec==null) plec = wyniki.get(i).zawodnik.plec;
			//System.out.println(Character.toChars(109));

			if(wyniki.get(i).zawodnik.plec.codePointAt(0)==plec.codePointAt(0)) filtrWyniki.add(wyniki.get(i));
		}
		return filtrWyniki;
	}
	*/
	
	public String[][] getWynikiTable(String filtrPlec, String filtrKategoria, String filtrMiejscowosc, String filtrTeam){
		//ArrayList<przejazd> filtrWyniki = getFiltrWynikiList(filtrPlec, filtrKategoria, filtrMiejscowosc, filtrTeam);
		ArrayList<przejazd> filtrWyniki = getFiltrWynikiList(filtrPlec, filtrKategoria, filtrMiejscowosc, filtrTeam);
		String[][] table = new String[filtrWyniki.size()][10];
		for(int i=0; i<filtrWyniki.size(); i++){
			String pozycja, nr, imie, nazwisko, team, czas, okrazenie, plec, kategoria, miejscowosc;
			nr = Integer.toString(filtrWyniki.get(i).zawodnik.nr);
			imie = filtrWyniki.get(i).zawodnik.imie;
			nazwisko = filtrWyniki.get(i).zawodnik.nazwisko;
			team = filtrWyniki.get(i).zawodnik.team;
			plec = filtrWyniki.get(i).zawodnik.plec;
			kategoria = filtrWyniki.get(i).zawodnik.kategoria;
			miejscowosc = filtrWyniki.get(i).zawodnik.miejscowosc;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			TimeZone zone = TimeZone.getTimeZone("GMT-0");
			format.setTimeZone(zone);
			czas = format.format(filtrWyniki.get(i).czas);
			//format.setTimeZone("GMT-1");
			czas = format.format(filtrWyniki.get(i).czas);
			//czas = Integer.toString(przejazdySort.get(i).czas);
			okrazenie = Integer.toString(filtrWyniki.get(i).okrazenie);
			pozycja = Integer.toString(filtrWyniki.get(i).pozycja);
			table[i] = new String[] {nr, imie, nazwisko, team, plec, kategoria, miejscowosc, czas, okrazenie, pozycja};
		}
		return table;
	}
	
	public String[][] getWynikiTable(){
		String[][] table = new String[wyniki.size()][6];
		for(int i=0; i<wyniki.size(); i++){
			String pozycja, nr, imie, nazwisko, team, czas, okrazenie, plec, kategoria, miejscowosc;
			nr = Integer.toString(wyniki.get(i).zawodnik.nr);
			imie = wyniki.get(i).zawodnik.imie;
			nazwisko = wyniki.get(i).zawodnik.nazwisko;
			team = wyniki.get(i).zawodnik.team;
			plec = wyniki.get(i).zawodnik.plec;
			kategoria = wyniki.get(i).zawodnik.kategoria;
			miejscowosc = wyniki.get(i).zawodnik.miejscowosc;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			TimeZone zone = TimeZone.getTimeZone("GMT-0");
			format.setTimeZone(zone);
			czas = format.format(wyniki.get(i).czas);
			//format.setTimeZone("GMT-1");
			czas = format.format(wyniki.get(i).czas);
			//czas = Integer.toString(przejazdySort.get(i).czas);
			okrazenie = Integer.toString(wyniki.get(i).okrazenie);
			pozycja = Integer.toString(wyniki.get(i).pozycja);
			table[i] = new String[] {nr, imie, nazwisko, team, plec, kategoria, miejscowosc, czas, okrazenie, pozycja};
		}
		return table;
	}
	
	
	String exportWyniki(){
		String html="<HTML>" +
				"<HEAD><meta http-equiv='Content-Type' content='text/html; charset=utf-8'></HEAD>" +
				"<style>" +
				"table{"+
					"border-collapse: collapse;"+
					"border: 1px solid;"+
					"font-family: tahoma;"+
					"font-size: 15;"+
					"text-transform: uppercase;"+
				"}"+
				"table td, th{" +
					"vertical-align: middle;" +
					"padding: 0px 10px;" +
					"border-style: solid none;"+
					"border-width: 1px;"+
				"}"+
				"#przejazdy{" +
					"vertical-align: top;" +
					"font-size: 12;" +
				"}" +
				
				"</style>" +
				"<table><tr><th>poz</th><th>nr</th><th>imie</th><th>nazwisko</th><th>team</th><th>m-czasy</th><th>okr</th><th>czas</th></tr>";
		for(int i=0; i<wyniki.size(); i++){
			html += "<tr>";
			html += "<td>"+Integer.toString(wyniki.get(i).pozycja)+"</td>";
			html += "<td>"+Integer.toString(wyniki.get(i).zawodnik.nr)+"</td>";
			html += "<td>"+wyniki.get(i).zawodnik.imie+"</td>";
			html += "<td>"+wyniki.get(i).zawodnik.nazwisko+"</td>";
			html += "<td>"+wyniki.get(i).zawodnik.team+"</td>";
			html += "<td id='przejazdy'>";
			for(int n=0; n<przejazdy.size(); n++){
				if(przejazdy.get(n).zawodnik.nr==wyniki.get(i).zawodnik.nr){
					if(n==0){
						html += timeToString(przejazdy.get(n).czas);
					}
					else{
						html += "<br>"+timeToString(przejazdy.get(n).czas);
					}
				}
			}
			html += "</td>";
			html += "<td>"+wyniki.get(i).okrazenie+"</td>";
			html += "<td>"+pomiar.getString(wyniki.get(i).czas)+"</td>";
			html += "</tr>";
		}
		html += "</table></head>";
		if(new csv("wyniki__"+pomiar.getDateStart()+".html").write(html)){
			return (String)("wyniki__"+pomiar.getDateStart()+".html");
		}
		return null;
	}
	
	boolean dodajPrzejazdCSV(ArrayList<String> listPrzejazd){
		if(plik.file!=null){
			if(plik.writeLine(listPrzejazd)) return true;
			return false;
		}
		return false;
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
