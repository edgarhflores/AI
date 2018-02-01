//Program:		Flores1.java
//Course:		COSC470
//Description:	
//Author:		Edgar Flores
//Revised:		1/27/2018
//Language:		Java
//IDE:			NetBeans 8.2
//Notes:		none
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
    //Method:	
    //Description:	
    //Parameters:  	none
    //Returns:     	nothing
    //Calls:       	nothing
    //Globals:      none
    //********************************************************************
    public static void main(String[] args) {
        KeyboardInputClass userInput = new KeyboardInputClass();
        TextFileClass textFile = new TextFileClass();
        
        String fileName = "maze.txt";
        System.out.println("File to be opened: " + fileName);
        textFile.fileName = fileName;
        
        //textFile.getFileName("Enter text file: ");
        
        if (textFile.fileName.length()>0){
            
            textFile.getFileContents();
            String words [] = textFile.text;
            
            String lineZero = words[0];
            System.out.println("string = "+lineZero);
            String lineOne = words[1];
            String lineTwo = words[2];
            String lineThree = words[3];
            
            
            row = Integer.parseInt(lineZero);
            System.out.println("row = "+row);
            col = Integer.parseInt(lineOne);
            startRow = Integer.parseInt(lineTwo);
            startCol = Integer.parseInt(lineThree);
            
            
            int count = 0;
            for(String word: words){
                
                
                if (word == null)
                    break;
                System.out.println(word);
                count++;
            }
            System.out.println("Line count = " + textFile.lineCount);
            System.out.println("Print line count = " + count);    
        }
        else 
            System.out.println("No text entered");
    }
}
