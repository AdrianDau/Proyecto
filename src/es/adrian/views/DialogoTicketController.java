package es.adrian.views;

import es.adrian.logic.Logica;
import es.adrian.models.Consumicion;
import es.adrian.models.Estado;
import es.adrian.models.Mesa;
import es.adrian.models.Ticket;
import es.adrian.utils.DateUtils;
import es.adrian.views.filters.FilterDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogoTicketController extends BaseController implements Initializable {

    private FilterDate filterDate;

    private ObservableList<Ticket> ticketFilter;

    @FXML
    private TableView<Ticket> tableViewTickets;

    @FXML
    private TableView<Consumicion> tableViewConsumicionesTicket;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFinal;

    @FXML
    private Button botonReabrir;

    @FXML
    void reabrirTicket() {
        Ticket ticket = tableViewTickets.getSelectionModel().getSelectedItem();
//            for(Ticket t: ticketFilter) {
//                if(t.getEstado().equals(Estado.ABIERTO)) {
                    botonReabrir.setDisable(true);
//                }
//            }

            Logica.getInstance().actualizarEstadoTicket(ticket, Estado.ABIERTO);
            System.out.println(ticket.getEstado());
            tableViewTickets.refresh();
            DialogoPedidoController controller = (DialogoPedidoController) cargarDialogo("DialogoPedido.fxml");
            controller.setTicket(ticket);
            controller.getStage().setMinHeight(450);
            controller.getStage().setMinWidth(650);
            controller.mostrarDialogo(true);
            tableViewTickets.getSelectionModel().clearSelection();
            tableViewTickets.refresh();
    }

    public void setTickets(Mesa mesa) {
        for(Ticket t: Logica.getInstance().getListaTickets()) {
            if(t.getIdMesa() == mesa.getId()) {
                ticketFilter.add(t);
            }
        }
        tableViewTickets.setItems(ticketFilter);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            ticketFilter = FXCollections.observableArrayList();
            filterDate = new FilterDate(ticketFilter);

            tableViewTickets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Ticket>() {
                @Override
                public void changed(ObservableValue<? extends Ticket> observableValue, Ticket ticket, Ticket ticketSelect) {
                    tableViewTickets.refresh();
                    int index = tableViewTickets.getSelectionModel().getSelectedIndex();
                    if (index >= 0 && index < tableViewTickets.getItems().size()) {
                        ObservableList<Consumicion> lista = FXCollections.observableArrayList(ticketSelect.getListaConsumiciones());
                        tableViewConsumicionesTicket.setItems(lista);
                        boolean activarBoton = false;

                        for(Ticket t: ticketFilter) {
                            if(t.getEstado().equals(Estado.ABIERTO)) {
                                activarBoton = true;

                            }

                        }
                        botonReabrir.setDisable(activarBoton);
                    }
                    tableViewConsumicionesTicket.refresh();
                }
            });

        fechaInicio.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(fechaInicio.getEditor().textProperty().toString());
                System.out.println("oldValue: " + oldValue);
                System.out.println("newValue: " + newValue);

                tableViewTickets.setItems(filterDate.filtrarDate(DateUtils.fromStringToLocalDate(newValue),fechaFinal.getValue()));
            }
        });

        fechaFinal.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(fechaFinal.getEditor().textProperty().toString());
                System.out.println("oldValue: " + oldValue);
                System.out.println("newValue: " + newValue);

                tableViewTickets.setItems(filterDate.filtrarDate(fechaInicio.getValue(), DateUtils.fromStringToLocalDate(newValue)));
            }
        });

        
    }
}
