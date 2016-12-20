/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.tutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jsudoku.JSudokuTable;
import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;

/**
 *
 * @author Nicolas
 */
public class Square extends Competence {
    Map<Integer, BayesNode> coupReseau;
    Map<Integer, BayesNode> descriptionReseau;
    Map<BayesNode,String> evidence = new HashMap<BayesNode,String>();
    BayesNode square = null;
    
    public Square(JSudokuTable m_table) {
        super(1, "Strategie carre");
        creerReseau(m_table);
        inferer.setNetwork(reseau);
    }
    
    @Override
    public double[] verifieStrategie(int m_lastRow, int m_lastCol, JSudokuTable m_table){
        coupOk(m_lastRow, m_lastCol, m_table);
        descriptionOk();
        bonCarre(m_lastRow, m_lastCol);
        inferer.setEvidence(evidence);
        return inferer.getBeliefs(square);
    }
    
    public void coupOk(int m_lastRow, int m_lastCol, JSudokuTable m_table){
        if(m_table.m_data[m_lastRow][m_lastCol] == m_table.m_solution[m_lastRow][m_lastCol]){
            evidence.put(coupReseau.get(m_lastCol*9 + m_lastCol), "true");
            evidence.put(descriptionReseau.get(m_lastCol*9 + m_lastCol), "true");
            
            for(int node : coupReseau.keySet()){
                int reste = node%9;
                if(m_table.m_data[(node - reste)/9][reste] != m_table.m_solution[(node - reste)/9][reste]){
                    return;
                }
            }
            evidence.put(coupReseau.get(coupReseau.keySet().iterator().next()).getParents().get(0), "true");
        }else{
            evidence.put(coupReseau.get(m_lastCol*9 + m_lastCol), "false");
            evidence.put(descriptionReseau.get(m_lastCol*9 + m_lastCol), "false");
        }
    }
    
    public void descriptionOk(){
        
    }
    
    public void bonCarre(int m_lastRow, int m_lastCol){
        int rowCarre = (m_lastRow/3)*3;
        int colCarre = (m_lastCol/3)*3;
        
        
    }

    @Override
    public void creerReseau(JSudokuTable m_table) {
        reseau = new BayesNet();
        square = reseau.createNode("strategie carre");
        square.addOutcomes("true", "false");
        square.setProbabilities(0.5, 0.5);
        List<BayesNode> squareList = Arrays.asList(square);
        
        BayesNode coupOk = reseau.createNode("coup reussi");
        coupOk.addOutcomes("true", "false");
        coupOk.setParents(squareList);
        coupOk.setProbabilities(0.25, 0.25, 0.25, 0.25);
        List<BayesNode> coupList = Arrays.asList(coupOk);
        
        BayesNode descriptionOk = reseau.createNode("description ok");
        descriptionOk.addOutcomes("true", "false");
        descriptionOk.setParents(squareList);
        descriptionOk.setProbabilities(0.25, 0.25, 0.25, 0.25);
        List<BayesNode> descriptionList = Arrays.asList(descriptionOk);
        
        BayesNode carreOk = reseau.createNode("joue dans le bon carre");
        carreOk.addOutcomes("true", "false");
        carreOk.setParents(squareList);
        carreOk.setProbabilities(0.25, 0.25, 0.25, 0.25);
        
        coupReseau = new HashMap<Integer, BayesNode>();
        descriptionReseau = new HashMap<Integer, BayesNode>();
        for(int i = 0; i < m_table.m_data.length;i++){
            for(int j = 0; j < m_table.m_data[i].length;j++){
                if(m_table.m_data[i][j].getNum() == 0){
                    int idNode = i*9 + j;
                    
                    BayesNode coupNode = reseau.createNode("coup num " + idNode);
                    coupNode.addOutcomes("true", "false");
                    coupNode.setParents(coupList);
                    coupNode.setProbabilities(0.25, 0.25, 0.25, 0.25);
                    coupReseau.put(i*3 + j, coupNode);
                    
                    BayesNode descriptionNode = reseau.createNode("description num " + idNode);
                    descriptionNode.addOutcomes("true", "false");
                    descriptionNode.setParents(descriptionList);
                    descriptionNode.setProbabilities(0.25, 0.25, 0.25, 0.25);
                    descriptionReseau.put(i*3 + j, descriptionNode);
                }
            }
        }
    }
}
