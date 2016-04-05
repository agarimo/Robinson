package robinson.ctrl;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import files.LoadFile;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import robinson.Query;
import robinson.Var;
import static robinson.Var.calculaCif;
import static robinson.Var.validarCif;
import static robinson.Var.validarTelefono;
import static robinson.ctrl.Insert.insertItem;
import robinson.model.Proveedor;

/**
 *
 * @author Agarimo
 */
public class WinC implements Initializable {

    ObservableList<Proveedor> proveedores;
    FileChooser fileChooser;
    LoadFile importFile;

    @FXML
    private VBox rootPane;

    @FXML
    private VBox mainPane;

    @FXML
    private VBox addPane;

    @FXML
    private VBox importPane;

    @FXML
    private VBox viewPane;

    @FXML
    private Button btAgregar;

    @FXML
    private Button btImportar;

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

    @FXML
    private TextField tfFileImport;

    @FXML
    private Button btSelectFile;

    @FXML
    private Label lbProgreso;

    @FXML
    private ProgressBar pgProgreso;

    @FXML
    private Button btRunImport;

    @FXML
    private Button volverImport;

    @FXML
    private Label lbInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GlyphsDude.setIcon(btAgregar, MaterialDesignIcon.NOTE_PLUS, "40");
        GlyphsDude.setIcon(btConsultar, MaterialDesignIcon.FILE_FIND, "40");
        GlyphsDude.setIcon(btImportar, MaterialDesignIcon.IMPORT, "40");
        showPane(1);
        proveedores = FXCollections.observableArrayList();
        proveedores.addAll(Query.listaProveedor("SELECT * FROM " + Var.dbName + ".proveedor"));
        cbProveedor.setItems(proveedores);
        fileChooser = new FileChooser();
        ExtensionFilter ef = new ExtensionFilter("Archivos CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(ef);
        fileChooser.setTitle("Seleccione el archivo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        btRunImport.setDisable(true);
        pgProgreso.setVisible(false);
        lbProgreso.setVisible(false);
    }

    private void showPane(int aux) {
        switch (aux) {
            case 1:
                mainPane.setVisible(true);
                addPane.setVisible(false);
                viewPane.setVisible(false);
                importPane.setVisible(false);
                break;

            case 2:
                mainPane.setVisible(false);
                addPane.setVisible(true);
                viewPane.setVisible(false);
                importPane.setVisible(false);
                break;

            case 3:
                mainPane.setVisible(false);
                addPane.setVisible(false);
                viewPane.setVisible(true);
                importPane.setVisible(false);
                break;

            case 4:
                mainPane.setVisible(false);
                addPane.setVisible(false);
                viewPane.setVisible(false);
                importPane.setVisible(true);
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
    void importarArchivo(ActionEvent event) {
        showPane(4);
        pgProgreso.setVisible(false);
        lbProgreso.setVisible(false);
    }

    @FXML
    void selectFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(Var.stage);
        if (file != null) {
            importFile = new LoadFile(file);
            lbInfo.setText("Cargados " + importFile.getCount() + " registros");
            tfFileImport.setText(file.getAbsolutePath());
            btRunImport.setDisable(false);
        }
    }

    @FXML
    void runImport(ActionEvent event) {
        btRunImport.setDisable(true);
        importar();
    }

    private void importar() {
        pgProgreso.setVisible(true);
        lbProgreso.setVisible(true);
        Import i = new Import(importFile);
        pgProgreso.progressProperty().bind(i.progressProperty());
        lbProgreso.textProperty().bind(i.messageProperty());

        i.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                pgProgreso.setVisible(false);
                lbProgreso.setVisible(false);
                
//                MyObject result = task.getValue();
                // now do something with result
            }
        });

        new Thread(i).start();
    }

    @FXML
    void volverImport(ActionEvent event) {
        tfFileImport.setText("");
        lbInfo.setText("");
        btRunImport.setDisable(true);
        showPane(1);
    }

    @FXML
    void consultarRegistro(ActionEvent event) {
        showPane(3);
    }

    @FXML
    void aceptarAdd(ActionEvent event) {
        Proveedor proveedor = (Proveedor) cbProveedor.getSelectionModel().getSelectedItem();
        String cif = tfCif.getText().toUpperCase().trim();
        String telefono = tfTelefono.getText().toUpperCase().trim();

        if (validarCif(cif)) {
            if (validarTelefono(telefono)) {
                Insert.insertItem(proveedor, cif, telefono);
                showMainPane(new ActionEvent());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR EN TELÉFONO");
                alert.setContentText("El teléfono contiene caracteres no válidos.");

                alert.showAndWait();
            }
        } else {
            String aux = calculaCif(cif);

            if (aux != null) {
                if (validarTelefono(telefono)) {
                    insertItem(proveedor, aux, telefono);
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
    }
}
