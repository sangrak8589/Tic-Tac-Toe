package cpsc2150.homeworks.hw3;
import java.util.*;

/*
    Sangram Kadam (sangrak)
    CPSC 2150
    Spring 2018
    Kevin Plis
    HW 3
 */

/**
 * @invariant [myMap is a collection of key value pairs that holds a player and a list of their positions on the board]
 *            and  minDimension = 0;
 * @invariant 0 < row <= MAX_SIZE
 * @invariant 0 < column <= MAX_SIZE
 * @invariant 0 < numToWin <= row and 0 < numToWin <= column
 * Correspondence NUM_ROWS = row
 * Correspondence NUM_COLS = column
 * Correspondence this = board
 */
public class GameBoardMem implements IGameBoard {

    //initialize the map
    private HashMap<Character, List<BoardPosition>> myMap;
    static private final int MIN_DIMENSION = 0;
    private Integer row;
    private Integer column;
    private int numToWin;
    static private final int TWO_DIGIT = 10;

    /**
     * @param row is the row selected by the user
     * @param column is the column selected by the user
     * @param numToWin is the number of tokens in a row chosen by the user for a win to occur
     * @requires 0 < row <= MAX_SIZE and 0 < column <= MAX_SIZE and 0 < numToWin <= column and the board to exist
     * @ensures [a new map is created every time the constructor is called] and [myMap never changes] [and map is empty]
     */
    public GameBoardMem(int row, int column, int numToWin){

        this.row = row;
        this.column = column;
        this.numToWin = numToWin;

        //create new map every time constructor is called
        myMap = new HashMap<>();
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
        //loop through the map and see if pos already exists
        for(Map.Entry<Character, List<BoardPosition>> m: myMap.entrySet()){
            if(m.getValue().contains(pos)) {
                return false;
            }
        }
        return true;
    }

