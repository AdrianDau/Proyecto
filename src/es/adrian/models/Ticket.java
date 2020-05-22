package es.adrian.models;

import es.adrian.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket {
    private int numero;
    private Date fecha;
    private int idMesa;
    private Estado estado;
    private List<Consumicion> listaConsumiciones;

    public Ticket(int numero, int idMesa) {
        this.numero = numero;
        this.fecha = new Date();
        this.idMesa = idMesa;
        this.estado = Estado.ABIERTO;
        this.listaConsumiciones = new ArrayList<>();
    }

    public Ticket(int numero, Date fecha, int idMesa) {
        this.numero = numero;
        this.fecha = fecha;
        this.idMesa = idMesa;
        this.estado = Estado.CERRADO;
        this.listaConsumiciones = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Consumicion> getListaConsumiciones() {
        return listaConsumiciones;
    }

}
