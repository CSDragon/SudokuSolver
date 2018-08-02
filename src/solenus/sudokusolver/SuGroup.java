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
    
    /**
     * Takes the initial grid state and figures out the group's initial conditions, as well as the cells inside of it.
     */
    public void initialize()
    {
        //set parenthood
        for(int i = 0; i<9; i++)
        {
            switch (groupType)
            {
                case ROW:
                    cells[i].setParentRow(this);
                    break;
                case COL:
                    cells[i].setParentCol(this);
                    break;
                case BOX:
                    cells[i].setParentSquare(this);
                    break;
                default:
                    break;
            }
        }
        
        //find the ones we've already confirmed
        for(int i = 0; i<9; i++)
            if(cells[i].getConfirmed())
                confirmed[cells[i].getNumber()-1] = true;
      
        //now that we have a list of all confirmed numbers in this group, make sure each cell knows it can't be that.
        for(int i = 0; i<9; i++)
            if(!cells[i].getConfirmed())
                for(int j = 0; j<9; j++)
                    if(confirmed[j])
                        cells[i].eliminate(j+1);
    }
    
    
    public void printGroup()
    {
        switch (groupType) 
        {
            case ROW:
            case COL:
                for(int i = 0; i < 8; i++)
                {
                    if(cells[i].getConfirmed())
                        System.out.print(cells[i].getNumber() + ", ");
                    else
                        System.out.print(" , ");
                }   
                
                if(cells[8].getConfirmed())
                    System.out.println(cells[8].getNumber());
                else
                    System.out.println(" ");
                break;
            case BOX:
                for(int i = 0; i < 9; i++)
                {
                    if(i%3 != 2)
                    {
                        if(cells[i].getConfirmed())
                            System.out.print(cells[i].getNumber() + ", ");
                        else
                            System.out.print(" , ");
                    }

                    else
                    {
                        if(cells[i].getConfirmed())
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
