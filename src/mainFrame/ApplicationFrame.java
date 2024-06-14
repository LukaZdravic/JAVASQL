package mainFrame;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainPackage.ExportDialog;
import mainPackage.LoadingDialog;
import mainPackage.MainClass;
import mainPackage.NativeClass;
import mainPackage.Table;
import mainPackage.WelcomeDialog;
import javafx.beans.property.SimpleStringProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationFrame extends Application {
    
	public static List<Table> dataBase = new ArrayList<Table>();
	
	public int dirty = 0;
	
	
	private void populateWindow(BorderPane root) {
        TableView<Table> tableView = new TableView<>();
        
        TableView<ObservableList<String>> resultTableView = new TableView<>();
        
        TextArea sqlInputArea;
 
        
        
        
        TableColumn<Table, String> tableNameColumn = new TableColumn<>("Naziv tabele");
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        TableColumn<Table, String> tableColumns = new TableColumn<>("Kolone");
        tableColumns.setCellValueFactory(new PropertyValueFactory<>("kolone"));
        tableView.getColumns().add(tableNameColumn);
        tableView.getColumns().add(tableColumns);
        
        
        tableView.getItems().addAll(dataBase);
        
        
        
        
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tableView);
        root.setLeft(leftPane);

       
        sqlInputArea = new TextArea();
        sqlInputArea.setPromptText("Unesite upit...");

        Button executeButton = new Button("Run!");
        
        Button deleteButton = new Button();
        
        File file = new File("ximg.png");
        Image image = new Image(file.toURI().toString());
        
        
        ImageView imageView = new ImageView(image);
        
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        
        // Set the graphic of the button to the ImageView
        deleteButton.setGraphic(imageView);
        
        Button exportButton = new Button("Export!");
        
        VBox centerPane = new VBox();
        HBox hb = new HBox();
        
        final Pane spacer = new Pane();
        spacer.setMinSize(185, 1);
        
        final Pane spacer2 = new Pane();
        spacer2.setMinSize(185, 1);
        
        hb.getChildren().addAll(executeButton,spacer,deleteButton,spacer2,exportButton);
        centerPane.getChildren().addAll(sqlInputArea,hb);
        root.setBottom(centerPane);


 
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                resultTableView.getItems().clear();
                resultTableView.getColumns().clear();
                
                ArrayList<String> kolone = newSelection.getKolone();
                for (int i =0;i<kolone.size();i++) {
                	final int columnIndex = i;
                	TableColumn<ObservableList<String>, String> tc = new TableColumn<>(kolone.get(i));
                	
                	tc.setCellValueFactory(cellData -> {
                        ObservableList<String> row = cellData.getValue();
                        return new SimpleStringProperty(row.size() > columnIndex ? row.get(columnIndex) : "");
                    });
                	
                	resultTableView.getColumns().add(tc);
                }
                ArrayList<ArrayList<String>> rekords = newSelection.getRekordi();
                for (ArrayList<String> record : rekords) {
                    ObservableList<String> row = FXCollections.observableArrayList(record);

                    resultTableView.getItems().add(row);
                }
       
                
        
                
            }
        });

        executeButton.setOnAction((ae)->{
        	System.out.println("Execute!");
        	if (dirty==0) dirty=1; 
			MainClass.exportCustom("temporaryDB.txt");
			//System.out.println(sqlInputArea.getText());
			
            
            
            String tmp = "";
			new NativeClass().izvrsiUpit(sqlInputArea.getText().trim());
			// procitaj iz temporaryANSWER.txt fajla
			resultTableView.getItems().clear();
            resultTableView.getColumns().clear();
			BufferedReader reader = MainClass.getReader("temporaryANSWER.txt");
			if (reader==null) {System.out.println("FAIL!!");return;}
			try {
				tmp = reader.readLine();
				if(tmp==null) tmp="";
				String [] k = tmp.split(";");
				String[] kol = k[0].split(",");
				ArrayList<String> kolone = new ArrayList<String>(Arrays.asList(kol));
				for(int i =0;i<kolone.size();i++) {
					final int columnIndex = i;
                	TableColumn<ObservableList<String>, String> tc = new TableColumn<>(kolone.get(i));
                	
                	tc.setCellValueFactory(cellData -> {
                        ObservableList<String> row = cellData.getValue();
                        return new SimpleStringProperty(row.size() > columnIndex ? row.get(columnIndex) : "");
                    });
                	
                	resultTableView.getColumns().add(tc);
				}
				
				
				for (int i=1;i<k.length;i++) {
					String[] val = k[i].split(",");
					ArrayList<String> values = new ArrayList<String>(Arrays.asList(val));
					ObservableList<String> row = FXCollections.observableArrayList(values);

                    resultTableView.getItems().add(row);
					
				}
				reader.close();
			
				if(tmp.equals("")) {
					reader = MainClass.getReader("temporaryCHANGE.txt");
					if (reader==null) {System.out.println("FAIL!!");return;}
					tmp = reader.readLine();
					TableColumn<ObservableList<String>, String> tc = new TableColumn<>("Izmenjeno: ");
					
					tc.setCellValueFactory(cellData -> {
                        ObservableList<String> row = cellData.getValue();
                        return new SimpleStringProperty(row.size() > 0 ? row.get(0) : "");
                    });
					resultTableView.getColumns().clear();
					resultTableView.getColumns().add(tc);
					
					String g = "Promenjeno je " + tmp + " redova informacija";
					ArrayList<String> values = new ArrayList<String>();
					values.add(g);
					ObservableList<String> row = FXCollections.observableArrayList(values);
					resultTableView.getItems().clear();
                    resultTableView.getItems().add(row);
				}
			} catch (IOException e) {}
			dataBase.clear();
			
			MainClass.ucitajCustom("temporaryDB.txt");
			
			tableView.getItems().clear();
			tableView.getItems().addAll(dataBase);
			
			resultTableView.refresh();
			
			tableView.refresh();
        });
        
        deleteButton.setOnAction((ae)->{
			System.out.println("Delete!");
			Table selectedRow = tableView.getSelectionModel().getSelectedItem();
			tableView.getItems().remove(selectedRow);
			dataBase.remove(selectedRow);
			resultTableView.getItems().clear();
            resultTableView.getColumns().clear();
			tableView.refresh();
			tableView.getSelectionModel().clearSelection();
		});
        
        exportButton.setOnAction((ae)->{
			System.out.println("Export!");
			new ExportDialog();
			
		});
        
        
        root.setRight(resultTableView);

       
    }
	
	@Override
    public void start(Stage primaryStage) throws IOException {
    	try {
    		
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,500,500);
			
			primaryStage.setTitle("SQL");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest((ae)->{
				if(dirty!=0) new ExportDialog();
				primaryStage.close();
				
			});
			
			new WelcomeDialog(() -> {
	            populateWindow(root) ;
	        });
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	


    public static void main(String[] args) {
        launch();
    }
}