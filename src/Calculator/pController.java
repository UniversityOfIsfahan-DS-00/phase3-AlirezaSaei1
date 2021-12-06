package Calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class pController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void show(ActionEvent e){
        try {
            root = FXMLLoader.load(getClass().getResource("JFXs/Polynomial.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Polynomial");
            stage.show();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void getValues(ActionEvent e){

    }

}
