package cpsc2150.homeworks.hw3;

/*
    Sangram Kadam (sangrak)
    CPSC 2150
    Spring 2018
    Kevin Plis
    HW 3
 */

/**
 * @invariant [theBoardFast is a grid with rows and columns] and minDimension = 0;
 * @invariant 0 < row <= MAX_SIZE
 * @invariant 0 < column <= MAX_SIZE
 * @invariant 0 < numToWin <= row and 0 < numToWin <= column
 * Correspondence NUM_ROWS = row
 * Correspondence NUM_COLS = column
 * Correspondence this = theBoardFast[0...row - 1][0...column - 1]
 */
public class GameBoardFast implements IGameBoard {

    private char [][] theBoardFast;
    static private final int MIN_DIMENSION = 0;
    private int row;
    private int column;
    private int numToWin;
    static private final int TWO_DIGIT = 10;


    /**
     * @param row is the row selected by the user
     * @param column is the column selected by the user
     * @param numToWin is the number of tokens in a row chosen by the user for a win to occur
     * @requires 0 < row <= MAX_SIZE and 0 < column <= MAX_SIZE and 0 < numToWin <= column and the board to exist
     * @ensures theBoardFast = char array of size [#row][#column] and [theBoardFast never changes] and [every position
     *          in the #row by #column board is initialized to ' '
     *
     */
    public GameBoardFast(int row, int column, int numToWin){

        this.row = row;
        this.column = column;
        this.numToWin = numToWin;


        theBoardFast = new char[row][column];

        //initialize the board so that each position has a blank space character
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                theBoardFast[i][j] = ' ';
            }
        }
    }

    public boolean checkSpace(BoardPosition pos){

        //check if row is in bounds
        if((pos.getRow() < MIN_DIMENSION) || (pos.getRow() >= row)){
            return false;
        }
        //check if column is in bounds
        if((pos.getColumn() < MIN_DIMENSION) || (pos.getColumn() >= column)){
            return false;
        }
        //returns true if the position specified in pos is available, false otherwise
        if(theBoardFast[pos.getRow()][pos.getColumn()] != ' '){
            return false;
        }
        //returns true otherwise
        return true;
    }

    public void placeMarker(BoardPosition lastPos){
        int row = lastPos.getRow();
        int column = lastPos.getColumn();

        //places the character in marker on the position specified by marker
        theBoardFast[row][column] = lastPos.getPlayer();
    }

    public boolean checkForWinner(BoardPosition lastPos){

        /*
        this function will check to see if the lastPos placed resulted in a winner. It so it will return true,
        otherwise false.
        Passing in the last position will help limit the possible places to check for a win condition, since you can
        assume that any win condition that did not include the most recent play made would have been caught earlier.
        */

        //if any of these functions return false, return false
        if(checkHorizontalWin(lastPos) == false && checkVerticalWin(lastPos) == false && checkDiagonalWin(lastPos) ==
                false){
            return false;
        }
        //otherwise return true
        return true;
    }

    public boolean checkForDraw(){

        /*this function will check to see if the game has resulted in a tie. A game is tied if there are no free
        board positions remaining. It will return true if the game is tied, and false otherwise.*/
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                if(theBoardFast[i][j] == ' '){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * @param lastPos = last position placed by the user
     * @requires [the board to exist] and [lastPos has to exist]
     * @ensures [lastPos doesn't ever change]
     * @return checkHorizontalWin = false if count < NUM_TO_WIN
     */
    private boolean checkHorizontalWin(BoardPosition lastPos) {
        //checks to see if the last marker placed resulted in 5 in a row horizontally. Returns true if it does,
        // otherwise false

        int lastColumn= lastPos.getColumn();
        int count = 0;
        char player = lastPos.getPlayer();

        for(int i = 0; i < row; i++){
            if(player == theBoardFast[i][lastColumn]){
                count++;
            }
            else{
                count = 0;
            }
            if(count >= numToWin){
                return true;
            }
        }
        return false;
    }

    /**
     * @param lastPos = last position placed by the user
     * @requires [the board to exist] and [lastPos has to exist]
     * @ensures [lastPos doesn't ever change]
     * @return checkVerticalWin = false if count < NUM_TO_WIN
     */
    private boolean checkVerticalWin(BoardPosition lastPos) {
        /*checks to see if the last marker placed resulted in 5 in a row vertically. Returns true if it does,
         otherwise false*/

        int lastRow = lastPos.getRow();
        int count = 0;
        char player = lastPos.getPlayer();

        for(int i = 0; i < column; i++){
            if(player == theBoardFast[lastRow][i]){
                count++;
            }
            else{
                count = 0;
            }
            if(count >= numToWin){
                return true;
            }
        }
        return false;
    }

    /**
     * @param lastPos = last position placed by the user
     * @requires [the board to exist] and [lastPos has to exist]
     * @ensures [checkDiagonalWin never accesses theBoard out of bounds] and [lastPos never changes]
     * @return checkDiagonalWin = true if count >= NUM_TO_WIN
     */
    private boolean checkDiagonalWin(BoardPosition lastPos) {
        /* checks to see if the last marker placed resulted in 5 in a row diagonally. Returns true if it does,
           otherwise false*/
        //Note: there are two diagonals to check

        int count = 1;
        int lastRow = lastPos.getRow();
        int lastColumn = lastPos.getColumn();
        char player = lastPos.getPlayer();


        //down and right
        for(int i = lastRow + 1, j = lastColumn + 1; i < row && j < column; i++, j++) {
            if (player == theBoardFast[i][j]) {
                //increase count
                count++;
            }
            else {
                //leave the loop
                break;
            }
            if (count >= numToWin) {
                //winner if count is >=5
                return true;
            }
        }
        //up and left
        for(int i = lastRow - 1, j = lastColumn - 1; i >= 0 && j >= 0; i--, j-- ){
            if(player == theBoardFast[i][j]){
                count++;
            }
            else{
                break;
            }
            if(count >= numToWin){
                return true;
            }
        }
        //reset count to 1 after checking one diagonal
        count = 1;
        //up and right
        for(int i = lastRow - 1, j = lastColumn + 1; i >= 0 && j < column; i--, j++ ){
            if(player == theBoardFast[i][j]){
                count++;
            }
            else{
                break;
            }
            if(count >= numToWin){
                return true;
            }
        }
        //down and left
        for(int i = lastRow + 1, j = lastColumn - 1; i < row && j >= 0; i++, j-- ){
            if(player == theBoardFast[i][j]){
                count++;
            }
            else{
                break;
            }
            if(count >= numToWin){
                return true;
            }
        }
        //otherwise return false
        return false;
    }

    /**
     * @requires [this, which is the GameBoardFast object that toString is acting on, is an initialized GameBoardFast
     *           object]
     * @ensures instance of the class (GameBoardFast) remains constant
     * @return str = [output of the board]
     */
    @Override
    public String toString()
    {
        String str = "";

        int rowCount = 0;
        int columnCount = 0;


        rowCount = rowCount - 1;
        columnCount = columnCount - 1;

        //output board when #of columns is 9 or less
        if(column <= TWO_DIGIT - 1){
            //if the number of rows are 10 or more add another space
            if(row >= TWO_DIGIT){
                str+= " ";
            }
            str+= "   ";
            //outputs number of columns at the top
            for(int c = 0; c < column; c++){
                columnCount++;
                str += columnCount + "| ";
            }
            str+= "\n";
        }
        //output board when # of columns is 10 or greater
        else if(column >= TWO_DIGIT){
            //if the number of rows are 10 or more add another space
            if(row >= TWO_DIGIT){
                str+= " ";
            }
            str+= "   ";
            //outputs the first 9 column numbers on top
            for(int c = 0; c < TWO_DIGIT - 1; c++){
                columnCount++;
                str += columnCount + "| ";
            }
            //outputs the column numbers at the top from 10 till whatever # of columns user chose
            for(int c = TWO_DIGIT; c <= column; c++){
                columnCount++;
                str += columnCount + "|";
            }
            str+= "\n";
        }
        //output board when # of rows is 9 or less
        if(row <= TWO_DIGIT - 1){
            //outputs the row numbers at the side
            for(int i = 0; i <= row - 1; i++) {
                rowCount++;
                str += rowCount + "|";
                //outputs the number of | that makes the columns (contingent upon # of columns)
                for (int j = 0; j < column; j++) {
                    str += (theBoardFast[i][j] + " |");
                }
                str += "\n";
            }
        }
        //output board when # of rows is 10 or greater
        else if(row >= TWO_DIGIT){
            //outputs the first 9 row numbers at the side
            for(int i = 0; i <= TWO_DIGIT - 1; i++) {
                rowCount++;
                str += rowCount + " |";
                //outputs the number of | that makes the columns (contingent upon # of columns)
                for (int j = 0; j < column; j++) {
                    str += (theBoardFast[i][j] + " |");
                }
                str += "\n";
            }
            //outputs the remaining row numbers at the side from 10 to whatever # of rows user chose
            for(int i = TWO_DIGIT; i < row; i++){
                rowCount++;
                str += rowCount + "|";
                //outputs the number of | that makes the columns (contingent upon # of columns)
                for(int j = 0; j < column; j++){
                    str += (theBoardFast[i][j] + " |");
                }

                str += "\n";
            }
        }
        return str;
    }
    /**
     * @param obj = object of the Object class
     * @requires [obj to exist]
     * @ensures obj doesn't change
     * @return equals = true if two GameBoardFast's are the same]
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        //instance of
        if(!(obj instanceof GameBoardFast)){
            return false;
        }
        //casting
        GameBoardFast g = (GameBoardFast) obj;
        if((this.theBoardFast == g.theBoardFast)){
            return true;
        }
        //return false otherwise
        return false;
    }
}


