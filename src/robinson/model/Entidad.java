package robinson.model;

import java.util.Date;
import java.util.List;
import robinson.Var;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Entidad {

    private int id;
    private int idProveedor;
    private String cif;
    private Date fechaEntrada;
    private List<Telefono> telefonos;
    
    public Entidad(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    @Override
    public String toString() {
        return "Entidad{" + "id=" + id + ", idProveedor=" + idProveedor + ", cif=" + cif + ", fechaEntrada=" + fechaEntrada + ", telefonos=" + telefonos + '}';
    }
    
    public String SQLBuscar(){
        return "SELECT * FROM "+Var.dbName+".entidad WHERE cif="+Varios.entrecomillar(this.cif);
    }
    
    public String SQLCrear() {
        return "INSERT into " + Var.dbName + ".entidad (id_proveedor,cif,fecha_entrada) values("
                + this.idProveedor + ","
                + Varios.entrecomillar(this.cif) + ","
                + "CURDATE()"
                + ");";
    }
}
