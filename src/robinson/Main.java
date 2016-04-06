package robinson;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Agarimo
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Var.initVar();
        Var.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("/robinson/view/Win.fxml"));

        Scene scene = new Scene(root);

        Image icon = new Image(getClass().getResourceAsStream("/robinson/resources/robinsonPng.png"));
        Var.stage.getIcons().add(icon);
        Var.stage.setTitle("Lista Robinson 1.1");

        Var.stage.setResizable(false);
        Var.stage.setScene(scene);
        Var.stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
