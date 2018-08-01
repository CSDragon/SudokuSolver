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
public class NumGrid 
{
    private int gridNum;
    private boolean[][] potential;
    
    public NumGrid(int gridNumber)
    {
        gridNum = gridNumber;
        potential = new boolean[9][9];
        Arrays.fill(potential, true);
    }
    
    public void setGrid(int[][] numGrid)
    {
        for(int i = 0; i<9; i++)
            for(int j = 0; j<9; j++)
                potential[i][j] = (numGrid[i][j] < 1);
    }
    
    
    public void updateCell(int x, int y)
    {
        potential[x][y] = false;
    }
    
    public void updateCell(SuCell cell)
    {
        potential[cell.getxPos()][cell.getyPos()] = false;
    }
}
