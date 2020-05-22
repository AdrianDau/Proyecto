package es.adrian.views;

import es.adrian.logic.Logica;
import es.adrian.models.Estado;
import es.adrian.models.Mesa;
import es.adrian.models.Ticket;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import jfxtras.styles.jmetro.Style;
import jfxtras.styles.jmetro.JMetro;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogoMesaController extends BaseController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private TableView<Mesa> tableViewMesas;

    @FXML
    private Button botonAbrirTicket;

    @FXML
    private Button botonVerTickets;

    @FXML
    private Button botonModificar;

    @FXML
    private Button botonBorrar;

    @FXML
    private ComboBox<Style> estilosCb;

    @FXML
    void addMesa(ActionEvent event) {
        BaseController bc = cargarDialogo("AddMesa.fxml");
        bc.mostrarDialogo(true);
    }

    @FXML
    void modificarMesa(ActionEvent event) {
        AddMesaController amController = (AddMesaController) cargarDialogo("AddMesa.fxml");
        Mesa mesa = tableViewMesas.getSelectionModel().getSelectedItem();
        amController.setMesaModificar(mesa);
        amController.mostrarDialogo(true);
    }

    @FXML
    void borrarMesa(ActionEvent event) {
        int indice = tableViewMesas.getSelectionModel().getSelectedIndex();
        Logica.getInstance().borrarMesa(indice);
    }

    @FXML
    public void verCarta(ActionEvent event) {
        BaseController controller = cargarDialogo("DialogoCarta.fxml");
        controller.getStage().setMinHeight(450);
        controller.getStage().setMinWidth(650);
        controller.mostrarDialogo(true);
    }

    @FXML
    public void nuevoTicket(ActionEvent event) {
        Mesa mesa = tableViewMesas.getSelectionModel().getSelectedItem();
        Ticket ticket = Logica.getInstance().añadirAbrirTicket(mesa);

        if(ticket.getEstado().equals(Estado.ABIERTO)) {
            DialogoPedidoController dpController = (DialogoPedidoController) cargarDialogo("DialogoPedido.fxml");
            dpController.setTicket(ticket);
            System.out.println(ticket.getNumero() + " " + ticket.getIdMesa() + " " + ticket.getFecha() + " " + ticket.getEstado());
            dpController.getStage().setMinHeight(450);
            dpController.getStage().setMinWidth(650);
            dpController.mostrarDialogo(true);
        }
        else {
            System.out.println("El ticket está cerrado.");
        }
    }

    @FXML
    public void verTicket(ActionEvent event) {
        DialogoTicketController dtController = (DialogoTicketController) cargarDialogo("DialogoTicket.fxml");
        Mesa mesa = tableViewMesas.getSelectionModel().getSelectedItem();
        dtController.setTickets(mesa);
        dtController.mostrarDialogo(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       tableViewMesas.setItems(Logica.getInstance().getListaMesas());

       botonBorrar.setDisable(true);
       botonModificar.setDisable(true);
       botonAbrirTicket.setDisable(true);
       botonVerTickets.setDisable(true);

       tableViewMesas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Mesa>() {
           @Override
           public void changed(ObservableValue<? extends Mesa> observableValue, Mesa mesa, Mesa mesaSelected) {
               if(tableViewMesas.getSelectionModel().getSelectedItem() != null) {
                   botonBorrar.setDisable(false);
                   botonModificar.setDisable(false);
                   botonAbrirTicket.setDisable(false);
                   botonVerTickets.setDisable(false);
               }
           }
       });

       try {
           estilosCb.getItems().addAll(Style.DARK, Style.LIGHT);
//           estilosCb.getSelectionModel().select(Style.valueOf(Application.getUserAgentStylesheet()));
           estilosCb.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   if (estilosCb.getSelectionModel().getSelectedItem() != null) {
                       new JMetro(getStage().getScene(), estilosCb.getSelectionModel().getSelectedItem());
                   }
               }
           });
//           estilosCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Style>() {
//               @Override
//               public void changed(ObservableValue<? extends Style> observableValue, Style style, Style estilo) {
//
//               }
//           });
       } catch (Exception e) {
           System.out.println("Aaa");
       }



    }

}
