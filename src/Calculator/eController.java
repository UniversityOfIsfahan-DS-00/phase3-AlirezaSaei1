package Calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class eController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void show(ActionEvent e) {
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


    public void expTree(ActionEvent e) {
        TreeNode n = expressionTree(Expression.postfix);
        System.out.println("====================EXPRESSION TREE====================");
        System.out.println(traversePreOrder(n));
    }


    public static TreeNode expressionTree(String postfix) {
        myStack<TreeNode> st = new myStack<>();
        TreeNode t1, t2, temp;
        StringBuilder sb;

        for (int i = 0; i < postfix.length(); i++) {
            sb = new StringBuilder();
            while (postfix.charAt(i) != ' ') {
                sb.append(postfix.charAt(i++));
            }
            if (!"+-*/^".contains(String.valueOf(sb))) {
                temp = new TreeNode(String.valueOf(sb));
                st.push(temp);
            } else {
                temp = new TreeNode(String.valueOf(sb));

                t1 = st.pop();
                t2 = st.pop();

                temp.left = t1;
                temp.right = t2;

                st.push(temp);
            }

        }
        temp = st.pop();
        return temp;
    }

    public String traversePreOrder(TreeNode root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.data);

        String pointerRight = "└──";
        String pointerLeft = (root.right != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.left, root.right != null);
        traverseNodes(sb, "", pointerRight, root.right, false);

        return sb.toString();
    }

    public void traverseNodes(StringBuilder sb, String padding, String pointer, TreeNode node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.data);

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.right != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.left, node.right != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.right, false);
        }
    }

    public void back(ActionEvent e) {
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
    TextField input;
    @FXML
    Label answer;
    @FXML
    ListView<String> steps;
    public void calculate(ActionEvent e) {
        steps.getItems().clear();
        try {
            Expression exp = new Expression();
            String inp = input.getText();
            String check = exp.earlyCheck(inp);

            if (!check.equals("OK")) {
                throw new Exception(check);
            }

            String infix = exp.getExpression(inp);
            String postfix = exp.infixToPostfix(infix);
            double solution = exp.evaluatePostfix(postfix);

            System.out.println("Postfix: " + postfix);
            answer.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color:  #696969;");
            answer.setText(String.valueOf(solution));
            Expression.stepByStep.add("Final Value: " + solution);
            List<String> newList = Expression.stepByStep.stream()
                    .distinct()
                    .collect(Collectors.toList());

            steps.getItems().addAll(newList);
            tree.setDisable(false);
        } catch (Exception exception) {
            tree.setDisable(true);
            answer.setStyle("-fx-text-fill: #ed7068; -fx-background-color:  #696969");
            answer.setText(exception.getMessage());
        }
    }

    @FXML Button one;@FXML Button two;
    @FXML Button three;
    @FXML Button four;@FXML Button five;
    @FXML Button six;@FXML Button seven;
    @FXML Button eight;@FXML Button nine;
    @FXML Button zero;@FXML Button dot;
    @FXML Button p1;@FXML Button p2;
    @FXML Button add;@FXML Button minus;
    @FXML Button multiple;@FXML Button divide;
    @FXML Button power;@FXML Button ce;
    @FXML Button del;@FXML Button tree;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn(del, ce, one, two, three, four, five, six, seven);
        btn(nine, zero, dot, p1, p2, add, minus, multiple, divide);
        power.setSkin(new MyButtonSkin(power));
        eight.setSkin(new MyButtonSkin(eight));
        input.setStyle("-fx-text-fill: white; -fx-background-color: #696969");

        one.setOnAction(e -> input.setText(input.getText() + "1"));
        two.setOnAction(e -> input.setText(input.getText() + "2"));
        three.setOnAction(e -> input.setText(input.getText() + "3"));
        four.setOnAction(e -> input.setText(input.getText() + "4"));
        five.setOnAction(e -> input.setText(input.getText() + "5"));
        six.setOnAction(e -> input.setText(input.getText() + "6"));
        seven.setOnAction(e -> input.setText(input.getText() + "7"));
        eight.setOnAction(e -> input.setText(input.getText() + "8"));
        nine.setOnAction(e -> input.setText(input.getText() + "9"));
        zero.setOnAction(e -> input.setText(input.getText() + "0"));
        p1.setOnAction(e -> input.setText(input.getText() + "("));
        p2.setOnAction(e -> input.setText(input.getText() + ")"));
        add.setOnAction(e -> input.setText(input.getText() + "+"));
        minus.setOnAction(e -> input.setText(input.getText() + "-"));
        multiple.setOnAction(e -> input.setText(input.getText() + "*"));
        divide.setOnAction(e -> input.setText(input.getText() + "/"));
        power.setOnAction(e -> input.setText(input.getText() + "^"));
        dot.setOnAction(e -> input.setText(input.getText() + "."));
        ce.setOnAction(e -> {
            input.setText("");
            steps.getItems().clear();
            answer.setText("");
            tree.setDisable(true);
        });
        del.setOnAction(e -> {
            try {
                input.setText(input.getText().substring(0, input.getText().length() - 1));
            } catch (Exception ignored) {
            }
        });

    }

    private void btn(Button nine, Button zero, Button dot, Button p1, Button p2, Button add, Button minus, Button multiple, Button divide) {
        nine.setSkin(new MyButtonSkin(nine));
        zero.setSkin(new MyButtonSkin(zero));
        dot.setSkin(new MyButtonSkin(dot));
        p1.setSkin(new MyButtonSkin(p1));
        p2.setSkin(new MyButtonSkin(p2));
        add.setSkin(new MyButtonSkin(add));
        minus.setSkin(new MyButtonSkin(minus));
        multiple.setSkin(new MyButtonSkin(multiple));
        divide.setSkin(new MyButtonSkin(divide));
    }
}
