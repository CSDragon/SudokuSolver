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
public class SuGrid 
{
    private SuCell[][] cells;
    private SuGroup[] rows;
    private SuGroup[] cols;
    private SuGroup[] squares;
    
    
    public SuGrid()
    {
        cells = new SuCell[9][9];
        for(int i = 0; i<9; i++)
            for(int j = 0; j<9; j++)
                cells[i][j] = new SuCell(i,j);
        
        //make cols
        cols = new SuGroup[9];
        for(int i = 0; i<9; i++)
        {
            SuCell[] cellGroup = new SuCell[9];
            for(int j = 0; j<9; j++)
                cellGroup[j] = cells[i][j];
            cols[i] = new SuGroup(SuGroup.COL, cellGroup);
        }
        
        //make rows
        rows = new SuGroup[9];
        for(int j = 0; j<9; j++)
        {
            SuCell[] cellGroup = new SuCell[9];
            for(int i = 0; i<9; i++)
                cellGroup[i] = cells[i][j];
            rows[j] = new SuGroup(SuGroup.ROW, cellGroup);
        }
        
        //make squares
        squares = new SuGroup[9];
        for(int i = 0; i<9; i++)
        {
            SuCell[] cellGroup = new SuCell[9];
            for(int j = 0; j<9; j++)
                cellGroup[j] = cells[(i%3)*3 + j%3][(i/3)*3 + j/3];
            squares[i] = new SuGroup(SuGroup.BOX, cellGroup);
        }
    }
    
    public void setGrid(int[][] nums)
    {
        for(int i = 0; i<9; i++)
            for(int j=0; j<9; j++)
            {
                if(nums[i][j] != -1)
                    cells[i][j].set(nums[i][j]);
                else
                    cells[i][j].reset();
            }
    }
    
    
    public void printGrid()
    {
        for(int j = 0; j<9; j++)
        {
            for(int i = 0; i<8; i++)
            {
                if(cells[i][j].getNumber()>0)
                    System.out.print(cells[i][j].getNumber() + ", ");
                else
                    System.out.print(" , ");
            }
            
            if(cells[8][j].getNumber()>0)
                    System.out.println(cells[8][j].getNumber());
                else
                    System.out.println(" ");
            
        }
    }
    
    
    
    public SuGroup getRow(int rowNum)
    {
        return rows[rowNum];
    }
    
    public SuGroup getCol(int colNum)
    {
        return cols[colNum];
    }
    
    public SuGroup getSquare(int squareNum)
    {
        return squares[squareNum];
    }
    
    
    
    
}
