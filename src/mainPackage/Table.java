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
	public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<String> getKolone() {
        return kolone;
    }
    
    public ArrayList<ArrayList<String>> getRekordi(){
    	return rekordi;
    }

    public void setKolone(ArrayList<String> kolone) {
        this.kolone = kolone;
    }

    public int getKoloneSize() {
        return kolone.size();
    }
    
    public void addToRecord(ArrayList<String> rec) {
    	rekordi.add(rec);
    }

	
}
