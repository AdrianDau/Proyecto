package es.adrian.logic;

import es.adrian.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Logica {
    private static Logica INSTANCE = null;

    private ObservableList<Mesa> listaMesas;
    private ObservableList<Producto> listaProductos;
    private ObservableList<Ticket> listaTickets;
    private ObservableList<Consumicion> listaConsumiciones;

    private Logica() {
        listaMesas = FXCollections.observableArrayList();
        listaMesas.add(new Mesa(1, "Primera por la derecha", 4));
        listaMesas.add(new Mesa(2, "Segunda por la derecha", 6));

        listaProductos = FXCollections.observableArrayList();
        listaProductos.add(new Producto("Macarrones", 6.25, Categoria.PRIMERPLATO));
        listaProductos.add(new Producto("Agua", 2.00, Categoria.BEBIDAS));

        listaTickets = FXCollections.observableArrayList();

        listaConsumiciones = FXCollections.observableArrayList();
    }

    public static Logica getInstance() {
        if(INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Mesa> getListaMesas() {
        return listaMesas;
    }

    public void añadirMesa(Mesa mesa) {
        listaMesas.add(mesa);
    }

    public void modificarMesa(Mesa mesa) {
        int posicion = listaMesas.indexOf(mesa);
        listaMesas.set(posicion, mesa);
    }

    public void borrarMesa(int indice) {
        listaMesas.remove(indice);
    }

    public ObservableList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void añadirProducto(Producto producto) {
        listaProductos.add(producto);
    }

    public void modificarProducto(Producto producto) {
        int posicion = listaProductos.indexOf(producto);
        listaProductos.set(posicion, producto);
    }

    public void borrarProducto(int indice) {
        listaProductos.remove(indice);
    }

    public ObservableList<Ticket> getListaTickets() {
        return listaTickets;
    }

    public Ticket añadirAbrirTicket(Mesa mesa) {
        List<Ticket> listaT = listaTickets;

        Ticket ticketAux = null;
        for(Ticket t : listaT) {
            if (t.getIdMesa() == mesa.getId() && t.getEstado().equals(Estado.ABIERTO)) {
                ticketAux = t;
                System.out.println("Ticket abierto encontrado");

                return ticketAux;

            }
        }
        int idTicket = maxValorListaTickets() + 1;
        int idMesa = mesa.getId();
        ticketAux = new Ticket(idTicket, idMesa);
        listaTickets.add(ticketAux);

//        if(ticketAux == null) {
//            int idTicket = maxValorListaTickets() + 1;
//            int idMesa = mesa.getId();
//            ticketAux = new Ticket(idTicket, idMesa);
//            listaTickets.add(ticketAux);
//        }

        return ticketAux;
    }

    public int maxValorListaTickets() {
        int maximo = 0;
        for(int i = 0; i < listaTickets.size(); i++) {
            if(maximo < listaTickets.get(i).getNumero()) {
                maximo = listaTickets.get(i).getNumero();
            }
        }
        return maximo;
    }

    public void actualizarEstadoTicket(Ticket ticket, Estado estado) {
        for(Ticket t: listaTickets) {
            if(t.getNumero() == ticket.getNumero()) {
                t.setEstado(estado);
                ticket.setEstado(estado);
            }
        }
    }

    public String calcularTotalTicket(Ticket ticket) {
        double totalTicket = 0;
        for(Consumicion c: ticket.getListaConsumiciones()) {
            totalTicket += c.getProducto().getPrecio()*c.getCantidad();
        }

        return String.valueOf(totalTicket);
    }

    public ObservableList<Consumicion> getListaConsumiciones() {
        return listaConsumiciones;
    }

    public void añadirConsumicion(Consumicion consumicion) {
        listaConsumiciones.add(consumicion);
    }

    public void borrarConsumicion(int indice) {
        listaConsumiciones.remove(indice);
    }
}
