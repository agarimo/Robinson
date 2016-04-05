package robinson.ctrl;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robinson.Query;
import robinson.Var;
import robinson.model.Entidad;
import robinson.model.Proveedor;
import robinson.model.Telefono;
import sql.Sql;

/**
 *
 * @author Agarimo
 */
public class Insert {
    
    public static void insertItem(Proveedor proveedor, String cif, String telefono) {
        Entidad entidad;
        Telefono telf;

        entidad = new Entidad();
        entidad.setCif(cif);
        entidad.setIdProveedor(proveedor.getId());
        entidad = insertEntidad(entidad);

        telf = new Telefono();
        telf.setIdEntidad(entidad.getId());
        telf.setTelefono(telefono);
        insertTelefono(telf);
    }

    private static Entidad insertEntidad(Entidad entidad) {
        Entidad aux = Query.getEntidad(entidad.SQLBuscar());

        if (aux == null) {
            try {
                Sql bd = new Sql(Var.con);
                bd.ejecutar(entidad.SQLCrear());
                bd.close();

                aux = Query.getEntidad(entidad.SQLBuscar());
            } catch (SQLException ex) {
                Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return aux;
    }

    private static void insertTelefono(Telefono telefono) {
        try {
            Sql bd = new Sql(Var.con);
            bd.ejecutar(telefono.SQLCrear());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
