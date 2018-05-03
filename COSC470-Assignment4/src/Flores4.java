//Program:      Othello
//Course:       COSC470
//Description:  Permits two programs, each using this control structure (but each with additional
//              customized classes and/or methods)to play Othello (i.e, against each other).
//Author:       
//Revised:      

import java.io.*;

//***************************************************************************************************
//***************************************************************************************************
//Class:        Othello
//Description:  Main class for the program. Allows set-up and plays one side.

public class Flores4 {
    
    public static char myColor = '?';           //B (black) or W (white) - ? means not yet selected
    public static char opponentColor = '?';     //ditto but opposite
    
    //INSERT ANY ADDITIONAL GLOBAL VARIABLES HERE
    //===========================================
    //===========================================
    public static int playMode = 2;
    
    //===========================================
    //===========================================
    //***************************************************************************************************
    //Method:		main
    //Description:	Calls routines to play Othello
    //Parameters:	none
    //Returns:		nothing
    //Calls:        loadBoard, saveBoard, showBoard, constructor in Board class
    //              getCharacter, getInteger, getKeyboardInput, constructor in KeyboardInputClass
    public static void main(String args[]) {
        //INSERT ANY ADDITIONAL CONTROL VARIABLES HERE
        //============================================
        //============================================
        
        int depth = 0;
        
        //Board myBoard;
        
        //============================================
        //============================================
        
        KeyboardInputClass keyboardInput = new KeyboardInputClass();
        int pollDelay = 250;
        long moveStartTime, moveEndTime, moveGraceTime = 10000;     //times in milliseconds
        Board currentBoard = Board.loadBoard();
        String myMove = "", myColorText = "";
        System.out.println("--- Othello ---");
        System.out.println("Player: Edgar Flores\n");
        if (currentBoard != null) {                                 //board found, make sure it can be used
            if (currentBoard.status == 1) {                          //is a game in progress?   
                if (keyboardInput.getCharacter(true, 'Y', "YN", 1, "A game appears to be in progress. Abort it? (Y/N (default = Y)") == 'Y') {
                    currentBoard = null;
                } else {
                    System.out.println("Exiting program. Try again later...");
                    System.exit(0);
                }
            }
        }
        if ((currentBoard == null) || (currentBoard.status == 2)) {   //create a board for a new game
            int rows = 8;
            int cols = 8;
            if (keyboardInput.getCharacter(true, 'Y', "YN", 1, "Use standard board? (Y/N: default = Y):") == 'N') {
                rows = keyboardInput.getInteger(true, rows, 4, 26, "Specify the number of rows for the board (default = " + rows + "):");
                cols = keyboardInput.getInteger(true, cols, 4, 26, "Specify the number of columns for the board (default = " + cols + "):");
            }
            int maxTime = 60;
            maxTime = keyboardInput.getInteger(true, maxTime, 10, 600, "Max time (seconds) allowed per move (Default = " + maxTime + "):");
            currentBoard = new Board(rows, cols, maxTime);
            while (currentBoard.saveBoard() == false) {
            }               //try until board is saved (necessary in case of access conflict)
        }

        //INSERT CODE HERE FOR ANY ADDITIONAL SET-UP OPTIONS
        //==================================================
        //==================================================
        //show defaults
        //  - show play mode
        if (playMode == 1) {
            System.out.println("Play Mode: Intelligent Mode");
            depth = 8;
        } else if (playMode == 2) {
            System.out.println("Play Mode: Random Mode");
            depth = 1;
        } else if (playMode == 3) {
            System.out.println("Play Mode: Manual Mode");
        }
        System.out.println("");
        //  - show max depth search level
        //give option to change
        //  - change play mode
        //      1. Intellegent
        //      2. Random
        //      3. Manuel
        char s = keyboardInput.getCharacter(true, 'Y', "ynYN", 1, "Play with current settings? (Y/N (default = Y)");
        boolean settings = (s == 'Y') ? settings = true : false;
        if (!settings) {
            System.out.println("");
            System.out.println("Enter play Mode: (Default = Random Mode)\n");
            //System.out.println("1) Intelligent Mode");
            System.out.println("2 = Random Mode");
            System.out.println("3 = Manual Mode");
            playMode = keyboardInput.getInteger(true, 2, 1, 3, "");
            System.out.println("");
        }
        //  - Change depth level
        
//        
//        myBoard = new myBoard(currentBoard.boardRows, currentBoard.boardRows, currentBoard.maxMoveTime);
//        
//        myBoard.
//        
//        for (int i = 0; i < args.length; i++) {
//            for
//            
//        }
//        
//        myBoard.board[][];
        //==================================================
        //==================================================
        //At this point set-up must be in progress so colors can be assigned
        if (currentBoard.colorSelected == '?') {                    //if no one has chosen a color yet, choose one (player #1)
            myColor = keyboardInput.getCharacter(true, 'B', "BW", 1, "Select color: B=Black; W=White (Default = Black):");
            currentBoard.colorSelected = myColor;

            while (currentBoard.saveBoard() == false) {
            }               //try until the board is saved
            System.out.println("You may now start the opponent's program...");
            while (currentBoard.status == 0) {                      //wait for other player to join in
                currentBoard = null;                                //get the updated board
                while (currentBoard == null) {
                    currentBoard = Board.loadBoard();
                }
            }
        } else {                                                      //otherwise take the other color (this is player #2)
            if (currentBoard.colorSelected == 'B') {
                myColor = 'W';
            } else {
                myColor = 'B';
            }
            currentBoard.status = 1;                                //by now, both players are engaged and play can begin
            while (currentBoard.saveBoard() == false) {
            }               //try until the board is saved
        }

        if (myColor == 'B') {
            myColorText = "Black";
            opponentColor = 'W';
        } else {
            myColorText = "White";
            opponentColor = 'B';
        }
        System.out.println("This player will be " + myColorText + "\n");

        //INSERT CODE HERE FOR ANY ADDITIONAL OUTPUT OPTIONS
        //==================================================
        //==================================================
        //==================================================
        //==================================================
        //Now play can begin. (At this point each player should have an identical copy of currentBoard.)
        while (currentBoard.status == 1) {
            if (currentBoard.whoseTurn == myColor) {
                if (currentBoard.whoseTurn == 'B') {
                    System.out.println("Black's turn to move...");
                } else {
                    System.out.println("White's turn to move");
                }
                currentBoard.showBoard();
                String previousMove = currentBoard.move;
                moveStartTime = System.currentTimeMillis();

                //CALL METHOD(S) HERE TO SELECT AND MAKE A VALID MOVE
                //===================================================
                //===================================================
                
                
                switch (playMode) {
                    case 1:
                        //System.out.println("Play Mode: Intelligent Mode ");
                        break;
                    case 2:
                        //System.out.println("Play Mode: Random Mode");
                        
                        myMove = GameRules.main(currentBoard, myColor, opponentColor, playMode, depth);
                        break;
                    case 3:
                       // System.out.println("Play Mode: Manual Mode");
                        myMove = GameRules.main(currentBoard, myColor, opponentColor, playMode, depth);
                        break;
                    default:
                        break;
                }
                //===================================================
                //===================================================
                //YOU MAY ADD NEW CLASSES AND/OR METHODS BUT DO NOT
                //CHANGE ANY EXISTING CODE BELOW THIS POINT
                moveEndTime = System.currentTimeMillis();
                if ((moveEndTime - moveStartTime) > (currentBoard.maxMoveTime * 1000 + moveGraceTime)) {
                    System.out.println("\nMaximum allotted move time exceeded--Opponent wins by default...\n");
                    keyboardInput.getKeyboardInput("\nPress ENTER to exit...");
                    currentBoard.status = 2;
                    while (currentBoard.saveBoard() == false) {
                    }       //try until the board is saved
                    System.exit(0);
                }

                if (myMove.length() != 0) {
                    System.out.println(myColorText + " chooses " + myMove + "\n");
                    currentBoard.showBoard();
                    System.out.println("Waiting for opponent's move...\n");
                } else {
                    if (previousMove.length() == 0) {               //neither player can move
                        currentBoard.status = 2;                    //game over...
                        System.out.println("\nGame over!");
                        int blackScore = 0;
                        int whiteScore = 0;
                        for (int r = 0; r < currentBoard.boardRows; r++) {
                            for (int c = 0; c < currentBoard.boardCols; c++) {
                                if (currentBoard.board[r][c] == 'B') {
                                    blackScore++;
                                } else if (currentBoard.board[r][c] == 'W') {
                                    whiteScore++;
                                }
                            }
                        }
                        if (blackScore > whiteScore) {
                            System.out.println("Blacks wins " + blackScore + " to " + whiteScore);
                        } else if (whiteScore > blackScore) {
                            System.out.println("White wins " + whiteScore + " to " + blackScore);
                        } else {
                            System.out.println("Black and White tie with scores of " + blackScore + " each");
                        }
                    } else {
                        System.out.println("No move available. Opponent gets to move again...");
                    }
                }
                currentBoard.move = myMove;
                currentBoard.whoseTurn = opponentColor;
                while (currentBoard.saveBoard() == false) {
                }           //try until the board is saved
            } else {                                                   //wait a moment then poll again
                try {
                    Thread.sleep(pollDelay);
                } catch (Exception e) {
                }
            }
            currentBoard = null;                                    //get the updated board
            while (currentBoard == null) {
                currentBoard = Board.loadBoard();
            }
        }
        keyboardInput.getKeyboardInput("\nPress ENTER to exit...");
    }
    //***************************************************************************************************
}

