package model;

public class Producto {
    private String uid;
    private String nombre;
    private String tipo;
    private String precio;
    private String stock;
    private String descripcion;

    public Producto() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {

        return  "Nombre: " +nombre + "\n" + "Tipo: " + tipo+ "\n"+ "Precio: " +precio+ "\n"+ "StocK: " +stock+ "\n"+"Descripcio: "+descripcion ;
    }

}
