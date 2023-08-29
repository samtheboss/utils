/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swifts;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

/**
 *
 * @author samue
 */
public class utils {

    public String safeSQL(String str) {

        if (str.isEmpty()) {
            return "";
        } else {
            str = str.replace("'", "''");
            return str;

        }
    }

    public void Notification(String title, String text, String type) {
        Notifications note = Notifications.create().title(title).text(text).position(Pos.CENTER);
        if ("error".equals(type)) {
            note.showError();
        } else {
            note.showInformation();
        }

    }
    public static String genUpdateSQL(Object object, List<WhereBy> clauses, String table)  {
        if (object == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            String col = f.getName();
            Object val = null;
            try {
                val = f.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (Objects.isNull(val)) {
                continue;
            }
            String str = Objects.toString(val);
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(col).append(" = ");
            if (val instanceof String || val instanceof Timestamp) {
                sb.append("'").append(val).append("'");
            } else {
                sb.append(str);
            }
        }

        return "UPDATE " + table + " SET " + sb + whereClauses(clauses);
    }

    public static String generateInsertSQL(Object object, String tableName) {
        StringBuilder columnsBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        Field[] fields = object.getClass().getDeclaredFields();
        boolean isFirst = true;

        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = field.getName();
            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                value = null;
            }

            if (value != null) {
                if (!isFirst) {
                    columnsBuilder.append(", ");
                    valuesBuilder.append(", ");
                }
                columnsBuilder.append(columnName);
                valuesBuilder.append(getFormattedValue(value));
                isFirst = false;
            }
        }

        return "INSERT INTO " + tableName + " (" + columnsBuilder.toString() + ") VALUES (" + valuesBuilder.toString() + ")";
    }

    private static String getFormattedValue(Object value) {
        if (value instanceof String || value instanceof Timestamp) {
            return "'" + value + "'";
        }
        return value.toString();
    }

    private static String escape(String s) {
        return s.replace("'", "''");
    }

    private static String dbArg(Object o) {
        if (o == null) {
            return "NULL";
        }
        if (o instanceof String || o instanceof Timestamp) {
            return "'" + o + "'";
        }
        return Objects.toString(o);
    }

    public enum Equate {
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        LESS_THAN,
        BETWEEN,
        LIKE,
        START_WITH;
    }

    public static void systemOut(Object o) {
        System.out.println(o.toString());
    }

    public static class WhereBy {

        private String column;
        private Object value;
        private Equate equate;

        public WhereBy(String column, Object value) {
            this(column, value, Equate.EQUALS);
        }

        public WhereBy(String column, Object value, Equate equate) {
            this.column = column;
            this.value = value;
            this.equate = equate;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Equate getEquate() {
            return equate;
        }

        public void setEquate(Equate equate) {
            this.equate = equate;
        }
    }
    
    //9415

    public static String generateSelectSQL( List<WhereBy> clauses, String tableName,String... cols) {
        StringBuilder sql = new StringBuilder();

        String columns = String.join(", ", cols);
        sql.append("SELECT ").append(columns).append(" FROM ").append(tableName);

       sql.append(whereClauses(clauses));

        return sql.toString();
    }


    public static StringBuilder whereClauses(List<WhereBy> clauses){
        StringBuilder sql= new StringBuilder("");
        if (clauses != null && !clauses.isEmpty()) {
            sql.append(" WHERE");
            int i = 0;
            for (WhereBy whereBy : clauses) {
                if (i > 0) {
                    sql.append(" AND");
                }
                sql.append(" ").append(whereBy.column);
                Object value = whereBy.value;
                if (null == whereBy.equate) {
                    sql.append(" ").append(" <> ").append(dbArg(value));
                } else {
                    switch (whereBy.equate) {
                        case EQUALS:
                            sql.append(" = ").append(dbArg(value));
                            break;
                        case GREATER_THAN:
                            sql.append(" > ").append(dbArg(value));
                            break;
                        case LESS_THAN:
                            sql.append(" < ").append(dbArg(value));
                            break;
                        case LIKE:
                            sql.append(" LIKE '%").append(escape(Objects.toString(value))).append("%'");
                            break;
                        case BETWEEN:
                            List<Object> l = new ArrayList<>((List<Object>) value);
                            sql.append(" BETWEEN ").append(dbArg(l.get(0))).append(" AND ").append(dbArg(l.get(1)));
                            break;
                        default:
                            sql.append(" ").append(" <> ").append(dbArg(value));
                            break;
                    }
                }
                i++;
            }
        }
        return sql;
    }
}
