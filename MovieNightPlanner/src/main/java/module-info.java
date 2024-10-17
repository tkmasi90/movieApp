module fi.tuni.swdesign.movienightplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.base;
    
    opens fi.tuni.swdesign.movienightplanner.controllers to javafx.fxml;
    opens fi.tuni.swdesign.movienightplanner.models to com.google.gson;

    exports fi.tuni.swdesign.movienightplanner;
    exports fi.tuni.swdesign.movienightplanner.controllers;
    exports fi.tuni.swdesign.movienightplanner.models;
    exports fi.tuni.swdesign.movienightplanner.utilities;
}
