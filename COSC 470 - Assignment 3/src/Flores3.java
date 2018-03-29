
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.*;

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

    public static void main(String[] args) {

        EasyImageDisplay imageFile = EasyImageDisplayDriver.main();

        //System.out.println("Hello");
        //EasyImageDisplay image = ImageDisplay();
        //graysacle value defualt = 0
        //Minimun region size defualt = 200
        //Maximum region size default = 10000
        //Circle threshold default = 5
        //Rectangle threshold defualt = 5
        //Square threshold default = 10
        //Neighborhood defualt = 8
        //press ENTER to show labeled image... // this shows labled regions, but 
        //I think this is optional, but I think
        //this could deal with findRegions()
        //press ENTER to show marked shapes (red = square, bule = circles, green = rectangles)
        perfromRegions(imageFile);

        System.exit(0);

    }

    private static void perfromRegions(EasyImageDisplay image) {

        KeyboardInputClass input = new KeyboardInputClass();

        System.out.println("Image Deminonsions:");
        imageHeight = image.imageHeight;
        System.out.println("Height = " + imageHeight);

        imageWidth = image.imageWidth;
        System.out.println("Width = " + imageWidth);

        //Print image pixel from easy image
//        for (int i = 0; i < image.imageHeight; i++) {
//            for (int j = 0; j < image.imageWidth; j++) {
//                System.out.printf("%3d", image.pixels[i][j] );
//            }
//            System.out.println("");
//        }    
        // input.getKeyboardInput("Press ENTER to continue...");
        System.out.println("Working on creating a regions object...");
        Regions r = new Regions(2, image.imageWidth, image.imageHeight, image.pixels, 8, false, 0);
        System.out.println("Done");

        System.out.println("Finding regions..");
        r.findRegions();

        //Print regions found
//        for (int i = 0; i < image.imageHeight; i++) {
//            for (int j = 0; j < image.imageWidth; j++) {
//                System.out.printf("%6d",r.labeledImage[i][j]);
//            }
//            System.out.println("");
//        }
        System.out.println("Filtering regions...");

        int filterR[][] = r.filterRegions(200, 10000, true, 5);

//        System.out.println("Region IDs found: ");
//        for (int j = 0; j < idPos; j++) {
//            System.out.println(regionsIds[j]);
//        }//end of for loop
        //*********** Signle out each region ***********
        //----- Method 1  -----
        //For each regions ID print the single
        //Loop through all regions
//        int index = 0;
//        while (index < idPos) {
//            int singleR[][] = r.getSingleRegion(regionsIds[index]);
        //Print out a region
//            System.out.println("RegionID-" + regionsIds[index]);
//            input.getKeyboardInput("Press ENTER to print selceted region...");
//            for (int i = 0; i < image.imageHeight; i++) {
//                for (int j = 0; j < image.imageWidth; j++) {
//                    System.out.print(singleR[i][j]);
//                }//end of inner for loop   
//                System.out.println("");
//            }//end of for loop 
//            input.getKeyboardInput("Press ENTER to Print Next Region...");
//            index++;
//        }//end of while
        //----- Method 2  -----      
//        int index = 0;
//        while (index < idPos) {
//            int singleR[][] = r.getSingleRegion(regionsIds[index]);
//            image.pixels = singleR;
//            image.showImage("RegionID-" + regionsIds[index], true);          
//            input.getKeyboardInput("Press ENTER to Print Next Region...");
//            image.closeImageDisplay();
//            index++;
//        }//end of while       
//========================================== START OF: Understanding Region Class Globals ========================================== 
        //------ What is pixelInRegion? ------ 
        System.out.println("Total pixels in Region 9909 [square] " + r.pixelsInRegion[9909]);

        //------ What is firstPixelInRegion? ------ 
//        System.out.println("first Pixel height = " + r.firstPixelInRegion.length);
//        System.out.println("first Pixel width = " + r.firstPixelInRegion[0].length);
        //Frist pixel of 9909
        //------ What is borderPixelCount[]? ------ 
        System.out.printf("boarder pixel count for region 9909 is %d\n", r.borderPixelCount[9909]);

        //------ sumOfRows[]? ------ 
        System.out.printf("Sum of Rows for region 9909 is %d\n", r.sumOfRows[9909]);

        //------ sumOfColmuns[]? ------ 
        System.out.printf("Sum of Cols for region 9909 is %d\n", r.sumOfColumns[9909]);
//========================================== END OF: Understanding Region Class Globals ========================================== 

        //============================ Solve for a square ============================
        System.out.println("Solving for square....");
        int singleR[][] = r.getSingleRegion(9909);
        System.out.println("RegionID - 9909[square]");
        int regionCol = 0;
        int regionRow = 0;
        int totalPix;

        //------------- Method 1 -------------
        //Square = 9909, 102053
//        //input.getKeyboardInput("Press ENTER to print selceted region...");
//
////        for (int row = 0; row < image.imageHeight; row++) {
////            for (int col = 0; col < image.imageWidth; col++) {
////                if (row == 23 && col == 518) {
////                    singleR[row][col] = 100;
////                }
////                if (row == endRow && col == endCol) {
////                    singleR[row][col] = 100;
////                }
//////                if(row >= 23 && row < (image.imageHeight - 23) && col >= 518 && col < (image.imageWidth - 518)){
//////                    singleR[row][col] = 100;
//////                }
////                System.out.printf("%4d", singleR[row][col]);
////            }//end of inner for loop 
////            System.out.println("");
////
////        }//end of for loop 
//
//        for (int row = 0; row < image.imageHeight; row++) {
//            for (int col = 0; col < image.imageWidth; col++) {
//                
//                //System.out.printf("%4d", singleR[row][col]);
//                
//                if (singleR[row][col] == 255) {
//                    
//                    System.out.printf("%4d", singleR[row][col]);
//
////                    if (row == 28) {
////                        imageCol++;
////                    }
////                    if (col == 518) {
////                        row++;
////                        System.out.println("");
////                    }
//
//                }//end of if statement
//
////                if (singleR[row][col] != 0) {
////                    System.out.print(singleR[row][col]);
////                    col++;
//////                    if(j == 0){
//////                        row++;
//////                    }
////                } else if (col == (image.imageWidth - 1)) {
////                    System.out.println("");
////                }//end of if   
//            }//end of inner for loop 
//            //if (singleR[row][0] == 255) {
//                System.out.println("");
//            //}//end of if statement
//        }//end of for loop 
        //------------- Method 2 -------------
        //Recursive calls
        //First make a recursive call to go south
        //Save all of the region IDs
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

        int squareRegions[] = new int[idPos];
        int recRegions[] = new int[idPos];
        int circleRegions[] = new int[idPos];
        int pos = 0;
        String shape = "";

        for (int index = 0; index < idPos; index++) {
            singleR = r.getSingleRegion(regionsIds[index]);
            startRow = r.firstPixelInRegion[regionsIds[index]][0];
            System.out.printf("Start row = %d\n", startRow);
            startCol = r.firstPixelInRegion[regionsIds[index]][1];
            System.out.printf("Start col = %d\n", startCol);
            shape = evaluateShape(singleR, r.pixelsInRegion[regionsIds[index]]);
            if (shape.equals("square")) {
                squareRegions[pos] = regionsIds[index];
            }
            if (shape.equals("rectangle")) {
                recRegions[pos] = regionsIds[index];
            }
            if (shape.equals("circle")) {
                circleRegions[pos] = regionsIds[index];
            }

            System.out.printf("Region Id: %4d is a %s\n", regionsIds[index], shape);
            image.pixels = singleR;
            image.showImage("Region ID " + regionsIds[index] + " is a " + shape, true);
            input.getKeyboardInput("Press ENTER to Evaluate Next Region...");
            image.closeImageDisplay();
            rowCount = 0;
            colCount = 0;

        }//end of while 

        //checkForSquare();
        //Then make a recursive call to go east
        //------------- Square information output -------------
//        System.out.println("--Square--");
//        System.out.println("Row = " + rowCount);
//        System.out.println("Col = " + colCount);
//        System.out.println("Pixel total = " + (rowCount * colCount));
//        System.out.println("showing region 9909....");
//        singleR = r.getSingleRegion(9909);
//        image.pixels = singleR;
//        image.showImage("RegionID: 9909", true);
//        input.getKeyboardInput("Press ENTER to Exit...");
//        image.closeImageDisplay();
        //============================ Solve for a square ============================
        //What is firstPixelInRegion??
        //in a new region                                                                 
        //Identify 
        //========== Scratch ==========
        //Create a regions object for each region ID to print out the sumOfColumns and sumOfRows
        //ID of a square = 8203
        //------ Method 1 -----
        // int square[][] = r.getSingleRegion(8203);
        //EasyImageDisplay squareObj = new EasyImageDisplay();
        //(int type, int width, int height, int image[][], int pixelNeighborhood, boolean includeBlack, int tolerance)
        //Regions squareRegion = new Regions(2, square.length, (square[0].length - 1), square, 8, true, 0);
//        for (int i = 0; i < squareRegion.sumOfColumns.length; i++) {
//            //System.out.println(r.sumOfColumns.length); 
//            if (squareRegion.sumOfColumns[i] != 0) {
//                System.out.println(squareRegion.sumOfColumns[i]);
//            }
//        }
        //----- Method 2 -----
        //int index = 0;
        //while (index < idPos) {
        // int singleR[][] = r.getSingleRegion(regionsIds[8203]);
        //Regions squareRegion = new Regions(2, singleR.length, (singleR[0].length - 1), singleR, 8, true, 0);
        //index++;
        //}//end of while 
        //========== Scratch ==========
    }// end of preform regions

    private static boolean checkId(int i, int rId[]) {
        for (int j = 0; j < rId.length; j++) {
            if (rId[j] == i) {
                return false;
            }
        }
        return true;
    }

    private static void outputAllRegions(int regionsId, Regions r) {
        int singleR[][] = r.getSingleRegion(regionsId);
        for (int i = 0; i < singleR.length; i++) {
            for (int j = 0; j < singleR[1].length; j++) {
                System.out.print(singleR[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static void getRegionRowCount(int[][] singleR, int currRow, int currCol) {

        if (currRow < imageHeight && currRow >= 0 && currCol < imageWidth && currCol >= 0 && singleR[currRow][currCol] == 255) {
            rowCount++;
            getRegionRowCount(singleR, currRow + 1, currCol);
        }
    }

    private static void getRegionColCount(int[][] singleR, int currRow, int currCol) {
        if (currRow < imageHeight && currRow >= 0 && currCol < imageWidth && currCol >= 0 && singleR[currRow][currCol] == 255) {
            colCount++;
            getRegionColCount(singleR, currRow, currCol + 1);
        }
    }

    private static String evaluateShape(int[][] singleR, int pixelSize) {
        ArrayList<Integer> circleRow = new ArrayList();
        getRegionRowCount(singleR, startRow, startCol);
        getRegionColCount(singleR, startRow, startCol);
        System.out.println("");
        System.out.println("Row = " + rowCount);
        System.out.println("Col = " + colCount);
        // System.out.println("Pixel total = " + (rowCount * colCount));
        // System.out.println("Pixel from regions total = " + pixelSize);
        //System.out.printf("If square %d should be less than %d \n", Math.abs((rowCount * colCount) - pixelSize), piz);
        //|| (rowCount * colCount) <= (pixelSize + recTolerance)

        //Check for sqr and rec
        if ((rowCount * colCount) <= (pixelSize + squareTolerance)) {
            System.out.println("Might be a square or rectangle..");
            //System.out.println("|rowCount - colCount| = " + Math.abs(rowCount - colCount));
            //check for sqr
            System.out.println("Checking for square...\n");
            if (Math.abs(rowCount - colCount) <= squareTolerance) {
                System.out.println("Square because " + Math.abs(rowCount - colCount) + " is less then tolerance = " + squareTolerance);
                return "square";
                //check for rec
            } else {
                System.out.println("Not a sqaure...\n");
                System.out.println("Checking for rec...");

                int preCol = colCount ;
                int currCount = 0;
                int tracker = 0;
                boolean isRec = true;

                for (int i = 0; i < imageHeight; i++) {
                    currCount = 0;
                    for (int j = 0; j < imageWidth; j++) {
                        //System.out.print(singleR[i][j]);

                        if (singleR[i][j] == 255) {
                            currCount++;
                        }
                    }
                    if (currCount > 0) {
                        tracker = currCount;
                        System.out.println("currCount = " + currCount);
                        System.out.println("preCol = " + preCol);
                        if (Math.abs(currCount - preCol) > recTolerance) {
                            System.out.println("NOT Rectangle because preCol[" + preCol + "] != currCol[" + currCount + "]");
                            isRec = false;
                            break;
                            //return "unidentified";
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
                    //System.out.println("row = " + currCount);
                    circleRow.add(currCount);
                    //System.out.println("row = "+currCount);
                }//end of if statment
            }// end of outter for loop
            int bottom = circleRow.size() - 1;
            for (int top = 0; top < (circleRow.size() / 2); top++) {
                int topRow = circleRow.get(top);
                System.out.println("top = " + topRow);
                int bottomRow = circleRow.get(bottom);
                System.out.println("bottom = " + bottomRow);
                if (Math.abs(topRow - bottomRow) > circleTolerance) {
                    isCircle = false;
                    System.out.println("Not a circle");
                    //return "unidentified";
                }
                bottom--;
            }
            if(isCircle == true){
                return "circle";
            }
        }//end of if statement   
        return "unidentified";
    }
}
