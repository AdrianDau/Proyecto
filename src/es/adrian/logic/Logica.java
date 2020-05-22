package es.adrian.logic;

import es.adrian.models.*;
import es.adrian.utils.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class Logica {
    private static Logica INSTANCE = null;

    private ObservableList<Mesa> listaMesas;
    private ObservableList<Producto> listaProductos;
    private ObservableList<Ticket> listaTickets;
    private ObservableList<Consumicion> listaConsumiciones;

    private Logica() {
        listaMesas = FXCollections.observableArrayList();
        cargarMesas();

        listaProductos = FXCollections.observableArrayList();
        cargarProductos();
//        listaProductos.add(new Producto("Macarrones", 6.25, Categoria.PRIMERPLATO));
//        listaProductos.add(new Producto("Agua", 2.00, Categoria.BEBIDAS));

        listaTickets = FXCollections.observableArrayList();
        listaTickets.add(new Ticket(1, DateUtils.createNewDate("05/12/2020 17:30:00") ,1));
        listaTickets.add(new Ticket(2, DateUtils.createNewDate("05/22/2020 14:20:20") ,1));
        listaTickets.add(new Ticket(3, DateUtils.createNewDate("05/10/2020 13:30:00") ,2));
        listaTickets.add(new Ticket(4, DateUtils.createNewDate("05/16/2020 19:20:20") ,2));

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

    public int maxValorListaMesas() {
        int maximo = 0;
        for(int i = 0; i < listaMesas.size(); i++) {
            if(maximo < listaMesas.get(i).getId()) {
                maximo = listaMesas.get(i).getId();
            }
        }
        return maximo +1;
    }

    private void cargarMesas(){
        try {
            File fichero = new File("C:\\Users\\AdrianDau\\Desktop\\Mesas.csv");
            FileReader fr = new FileReader(fichero);
            BufferedReader bf = new BufferedReader(fr);
            String linea;
            while((linea = bf.readLine()) != null) {
                String[] parts = linea.split(";");
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];

                Mesa mesa = new Mesa(Integer.parseInt(part1), part2, Integer.parseInt(part3));
                listaMesas.add(mesa);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void cargarProductos() {
        try {
            File fichero = new File("C:\\Users\\AdrianDau\\Desktop\\Productos.csv");
            FileReader fr = new FileReader(fichero);
            BufferedReader bf = new BufferedReader(fr);
            String linea;
            while((linea = bf.readLine()) != null) {
                String[] parts = linea.split(";");
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];

                Producto producto = new Producto(part1, Double.parseDouble(part2), Categoria.valueOf(part3));
                listaProductos.add(producto);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        int idTicket = maxValorListaTickets();
        int idMesa = mesa.getId();
        ticketAux = new Ticket(idTicket, idMesa);
        listaTickets.add(ticketAux);

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

    public void finalizar() {
        INSTANCE = null;
    }
}
