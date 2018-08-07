/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.util.ArrayList;
import java.util.Arrays;

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
        
        
        nums = SudokuReader.test();
           

        grid.setGrid(nums);
        
        grid.initialize();
        
        System.out.println("Before");
        grid.printGrid();
        
        levelThreeLogic();
        
        System.out.println("After");
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
        
        
        grid.printPotentials();
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

    /**
     * Checks if a Set in a group is a Pure Set.
     * A Pure Set is any number of cells that have the exact same potentials with exactly N potentials, where N is the Set size.
     * IE: Two cells with the potential "14" is a Pure Set. Three cells with the potential "258" is a pure set. Two cells with the potential "456" is NOT.
     * When a Pure Set is found, that means no other cells in that Group can have those potentials.
     * Those potentials are removed from all other cells in the Group.
     * @param cells The list of cells to check the purity of.
     * @param sg The group which the cells are in.
     * @return If any changes were made.
     */    
    public static boolean pureSet(ArrayList<SuCell> cells, SuGroup sg)
    {
        //Initlaize Or checks with false.
        boolean changeMade = false;
        
        //First, let's make sure all cells in the Set have the right Pottentials.
        //Initialise And checks with true.
        boolean lengthCheck = true;

        //Make sure each cell's potentials have the right length. If any of them don't, we don't have a set.
        for(SuCell cell : cells)
            lengthCheck &= cell.getListPotentials().size() == cells.size();
        
        
        //next we need an arraylist of all the Set's potentials for the multi-intersection function.
        ArrayList<boolean[]> potentialsList = new ArrayList<>();
        for (SuCell cell : cells) 
            potentialsList.add(cell.getPotentials());
        

        //Intersect the Set's pottentials.
        ArrayList<Integer> intersection = DataUtils.boolArrayToIntList(DataUtils.boolArrayAnd(potentialsList));
        
        //If the intersection is also the right length, then we've proven all cells in the set have the same N potentials.
        lengthCheck &= intersection.size() == cells.size();
        
        
        //At this point, lengthCheck will be true only if we have a Pure Set
        if(lengthCheck)
        {
            for(int i = 0; i<9; i++)
                if(!cells.contains(sg.getCell(i)))
                    for (Integer elim : intersection) 
                        changeMade |= sg.getCell(i).eliminate(elim);
        }
        

        return changeMade;

    }
    
    public static boolean purePair(SuGroup sg)
    {
        boolean gridChanged = false;
        
        for(int i = 0; i<9; i++)
            for(int j = i+1; j<9; j++)
            {
                gridChanged |= l3CheckOnlyHavePair(sg.getCell(i), sg.getCell(j), sg);
                
                ArrayList<SuCell> cells = new ArrayList<>();
                cells.add(sg.getCell(i));
                cells.add(sg.getCell(j));
                gridChanged |= pureSet(cells, sg);
            }
        
        return gridChanged;
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
                notDone |= purePair(grid.getRow(i));
                notDone |= purePair(grid.getCol(i));
                notDone |= purePair(grid.getSquare(i));
            }
            
            for(int i = 0; i<9; i++)
                for(int j = 0; j<9; j++)
                {
                    notDone |= pointingSet(grid.getSquare(i), grid.getRow(j));
                    notDone |= pointingSet(grid.getSquare(i), grid.getCol(j));
                }
            
            gridChanged |= notDone;
        }
        
        return gridChanged;
    }
    
    
    /**
     * Identifies the common cells between two groups and sees if they contain the only instances of a potential in one of the groups.
     * @param g1 The first set to check.
     * @param g2 The second set to check.
     * @return If the grid changed as a result of this action.
     */
    public static boolean pointingSet(SuGroup g1, SuGroup g2)
    {
        boolean gridChanged = false;
        
        ArrayList<SuCell> intersection = DataUtils.intersection(new ArrayList<>(Arrays.asList(g1.getCells())), new ArrayList<> (Arrays.asList(g2.getCells())));
        
        //we aren't interested in groups with no intersection (size == 0) or interesctions of rows and colums. Those were handled already. Only Squares with a row or col that actually intersect. 
        if(intersection.size() == 3)
        {
            
            //Get the list of potentials in the intersection
            ArrayList<ArrayList<Integer>> intersectionList = new ArrayList<>();
            for(SuCell cell : intersection)
                intersectionList.add(cell.getListPotentials());
            ArrayList<Integer> intersectionPotentials = DataUtils.multiUnion(intersectionList);
            
            //Get the list of cells in g1 not in the intersection, as well as the list of potentials unique to cells in the intersection. 
            ArrayList<SuCell> g1Ex = DataUtils.exclusion(new ArrayList<>(Arrays.asList(g1.getCells())), intersection);
            ArrayList<ArrayList<Integer>> g1ExList = new ArrayList<>();
            for(SuCell cell : g1Ex)
                g1ExList.add(cell.getListPotentials());
            ArrayList<Integer> g1ExPotentials = DataUtils.multiUnion(g1ExList);
            ArrayList<Integer> g1PotentialIntersection = DataUtils.exclusion(intersectionPotentials, g1ExPotentials);
            
            //Get the list of cells in g2 not in the intersection, as well as the list of potentials unique to cells in the intersection.
            ArrayList<SuCell> g2Ex = DataUtils.exclusion(new ArrayList<>(Arrays.asList(g2.getCells())), intersection);
            ArrayList<ArrayList<Integer>> g2ExList = new ArrayList<>();
            for(SuCell cell : g2Ex)
                g2ExList.add(cell.getListPotentials());
            ArrayList<Integer> g2ExPotentials = DataUtils.multiUnion(g2ExList);
            ArrayList<Integer> g2PotentialIntersection = DataUtils.exclusion(intersectionPotentials, g2ExPotentials);
            
            //A "pointing set" occurs when all the points with a certain pottential in one group occur within the intersection space.
            //This means that for the other group, that group's instance of that number must be in the intersection as well, and others can be eliminated.
            //IE: All the 2's in a square are lined up in a row. That means the 2 for that row can't be outside that square, or it would invalidate that square. So none of those cells can have 2.
            
            //So g2Ex excludes the poentals unique to g1's potential intersection
            for (Integer potential : g1PotentialIntersection) 
                for (SuCell cell : g2Ex) 
                    gridChanged |= cell.eliminate(potential);
            
            //And g1Ex excludes the poentals unique to g1's potential intersection
            for (Integer potential : g2PotentialIntersection) 
                for (SuCell cell : g1Ex) 
                    gridChanged |= cell.eliminate(potential);
            
            //If there were no potentials unique to the intersection, the for loop will never incrament, and nothing will change, which is what we want.
            
        }
        
        return gridChanged;
    }
    
}
