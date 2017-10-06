package com.example.mark.baseapp;

/**
 * Name:
 *
 *      Game - contains all needed elements for the game
 *
 * Description:
 *
 *      Sets up the proper board for the game type and keeps track of the players.
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class Game {

    conectFourBoard b; // = new conectFourBoard();
    Board board;
    Player playerOne = new Player("Player 1");
    Player playerTwo = new Player("Player 2");

    int currentPlayer = 0;
    private int moveLeft;
    boolean active = true;

    /**
     * Name:
     *
     *      Game - sets up the desired game
     *
     * Synopsis:
     *
     *      public Game(int a_moves, int game)
     *
     *          a_moves --> used to set total possible moves (connect four)
     *          a_game --> is the game type, connect four or checkers
     *
     * Description:
     *
     *      Creates a board, b, for connect four games or a standard board for checkers.
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public Game(int a_moves, int a_game) {

        this.moveLeft = a_moves;

        switch (a_game){
            case 1:
                b = new conectFourBoard();
                break;
            case 2:
                board = new Board(8,8);
                break;
            default:
                break;
        }
    }

    /**
     * Name:
     *
     *      getMoveLeft - getter for moveLeft
     *
     * Synopsis:
     *
     *      public int getMoveLeft()
     *
     * Description:
     *
     *      Gets the moveLeft variable and returns it
     *
     * Returns:
     *
     *      moveLeft
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public int getMoveLeft() {
        return moveLeft;
    }

    /**
     * Name:
     *
     *      decrementMoveLeft - lowers moveLeft
     *
     * Synopsis:
     *
     *      public void decrementMoveLeft()
     *
     * Description:
     *
     *      Decrements moveLeft
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void decrementMoveLeft(){--this.moveLeft;}

    /**
     * Name:
     *
     *      setMoveLeft - sets the moveLeft
     *
     * Synopsis:
     *
     *      public void setMoveLeft(int a_moveLeft)
     *
     *          a_moveLeft --> number to set moveLeft to
     *
     * Description:
     *
     *      Sets moveLeft to a given value
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setMoveLeft(int a_moveLeft) {
        this.moveLeft = a_moveLeft;
    }

    /**
     * Name:
     *
     *      getNextPlayer - returns the next player
     *
     * Synopsis:
     *
     *      public int getNextPlayer()
     *
     * Description:
     *
     *      Checks the current player and returns the alternative player
     *
     * Returns:
     *
     *      1 --> if the current player is 0
     *      0 --> if the current player is 1
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public int getNextPlayer()
    {
        if (currentPlayer == 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

}
