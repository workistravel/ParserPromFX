import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FinishController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fild;

    @FXML
    void setInfo(ActionEvent event) {

    }

    @FXML
    void initialize() {

        String s= Controller.saveRes;

        fild.setText(s);

    }
}


