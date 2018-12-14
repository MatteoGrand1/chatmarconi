/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author 5ei
 */
public class ChatInterface extends Application{
    
    static private Stage stage;
    
    static private Scene login;
    static private Scene main;
    
    static private Parent loginroot;
    static private Parent mainroot;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        stage.initStyle(StageStyle.UNDECORATED);
        
        loginroot = FXMLLoader.load(getClass().getResource("/chatinterface/Login.fxml"));
        mainroot = FXMLLoader.load(getClass().getResource("/chatinterface/Main.fxml"));
        
        SetloginScene();
        
    }

    private static void SetloginScene() throws IOException{
        login = new Scene(loginroot);
        stage.setScene(login);
        stage.show();
    }
    
    static void SetMainScene() throws IOException{
        main = new Scene(mainroot);
        stage.setScene(main);
        stage.show();
      }
  
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    

     
    
}
