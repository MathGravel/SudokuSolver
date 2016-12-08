/*
 * JSudokuTable.java
 *
 * Created on 14. Oktober 2007, 22:11
 *
 *  Copyright (C) 14. Oktober 2007  <reiner>
 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */


package jsudoku;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.Adler32;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author reiner
 */

public final class JSudokuTable
{
    public int m_data[][];
    public int m_corr = 0;
    Random m_random = null;
    private String m_hash = new String();
    
    /** Creates a new instance of JSudokuTable */
    public JSudokuTable()
    {
        m_data = new int [9][9];
        initRandom();
        init();
    }
    
    public void init()
    {
        for (int row=0; row<9; row++)
        {
            for (int col=0; col<9; col++)
            {
                m_data[row][col] = 0;
            }
        }
    }
    
    public JSudokuTable(JSudokuTable table)
    {
        m_data = new int [9][9];
        for (int row=0; row<9; row++)
        {
            for (int col=0; col<9; col++)
            {
                m_data[row][col] = table.m_data[row][col];
            }
        }
    }

    protected JSudokuTable clone()
    {
        JSudokuTable table = new JSudokuTable();
        for (int row=0; row<9; row++)
        {
            for (int col=0; col<9; col++)
            {
                table.m_data[row][col] = m_data[row][col];
            }
        }
        return table;
    }
    
    protected void set(JSudokuTable table)
    {
        for (int row=0; row<9; row++)
        {
            for (int col=0; col<9; col++)
            {
                m_data[row][col] = table.m_data[row][col];
            }
        }
    }

    protected void initRandom()
    {
        try
        {
            m_random = SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException ex)
        {
            // using default Random class
            m_random = new Random();
        }
    }
    
    public String getHash()
    {
    	return m_hash;
    }

    public void setHash(String hash)
    {
        m_hash = hash;
    }
    
    protected String doGetHash()
    {
        Adler32 adler32 = new Adler32();
        for (int row=0; row<9; row++)
        {
            for (int col=0; col<9; col++)
            {
            adler32.update((byte)m_data[row][col]);
            }
        }
        return Long.toHexString(adler32.getValue());
    }
    
    public boolean isValid(int row, int col)
    {
        int j, h;
        h = m_data[row][col];
        if ( h != 0)
        {
            // check cols
            for (j=0; j<9; j++)
            {
            if (col==j) continue;
            if (m_data[row][j] == h)
                return false;
            }
            // check rows
            for (j=0; j<9; j++)
            {
            if (row==j) continue;
            if (m_data[j][col] == h)
                return false;
            }
            // check square
            int rowBlock = row/3;
            int colBlock = col/3;
            int rowBlockOffset = row%3;
            int colBlockOffset = col%3;
            int colIndex, rowIndex;
            for (colIndex=0; colIndex<3; colIndex++)
            {
            if (colBlockOffset==colIndex) continue;
            for (rowIndex=0; rowIndex<3; rowIndex++)
            {
                if (rowBlockOffset==rowIndex) continue;
                if (m_data[rowBlock*3 + rowIndex][colBlock*3 + colIndex] == h)
                return false;
            }
            }
        }
        return true;
    }

    public boolean isValid()
    {
        int row, col, j, h;
        for (col=0; col<9; col++)
        {
            for (row=0; row<9; row++)
            {
            if (!isValid(row, col))
                return false;
            }
        }
        return true;
    }

    public boolean checkValid(boolean[][] mask)
    {
        int row, col;
        for (col=0; col<9; col++)
        {
            for (row=0; row<9; row++)
            {
            mask[row][col] = !isValid(row, col);
            }
        }
        return true;
    }
    
    public boolean isComplete()
    {
        int row, col;
        for (row=0; row<9; row++)
        {
            for (col=0; col<9; col++)
            {
            if (m_data[row][col] == 0)
                return false;
            }
        }
        return true;
    }

    /**
     * I use a mask (array) where the index is the value and the value of the array item
     * indicates if this number is valid for this field
     * the array item at index 0 is only a dummy
     * @return the number of possibilities, zero if the table is invalid
     */
    
    public int getPossibleValueMask(int row, int col, int[] mask)
    {
        assert mask.length == 10;
        int i;
        // init
        for (i=1; i<10; i++)
            mask[i] = 1;
        // check rows and cols
        for (i=0; i<9; i++)
        {
            mask[m_data[row][i]] = 0;
            mask[m_data[i][col]] = 0;
        }
        // check square
        int rowBlock = (row/3)*3;
        int colBlock = (col/3)*3;
        int colIndex, rowIndex;
        for (colIndex=0; colIndex<3; colIndex++)
        {
            for (rowIndex=0; rowIndex<3; rowIndex++)
            mask[m_data[rowBlock + rowIndex][colBlock + colIndex]] = 0;
        }
        int count = 0;
        for (i=1; i<10; i++)
        {
            if (mask[i] != 0)
            count++;
        }
        return count;
    }

