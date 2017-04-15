package com.mattcramblett;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Entry point/testing for AI lab2
 *
 * @author Matthew Cramblett
 *
 */
public class TestHarness {

    private static String fileName;

    private static Scanner reader;

    private static ArrayList<Integer> fixedPos;

    /**
     * Gets input for the 4 sudoku problem.
     *
     * @param in the input stream to read from
     */
    private static String getInput(Scanner in) {
        String four = in.nextLine();
        if(!four.equals("4")) {
            System.out.println("ERROR - expected '4' at start of input file.");
            System.exit(1);
        }

        fixedPos = new ArrayList<Integer>();
        StringBuilder sb = new StringBuilder();

        //loop through the input and get values for board
        for(int i=0; i<4; i++) {
            String line = in.nextLine();
            for(int j=0; j<4; j++) {
                if (line.charAt(j) == '*') {
                    sb.append(ThreadLocalRandom.current().nextInt(1, 5)); //fill in random integer
                } else {
                    sb.append(line.charAt(j));
                    fixedPos.add((i*4) + j); //add position of the fixed number
                }
            }
        }
        return sb.toString();
    }

    /**
     * Bootstrap/entry point for test harness of the 4 sudoku problem.
     *
     * @param args first arg should be name of file to read from
     */
    public static void main(String[] args) {
        if(args[0] == null) {
            System.out.println("ERROR - command line argument of file name needed.");
            System.exit(1);
        }
        fileName  = args[0];
        //set up file reading for the data:
        try {
            reader = new Scanner(new File(fileName));

            //create the hill climber
            HillClimber hc = new HillClimber(getInput(reader), fixedPos);

            //solve and print solution
            hc.solve();
            System.out.println("\nSOLUTION:\n");
            System.out.println(hc.boardToString());

        } catch (FileNotFoundException e) {
            System.out.println("ERROR reading data file " + fileName);
            e.printStackTrace();
        } finally{
            reader.close();
        }
    }
}
