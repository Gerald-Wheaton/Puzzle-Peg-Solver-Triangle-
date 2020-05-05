import java.beans.Statement;
import java.util.Scanner;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Stack;

public class PuzzlePegs {
    public static Stack<String> jumpRecord = new Stack<String>();
    public static Stack<String> stateStack = new Stack<String>();
    public static int numPegs = 14; //number of pegs minus the starting hole
    public static boolean success = true;

    //all possible moves to make in the triangle
    public static int[][] triplets = {

        //North West triplets in the triangle
        {0, 1, 3},
        {1, 3, 6},
        {3, 6, 10},
        {2, 4, 7},
        {4, 7, 11},
        {5, 8, 12},
        //North East triplets in the triangle
        {0, 2, 5},
        {2, 5, 9},
        {5, 9, 10},
        {1, 4, 8},
        {4, 8, 13},
        {3, 7, 12},
        //East to West triplets in the triangle
        {3, 4, 5},
        {6, 7, 8},
        {7, 8, 9},
        {10, 11, 12},
        {11, 12, 13},
        {12, 13, 14}
    };
    public static void main(String[] args) {

        String[] board = {"P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P"};
        int endPegPos = 13; //default starting hole position
        String[] originalBoard = new String[board.length];
        originalBoard = board.clone();

        //making adjustments to peg board based on user input
        if (args.length == 2) {
            endPegPos = Integer.parseInt(args[1]);
            board[endPegPos - 1] = "H"; //places hole in user defined location
        }
        else {
            board[endPegPos - 1] = "H"; //places peg in default location: bottom middle
        }

        if (PuzzleSolver(board, endPegPos) == false) {
            System.out.println("No Solution Found");
        }
        if (PuzzleSolver(board, endPegPos) == success) {
            System.out.println("Solution Found");
            PrintMovesStack(board, endPegPos, originalBoard);
        }
    }

    public static boolean PuzzleSolver(String[] board, int endPegPos) {
        if (numPegs == 1 && board[endPegPos - 1] == "P") {
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
                
                String[] printBoard = new String[15];
                printBoard = board.clone();

                if(PuzzleSolver(board, endPegPos) == true) {
                    //save current board to stack
                    stateStack.push("    " + printBoard[0] + "\n" + "   " + printBoard[1] + " " + printBoard[2] + "\n" + "  " + printBoard[3] + " " + printBoard[4] + " " + printBoard[5] + "\n" + " " + printBoard[6] + " " + printBoard[7] + " " + printBoard[8] + " " + printBoard[9] + "\n" + printBoard[10] + " " + printBoard[11] + " " + printBoard[12] + " " + printBoard[13] + " " + printBoard[14] + "\n");
                    //store succcessful move on stack
                    jumpRecord.push("Peg in position " + (triplets[i][0] + 1) + " jumped over " + (triplets[i][1] + 1) + " to position " + (triplets[i][2] + 1));

                    return true;
                }
                else {
                    //remove previous addition because it did not result in a winning move
                    if (jumpRecord.contains("Peg in position " + (triplets[i][0] + 1) + " jumped over " + (triplets[i][1] + 1) + " to position " + (triplets[i][2] + 1))) {
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
            }

            if (HPP(board, i) == true) {
                //make move
                board[triplets[i][0]] = "P";
                board[triplets[i][1]] = "H";
                board[triplets[i][2]] = "H";
                //remove a peg from the board
                numPegs--;

                String[] printBoard = new String[15];
                printBoard = board.clone();

                if(PuzzleSolver(board, endPegPos) == true) {
                    //save current board to stack
                    stateStack.push("    " + printBoard[0] + "\n" + "   " + printBoard[1] + " " + printBoard[2] + "\n" + "  " + printBoard[3] + " " + printBoard[4] + " " + printBoard[5] + "\n" + " " + printBoard[6] + " " + printBoard[7] + " " + printBoard[8] + " " + printBoard[9] + "\n" + printBoard[10] + " " + printBoard[11] + " " + printBoard[12] + " " + printBoard[13] + " " + printBoard[14] + "\n");
                    //store succcessful move on stack
                    jumpRecord.push("Peg in position " + (triplets[i][2] + 1) + " jumped over " + (triplets[i][1] + 1) + " to position " + (triplets[i][0] + 1));

                    return true;
                }
                else {
                    //remove previous addition because it did not result in a winning move
                    if (jumpRecord.contains("Peg in position " + (triplets[i][2] + 1) + " jumped over " + (triplets[i][1] + 1) + " to position " + (triplets[i][0] + 1))){
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
        }
        return false;
    }

    //functions PPH and HPP return true if the the combinations of pegs and hols are found on in the specified triplet pair on the board
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

    public static void PrintMovesStack(String[] board, int endPegPos, String[] originalBoard) {
        int i = 1;
        stateStack.push("    " + originalBoard[0] + "\n" + "   " + originalBoard[1] + " " + originalBoard[2] + "\n" + "  " + originalBoard[3] + " " + originalBoard[4] + " " + originalBoard[5] + "\n" + " " + originalBoard[6] + " " + originalBoard[7] + " " + originalBoard[8] + " " + originalBoard[9] + "\n" + originalBoard[10] + " " + originalBoard[11] + " " + originalBoard[12] + " " + originalBoard[13] + " " + originalBoard[14] + "\n");

        if (!stateStack.isEmpty()) {
            System.out.println("STARTING BOARD\n" + stateStack.pop());
        }
        while(!jumpRecord.isEmpty()) {
            System.out.println("Move #" + i + ": " + jumpRecord.pop() + "\n");
            i++;
            
            if (!stateStack.isEmpty()) {
                System.out.println(stateStack.pop());
            }
            System.out.println();
        }
    }
}
