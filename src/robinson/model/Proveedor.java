package robinson.model;

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
    public String toString(){
        return this.nombre;
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
