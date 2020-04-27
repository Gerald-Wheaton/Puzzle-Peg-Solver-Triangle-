import java.beans.Statement;
import java.util.Scanner;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Stack;

public class PuzzlePegs {
    public static Stack<String> jumpRecord = new Stack<String>();
    public static Stack<String[]> stateStack = new Stack<String[]>();
    public static int numPegs = 14;
    public static boolean success = true;
    //all possible moves to make in the triangle
    public static int[][] triplets = {

        /*{0, 1, 3},
        {1, 3, 6},
        {2, 4, 7},
        {0, 2, 5},
        {2, 5, 9},
        {3, 4, 5},
        {6, 7, 8},
        {7, 8, 9},
        {1, 4, 8}*/

        //North East triplets in the triangle
        {0, 1, 3},
        {1, 3, 6},
        {3, 6, 10},
        {2, 4, 7},
        {4, 7, 11},
        {5, 8, 12},
        //North West triplets in the triangle
        {0, 2, 5},
        {2, 5, 9},
        {5, 9, 10},
        {1, 4, 8},
        {4, 8, 13},
        {6, 7, 12},
        //East to West triplets in the triangle
        {3, 4, 5},
        {6, 7, 8},
        {7, 8, 9},
        {10, 11, 12},
        {11, 12, 13},
        {12, 13, 14}
    };
    public static void main(String[] args) {

        String[] board = {"P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "H", "P", "P"};

        if (PuzzleSolver(board) == false) {
            System.out.println("No Solution Found");
        }
        if (PuzzleSolver(board) == success) {
            System.out.println("Solution Found");
            PrintMovesStack();
        }
    }

    public static boolean PuzzleSolver(String[] board) {
        if (numPegs == 1) {
            return success;
        }
        
        //for all moves possible
        for(int i = 0; i < triplets.length; i++) {

            //find moves
            //each triplets[][] will show a patter on the board of PPH or HPP if neither is present then the loop is run again
            if (PPH(board, i) == true) {
                //make move
                board[triplets[i][0]] = "H";
                board[triplets[i][1]] = "H";
                board[triplets[i][2]] = "P";
                //remove a peg from the board
                numPegs--;


                if(PuzzleSolver(board) == true) {
                    //store current move on stack
                    //store sccessful move and current board in stack
                    stateStack.push(board);
                    jumpRecord.push("Peg in position " + triplets[i][0] + " jumped over " + triplets[i][1] + " to position " + triplets[i][2]);
                    
                    return true;
                }

                //remove previous addition because it did not result in a winning move
                if (jumpRecord.contains("Peg in position " + triplets[i][0] + " jumped over " + triplets[i][1] + " to position " + triplets[i][2])) {
                    jumpRecord.pop();
                    stateStack.pop();
                }   

                //return triplet to previous state since new one doesn"t lead to win
                board[triplets[i][0]] = "P";
                board[triplets[i][1]] = "P";
                board[triplets[i][2]] = "H";
                //return a peg to the board since previous move was unsuccessful
                numPegs++;
            }

            if (HPP(board, i) == true) {
                //make move
                board[triplets[i][0]] = "P";
                board[triplets[i][1]] = "H";
                board[triplets[i][2]] = "H";
                //remove a peg from the board
                numPegs--;


                if(PuzzleSolver(board) == true) {
                    //store current move on stack
                    //store sccessful move and current board in stack
                    stateStack.push(board);
                    jumpRecord.push("Peg in position " + triplets[i][2] + " jumped over " + triplets[i][1] + " to position " + triplets[i][0]);
                    
                    return true;
                }

                //remove previous addition because it did not result in a winning move
                //stateStack.pop(); 
                if (jumpRecord.contains("Peg in position " + triplets[i][2] + " jumped over " + triplets[i][1] + " to position " + triplets[i][0])) {
                    jumpRecord.pop();
                    stateStack.pop();
                }

                //return triplet to previous state since new one doesn"t lead to win
                board[triplets[i][0]] = "H";
                board[triplets[i][1]] = "P";
                board[triplets[i][2]] = "P";
                //return a peg to the board since previous move was unsuccessful
                numPegs++;
            }
        }
        return false;
    }

    //functions PPH and HPP return true if the the combinations of pegs and hols are found on in the specified triplet pair
    /* -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- */
    public static boolean PPH(String[] board, int i) {
        if (board[triplets[i][0]].equals("P") && board[triplets[i][1]].equals("P") && board[triplets[i][2]].equals("H")) {
            return true;
        }
        return false;
    }

    public static boolean HPP(String[] board, int i) {
        if (board[triplets[i][0]].equals("H") && board[triplets[i][1]].equals("P") && board[triplets[i][2]].equals("P")) {
            return true;
        }
        return false;
    }
    /* -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- */

    public static void PrintMovesStack() {
        int i = 1;
        while(!jumpRecord.isEmpty()) {
            System.out.println("Move #" + i + ": " + jumpRecord.pop());
            i++;
        }
    }
}
