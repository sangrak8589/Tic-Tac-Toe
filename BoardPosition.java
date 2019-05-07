package cpsc2150.homeworks.hw3;

/*
    Sangram Kadam (sangrak)
    CPSC 2150
    Spring 2018
    Kevin Plis
    HW 3
 */

/**
 * @invariant 0 < row <= MAX_SIZE and 0 < column <= MAX_SIZE and [character is any one of a possible 10 characters in
 *            character array]
 */
public class BoardPosition {

    private Integer row;
    private Integer column;
    private Character character;

    /**
     *
     * @param row is the row selected by the user
     * @param column is the column selected by the user
     * @param character is the mark of the current player, character is any one of a possible 10 characters in character
     *            array
     * @requires 0 < row <= MAX_SIZE and 0 < column <= MAX_SIZE and [character is any one of a possible  10 characters
     *           in character array]
     * @ensures row = #row and column = #column and character = #character
     */
    public BoardPosition(Integer row, Integer column, Character character){

        //initializes row, column, and character
        this.row = row;
        this.column = column;
        this.character = character;

    }

    /**
     * @requires [row to have a value when returned]
     * @ensures getRow = (row selected by user) and 0 < row <= MAX_SIZE and [the object that the getter is a part of
     *          never changes (row never changes)]
     * @return getRow = [row selected by user]
     */
    public int getRow(){
        //returns the row
        return row;
    }
    /**
     * @requires [column to have a value when returned]
     * @ensures getColumn = (column selected by user) and 0 < column <= MAX_SIZE and [the object that the getter is
     *          a part of never changes (column never changes)]
     * @return getRow = [column selected by user]
     */
    public int getColumn(){
        //returns the column
        return column;
    }
    /**
     * @requires [character to have a value when returned (any one of 10 characters in the character array]
     * @ensures getPlayer = [any one of a possible ten characters depending on number of players and who
     *          current playeris] and [the object that the getter is a part of never changes]
     * @return getPlayer = [any one of a possible ten characters in the character array] (depending on current player)
     */
    public char getPlayer() {
        //returns X or O to signify the correct player
        return character;
    }
    /**
     * @requires [this, which is the BoardPosition object that toString is acting on, is an initialized BoardPosition
     *           object]
     * @ensures instance of the class remains constant
     * @return str = Player <player> at position <row>,<column>
     */
    @Override
    public String toString()
    {
        String str = "";
        str += "Player " + getPlayer() + " at position " + getRow() + "," + getColumn();

        return str;
    }

    /**
     * @param obj = object of the Object class
     * @requires [obj to exist]
     * @ensures obj doesn't change
     * @return equals = true if [same column, row, and position]
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        //instance of
        if(!(obj instanceof BoardPosition)){
            return false;
        }
        //casting
        BoardPosition b = (BoardPosition) obj;
        if((row == b.getRow()) && (column == b.getColumn()) && (character == b.getPlayer())){
            return true;
        }
        //return false otherwise
        return false;
    }
}
