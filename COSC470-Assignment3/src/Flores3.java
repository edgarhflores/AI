import java.util.*;
//Program:      Flores3.java
//Course:       COSC470
//Description:	Assignment 3 - Region
//Author:       Edgar Flores
//Revised:      3/29/2018
//Language:     Java
//IDE:          NetBeans 8.2
//Notes:        This program will identify superimosed shapes on an image. Then
//              Color the shapes accordingly.
//******************************************************************************
//******************************************************************************

//Class:        Flores3
//Description:	Main class for the program
//******************************************************************************
//******************************************************************************
public class Flores3 {

    public static int imageWidth;
    public static int imageHeight;
    public static int startCol;
    public static int startRow;
    public static int rowCount;
    public static int colCount;
    public static int squareTolerance = 10;
    public static int recTolerance = 5;
    public static int circleTolerance = 5;

    //********************************************************************
    //Method:       main
    //Description:  Calls apporiate methods to find identify shapes
    //Parameters:   none
    //Returns:      nothing
    //Calls:        preformRegions
    //Globals:       
    public static void main(String[] args) {

        EasyImageDisplay imageFile = EasyImageDisplayDriver.main();
        System.out.println("Edgar Flores - Assignment 3 - Regions");
        //graysacle value defualt = 0
        System.out.println("Grayscale value is 0");
        //Minimun region size defualt = 200
        System.out.println("");
        //Maximum region size default = 10000
        //Circle threshold default = 5
        //Rectangle threshold defualt = 5
        //Square threshold default = 10
        //Neighborhood defualt = 8
        //press ENTER to show labeled image... // this shows labled regions, but 
        //I think this is optional, but I think
        //this could deal with findRegions()
        //press ENTER to show marked shapes (red = square, blue = circles, green = rectangles)
        perfromRegions(imageFile);

        System.exit(0);

    }

    //********************************************************************
    //Method:       perfromRegions
    //Description:  
    //Parameters:   none
    //Returns:      nothing
    //Calls:        
    //Globals:       
    private static void perfromRegions(EasyImageDisplay image) {

        KeyboardInputClass input = new KeyboardInputClass();
        imageHeight = image.imageHeight;
        imageWidth = image.imageWidth;
        System.out.println("Working on creating a regions object...");
        Regions r = new Regions(2, image.imageWidth, image.imageHeight, image.pixels, 8, false, 0);
        System.out.println("Done");
        System.out.println("Finding regions..");
        r.findRegions();
        //Print Labeled Image found
//        for (int i = 0; i < image.imageHeight; i++) {
//            for (int j = 0; j < image.imageWidth; j++) {
//                System.out.printf("%6d",r.labeledImage[i][j]);
//            }
//            System.out.println("");
//        }
        System.out.println("Filtering regions...");
        int filterR[][] = r.filterRegions(200, 10000, true, 5);
        int singleR[][];
        System.out.println("Obtainging region IDs...");
        int regionsIds[] = new int[5000];
        int idPos = 0;
        for (int i = 0; i < image.imageHeight; i++) {
            for (int j = 0; j < image.imageWidth; j++) {
                if (filterR[i][j] > 0) {
                    if (checkId(filterR[i][j], regionsIds) == true) {
                        regionsIds[idPos] = filterR[i][j];
                        idPos++;
                    }//end of if
                }//end of if
            }//inner for
        }//End of outter for loop
        System.out.println("Regions IDs found: " + idPos);

        //SearchForShapesInRegions
        int squareRegions[] = new int[idPos];

        int redPixels[][] = new int [imageHeight][imageWidth];
        int bluePixels[][] = new int [imageHeight][imageWidth];
        int greenPixels[][] = new int [imageHeight][imageWidth];
        
        deepCopy(redPixels, image);
        deepCopy(bluePixels, image);
        deepCopy(greenPixels, image);
        
        EasyImageDisplay coloredImageShapes = new EasyImageDisplay(1, imageWidth, imageHeight, redPixels, bluePixels, greenPixels, image.pixels);
        int recRegions[] = new int[idPos];
        int circleRegions[] = new int[idPos];
        int sqrPos = 0;
        int recPos = 0;
        int cirPos = 0;
        String shape = "";
        for (int index = 0; index < idPos; index++) {
            System.out.println("");
            System.out.println("------Evaluating Region ID: " + regionsIds[index]+"------");
            singleR = r.getSingleRegion(regionsIds[index]);
            startRow = r.firstPixelInRegion[regionsIds[index]][0];
            startCol = r.firstPixelInRegion[regionsIds[index]][1];
            shape = evaluateShape(singleR, r.pixelsInRegion[regionsIds[index]]);
            if (shape.equals("square")) {
                squareRegions[sqrPos] = regionsIds[index];
                sqrPos++;

            }
            if (shape.equals("rectangle")) {
                recRegions[recPos] = regionsIds[index];
                recPos++;
            }
            if (shape.equals("circle")) {
                circleRegions[cirPos] = regionsIds[index];
                cirPos++;
            }
            System.out.printf("Region %4d is a %s\n", regionsIds[index], shape);
            image.pixels = singleR;

            image.showImage("Region ID " + regionsIds[index] + " is a " + shape, true);
            input.getKeyboardInput("Press ENTER to Evaluate Next Region...");
            image.closeImageDisplay();

            rowCount = 0;
            colCount = 0;
        }//end of while 
        
        
        
        for (int i = 0; i < squareRegions.length; i++) {
            if (squareRegions[i] > 0) {
                colorRegion(1, coloredImageShapes, r, squareRegions[i]);
            }
        }
        for (int i = 0; i < circleRegions.length; i++) {
            if (circleRegions[i] > 0) {
                colorRegion(2, coloredImageShapes, r, circleRegions[i]);
            }
        }      

        for (int i = 0; i < recRegions.length; i++) {
            if (recRegions[i] > 0) {
                colorRegion(3, coloredImageShapes, r, recRegions[i]);
            }
        }
        
        //(int type, int width, int height, int redValues[][], int greenValues[][], int blueValues[][], int values[][]) {

        
        coloredImageShapes.showImage("Colored Shapes", true);
        input.getKeyboardInput("Press ENTER to Evaluate Next Region...");
        coloredImageShapes.closeImageDisplay();

    }// end of preform regions

