package es.adrian;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import es.adrian.views.BaseController;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/adrian/views/DialogoMesa.fxml"));
        Parent root = fxmlLoader.load();
        BaseController baseController = fxmlLoader.getController();
        stage.setTitle("Inicio");
        stage.setScene(new Scene(root));
        baseController.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
