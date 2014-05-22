
public class zawodnik {
	public static int mezczyzna;
	public static int kobieta;
	public int nr;
	public String imie;
	public String nazwisko;
	public String team;
	public String kategoria;
	public String plec;
	public String miejscowosc;
	public zawodnik(int nr, String imie, String nazwisko, String team) {
		super();
		this.nr = nr;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.team = team;
		plec = "m";
		kategoria = "elita";
		miejscowosc = null;
	}
	
	public zawodnik(int nr, String imie, String nazwisko, String team, String plec, String kategoria, String miejscowosc) {
		super();
		this.nr = nr;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.team = team;
		this.plec = plec;
		this.kategoria = kategoria;
		this.miejscowosc = miejscowosc;
	}
	
}
