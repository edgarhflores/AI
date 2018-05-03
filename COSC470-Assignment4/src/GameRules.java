
public class GameRules {

    public static char myColor = '?';           //B (black) or W (white) - ? means not yet selected
    public static char opponentColor = '?';     //ditto but opposite
    public static int opponentDiscCount = 0;
    public static int north = 0;
    public static int northEast = 1;
    public static int east = 2;
    public static int southEast = 3;
    public static int south = 4;
    public static int southWest = 5;
    public static int west = 6;
    public static int northWest = 7;
    public static int playMode = 0;
    public static int row2 = 0;

    public static String main(Board b, char myColor, char opponentColor, int playMode, int depth) {
        
        KeyboardInputClass keyboardInput = new KeyboardInputClass();
        GameRules.myColor = myColor;
        GameRules.opponentColor = opponentColor;
        GameRules.playMode = playMode;
        String myMove = "";

        switch (playMode) {
            case 1:
                //myMove = aiPlayer(b);
                break;
            case 2:
                //System.out.println("Play Mode: Random Mode");
                myMove = generateMoves(b);
                //myMove = randomMove(b, 1);
                break;
            case 3:
                char rowText = keyboardInput.getCharacter(true, ' ', "ABCDEFGH", 1, "Enter row: ");
                char colText = keyboardInput.getCharacter(true, ' ', "ABCDEFGH", 1, "Enter column: ");
                System.out.println("");
                //must translate the alpahet entered into an actual number
                int row = (int) rowText - 65;
                int col = (int) colText - 65;
                if (checkMove(b, row, col)) {
                    myMove = "" + rowText + colText;
                }
                break;
            default:
                break;
        }

        return myMove;
    }// end of main

    // Checks to see that the place where the user placed the disc can outflank another disc  /,
    private static boolean checkMove(Board b, int row, int col) {

        //System.out.println("Check N...\n");
        boolean isN = check(b, (row - 1), col, north);
        //System.out.println("Check NE...\n");
        boolean isNE = check(b, (row - 1), (col + 1), northEast);
        boolean isE = check(b, row, (col + 1), east);
        boolean isSE = check(b, (row + 1), (col + 1), southEast);
        boolean isS = check(b, (row + 1), col, south);
        boolean isSW = check(b, (row + 1), (col - 1), southWest);
        boolean isW = check(b, row, (col - 1), west);
        boolean isNW = check(b, (row - 1), (col - 1), northWest);

        return outFlank(b, row, col, isN, isNE, isE, isSE, isS, isSW, isW, isNW);

    }

