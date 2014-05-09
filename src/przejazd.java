
public class przejazd {
	zawodnik zawodnik;
	int czas;
	int okrazenie=0;
	int pozycja;
	public przejazd(zawodnik zawodnik, int czas, int okrazenie, int pozycja) {
		super();
		this.zawodnik = zawodnik;
		this.czas = czas;
		this.okrazenie = okrazenie;
		this.pozycja = pozycja;
	}
	void ustawPozycje(int pozycja){
		this.pozycja = pozycja;
	}
	
}
