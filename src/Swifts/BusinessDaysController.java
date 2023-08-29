/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swifts;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tornadofx.control.DateTimePicker;
import apis.stock.DBConnection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
// 0003.12.02

/**
 * @author samue
 */
public class BusinessDaysController implements Initializable {

  DBConnection dbUtils = new DBConnection();
  utils utill = new utils();
  Connection conn;
  String username = "SMARTAPPS";

  @FXML private ComboBox<String> ch_locations;

  @FXML private DateTimePicker startdate;

  @FXML private DateTimePicker enddate;

  @FXML private JFXButton btn_createshift;

  @FXML private JFXButton btn_close_shift;

  @FXML private TableView<BusinessModel> table;

  @FXML private TableColumn<BusinessModel, String> tc_serial_number;

  @FXML private TableColumn<BusinessModel, String> tc_fiscal_day;

  @FXML private TableColumn<BusinessModel, String> tc_fiscal_month;

  @FXML private TableColumn<BusinessModel, String> tc_started_by;

  @FXML private TableColumn<BusinessModel, String> tc_start_date;

  @FXML private TableColumn<BusinessModel, String> tc_closed_by;

  @FXML private TableColumn<BusinessModel, String> tb_close_date;

  @FXML private TableColumn<BusinessModel, String> tc_active_shifts;

  @FXML private TableColumn<BusinessModel, String> tc_status;

  @FXML private JFXCheckBox ch_opened;

  @FXML private JFXCheckBox chk_closed;

  @FXML private JFXTextField txt_filter;

  @FXML private JFXButton btn_search;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initTable();
    setListeners();
  }

  public void checkOpenOrders(String tables) {
    List<utils.WhereBy> wheres = new ArrayList<>();
    wheres.add(new utils.WhereBy("ORDER_STATUS", "ACT"));
    String openCash_orders = utils.generateSelectSQL(wheres, tables, "COUNT");
    int orderCount = (int) dbUtils.singleValue(openCash_orders);
    if (orderCount > 0) {
      utill.Notification("Failed", "You have " + orderCount + " open orders ", "error");
    } else {
      closeDay();
    }
  }

  public void addBusinessDay() {
    BusinessModel model = new BusinessModel();
    int fiscalDate = startdate.getDateTimeValue().getDayOfMonth();
    int fiscalMonth = startdate.getDateTimeValue().getMonth().getValue();
    int fiscalYear = startdate.getDateTimeValue().getYear();
    LocalDateTime localDateTime = startdate.getDateTimeValue();
    String status = "OPN";
    String location = "";
    if (ch_locations.getSelectionModel().getSelectedItem() != null) {
      location = ch_locations.getSelectionModel().getSelectedItem();
    } else {
      utill.Notification("Failed", "Location is Required", "error");
    }
    model.setFiscalDay(fiscalDate);
    model.setFiscalMonth(fiscalMonth);
    model.setFiscal_Year(fiscalYear);
    model.setStarted_By(username);
    // model.setDate_Started(businesStartTime);
    model.setStatus(status);
    model.setLocation(location);
    model.setDescription("test");
    String insertSql = "";
    insertSql = utils.generateInsertSQL(model, "BUSINESS_DAYS");
    utils.systemOut(insertSql);
    List<utils.WhereBy> wheres = new ArrayList<>();
    wheres.add(new utils.WhereBy("status", "OPN"));
    String openBusinessDays = utils.generateSelectSQL(wheres, "BUSINESS_DAYS", "COUNT");
    utils.systemOut(openBusinessDays);
    int count = (int) dbUtils.singleValue(openBusinessDays);
    if (count >= 1) {
      utill.Notification("Failed", "Close Already opened Day to continue", "error");
      initTable();
    } else {
      DBConnection.updateData(insertSql);
      initTable();
      utill.Notification("Success", "Day Created successful", "");
    }
  }

  public void closeDay() {
    try {
      LocalDateTime localDateTime = enddate.getDateTimeValue();
      Timestamp endDateTimestamp = Timestamp.valueOf(localDateTime);

      BusinessModel model = new BusinessModel();
      model.setClosed_By(username);
      model.setStatus("CSD");
      model.setDate_Closed(endDateTimestamp);
      List<utils.WhereBy> updateWheres = new ArrayList<>();
      List<utils.WhereBy> selectWhere = new ArrayList<>();

      updateWheres.add(new utils.WhereBy("STATUS", "OPN"));
      String update = utils.genUpdateSQL(model, updateWheres, "BUSINESS_DAYS");
      String ifExistOpenDAy = utils.generateSelectSQL(updateWheres, "BUSINESS_DAYS", "COUNT");
      int count = (int) dbUtils.singleValue(ifExistOpenDAy);
      if (count >= 1) {

        DBConnection.updateData(update);
        initTable();
        // addBusinessDay();
      } else {
        utill.Notification("Failed", "No Open day", "error");
        initTable();
      }

    } catch (Exception ex) {
      Logger.getLogger(BusinessDaysController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void setCombox() {
    String sql = "SELECT main_location FROM Locations";
    List<Map<String, Object>> dataList = dbUtils.multipleValues(sql);
    List<String> locations =
        dataList.stream()
            .map(rowMap -> (String) rowMap.get("MAIN_LOCATION"))
            .collect(Collectors.toList());
    ch_locations.getItems().addAll(locations);
  }

  public void setListeners() {
    setCombox();

    btn_createshift.setOnAction(
        e -> {
          addBusinessDay();
        });
    btn_close_shift.setOnAction(
        e -> {
          checkOpenOrders("CASH_ORDERS");
        });
    getOpenDatetime();

    txt_filter.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        System.out.println("TextField gained focus");
      } else {
        System.out.println("TextField lost focus");
      }
    });

  }

  void getOpenDatetime() {
    Timestamp openStartTime;
    List<utils.WhereBy> wheres = new ArrayList<>();
    wheres.add(new utils.WhereBy("STATUS", "OPN"));
    String sql = utils.generateSelectSQL(wheres, "BUSINESS_DAYS", "DATE_STARTED");

    String time = String.valueOf(dbUtils.singleValue(sql));
    if (time.isEmpty()) {
      openStartTime = Timestamp.valueOf(LocalDateTime.now());
    } else {
      openStartTime = Timestamp.valueOf(time);
    }
    LocalDateTime startDateTime = openStartTime.toLocalDateTime();
    LocalDateTime eDateTime = LocalDateTime.now();

    enddate.setDateTimeValue(eDateTime);
    startdate.setDateTimeValue(startDateTime);
  }

  public void initTable() {
    conn = new DBConnection().getConnection();
    tc_serial_number.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getBusiness_Day())));
    tc_closed_by.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getClosed_By())));
    tc_fiscal_day.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getFiscalDay())));
    tc_fiscal_month.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getFiscalMonth())));
    tc_started_by.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getStarted_By())));
    tc_status.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getStatus())));
    tc_start_date.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getDate_Started())));
    tb_close_date.setCellValueFactory(
        d -> new ReadOnlyObjectWrapper<>(String.valueOf(d.getValue().getDate_Started())));

    table.setItems(BusinessModel.getALlDays(conn, "ALL"));
  }
}
