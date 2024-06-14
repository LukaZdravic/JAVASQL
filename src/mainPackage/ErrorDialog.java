package mainPackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorDialog extends Stage{
	
	private String message;
	public ErrorDialog(String m) {
		this.message = m;
		setTitle("Error!");
		setHeight(100);
		setWidth(300);
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setOnCloseRequest((ae)->{
			close();
		});
		
		Label l = new Label(m);
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(l);
		
		layout.setAlignment(Pos.CENTER);
		Scene scena = new Scene(layout);
		
		setScene(scena);
	
		show();
		
	}

}
