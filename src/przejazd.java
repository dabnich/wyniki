import java.util.ArrayList;


public class przejazd {
	zawodnik zawodnik;
	int czas;
	int okrazenie=0;
	int pozycja;
	public przejazd(zawodnik zawodnik, int czas, int pozycja, int okrazenie) {
		super();
		this.zawodnik = zawodnik;
		this.czas = czas;
		this.okrazenie = okrazenie;
		this.pozycja = pozycja;
	}
	
	public przejazd(zawodnik zawodnik, int czas){
		this.zawodnik = zawodnik;
		this.czas = czas;
	}
	
	void ustawOkrazenie(int okrazenie){
		this.okrazenie = okrazenie;
	}
	
	void ustawPozycje(int pozycja){
		this.pozycja = pozycja;
	}
	
}
