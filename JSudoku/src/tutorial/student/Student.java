/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.student;

import org.eclipse.recommenders.jayes.BayesNet;

/**
 *
 * @author Nicolas
 */
public class Student {
    private StrategyLevel level;
    private BayesNet reseau;

    public Student(BayesNet reseau) {
        this.level = StrategyLevel.faible;
        this.reseau = reseau;
    }

    public StrategyLevel getLevel() {
        return level;
    }

    public void setLevel(StrategyLevel level) {
        this.level = level;
    }

    public BayesNet getReseau() {
        return reseau;
    }

    public void setReseau(BayesNet reseau) {
        this.reseau = reseau;
    }
}
