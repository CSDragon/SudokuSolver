/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author chrsc
 */
public class DataUtils 
{
    
    //Credit StackOverflow user Adarshr (https://stackoverflow.com/questions/5283047/intersection-and-union-of-arraylists-in-java/5283123#5283123)
    public static <T> ArrayList<T> intersection(ArrayList<T> list1, ArrayList<T> list2) 
    {
        ArrayList<T> list = new ArrayList<T>();

        for (T t : list1) 
        {
            if(list2.contains(t)) 
            {
                list.add(t);
            }
        }

        return list;
    }
    
    //Credit StackOverflow user Adarshr (https://stackoverflow.com/questions/5283047/intersection-and-union-of-arraylists-in-java/5283123#5283123)
    public static <T> ArrayList<T> union(ArrayList<T> list1, ArrayList<T> list2) 
    {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }
    
    public static <T> ArrayList<T> multiUnion(ArrayList<ArrayList<T>> lists)
    {
        Set<T> set = new HashSet<T>();

        for(int i = 0; i<lists.size(); i++)
            set.addAll(lists.get(i));

        return new ArrayList<T>(set);
    }
    
    public static <T> ArrayList<T> exclusion(ArrayList<T> list1, ArrayList<T> list2) 
    {
        ArrayList<T> list = new ArrayList<T>();

        for (T t : list1) 
        {
            if(!list2.contains(t)) 
            {
                list.add(t);
            }
        }

        return list;
    }
    
    //Credit StackOverflow user Traveling Salesman (https://stackoverflow.com/questions/2989987/how-can-i-check-if-two-arraylist-differ-i-dont-care-whats-changed/41788514#41788514)
    public static <T> boolean isTwoArrayListsWithSameValues(ArrayList<T> list1, ArrayList<T> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(T itemList1: list1)
        {
            if(!list2.contains(itemList1))
                return false;
        }

        return true;
    }
    
    
    /**
     * Given a list of boolean arrays, ORs them (Union).
     * Assumes length 9 boolean array for Sudoku. Not generic.
     * @param bools List of boolean arrays
     * @return The resultant Or'd (Union) array.
     */
    public static boolean[] boolArrayOr(ArrayList<boolean[]> bools)
    {
        boolean[] ret = new boolean[9];
        Arrays.fill(ret, false);
        //for each, OR each element in the array.
        for(int i = 0; i<bools.size(); i++)
            for(int j = 0; j<9; j++)
                ret[j] |= bools.get(i)[j];
        
        return ret;
    }
    
    /**
     * Given a list of boolean arrays, ORs them (Intersection).
     * Assumes length 9 boolean array for Sudoku. Not Generic
     * @param bools List of boolean arrays.
     * @return The resultant And'd (Intersection) array.
     */
    public static boolean[] boolArrayAnd(ArrayList<boolean[]> bools)
    {
        boolean[] ret = new boolean[9];
        
        //We have to fill ret with True for Anding, but if bools is empty it should return all False
        if(bools.isEmpty())
            return ret;
        Arrays.fill(ret, true);
        //for each, AND each element in the array.
        for(int i = 0; i<bools.size(); i++)
            for(int j = 0; j<9; j++)
                ret[j] &= bools.get(i)[j];
        
        return ret;
    }
    
    /**
     * Takes a boolean array and excludes all objects in a list of other boolean arrays.
     * Assumes length 9 boolean array for Sudoku. Not Generic
     * @param b1 The boolean array to compare 
     * @param bools The arrays to compare against.
     * @return The Excluded boolean array
     */
    public static boolean[] boolArrayExclude(boolean[] b1, ArrayList<boolean[]> bools)
    {
        boolean[] ret = new boolean[9];
        
        //For Ret to have an object, it must both be in b1 and not in bools.
        for(int i = 0; i<bools.size(); i++)
            for(int j = 0; j<9; j++)
                ret[j] = b1[j] & !bools.get(i)[j];
        
        
        return ret;
    }
    
    /**
     * Turns a boolean array into an integer arraylist of contained values.
     * Assumes length 9 boolean array for Sudoku. Not Generic
     * @param b1 The boolean array
     * @return The Integer list.
     */
    public static ArrayList<Integer> boolArrayToIntList(boolean[] b1)
    {
        ArrayList<Integer> ret = new ArrayList<>();

        for(int i = 0; i<9; i++)
            if(b1[i])
                ret.add(i+1);
        
        return ret;
    }
}
