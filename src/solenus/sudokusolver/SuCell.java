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
    private ArrayList<Integer> listPotentials;
    
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
        number = 0;
        
        potentials = new boolean[9];
        Arrays.fill(potentials, true);
        listPotentials = new ArrayList<>();
        for(int i = 1; i<10; i++)
            listPotentials.add(i);
    }
    
    public SuCell()
    {
        this(-1,-1);
    }
    
    /**
     * Eliminates a number (not index) from potentials
     * @param elim number to be eliminated.
     * @return if a change was actually made.
     */
    public boolean eliminate(int elim)
    {
        
        if(potentials[elim-1])
        {
            listPotentials.remove(listPotentials.indexOf(elim));
            potentials[elim-1] = false;
            return true;
        }
        return false;
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
        listPotentials.clear();
        
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
        number = 0;
        Arrays.fill(potentials, true);
        listPotentials = new ArrayList<>();
        for(int i = 1; i<10; i++)
            listPotentials.add(i);
        
    }
    
    /**
     * Zeroes out the potentials.
     */
    public void zeroPotentials()
    {
        Arrays.fill(potentials, false);
        listPotentials.clear();
    }
    
    /**
     * Adds a potential 
     * @param pot The potential to add
     */
    public void addPotential(int pot)
    {
        if(!potentials[pot-1])
            listPotentials.add(pot);
        potentials[pot-1] = true;
    }
    
    
    /**
     * Potentials to int list
     * @return An arraylist of the potentials.
     */
    public ArrayList<Integer> getListPotentials()
    {
        return listPotentials;
    }
    
    public void printCell()
    {
        System.out.println("Cell (" + xPos + "," + yPos + ")");
        System.out.println("Confirmed: "+ confirmed);
        if(confirmed)
            System.out.println(number);
        else
        {
            for(int i = 0; i<listPotentials.size(); i++)
                System.out.print(listPotentials.get(i));
            System.out.println();
        }
    }
    
    /**
     * Prints the potentials with whitespaces to make an even coating.
     * @param maxPotentials the max number of potentials any cell has in the grid.
     */
    public void printPotentials(int maxPotentials)
    {
        if(confirmed)
        {
            for(int i = 0; i<maxPotentials-listPotentials.size()-1; i++)
                System.out.print("-");
            
            System.out.print(number);
        }
        else
        {
            for(int i = 0; i<maxPotentials-listPotentials.size(); i++)
                System.out.print("-");
            for(int i = 0; i<listPotentials.size(); i++)
                System.out.print(listPotentials.get(i));
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
