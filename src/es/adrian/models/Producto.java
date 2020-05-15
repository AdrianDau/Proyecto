package es.adrian.models;

public class Producto {
    private String nombreProducto;
    private double precio;
    private Categoria categoria;

    public Producto(String nombreProducto, double precio, Categoria categoria) {
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nombreProducto;
    }
}