//*******************************************************************************************************
//*******************************************************************************************************
//Class:        Board
//Description:  Othello board and related parms
class Board implements Serializable {
    char status;        //0=set-up for a new game is in progress; 1=a game is in progress; 2=game is over
    char whoseTurn;     //'?'=no one's turn yet--game has not begun; 'B'=black; 'W'=white
    String move;        //the move selected by the current player (as indicated by whoseTurn)
    char colorSelected; //'B' or 'W' indicating the color chosen by the first player to access the file
                        //for a new game ('?' if neither player has yet chosen a color)
                        //Note: this may or may not be the color for the player accessing the file
    int maxMoveTime;    //maximum time allotted for a move (in seconds)
    int boardRows;      //size of the board (allows for variations on the standard 8x8 board)
    int boardCols;
    char board[][];     //the board. Positions are filled with: blank = no piece; 'B'=black; 'W'=white
    //***************************************************************************************************
    //Method:       Board
    //Description:  Constructor to create a new board object
    //Parameters:	rows - size of the board
    //              cols
    //              time - maximum time (in seconds) allowed per move
    //Calls:		nothing
    //Returns:		nothing
    Board(int rows, int cols, int time){
        int r,c;
        status = 0;
        whoseTurn = 'B';        //Black always makes the first move
        move = "*";
        colorSelected = '?';
        maxMoveTime = time;
        boardRows = rows;
        boardCols = cols;
        board = new char[boardRows][boardCols];
        for (r = 0; r < boardRows; r++)
            for (c = 0; c < boardCols; c++)
                board[r][c] = ' ';
        r = boardRows/2 - 1;
        c = boardCols/2 - 1;
        board[r][c] = 'W';
        board[r][c+1] = 'B';
        board[r+1][c] = 'B';
        board[r+1][c+1] = 'W';
    }
    //***************************************************************************************************
    //Method:       saveBoard
    //Description:  Saves the current board to disk as a binary file named "OthelloBoard"
    //Parameters:	none
    //Calls:		nothing
    //Returns:		true if successful; false otherwise
    public boolean saveBoard() {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("OthelloBoard"));
            outStream.writeObject(this);
            outStream.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    //***************************************************************************************************
    //Method:       loadBoard
    //Description:  Loads the current Othello board and data from a binary file
    //Parameters:   none
    //Calls:        nothing
    //Returns:      a Board object (or null if routine is unsuccessful)
    public static Board loadBoard() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("OthelloBoard"));
            Board boardObject = (Board) inStream.readObject();
            inStream.close();
            return boardObject;
        } catch (Exception e) { }
        return null;
    }
    //***************************************************************************************************
    //Method:       showBoard
    //Description:  Displays the current Othello board using extended Unicode characters. Looks fine
    //               in a command window but may not display well in the NetBeans IDE...
    //Parameters:   none
    //Calls:        nothing
    //Returns:      nothing
    public void showBoard() {
        int r,c;
        System.out.print("  ");                         //column identifiers
        for (c = 0; c < boardCols; c++){
            System.out.print(" "+(char)(c+65));
        }
        System.out.println();
        
        //top border
        System.out.print("  "+(char)9484);                   //top left corner \u250C
        for (c = 0; c < boardCols - 1; c++){
            System.out.print((char)9472);               //horizontal \u2500
            System.out.print((char)9516);               //vertical T \u252C
        }
        System.out.print((char)9472);                   //horizontal \u2500
        System.out.println((char)9488);                 //top right corner \u2510
       
        //board rows
        for (r = 0; r < boardRows; r++) {
            System.out.print(" "+(char)(r+65));         //row identifier
            System.out.print((char)9474);               //vertical \u2502
            for (c = 0; c < boardCols; c++){
                System.out.print(board[r][c]);
                System.out.print((char)9474);           //vertical \u2502
            }
            System.out.println();
            
            //insert row separators
            if (r < boardRows - 1) {
                System.out.print("  "+(char)9500);           //left T \u251C
                for (c = 0; c < boardCols - 1; c++){
                    System.out.print((char)9472);       //horizontal \u2500
                    System.out.print((char)9532);       //+ (cross) \u253C
                }
                System.out.print((char)9472);           //horizontal \u2500
                System.out.println((char)9508);         //right T \u2524
            }
        }

        //bottom border
        System.out.print("  "+(char)9492);                   //lower left corner \u2514
        for (c = 0; c < boardCols - 1; c++){
            System.out.print((char)9472);               //horizontal \u2500
            System.out.print((char)9524);               //upside down T \u2534
        }
        System.out.print((char)9472);                   //horizontal \u2500
        System.out.println((char)9496);                 //lower right corner \u2518
        
        return;
    }
    //***************************************************************************************************
}
//*******************************************************************************************************
//**********************************************************************************************************