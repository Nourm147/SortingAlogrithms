module com.sorting {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires lombok;
    requires java.desktop;
    requires javafx.base;
    opens com.sorting to javafx.fxml;
    exports com.sorting;
}
