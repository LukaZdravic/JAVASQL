package mainPackage;

import java.util.ArrayList;

public class Table {
	private ArrayList<ArrayList<String>> rekordi = new ArrayList<ArrayList<String>>();
	private ArrayList<String> kolone = new ArrayList<String>();
	private String tableName;
	public Table(String tableName,ArrayList<String> kolone) {
		this.tableName = tableName;
		this.kolone = kolone;
	}
	int getKoloneSize() {
		return kolone.size();
	}
	
	
}
