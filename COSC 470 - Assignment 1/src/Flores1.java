//Program:      Flores1.java
//Course:       COSC470
//Description:	Assignment 1 - Maze
//Author:       Edgar Flores
//Revised:      1/27/2018
//Language:     Java
//IDE:          NetBeans 8.2
//Notes:        Program that automatically finds the shortest way out of a 
//              two-dimensional M row by N column maze from any specified 
//              starting point within the maze. 
//******************************************************************************
//******************************************************************************

//Class:        Flores1
//Description:	Shortest path finder for a given maze
//******************************************************************************
//******************************************************************************
public class Flores1 {
    static int row;
    static int col;
    static int startRow;
    static int startCol;
    static int pathCount = 0;
    static int shortestPathCount = Integer.MAX_VALUE;

    //Method:       main
    //Description:	Asks user for a maze text file and executes approripiate 
    //              methods to find the shortest path out of a maze.
    //Parameters:   none
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      int row
    //              int col
    //              int startRow
    //              int startCol
    //              int pathcount
    //              int shortestPathCount
    //********************************************************************
    public static void main(String[] args) {
        KeyboardInputClass userInput = new KeyboardInputClass();
        boolean loop = true;
        System.out.println("Edgar Flores");
        System.out.println("Assignment 1: Maze Travler");
        while (loop) {
            TextFileClass textFile = new TextFileClass();
            textFile.getFileName("Enter text file: ");
            if (textFile.fileName.length() > 0) {
                textFile.getFileContents();
                readFile(textFile);
                char maze[][] = new char[row][col];
                char shortestPath[][] = new char[row][col];
                importMazeFromTextFile(maze, textFile);
                System.out.println(row);
                System.out.println(col);
                System.out.println(startRow);
                System.out.println(startCol);
                printMaze(maze, true, false);
                char s = userInput.getCharacter(true, 'n', "ynNY", 1, "\nShow the intermediary steps? (y/n) (defualt: n)");
                boolean show = (s == 'Y') ? show = true : false;
                boolean pause = false;
                if (show == true) {
                    s = userInput.getCharacter(true, 'n', "ynNY", 1, "\nPause after every step? (y/n)(default: n)");
                    pause = (s == 'Y') ? pause = true : false;
                }
                search(maze, shortestPath, startRow, startCol, startRow, startCol, show, pause);
                if (shortestPathCount != Integer.MAX_VALUE) {
                    System.out.println("==========Shortest path==========");
                    System.out.println();
                    System.out.println("Path count = " + shortestPathCount);
                    System.out.println();
                    printMaze(shortestPath, true, false);
                    System.out.println("===================================");
                } else {
                    System.out.println("No exit found...");
                }
            } else {
                System.out.println("\nNo text entered");
            }//end of else
            char l = userInput.getCharacter(true, 'n', "ynNY", 1, "\nOpen another maze file? (y/n)");
            System.out.println();
            loop = (l == 'Y') ? loop = true : false;
            if (loop) {
                pathCount = 0;
                shortestPathCount = 1000;
            }
        }
    }

    //Method:       readFile
    //Description:	Reads the first four lines of the maze file to determine the
    //              total rows and columns of the text file and the starting
    //              point in the maze
    //Parameters:   none
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      int row
    //              int col
    //              int startRow
    //              int startCol
    //********************************************************************
    private static void readFile(TextFileClass textFile) {

        System.out.println("\n" + textFile.fileName + " opened");
        try {
            row = Integer.parseInt(textFile.text[0]);
            col = Integer.parseInt(textFile.text[1]);
            startRow = Integer.parseInt(textFile.text[2]);
            startCol = Integer.parseInt(textFile.text[3]);
        } catch (Exception e) {
            System.out.println("Maze not formated appropriately");
        }
    }//end of printTextFile

