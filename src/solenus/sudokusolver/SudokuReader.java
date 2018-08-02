/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solenus.sudokusolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Chris
 */
public class SudokuReader 
{
    
    public static int[][] test()
    {
        try
        {
        BufferedReader in = new BufferedReader(new FileReader("assets/sampleEasy.txt"));
        return makeNumGrid(in);
        }
        catch(Exception E)
        {
                        System.out.println("oh");

        }
        return null;
    }
    
    
    public static int[][] makeNumGrid(BufferedReader in) throws IOException
    {
        int[][] ret = new int[9][9];
        
        for(int i = 0; i<9; i++)
        {
            char[] line = new char[9];
            line = in.readLine().toCharArray();
            for(int j = 0; j<9; j++)
                ret[i][j] =  line[j]-48; 
        }
        
        return ret;
    }
}