    private static boolean outFlank(Board b, int row, int col, boolean isN, boolean isNE, boolean isE, boolean isSE, boolean isS, boolean isSW, boolean isW, boolean isNW) {
        boolean isValid = false;

        if (isN) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, row - 1, col, north);
        }
        if (isNE) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, (row - 1), (col + 1), northEast);
        }
        if (isE) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, row, (col + 1), east);
        }
        if (isSE) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, (row + 1), (col + 1), southEast);
        }
        if (isS) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, (row + 1), col, south);
        }
        if (isSW) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, (row + 1), (col - 1), southWest);
        }
        if (isW) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, row, (col - 1), west);
        }
        if (isNW) {
            isValid = true;
            b.board[row][col] = myColor;
            flipDiscs(b, (row - 1), (col - 1), northWest);
        }

        return isValid;
    }

    private static boolean check(Board b, int currRow, int currCol, int dir) {
        boolean isValid = false;
        System.out.println("Preforming Check...");
        switch (dir) {
            case 0: //n
                System.out.println("Check N...");
                break;
            case 1://ne
                System.out.println("Check NE...");
                break;
            case 2://e
                System.out.println("Check E...");
                break;
            case 3://se
                System.out.println("Check SE...");
                break;
            case 4://s
                System.out.println("Check S...");
                break;
            case 5://sw
                System.out.println("Check SW...");
                break;
            case 6://w
                System.out.println("Check W...");
                break;
            case 7://nw
                System.out.println("Check NW...");
                break;
        }// end of switch  
        if (currRow < b.boardRows && currRow >= 0 && currCol < b.boardCols && currCol >= 0) {
            //System.out.println("row = " + currRow + "" + " is less than totalRows = " + b.boardRows);
            System.out.println("board[" + currRow + "][" + currCol + "] = " + b.board[currRow][currCol]);
            //System.out.println("opponentColor = " + opponentColor);
            boolean isEqual = b.board[currRow][currCol] == opponentColor;
            System.out.println("board[currRow][currCol] == opponentColor? " + isEqual);
            if (b.board[currRow][currCol] == opponentColor) {
                opponentDiscCount++;
                switch (dir) {
                    case 0: //n
                        // System.out.println("Check N...\n");
                        return check(b, currRow - 1, currCol, dir);
                    case 1://ne
                        //System.out.println("Check NE...\n");
                        return check(b, currRow - 1, currCol + 1, dir);
                    case 2://e
                        //System.out.println("Check E...\n");
                        return check(b, currRow, currCol + 1, dir);
                    case 3://se
                        // System.out.println("Check E...\n");
                        return check(b, currRow + 1, currCol + 1, dir);
                    case 4://s
                        //System.out.println("Check S...\n");
                        return check(b, currRow + 1, currCol, dir);
                    case 5://sw
                        // System.out.println("Check SW...\n");
                        return check(b, currRow + 1, currCol - 1, dir);
                    case 6://w
                        // System.out.println("Check W...\n");
                        return check(b, currRow, currCol - 1, dir);
                    case 7://nw
                        // System.out.println("Check NW...\n");
                        return check(b, currRow - 1, currCol - 1, dir);
                }// end of switch               
            }// end of opponent color check
            //System.out.println("\nThis new space is not the same as opponentColor...");
            //System.out.println("board[" + currRow + "][" + currCol + "] = " + b.board[currRow][currCol]);
            isEqual = b.board[currRow][currCol] == myColor;
            //System.out.println("myColor = " + myColor);
            System.out.println("board[currRow][currCol] == myColor? " + isEqual);
            if (b.board[currRow][currCol] == myColor) {
                //System.out.println("");
                isEqual = opponentDiscCount > 0;
                System.out.println("Was at least one opponent disc counted? " + isEqual);
                if (opponentDiscCount > 0) {
                    isValid = true;
                }//end of more than one disc counted check
            }//end of my color check
        }//end of bounderies check
        //System.out.println("Returning isValid");
        System.out.println("isValid = " + isValid);
        System.out.println("");
        opponentDiscCount = 0;
        return isValid;
    }// end of check

    private static void flipDiscs(Board b, int currRow, int currCol, int dir) {
        //System.out.println("Flipping discs, dir: up...");

        if (currRow < b.boardRows && currRow >= 0 && currCol < b.boardCols && currCol >= 0) {

            if (b.board[currRow][currCol] == opponentColor) {

                // System.out.println("Before change: board["+currRow+"]["+currCol+"] = "+b.board[currRow][currCol]);
                b.board[currRow][currCol] = myColor;

                // System.out.println("After change: board["+currRow+"]["+currCol+"] = "+b.board[currRow][currCol]);
                switch (dir) {
                    case 0: //n
                        flipDiscs(b, currRow - 1, currCol, dir);
                        break;
                    case 1://ne
                        flipDiscs(b, currRow - 1, currCol + 1, dir);
                        break;
                    case 2://e
                        flipDiscs(b, currRow, currCol + 1, dir);
                        break;
                    case 3://se
                        flipDiscs(b, currRow + 1, currCol + 1, dir);
                        break;
                    case 4://s
                        flipDiscs(b, currRow + 1, currCol, dir);
                        break;
                    case 5://sw
                        flipDiscs(b, currRow + 1, currCol - 1, dir);
                        break;
                    case 6://w
                        flipDiscs(b, currRow, currCol - 1, dir);
                        break;
                    case 7://nw
                        flipDiscs(b, currRow - 1, currCol - 1, dir);
                        break;
                }// end of switch 
            }// end of opponent color check

//            if (b.board[currRow][currCol] == myColor) {
//                if (opponentDiscCount > 0) {
//
//                }// end of if opponent disc count
//            }// end of if statement

        }// end of check boundries

//        System.out.println("Done");
//        System.out.println("");
    }// end of flip discs

    private static String randomMove(Board b, int depth) {
        //System.out.println("In random move");
        String move = "";
        //LList children = new LList();
        move = generateMoves(b);
        //System.out.println("Random moves generated: " + children.getLength());
        //for (int i = 1; i < children.getLength() + 1; i++) {
        //Board child = (Board) children.getEntry(i);
        // child.setHeuristic = 
        //assignHeuristic(child);
        //open.enqueue(child);
        //}
        return move;
    }

    private static String generateMoves(Board b) {
        int[][] movePos = new int[100][2];
        String move;
        int row = 0;
        int col = 0;
        for (int i = 0; i < b.boardRows; i++) {
            for (int j = 0; j < b.boardCols; j++) {
                if (b.board[i][j] == myColor) {
                    System.out.println("Mycolor found at: row [" + i + "] col [" + j + "]");
                    checkMove2(b, i, j, movePos);
//                    if (isVaild) {
//                        movePos[0][row] = i;
//                        movePos[1][row] = j;
//                        row++;
//                    }// end of inner if
                }// end of outter if               
            }// end of for  for col      
        }// end of for for row
        System.out.println("Moves generated: " + row2);
        System.out.println("Valid moves are: ");
        for (int i = 0; i < row2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(movePos[i][j] + "  ");
            }
            System.out.println("");
        }
   
        int random = (int) (Math.random() * row2); 
        
        row = movePos[random][0];
        col = movePos[random][1];
        
        System.out.println("Move choosen is: " + random);
        System.out.println("row: " + row);
        System.out.println("col: " + col);        
        
        char rowText = (char) (row + 65);
        char colText = (char) (col + 65);
        
