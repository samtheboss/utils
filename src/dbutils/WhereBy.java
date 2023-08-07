/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutils;


/**
 *
 * @author samue
 */

public class WhereBy {
    public String column;
    public Object value;
    public Equate equate;

    public WhereBy(String column, Object value, Equate equate) {
        this.column = column;
        this.value = value;
        this.equate = equate;
    }
}
