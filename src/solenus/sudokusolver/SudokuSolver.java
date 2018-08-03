/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.util.ArrayList;

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
           

        grid.setGrid(nums);
        
        grid.initialize();
        levelThreeLogic();
        
        
        
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
                grid.getCell(j, i).printCell();
        
    }
    
    
    
    public static boolean levelOneLogic()
    {
        boolean gridChanged = false;
        boolean notDone = true;
        while(notDone)
        {
            notDone = false;
            
            for(int i = 0; i < 9; i++)
                for(int j = 0; j < 9; j++)
                {
                    SuCell temp = grid.getCell(i,j);
                    if(!temp.getConfirmed() && temp.getListPotentials().size() == 1)
                    {
                        temp.set(temp.getListPotentials().get(0));
                        notDone = true;
                    }
                }
            
            gridChanged |= notDone;
        }
        return gridChanged;
    }
    
    public static boolean levelTwoLogic()
    {
        boolean gridChanged = false;
        boolean notDone = true;
        grid.printGrid();
        while(notDone)
        {
            notDone = false;
            
            notDone |= levelOneLogic();
            
            for(int i = 0; i<9; i++)
            {
                notDone |= l2CheckGroup(grid.getRow(i));
                notDone |= l2CheckGroup(grid.getCol(i));
                notDone |= l2CheckGroup(grid.getSquare(i));
            }
            
            gridChanged |= notDone;
        }
        
        return gridChanged;
    }
    
    /**
     * Checks if any groups have only one place where a number could be.
     * @param sg The group to check.
     * @return 
     */
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
    
    
    /**
     * Checks if a pair are a "Only Have" pair, where they are the only two cells in a group that can be two numbers, removing other potentials.
     * @param c1 The first cell
     * @param c2 The second cell
     * @param sg The group they're in
     * @return If any changes were made.
     */
    public static boolean l3CheckOnlyHavePair(SuCell c1, SuCell c2, SuGroup sg)
    {
        boolean changeMade = false;
        
        //Find the ones c1 and c2 have in common.
        ArrayList<Integer> intersection = DataUtils.intersection(c1.getListPotentials(), c2.getListPotentials());
        //Find the ones the others have total.
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        for(int i = 0; i<9; i++)
            if(sg.getCell(i) != c1 && sg.getCell(i) != c2)
                lists.add(sg.getCell(i).getListPotentials());
        ArrayList<Integer> union = DataUtils.multiUnion(lists);
        
        //Find the ones that c1 and c2 have that the others do not.
        ArrayList<Integer> exclusion = DataUtils.exclusion(intersection, union);
        
        //If there's only two numbers, we have a pair.
        if(exclusion.size() == 2)
        {
            if(!DataUtils.isTwoArrayListsWithSameValues(c1.getListPotentials(), exclusion) || !DataUtils.isTwoArrayListsWithSameValues(c2.getListPotentials(), exclusion))
            {
                changeMade = true;

                //change them so they only have the two potentials.
                c1.zeroPotentials();
                c1.addPotential(exclusion.get(0));
                c1.addPotential(exclusion.get(1));

                c2.zeroPotentials();
                c2.addPotential(exclusion.get(0));
                c2.addPotential(exclusion.get(1));
            }
        }
        
        return changeMade;
    }
    
    public static boolean l3PurePair(SuCell c1, SuCell c2, SuGroup sg)
    {
        boolean changeMade = false;
        //they both need to be 2 long, and their intersection two long. That means they're the same.
        ArrayList<Integer> intersection = DataUtils.intersection(c1.getListPotentials(), c2.getListPotentials());
        if(c1.getListPotentials().size() == 2 && c2.getListPotentials().size() == 2 && intersection.size() == 2)
        {
            //for each cell in sg that isnt' c1 or c2
            for(int i = 0; i<9; i++)
                if(sg.getCell(i) != c1 && sg.getCell(i) != c2)
                {
                    //eliminate the pair
                    
                    //make sure we don't create infinite logic by only calling this a change if anything was actually eliminated.
                    changeMade |= sg.getCell(i).eliminate(intersection.get(0));
                    changeMade |= sg.getCell(i).eliminate(intersection.get(1));
                }
        }
        return changeMade;
    }
    
    public static boolean l3PairLogic(SuGroup sg)
    {
        boolean gridChanged = false;
        
        for(int i = 0; i<9; i++)
            for(int j = i+1; j<9; j++)
            {
                gridChanged |= l3CheckOnlyHavePair(sg.getCell(i), sg.getCell(j), sg);
                gridChanged |= l3PurePair(sg.getCell(i), sg.getCell(j), sg);
            }
        
        return gridChanged;
    }
    
    public static boolean l3Itter()
    {
        boolean notDone = false;
        
        return notDone;
    }
    
    public static boolean levelThreeLogic()
    {
        boolean gridChanged = false;
        
        boolean notDone = true;
        while(notDone)
        {
            notDone = false;
            
            notDone |= levelTwoLogic();
            
            for(int i = 0; i<9; i++)
            {
                notDone |= l3PairLogic(grid.getRow(i));
                notDone |= l3PairLogic(grid.getCol(i));
                notDone |= l3PairLogic(grid.getSquare(i));
            }
            
            gridChanged |= notDone;
        }
        
        return gridChanged;
    }
    
}
