package mainFrame;

import javafx.application.Application;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainPackage.LoadingDialog;
import mainPackage.Table;
import mainPackage.WelcomeDialog;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ApplicationFrame extends Application {
    
	public static List<Table> dataBase = new ArrayList<Table>();
	
	private void populateWindow(BorderPane root) {
        TableView<Table> tableView = new TableView<>();
        TableView<Table> resultTableView = new TableView<>();
        TextArea sqlInputArea;

        TableColumn<Table, String> tableNameColumn = new TableColumn<>("Naziv tabele");
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("nazivTabele"));
        tableView.getColumns().add(tableNameColumn);

        //List<TableInfo> tableList = getDatabaseTables();
        //tableView.getItems().addAll(tableList);
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tableView);
        root.setLeft(leftPane);

        // Add SQL input area and execution button
        sqlInputArea = new TextArea();
        sqlInputArea.setPromptText("Unesite upit...");

        Button executeButton = new Button("Run!");
        //executeButton.setOnAction(e -> executeSQLStatement());

        VBox centerPane = new VBox();
        centerPane.getChildren().addAll(sqlInputArea, executeButton);
        root.setBottom(centerPane);

        // Add result display area
        resultTableView = new TableView<>();
        root.setRight(resultTableView);

       
    }
	
	@Override
    public void start(Stage primaryStage) throws IOException {
    	try {
    		
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,500,500);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			
			new WelcomeDialog();
			populateWindow(root);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	


    public static void main(String[] args) {
        launch();
    }
}