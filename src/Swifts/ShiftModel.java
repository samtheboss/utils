package Swifts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ShiftModel {
  Long shift_number;
  Long business_day;
  String shift_type;
  String location;
  String description;
  String started_by;
  Timestamp start_date;
  String ended_by;
  Timestamp end_date;
  String status;

  public ShiftModel(Long business_day, String shift_type, String location, String description, String started_by, Timestamp start_date, String ended_by, Timestamp end_date, String status) {
    this.business_day = business_day;
    this.shift_type = shift_type;
    this.location = location;
    this.description = description;
    this.started_by = started_by;
    this.start_date = start_date;
    this.ended_by = ended_by;
    this.end_date = end_date;
    this.status = status;
  }

  public ShiftModel() {}

  public Long getShift_number() {
    return shift_number;
  }

  public void setShift_number(long shift_number) {
    this.shift_number = shift_number;
  }

  public long getBusiness_day() {
    return business_day;
  }

  public void setBusiness_day(long business_day) {
    this.business_day = business_day;
  }

  public String getShift_type() {
    return shift_type;
  }

  public void setShift_type(String shift_type) {
    this.shift_type = shift_type;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStarted_by() {
    return started_by;
  }

  public void setStarted_by(String started_by) {
    this.started_by = started_by;
  }

  public Timestamp getStart_date() {
    return start_date;
  }

  public void setStart_date(Timestamp start_date) {
    this.start_date = start_date;
  }

  public String getEnded_by() {
    return ended_by;
  }

  public void setEnded_by(String ended_by) {
    this.ended_by = ended_by;
  }

  public Timestamp getEnd_date() {
    return end_date;
  }

  public void setEnd_date(Timestamp end_date) {
    this.end_date = end_date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

//  public static void addToShifts(ShiftModel model, Connection con) {
//    utills utill = new utills();
//
//    if (model != null && con != null) {
//      String addSQL =
//          "INSERT INTO SHIFTS (BUSINESS_DAY, SHIFT_TYPE, LOCATION, DESCRIPTION, STARTED_BY, START_DATE, ENDED_BY, END_DATE, STATUS) "
//              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//      try (PreparedStatement statement = con.prepareStatement(addSQL)) {
//        statement.setLong(1, model.getBusiness_day());
//        statement.setString(2, utill.safeSQL(model.getShift_type()));
//        statement.setString(3, utill.safeSQL(model.getLocation()));
//        statement.setString(4, utill.safeSQL(model.getDescription()));
//        statement.setString(5, utill.safeSQL(model.getStarted_by()));
//        statement.setTimestamp(6, model.getStart_date());
//        statement.setString(7, utill.safeSQL(model.getEnded_by()));
//        statement.setTimestamp(8, model.getEnd_date());
//        statement.setString(9, utill.safeSQL(model.getStatus()));
//
//        // Execute the prepared statement
//        statement.executeUpdate();
//      } catch (SQLException e) {
//        // Handle any exceptions appropriately
//        e.printStackTrace();
//      }
//    }
//  }

  public static ObservableList<ShiftModel> getALLShifts(Connection con, String status) {
    ObservableList<ShiftModel> values = FXCollections.observableArrayList();
    String sql;
    if ("ALL".equals(status)) {
      sql = "SELECT * FROM SHIFTS ";
    } else {
      sql = "SELECT * FROM SHIFTS WHERE STATUS ='" + status + "'";
    }

    try (PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

      while (rs.next()) {
        ShiftModel val = new ShiftModel();
        val.setBusiness_day(rs.getLong("BUSINESS_DAY"));
        val.setShift_number(rs.getLong("shift_number"));
        val.setDescription(rs.getString("DESCRIPTION"));
        val.setLocation(rs.getString("LOCATION"));
        val.setStarted_by(rs.getString("STARTED_BY"));
        val.setStart_date(rs.getTimestamp("START_DATE"));
        val.setEnded_by(rs.getString("ENDED_BY"));
        val.setEnd_date(rs.getTimestamp("END_DATE"));
        val.setStatus(rs.getString("STATUS"));
        val.setShift_type(rs.getString("shift_type"));

        values.add(val);
      }
    } catch (SQLException e) {
      e.printStackTrace();

    }
    return values;
  }
}
