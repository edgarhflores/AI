//Program:      GameBoard.java
//Course:       COSC470
//Description:	Assignment 2 - 8 Puzzel
//Author:       Edgar Flores
//Revised:      2/27/2018
//Language:     Java
//IDE:          NetBeans 8.2
//Notes:        This program creates a 8, 15, 24, or 35 puzzle and prefroms actions
//              pertaining to manipulating the puzzel. 
//******************************************************************************
//******************************************************************************

//Class:        GameBoard
//Description:	Main class for the program
//******************************************************************************
//******************************************************************************
public class GameBoard {

    String board[];
    String goalBoard[];
    int space; // the index of blank tile
    int dem; // the deminsions of the board
    int previousShuffleMove; // the previous shuffle move that was made 
    GameBoard parent = null;
    //********************************************************************
    //Method:       GameBoard
    //Description:  constructor for the game board
    //Parameters:   boardSize - the size of the puzzel board to build
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      board, space 

    public GameBoard(int boardSize, int manual) {
        KeyboardInputClass input = new KeyboardInputClass();
        board = new String[boardSize + 1];
        goalBoard = new String[boardSize + 1];
        dem = (int) Math.sqrt(board.length + 0.0);
        if (manual != -1) {
            for (int i = 0; i < board.length; i++) {
                if (i == board.length - 1) {
                    board[i] = " ";
                    goalBoard[i] = " ";
                    space = i;
                } else {
                    board[i] = Integer.toString(i + 1);
                    goalBoard[i] = Integer.toString(i + 1);
                }//end of else
            }//end of for loop
        } else {
            int row = 1;
            int col = 1;
            for (int i = 0; i < board.length; i++) {
                board[i] = input.getString(" ", "Enter tile for position " + row + "," + col);
                col++;
                if ((i + 1) % dem == 0) {
                    row++;
                    col = 1;
                }
            }
        }
    }//end of constructor

    //********************************************************************
    //Method:       printBoard
    //Description:  prints the board
    //Parameters:   none
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      dem
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            int a = i + 1;// index to be used for modulo
            String t = board[i];
            System.out.printf("%5s", t);
            if (a % dem == 0)// prints tiles on a board until row finishes 
                System.out.println();
        }
        System.out.println();
    }

    //********************************************************************
    //Method:       moveATile
    //Description:  makes one vaild move on the board puzzel
    //Parameters:   none
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      board, space, dem, previousShuffleMove
    public boolean moveATile(int dir, boolean random) {
        boolean isShuffled = true;
        boolean isValid = false;
        do{
            
            if(random == true){
                dir = (int) (Math.random() * (5 - 1) + 1);
            }else{
                isShuffled = false;
            }
            
            switch (dir) {
                
                case 1://up
                    if (space - dem >= 0 && previousShuffleMove != 3) {
                        isShuffled = swapATile((space - dem), 1);
                        isValid = true;
                    }
                    break;
                case 2:// east
                    if (space + 1 < board.length && (space + 2) % dem != 1 && previousShuffleMove != 4) {
                        isShuffled = swapATile((space + 1), 2);
                        isValid = true;
                    }
                    break;
                case 3://south
                    if (space + dem < board.length && previousShuffleMove != 1) {
                        isShuffled = swapATile((space + dem), 3);
                        isValid = true;
                    }
                    break;
                case 4://west
                    if (space - 1 >= 0 && space % dem != 0 && previousShuffleMove != 2) {
                        isShuffled = swapATile((space - 1), 4);
                        isValid = true;
                    }
            }//end of switch
        }while (isShuffled);
        return isValid;
    }// end of moveATile

    private boolean swapATile(int vaildTilePos, int dir) {
        String vaildTile;
        String openTile;
        vaildTile = board[vaildTilePos];
        openTile = board[space];
        board[vaildTilePos] = openTile;
        board[space] = vaildTile;
        space = vaildTilePos;
        previousShuffleMove = dir;
        return false ;
    }

    public void getParent(GameBoard parent){
        this.parent = parent;
    }
}// end of class
