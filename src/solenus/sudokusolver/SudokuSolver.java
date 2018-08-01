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
    private static SuGrid grid;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        grid = new SuGrid();
        int nums[][] = new int[9][9];
        for(int k = 0; k< 81; k++)
            nums[k%9][k/9] = (int)(Math.random()*10);
        
        //sample
        nums = new int[][]{{0,1,9,0,5,0,7,0,0}
                          ,{0,7,0,1,9,0,0,0,8}
                          ,{0,0,0,3,0,6,2,0,9}
                          ,{0,0,5,0,0,2,0,0,3}
                          ,{7,0,0,0,4,0,0,0,1}
                          ,{4,0,0,8,0,0,6,0,0}
                          ,{9,0,4,6,0,7,0,0,0}
                          ,{6,0,0,0,2,3,0,8,0}
                          ,{0,0,7,0,1,0,3,9,0}};
        
        

        grid.setGrid(nums);
        
        grid.initialize();
        levelOneLogic();
        
        grid.printGrid();
        System.out.println("");

        
        for(int i = 0; i< 9; i++)
            grid.getRow(i).printGroup();
        System.out.println("");
        
        for(int i = 0; i< 9; i++)
            grid.getCol(i).printGroup();
        System.out.println("");

        
        for(int i = 0; i< 9; i++)
        {
            grid.getSquare(i).printGroup();
            System.out.println("");
        }
        
    }
    
    public static void levelOneLogic()
    {
        boolean notDone = true;
        while(notDone)
        {
            notDone = false;
            
            for(int i = 0; i < 9; i++)
                for(int j = 0; j < 9; j++)
                {
                    SuCell temp = grid.getCell(i,j);
                    if(!temp.getConfirmed() && temp.listPotentials().size() == 1)
                    {
                        temp.set(temp.listPotentials().get(0));
                        notDone = true;
                    }
                }
        }
    }
    
    
}
