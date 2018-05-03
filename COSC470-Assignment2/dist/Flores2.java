//Program:      Flores1.java
//Course:       COSC470
//Description:	Assignment 2 - 8 Puzzel
//Author:       Edgar Flores
//Revised:      3/1/2018
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
                System.out.println("Search mode = breadth-first");
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

    //********************************************************************
    //Method:       breadthFristSearch
    //Description:  implements breath-frist search
    //Parameters:   board 1 - the shuffled board
    //Returns:      nothing
    //Calls:        showSolutions, findPath, generateChildren, checkChildren           
    //Globals:      none 
    private static void breadthFristSearch(GameBoard board1) {
        System.out.println("Working...");
        boolean loop = true;
        boolean success = false;
        LinkedQueue open = new LinkedQueue();
        open.enqueue(board1);
        LList closed = new LList();
        LList children = null;
        LList goalPath = null;

        while (!open.isEmpty() && loop) {
            GameBoard x = (GameBoard) open.dequeue();
            closed.add(x);
            if (success(x)) {
                System.out.println("Success!");
                x.printBoard();
                //findPath(goalPath, x);
                //System.out.println("Goal path is...");
                //showSolutions(goalPath);
                loop = false;
                success = true;
            } else {
                children = new LList();
                generateChildren(x, children);
                checkChildren(children, open, closed);
                for (int i = 1; i < children.getLength() + 1; i++) {
                    GameBoard child = (GameBoard) children.getEntry(i);
                    open.enqueue(child);
                }
            }
        }
        if(!success){
        System.out.println("Fail, no solution found");
        }

    }
    
    //********************************************************************
    //Method:       success
    //Description:  Checks to see if a node that was generated is a solution
    //Parameters:   x - the node that is being evaluted as a solution
    //Returns:      true - a solution was found
    //              false - no solution was found
    //Calls:        nothing
    //Globals:      none 
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

    //********************************************************************
    //Method:       generateChildren
    //Description:  creates all possible children which are know as possible
    //              moves to take to solve the maze
    //Parameters:   x - the parent node
    //              children - a linked list where all children are stored
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      none 
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
            }
            
        }//end of for loop
    }// end of generateMoves

    //********************************************************************
    //Method:       showSolutions
    //Description:  displays a linked list
    //Parameters:   list - the list to display
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      none 
    private static void showSolutions(LList list) {
        GameBoard temp;
        for (int i = 1; i < list.getLength() + 1; i++) {
            temp = (GameBoard) list.getEntry(i);
            temp.printBoard();
        }
    }

    //********************************************************************
    //Method:       checkChildren
    //Description:  Before placing a child to the open queue, one must check that
    //              the possible move is not in the open queue or closed list.
    //Parameters:   children - the linked list containing all the children
    //              open - the queue that contains the nodes in open
    //              closed - the list that contains nodes that have been visited
    //Returns:      nothing
    //Calls:        isContained
    //Globals:      none 
    private static void checkChildren(LList children, LinkedQueue open, LList closed) {
        LinkedQueue tempQueue = new LinkedQueue();
        for (int i = 1; i < (children.getLength() + 1); i++) {
            GameBoard child = (GameBoard) children.getEntry(i);
            for (int j = 1; j < (closed.getLength() + 1); j++) {
                GameBoard boardClosed = (GameBoard) closed.getEntry(j);
                if (isContained(child, boardClosed)) {
                    children.remove(i);
                }
            }

            while (!open.isEmpty()) {
                GameBoard boardOpen = (GameBoard) open.dequeue();
                tempQueue.enqueue(boardOpen);
                if (isContained(child, boardOpen) && !open.isEmpty()) {
                    children.remove(i);
                }
            }

        }

        while (!tempQueue.isEmpty()) {
            GameBoard temp = (GameBoard) tempQueue.dequeue();
            open.enqueue(temp);
        }
    }

    //********************************************************************
    //Method:       isContained
    //Description:  checks to see if one board of a Gameboard object is equal to
    //              the board of a different Gameboard object
    //Parameters:   child - the first object to compared
    //              a - the second object to be compared
    //Returns:      true - the first and second object are equal
    //              false - the first and seconf object are not equal
    //Calls:        nothing
    //Globals:      none 
    private static boolean isContained(GameBoard child, GameBoard a) {
        for (int i = 0; i < a.board.length; i++) {
            if (!child.board[i].equals(a.board[i])) {
                return false;
            }
        }
        return true;
    }

    //********************************************************************
    //Method:       findPath
    //Description:  finds the goal path
    //Parameters:   goalPath - a linked list that contains the goal path
    //              x - the node from which to start with
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      none 
    private static void findPath(LList goalPath, GameBoard x) {
        goalPath = new LList();
        boolean found = false;
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
