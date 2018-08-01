/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author chrsc
 */
public class SuCell 
{

    /**
     * @param parentSquare the parentSquare to set
     */
    public void setParentSquare(SuGroup parentSquare) {
        this.parentSquare = parentSquare;
    }
    private boolean confirmed;
    private int number;
    private boolean[] potentials;
    
    private int xPos;
    private int yPos;
    
    private SuGroup parentRow;
    private SuGroup parentCol;
    private SuGroup parentSquare;
    
    public SuCell(int x, int y)
    {
        xPos = x;
        yPos = y;
        confirmed = false;
        number = -1;
        
        potentials = new boolean[9];
        Arrays.fill(potentials, true);
    }
    
    public SuCell()
    {
        this(-1,-1);
    }
    
    /**
     * Eliminates a number (not index) from potentials
     * @param elim number to be eliminated.
     */
    public void eliminate(int elim)
    {
        potentials[elim-1] = false;
    }
    
    
    /**
     * Checks if there's only one potential left.
     * @return The number that was identified, (not an index).
     */
    public int potentailCheck()
    {
        int num = -1;
        for(int i = 0; i<9; i++)
        {
            if(getPotentials()[i] && num == -1)
                num = i+1;
            else if(getPotentials()[i] && num != -1)
                num = -2;
        }
        
        if(num < 0)
        {
            confirmed = true;
            number = num;
        }
        return getNumber();
    }
    
    
    /**
     * Confirms a square as a number;
     * @param num 
     */
    public void set(int num)
    {
        confirmed = true;
        number = num;
        Arrays.fill(potentials, false);
        
        if(parentRow != null)
            parentRow.eliminate(num);
        if(parentCol != null)
            parentCol.eliminate(num);
        if(parentSquare != null)
            parentSquare.eliminate(num);
    }
    
    public void reset()
    {
        confirmed = false;
        number = -1;
        Arrays.fill(potentials, true);
    }
    
    
    /**
     * Potentials to int list
     * @return An arraylist of the potentials.
     */
    public ArrayList<Integer> listPotentials()
    {
        ArrayList<Integer> ret = new ArrayList<>();
        
        for(int i = 0; i<9; i++)
        {
            if(potentials[i])
                ret.add(i+1);
        }
        return ret;
    }
    
    public void printCell()
    {
        System.out.println("Cell (" + xPos + "," + yPos + ")");
        System.out.println("Confirmed: "+ confirmed);
        if(confirmed)
            System.out.println(number);
        else
        {
            for(int i = 0; i<9; i++)
                if(potentials[i])
                    System.out.print(i+1);
            System.out.println();
        }
    }
    
    
    
    /**
     * @return the confirmed
     */
    public boolean getConfirmed() 
    {
        return confirmed;
    }

    /**
     * @return the number
     */
    public int getNumber() 
    {
        return number;
    }

    /**
     * @return the potentials
     */
    public boolean[] getPotentials() 
    {
        return potentials;
    }

    /**
     * @return the xPos
     */
    public int getxPos() 
    {
        return xPos;
    }

    /**
     * @return the yPos
     */
    public int getyPos() 
    {
        return yPos;
    }
    
    /**
     * @return the parentRow
     */
    public SuGroup getParentRow() 
    {
        return parentRow;
    }

    /**
     * @param parentRow the parentRow to set
     */
    public void setParentRow(SuGroup parentRow) 
    {
        this.parentRow = parentRow;
    }

    /**
     * @return the parentCol
     */
    public SuGroup getParentCol() 
    {
        return parentCol;
    }

    /**
     * @param parentCol the parentCol to set
     */
    public void setParentCol(SuGroup parentCol) 
    {
        this.parentCol = parentCol;
    }

    /**
     * @return the parentSquare
     */
    public SuGroup getParentSquare() 
    {
        return parentSquare;
    }
}
