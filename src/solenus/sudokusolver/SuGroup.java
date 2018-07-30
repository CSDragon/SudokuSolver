/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

/**
 *
 * @author chrsc
 */
public class SuGroup 
{

    public static final int BOX = 0;
    public static final int ROW = 1;
    public static final int COL = 2;
    
    private boolean[] confirmed;
    private SuCell[] cells;
    private int groupType;
    
    private boolean valid;
    
    public SuGroup(int _groupType, SuCell[] _cells)  
    {
        confirmed = new boolean[9];
        cells = _cells;
        groupType = _groupType;
        valid = true;
    }
    
    /**
     * Eliminates a number from all contained cells.
     * @param elim The number to be eliminated (not index)
     */
    public void eliminate(int elim)
    {
        for(SuCell c:getCells())
        {
            c.eliminate(elim);
        }
    }
    
    
    public void printGroup()
    {
        switch (groupType) 
        {
            case ROW:
            case COL:
                for(int i = 0; i < 8; i++)
                {
                    if(cells[i].getNumber()>0)
                        System.out.print(cells[i].getNumber() + ", ");
                    else
                        System.out.print(" , ");
                }   
                
                if(cells[8].getNumber()>0)
                    System.out.println(cells[8].getNumber());
                else
                    System.out.println(" ");
                break;
            case BOX:
                for(int i = 0; i < 9; i++)
                {
                    if(i%3 != 2)
                    {
                        if(cells[i].getNumber()>0)
                            System.out.print(cells[i].getNumber() + ", ");
                        else
                            System.out.print(" , ");
                    }

                    else
                    {
                        if(cells[i].getNumber()>0)
                            System.out.println(cells[i].getNumber());
                        else
                            System.out.println(" ");
                    }
                }
                break;
            default:
                break;
        }
    }
    
    
    /**
     * @return the confirmed
     */
    public boolean[] getConfirmed() 
    {
        return confirmed;
    }

    /**
     * @return the cells
     */
    public SuCell[] getCells() 
    {
        return cells;
    }
    
    public SuCell getCell(int i)
    {
        return cells[i];
    }

    /**
     * @return the groupType
     */
    public int getGroupType() 
    {
        return groupType;
    }

    /**
     * @return the valid
     */
    public boolean isValid() 
    {
        return valid;
    }
    
}
