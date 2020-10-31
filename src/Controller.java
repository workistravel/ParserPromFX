import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller {

         static String homeCat;
         static String separ;
         static String saveCat;
         static String saveRes;

         DirectoryChooser directoryChooser = new DirectoryChooser();

        @FXML
        public static Stage primaryStage;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button start;

        @FXML
        private Button selectResources;

        @FXML
        private TextField viewResources;

        @FXML
        private Button selectSave;

        @FXML
        private TextField viewSave;

        @FXML
        void selectRes(ActionEvent event) {

        }

        @FXML
        void selectSave(ActionEvent event) {

        }

        @FXML
        void strartApp(ActionEvent event) {

        }

        @FXML
        void initialize() {
                Properties prop = System.getProperties();
                separ = prop.getProperty("file.separator");
                homeCat = prop.getProperty("user.home");
                viewResources.appendText(homeCat);
                viewSave.appendText(homeCat);


                start.setOnAction(event->{


                        WriteToXLSX wxlsx= new WriteToXLSX();
                        HashMap<String, List> catalog= new HashMap<>();
                        List<myRow> prom;
                        ParseFolder parseFolder = new ParseFolder();

                        catalog = parseFolder.parceFolder(saveCat);
                        LogicSearchForMatches lsm= new LogicSearchForMatches();
                        prom=lsm.searchMatchers(catalog);

                        wxlsx.writeXLSX(prom);

                        start.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("finish.fxml"));
                        try {
                                loader.load();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.showAndWait();

                });

                selectResources.setOnAction(event -> {

                        File dir = directoryChooser.showDialog(primaryStage);
                        if (dir != null) {
                                viewResources.setText(dir.getAbsolutePath());
                                saveCat= viewResources.getText();
                        } else {
                                viewResources.setText(null);
                        }
                });

                selectSave.setOnAction(event -> {
                        File dir = directoryChooser.showDialog(primaryStage);
                        if (dir != null) {
                                viewSave.setText(dir.getAbsolutePath());
                                saveRes = viewSave.getText();
                        } else {
                                viewSave.setText(null);
                        }
                });

        }


}



