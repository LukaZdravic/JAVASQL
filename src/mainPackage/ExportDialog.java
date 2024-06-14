package mainPackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExportDialog extends Stage{
	
	
	public ExportDialog() {
		setTitle("Izaberite opciju!");
		setHeight(200);
		setWidth(300);
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		
		setOnCloseRequest((ae)->{
			close();
			
		});
		
		Label l = new Label("Po kom formatu zelite da sacuvate: \n");
		
		Button sqlFormat = new Button("SQL Format!");
		Button customFormat = new Button("Custom Format!");
		
		TextField tf = new TextField();
		tf.setPromptText("Unesite naziv fajla(punu putanju do fajla)...");
		
		
		sqlFormat.setOnAction((ae)->{
			MainClass.exportSQL(tf.getText());
			//close();
		});
		
		customFormat.setOnAction((ae)->{
			MainClass.exportCustom(tf.getText());
			//C:\Users\lukaz\Desktop
			//close();
		});
		
		
		HBox buttonBox = new HBox(10);
		
		buttonBox.getChildren().addAll(sqlFormat,customFormat);
		buttonBox.setAlignment(Pos.CENTER);
		
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(l,buttonBox,tf);
		
		layout.setAlignment(Pos.CENTER);
		
		Scene scena = new Scene(layout);
		
		setScene(scena);
	
		show();
		
		
		
	}

}
