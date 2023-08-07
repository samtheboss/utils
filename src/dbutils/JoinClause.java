/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutils;

import java.util.List;

/**
 *
 * @author samue
 */
public class JoinClause {

    JoinType joinType;
    String tableName;
    String[] tableCols;
    List<String> joinConditions; // List of join conditions

    public JoinClause(JoinType joinType, String tableName, String[] tableCols, List<String> joinConditions) {
        this.joinType = joinType;
        this.tableName = tableName;
        this.tableCols = tableCols;
        this.joinConditions = joinConditions;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public String getTableName() {
        return tableName;
    }

    public String[] getTableCols() {
        return tableCols;
    }

    public List<String> getJoinConditions() {
        return joinConditions;
    }
}
