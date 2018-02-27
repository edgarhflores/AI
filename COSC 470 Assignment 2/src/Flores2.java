
public class Flores2 {

    public static void main(String[] args) {
        KeyboardInputClass input = new KeyboardInputClass();

        //Step 1
        //  Know the size of the board to create. The size will be either 8, 15, 24, or 35
        int boardSize = input.getInteger(false, 8, 0, 0, "Enter board size: ");
        int shuffleMoves = input.getInteger(false, 8, 0, 0, "Enter how many moves to shuffle the board: ");
        GameBoard board1 = new GameBoard(boardSize);
        board1.printBoard();

        for (int i = 0; i < shuffleMoves; i++) {
            System.out.printf("---Shuffle move %d---", (i+1));
            System.out.println();
            board1.shuffleATile();
            board1.printBoard();
        }

        //shuffleBoard(board);
    }//end of main

    //Step 3 
    // Shuffle the board
    private static void shuffleBoard(String[][] board) {

    }

}//end of class

//Step 4
//  Solve the board
