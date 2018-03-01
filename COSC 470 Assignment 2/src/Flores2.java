//Program:      Flores1.java
//Course:       COSC470
//Description:	Assignment 2 - 8 Puzzel
//Author:       Edgar Flores
//Revised:      2/27/2018
//Language:     Java
//IDE:          NetBeans 8.2
//Notes:        This program solves the 8, 15, 24, or 35 puzzle (at the choice of the user) 
//              from a valid user specified or random (based on user specified # 
//              of shuffle moves) starting position. This program computes an optimum 
//              path to the goal state using brute force breadth-first search.
//******************************************************************************
//******************************************************************************

//Class:        Flores2
//Description:	Main class for the program
//******************************************************************************
//******************************************************************************
public class Flores2 {

    static int boardSize = 1;

    //********************************************************************
    //Method:       main
    //Description:  Makes the appropriate calss to creates a board, shuffles the 
    //              board, and slove the board.
    //Parameters:   none
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      none 
    public static void main(String[] args) {
        KeyboardInputClass input = new KeyboardInputClass();
        GameBoard board1;

        System.out.println("Edgar Flores: Assignment 2 - Program to solve the 8, 15, 24, or 36 Puzzle...\n");
        while (boardSize != 0) {
            boardSize = input.getInteger(false, 8, 0, 0, "Specify the puzzle size (8, 15, 24, or 35; 0 to exit): ");
            if (boardSize != 0) {
                int shuffleMoves = input.getInteger(false, -1, 0, 0, "Number of shuffle moves desired? (press ENTER alone to specify starting board)");
                char s = input.getCharacter(true, 'N', "YNys", 1, "Show intermediate board positions? (Y/N: Default=N)");
                boolean show = (s == 'Y') ? show = true : false;
                if (shuffleMoves == -1) {
                    board1 = new GameBoard(boardSize, shuffleMoves);
                    GameBoard board2 = new GameBoard(boardSize, 1);
                    System.out.println("Goal");
                    board2.printBoard();
                    System.out.println("Starting Board");
                    board1.printBoard();
                } else {
                    board1 = new GameBoard(boardSize, shuffleMoves);
                    System.out.println("Goal");
                    board1.printBoard();
                    for (int i = 0; i < shuffleMoves; i++) {

                        board1.moveATile(0, true);
                        if (show == true) {
                            System.out.println("---Shuffle move " + (i + 1) + "---");
                            board1.printBoard();
                        }
                    }
                    System.out.printf("Start state (%d shuffle moves actually made)\n", shuffleMoves);
                    System.out.println("Starting Board");
                    board1.printBoard();

                }// end of else 
                breadthFristSearch(board1);
            }// end of if          
        }// end of while      
    }//end of main

    private static void breadthFristSearch(GameBoard board1) {
        System.out.println("In breadth first...");
        boolean loop = true;
        int iteration = 0;
        LinkedQueue open = new LinkedQueue();
        open.enqueue(board1);
        LList closed = new LList();
        LList children = null;
        LList goalPath = null;

        while (!open.isEmpty() && loop) {
            System.out.println();
            System.out.println("Iteration " + iteration);
            GameBoard x = (GameBoard) open.dequeue();
            System.out.println("Pop off x...");
            System.out.println("x is : ");
            x.printBoard();
            closed.add(x);
            System.out.println("closed list contains " + closed.getLength() + " nodes...");
            showSolutions(closed);
            System.out.println("is x solution?");
            if (success(x)) {
                System.out.println("Success!");
                x.printBoard();
                findPath(goalPath, x);
                System.out.println("Goal path is...");
                showSolutions(goalPath);
                loop = false;
            } else {
                System.out.println();
                System.out.println("No, generate children...");
                children = new LList();
                generateChildren(x, children);
                showSolutions(children);
                System.out.println("Making sure children generated are not in open or closed...");
                checkChildren(children, open, closed);
                System.out.println("Children to be used...");
                showSolutions(children);
                for (int i = 1; i < children.getLength() + 1; i++) {
                    GameBoard child = (GameBoard) children.getEntry(i);
                    open.enqueue(child);
                }
            }
            
            iteration++;
        }
        
        System.out.println("Fail");

    }

    private static boolean success(GameBoard x) {
        boolean success = true;
        for (int i = 0; i < x.board.length; i++) {

            if (!x.board[i].equals(x.goalBoard[i])) {
                success = false;
                break;
            }
        }
        return success;
    }

    private static void generateChildren(GameBoard x, LList children) {
        boolean isValid;
        for (int i = 0; i < 4; i++) {
            GameBoard child = new GameBoard(boardSize, 1);
            for (int j = 0; j < x.board.length; j++) {
                child.board[j] = x.board[j];
            } 
            child.space = x.space;
            child.getParent(x);
            isValid = child.moveATile((i + 1), false);
            if (isValid) {
                children.add(child);
                System.out.println("Move valid...");
                child.printBoard();
            }
            
        }//end of for loop
    }// end of generateMoves

    private static void showSolutions(LList list) {
        GameBoard temp;
        int length = list.getLength();
        System.out.printf("There are %d moves", length);
        System.out.println();
        for (int i = 1; i < list.getLength() + 1; i++) {
            temp = (GameBoard) list.getEntry(i);
            temp.printBoard();
        }
    }

    private static void checkChildren(LList children, LinkedQueue open, LList closed) {
        LinkedQueue tempQueue = new LinkedQueue();
        System.out.println("Checking...");
        for (int i = 1; i < (children.getLength() + 1); i++) {
            GameBoard child = (GameBoard) children.getEntry(i);
            for (int j = 1; j < (closed.getLength() + 1); j++) {
                GameBoard boardClosed = (GameBoard) closed.getEntry(j);
                System.out.println("for loop termination num =  " + (closed.getLength() + 1));
                System.out.println("boardClosed = node " + j);
                System.out.println("Closed board about to be compared...");
                boardClosed.printBoard();
                if (isContained(child, boardClosed)) {
                    System.out.println("Previous children length = " + children.getLength());
                    children.remove(i);
                    System.out.println("New children length = " + children.getLength());
                    System.out.println("Contained removed");
                }
            }

            while (!open.isEmpty()) {
                System.out.println("Opened board about to be compared...");
                
                GameBoard boardOpen = (GameBoard) open.dequeue();
                boardOpen.printBoard();
                tempQueue.enqueue(boardOpen);
                if (isContained(child, boardOpen) && !open.isEmpty()) {
                    System.out.println("Previous children length = " + children.getLength());
                    children.remove(i);
                    System.out.println("New children length = " + children.getLength());
                    System.out.println("Contained removed");
                }
            }

        }

        while (!tempQueue.isEmpty()) {
            GameBoard temp = (GameBoard) tempQueue.dequeue();
            open.enqueue(temp);
        }
    }

    private static boolean isContained(GameBoard child, GameBoard a) {
        System.out.println("===isContained===");
        System.out.println("child ...");
        child.printBoard();
        System.out.println("being compared to a ...");
        a.printBoard();
        for (int i = 0; i < a.board.length; i++) {

            if (!child.board[i].equals(a.board[i])) {
                System.out.println("Not contained");
                return false;
            }
        }
        System.out.println("Is Contained!!");
        return true;
    }

    private static void findPath(LList goalPath, GameBoard x) {
        goalPath = new LList();
        boolean found = false;
        //GameBoard start = (GameBoard)closed.getEntry(1);
        GameBoard preParent;

        while(!found){
            preParent = x.parent;
            goalPath.add(preParent);
            if(preParent.parent == null){
                found = true;
            }
        }
    }

}//end of class
