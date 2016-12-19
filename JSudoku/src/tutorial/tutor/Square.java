/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.tutor;

import java.util.Arrays;
import java.util.List;
import jsudoku.JSudokuTable;
import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;

/**
 *
 * @author Nicolas
 */
public class Square extends Competence {
    
    public Square() {
        super(1, "Stratégie carré");
    }
    
    @Override
    public boolean verifieStratégie(int m_lastRow, int m_lastCol, JSudokuTable m_table){
        if(m_table.m_data[m_lastRow][m_lastCol] != m_table.m_solution[m_lastRow][m_lastCol]){
            return false;
        }
        
        int rowCarre = (m_lastRow/3)*3;
        int colCarre = (m_lastCol/3)*3;
        boolean unManque = false;
        
        int rowVide = 0;
        int colVide = 0;
        
        for(int i = rowCarre; i<(rowCarre+3);i++){
            for(int j = colCarre; j<(colCarre+3);j++){
                if(m_table.m_data[i][j].getNum() == 0){
                    if(rowVide != 0)
                        unManque = false;
                    else{
                        rowVide = i;
                        colVide = j;
                        unManque = true;
                    }
                }
                m_table.m_data[i][j].setFond(false);
            }
        }
        
        if(unManque){
            m_table.m_data[rowVide][colVide].setFond(true);
            return true;
        }
        return false;
    }

    @Override
    public BayesNet creerReseau() {
        BayesNet reseau = new BayesNet();
        BayesNode square = reseau.createNode("stratégie carré");
        square.addOutcomes("true", "false");
        square.setProbabilities(0.5, 0.5);
        List<BayesNode> squareList = Arrays.asList(square);
        
        BayesNode coupOk = reseau.createNode("coup réussi");
        coupOk.addOutcomes("true", "false");
        coupOk.setParents(squareList);
        coupOk.setProbabilities(0.5, 0.5);
        
        BayesNode descriptionOk = reseau.createNode("description ok");
        descriptionOk.addOutcomes("true", "false");
        descriptionOk.setParents(squareList);
        descriptionOk.setProbabilities(0.5, 0.5);
        
        BayesNode carreOk = reseau.createNode("joue dans le bon carré");
        carreOk.addOutcomes("true", "false");
        carreOk.setParents(squareList);
        carreOk.setProbabilities(0.5, 0.5);
        
        return reseau;
    }
}
