/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import Swifts.BusinessDaysController;
import apis.stock.StockLedgerController;
import apis.stock.StockReportsController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author samue
 */
public class Apis extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/apis/stock/MainWindow.fxml"));
//
//        Scene scene = new Scene(root);
//
//        stage.setScene(scene);
//        stage.show();
        loadui();
    }

    void loadui() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(
                    "/Swifts/BusinessDays.fxml"
            ));
            loader.setController(new BusinessDaysController());
            Parent parent = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            
           
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Apis.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
