/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatinterface;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author 5ei
 */
public class MainController implements Initializable {
    
    static ObservableList SubTopic = FXCollections.observableArrayList();
    
    
    @FXML
    private static ListView<String> topicSubbed;
    @FXML
    private JFXTextArea ChatArea;
      

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public static void PopolaLista() {
        SubTopic.addAll("Topic1","Topic2","Topic3");
        topicSubbed.getItems().addAll(SubTopic);
    } 

    @FXML
    private void CloseButtonAction(MouseEvent event) {
        System.exit(0);
    }
    
}
