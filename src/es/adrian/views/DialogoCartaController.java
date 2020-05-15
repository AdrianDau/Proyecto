package es.adrian.views;

import es.adrian.logic.Logica;
import es.adrian.models.Categoria;
import es.adrian.models.Producto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogoCartaController extends BaseController implements Initializable {

    private Producto productoModificar;

    @FXML
    private TableView<Producto> listaProductos;

    @FXML
    private ComboBox<Categoria> categoriasCb;

    @FXML
    private TextField productoTf;

    @FXML
    private TextField precioTf;

    @FXML
    private Button botonAñadir;

    @FXML
    private Button botonModificar;

    @FXML
    private Button botonBorrar;

    @FXML
    void addModificarProducto(ActionEvent event) {
        if (productoModificar != null) {
            productoModificar.setNombreProducto(productoTf.getText());
            productoModificar.setPrecio(Double.parseDouble(precioTf.getText()));
            productoModificar.setCategoria(categoriasCb.getValue());

            Logica.getInstance().modificarProducto(productoModificar);

            productoModificar = null;

            productoTf.setText(null);
            precioTf.setText(null);
            categoriasCb.setValue(null);
        }
        else {
            String nombre = productoTf.getText();
            double precio = Double.parseDouble(precioTf.getText());
            Categoria categoria = categoriasCb.getValue();
            Producto producto = new Producto(nombre, precio, categoria);

            Logica.getInstance().añadirProducto(producto);

            productoTf.setText(null);
            precioTf.setText(null);
            categoriasCb.setValue(null);
        }
    }

    @FXML
    void modificarProducto(ActionEvent event) {
        productoModificar = listaProductos.getSelectionModel().getSelectedItem();
        setProductoModificar(productoModificar);

        botonModificar.setDisable(true);
        botonBorrar.setDisable(true);
        listaProductos.getSelectionModel().clearSelection();
    }

    @FXML
    void borrarProducto(ActionEvent event) {
        int indice = listaProductos.getSelectionModel().getSelectedIndex();
        Logica.getInstance().borrarProducto(indice);

        botonBorrar.setDisable(true);
        botonModificar.setDisable(true);
        listaProductos.getSelectionModel().clearSelection();
    }

    public void setProductoModificar(Producto producto) {
        productoTf.setText(producto.getNombreProducto());
        precioTf.setText(String.valueOf(producto.getPrecio()));
        categoriasCb.setValue(producto.getCategoria());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getStage().setMinWidth(800);
        getStage().setMaxHeight(800);
        listaProductos.setItems(Logica.getInstance().getListaProductos());

        categoriasCb.getItems().setAll(Categoria.values());

        botonBorrar.setDisable(true);
        botonModificar.setDisable(true);

        listaProductos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observableValue, Producto producto, Producto productoSelect) {
                if(productoSelect != null) {
                    botonBorrar.setDisable(false);
                    botonModificar.setDisable(false);
                }
            }
        });

        ValidationSupport validationSupport = new ValidationSupport();

//        validationSupport.registerValidator(productoTf, Validator.createEmptyValidator("Este campo no puede estar vacío", Severity.ERROR));
//        validationSupport.registerValidator(precioTf, Validator.createEmptyValidator("Este campo no puede estar vacío"));
//        validationSupport.registerValidator(precioTf, Validator.createRegexValidator("Formato: números.números", "\\d+\\.\\d+", Severity.ERROR));
//        validationSupport.registerValidator(categoriasCb, Validator.createEmptyValidator("Este campo no puede estar vacío"));

        botonAñadir.disableProperty().bind(validationSupport.invalidProperty());
    }
}
