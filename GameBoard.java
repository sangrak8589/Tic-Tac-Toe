package cpsc2150.homeworks.hw3;


/*
    Sangram Kadam (sangrak)
    CPSC 2150
    Spring 2018
    Kevin Plis
    HW 3
 */

/**
 * @invariant [theBoard is a grid with rows and columns] and BOARD_SIZE >= 3 [smallest functional tic tac toe board is 3
 *             by 3] and NUM_TO_WIN >= 3 and maxDimension = BOARD_SIZE and minDimension = 0;
 */
public class GameBoard {

    private char [][] theBoard;
    static private final int BOARD_SIZE = 8;
    static private final int NUM_TO_WIN = 5;
    static private final int MAX_DIMENSION = 8;
    static private final int MIN_DIMENSION = 0;

    /**
     * @requires [the board to exist] and BOARD_SIZE > 0
     * @ensures theBoard = new char [BOARD_SIZE][BOARD_SIZE] and every index in theBoard is = ' ' and [theBoard
     *          never changes]
     */
    public GameBoard(){

        theBoard = new char[BOARD_SIZE][BOARD_SIZE];

        //each position has a single blank space character
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                theBoard[i][j] = ' ';
            }
        }

    }

    /**
     * @param pos = instance of BoardPosition that allows access to the get functions
     * @requires pos = object of BoardPosition
     * @ensures  [pos never changes] and [boolean is always returned]
     * @return  checkSpace = true iff [row and column are within bounds] and iff [row and column are empty]
     */
    public boolean checkSpace(BoardPosition pos){

        //check if row is in bounds
        if((pos.getRow() < MIN_DIMENSION) || (pos.getRow() >= MAX_DIMENSION)){
            return false;
        }
        //check if column is in bounds
        if((pos.getColumn() < MIN_DIMENSION) || (pos.getColumn() >= MAX_DIMENSION)){
            return false;
        }
        //returns true if the position specified in pos is available, false otherwise
        if(theBoard[pos.getRow()][pos.getColumn()] != ' '){
            return false;
        }
        //returns true otherwise
        return true;
    }

    /**
     * @param marker = instance of BoardPosition that allows access to row, column, and character
     * @requires marker = instance of BoardPosition and [the board to exist] and checkSpace() to be true.
     * @ensures row = getRow() and column = getColumn() (both functions are from BoardPosition class, hence the need
     *          a BoardPosition object)
     *          and marker never changes
     */
    public void placeMarker(BoardPosition marker)
    {
        int row = marker.getRow();
        int column = marker.getColumn();

        //places the character in marker on the position specified by marker
        theBoard[row][column] = marker.getPlayer();

    }

    /**
     * @param lastPos = last position placed by current player
     * @requires [board has to exist] and [lastPos has to exist] and [lastPos to be an object of BoardPosition class]
     * @ensures [lastPos never changes] and [boolean is always returned]
     * @return checkForWinner = false if checkHorizontalWin is false
     *         and checkForWinner = false if checkVerticalWin is false
     *         and checkForWinner = false if checkDiagonalWin is false
     */
    public boolean checkForWinner(BoardPosition lastPos)
    {
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

    /**
     * @requires [the board to exist]
     * @ensures BOARD_SIZE > 0 and [boolean is always returned]
     * @return checkForDraw = false if any index of theBoard = ' ' (any index is free indicates not a draw)
     */
    public boolean checkForDraw()
    {
        /*this function will check to see if the game has resulted in a tie. A game is tied if there are no free
        board positions remaining. It will return true if the game is tied, and false otherwise.*/
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(theBoard[i][j] == ' '){
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

        int column = lastPos.getColumn();
        int count = 0;
        char player = lastPos.getPlayer();

        for(int i = 0; i < BOARD_SIZE; i++){
            if(player == theBoard[i][column]){
                count++;
            }
            else{
                count = 0;
            }
            if(count == NUM_TO_WIN){
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

        int row = lastPos.getRow();
        int count = 0;
        char player = lastPos.getPlayer();

        for(int i = 0; i < BOARD_SIZE; i++){
            if(player == theBoard[row][i]){
                count++;
            }
            else{
                count = 0;
            }
            if(count == NUM_TO_WIN){
                return true;
            }
        }
        return false;
    }

    /**
     * @param lastPos = last position placed by the user
     * @requires [the board to exist] and [lastPos has to exist]
     * @ensures [checkDiagonalWin never accesses theBoard out of bounds] and [lastPos never changes]
     * @return checkDiagonalWin = true if count = NUM_TO_WIN
     */
    private boolean checkDiagonalWin(BoardPosition lastPos) {
        /* checks to see if the last marker placed resulted in 5 in a row diagonally. Returns true if it does,
           otherwise false*/
        //Note: there are two diagonals to check

        int count = 1;
        int row = lastPos.getRow();
        int column = lastPos.getColumn();
        char player = lastPos.getPlayer();


        //down and right
        for(int i = row + 1, j = column + 1; i < BOARD_SIZE && j < BOARD_SIZE; i++, j++) {
            if (player == theBoard[i][j]) {
                //increase count
                count++;
            }
            else {
                //leave the loop
                break;
            }
            if (count >= NUM_TO_WIN) {
                //winner if count is >=5
                return true;
            }
        }
        //up and left
        for(int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j-- ){
            if(player == theBoard[i][j]){
                count++;
            }
            else{
                break;
            }
            if(count >= NUM_TO_WIN){
                return true;
            }
        }
        //reset count to 1 after checking one diagonal
        count = 1;
        //up and right
        for(int i = row - 1, j = column + 1; i >= 0 && j < BOARD_SIZE; i--, j++ ){
            if(player == theBoard[i][j]){
                count++;
            }
            else{
                break;
            }
            if(count >= NUM_TO_WIN){
                return true;
            }
        }
        //down and left
        for(int i = row + 1, j = column - 1; i < BOARD_SIZE && j >= 0; i++, j-- ){
            if(player == theBoard[i][j]){
                count++;
            }
            else{
                break;
            }
            if(count >= NUM_TO_WIN){
                return true;
            }
        }
        //otherwise return false
        return false;
    }

    /**
     * @requires [this, which is the GameBoard object that toString is acting on, is an initialized GameBoard object]
     * @ensures instance of the class (GameBoard) remains constant
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

        str+= "  ";
        //outputs the column numbers at the top
        for(int c = 0; c < BOARD_SIZE; c++){
            columnCount++;
            str += columnCount + "|";
        }
        str+= "\n";

        //outputs the row numbers at the side
        for(int i = 0; i < BOARD_SIZE; i++){
            rowCount++;
            str +=  rowCount + "|";
            //outputs the number of | that makes the columns (contingent upon # of columns)
            for(int j = 0; j < BOARD_SIZE; j++){
                str += (theBoard[i][j] + "|");
            }
            str += "\n";
        }
        return str;
    }
    /**
     * @param obj = object of the Object class
     * @requires [obj to exist]
     * @ensures obj doesn't change
     * @return equals = true if two GameBoard's are the same]
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        //instance of
        if(!(obj instanceof GameBoard)){
            return false;
        }
        //casting
        GameBoard g = (GameBoard) obj;
        if((this.theBoard == g.theBoard)){
            return true;
        }
        //return false otherwise
        return false;
    }
}