    //Method:      importMazeFromTextFile
    //Description: Reads chars from the text file and translates each char from
    //             the text file maze to either a black square or a space then
    //             store that new char to a 2-D char array
    //Parameters:  maze - 2-D char array for the maze
    //             textFile - the text file containing the maze
    //Returns:     nothing
    //Calls:       nothing
    //Globals:     none
    //********************************************************************
    private static void importMazeFromTextFile(char[][] maze, TextFileClass textFile) {
        try {
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[4].length; j++) {
                    if (textFile.text[i + 4].charAt(j) == '0') {
                        maze[i][j] = ' ';
                    } else {
                        maze[i][j] = '\u2588';
                    }
                }//end of inner for loop
            }// end of outter for loop
        } catch (Exception e) {
            System.out.println("Maze not formated appropriately");
        }
    }// end of importMazeFromTextFile
    //Method:       printMaze       
    //Description:	Prints the maze
    //Parameters:   Maze - the maze to be printed
    //              show - determines whether or not to print the maze
    //              pause - determines whether or not to stall after printing the
    //                      maze
    //Returns:     nothing
    //Calls:       nothing
    //Globals:     int row
    //             int col
    //********************************************************************

    private static void printMaze(char[][] maze, boolean show, boolean pause) {
        KeyboardInputClass stall = new KeyboardInputClass();
        if (show == true) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.print(maze[i][j]);
                }//end of inner for loop
                System.out.println();
            }//end of outter for loop
            System.out.println();
            if (pause == true) {
                stall.getKeyboardInput("Press Enter to continue...");
            }
        }
    }//end of printMaze
    //Method:       search 
    //Description:	Prefroms a recurisve search of through the maze to determine
    //              the shortes path. This algorithm checks up, east, down, and
    //              west.
    //Parameters:   maze - the maze being searched
    //              shortestPath - the path of the maze 
    //              currRow - the row that is being evaluted of the maze
    //              currCol - the column that is being evaluted of the maze
    //              preRow - the previous row of that was evalued of the maze
    //              preCol - the previous column of that was evalued of the maze
    //Returns:      nothing
    //Calls:        search
    //Globals:      int row
    //              int col
    //              int pathCount
    //********************************************************************

    private static void search(char[][] maze, char[][] shortestPath, int currRow, int currCol, int preRow, int preCol, boolean show, boolean pause) {
        if (currRow < row && currRow >= 0 && currCol < col && currCol >= 0) {
            switch (maze[currRow][currCol]) {
                case ' ':
                    pathCount++;
                    maze[preRow][preCol] = '*';
                    maze[currRow][currCol] = 'X';
                    if (currRow == row - 1 || currCol == col - 1 || currRow == 0 || currCol == 0) {
                        compareShortestPath(maze, shortestPath, pathCount, show, pause);
                    }
                    printMaze(maze, show, pause);
                    //Up
                    search(maze, shortestPath, currRow - 1, currCol, currRow, currCol, show, pause);
                    //East
                    search(maze, shortestPath, currRow, currCol + 1, currRow, currCol, show, pause);
                    //Down
                    search(maze, shortestPath, currRow + 1, currCol, currRow, currCol, show, pause);
                    //West 
                    search(maze, shortestPath, currRow, currCol - 1, currRow, currCol, show, pause);
                    pathCount--;
                    maze[preRow][preCol] = 'X';
                    printMaze(maze, show, pause);
                default:
                    maze[preRow][preCol] = ' ';
            }
        }
    } // end of search
    //Method:       compareShortestPath	
    //Description:	When a path is found, this method compares the shortest path
    //              count of steps for the new found path to the shortest path
    //              count to determine the shortest path. 
    //Parameters:   maze - the maze
    //              shortestPath - the maze that holds the shortest path 
    //              path - the total number of steps in a path
    //              show - determines whether or not to print the maze
    //              pause - determines whether or not to stall after printing the
    //                      maze
    //Returns:     nothing
    //Calls:       nothing
    //Globals:     none
    //********************************************************************

    private static void compareShortestPath(char[][] maze, char[][] shortestPath, int path, boolean show, boolean auto) {
        if (path < shortestPathCount) {
            shortestPathCount = path;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    shortestPath[i][j] = maze[i][j];
                }//end of inner for loop
            }// end of outter for loop
        }
        if (show == true) {
            System.out.println("-----Path found-----");
            System.out.println("Path count = " + pathCount);
            //printMaze(maze, show, auto);
        }
    }
}// end of class
