package robinson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import robinson.model.Entidad;
import robinson.model.Proveedor;
import robinson.model.Telefono;
import sql.Sql;

/**
 *
 * @author Agarimo
 */
public class Query extends sql.Query{
    
    public static Entidad getEntidad(String query) {
        Entidad aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Entidad();
                aux.setId(rs.getInt("id"));
                aux.setIdProveedor(rs.getInt("id_proveedor"));
                aux.setCif(rs.getString("cif"));
                aux.setFechaEntrada(rs.getDate("fecha_entrada"));
            }
            
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    
    public static Proveedor getProveedor(String query) {
        Proveedor aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Proveedor();
                aux.setId(rs.getInt("id"));
                aux.setNombre(rs.getString("nombre"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    
    public static Telefono getTelefono(String query) {
        Telefono aux = null;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Telefono();
                aux.setId(rs.getInt("id"));
                aux.setIdEntidad(rs.getInt("id_entidad"));
                aux.setTelefono(rs.getString("telefono"));
                aux.setFechaEntrada(rs.getDate("fecha_entrada"));
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    
    public static List<Entidad> listaEntidad(String query) {
        List<Entidad> list = new ArrayList();
        Entidad aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Entidad();
                aux.setId(rs.getInt("id"));
                aux.setIdProveedor(rs.getInt("id_proveedor"));
                aux.setCif(rs.getString("cif"));
                aux.setFechaEntrada(rs.getDate("fecha_entrada"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Proveedor> listaProveedor(String query) {
        List<Proveedor> list = new ArrayList();
        Proveedor aux;
        
        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Proveedor();
                aux.setId(rs.getInt("id"));
                aux.setNombre(rs.getString("nombre"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static List<Telefono> listaTelefono(String query) {
        List<Telefono> list = new ArrayList();
        Telefono aux;

        try {
            bd = new Sql(Var.con);
            rs = bd.ejecutarQueryRs(query);

            while (rs.next()) {
                aux = new Telefono();
                aux.setId(rs.getInt("id"));
                aux.setIdEntidad(rs.getInt("id_enditad"));
                aux.setTelefono(rs.getString("telefono"));
                aux.setFechaEntrada(rs.getDate("fecha_entrada"));
                list.add(aux);
            }
            rs.close();
            bd.close();
        } catch (SQLException ex) {
            error(ex.getMessage());
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
