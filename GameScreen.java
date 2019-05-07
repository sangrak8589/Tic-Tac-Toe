package cpsc2150.homeworks.hw3;

/*
    Sangram Kadam (sangrak)
    CPSC 2150
    Spring 2018
    Kevin Plis
    HW 3
 */

import java.util.*;

public class GameScreen {

    public static void main(String[] args) {

        final char [] playerHolder = {'X', 'O', 'Y', 'Z', 'A', 'K', 'E', 'J', 'N', 'H'};

        final int MAX_PLAYERS = 10;
        final int MIN_PLAYERS = 2;

        int cpIndex = -1;
        char playAgain;
        char implementation;
        char currentPlayer;
        final char upperYes = 'Y';
        final char lowerYes = 'y';
        final char upperF = 'F';
        final char lowerF = 'f';
        final char upperM = 'M';
        final char lowerM = 'm';

        Integer rowLocation;
        Integer columnLocation;
        Integer numRow;
        Integer numCol;
        Integer numPlayers;
        Integer numTokens;

        BoardPosition m;
        IGameBoard q;


            do {
                //ask user for number of players
                System.out.println("How many players will play? (Max of 10)");
                Scanner scanner = new Scanner(System.in);
                numPlayers = scanner.nextInt();

                //check user input for number of players
                if ((numPlayers > MAX_PLAYERS) || (numPlayers < MIN_PLAYERS)) {
                    //print error message to the screen
                    System.out.println("Must be between 2 and 10 players");
                }
            }
            //repeat do while loop until conditions for numPlayers is met
            while ((numPlayers > MAX_PLAYERS) || (numPlayers < MIN_PLAYERS));

            do {
                //ask user for number of rows
                System.out.println("How many rows should be on the board?");
                Scanner scanner = new Scanner(System.in);
                numRow = scanner.nextInt();


                //check user input for number of rows
                if ((numRow > IGameBoard.MAX_SIZE) || (numRow <= 0)) {
                    //print error message to the screen
                    System.out.println("Can only have 100 rows or less");
                }
            }
            //repeat do while loop until conditions for numRows is met
            while ((numRow > IGameBoard.MAX_SIZE) || (numRow <= 0));

            do {
                //ask user for number of columns
                System.out.println("How many columns should be on the board?");
                Scanner scanner = new Scanner(System.in);
                numCol = scanner.nextInt();


                //check user input for number of columns
                if ((numCol > IGameBoard.MAX_SIZE) || (numCol <= 0)) {
                    //print error message to the screen
                    System.out.println("Can only have 100 columns or less");
                }
            }
            //repeat do while loop until conditions for numCols is met
            while ((numCol > IGameBoard.MAX_SIZE) || (numCol <= 0));

            do {
                //ask user for number of tokens in a row to win
                System.out.println("How many in a row to win?");
                Scanner scanner = new Scanner(System.in);
                numTokens = scanner.nextInt();


                //check user input for number of tokens
                if (((numTokens > numRow) || (numTokens > numCol)) || (numTokens <= 0)) {
                    //print error message to the screen
                    System.out.println("You can't have that many because that's more than the number of rows or " +
                            "columns");
                }
            }
            //repeat do while loop until conditions for numTokens is met
            while (((numTokens > numRow) || (numTokens > numCol)) || (numTokens <= 0));

            do {
                //ask user for type of implementation
                System.out.println("Enter F for a (F)ast implementation or M for a (M)emory efficient implementation");
                Scanner scanner = new Scanner(System.in);
                implementation = scanner.next().charAt(0);
            }
            //repeat do while loop until conditions for user input is met
            while ((implementation != upperF) && (implementation != lowerF) && (implementation != upperM) &&
                    (implementation != lowerM));

            //choose which implementation to use
            if ((implementation == upperF) || (implementation == lowerF)) {
                q = new GameBoardFast(numRow, numCol, numTokens);
            } else {
                q = new GameBoardMem(numRow, numCol, numTokens);
            }

            do {
                //print the board
                System.out.println(q.toString());
                //update cpIndex
                cpIndex = changePlayer(cpIndex, numPlayers);
                //changes the player first
                currentPlayer = playerHolder[cpIndex];

                do {
                    //ask user for row
                    System.out.println("Player " + currentPlayer + " Please enter your ROW");
                    Scanner scanner = new Scanner(System.in);
                    rowLocation = scanner.nextInt();

                    //ask user for column
                    System.out.println("Player " + currentPlayer + " Please enter your COLUMN");
                    columnLocation = scanner.nextInt();

                    //get marker
                    m = new BoardPosition(rowLocation, columnLocation, currentPlayer);

                    //check if space is available
                    //if space is not available print that
                    if (!q.checkSpace(m)) {
                        //print the board
                        System.out.println(q.toString());
                        System.out.println("That space is unavailable, please pick again");
                    }
                }
                //do all the above while user input is out of bounds or space is unavailable(checkspace)
                while ((!q.checkSpace(m)));

                //if space is available and user input is good, then...
                // place the marker
                q.placeMarker(m);

                //check winner or tie
                //if there is a winner
                if (q.checkForWinner(m) == true) {
                    //print message to play again
                    System.out.println("Player " + currentPlayer + " wins!");
                    System.out.println("Would you like to play again? Y/N");

                    Scanner scanner = new Scanner(System.in);
                    playAgain = scanner.next().charAt(0);

                    //if user wants to play again
                    if (playAgain == upperYes || playAgain == lowerYes) {
                        //prompt user for board specifications again
                        do {
                            //ask user for number of players
                            System.out.println("How many players will play? (Max of 10)");
                            numPlayers = scanner.nextInt();

                            //check user input for number of players
                            if ((numPlayers > MAX_PLAYERS) || (numPlayers < MIN_PLAYERS)) {
                                //print error message to the screen
                                System.out.println("Must be between 2 and 10 players");
                            }
                        }
                        //repeat do while loop until conditions for numPlayers is met
                        while ((numPlayers > MAX_PLAYERS) || (numPlayers < MIN_PLAYERS));

                        do {
                            //ask user for number of rows
                            System.out.println("How many rows should be on the board?");
                            numRow = scanner.nextInt();


                            //check user input for number of rows
                            if ((numRow > IGameBoard.MAX_SIZE) || (numRow <= 0)) {
                                //print error message to the screen
                                System.out.println("Can only have 100 rows or less");
                            }
                        }
                        //repeat do while loop until conditions for numRows is met
                        while ((numRow > IGameBoard.MAX_SIZE) || (numRow <= 0));

                        do {
                            //ask user for number of columns
                            System.out.println("How many columns should be on the board?");
                            numCol = scanner.nextInt();


                            //check user input for number of columns
                            if ((numCol > IGameBoard.MAX_SIZE) || (numCol <= 0)) {
                                //print error message to the screen
                                System.out.println("Can only have 100 columns or less");
                            }
                        }
                        //repeat do while loop until conditions for numCols is met
                        while ((numCol > IGameBoard.MAX_SIZE) || (numCol <= 0));

                        do {
                            //ask user for number of tokens in a row to win
                            System.out.println("How many in a row to win?");
                            numTokens = scanner.nextInt();

                            //check user input for number of tokens
                            if (((numTokens > numRow) || (numTokens > numCol)) || (numTokens <= 0)) {
                                //print error message to the screen
                                System.out.println("You can't have that many because that's more than the number of " +
                                        "rows or " + "columns");
                            }
                        }
                        //repeat do while loop until conditions for numTokens is met
                        while (((numTokens > numRow) || (numTokens > numCol)) || (numTokens <= 0));

                        do {
                            //ask user for type of implementation
                            System.out.println("Enter F for a (F)ast implementation or M for a (M)emory efficient " +
                                    "implementation");
                            implementation = scanner.next().charAt(0);
                        }
                        //repeat do while loop until conditions for user input is met
                        while ((implementation != upperF) && (implementation != lowerF) && (implementation != upperM) &&
                                (implementation != lowerM));

                        //choose which implementation to use
                        if ((implementation == upperF) || (implementation == lowerF)) {
                            q = new GameBoardFast(numRow, numCol, numTokens);
                        } else {
                            q = new GameBoardMem(numRow, numCol, numTokens);
                        }
                        //change starting player back to X
                        cpIndex = -1;
                    }
                    //if user doesn't want to play again
                    else {
                        //exit the program
                        System.exit(0);
                    }
                }
                //if there isn't a winner
                if (!q.checkForWinner(m)) {
                    //check for draw
                    q.checkForDraw();
                }
                //if it's a draw
                if (q.checkForDraw() == true) {
                    //print message to play again
                    System.out.println("The game has resulted in a draw!");
                    System.out.println("Would you like to play again? Y/N");

                    Scanner scanner = new Scanner(System.in);
                    playAgain = scanner.next().charAt(0);

                    //if user wants to play again
                    if ((playAgain == upperYes) || (playAgain == lowerYes)) {

                        //prompt user for board specifications again
                        do {
                            //ask user for number of players
                            System.out.println("How many players will play? (Max of 10)");
                            numPlayers = scanner.nextInt();

                            //check user input for number of players
                            if ((numPlayers > MAX_PLAYERS) || (numPlayers < MIN_PLAYERS)) {
                                //print error message to the screen
                                System.out.println("Must be between 2 and 10 players");
                            }
                        }
                        //repeat do while loop until conditions for numPlayers is met
                        while ((numPlayers > MAX_PLAYERS) || (numPlayers < MIN_PLAYERS));

                        do {
                            //ask user for number of rows
                            System.out.println("How many rows should be on the board?");
                            numRow = scanner.nextInt();


                            //check user input for number of rows
                            if ((numRow > IGameBoard.MAX_SIZE) || (numRow < 0)) {
                                //print error message to the screen
                                System.out.println("Can only have 100 rows or less");
                            }
                        }
                        //repeat do while loop until conditions for numRows is met
                        while ((numRow > IGameBoard.MAX_SIZE) || (numRow < 0));

                        do {
                            //ask user for number of columns
                            System.out.println("How many columns should be on the board?");
                            numCol = scanner.nextInt();


                            //check user input for number of columns
                            if ((numCol > IGameBoard.MAX_SIZE) || (numCol < 0)) {
                                //print error message to the screen
                                System.out.println("Can only have 100 columns or less");
                            }
                        }
                        //repeat do while loop until conditions for numCols is met
                        while ((numCol > IGameBoard.MAX_SIZE) || (numCol < 0));

                        do {
                            //ask user for number of tokens in a row to win
                            System.out.println("How many in a row to win?");
                            numTokens = scanner.nextInt();

                            //check user input for number of tokens
                            if (((numTokens > numRow) || (numTokens > numCol)) || (numTokens <= 0)) {
                                //print error message to the screen
                                System.out.println("You can't have that many because that's more than the number of " +
                                        "rows or " + "columns");
                            }
                        }
                        //repeat do while loop until conditions for numTokens is met
                        while (((numTokens > numRow) || (numTokens > numCol)) || (numTokens <= 0));

                        do {
                            //ask user for type of implementation
                            System.out.println("Enter F for a (F)ast implementation or M for a (M)emory efficient " +
                                    "implementation");
                            implementation = scanner.next().charAt(0);
                        }
                        //repeat do while loop until conditions for user input is met
                        while ((implementation != upperF) && (implementation != lowerF) && (implementation != upperM) &&
                                (implementation != lowerM));

                        //choose which implementation to use
                        if ((implementation == upperF) || (implementation == lowerF)) {
                            q = new GameBoardFast(numRow, numCol, numTokens);
                        } else {
                            q = new GameBoardMem(numRow, numCol, numTokens);
                        }
                        //change starting player back to X
                        cpIndex = -1;
                    }
                    //if user doesn't want to play again
                    else {
                        //exit the program
                        System.exit(0);
                    }
                }
            }
            //outer do-while loop is repeated until there is a winner or a tie game
            while ((q.checkForWinner(m) == false) || (q.checkForDraw() == false));
    }

    /**
     * @param player is the index of the current player (has to be between 0 and 9)
     * @param currentNumPlayers is amount of players
     * @requires 0 <= player <= 9 and 2 <= currentNumPlayers <= 10
     * @ensures [player index will increment or reset to 0 and then increment] and [currentNumPlayers is not changed
     *           by the function]
     * @return player = integer value between 0 and 9
     */

    static private int changePlayer(int player, int currentNumPlayers) {

       //if current player is the last player then reset player to 0
       if(player == currentNumPlayers - 1){
           player = 0;
       }
       //else update player by incrementing it by 1
       else{
           player++;
       }

       return player;
    }
}

