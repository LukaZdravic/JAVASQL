package mainPackage;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainFrame.ApplicationFrame;
import mainPackage.MainClass;
public class LoadingDialog extends Stage{
	public LoadingDialog() {
		setTitle("Izaberite opciju!");
		setHeight(200);
		setWidth(300);
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		
		setOnCloseRequest((ae)->{
			close();
			
		});
		Label l = new Label("Koji format zelite da ucitate: \n");
		
		Button sqlFormat = new Button("SQL Format!");
		Button customFormat = new Button("Custom Format!");
		
		TextField tf = new TextField();
		tf.setPromptText("Unesite naziv fajla(punu putanju do fajla)...");
	
		sqlFormat.setOnAction((ae)->{
			MainClass.ucitajSQL(tf.getText());
			//close();
		});
		
		customFormat.setOnAction((ae)->{
			MainClass.ucitajCustom(tf.getText());
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
