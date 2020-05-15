package es.adrian.models;

public class Mesa {
    private int id;
    private String localizacion;
    private int capacidad;

    public Mesa(int id, String localizacion, int capacidad) {
        this.id = id;
        this.localizacion = localizacion;
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}
