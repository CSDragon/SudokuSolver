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
        
        //sample medium
        nums = new int[][] {{0,0,3,5,0,2,9,0,0},
                            {0,0,0,0,4,0,0,0,0},
                            {1,0,6,0,0,0,3,0,5},
                            {9,0,0,2,5,1,0,0,8},
                            {0,7,0,4,0,8,0,3,0},
                            {8,0,0,7,6,3,0,0,1},
                            {3,0,8,0,0,0,1,0,4},
                            {0,0,0,0,2,0,0,0,0},
                            {0,0,5,1,0,4,8,0,0}};
        
        nums = SudokuReader.test();
        
        //sample hard
        /*
        nums = new int[][]{{0,0,1,0,0,7,0,9,0},
                           {5,9,0,0,8,0,0,0,1},
                           {0,3,0,0,0,0,0,8,0},
                           {0,0,0,0,0,5,8,0,0},
                           {0,5,0,0,6,0,0,2,0},
                           {0,0,4,1,0,0,0,0,0},
                           {0,8,0,0,0,0,0,3,0},
                           {1,0,0,0,2,0,0,7,9},
                           {0,2,0,7,0,0,4,0,0}};
        */
        

        grid.setGrid(nums);
        
        grid.initialize();
        levelTwoLogic();
        
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
        
        for(int i = 0; i<9; i++)
            for(int j = 0; j<9; j++)
                grid.getCell(i, j).printCell();
        
    }
    
    
    
    public static boolean levelOneLogic()
    {
        boolean levelTwoNotDone = false;
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
                        levelTwoNotDone = true;
                    }
                }
        }
        return levelTwoNotDone;
    }
    
    public static void levelTwoLogic()
    {
        boolean notDone = true;
        grid.printGrid();
        while(notDone)
        {
            notDone = false;
            
            for(int i = 0; i<9; i++)
            {
                if(l2CheckGroup(grid.getRow(i)))
                    notDone = true;
                if(l2CheckGroup(grid.getCol(i)))
                    notDone = true;
                if(l2CheckGroup(grid.getSquare(i)))
                    notDone = true;
            }
            
            if(levelOneLogic())
                notDone = true;
        }
    }
    
    public static boolean l2CheckGroup(SuGroup sg)
    {
        boolean notDone = false;
        
        //for each number
        for(int i = 0; i<9; i++)
        {
            //if only one cell can be that number, it must be that cell.
            int onlyOne = -1;
            for(int j = 0; j<9; j++)
            {
                //if it's the first, we want to remember it. If there's been others, we don't want anything.
                if(sg.getCell(j).getPotentials()[i])
                {
                    if(onlyOne == -1)
                        onlyOne = j;
                    else
                        onlyOne = -2;
                }
            }
            if(onlyOne > -1)
            {
                    sg.getCell(onlyOne).set(i+1);
                    notDone = true;
            }
            
        }
        
        return notDone;
    }
    
    
}
