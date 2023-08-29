/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author smartApps
 */
public class DBConnection {

    public Connection conn;

    String url, user, pass, database;

//    public Connection connection() {
//
//        url = "jdbc:db2://localhost:50000/lubes";
//        user = "maliplus";
//        pass = "Boss@3318";
//        try {
//            Class.forName("com.ibm.db2.jcc.DB2Driver");
//            conn = DriverManager.getConnection(url, user, pass);
//            System.out.println("Connection successful");
//            return conn;
//        } catch (ClassNotFoundException | SQLException ex) {
////        ProgressDialog pd = new ProgressDialog();
//            //   pd.close();
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//
//    }

    public Connection getConnection() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    return conn;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        url = "jdbc:db2://localhost:50000/lubes";
        user = "maliplus";
        pass = "Boss@3318";

        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection successful");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Object singleValue(String sql) {
        Object name = "";
        Connection dbCon = new DBConnection().getConnection();
        try {
            PreparedStatement pst = dbCon.prepareStatement(sql);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                name = result.getObject(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    public Map<String, Object> multpleVLues(String sql) {
        Map<String, Object> res = new HashMap<>();
        try {
            if (conn == null) {
                conn = new DBConnection().getConnection();
            }
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    String colName = rsmd.getColumnLabel(i).toUpperCase();
                    Object val = rs.getObject(colName);
                    res.put(colName, val);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public List<Map<String, Object>> multipleValues(String sql) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            if (conn == null) {
                conn = new DBConnection().getConnection();
            }

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> rowMap = new HashMap<>();

                for (int i = 1; i <= cols; i++) {
                    String colName = rsmd.getColumnLabel(i).toUpperCase();
                    Object val = rs.getObject(i);
                    rowMap.put(colName, val);
                }

                resultList.add(rowMap);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultList;
    }
    public List<Object> listOfData(String sql) {
        List<Object> resultList = new ArrayList<>();
        try (Connection con = new DBConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                List<Object> rowList = new ArrayList<>();
                for (int i = 1; i <= cols; i++) {
                    Object val = rs.getObject(i);
                    rowList.add(val);
                }
                resultList.add(rowList);
            }
        } catch (SQLException ex) {
            // Handle the exception appropriately, such as logging or throwing
            ex.printStackTrace();
        }
        return resultList;
    }

    public static void updateData(String sql) {

        try {
            Connection connection = new DBConnection().getConnection();
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(sql);
            System.out.println("Update successful. " + rowsAffected + " rows affected.");
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage()); 
        }

    }
    public static void updateData(String sql, Object... params) {
        try {
            Connection connection = new DBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update successful. " + rowsAffected + " rows affected.");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
