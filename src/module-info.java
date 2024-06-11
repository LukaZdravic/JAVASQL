module POOPPROJECT {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;


    opens mainFrame to javafx.fxml;
    exports mainFrame;
    exports mainPackage;
}