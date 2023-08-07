module ferrell.softwareproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens ferrell.softwareproject to javafx.fxml;
    exports ferrell.softwareproject;
}