    /*
     * finds the next position with lowest possibilities
     * @return true if a free fiels was found
    */
    
    public int getNextFreeField(int[] rowCol, int[] mask)
    {
        int row, col, bestCount = 10, count = 0;
        boolean flag = false;
        int[] mask2 = new int[10];
        row:
        for (row=0; row<9; row++)
        {
            for (col=0; col<9; col++)
            {
            if (m_data[row][col] == 0)
            {
                count = getPossibleValueMask(row, col, mask2);
                if (count == 0) return 0;
                if (count < bestCount)
                {
                flag = true;
                bestCount = count;
                rowCol[0] = row;
                rowCol[1] = col;
                System.arraycopy(mask2, 0, mask, 0, mask2.length);
                if (count == 1) break row;
                }
            }
            }
        }
        return count;
    }
    
    public void trace()
    {
        System.err.print("===================================\n");
        int row, col;
        for (row=0; row<9; row++)
        {
            for (col=0; col<9; col++)
            {
            System.err.print(m_data[row][col]);
            if ((col+1)%3 == 0)
                System.err.print(" | ");
            else
                System.err.print("   ");
            }
            if (row > 0 && row < 8 && (row+1)%3 == 0)
            System.err.print("\n-----------------------------------\n");
            else
            System.err.print("\n");
        }
    }

    public int geFreeFields(ArrayList<Integer> array)
    {
        array.clear();
        int row, col;
        for (col=0; col<9; col++)
        {
            for (row=0; row<9; row++)
            {
            if (m_data[row][col] == 0)
                array.add(new Integer(col + row*9));
            }
        }
        return array.size();
    }
    
    public int geNumberOfFreeFields()
    {
        int count = 0;
        int row, col;
        for (col=0; col<9; col++)
        {
            for (row=0; row<9; row++)
            {
            if (m_data[row][col] == 0)
                count++;
            }
        }
        return count;
    }

    public boolean create(int freeFields)
    {
        init();
        if (freeFields == 0)

            freeFields = Main.m_settings.getMinFreeFields() + m_random.nextInt(Main.m_settings.getMaxFreeFields()-Main.m_settings.getMinFreeFields());
        boolean flag = doCreate(freeFields);
        if (flag)
        {
            JSudokuTable sk = new JSudokuTable(this);
            flag = solve();
            set(sk);
        }
        m_hash = doGetHash();
        return flag;
    }
    
    private boolean doCreate(int freeFields)
    {
        // trace();
        boolean flag = false;
        ArrayList<Integer> array = new ArrayList<Integer>();
        int index, row, col, i;
        int [] mask = new int[10];
        if (geFreeFields(array) > freeFields)
        {
            index = m_random.nextInt(array.size());
            row = array.get(index)/9;
            col = array.get(index)%9;
            getPossibleValueMask(row, col, mask);
            ArrayList<Integer> array2 = new ArrayList<Integer>();
            for (i=1; i<10; i++)
            if (mask[i] != 0) array2.add(new Integer(i));
            while(array2.size() != 0)
            {
                index = m_random.nextInt(array2.size());
                m_data[row][col] = array2.get(index);
                JSudokuTable sk = new JSudokuTable(this);
                if (solve())
                {
                    set(sk);
                    flag = doCreate(freeFields);
                    if (flag) break;
                    else m_data[row][col] = 0;
                }
                else
                {
                    // undo
                    set(sk);
                    m_data[row][col] = 0;
                }
                array2.remove(index);
            }
        }
        else flag = true;
        return flag;
    }
    
    public boolean solve()
    {
        m_corr = 0;
        return doSolve();
    }
    
    public boolean doSolve()
    {
        boolean flag = false;
        if (!isComplete())
        {
            int [] rowCol = new int[2];
            int [] mask = new int[10];
            int count = getNextFreeField(rowCol, mask);
            if (count > m_corr)
            m_corr = count;
            if (count > 0)
            {
                // System.err.println(rowCol[0] + " " + rowCol[1]);
                for (int i=1; i<10; i++)
                {
                    if (mask[i] != 0)
                    {
                        m_data[rowCol[0]][rowCol[1]] = i;
                        // trace();
                        if (flag = doSolve()) break;
                    }
                }
                if (!flag)
                    m_data[rowCol[0]][rowCol[1]] = 0;
            }
        }
        else flag = true;
        return flag;
    }
}
