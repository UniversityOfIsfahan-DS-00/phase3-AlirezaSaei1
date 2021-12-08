package Calculator;

import javafx.event.ActionEvent;

public class Controller{
    public void arithmeticExpression(ActionEvent e){
        eController ec = new eController();
        ec.show(e);
    }

    public void exit(ActionEvent e){
        System.exit(0);
    }

}
