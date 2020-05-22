package es.adrian.views;

import es.adrian.logic.Logica;
import es.adrian.models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogoPedidoController extends BaseController implements Initializable {

    private Ticket ticketAux;

    private Producto pedirProducto;

    @FXML
    private TableView<Producto> tableViewProductos;

    @FXML
    private TableView<Consumicion> tableViewPedidos;

    @FXML
    private TextField productoTf;

    @FXML
    private TextField cantidadTf;

    @FXML
    private TextField totalTf;

    @FXML
    private Button botonPedir;

    @FXML
    private Button botonQuitar;

    @FXML
    void pedirProducto(ActionEvent event) {
        int cantidad = Integer.parseInt(cantidadTf.getText());
        Consumicion consumicion = new Consumicion(pedirProducto, cantidad);

        boolean add = true;

        for(Consumicion c: ticketAux.getListaConsumiciones()) {
            if(c.getProducto().getNombreProducto().equals(pedirProducto.getNombreProducto())) {
                c.setCantidad(c.getCantidad() + cantidad);
                add = false;
            }
        }

        if(add) {
            ticketAux.getListaConsumiciones().add(consumicion);
        }

        Logica.getInstance().getListaConsumiciones().clear();
        Logica.getInstance().getListaConsumiciones().addAll(ticketAux.getListaConsumiciones());

        String totalTicket = Logica.getInstance().calcularTotalTicket(ticketAux);
        totalTf.setText(totalTicket);

        cantidadTf.setText(null);
    }

    @FXML
    void quitarProducto(ActionEvent event) {
        int indice = tableViewPedidos.getSelectionModel().getSelectedIndex();
        Logica.getInstance().borrarConsumicion(indice);
        ticketAux.getListaConsumiciones().remove(indice);
        tableViewPedidos.getSelectionModel().clearSelection();
        botonQuitar.setDisable(true);

        String totalTicket = Logica.getInstance().calcularTotalTicket(ticketAux);
        totalTf.setText(totalTicket);
    }

    public void setTicket(Ticket ticket) {
        this.ticketAux = ticket;
        Logica.getInstance().getListaConsumiciones().clear();
        Logica.getInstance().getListaConsumiciones().addAll(ticketAux.getListaConsumiciones());
        tableViewPedidos.setItems(Logica.getInstance().getListaConsumiciones());

        String totalTicket = Logica.getInstance().calcularTotalTicket(ticketAux);
        totalTf.setText(totalTicket);
    }

    @FXML
    public void pagarTicket() {
        ticketAux.setEstado(Estado.CERRADO);
        getStage().close();
    }

    @FXML
    public void salirTicket() {
        getStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productoTf.setEditable(false);
        totalTf.setEditable(false);

        tableViewProductos.setItems(Logica.getInstance().getListaProductos());

        botonQuitar.setDisable(true);

        tableViewProductos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observableValue, Producto producto, Producto productoSelect) {
                pedirProducto = productoSelect;
                productoTf.setText(productoSelect.getNombreProducto());

            }
        });

        tableViewPedidos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Consumicion>() {
            @Override
            public void changed(ObservableValue<? extends Consumicion> observableValue, Consumicion consumicion, Consumicion pedidoSelect) {
                if(tableViewPedidos.getSelectionModel().getSelectedItem() != null) {
                    botonQuitar.setDisable(false);
                }

            }
        });

        ValidationSupport validationSupport = new ValidationSupport();

        validationSupport.registerValidator(productoTf, Validator.createEmptyValidator("Este campo no puede estar vacío", Severity.ERROR));
        validationSupport.registerValidator(cantidadTf, Validator.createRegexValidator("Solo números", "[0-9]+", Severity.ERROR));

        botonPedir.disableProperty().bind(validationSupport.invalidProperty());

    }

}
