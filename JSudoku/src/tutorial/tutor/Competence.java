/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.tutor;

import jsudoku.JSudokuTable;
import org.eclipse.recommenders.jayes.BayesNet;

/**
 *
 * @author Nicolas
 */
public abstract class Competence {
    int id;
    String description;

    public Competence(int id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public abstract BayesNet creerReseau();
    public abstract boolean verifieStrategie(int m_lastRow, int m_lastCol, JSudokuTable m_table);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
