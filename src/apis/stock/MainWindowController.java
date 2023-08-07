/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis.stock;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author samue
 */
public class MainWindowController implements Initializable {
    @FXML
    private TabPane tabPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addtabs();
       
    }

   private Tab createTab(String tabTitle, String fxmlPath, Object controller) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setController(controller);
        Tab tab = new Tab(tabTitle);
        tab.setContent(loader.load());
        return tab;
    }
    void addtabs(){
       
        try {
          Tab tab1=   createTab("Report View", "/apis/stock/StockReports.fxml",new StockReportsController());
          Tab tab2=   createTab("Stock Take", "/apis/stock/StockLedger.fxml",new StockLedgerController());

           tabPane.getTabs().addAll(tab1,tab2);
            
        } catch (Exception ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
