/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.util.Arrays;

/**
 *
 * @author chrsc
 */
public class SuCell 
{
    private boolean confirmed;
    private int number;
    private boolean[] potentials;
    
    private int xPos;
    private int yPos;
    
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
        //potentials[number-1] = true;
    }
    
    public void reset()
    {
        confirmed = false;
        number = -1;
        Arrays.fill(potentials, true);
    }
    
    
    
    /**
     * @return the confirmed
     */
    public boolean isConfirmed() 
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
}
