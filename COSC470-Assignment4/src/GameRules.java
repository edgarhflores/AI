
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

    public static Board main(Board b, char myColor, char opponentColor, int playMode, int depth) {

        KeyboardInputClass keyboardInput = new KeyboardInputClass();
        GameRules.myColor = myColor;
        GameRules.opponentColor = opponentColor;
        GameRules.playMode = playMode;
        String myMove;

        switch (playMode) {
            case 1:
                myMove = aiPlayer(b, 1);
                b.move = myMove;
                break;
            case 2:
                //System.out.println("Play Mode: Random Mode");
                //myMove = generateMoves(b);
                myMove = randomMove(b);
                b.move = myMove;
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
                    b.move = myMove;
                }
                break;
            default:
                break;
        }

        return b;
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

    private static String generateMoves(Board b, int[][] moves) {
        //int[][] movePos = new int[100][2];
        String move = "";
        int row = 0;
        int col = 0;
        for (int i = 0; i < b.boardRows; i++) {
            for (int j = 0; j < b.boardCols; j++) {
                if (b.board[i][j] == myColor) {
                    System.out.println("Mycolor found at: row [" + i + "] col [" + j + "]");
                    checkMove2(b, i, j, moves);
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
                System.out.print(moves[i][j] + "  ");
            }
            System.out.println("");
        }

        char rowText = (char) (row + 65);
        char colText = (char) (col + 65);

        if (playMode == 2) {
            int random = (int) (Math.random() * row2);

            row = moves[random][0];
            col = moves[random][1];

            System.out.println("Move choosen is: " + random);
            System.out.println("row: " + row);
            System.out.println("col: " + col);

            if (row2 == 0) {
                move = "";
                row2 = 0;
            } else {
                checkMove(b, row, col);
                move = "" + rowText + colText;
                row2 = 0;
            }
        } else if (playMode == 3) {
            move = "" + rowText + colText;
        }
        return move;
    }// end of method

    private static void checkMove2(Board b, int row, int col, int[][] movePos) {
        check2(b, (row - 1), col, north, movePos);
        check2(b, (row - 1), (col + 1), northEast, movePos);
        check2(b, row, (col + 1), east, movePos);
        check2(b, (row + 1), (col + 1), southEast, movePos);
        check2(b, (row + 1), col, south, movePos);
        check2(b, (row + 1), (col - 1), southWest, movePos);
        check2(b, row, (col - 1), west, movePos);
        check2(b, (row - 1), (col - 1), northWest, movePos);
    }

    private static void check2(Board b, int currRow, int currCol, int dir, int[][] movePos) {
        boolean isValid = false;
        if (currRow < b.boardRows && currRow >= 0 && currCol < b.boardCols && currCol >= 0) {
            if (b.board[currRow][currCol] == opponentColor) {
                opponentDiscCount++;
                switch (dir) {
                    case 0: //n
                        check2(b, currRow - 1, currCol, dir, movePos);
                        break;
                    case 1://ne
                        check2(b, currRow - 1, currCol + 1, dir, movePos);
                        break;
                    case 2://e
                        check2(b, currRow, currCol + 1, dir, movePos);
                        break;
                    case 3://se
                        check2(b, currRow + 1, currCol + 1, dir, movePos);
                        break;
                    case 4://s
                        check2(b, currRow + 1, currCol, dir, movePos);
                        break;
                    case 5://sw
                        check2(b, currRow + 1, currCol - 1, dir, movePos);
                        break;
                    case 6://w
                        check2(b, currRow, currCol - 1, dir, movePos);
                        break;
                    case 7://nw
                        check2(b, currRow - 1, currCol - 1, dir, movePos);
                        break;
                }// end of switch               
            }// end of opponent color check
            if (b.board[currRow][currCol] == ' ') {
                if (opponentDiscCount > 0) {
                    isValid = true;
                }//end of more than one disc counted check
            }//end of my color check
        }//end of bounderies check
        if (isValid) {
            boolean found = false;
            //System.out.println("board[" + currRow + "][" + currCol + "] ");
            for (int i = 0; i < row2; i++) {
                if (movePos[i][0] == currRow && movePos[i][1] == currCol) {
                    found = true;
                }

            }
            if (!found) {
                movePos[row2][0] = currRow;
                movePos[row2][1] = currCol;
                row2++;
            }
        }
        opponentDiscCount = 0;
    }// end of check

    private static String aiPlayer(Board b, int depth) {
        System.out.println("In AI player..");
        int[][] moves = new int[1000][2];
        int level = 0;
        //System.out.println("Working...");
        //need a variablo flip flop from current player to oppenent player I think that is myColor and oppenetColor
        boolean loop = true;
        LinkedQueue open = new LinkedQueue();
        open.enqueue(b);
        LList closed = new LList();
        LList children = null;
        //LList goalPath = null;
        System.out.println("Is open empty? " + open.isEmpty());
        while (!open.isEmpty() && loop) {
            System.out.println("In while loop..");
            Board x = (Board) open.dequeue();
            if (level == depth) { // this needs to stop at the cut off point so like depth = user cut off point
                loop = false;
            } else {
                children = new LList();
                System.out.println("Generating moves..");
                generateMoves(x, moves);
//                System.out.println("Valid moves are: ");
//                for (int i = 0; i < row2; i++) {
//                    for (int j = 0; j < moves[0].length; j++) {                   
//                        System.out.print(moves[i][j] + "  ");
//                    }
//                    System.out.println("");
//                }
                generateChildren(x, children, moves);
                System.out.println("Children generated:");
                for (int i = 1; i <= children.getLength(); i++) {
                    Board temp = (Board) children.getEntry(i);
                    System.out.println("---- "+temp.move+" ----");
                    temp.showBoard();
                    System.out.println("");
                }
                //checkChildren(children, open, closed);
                //for (int i = 1; i < children.getLength() + 1; i++) {
                //Board child = (Board) children.getEntry(i);
                //child.setHeuristic = 
                //assignHeuristic(child);   
                //open.enqueue(child);
                //}               
            }

            level++;
        }
        return "";
    }

    private static boolean success(Board x) {
        checkMove(x, 1, 2);
        return false;
    }

    private static void generateChildren(Board x, LList children, int[][] moves) {
        int row;
        int col;
        char rowText;
        char colText;
        for (int i = 0; i < row2; i++) { // number of childeren to generate
            row = moves[i][0];
            col = moves[i][1];
            rowText = (char) (row + 65);
            colText = (char) (col + 65);
            System.out.println("Generating child for: ");
            Board child = new myBoard(x.boardRows, x.boardCols, x.maxMoveTime);
            copy(child, x);
            checkMove(child, row, col);
            child.move = ""+rowText+colText;
            children.add(child);
        }
    }

    private static void checkChildren(LList children, LinkedQueue open, LList closed) {

        //Check to make sure none are in opened or closed
        //                                                                                
    }

    private static void assignHeuristic(Board child) {

    }

    private static String randomMove(Board b) {
        int[][] moves = new int[1000][2];
        return generateMoves(b, moves);
    }

    private static void copy(Board child, Board x) {
        child.colorSelected = x.colorSelected;
        child.move = x.move;
        child.status = x.status;
        child.whoseTurn = x.whoseTurn;
        for (int i = 0; i < x.boardRows; i++) {
            for (int j = 0; j < x.boardCols; j++) {
                child.board[i][j] = x.board[i][j];
            }
        }
    }

}// end of class
