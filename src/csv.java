import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class csv {
	String file;
	
	public csv(String file){
		this.file = file;
	}
	
	String[] getToArray() {
		File f = new File(file);
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(f));
		} 
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		
		try {
			ArrayList<String> lista = new ArrayList<String>();
			
			String linia = "1";
			while(linia!=null) {
				linia = reader.readLine();
				lista.add(linia);
			}
			lista.remove(lista.size()-1);
			String[] tab = new String[lista.size()];
			for(int i=0; i<lista.size(); i++){
				tab[i] = lista.get(i);
			}
			return tab;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	
	ArrayList<ArrayList<String>> getToList() {
		File f = new File(file);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		
		try {
			ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
			
			String linia = "1";
			
			while(true) {
				linia = reader.readLine();
				if(linia==null)break;
				String[] elementyTab = linia.split(",");
				ArrayList<String> elementyList = new ArrayList<String>();
				for(int i=0; i<elementyTab.length; i++){
					elementyList.add(elementyTab[i]);
				}
				lista.add(elementyList);
			}
			reader.close();
			return lista;
			
			//String[] tab = new String[lista.size()];
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	boolean writeLine(ArrayList<String> lista){
		File f = new File(file);
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
			//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f));
			

			for(int i=0; i<lista.size(); i++){
				
				if(i<lista.size()-1)
					writer.write(lista.get(i)+",");
				else
					writer.write(lista.get(i));
				//writer.newLine();
			}
			writer.newLine();
			writer.close();
			System.out.println("ok");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
}
