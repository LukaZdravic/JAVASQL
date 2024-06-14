package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import mainFrame.ApplicationFrame;


/**
 * Main klasa ima potrebne metode za export,inport kao i otvaranje fajla!
 */
public class MainClass {
	
	
	 /**
     * Eksportuje bazu podataka po custom formatu i to u fajl pod nazivom path
     *
     * @param path putanja do fajla gde zelimo da eksportujemo bazu
     */
	public static void exportCustom(String path) {
		BufferedWriter writer = getWriter(path);
		if (writer == null) return;
		try {
			for (Table t : ApplicationFrame.dataBase) {
				writer.write(t.getTableName()+";");
				ArrayList<String> kolone = t.getKolone();
				for (int j=0;j<kolone.size();j++) {
					writer.write(kolone.get(j));
					if (j<kolone.size()-1) writer.write(',');
				}
				writer.write(";\n");
				
				ArrayList<ArrayList<String>> records = t.getRekordi();
				
				for (int i =0;i<records.size();i++) {
					
					writer.write(t.getTableName()+";");
					kolone = t.getKolone();
					for (int j=0;j<kolone.size();j++) {
						writer.write(kolone.get(j));
						if (j<kolone.size()-1) writer.write(',');
					}
					writer.write(';');
					
					
					ArrayList<String> r = records.get(i);
					for (int j=0;j<r.size();j++) {
						writer.write(r.get(j));
						if (j<r.size()-1) writer.write(',');
					}
					writer.write('\n');
				}
				
				
			}
			writer.close();
		}catch (IOException e) {}
		
		
	}
	
	/**
     * Eksportuje bazu podataka po SQL formatu i to u fajl pod nazivom path
     *
     * @param path putanja do fajla gde zelimo da eksportujemo bazu
     */
	public static void exportSQL(String path) {
		BufferedWriter writer = getWriter(path);
		if (writer == null) return;
		try {
			for (Table t : ApplicationFrame.dataBase) {
				writer.write("CREATE TABLE "+t.getTableName()+" ( ");
				ArrayList<String> kolone = t.getKolone();
				for (int j=0;j<kolone.size();j++) {
					writer.write(kolone.get(j));
					if (j<kolone.size()-1) writer.write(',');
				}
				writer.write(" )\n");
				
				ArrayList<ArrayList<String>> records = t.getRekordi();
				
				for (int i =0;i<records.size();i++) {
					writer.write("INSERT INTO "+t.getTableName()+" ( ");
					kolone = t.getKolone();
					for (int j=0;j<kolone.size();j++) {
						writer.write(kolone.get(j));
						if (j<kolone.size()-1) writer.write(',');
					}
					writer.write(" ) VALUES ( ");
					
					ArrayList<String> r = records.get(i);
					for (int j=0;j<r.size();j++) {
						writer.write(r.get(j));
						if (j<r.size()-1) writer.write(',');
					}
					writer.write(" )\n");
				}
				
				
			}
			writer.close();
		}catch (IOException e) {}
		
		
	}
	
	/**
     * Ucitava bazu podataka po SQL formatu i to sa fajla pod nazivom path
     *
     * @param path putanja do fajla odakle zelimo da ucitamo bazu
     */
	public static void ucitajSQL(String path) {
		BufferedReader reader = getReader(path);
		if (reader==null) return;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(" ");
			    if (parts.length==6) {
			    	String[] koloneTMP = parts[4].split(",");
			    	ArrayList<String> kolone = new ArrayList<String>(Arrays.asList(koloneTMP));
			    	ApplicationFrame.dataBase.add(new Table(parts[2],kolone));

			    }
			    else if (parts.length>6){
			    	for (int i = 0;i<ApplicationFrame.dataBase.size();i++) {
			    		if (ApplicationFrame.dataBase.get(i).getTableName().equals(parts[2])) {
			    			
			    			String[] vrednostiTMP = parts[8].split(",");
			    			ArrayList<String> vrednosti = new ArrayList<String>(Arrays.asList(vrednostiTMP));
			    			ApplicationFrame.dataBase.get(i).addToRecord(vrednosti);
			    		}
			    	}
			    }
			    
			    
			}
			reader.close();
		} catch (IOException e) {}
		
		System.out.println("FINISHED LOADING!");
	}
	
	/**
     * Ucitava bazu podataka po custom formatu i to sa fajla pod nazivom path
     *
     * @param path putanja do fajla odakle zelimo da ucitamo bazu
     */
	public static void ucitajCustom(String path) {
		BufferedReader reader = getReader(path);
		if (reader==null) return;
        String line;
		try {
			while ((line = reader.readLine()) != null) {
			    //System.out.println(line);
			    String[] parts = line.split(";");
			    if (parts.length==2) {
			    	String[] koloneTMP = parts[1].split(",");
			    	ArrayList<String> kolone = new ArrayList<String>(Arrays.asList(koloneTMP));
			    	ApplicationFrame.dataBase.add(new Table(parts[0],kolone));

			    }
			    else {
			    	for (int i = 0;i<ApplicationFrame.dataBase.size();i++) {
			    		if (ApplicationFrame.dataBase.get(i).getTableName().equals(parts[0])) {
			    			
			    			String[] vrednostiTMP = parts[2].split(",");
			    			ArrayList<String> vrednosti = new ArrayList<String>(Arrays.asList(vrednostiTMP));
			    			ApplicationFrame.dataBase.get(i).addToRecord(vrednosti);
			    		}
			    	}
			    }
			    
			    
			}
			reader.close();
		} catch (IOException e) {}
		System.out.println("FINISHED LOADING!");
	}
	
	 /**
     * Vraca podatak tipa BufferedReader koji moze da se koristi za citanje iz fajla sa putanje path, ili null ako se javi neka greska prilikom otvaranja
     *
     * @param filePath putanja do fajla koji zelimo da citamo
     * @return BufferedReader podatak ili null
     */
	public static BufferedReader getReader(String filePath) {
		BufferedReader reader = null;
		try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            new ErrorDialog("Ne postoji fajl koji zelite da otvorite!");
        }
		return reader;
	}
	/**
     * Vraca podatak tipa BufferedWriter koji moze da se koristi za pisanje u fajl sa putanje path, ili null ako se javi neka greska prilikom otvaranja
     *
     * @param filePath putanja do fajla u koji zelimo da upisemo
     * @return BufferedWriter podatak ili null
     */
	public static BufferedWriter getWriter(String filePath) {
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch (FileNotFoundException e) {
            new ErrorDialog("Ne postoji fajl koji zelite da otvorite!");
        } catch (IOException e) {}
		return writer;
	}
}
