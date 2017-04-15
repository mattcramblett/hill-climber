package com.mattcramblett;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides utilities for the hill climber solution to the
 * 4 sudoku problem.
 *
 * @author Matthew Cramblett
 *
 */
public class Utility {

    /**
     * Mapping of each position on the board and which element lies diagonal
     * from it. This is to help more efficiently check for conflicts within
     * the same square.
     */
    public static final Map<Integer, Integer> diagMap;
    static
    {
        diagMap = new HashMap<Integer, Integer>();
        diagMap.put(0, 5);
        diagMap.put(1, 4);
        diagMap.put(2, 7);
        diagMap.put(3, 6);
        diagMap.put(4, 1);
        diagMap.put(5, 0);
        diagMap.put(6, 7);
        diagMap.put(7, 2);
        diagMap.put(8, 13);
        diagMap.put(9, 12);
        diagMap.put(10, 15);
        diagMap.put(11, 14);
        diagMap.put(12, 9);
        diagMap.put(13, 8);
        diagMap.put(14, 11);
        diagMap.put(15, 10);
    }
}
