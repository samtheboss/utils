package apis.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockTakingDataHandler {

    private static final Logger LOG = Logger.getLogger(StockTakingDataHandler.class.getName());
    private static Connection conn = new DBConnection().connection();
    String query = "";
    int i = 1;

    public StockTakingDataHandler() {

    }

    public static List<StockLedger> loadData(String trns, double orderNumber,
            String location, String audited, Timestamp startDate, Timestamp endDate, int page, String searchText) {
        String status;
        int offset = (page <= 1) ? 0 : (page - 1) * 50;
        String sql = "SELECT "
                + "SERIAL_NO AS serialNo, "
                + "ORDER_TYPE AS orderType, "
                + "ORDER_NO AS orderNo, "
                + "JOURNAL_TYPE AS journalType, "
                + "JOURNAL_NUMBER AS journalNumber, "
                + "TRN_TYPE AS trnType, "
                + "ORDER_QTY AS orderQty, "
                + "CREATED_BY AS createdBy, "
                + "DATE_CREATED AS dateCreated, "
                + "DATE_MODIFIED AS dateModified, "
                + "APPROVED_BY AS approvedBy, "
                + "DATE_APPROVED AS dateApproved, "
                + "ITEM_CODE AS itemCode, "
                + "DESCRIPTION AS description, "
                + "SUOM AS suom, "
                + "CONFIRMED AS confirmed, "
                + "CONFIRMED_ORDER_NUMBER AS confirmedOrderNo, "
                + "CONFIRMED_SERIAL_NUMBER AS confirmedSerialNo, "
                + "CONFIRMED_QUANTITY AS confirmedQty, "
                + "CONFIRMED_APPROVAL_DATE AS confirmedApprovalDate, "
                + "CONFIRMED_APPROVED_BY AS confirmedapprovedBy, "
                + "DEVICE_ID AS deviceId, "
                + "PACK_SIZE AS packSize, "
                + "ITEM_TYPE AS itemTYpe, "
                + "EXPIRY_WARRANTY_DATE AS expiryDate, "
                + "LOT_SERIAL AS lotSerial, "
                + "ITEM_LOCATION AS itemLocation, "
                + "BARCODE AS barcode "
                + "FROM STOCK_ANALYSIS_REPORTS ";

        audited = audited.equalsIgnoreCase("All") ? "'X', 'Y'" : "'" + audited + "'";
        sql += "WHERE CONFIRMED IN (" + audited + ") ";

        if (trns != null) {
            sql += "AND TRN_TYPE IN (" + trns + ") ";
        }

        if (orderNumber >= 0) {
            sql += "AND ORDER_NO= " + orderNumber + " ";
        }

        if (startDate != null) {
            sql += " and DATE_APPROVED>='" + startDate.toString() + "' ";
        }
        if (endDate != null) {
            sql += " and DATE_APPROVED<='" + endDate.toString() + "' ";
        }
        if (location != null && !"ALL LOCATIONS".equals(location)) {
            sql += " and ITEM_LOCATION='" + location + "' ";
        }

        if (searchText != null) {
            sql += "AND (UPPER(ORDER_TYPE) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(APPROVED_BY) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(ITEM_LOCATION) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(SUOM) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(CONFIRMED_APPROVED_BY) LIKE UPPER('%" + searchText + "%')) ";
        }

        sql += "LIMIT 50 OFFSET " + offset;

        List<StockLedger> data = new ArrayList<>();

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                double serialNo = rs.getDouble("serialNo");
                String orderType = rs.getString("orderType");
                double orderNo = rs.getDouble("orderNo");
                String journalType = rs.getString("journalType");
                double journalNumber = rs.getDouble("journalNumber");
                String trnType = rs.getString("trnType");
                double orderQty = rs.getDouble("orderQty");
                String createdBy = rs.getString("createdBy");
                Date dateCreated = rs.getDate("dateCreated");
                Date dateModified = rs.getDate("dateModified");
                String approvedBy = rs.getString("approvedBy");
                Date dateApproved = rs.getDate("dateApproved");
                String itemCode = rs.getString("itemCode");
                String description = rs.getString("description");
                String suom = rs.getString("suom");
                String confirmed = rs.getString("confirmed");
                double confirmedOrderNo = rs.getDouble("confirmedOrderNo");
                double confirmedSerialNo = rs.getDouble("confirmedSerialNo");
                double confirmedQty = rs.getDouble("confirmedQty");
                Date confirmedApprovalDate = rs.getDate("confirmedApprovalDate");
                String confirmedapprovedBy = rs.getString("confirmedapprovedBy");
                String deviceId = rs.getString("deviceId");
                double packSize = rs.getDouble("packSize");
                String itemTYpe = rs.getString("itemTYpe");
                Date expiryDate = rs.getDate("expiryDate");
                String lotSerial = rs.getString("lotSerial");
                String itemLocation = rs.getString("itemLocation");
                String barcode = rs.getString("barcode");

                StockLedger stockLedger = new StockLedger(serialNo, orderType, orderNo, journalType,
                        journalNumber, trnType, orderQty, createdBy, dateCreated, dateModified, approvedBy,
                        dateApproved, itemCode, description, suom, confirmed, confirmedOrderNo, confirmedSerialNo,
                        confirmedQty, confirmedApprovalDate, confirmedapprovedBy, deviceId, packSize, itemTYpe,
                        expiryDate, lotSerial, itemLocation, barcode);
                data.add(stockLedger);
            }

        } catch (SQLException ex) {
            Logger.getLogger(StockTakingDataHandler.class.getName()).log(Level.SEVERE, null, ex);
            data = new ArrayList<>();
        }

        return data;
    }

    public static ResultSet loadData(String trns, double orderNumber, String location, String audited,
            Timestamp startDate, Timestamp endDate, int page, String searchText, String module) {
        String status;
        int offset = (page <= 1) ? 0 : (page - 1) * 50;
        String sql = "SELECT " + "SERIAL_NO AS serialNo, " + "ORDER_TYPE AS orderType, " + "ORDER_NO AS orderNo, "
                + "JOURNAL_TYPE AS journalType, " + "JOURNAL_NUMBER AS journalNumber, " + "TRN_TYPE AS trnType, "
                + "ORDER_QTY AS orderQty, " + "CREATED_BY AS createdBy, " + "DATE_CREATED AS dateCreated, "
                + "DATE_MODIFIED AS dateModified, " + "APPROVED_BY AS approvedBy, " + "DATE_APPROVED AS dateApproved, "
                + "ITEM_CODE AS itemCode, " + "DESCRIPTION AS description, " + "SUOM AS suom, "
                + "CONFIRMED AS confirmed, " + "CONFIRMED_ORDER_NUMBER AS confirmedOrderNo, "
                + "CONFIRMED_SERIAL_NUMBER AS confirmedSerialNo, " + "CONFIRMED_QUANTITY AS confirmedQty, "
                + "CONFIRMED_APPROVAL_DATE AS confirmedApprovalDate, "
                + "CONFIRMED_APPROVED_BY AS confirmedapprovedBy, " + "DEVICE_ID AS deviceId, "
                + "PACK_SIZE AS packSize, " + "ITEM_TYPE AS itemTYpe, " + "EXPIRY_WARRANTY_DATE AS expiryDate, "
                + "LOT_SERIAL AS lotSerial, " + "ITEM_LOCATION AS itemLocation, " + "BARCODE AS barcode "
                + "FROM STOCK_ANALYSIS_REPORTS ";

        audited = audited.equalsIgnoreCase("All") ? "'X', 'Y'" : "'" + audited + "'";
        sql += "WHERE CONFIRMED IN (" + audited + ") ";

        if (trns != null) {
            sql += "AND TRN_TYPE IN (" + trns + ") ";
        }

        if (orderNumber >= 0) {
            if (module.equals("SALES") || module.equals("PURCHASES")) {
                sql += "AND ORDER_NO= " + orderNumber + " ";
            } else {
                sql += "AND journal_number= " + orderNumber + " ";
            }

        }

        if (startDate != null) {
            sql += " and DATE_APPROVED>='" + startDate.toString() + "' ";
        }
        if (endDate != null) {
            sql += " and DATE_APPROVED<='" + endDate.toString() + "' ";
        }
        if (location != null && !"ALL LOCATIONS".equals(location)) {
            sql += " and ITEM_LOCATION='" + location + "' ";
        }

        if (searchText != null) {
            sql += "AND (UPPER(ORDER_TYPE) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(APPROVED_BY) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(ITEM_LOCATION) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(SUOM) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(CONFIRMED_APPROVED_BY) LIKE UPPER('%" + searchText + "%')) ";
        }

        sql += "LIMIT 50 OFFSET " + offset;

        System.out.println("sql" + sql);

        List<StockLedger> data = new ArrayList<>();

        PreparedStatement pst = null;
        try {
            conn = new DBConnection().connection();
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(StockTakingDataHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void saveStocks(List<StockLedger> stocks) {
        query = "INSERT INTO STOCK_ANALYSIS (SERIAL_NO, APPROVED_BY, CONFIRMED, "
                + "CONFIRMED_APPROVAL_DATE, CONFIRMED_ORDER_NUMBER, CONFIRMED_QUANTITY, CONFIRMED_SERIAL_NUMBER, "
                + "CONFIRMED_APPROVED_BY, CREATED_BY, DATE_APPROVED, DATE_CREATED, DATE_MODIFIED, DESCRIPTION, "
                + "DEVICE_ID, ITEM_CODE, ORDER_NO, ORDER_QTY, ORDER_TYPE, SUOM, TRN_TYPE, JOURNAL_TYPE, "
                + "JOURNAL_NUMBER, BARCODE) "
                + "	VALUES";

        i = 1;
        System.out.println("stock model" + stocks.toString());
        stocks.forEach(stock -> {
            if (i > 1) {
                query += ",";
            }

            query += "(" + stock.getSerialNo() + ", "
                    + "'" + stock.getApprovedBy() + "', "
                    + "'" + stock.getConfirmed() + "', "
                    + "'" + new Timestamp(stock.getConfirmedApprovalDate().getTime()) + "', "
                    + stock.getConfirmedOrderNo() + ", "
                    + stock.getConfirmedQty() + ", "
                    + stock.getConfirmedSerialNo() + ", "
                    + "'" + stock.getConfirmedapprovedBy() + "', "
                    + "'" + stock.getCreatedBy() + "', "
                    + "'" + new Timestamp(stock.getDateApproved().getTime()) + "', "
                    + "'" + new Timestamp(stock.getDateCreated().getTime()) + "', "
                    + "'" + new Timestamp(stock.getDateModified().getTime()) + "', "
                    + "'" + stock.getDescription() + "', "
                    + "'" + stock.getDeviceId() + "', "
                    + "'" + stock.getItemCode() + "', "
                    + stock.getOrderNo() + ", "
                    + stock.getOrderQty() + ", "
                    + "'" + stock.getOrderType() + "', "
                    + "'" + stock.getSuom() + "', "
                    + "'" + stock.getTrnType() + "', "
                    + "'" + stock.getJournalType() + "', "
                    + stock.getJournalNumber() + ", "
                    + "'" + stock.getBarcode() + "') ";
            System.out.println("SQL" + stock.getAlreadyConfirmed());
            i++;
        });

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = conn;
            ps = connection.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getLocations() {
        String query = "SELECT DISTINCT ITEM_LOCATION FROM STOCK_ANALYSIS_REPORTS";
        List<String> locations = new ArrayList<>();

        PreparedStatement pst = null;
        try {

            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                locations.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;
    }

    public static LocalDate getEarliestCreationDate() {
        String query = "SELECT DATE_CREATED AS dateCreated FROM STOCK_ANALYSIS_REPORTS ORDER BY DATE_CREATED ASC LIMIT 1";
        Date dateCreated = new Date();

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dateCreated = rs.getDate("dateCreated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Instant.ofEpochMilli(dateCreated.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Long getDataCount(String trns, double orderNumber, String location, String audited,
            Timestamp startDate, Timestamp endDate, int page, String searchText) {
        String status;
        int offset = (page <= 1) ? 0 : (page - 1) * 50;
        String sql = "select count(*) as num from (SELECT " + "SERIAL_NO AS serialNo, " + "ORDER_TYPE AS orderType, "
                + "ORDER_NO AS orderNo, " + "JOURNAL_TYPE AS journalType, " + "JOURNAL_NUMBER AS journalNumber, "
                + "TRN_TYPE AS trnType, " + "ORDER_QTY AS orderQty, " + "CREATED_BY AS createdBy, "
                + "DATE_CREATED AS dateCreated, " + "DATE_MODIFIED AS dateModified, " + "APPROVED_BY AS approvedBy, "
                + "DATE_APPROVED AS dateApproved, " + "ITEM_CODE AS itemCode, " + "DESCRIPTION AS description, "
                + "SUOM AS suom, " + "CONFIRMED AS confirmed, " + "CONFIRMED_ORDER_NUMBER AS confirmedOrderNo, "
                + "CONFIRMED_SERIAL_NUMBER AS confirmedSerialNo, " + "CONFIRMED_QUANTITY AS confirmedQty, "
                + "CONFIRMED_APPROVAL_DATE AS confirmedApprovalDate, "
                + "CONFIRMED_APPROVED_BY AS confirmedapprovedBy, " + "DEVICE_ID AS deviceId, "
                + "PACK_SIZE AS packSize, " + "ITEM_TYPE AS itemTYpe, " + "EXPIRY_WARRANTY_DATE AS expiryDate, "
                + "LOT_SERIAL AS lotSerial, " + "ITEM_LOCATION AS itemLocation, " + "BARCODE AS barcode "
                + "FROM STOCK_ANALYSIS_REPORTS ";

        audited = audited.equalsIgnoreCase("All") ? "'X', 'Y'" : "'" + audited + "'";
        sql += "WHERE CONFIRMED IN (" + audited + ") ";

        if (trns != null) {
            sql += "AND TRN_TYPE IN (" + trns + ") ";
        }

        if (orderNumber >= 0) {
            sql += "AND ORDER_NO= " + orderNumber + " ";
        }

        if (startDate != null) {
            sql += " and DATE_APPROVED>='" + startDate.toString() + "' ";
        }
        if (endDate != null) {
            sql += " and DATE_APPROVED<='" + endDate.toString() + "' ";
        }
        if (location != null && !"ALL LOCATIONS".equals(location)) {
            sql += " and ITEM_LOCATION='" + location + "' ";
        }

        if (searchText != null) {
            sql += "AND (UPPER(ORDER_TYPE) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(APPROVED_BY) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(ITEM_LOCATION) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(SUOM) LIKE UPPER('%" + searchText + "%') ";
            sql += "OR UPPER(CONFIRMED_APPROVED_BY) LIKE UPPER('%" + searchText + "%')) ";
        }

        sql += "LIMIT 50 OFFSET " + offset + ")";

        Long num = (long) 0;
        try {

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                num = rs.getLong("num");
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
            LOG.log(Level.WARNING, ex.getLocalizedMessage());
            return (long) 0;
        }
        return num;
    }

}