    //********************************************************************
    //Method:       checkId
    //Description:  
    //Parameters:   none
    //Returns:      nothing
    //Calls:        
    //Globals:       
    private static boolean checkId(int i, int rId[]) {
        for (int j = 0; j < rId.length; j++) {
            if (rId[j] == i) {
                return false;
            }
        }
        return true;
    }


    //********************************************************************
    //Method:       getRegionRowCount
    //Description:  
    //Parameters:   none
    //Returns:      nothing
    //Calls:        
    //Globals:     
    private static void getRegionRowCount(int[][] singleR, int currRow, int currCol) {

        if (currRow < imageHeight && currRow >= 0 && currCol < imageWidth && currCol >= 0 && singleR[currRow][currCol] == 255) {
            rowCount++;
            getRegionRowCount(singleR, currRow + 1, currCol);
        }
    }
    
    //********************************************************************
    //Method:       getRegionRowCount
    //Description:  
    //Parameters:   none
    //Returns:      nothing
    //Calls:        
    //Globals:   
    private static void getRegionColCount(int[][] singleR, int currRow, int currCol) {
        if (currRow < imageHeight && currRow >= 0 && currCol < imageWidth && currCol >= 0 && singleR[currRow][currCol] == 255) {
            colCount++;
            getRegionColCount(singleR, currRow, currCol + 1);
        }
    }

    //********************************************************************
    //Method:       evaluateShape
    //Description:  
    //Parameters:   none
    //Returns:      nothing
    //Calls:        
    //Globals:  
    private static String evaluateShape(int[][] singleR, int pixelSize) {
        ArrayList<Integer> circleRow = new ArrayList<>();
        getRegionRowCount(singleR, startRow, startCol);
        getRegionColCount(singleR, startRow, startCol);
        //Check for sqr and rec
        if ((rowCount * colCount) <= (pixelSize + squareTolerance)) {
            System.out.println("\nChecking for square...");
            if (Math.abs(rowCount - colCount) <= squareTolerance) {
                System.out.println("Square because " + Math.abs(rowCount - colCount) + " is less then tolerance = " + squareTolerance);
                return "square";
                //check for rec
            } else {
                System.out.println("Not a sqaure...\n");
                System.out.println("Checking for rec...");

                int preCol = colCount;
                int currCount = 0;
                int tracker = 0;
                boolean isRec = true;

                for (int i = 0; i < imageHeight; i++) {
                    currCount = 0;
                    for (int j = 0; j < imageWidth; j++) {
                        if (singleR[i][j] == 255) {
                            currCount++;
                        }
                    }
                    if (currCount > 0) {
                        tracker = currCount;
                        //System.out.println("currCount = " + currCount);
                        //System.out.println("preCol = " + preCol);
                        if (Math.abs(currCount - preCol) > recTolerance) {
                            System.out.println("NOT Rectangle because preCol[" + preCol + "] != currCol[" + currCount + "]");
                            isRec = false;
                            break;
                        }
                    }
                }

                if (isRec) {
                    System.out.println("Rectangle because ALL preCol[" + preCol + "] == currCol[" + tracker + "]");
                    return "rectangle";
                }
            }
            System.out.println("");
            System.out.println("Checking for circle...");
            //check for circle
            boolean isCircle = true;
            int currCount = 0;
            for (int i = 0; i < imageHeight; i++) {
                currCount = 0;
                for (int j = 0; j < imageWidth; j++) {
                    if (singleR[i][j] == 255) {
                        currCount++;
                    }//end of if statement
                }//end of inner for loop
                if (currCount > 0) {
                    circleRow.add(currCount);
                }//end of if statment
            }// end of outter for loop
            int bottom = circleRow.size() - 1;
            for (int top = 0; top < (circleRow.size() / 2); top++) {
                int topRow = circleRow.get(top);
                //System.out.println("top = " + topRow);
                int bottomRow = circleRow.get(bottom);
                //System.out.println("bottom = " + bottomRow);
                if (Math.abs(topRow - bottomRow) > circleTolerance) {
                    isCircle = false;
                    //System.out.println("Not a circle");
                }
                bottom--;
            }
            if (isCircle == true) {
                return "circle";
            }
        }//end of if statement   
        return "unidentified";
    }

    private static void deepCopy(int[][] coloPixels, EasyImageDisplay image) {
        for (int i = 0; i < coloPixels.length; i++) {
            for (int j = 0; j < coloPixels[0].length; j++) {
                coloPixels[i][j] = image.pixels[i][j];
            }
        }
    }

    private static void colorRegion(int i, EasyImageDisplay image, Regions r, int regionsId) {
        int[][] region = r.getSingleRegion(regionsId);
        int[][] temp = region;
        switch (i) {
            case 1:
                temp = image.redPixels;
                break;
            case 2:
                temp = image.bluePixels;
                break;
            case 3:
                temp = image.greenPixels;
                break;
        }
        for (int row = 0; row < region.length; row++) {
            for (int col = 0; col < region[0].length; col++) {
                if (region[row][col] != 0) {
                    image.greenPixels[row][col] = 0;
                    image.bluePixels[row][col] = 0;
                    image.redPixels[row][col] = 0;
                    temp[row][col] = region[row][col];
                }
            }

        }
    }
}
