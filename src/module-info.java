module POOPPROJECT {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;


    opens mainFrame to javafx.fxml;
    exports mainFrame;
    exports mainPackage;
}