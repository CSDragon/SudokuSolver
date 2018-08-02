/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.util.ArrayList;
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
}
