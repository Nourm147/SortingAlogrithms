module com.sorting {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.sorting to javafx.fxml;
    exports com.sorting;
}