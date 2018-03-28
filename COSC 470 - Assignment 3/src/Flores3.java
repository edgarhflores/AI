
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Flores3 {

    public static void main(String[] args) {
        
        int[] pixel = new int[5];
        
        pixel[0]++;
        
        System.out.println("pixel[0]" + pixel[0]);
        System.out.println("pixel[1]" + pixel[1]);

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
        System.out.println("Height = " + image.imageHeight);
        System.out.println("Width = " + image.imageWidth);

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
//                System.out.print(r.labeledImage[i][j] + " ");
//            }
//            System.out.println("");
//        }
        System.out.println("Filtering regions...");
        int filterR[][] = r.filterRegions(200, 10000, true, 5);

        //Save all of the region IDs
        System.out.println("Obtainging region IDs...");
        int regionsIds[] = new int[500];
        int idPos = 0;
        //create a loop that searches thorugh the numbers that have been added that are other than 0
        for (int i = 0; i < image.imageHeight; i++) {
            for (int j = 0; j < image.imageWidth; j++) {
                //System.out.print(filterR[i][j] + "  ");
                if (filterR[i][j] > 0) {
                    //System.out.println(filterR[i][j]);
                    if (checkId(filterR[i][j], regionsIds) == true) {
                        regionsIds[idPos] = filterR[i][j];
                        idPos++;
                        //num
                    }//end of if
                }//end of if
            }//inner for
            //System.out.println("");
        }//End of outter for loop
        //Print the region IDs found
        System.out.println("Regions IDs found: " + idPos);
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
        //============================ Solve for a square ============================
        //Square = 9909, 102053
        int singleR[][] = r.getSingleRegion(9909);

        System.out.println("RegionID - 9909");
        input.getKeyboardInput("Press ENTER to print selceted region...");
        int imageCol = 0;
        int imageRow = 0;
        for (int row = 0; row < image.imageHeight; row++) {
            for (int col = 0; col < image.imageWidth; col++) {
                // if()
                if (singleR[row][col] == 255) {
                    System.out.print(singleR[row][col]);

                    if (row == 0) {
                        imageCol++;
                    }
                    if (col == 0) {
                        row++;
                        System.out.println("");
                    }
                }

//                if (singleR[row][col] != 0) {
//                    System.out.print(singleR[row][col]);
//                    col++;
////                    if(j == 0){
////                        row++;
////                    }
//                } else if (col == (image.imageWidth - 1)) {
//                    System.out.println("");
//                }//end of if   
            }//end of inner for loop 
            if (singleR[row][0] == 255) {
                System.out.println("");
            }
        }//end of for loop 
        System.out.println("--Square--");
        System.out.println("Row = " + imageRow);
        System.out.println("Col = " + imageCol);

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
}
