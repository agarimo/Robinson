package robinson.model;

import java.util.Date;
import robinson.Var;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Telefono {
    private int id;
    private int idEntidad;
    private String telefono;
    private Date fechaEntrada;
    
    public Telefono(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    
    public String SQLBuscar(){
        return "SELECT * FROM "+Var.dbName+".telefono where telefono="+Varios.entrecomillar(this.telefono);
    }
    
    public String SQLCrear() {
        return "INSERT into " + Var.dbName + ".telefono (id_entidad, telefono,fecha_entrada) values("
                + this.idEntidad + ","
                + Varios.entrecomillar(this.telefono) + ","
                + "CURDATE()"
                + ");";
    }
}
