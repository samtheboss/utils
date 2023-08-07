/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swifts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.Notifications;

/**
 *
 * @author samue
 */
public class BusinessModel {

    Long business_Day;
    String description;
    String location;
    Integer fiscal_Day;
    Integer fiscal_Month;
    Integer fiscal_Year;
    String started_By;
    Timestamp date_Started;
    String modified_By;
    Timestamp date_Modified;
    String closed_By;
    Timestamp date_Closed;
    String status;
    String day_Type;

    public long getBusiness_Day() {
        return business_Day;
    }

    public void setBusiness_Day(long business_Day) {
        this.business_Day = business_Day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFiscalDay() {
        return fiscal_Day;
    }

    public void setFiscalDay(int fiscalDay) {
        this.fiscal_Day = fiscalDay;
    }

    public int getFiscalMonth() {
        return fiscal_Month;
    }

    public void setFiscalMonth(int fiscalMonth) {
        this.fiscal_Month = fiscalMonth;
    }

    public int getFiscal_Year() {
        return fiscal_Year;
    }

    public void setFiscal_Year(int fiscal_Year) {
        this.fiscal_Year = fiscal_Year;
    }

    public String getStarted_By() {
        return started_By;
    }

    public void setStarted_By(String started_By) {
        this.started_By = started_By;
    }

    public Timestamp getDate_Started() {
        return date_Started;
    }

    public void setDate_Started(Timestamp date_Started) {
        this.date_Started = date_Started;
    }

    public String getModified_By() {

        return modified_By == null ? "" : modified_By;
    }

    public void setModified_By(String modified_By) {
        this.modified_By = modified_By;
    }

    public Timestamp getDate_Modified() {
        return date_Modified;
    }

    public void setDate_Modified(Timestamp date_Modified) {
        this.date_Modified = date_Modified;
    }

    public String getClosed_By() {
        return closed_By == null ? "" : closed_By;
    }

    public void setClosed_By(String closed_By) {
        this.closed_By = closed_By;
    }

    public Timestamp getDate_Closed() {
        return date_Closed;
    }

    public void setDate_Closed(Timestamp date_Closed) {
        this.date_Closed = date_Closed;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDay_Type() {
        return day_Type == null ? "" : day_Type;
    }

    public void setDay_Type(String day_Type) {
        this.day_Type = day_Type;
    }

    public static void addToBusinessDays(BusinessModel val, Connection con) {
        utills u = new utills();
        if (val != null && con != null) {
            String addSQL = "INSERT INTO BUSINESS_DAYS (DESCRIPTION, LOCATION, FISCAL_DAY, FISCAL_MONTH, FISCAL_YEAR, STARTED_BY, DATE_STARTED, MODIFIED_BY, DATE_MODIFIED, CLOSED_BY, DATE_CLOSED, STATUS, DAY_TYPE) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(addSQL)) {
                statement.setString(1, u.safeSQL(val.getDescription()));
                statement.setString(2, u.safeSQL(val.getLocation()));
                statement.setInt(3, val.getFiscalDay());
                statement.setInt(4, val.getFiscalMonth());
                statement.setInt(5, val.getFiscal_Year());
                statement.setString(6, u.safeSQL(val.getStarted_By()));
                statement.setTimestamp(7, val.getDate_Started());
                statement.setString(8, u.safeSQL(val.getModified_By()));
                statement.setTimestamp(9, val.getDate_Modified());
                statement.setString(10, u.safeSQL(val.getClosed_By()));
                statement.setTimestamp(11, val.getDate_Closed());
                statement.setString(12, u.safeSQL(val.getStatus()));
                statement.setString(13, u.safeSQL(val.getDay_Type()));              
                statement.executeUpdate();
                Notifications note = Notifications.create().title("Success").text("Business day added Successful");
                note.show();

            } catch (SQLException e) {
                Notifications note = Notifications.create().title("error").text("Failed To Create business day");
                note.show();
               
                e.printStackTrace();
            }
        }
    }

    public static ObservableList<BusinessModel> getALlDays(Connection con, String status) {
        ObservableList<BusinessModel> values = FXCollections.observableArrayList();
        String sql;
        if ("ALL".equals(status)) {
            sql = "SELECT * FROM BUSINESS_DAYS ";
        } else {
            sql = "SELECT * FROM BUSINESS_DAYS WHERE STATUS ='" + status + "'";
        }

        try (PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                BusinessModel val = new BusinessModel();
                val.setBusiness_Day(rs.getLong("BUSINESS_DAY"));
                val.setDescription(rs.getString("DESCRIPTION"));
                val.setLocation(rs.getString("LOCATION"));
                val.setFiscalDay(rs.getInt("FISCAL_DAY"));
                val.setFiscalMonth(rs.getInt("FISCAL_MONTH"));
                val.setFiscal_Year(rs.getInt("FISCAL_YEAR"));
                val.setStarted_By(rs.getString("STARTED_BY"));
                val.setDate_Started(rs.getTimestamp("DATE_STARTED"));
                val.setModified_By(rs.getString("MODIFIED_BY"));
                val.setDate_Modified(rs.getTimestamp("DATE_MODIFIED"));
                val.setClosed_By(rs.getString("CLOSED_BY"));
                val.setDate_Closed(rs.getTimestamp("DATE_CLOSED"));
                val.setStatus(rs.getString("STATUS"));
                val.setDay_Type(rs.getString("DAY_TYPE"));

                values.add(val);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return values;
    }
}
