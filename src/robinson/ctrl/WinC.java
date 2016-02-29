package robinson.ctrl;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import robinson.Query;
import robinson.Var;
import robinson.model.Entidad;
import robinson.model.Proveedor;
import robinson.model.Telefono;
import util.CalculaNif;
import util.Regex;
import util.Sql;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    ObservableList<Proveedor> proveedores;

    @FXML
    private VBox mainPane;

    @FXML
    private VBox addPane;

    @FXML
    private VBox viewPane;

    @FXML
    private Button btAgregar;

    @FXML
    private Button btConsultar;

    @FXML
    private ComboBox cbProveedor;

    @FXML
    private TextField tfCif;

    @FXML
    private TextField tfTelefono;

    @FXML
    private Button btAceptarAdd;

    @FXML
    private Button btCancelarAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GlyphsDude.setIcon(btAgregar, MaterialDesignIcon.NOTE_PLUS, "40");
        GlyphsDude.setIcon(btConsultar, MaterialDesignIcon.FILE_FIND, "40");
        mainPane.setVisible(true);
        addPane.setVisible(false);
        viewPane.setVisible(false);
        proveedores = FXCollections.observableArrayList();
        proveedores.addAll(Query.listaProveedor("SELECT * FROM " + Var.dbName + ".proveedor"));
        cbProveedor.setItems(proveedores);
    }

    private void showPane(int aux) {
        switch (aux) {
            case 1:
                mainPane.setVisible(true);
                addPane.setVisible(false);
                viewPane.setVisible(false);
                break;

            case 2:
                mainPane.setVisible(false);
                addPane.setVisible(true);
                viewPane.setVisible(false);
                break;

            case 3:
                mainPane.setVisible(false);
                addPane.setVisible(false);
                viewPane.setVisible(true);
                break;
        }
    }

    private void cleanView() {
        cbProveedor.getSelectionModel().select(0);
        tfCif.setText("");
        tfTelefono.setText("");
    }

    @FXML
    void showMainPane(ActionEvent event) {
        showPane(1);
    }

    @FXML
    void agregarRegistro(ActionEvent event) {
        cleanView();
        showPane(2);
    }

    @FXML
    void consultarRegistro(ActionEvent event) {
        showPane(3);
    }

    @FXML
    void aceptarAdd(ActionEvent event) {
        Proveedor proveedor = (Proveedor) cbProveedor.getSelectionModel().getSelectedItem();
        String cif = tfCif.getText();
        String telefono = tfTelefono.getText();

        if (validarCif(cif)) {
            if (validarTelefono(telefono)) {
                insertItem(proveedor, cif, telefono);
                showMainPane(new ActionEvent());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR EN TELÉFONO");
                alert.setContentText("El teléfono contiene caracteres no válidos.");

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR EN CIF");
            alert.setContentText("El cif introducido no es válido.");

            alert.showAndWait();

        }
    }

    private void insertItem(Proveedor proveedor, String cif, String telefono) {
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

    private Entidad insertEntidad(Entidad entidad) {
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

    private void insertTelefono(Telefono telefono) {
        try {
            Sql bd = new Sql(Var.con);
            bd.ejecutar(telefono.SQLCrear());
            bd.close();
        } catch (SQLException ex) {
            Logger.getLogger(WinC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validarCif(String cif) {
        CalculaNif cn = new CalculaNif();
        return cn.isvalido(cif);
    }

    private boolean validarTelefono(String telefono) {
        Regex rg = new Regex();
        return !rg.isBuscar("\\D", telefono);
    }

}