    public void placeMarker(BoardPosition lastPos){

        //add a new position if player is already on the board
        if(myMap.containsKey(lastPos.getPlayer()) == true) {
            myMap.get(lastPos.getPlayer()).add(lastPos);
        }
        else{
            //creates a new empty list
            List<BoardPosition> myList = new ArrayList<>();
            //add last pos to list
            myList.add(lastPos);
            //put list back into the map
            myMap.put(lastPos.getPlayer(), myList);
        }
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

    public boolean checkForDraw() {

        Integer count = 0;

        //loop through all the entries
        for (Map.Entry<Character, List<BoardPosition>> m : myMap.entrySet()){
            //set count to the sum of the number of values in each list
            count+= m.getValue().size();
        }
        //if count is equal to basically the area of a rectangle which are the #of cells then return true
        //basically means every cell is filled
        if (count == (row * column)) {
            return true;
        }
        return false;
    }
    /**
     * @param lastPos = last position placed by the user and [last pos is valid position]
     * @requires [the board to exist] and [lastPos has to exist]
     * @ensures [lastPos doesn't ever change]
     * @return checkHorizontalWin = false if count < NUM_TO_WIN
     */
    private boolean checkHorizontalWin(BoardPosition lastPos) {
        //checks to see if the last marker placed resulted in 5 in a row horizontally. Returns true if it does,
        // otherwise false

        int count = 0;
        boolean trackPlayer;

        //check to the right of last pos
        for (int i = lastPos.getRow() + 1; i < column; i++) {
            trackPlayer = false;
            for (BoardPosition m : myMap.get(lastPos.getPlayer())) {
                if (m.equals(new BoardPosition(lastPos.getRow(), i, m.getPlayer()))) {
                    count++;
                    trackPlayer = true;
                }
            }
            if (trackPlayer == false) {
                break;
            }
        }
        //check to the left of last pos
        for (int i = lastPos.getRow(); i >= 0; i--) {
            trackPlayer = false;
            for (BoardPosition m : myMap.get(lastPos.getPlayer())) {
                if (m.equals(new BoardPosition(lastPos.getRow(), i, m.getPlayer()))) {
                    count++;
                    trackPlayer = true;
                }
            }
            if(trackPlayer == false) {
                break;
            }
        }
        //check whether count is >= number of tokens needed to win
        if(count >= numToWin){
            return true;
        }
        //else return false
        return false;
    }

    /**
     * @param lastPos = last position placed by the user and [last pos is valid position]
     * @requires [the board to exist] and [lastPos has to exist]
     * @ensures [lastPos doesn't ever change]
     * @return checkVerticalWin = false if count < NUM_TO_WIN
     */
    private boolean checkVerticalWin(BoardPosition lastPos) {

        int count = 0;
        boolean trackPlayer;

        //check to the right of last pos
        for (int i = lastPos.getColumn() + 1; i < row; i++) {
            trackPlayer = false;
            for (BoardPosition m : myMap.get(lastPos.getPlayer())) {
                if (m.equals(new BoardPosition(i, lastPos.getColumn(), m.getPlayer()))) {
                    count++;
                    trackPlayer = true;
                }
            }
            if (trackPlayer == false) {
                break;
            }
        }
        //check to the left of last pos
        for (int i = lastPos.getColumn(); i >= 0; i--) {
            trackPlayer = false;
            for (BoardPosition m : myMap.get(lastPos.getPlayer())) {
                if (m.equals(new BoardPosition(i, lastPos.getColumn(), m.getPlayer()))) {
                    count++;
                    trackPlayer = true;
                }
            }
            if(trackPlayer == false) {
                break;
            }
        }

        //check whether count is >= number of tokens needed to win
        if(count >= numToWin){
            return true;
        }
        //else return false
        return false;
    }

    /**
     * @param lastPos = last position placed by the user and [last pos is valid position]
     * @requires [the board to exist] and [lastPos has to exist]
     * @ensures [checkDiagonalWin never accesses theBoard out of bounds] and [lastPos never changes]
     * @return checkDiagonalWin = true if count >= NUM_TO_WIN
     */
    private boolean checkDiagonalWin(BoardPosition lastPos) {
         /* checks to see if the last marker placed resulted in 5 in a row diagonally. Returns true if it does,
           otherwise false*/
        //Note: there are two diagonals to check

        int count = 1;
        //int lastRow = lastPos.getRow();
        //int lastColumn = lastPos.getColumn();
        char player = lastPos.getPlayer();


        //down and right
        for(int i = lastPos.getRow() + 1, j = lastPos.getColumn() + 1; i < row && j < column; i++, j++) {
                if (player == helpDiagonal(i, j, lastPos)){
                    //increase count
                    count++;
                } else {
                    //leave the loop
                    break;
                }
                if (count >= numToWin) {
                    //winner if count is >=5
                    return true;
                }
            }
        //up and left
        for(int i = lastPos.getRow() - 1, j = lastPos.getColumn() - 1; i >= 0 && j >= 0; i--, j-- ){
                if (player == helpDiagonal(i,j, lastPos)){
                    count++;
                } else {
                    break;
                }
                if (count >= numToWin) {
                    return true;
                }
        }
        //reset count to 1 after checking one diagonal
        count = 1;
        //up and right
        for(int i = lastPos.getRow() - 1, j = lastPos.getColumn() + 1; i >= 0 && j < column; i--, j++ ){
                if (player == helpDiagonal(i,j, lastPos)) {
                    count++;
                }
                else {
                    break;
                }
                if (count >= numToWin) {
                    return true;
                }
        }
        //down and left
        for(int i = lastPos.getRow() + 1, j = lastPos.getColumn() - 1; i < row && j >= 0; i++, j-- ){

            if (player == helpDiagonal(i,j, lastPos)){
                    count++;
                } else {
                    break;
                }
                if (count >= numToWin) {
                    return true;
                }
            }
        //otherwise return false
        return false;
    }
    /**
     * @requires [this, which is the GameBoardMem object that toString is acting on, is an initialized GameBoardMem
     *           object]
     * @ensures instance of the class (GameBoardMem) remains constant
     * @return str = [output of the board]
     */
    @Override
    public String toString(){
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
        if(row <= TWO_DIGIT - 1) {
            //outputs the row numbers at the side
            for (int i = 0; i <= row - 1; i++) {
                rowCount++;
                str += rowCount + "|";
                //outputs the number of | that makes the columns (contingent upon # of columns)
                for (int j = 0; j < column; j++) {

                    str += (playerAt(i, j) + " |");
                }
                str += "\n";
            }
        }
        //output board when # of rows is 10 or greater
        else if(row >= TWO_DIGIT) {
            //outputs the first 9 row numbers at the side
            for (int i = 0; i <= TWO_DIGIT - 1; i++) {
                rowCount++;
                str += rowCount + " |";
                //outputs the number of | that makes the columns (contingent upon # of columns)
                for (int j = 0; j < column; j++) {
                    //function call
                    str += (playerAt(i, j) + " |");

                }
                str += "\n";
            }
            //outputs the remaining row numbers at the side from 10 to whatever # of rows user chose
            for(int i = TWO_DIGIT; i < row; i++){
                rowCount++;
                str += rowCount + "|";
                //outputs the number of | that makes the columns (contingent upon # of columns)
                for(int j = 0; j < column; j++){
                    //function call
                    str += (playerAt(i, j) + " |");

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
     * @return equals = true if two GameBoardMem's are the same]
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        //instance of
        if(!(obj instanceof GameBoardMem)){
            return false;
        }
        //casting
        GameBoardMem g = (GameBoardMem) obj;
        if((this.myMap == g.myMap)){
            return true;
        }
        //return false otherwise
        return false;
    }

    //helper function to loop through the map and place marker
    /**
     * @param row is the last row selected by the user
     * @param column is the last column selected by the user
     * @requires 0 < row <= MAX_SIZE and 0 < column <= MAX_SIZE
     * @ensures currChar doesn't change and currChar will hold either the key (which represents a character i.e. 'X'
     *          or a blank space ' '
     * @return [currChar which holds the character of the player if there is a marker present or a blank space if user
     *         hasn't placed a marker at that spot]
     */
    private char playerAt(int row, int column) {

        char currChar = ' ';
        for (Map.Entry<Character, List<BoardPosition>> m : myMap.entrySet()) {
            if (m.getValue().contains(new BoardPosition(row, column, m.getKey()))) {
                //if it contains the new BoardPosition then set currChar to that player's character
                currChar = m.getKey();
            }
        }
        return currChar;
    }

    /**
     * @param row is the last row selected by the user
     * @param column is the last column selected by the user
     * @param lastPos is the instance of BoardPosition which indicates the last position placed by the player
     * @requires 0 < row <= MAX_SIZE and 0 < column <= MAX_SIZE and [lastPos has to exist] and [lastPos to be an object
     *           of BoardPosition class]
     * @ensures currChar doesn't change and currChar will hold either the key (which represents a character i.e. 'X'
     *          or a blank space ' '
     * @return [currChar which holds the character of the player if there is a marker present or a blank space if user
     *         hasn't placed a marker at that spot]
     */
    private char helpDiagonal(int row, int column, BoardPosition lastPos) {

        char currChar =' ';
        for (BoardPosition m : myMap.get((lastPos.getPlayer()))) {
            if (m.equals(new BoardPosition(row, column, m.getPlayer()))) {
                //if it contains the new BoardPosition then set currChar to that player's character
                currChar = m.getPlayer();
            }
        }
        return currChar;
    }
}
