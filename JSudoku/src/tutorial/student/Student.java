/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.student;

import jsudoku.JSudokuTable;
import tutorial.tutor.Competence;

/**
 *
 * @author Nicolas
 */
public class Student {
    private StrategyLevel level;
    private Competence competence;

    public Student(Competence competence) {
        this.level = StrategyLevel.faible;
        this.competence = competence;
    }

    public StrategyLevel getLevel() {
        return level;
    }

    public void setLevel(StrategyLevel level) {
        this.level = level;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }
    
    /**
     * Vérifie la strategie et obtient une inference pour modifier le niveau du joueur
     * @param m_lastRow ligne joue
     * @param m_lastCol colonne joue
     * @param m_table table de jeu
     */
    public void inferer(int m_lastRow, int m_lastCol, JSudokuTable m_table){
        if(m_lastRow != -1 || m_lastCol != -1){
            double[] infere = competence.verifieStrategie(0, 0, m_table);
            //JESS POUR CHANGER LE LEVEL
        }
    }
}
