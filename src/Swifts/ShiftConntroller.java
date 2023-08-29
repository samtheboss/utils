/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swifts;

import apis.stock.DBConnection;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tornadofx.control.DateTimePicker;

/**
 * @author samue
 */
public class ShiftConntroller implements Initializable {
  @FXML private DateTimePicker startdate;

  @FXML private DateTimePicker enddate;

  @FXML private JFXButton btn_createshift;

  @FXML private JFXButton btn_close_shift;

  @FXML private TableView<ShiftModel> table;

  @FXML private TableColumn<ShiftModel, String> tc_shift_number;

  @FXML private TableColumn<ShiftModel, String> tc_started_by;

  @FXML private TableColumn<ShiftModel, String> tc_start_date;

  @FXML private TableColumn<ShiftModel, String> tc_closed_by;

  @FXML private TableColumn<ShiftModel, String> tb_close_date;

  @FXML private TableColumn<ShiftModel, String> tc_status;
  DBConnection dbUtils = new DBConnection();
  utils utils = new utils();
  String username = "smartapps";

  Connection conn;

  public ShiftConntroller() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initTable();
    setListeners();
  }

  void setListeners() {
    btn_createshift.setOnAction(
        e -> {
          addShift();
        });
    btn_close_shift.setOnAction(
        e -> {
          closeShift();
        });
  }

  private void closeShift() {
    String endedBy = username;
    Timestamp endDate = Timestamp.valueOf(LocalDateTime.now());
    String status = "CSD";
    ShiftModel model = new ShiftModel();
    model.setStatus(status);
    model.setEnd_date(endDate);
    model.setEnded_by(endedBy);
    List<utils.WhereBy> updateWheres = new ArrayList<>();
    updateWheres.add(new utils.WhereBy("STATUS", "OPN"));
    updateWheres.add(new utils.WhereBy("STARTED_BY", username));
    String updateSql = utils.genUpdateSQL(model, updateWheres, "shifts");
    System.out.println(updateSql);
    DBConnection.updateData(updateSql);
    initTable();
  }

  public void addShift() {
    long businessDay = 0;
    String shiftType = "NRM";
    String location = "";
    String description = "";
    String startedBy = username;
    Timestamp startDate = Timestamp.valueOf(LocalDateTime.now());
    String endedBy = username;
    Timestamp endDate = Timestamp.valueOf(LocalDateTime.now());
    String status = "OPN";
    List<utils.WhereBy> wheres = new ArrayList<>();
    wheres.add(new utils.WhereBy("STATUS", "OPN"));
    String getOpenDaySql =
        utils.generateSelectSQL(wheres, "BUSINESS_DAYS", "BUSINESS_DAY", "LOCATION");
    utils.systemOut(getOpenDaySql);
    Map<String, Object> res = dbUtils.multpleVLues(getOpenDaySql);
    if (!res.isEmpty()){
    businessDay = Long.parseLong(Objects.toString(res.get("BUSINESS_DAY")));
    location = Objects.toString(res.get("LOCATION"));}
    else {
      utils.Notification("Failed","Error creating Shift","error");
    }

    ShiftModel model =
        new ShiftModel(
            businessDay,
            shiftType,
            location,
            description,
            startedBy,
            startDate,
            endedBy,
            endDate,
            status);
    String insert = Swifts.utils.generateInsertSQL(model, "SHIFTS");
    DBConnection.updateData(insert);
    initTable();
  }

  public void initTable() {
    conn = new DBConnection().getConnection();
    tc_shift_number.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getShift_number())));
    tc_closed_by.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getEnded_by())));

    tc_started_by.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getStarted_by())));
    tc_status.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getStatus())));
    tc_start_date.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getStart_date())));
    tb_close_date.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getEnd_date())));

    table.setItems(ShiftModel.getALLShifts(conn, "ALL"));
  }
}
