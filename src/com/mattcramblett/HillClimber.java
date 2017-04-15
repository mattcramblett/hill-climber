package com.mattcramblett;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class HillClimber {
    private String board;
    private ArrayList<Integer> fixed;

    /**
     * Creates a HillClimber to solve the 4-sudoku problem
     *
     * @param board
     * 			String representing the flattened board, initial state
     * @param fixed
     * 			List of positions on board where numbers are fixed
     */
    public HillClimber(String board, ArrayList<Integer> fixed) {
        this.board = board;
        this.fixed = fixed;
    }

    /**
     * Getter for the board.
     * @return
     * 		flattened String representation of the board
     */
    public String getBoard(){
        return board;
    }

    /**
     * Getter for the fixed position
     * @return
     * 		list of integers corresponding to where the initial state
     * 		should not be changed
     */
    public ArrayList<Integer> getFixed() {
        return fixed;
    }

    /**
     * Returns a better printed format of the board
     * @return
     * 		String of formatted board
     */
    public String boardToString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                sb.append(board.charAt((i*4)+j) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks for conflicts on the 4 sudoku board
     * @param board
     * 			string representing the 'flat' board
     * @return
     * 			number of conflicts on board (with duplicates, but consistent)
     */
    public static int countConflicts(String board) {
        assert(board.length() == 16);
        int count = 0;
        int rowMod = 0;
        String tempRow = board.substring(0, 4);

        for(int pos=0; pos<board.length(); pos++) {
            if(pos % 4 == 0 && pos > 0) {
                rowMod +=4; //each row, add 4
                tempRow = board.substring(pos, rowMod+4);
            }

            //column conflicts
            for(int i=4; i<16; i+=4) {
                if(board.charAt(pos) == board.charAt((pos+i) % 16)) count++;
            }

            //row conflicts
            for(int i=0; i<4; i++) {
                if(i != (pos - rowMod)) {
                    if(board.charAt(pos) == tempRow.charAt(i)) count++;
                }
            }

            //Same square conflict (diagonal from this position)
            if(board.charAt(pos) == board.charAt(Utility.diagMap.get(pos))) count++;

        }
        return count;
    }

    /**
     * generates a list of possible successors by changing the number at
     * the given position of the board
     * @param pos
     * 			position on board to change
     * @return
     * 			list of board states
     */
    private ArrayList<String> getSuccessors(int pos) {
        int current = Character.getNumericValue(board.charAt(pos));
        String thisBoard = board;
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0; i<4; i++) {
            if((i+1) != current) {
                //a successor will be the original board with the specified position replaced with
                //the other three alternative numbers
                String successor = thisBoard.substring(0, pos) + (i+1) + thisBoard.substring(pos+1, 16);
                result.add(successor);
            }
        }
        return result;
    }


    /**
     * Gets every possible successor for each position
     * based on the current state of the board
     * @return
     * 			list of all successor states
     */
    private ArrayList<String> getSuccessors() {
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0; i<16; i++) {
            //get successors of spaces that aren't fixed
            if(!fixed.contains(i)) result.addAll(getSuccessors(i));
        }
        return result;
    }

    /**
     * Chooses and returns the state with the lowest amount of conflicts
     * @param states
     * 			list of all states to choose from
     * @requires
     * 			|states| > 0
     * @return
     * 			string that represents the state with the least conflicts
     */
    private String chooseLowest(ArrayList<String> states) {
        String lowest = states.get(0);
        for(int i=1; i<states.size(); i++) {
            if(countConflicts(states.get(i)) < countConflicts(lowest)) {
                lowest = states.get(i);
            }
        }
        return lowest;
    }

    /**
     * Resets all of the non-fixed spaces with a random number 1-4
     * on the board
     */
    private void randomRestart() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<board.length(); i++) {
            if(!fixed.contains(i)) sb.append(ThreadLocalRandom.current().nextInt(1, 5));
            else sb.append(board.charAt(i));
        }
        board = sb.toString();
    }


    /**
     * Runs the Hill Climber algorithm with random restart
     */
    public void solve() {
        int count = 0;

        //loop until 0 conflicts
        while(countConflicts(board) > 0) {
            //get the lowest conflict successor
            String nextBoard = chooseLowest(getSuccessors());

            //update board to new state if it's better
            if(countConflicts(nextBoard) < countConflicts(board)) {
                System.out.println("#"+count+"  state: "+board+"   conflicts: "+countConflicts(board));
                board = nextBoard;
            } else {
                randomRestart();
                System.out.println("--RESTART--");
                count = 0;
            }
            count++;
        }
    }
}