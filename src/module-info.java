module Proyecto {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires controlsfx;
    requires org.jfxtras.styles.jmetro;

    exports es.adrian;
    exports es.adrian.logic;
    exports es.adrian.utils;
    exports es.adrian.models;
    exports es.adrian.views;

    opens es.adrian.views to javafx.fxml;
    opens es.adrian;
}