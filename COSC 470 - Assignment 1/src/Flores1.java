//Program:	Flores1.java
//Course:	COSC470
//Description:	
//Author:	Edgar Flores
//Revised:	1/27/2018
//Language:	Java
//IDE:		NetBeans 8.2
//Notes:	none
//******************************************************************************
//******************************************************************************

//Class:	Flores1
//Description:	
//******************************************************************************
//******************************************************************************
public class Flores1 {

    static int row;
    static int col;
    static int startRow;
    static int startCol;
    static int pathCount = 0;
    static int shortestPathCount = Integer.MAX_VALUE;

    //Method:	
    //Description:	
    //Parameters:  none
    //Returns:     nothing
    //Calls:       nothing
    //Globals:     none
    //********************************************************************
    public static void main(String[] args) {
        KeyboardInputClass userInput = new KeyboardInputClass();
        boolean loop = true;
        while (loop) {
            TextFileClass textFile = new TextFileClass();
            textFile.getFileName("Enter text file: ");
            if (textFile.fileName.length() > 0) {
                textFile.getFileContents();
                readFile(textFile);
                char maze[][] = new char[row][col];
                char shortestPath[][] = new char[row][col];
                if (maze.length == row && maze[4].length == col) {
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
                    System.out.println("Maze not formated appropriately");
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