//        if(checkMove(b, row, col)){
//            move = "" + rowText + colText;
//        }
        
        
        if(row2 == 0){
            move = "";
        }else{
            checkMove(b, row, col);
            move = "" + rowText + colText;
        }
        
        row2 = 0; 
        
        return move;
    }// end of method

    private static void checkMove2(Board b, int row, int col, int [][] movePos) {
        //System.out.println("Check N...\n");
        boolean isN = check2(b, (row - 1), col, north, movePos);
        //System.out.println("Check NE...\n");
        boolean isNE = check2(b, (row - 1), (col + 1), northEast, movePos);
        boolean isE = check2(b, row, (col + 1), east, movePos);
        boolean isSE = check2(b, (row + 1), (col + 1), southEast, movePos);
        boolean isS = check2(b, (row + 1), col, south, movePos);
        boolean isSW = check2(b, (row + 1), (col - 1), southWest, movePos);
        boolean isW = check2(b, row, (col - 1), west, movePos);
        boolean isNW = check2(b, (row - 1), (col - 1), northWest, movePos);

        //store(b, row, col, isN, isNE, isE, isSE, isS, isSW, isW, isNW, movePos);
    }

    private static boolean check2(Board b, int currRow, int currCol, int dir, int [][] movePos) {
        boolean isValid = false;
        //System.out.println("Preforming Check...");
//        switch (dir) {
//            case 0: //n
//                
//                //System.out.println("Check N...\n");
//                break;
//            case 1://ne
//                
//                //System.out.println("Check NE...\n");
//                break;
//            case 2://e
//                //System.out.println("Check E...\n");
//                break;
//            case 3://se
//                //System.out.println("Check SE...\n");
//                break;
//            case 4://s
//                //System.out.println("Check S...\n");
//                break;
//            case 5://sw
//                //System.out.println("Check SW...\n");
//                break;
//            case 6://w
//                //System.out.println("Check W...\n");
//                break;
//            case 7://nw
//                //System.out.println("Check NW...\n");
//                break;
//        }// end of switch  

        if (currRow < b.boardRows && currRow >= 0 && currCol < b.boardCols && currCol >= 0) {
           // System.out.println("row = " + currRow + "" + " is less than totalRows = " + b.boardRows);
           // System.out.println("board[" + currRow + "][" + currCol + "] = " + b.board[currRow][currCol]);
            //System.out.println("opponentColor = " + opponentColor);
            boolean isEqual = b.board[currRow][currCol] == opponentColor;
            //System.out.println("Does current Position equal oppent Color? " + isEqual);
            if (b.board[currRow][currCol] == opponentColor) {
                opponentDiscCount++;
                switch (dir) {
                    case 0: //n
                       // System.out.println("Check N...\n");
                        return check2(b, currRow - 1, currCol, dir, movePos);
                    case 1://ne
                        //System.out.println("Check NE...\n");
                        return check2(b, currRow - 1, currCol + 1, dir, movePos);
                    case 2://e
                        //System.out.println("Check E...\n");
                        return check2(b, currRow, currCol + 1, dir, movePos);
                    case 3://se
                       // System.out.println("Check E...\n");
                        return check2(b, currRow + 1, currCol + 1, dir, movePos);
                    case 4://s
                        //System.out.println("Check S...\n");
                        return check2(b, currRow + 1, currCol, dir, movePos);
                    case 5://sw
                       // System.out.println("Check SW...\n");
                        return check2(b, currRow + 1, currCol - 1, dir, movePos);
                    case 6://w
                       // System.out.println("Check W...\n");
                        return check2(b, currRow, currCol - 1, dir, movePos);
                    case 7://nw
                       // System.out.println("Check NW...\n");
                        return check2(b, currRow - 1, currCol - 1, dir, movePos);
                }// end of switch               
            }// end of opponent color check
            //System.out.println("\nCurrent position is not the same as opponentColor...");
            //System.out.println("board[" + currRow + "][" + currCol + "] = " + b.board[currRow][currCol]);
            isEqual = b.board[currRow][currCol] == ' ';
            //System.out.println("Is current position a blank? " + isEqual);
            if (b.board[currRow][currCol] == ' ') {
                isEqual = opponentDiscCount > 0;
                //System.out.println("Was at least one opponent disc counted? " + isEqual);
                if (opponentDiscCount > 0) {
                    isValid = true;
                }//end of more than one disc counted check
            }//end of my color check
        }//end of bounderies check
        //System.out.println("Returning isValid");
        
        if(isValid){
            System.out.println("board[" + currRow + "][" + currCol + "] ");
            movePos[row2][0]=  currRow;
            movePos[row2][1]=  currCol;
            row2++; 
        } 
//        System.out.println("isValid = " + isValid);
//        System.out.println("");
        opponentDiscCount = 0;
        return isValid;
    }// end of check
    
    private static String AIMove(Board board1, int depth) {
        //System.out.println("Working...");
        //need a variablo flip flop from current player to oppenent player I think that is myColor and oppenetColor
        boolean loop = true;
        LinkedQueue open = new LinkedQueue();
        open.enqueue(board1);
        LList closed = new LList();
        LList children = null;
        //LList goalPath = null;

        while (!open.isEmpty() && loop) {
            Board x = (Board) open.dequeue();
            if (depth == 1) { // this needs to stop at the cut off point so like depth = user cut off point
                loop = false;
            } else {
                children = new LList();
                generateChildren(x, children);
                checkChildren(children, open, closed);              
                //for (int i = 1; i < children.getLength() + 1; i++) {
                //Board child = (Board) children.getEntry(i);
                //child.setHeuristic = 
                //assignHeuristic(child);   
                //open.enqueue(child);
                //}               
            }
        }
        return "";
    }

    private static boolean success(Board x) {
        checkMove(x, 1, 2);
        return false;
    }

    private static void generateChildren(Board x, LList children) {
        throw new UnsupportedOperationException("jhvvjjvjhjvjvjvjjhvjvjvjvjhjhvjvhvjhvjvhvhvvvhjvvvhjvhvjvvhvhvvv."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void checkChildren(LList children, LinkedQueue open, LList closed) {

    }

    private static void assignHeuristic(Board child) {

    }

}// end of class
