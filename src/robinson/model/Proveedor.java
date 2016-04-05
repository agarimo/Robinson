package robinson.model;

import java.util.Objects;
import robinson.Var;
import util.Varios;

/**
 *
 * @author Agarimo
 */
public class Proveedor {
    private int id;
    private String nombre;
    
    public Proveedor(){
        
    }
    
    public Proveedor(String nombre){
        this.nombre=nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Proveedor other = (Proveedor) obj;
        return Objects.equals(this.nombre, other.nombre);
    }
    
    @Override
    public String toString(){
        return this.id+"-"+this.nombre;
    }
            
    
    public String SQLBuscar(){
        return "SELECT * FROM "+Var.dbName+".proveedor WHERE nombre="+Varios.entrecomillar(this.nombre);
    }
    
    public String SQLCrear() {
        return "INSERT into " + Var.dbName + ".proveedor (nombre) values("
                + Varios.entrecomillar(this.nombre)
                + ");";
    }
    
}
