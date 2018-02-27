
//Step 2
//Create the board
//  How to repersent the board?            
public class GameBoard {

    String board[];
    int space;
    int dem;
    int previousShuffleMove;
    public GameBoard(int boardSize) {
        board = new String[boardSize + 1];
        //single array
        //System.out.println(board.length);
        for (int i = 0; i < board.length; i++) {
            if (i == board.length - 1) {
                board[i] = " ";
                space = i;
            } else {
                board[i] = Integer.toString(i + 1);

            }
            dem = (int) Math.sqrt(board.length + 0.0);
        }
    }//end of constructor

    //    
//Print the board
    public void printBoard() {

        for (int i = 0; i < board.length; i++) {
            int a = i + 1;// index to be used for modulo
            String t = board[i];
            System.out.printf("%5s", t);
            if (a % dem == 0)// prints tiles on a board until row finishes 
            {
                System.out.println();
            }
        }
        System.out.println();
    }

    public void shuffleATile() {
        int ranDirec = (int) (Math.random() * (5 - 1) + 1);
        String vaildTile;
        String openTile;
        boolean isShuffled = true;
        while (isShuffled) {
            System.out.println("Generating random move...");           
            switch ((int) (Math.random() * (5 - 1) + 1)) {
                case 1://up
                    System.out.println("Checking if can go north...");
                    if (space - dem >= 0 && previousShuffleMove != 3) {
                        System.out.println("Going north");
                        vaildTile = board[space - dem];
                        openTile = board[space];
                        board[space - dem] = openTile;
                        board[space] = vaildTile;
                        space = space - dem;
                        System.out.println("Space is at: " + (space + 1));
                        isShuffled = false;
                        previousShuffleMove = 1;
                    }else if (previousShuffleMove == 3){
                        System.out.println("No, because last move came from that direction");
                        System.out.println();
                    }else {
                        System.out.println("No, because north does not exist");
                        System.out.println();
                    }
                    break;
                case 2:// east
                    System.out.println("Checking if can go east...");
                    if (space + 1 < board.length && (space+2) % dem != 1 && previousShuffleMove != 4) {
                        System.out.println("Going east");
                        vaildTile = board[space + 1];
                        openTile = board[space];
                        board[space + 1] = openTile;
                        board[space] = vaildTile;
                        space = space + 1;
                        System.out.println("Space is at: " + (space + 1));
                        isShuffled = false;
                        previousShuffleMove = 2;
                    }else if ((space+2) % dem == 1){
                        System.out.println("No, because this is a new row");
                        System.out.println();
                    }else if (previousShuffleMove == 4){
                        System.out.println("No, because last move came from that direction");
                        System.out.println();
                    }else
                        System.out.println("No, because east does not exist\n");
                    
                    break;
                case 3://south
                    System.out.println("Checking if can go south...");
                    if (space + dem < board.length && previousShuffleMove != 1) {
                        System.out.println("Going south");
                        vaildTile = board[space + dem];
                        openTile = board[space];
                        board[space + dem] = openTile;
                        board[space] = vaildTile;
                        space = space + dem;
                        System.out.println("Space is at: " + (space + 1));
                        isShuffled = false;
                        previousShuffleMove = 3;
                    }else if (previousShuffleMove == 1){
                        System.out.println("No, because last move came from that direction");
                        System.out.println();
                    }else{
                        System.out.println("No, because south does not exist");
                        System.out.println();
                    }
                    break;
                case 4://west
                    System.out.println("Checking if can go west...");
                    if (space - 1 >= 0 && space % dem != 0 && previousShuffleMove != 2) {
                        System.out.println("Going west");
                        vaildTile = board[space - 1];
                        openTile = board[space];
                        board[space - 1] = openTile;
                        board[space] = vaildTile;
                        space = space - 1;
                        System.out.println("Space is at: " + (space + 1));
                        isShuffled = false;
                        previousShuffleMove = 4;
                    }else if (space % dem == 0){
                        System.out.println("No, because this is a new row");
                        System.out.println();
                    }else if (previousShuffleMove == 2){
                        System.out.println("No, because last move came from that direction\n");
                    }else
                        System.out.println("No, because west does not exist\n");
                    break;
            }
        }

    }// end of shuffleATile

}// end of class

//double array
//        int tile = 1;
//        for (int i = 0; i < dem; i++) {
//            for (int j = 0; j < dem; j++) {
//                if (i == dem - 1 && j == dem - 1) {
//                    board[i][j] = " ";
//                } else {
//                    board[i][j] = Integer.toString(tile);
//                }
//                System.out.print(board[i][j] + " ");
//                tile++;
//            }
//            System.out.println();
//        }//end of for loop 
