package cpsc2150.homeworks.hw3;

/*
    Sangram Kadam (sangrak)
    CPSC 2150
    Spring 2018
    Kevin Plis
    HW 3
 */

/**
 * IGameBoard represents a 2-dimensional gameboard that has characters * on it as markers. No space on the board can
 * have multiple players, and there can be a clear winner. Board is NUM_ROWS x NUM_COLS in size. Indexing of the
 * gameboard starts in the top left corner with 0,0 and goes to NUM_ROWS-1, NUM_COLS-1.
 *
 * Initialization ensures: the Board does not have any markers on it
 * Defines: NUM_ROWS: Z
 * Defines: NUM_COLS: Z
 * Constraints: 0< NUM_ROWS <= MAX_SIZE
 *              0< NUM_COLS <= MAX_SIZE
 */
public interface IGameBoard {

    int MAX_SIZE = 100;

    // add your contracts
    /**
     * @param pos [is the instance of BoardPosition that allows access to the get functions]
     * @requires [pos is a valid object of BoardPosition] and [requires what is calling pos to exist]
     * @ensures [pos never changes] and [boolean is always returned]
     * @return checkSpace = true iff [row and column are within bounds] and iff [row and column are empty at pos]
     */
    boolean checkSpace(BoardPosition pos);


    // add your contracts
    /**
     * @param lastPos [is the instance of BoardPosition that allows access to row, column, and character]
     * @requires [lastPos is a valid instance of BoardPosition] and [requires what is calling last pos to exist]
     *           and checkSpace() to be true.
     * @ensures row = [what user chose for the # of rows and is passed through BoardPosition] and [column is what user
     *          chose for # of columns and is passed through BoardPosition] (both functions are from BoardPosition class
     *          ,hence the need a BoardPosition object) and [lastPos never changes]
     */
    void placeMarker(BoardPosition lastPos);


    // add your contracts
    /**
     * @param lastPos [is the instance of BoardPosition which indicates the last position placed by the player]
     * @requires [lastPos has to exist] and [lastPos to be an object of BoardPosition class]
     * @ensures [lastPos never changes] and [boolean is always returned]
     * @return checkForWinner = false if checkHorizontalWin is false
     *         and checkForWinner = false if checkVerticalWin is false
     *         and checkForWinner = false if checkDiagonalWin is false
     */
    boolean checkForWinner(BoardPosition lastPos);


    //add your contracts
    /**
     * @requires [the board to exist]
     * @ensures 0 < column <= MAX_SIZE and 0 < row <= MAX_SIZE and [boolean is always returned]
     * @return checkForDraw = false if any index of the board = ' ' (any index is free indicates not a draw)
     */
    boolean checkForDraw();
}



