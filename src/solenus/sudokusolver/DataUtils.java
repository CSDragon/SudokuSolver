/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class with utility functions for data structures
 * @author chrsc
 */
public class DataUtils 
{
    
    //Credit StackOverflow user Adarshr (https://stackoverflow.com/questions/5283047/intersection-and-union-of-arraylists-in-java/5283123#5283123)
    /**
     * Performs an intersection (finding common elements) on two ArrayLists of a same type.
     * @param <T> The type of object the ArrayLists contain.
     * @param list1 The first ArrayList to be intersected.
     * @param list2 The second ArrayList to be intersected
     * @return The resultant ArrayList of the intersection.
     */
    public static <T> ArrayList<T> intersection(ArrayList<T> list1, ArrayList<T> list2) 
    {
        ArrayList<T> ret = new ArrayList<>();

        //For each element in list1...
        for (T t : list1) 
        {
            //if it's in list2 as well...
            if(list2.contains(t)) 
            {
                //add it to the resultant ArrayList
                ret.add(t);
            }
        }

        return ret;
    }
    
    //Credit StackOverflow user Adarshr (https://stackoverflow.com/questions/5283047/intersection-and-union-of-arraylists-in-java/5283123#5283123)
    /**
     * Performs an union (finding all elements) on two ArrayLists of a same type.
     * @param <T> The type of object the ArrayLists contain.
     * @param list1 The first ArrayList to be unioned.
     * @param list2 The second ArrayList to be unioned
     * @return The resultant ArrayList of the union.
     */
    public static <T> ArrayList<T> union(ArrayList<T> list1, ArrayList<T> list2) 
    {
        //Create a HashSet to avoid duplication.
        Set<T> set = new HashSet<>();

        //Add both ArrayLists to the HashSet. The HashSet will contain all elements of each with no duplicates.
        set.addAll(list1);
        set.addAll(list2);

        //Return the HashSet in ArrayList form.
        return new ArrayList<>(set);
    }
    
    /**
     * Performs the Union operation on a variable number of ArrayLists
     * @param <T> The type of object the ArrayLists contain.
     * @param lists The ArrayList of ArrayLists to be Unioned.
     * @return The resulting ArrayList of the Union operation.
     */
    public static <T> ArrayList<T> multiUnion(ArrayList<ArrayList<T>> lists)
    {
        //Create a HashSet to avoid duplication.
        Set<T> set = new HashSet<>();

        //Add all ArrayLists to the HashSet. The HashSet will contain all elements of each with no duplicates.
        for(int i = 0; i<lists.size(); i++)
            set.addAll(lists.get(i));

        //Return the HashSet in ArrayList form.
        return new ArrayList<>(set);
    }
    
    /**
     * Performs an Exclusion operation, creating an ArrayList with all objects in list1 that are not also in list2.
     * @param <T> The type of object the ArrayLists contain.
     * @param list1 The list we want the objects from.
     * @param list2 The list we do not want the objects from.
     * @return The resultant ArrayList.
     */
    public static <T> ArrayList<T> exclusion(ArrayList<T> list1, ArrayList<T> list2) 
    {
        ArrayList<T> ret = new ArrayList<>();

        //For each element in list1
        for (T t : list1) 
        {
            //if it is not in list2
            if(!list2.contains(t)) 
            {
                //add it to the resultant.
                ret.add(t);
            }
        }

        return ret;
    }
    
    //Credit StackOverflow user Traveling Salesman (https://stackoverflow.com/questions/2989987/how-can-i-check-if-two-arraylist-differ-i-dont-care-whats-changed/41788514#41788514)
    /**
     * Checks of two ArrayLists contain the same objects, regardless of order. 
     * @param <T> The type of object the ArrayLists contain.
     * @param list1 The first ArrayList to be compared.
     * @param list2 The second ArrayList to be compared.
     * @return If the ArrayLists pass the equivalence check.
     */
    public static <T> boolean arrayListEquivalenceOrderIndependent(ArrayList<T> list1, ArrayList<T> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if(list1 == null || list2 == null)
            return false;

        //Make sure they're the same size.
        if(list1.size()!=list2.size())
            return false;
        
        //For each element in one...
        for(T itemList1: list1)
        {
            //make sure it's contained in the other.
            if(!list2.contains(itemList1))
                return false;
        }
        
        //If they're the same size and all of 1's objects is in 2, then they must all be the same, so they pass.
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

        //For each in the bool aray, add the number it represents.
        for(int i = 0; i<9; i++)
            if(b1[i])
                ret.add(i+1);
        
        return ret;
    }
}
