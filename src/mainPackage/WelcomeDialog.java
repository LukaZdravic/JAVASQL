package mainPackage;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainFrame.ApplicationFrame;
import mainFrame.ApplicationFrame.*;


public class WelcomeDialog extends Stage{
		
	public WelcomeDialog(Runnable r) {
		setTitle("Dobro dosli!");
		setHeight(200);
		setWidth(300);
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		
		setOnCloseRequest((ae)->{
			if(r!=null) {
				Platform.runLater(r);
			}
			close();
			
		});
		Label l = new Label("Da li zelite da bazu: \n");
		
		Button kreirajNovu = new Button("Kreirate novu!");
		Button ucitajPostojecu = new Button("Ucitate postojecu!");
				
		
		kreirajNovu.setOnAction((ae)->{
			System.out.println("Click kreiraj");
			ApplicationFrame.dataBase.clear();
			if(r!=null) {
				Platform.runLater(r);
			}
			close();
		});
		
		ucitajPostojecu.setOnAction((ae)->{
			System.out.println("Click postojeca");
			new LoadingDialog();
			//close();
		});
		
		HBox buttonBox = new HBox(10);
		
		buttonBox.getChildren().addAll(kreirajNovu,ucitajPostojecu);
		buttonBox.setAlignment(Pos.CENTER);
		
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(l,buttonBox);
		
		layout.setAlignment(Pos.CENTER);
		
		Scene scena = new Scene(layout);
		
		setScene(scena);
	
		show();
	}
	
}
