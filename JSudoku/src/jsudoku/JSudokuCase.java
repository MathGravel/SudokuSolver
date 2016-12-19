/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsudoku;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author mathieu
 */
public class JSudokuCase  {
    
    
    public final Map<Integer,Boolean>  tr;
    private int num;
    private boolean check = false;
    private boolean fond = false;
    
    public JSudokuCase(){
        num = 0;
        tr  = new TreeMap<Integer,Boolean>();
        for (int i = 1; i < 10; i++){
            tr.put(i, false);
        }

    }
    
    public JSudokuCase(JSudokuCase j){
        num = j.num;
        tr = new TreeMap<Integer, Boolean>(j.tr);
        check = j.check;
    }
    
    
    public int getNum() {
        return num;
    }
    
    public void setNum(int numb){
        num = numb;
    }
    
    /**
     *
     * @return
     */
    public LinkedList<Boolean> getPossibilities(){
            LinkedList<Boolean> ll = new LinkedList<Boolean>();
            for (boolean b : tr.values()) {
                ll.add(b);
            }
            return ll;
    }
    
    /**
     *
     * @param ll
     */
    public void SetPossibilities(LinkedList<Boolean> ll) {
        int i = 1;
        for (boolean b : ll) {
            tr.put(i, b);
            i++;
        }
        check = true;
    }

    public boolean isFond() {
        return fond;
    }

    public void setFond(boolean fond) {
        this.fond = fond;
    }
    
    public String toString() {
        if (num > 0) 
            return Integer.toString(num);

        else {
            boolean test = false;
            for (Boolean b : tr.values()) {
                test = test || b;
            }
            if (!test)
                return "";
            StringBuilder str = new StringBuilder();
            for (int i = 1; i < 10; i++){

                if (!tr.get(i)) {
                    str.append(" ");
                }
                else {
                    str.append(i);
                }
                if (i % 3 == 0){
                    str.append("\n");
                } else {
                str.append("   ");
                }
            }
            
            return str.toString();
        }
    }
}
