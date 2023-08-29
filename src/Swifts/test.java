package Swifts;

import apis.stock.DBConnection;
import dbutils.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Swifts.SQLQueryBuilder.generateSelectSQL;

public class test {
  private static String columnName;

  public static void main(String[] args) {
    //  Get the runtime instance
    //        Runtime runtime = Runtime.getRuntime();
    //
    //        // Get the max memory (heap size) in bytes
    //        long maxMemory = runtime.maxMemory();
    //
    //        // Convert bytes to megabytes
    //        long maxMemoryInMB = maxMemory / (1024 * 1024);
    //
    //        System.out.println("Max Heap Size: " + maxMemoryInMB + " MB");
    keyvalue();
  }

  public static void mma() {

    try {
      // Current max memory (in bytes)
      long currentMaxMemory = Runtime.getRuntime().maxMemory();
      System.out.println("Current Max Heap Size: " + currentMaxMemory + " bytes");

      // Set the new max memory size (in megabytes)
      long newMaxMemoryInMB = 3072; // 3GB
      long newMaxMemory = newMaxMemoryInMB * 1024 * 1024; // Convert to bytes

      // Create a new Java process with the increased heap size
      ProcessBuilder pb = new ProcessBuilder("java", "-Xmx" + newMaxMemoryInMB + "m");
      System.out.println(pb);
      Process process = pb.start();

      // Wait for the process to finish
      int exitCode = process.waitFor();

      // Get the updated max memory (in bytes)
      long updatedMaxMemory = Runtime.getRuntime().maxMemory();

      // Print the updated max memory size
      System.out.println("Updated Max Heap Size: " + updatedMaxMemory + " bytes");

      // Check the exit code of the process
      if (exitCode == 0) {
        System.out.println("Java process executed successfully.");
      } else {
        System.out.println("Java process encountered an error. Exit code: " + exitCode);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void testsql() {
    List<WhereBy> clauses = new ArrayList<>();
    clauses.add(new WhereBy("employees.age", 25, Equate.EQUALS));

    String mainTableName = "employees";
    String[] mainTableCols = {"id", "name", "age", "salary"};
    String sumColumn = "salary"; // Column to use in the SUM operation
    String countColumn = "age"; // Column to use in the COUNT operation

    List<JoinClause> joinClauses = new ArrayList<>();
    List<String> joinConditions1 = new ArrayList<>();
    joinConditions1.add("employees.department_id = departments.id");
    joinConditions1.add("employees.manager_id = managers.id");
    joinClauses.add(
        new JoinClause(
            JoinType.INNER_JOIN, "departments", new String[] {"department_id"}, joinConditions1));

    List<String> joinConditions2 = new ArrayList<>();
    joinConditions2.add("employees.some_id = other_table.some_id");
    joinClauses.add(
        new JoinClause(
            JoinType.LEFT_OUTER_JOIN, "other_table", new String[] {"some_id"}, joinConditions2));

    // Example: SELECT SUM(salary) AS result, employees.id, employees.name, employees.age,
    // employees.salary
    //          FROM employees
    //               INNER JOIN departments ON employees.department_id = departments.id
    //                                   AND employees.manager_id = managers.id
    //               LEFT OUTER JOIN other_table ON employees.some_id = other_table.some_id
    //          WHERE employees.age = 25
    String sqlQueryWithMultipleJoinConditions =
        generateSelectSQL(
            clauses,
            mainTableName,
            SelectOperation.SUM,
            mainTableCols,
            sumColumn,
            countColumn,
            joinClauses);
    System.out.println(sqlQueryWithMultipleJoinConditions);
  }

  static void testmaps() {
    String sql = "SELECT * FROM cash_orders"; // Replace with your SQL query

    DBConnection dbConnection = new DBConnection(); // Assuming you have a DBConnection class
    List<String> strings = new ArrayList<>();
    List<Map<String, Object>> dataList = dbConnection.multipleValues(sql);

    for (Map<String, Object> rowMap : dataList) {
      for (Map.Entry<String, Object> entry : rowMap.entrySet()) {
        String columnName = entry.getKey();
        Object columnValue = entry.getValue();
        strings.add(columnName);
        System.out.println(columnName + ": " + columnValue);
      }

      System.out.println("-----------");
    }
  }

  public static void keyvalue() {
    String sql = "SELECT * FROM cash_orders"; // Replace with your SQL query
    DBConnection dbConnection = new DBConnection(); // Assuming you have a DBConnection class
    List<Map<String, Object>> dataList = dbConnection.multipleValues(sql);
    for (Map<String, Object> rowMap : dataList) {
      String id = (String) rowMap.get("DESCRIPTION");
      System.out.println("-----------");
    }

  }
}
