module Proyecto {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires controlsfx;

    exports es.adrian;
    exports es.adrian.logic;
    exports es.adrian.models;
    exports es.adrian.views;

    opens es.adrian.views to javafx.fxml;
}