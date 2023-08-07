package Swifts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class shiftModel {
  long shiftNumber;
  long businessDay;
  String shiftType;
  String location;
  String description;
  String startedBy;
  Timestamp startDate;
  String endedBy;
  Timestamp endDate;
  String status;

  public shiftModel() {}

  public long getShiftNumber() {
    return shiftNumber;
  }

  public void setShiftNumber(long shiftNumber) {
    this.shiftNumber = shiftNumber;
  }

  public long getBusinessDay() {
    return businessDay;
  }

  public void setBusinessDay(long businessDay) {
    this.businessDay = businessDay;
  }

  public String getShiftType() {
    return shiftType;
  }

  public void setShiftType(String shiftType) {
    this.shiftType = shiftType;
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

  public String getStartedBy() {
    return startedBy;
  }

  public void setStartedBy(String startedBy) {
    this.startedBy = startedBy;
  }

  public Timestamp getStartDate() {
    return startDate;
  }

  public void setStartDate(Timestamp startDate) {
    this.startDate = startDate;
  }

  public String getEndedBy() {
    return endedBy;
  }

  public void setEndedBy(String endedBy) {
    this.endedBy = endedBy;
  }

  public Timestamp getEndDate() {
    return endDate;
  }

  public void setEndDate(Timestamp endDate) {
    this.endDate = endDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public static void addToShifts(shiftModel model, Connection con) {
    utills utill = new utills();

    if (model != null && con != null) {
      String addSQL =
          "INSERT INTO SHIFTS (BUSINESS_DAY, SHIFT_TYPE, LOCATION, DESCRIPTION, STARTED_BY, START_DATE, ENDED_BY, END_DATE, STATUS) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try (PreparedStatement statement = con.prepareStatement(addSQL)) {
        statement.setLong(1, model.getBusinessDay());
        statement.setString(2, utill.safeSQL(model.getShiftType()));
        statement.setString(3, utill.safeSQL(model.getLocation()));
        statement.setString(4, utill.safeSQL(model.getDescription()));
        statement.setString(5, utill.safeSQL(model.getStartedBy()));
        statement.setTimestamp(6, model.getStartDate());
        statement.setString(7, utill.safeSQL(model.getEndedBy()));
        statement.setTimestamp(8, model.getEndDate());
        statement.setString(9, utill.safeSQL(model.getStatus()));

        // Execute the prepared statement
        statement.executeUpdate();
      } catch (SQLException e) {
        // Handle any exceptions appropriately
        e.printStackTrace();
      }
    }
  }
}
