/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swifts;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tornadofx.control.DateTimePicker;

/**
 *
 * @author samue
 */
public class ShiftConntroller implements Initializable{
       @FXML
    private DateTimePicker startdate;

    @FXML
    private DateTimePicker enddate;

    @FXML
    private JFXButton btn_createshift;

    @FXML
    private JFXButton btn_close_shift;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> tc_shift_number;

    @FXML
    private TableColumn<?, ?> tc_started_by;

    @FXML
    private TableColumn<?, ?> tc_start_date;

    @FXML
    private TableColumn<?, ?> tc_closed_by;

    @FXML
    private TableColumn<?, ?> tb_close_date;

    @FXML
    private TableColumn<?, ?> tc_status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
