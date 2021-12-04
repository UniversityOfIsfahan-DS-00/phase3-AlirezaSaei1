package Calculator;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class eController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void show(ActionEvent e){
        try {
            root = FXMLLoader.load(getClass().getResource("JFXs/Expression.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Arithmetic Expression");
            stage.show();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    TextField input;
    @FXML
    Label answer;
    public void calculate(ActionEvent e){
        try {
            Expression exp = new Expression();
            String inp = input.getText();
            String check = exp.earlyCheck(inp);
            if(!check.equals("OK")){
                throw new Exception(check);
            }
            String infix = exp.getExpression(inp);
            String postfix = exp.infixToPostfix(infix);
            double solution = exp.evaluatePostfix(postfix);
            System.out.println("Postfix: " + postfix);
            answer.setStyle("-fx-text-fill: #000000;");
            answer.setText(String.valueOf(solution));
        }catch (Exception exception){

            answer.setStyle("-fx-text-fill: #ed7068;");
            answer.setText(exception.getMessage());
        }
    }

}
