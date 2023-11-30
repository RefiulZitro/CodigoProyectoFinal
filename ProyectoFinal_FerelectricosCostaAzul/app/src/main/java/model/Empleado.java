package model;

public class Empleado {
    private String uid;
    private String email;
    private String contrasena;
    private String nombre;
    private String cedula;
    private String telefono;
    private String cargo;
    private String dlaborales;
    private String hentrada;
    private String hsalida;
    private String salario;

    public Empleado() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDlaborales() {
        return dlaborales;
    }

    public void setDlaborales(String dlaborales) {
        this.dlaborales = dlaborales;
    }

    public String getHentrada() {
        return hentrada;
    }

    public void setHentrada(String hentrada) {
        this.hentrada = hentrada;
    }

    public String getHsalida() {
        return hsalida;
    }

    public void setHsalida(String hsalida) {
        this.hsalida = hsalida;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Nombre: "+nombre+ "\n" +"Email: "+email+ "\n"+"Contraseña: "+contrasena+ "\n" +"Cedula: "+cedula+ "\n" +"Telefono: "
                +telefono+ "\n" + "Cargo: "+cargo;
    }
}

