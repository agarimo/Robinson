package robinson.ctrl;

import files.LoadFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.concurrent.Task;
import robinson.Query;
import robinson.Var;
import static robinson.Var.calculaCif;
import static robinson.Var.validarCif;
import robinson.model.Proveedor;

/**
 *
 * @author Agarimo
 */
public class Import extends Task {

    LoadFile lf;
    List<Proveedor> proveedores;

    public Import(LoadFile lf) {
        this.lf = lf;
        this.lf.eliminaCabecera();
        proveedores = new ArrayList();
        proveedores.addAll(Query.listaProveedor("SELECT * FROM " + Var.dbName + ".proveedor"));
    }

    @Override
    protected Object call() throws Exception {
        updateMessage("Importando Fichero");
        double pg= 1;
        double tl= lf.getCount();
        String aux;
        Iterator<String> it = lf.getLineas().iterator();

        while (it.hasNext()) {
            updateMessage("Importando "+(int) pg+" de "+(int) tl);
            aux = it.next();
            split(aux);
            updateProgress(pg, tl);
            pg++;
        }

        return null;
    }

    private void split(String linea) {
        String[] split = linea.split(";");

        String cif = getCif(split[10]);
        String telefono1 = getTelefono(split[11]);
        Proveedor proveedor = getProveedor(split[32]);
        
        Insert.insertItem(proveedor, cif, telefono1);
    }

    private String getCif(String cif) {
        if (validarCif(cif)) {
            return cif;
        } else {
            String aux = calculaCif(cif);

            if (aux != null) {
                return aux;
            } else {
                return null;
            }
        }
    }

    private String getTelefono(String telefono) {
        if (Var.validarTelefono(telefono)) {
            return telefono;
        } else {
            return null;
        }
    }

    private Proveedor getProveedor(String nombre) {
        Proveedor proveedor;

        switch (nombre) {
            case "APM_DATA":
                proveedor = new Proveedor("APM_DATA");
                break;
            case "Meydis_1":
                proveedor = new Proveedor("MEYDIS");
                break;
            case "Informesonli":
                proveedor = new Proveedor("INFORMESONLINE");
                break;
            default:
                proveedor = new Proveedor("DESCONOCIDO");
                break;
        }

        return proveedores.get(proveedores.indexOf(proveedor));
    }

}
