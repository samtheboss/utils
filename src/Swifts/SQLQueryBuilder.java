package Swifts;

import static dbutils.Equate.BETWEEN;
import static dbutils.Equate.EQUALS;
import static dbutils.JoinType.INNER_JOIN;
import static dbutils.JoinType.LEFT_OUTER_JOIN;
import static dbutils.SelectOperation.COUNT;
import static dbutils.SelectOperation.DISTINCT;
import static dbutils.SelectOperation.SUM;

import dbutils.JoinClause;
import dbutils.SelectOperation;
import dbutils.WhereBy;
import java.util.*;

public class SQLQueryBuilder {

    public static String generateSelectSQL(List<WhereBy> clauses, String mainTableName, SelectOperation operation,
            String[] mainTableCols, String sumColumn, String countColumn, List<JoinClause> joinClauses) {
        StringBuilder sql = new StringBuilder();

        String selectOperation = "";
        switch (operation) {
            case COUNT:
                selectOperation = "COUNT";
                break;
            case SUM:
                selectOperation = "SUM";
                break;
            case DISTINCT:
                selectOperation = "DISTINCT";
                break;
            default:
                selectOperation = "";
                break;
        }

        String mainTableColumns = String.join(", ", mainTableCols);
        sql.append("SELECT ").append(selectOperation);

        if (operation == SelectOperation.SUM) {
            sql.append("(").append(sumColumn).append(")"); // Wrap SUM column in parentheses
        } else if (operation == SelectOperation.COUNT) {
            sql.append("(").append(countColumn).append(")"); // Wrap COUNT column in parentheses
        }

        sql.append(" AS result").append(", ").append(mainTableColumns).append(" FROM ").append(mainTableName);

        if (joinClauses != null && !joinClauses.isEmpty()) {
            for (JoinClause joinClause : joinClauses) {
                switch (joinClause.getJoinType()) {
                    case INNER_JOIN:
                        sql.append(" INNER JOIN ");
                        break;
                    case LEFT_OUTER_JOIN:
                        sql.append(" LEFT OUTER JOIN ");
                        break;
                    // Add more cases for other join types if needed
                    default:
                        break;
                }
                sql.append(joinClause.getTableName());
                sql.append(" ON ");
                List<String> joinConditions = joinClause.getJoinConditions();
                for (int i = 0; i < joinConditions.size(); i++) {
                    if (i > 0) {
                        sql.append(" AND ");
                    }
                    sql.append(joinConditions.get(i));
                }
            }
        }

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
                            return sql.toString();
                        default:
                            sql.append(" ").append(" <> ").append(dbArg(value));
                            break;
                    }
                }
                i++;
            }
        }

        return sql.toString();
    }

    private static String dbArg(Object value) {
        // Assuming you have a method to properly format the value for the database
        return "'" + value + "'";
    }

    private static String escape(String str) {
        // Assuming you have a method to properly escape characters for LIKE clauses
        return str.replaceAll("'", "''");
    }
}
