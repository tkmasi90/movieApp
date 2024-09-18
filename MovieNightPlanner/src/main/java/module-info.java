module fi.tuni.swdesign.movienightplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    opens fi.tuni.swdesign.movienightplanner to javafx.fxml;
    exports fi.tuni.swdesign.movienightplanner;
}
