package Calculator;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class eController implements Initializable {

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

    public void back(ActionEvent e){
        try {
            root = FXMLLoader.load(getClass().getResource("JFXs/Main.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu");
            stage.show();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    Label input;
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
            answer.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color:  #696969;");
            answer.setText(String.valueOf(solution));
        }catch (Exception exception){

            answer.setStyle("-fx-text-fill: #ed7068; -fx-background-color:  #696969");
            answer.setText(exception.getMessage());
        }
    }
    @FXML Button one;      @FXML Button two;
    @FXML Button three;    @FXML Button four;
    @FXML Button five;     @FXML Button six;
    @FXML Button seven;    @FXML Button eight;
    @FXML Button nine;     @FXML Button zero;
    @FXML Button dot;      @FXML Button p1;
    @FXML Button p2;       @FXML Button add;
    @FXML Button minus;    @FXML Button multiple;
    @FXML Button divide;   @FXML Button power;
    @FXML Button ce;       @FXML Button del;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        one.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "1");
            }
        });
        two.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "2");
            }
        });
        three.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "3");
            }
        });
        four.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "4");
            }
        });
        five.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "5");
            }
        });
        six.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "6");
            }
        });
        seven.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "7");
            }
        });
        eight.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "8");
            }
        });
        nine.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "9");
            }
        });
        zero.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "0");
            }
        });
        p1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "(");
            }
        });
        p2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + ")");
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "+");
            }
        });
        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "-");
            }
        });
        multiple.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "*");
            }
        });
        divide.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "/");
            }
        });
        power.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + "^");
            }
        });
        dot.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText() + ".");
            }
        });
        ce.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText("");
            }
        });
        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                input.setText(input.getText().substring(0,input.getText().length()-1));
            }
        });

    }
}
