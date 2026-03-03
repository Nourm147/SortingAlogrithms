module com.sorting {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires lombok;
    opens com.sorting to javafx.fxml;
    exports com.sorting;
}
