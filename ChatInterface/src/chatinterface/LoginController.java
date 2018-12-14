/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatinterface;

import com.jfoenix.controls.JFXListView;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



//Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException

/**
 *
 * @author 5ei
 */
public class LoginController implements Initializable {
    
    static private Scene main;
    @FXML
    private Label label;
    
    @FXML
    private void CloseButtonAction(Event event) {
        System.exit(0);
    }  
    
    @FXML
    private void LoginKeyAction(Event event) throws IOException {
        ChatInterface.SetMainScene();
        MainController.PopolaLista();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    
}
