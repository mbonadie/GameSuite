package com.example.mark.baseapp;

/**
 * Name:
 *
 *      moveCheckerCF - Connect Four move checking
 *
 * Description:
 *
 *      Class that contains functions to check for move validity of current location.
 *      Also contains functions that return if the player has connected four or
 *      more of their pieces.
 *
 * Author:
 *      Mark Bonadies
 *
 */

public class moveCheckCF {

    /**
     * Name:
     *
     *      checkPerpendicular - Checks for a win vertically and horizontally
     *
     * Synopsis:
     *
     *      public boolean checkPerpendicular(int a_Row, int a_Col, Game a_board)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *
     * Description:
     *
     *      Uses the current location to get the count of players pieces both horizontally and
     *      vertically. It uses the function checkLeft and checkRight to get the horizontal total,
     *      checking to see if it is greater than or equal to four. If so it sets win equal to true.
     *      It then uses the function checkUp and checkDown to get the vertical total, checking to
     *      see if it is greater than or equal to four. If so it sets win equal to true. If both are not
     *      greater than four win is left as its default value false.
     *
     * Returns:
     *
     *      win --> Is equal to true if either the vertical or horizontal counts are greater than 3, false otherwise
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public boolean checkPerpendicular(int a_Row, int a_Col, Game a_board){
        int count = 0;
        boolean win = false;
        count = checkLeft(a_Row,a_Col,a_board, 0) + checkRight(a_Row,a_Col,a_board, 0) + 1;
        if (count >= 4){
            win = true;
        }
        count = checkDown(a_Row,a_Col,a_board, 0) + 1;
        if (count >= 4){
            win = true;
        }
        return win;
    }

    /**
     * Name:
     *
     *      checkPerpendicularMax - Checks the highest total piece count horizontally and vertically
     *
     * Synopsis:
     *
     *      public int checkPerpendicularMax(int a_Row, int a_Col, Game a_board)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *
     * Description:
     *
     *      Uses the current location to get the count of players pieces both horizontally and
     *      vertically. It uses the function checkLeft and checkRight to get the horizontal total,
     *      checking to see if it is greater than or equal to four. If so it returns the count.
     *      It then uses the function checkUp and checkDown to get the vertical total, checking to
     *      see if it is greater than or equal to four. If so it returns count2. If both are not
     *      greater than four it returns the Max of count and count2.
     *
     * Returns:
     *
     *      count --> This is returned if greater than 3
     *      or
     *      count2 --> This is returned if greater than 3
     *      Max.math(count, count2) --> This is returned if neither is greater than 3
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public int checkPerpendicularMax(int a_Row, int a_Col, Game a_board){
        int count = 0;
        count = checkLeft(a_Row,a_Col,a_board, 0) + checkRight(a_Row,a_Col,a_board, 0) + 1;
        if (count >= 4){
            return count;
        }
        int count2 = 0;
        count2 = checkDown(a_Row,a_Col,a_board, 0) + 1;
        if (count2 >= 4){
            return count2;
        }
        return Math.max(count,count2);
    }

    /**
     * Name:
     *
     *      checkLeft - Checks the total amount of players pieces left of the current location
     *
     * Synopsis:
     *
     *      private int checkLeft(int a_Row, int a_Col, Game a_board, int a_total)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *          a_total --> Keeps track of the count of players piece left of current location
     *
     * Description:
     *
     *      Counts up all the current players pieces left of the current location and increments
     *      a_total to reflect that. It stops incrementing once it finds an empty location or
     *      an opponents piece.
     *
     * Returns:
     *
     *      a_total --> This has been incremented to reflect the count of players pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private int checkLeft(int a_Row, int a_Col, Game a_board, int a_total){
        while (--a_Col >= 0)
        {
            if(a_board.b.getLocation(a_Row,a_Col).getPlayersPiece() == a_board.currentPlayer){
                a_total++;
            }
            else
            {
                return a_total;
            }
        }
        return a_total;
    }

    /**
     * Name:
     *
     *      checkRight - Checks the total amount of players pieces to the right of the current location
     *
     * Synopsis:
     *
     *      private int checkRight(int a_Row, int a_Col, Game a_board, int a_total)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *          a_total --> Keeps track of the count of players piece right of current location
     *
     * Description:
     *
     *      Counts up all the current players pieces to the right of the current location and increments
     *      a_total to reflect that. It stops incrementing once it finds an empty location or
     *      an opponents piece.
     *
     * Returns:
     *
     *      a_total --> This has been incremented to reflect the count of players pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private int checkRight(int a_Row, int a_Col, Game a_board, int a_total){
        while (++a_Col <= 6)
        {
            if(a_board.b.getLocation(a_Row,a_Col).getPlayersPiece() == a_board.currentPlayer){
                a_total++;
            }
            else
            {
                return a_total;
            }
        }
        return a_total;
    }

    /**
     * Name:
     *
     *      checkDown - Checks the total amount of players pieces below current location
     *
     * Synopsis:
     *
     *      private int checkDown(int a_Row, int a_Col, Game a_board, int a_total)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *          a_total --> Keeps track of the count of players piece below current location
     *
     * Description:
     *
     *      Counts up all the current players pieces below the current location and increments
     *      a_total to reflect that. It stops incrementing once it finds an empty location or
     *      an opponents piece.
     *
     * Returns:
     *
     *      a_total --> This has been incremented to reflect the count of players pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private int checkDown(int a_Row, int a_Col, Game a_board, int a_total){
        while (++a_Row <= 5)
        {
            if(a_board.b.getLocation(a_Row,a_Col).getPlayersPiece() == a_board.currentPlayer) {
                a_total++;
            }
            else
            {
                return a_total;
            }
        }
        return a_total;
    }

    /**
     * Name:
     *
     *      checkDiagonal - Checks for a win, diagonally, in both directions
     *
     * Synopsis:
     *
     *      public boolean checkDiagonal(int a_Row, int a_Col, Game a_board)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *
     * Description:
     *
     *      Uses the current location to get the count of players pieces diagonally. It uses the
     *      function checkUpLeft and checkDownRight to get one diagonal total, checking to see if
     *      it is greater than or equal to four. If so it sets win equal to true. It then uses the
     *      function checkUpRight and checkDownLeft to get the other diagonal total, checking to
     *      see if it is greater than or equal to four. If so it sets win equal to true. If both are not
     *      greater than four it returns win's default value of false.
     *
     * Returns:
     *
     *      win --> Is equal to true if either diagonals are greater than 3, false otherwise
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public boolean checkDiagonal(int a_Row, int a_Col, Game a_board){
        int count = 0;
        boolean win = false;
        count = checkUpLeft(a_Row,a_Col,a_board, 0) + checkDownRight(a_Row,a_Col,a_board, 0) + 1;
        if (count >= 4){
            win = true;
        }
        count = checkUpRight(a_Row,a_Col,a_board, 0) + checkDownLeft(a_Row,a_Col,a_board, 0) + 1;
        if (count >= 4){
            win = true;
        }
        return win;
    }

    /**
     * Name:
     *
     *      checkDiagonalMax - Checks the total amount of players pieces, diagonally, in both directions
     *
     * Synopsis:
     *
     *      public int checkDiagonalMax(int a_Row, int a_Col, Game a_board)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *
     * Description:
     *
     *      Uses the current location to get the count of players pieces diagonally. It uses the
     *      function checkUpLeft and checkDownRight to get one diagonal total, checking to see if
     *      it is greater than or equal to four. If so it returns the count. It then uses the
     *      function checkUpRight and checkDownLeft to get the other diagonal total, checking to
     *      see if it is greater than or equal to four. If so it returns count2. If both are not
     *      greater than four it returns the Max of count and count2.
     *
     * Returns:
     *
     *      count --> This is returned if greater than 3
     *      or
     *      count2 --> This is returned if greater than 3
     *      Max.math(count, count2) --> This is returned if neither is greater than 3
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public int checkDiagonalMax(int a_Row, int a_Col, Game a_board){
        int count = 0;
        count = checkUpLeft(a_Row,a_Col,a_board, 0) + checkDownRight(a_Row,a_Col,a_board, 0) + 1;
        if (count >= 4){
            return count;
        }
        int count2 = 0;
        count2 = checkUpRight(a_Row,a_Col,a_board, 0) + checkDownLeft(a_Row,a_Col,a_board, 0) + 1;
        if (count2 >= 4){
            return count2;
        }
        return Math.max(count,count2);
    }

    /**
     * Name:
     *
     *      checkUpLeft - Checks the total amount of players pieces up to the left (diagonally)
     *
     * Synopsis:
     *
     *      private int checkUpLeft(int a_Row, int a_Col, Game a_board, int a_total)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *          a_total --> Keeps track of the count of players piece diagonally up to the left
     *
     * Description:
     *
     *      Counts up all the current players pieces, on the left upwards diagonal,
     *      not including the starting point. a_count is returned once an empty space
     *      or an opponents piece has been reached.
     *
     * Returns:
     *
     *      a_total --> This has been incremented to reflect the count of players pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private int checkUpLeft(int a_Row, int a_Col, Game a_board, int a_total){
        while (--a_Row >= 0 && --a_Col >= 0)
        {
            if(a_board.b.getLocation(a_Row,a_Col).getPlayersPiece() == a_board.currentPlayer){
                a_total++;
            }
            else
            {
                return a_total;
            }
        }
        return a_total;
    }

    /**
     * Name:
     *
     *      checkUpRight - Checks the total amount of players pieces up to the right (diagonally)
     *
     * Synopsis:
     *
     *      private int checkUpRight(int a_Row, int a_Col, Game a_board, int a_total)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *          a_total --> Keeps track of the count of players piece diagonally up to the right
     *
     * Description:
     *
     *      Counts up all the current players pieces, on the right upwards diagonal,
     *      not including the starting point. a_count is returned once an empty space
     *      or an opponents piece has been reached.
     *
     * Returns:
     *
     *      a_total --> This has been incremented to reflect the count of players pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private int checkUpRight(int a_Row, int a_Col, Game a_board, int a_total){
        while (--a_Row >= 0 && ++a_Col <= 6)
        {
            if(a_board.b.getLocation(a_Row,a_Col).getPlayersPiece() == a_board.currentPlayer){
                a_total++;
            }
            else
            {
                return a_total;
            }
        }
        return a_total;
    }

    /**
     * Name:
     *
     *      checkDownRight - Checks the total amount of players pieces down to the right (diagonally)
     *
     * Synopsis:
     *
     *      private int checkDownRight(int a_Row, int a_Col, Game a_board, int a_total)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *          a_total --> Keeps track of the count of players piece diagonally down to the right
     *
     * Description:
     *
     *      Counts up all the current players pieces, on the right downwards diagonal,
     *      not including the starting point. a_count is returned once an empty space
     *      or an opponents piece has been reached.
     *
     * Returns:
     *
     *      a_total --> This has been incremented to reflect the count of players pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private int checkDownRight(int a_Row, int a_Col, Game a_board, int a_total){

        while (++a_Row <= 5 && ++a_Col <= 6)
        {
            if(a_board.b.getLocation(a_Row,a_Col).getPlayersPiece() == a_board.currentPlayer){
                a_total++;
            }
            else
            {
                return a_total;
            }
        }
        return a_total;
    }

    /**
     * Name:
     *
     *      checkDownLeft - Checks the total amount of players pieces down to the left (diagonally)
     *
     * Synopsis:
     *
     *      private int checkDownLeft(int a_Row, int a_Col, Game a_board, int a_total)
     *
     *          a_Row --> The starting locations row
     *          a_Col --> The starting locations column
     *          a_board --> The Object that contains the game board, b, for connect four
     *          a_total --> Keeps track of the count of players piece diagonally down to the left
     *
     * Description:
     *
     *      Counts up all the current players pieces, on the left downwards diagonal,
     *      not including the starting point. a_count is returned once an empty space
     *      or an opponents piece has been reached.
     *
     * Returns:
     *
     *      a_total --> This has been incremented to reflect the count of players pieces
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private int checkDownLeft(int a_Row, int a_Col, Game a_board, int a_total){

        while (++a_Row <= 5 && --a_Col >= 0)
        {
            if(a_board.b.getLocation(a_Row,a_Col).getPlayersPiece() == a_board.currentPlayer){
                a_total++;
            }
            else
            {
                return a_total;
            }
        }
        return a_total;
    }

    /**
     * Name:
     *
     *      checkCurrent - Checks current given location for piece
     *
     * Synopsis:
     *
     *      public boolean checkCurrent(int a_Row, int a_Col, Game a_board)
     *
     *          a_Row --> The row of the board you want to see
     *          a_Col --> The column of the board you want to see
     *          a_board --> The Object that contains the game board, b, for connect four
     *
     * Description:
     *
     *      This checks the given row and column in the board to determine if it contains
     *      an existing piece.
     *
     * Returns:
     *
     *      true --> if the board is empty in that given location
     *      false --> if the board has a piece occupying that location
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public boolean checkCurrent(int a_Row, int a_Col, Game a_board){
        return a_board.b.getLocation(a_Row,a_Col).getEmpty();
    }

}
