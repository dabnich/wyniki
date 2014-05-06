import org.sqlite.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;


public class zawody {
	static final String USER = "username";
	static final String PASS = "password";
	static final String DB_URL = "jdbc:sqlite:wynik.db";
	private Connection conn;
	private Statement stat;
	ArrayList <zawodnik> zawodnicy = new ArrayList <zawodnik>();
	ArrayList <przejazd> przejazdy = new ArrayList <przejazd>();
	//conn = DriverManager.getConnection(DB_URL,USER,PASS);
	public zawody(){
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch(Exception e){
			e.getMessage();
		
		}
		try {
			conn = DriverManager.getConnection(DB_URL);
			stat = conn.createStatement();

		}
		catch(SQLException e){
			e.getMessage();
			System.out.println("problem z po³¹czeniem");

		}
	}
	
	boolean addZawodnik(int nr, String imie, String nazwisko, String team){
		try{
			zawodnicy.add(new zawodnik(nr, imie, nazwisko, team));
			insertZawodnik(nr, imie, nazwisko, team);
			return true;
		}
		catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	boolean addPrzejazd(zawodnik zawodnik, int czas){
		try{
			//int okrazenie = selectIleOkrazen(zawodnik)+1;
			int okrazenie = getOkrazenia(zawodnik);
			przejazdy.add(new przejazd(zawodnik, czas, okrazenie+1));
			insertPrzejazd(zawodnik.nr, czas, okrazenie+1);
			return true;
		}
		catch(Exception e){
			e.getMessage();
			return false;
		}
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
	
	boolean createTables(){
		
		String createZawodnicy = 
				"CREATE TABLE IF NOT EXISTS zawodnicy ("
				+ "nr int PRIMARY KEY,"
				+ "imie varchar(32) not null,"
				+ "nazwisko varchar(32) not null,"
				+ "team varchar(64) not null)";
		
		String createPrzejazdy = 
				"CREATE TABLE IF NOT EXISTS przejazdy ("
				+ "nr int not null,"
				+ "czas int not null,"
				+ "okrazenie int not null DEFAULT 1,"
				+ "FOREIGN KEY (nr) REFERENCES zawodnicy(nr) )";
		
		try {
			stat.execute(createZawodnicy);
			stat.execute(createPrzejazdy);
			return true;
		}
		catch(SQLException e){
			e.getMessage();
			System.out.println("problem z po³¹czeniem");
			e.printStackTrace();
			return false;
		}
	}
	
	boolean insertZawodnik(int nr, String imie, String nazwisko, String team){
		String InsertZawodnik = "INSTERT INTO 'zawodnicy' VALUES"
				+ " ("+nr+", '"+imie+"', '"+nazwisko+"', '"+team+"')";
		try {
			 PreparedStatement prepStmt = conn.prepareStatement(
					 "insert into zawodnicy values (?, ?, ?, ?);");
	            prepStmt.setInt(1, nr);
	            prepStmt.setString(2, imie);
	            prepStmt.setString(3, nazwisko);
	            prepStmt.setString(4, team);
	            prepStmt.execute();
			
			return true;
		}
		catch(SQLException e){
			e.getMessage();
			System.out.println("b³¹d zapytania");
			e.printStackTrace();
			return false;
		}
	}
	
	boolean insertPrzejazd(int nr, int czas, int okrazenie){
		String InsertPrzejazd = "INSTERT INTO 'przejazdy' VALUES"
				+ " ("+nr+", "+czas+")";
		try {
			 PreparedStatement prepStmt = conn.prepareStatement(
					 "insert into przejazdy values (?, ?, ?);");
			 prepStmt.setInt(1, nr);
			 prepStmt.setInt(2, czas);
			 prepStmt.setInt(3, okrazenie);
			 prepStmt.execute();
			 return true;
		}		
		catch(SQLException e){
			e.getMessage();
			System.out.println("b³¹d zapytania");
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<przejazd> selectPrzejazdy(){
		ArrayList<przejazd> lista = new ArrayList<przejazd>();
		try{
			ResultSet result = stat.executeQuery("Select nr, imie, nazwisko, team, czas, okrazenie FROM zawodnicy, przejazdy WHERE przejazdy.nr=zawodnicy.nr");
			while(result.next()){
				lista.add(new przejazd(new zawodnik(result.getInt("nr"), result.getString("imie"), result.getString("nazwisko"), result.getString("team")), result.getInt("czas"), result.getInt("okrazenie")));
			}
			return lista;
		}
		catch(SQLException e){
			e.getMessage();
			System.out.println("b³¹d zapytania");
			e.printStackTrace();
			return null;
		}
	}
	
	public int selectIleOkrazen(zawodnik zawodnik){
		try{
			ResultSet result = stat.executeQuery("Select okrazenie FROM przejazdy, zawodnicy WHERE przejazdy.nr=zawodnicy.nr AND zawodnicy.nr="+zawodnik.nr);
			return result.getInt("okrazenie");
		}
		catch(SQLException e){
			e.getMessage();
			System.out.println("b³¹d zapytaniakurwakruwa");
			e.printStackTrace();
			return 0;
		}

	}
	
	public ArrayList<zawodnik> selectZawodnicy(){
		ArrayList<zawodnik> lista = new ArrayList<zawodnik>();
		String select = "Select * FROM zawodnicy";
		try{
			ResultSet result = stat.executeQuery("Select * FROM zawodnicy");
			while(result.next()){
				lista.add(new zawodnik(result.getInt("nr"), result.getString("imie"), result.getString("nazwisko"), result.getString("team")));
			}
			return lista;
		}
		catch(SQLException e){
			e.getMessage();
			System.out.println("b³¹d zapytania");
			e.printStackTrace();
			return null;
		}
	}
	
}
