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
public class SudokuSolver 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        SuGrid su = new SuGrid();
        int nums[][] = new int[9][9];
        for(int k = 0; k< 81; k++)
            nums[k%9][k/9] = (int)(Math.random()*10);

        su.setGrid(nums);
        su.printGrid();
        System.out.println("");

        
        for(int i = 0; i< 9; i++)
            su.getRow(i).printGroup();
        System.out.println("");
        
        for(int i = 0; i< 9; i++)
            su.getCol(i).printGroup();
        System.out.println("");

        
        for(int i = 0; i< 9; i++)
        {
            su.getSquare(i).printGroup();
            System.out.println("");
        }
        
    }
    
    
}
