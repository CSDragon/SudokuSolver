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
 * Utility class for reading in Sudokus
 * @author Chris
 */
public class SudokuReader 
{
 
    /**
     * Used for reading in the test Sudoku
     * @return The test sudoku in int[][] form.
     */
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
    
    /**
     * Given a textstream, creates a Sudoku for 
     * @param in A textstream with the Sudoku in it.
     * @return A 9x9 integer array to make the Sudoku's grid.
     * @throws IOException 
     */
    public static int[][] makeNumGrid(BufferedReader in) throws IOException
    {
        int[][] ret = new int[9][9];
     
        //for 9 lines
        for(int i = 0; i<9; i++)
        {
            char[] line = in.readLine().toCharArray();
            //for 9 columns
            for(int j = 0; j<9; j++)
            {
                //Add the number to the int array at [row][col]
                ret[j][i] =  line[j]-48; 
            }
        }
        
        return ret;
    }
}
