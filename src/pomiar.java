import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class pomiar {
	public static int nieAktywny = 0;
	public static int zastopowany = 1;
	public static int aktywny = 2;
	int status=nieAktywny;
	long startTime;
	private long wartosc = 0;
	
	int getInt(){
		if(status==nieAktywny){
			return (int)(0);
		}
		if(status==zastopowany){
			return (int) wartosc;
		}
		return (int)(new Date().getTime()-startTime+wartosc);
	}
	
	public String getString(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		TimeZone zone = TimeZone.getTimeZone("GMT-0");
		format.setTimeZone(zone);
		if(status==nieAktywny){
			return format.format(0);
		}
		if(status==zastopowany){
			return format.format(wartosc);
		}
		return format.format(new Date().getTime()-startTime+wartosc);
	}
	
	public String getString(int czas){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		TimeZone zone = TimeZone.getTimeZone("GMT-0");
		format.setTimeZone(zone);
		return format.format(czas);
	}
	
	public String getDateStart(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		TimeZone zone = TimeZone.getTimeZone("GMT+2");
		format.setTimeZone(zone);
		return format.format(new Date().getTime());
	}

	public void startuj(){
		if(status != aktywny){
			status = aktywny;
			startTime = new Date().getTime();
		}
	}
	
	public void zatrzymaj(){
		if(status != zastopowany){
			status = zastopowany;
			wartosc = new Date().getTime()-startTime+wartosc;
			startTime = new Date().getTime();
		}
	}
	
	public void resetuj(){
		status = nieAktywny;
		wartosc = 0;
	}
	
}
