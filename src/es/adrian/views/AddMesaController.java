package es.adrian.views;

import es.adrian.logic.Logica;
import es.adrian.models.Mesa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMesaController extends BaseController implements Initializable {

    private Mesa mesaModificar;

    @FXML
    private TextField idTf;

    @FXML
    private TextField localizacionTf;

    @FXML
    private TextField capacidadTf;

    @FXML
    private Button botonAceptar;

    @FXML
    void altaModificarMesa(ActionEvent event) {
        if (mesaModificar != null) {
            mesaModificar.setId(Integer.parseInt(idTf.getText()));
            mesaModificar.setLocalizacion(localizacionTf.getText());
            mesaModificar.setCapacidad(Integer.parseInt(capacidadTf.getText()));

            Logica.getInstance().modificarMesa(mesaModificar);
        } else {
            int id = Integer.parseInt(idTf.getText());
            String localizacion = localizacionTf.getText();
            int capacidad = Integer.parseInt(capacidadTf.getText());
            Mesa mesa = new Mesa(id, localizacion, capacidad);
            Logica.getInstance().añadirMesa(mesa);
        }

        getStage().close();
    }

    public void setMesaModificar(Mesa mesa) {
        this.mesaModificar = mesa;
        idTf.setText(String.valueOf(mesa.getId()));
        localizacionTf.setText(mesa.getLocalizacion());
        capacidadTf.setText(String.valueOf(mesa.getCapacidad()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValidationSupport validationSupport = new ValidationSupport();

        validationSupport.registerValidator(idTf, Validator.createRegexValidator("Solo números", "[0-9]+", Severity.ERROR));
        validationSupport.registerValidator(localizacionTf, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        validationSupport.registerValidator(capacidadTf, Validator.createRegexValidator("Solo números", "[0-9]+", Severity.ERROR));

        botonAceptar.disableProperty().bind(validationSupport.invalidProperty());
    }
}
