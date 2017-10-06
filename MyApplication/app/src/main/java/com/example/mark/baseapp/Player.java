package com.example.mark.baseapp;

/**
 * Name:
 *
 *      Player - Information about player
 *
 * Description:
 *
 *      Keeps the players name
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class Player {

    // Player name to be displayed
    private String name = "";

    /**
     * Name:
     *
     *      Player - Constructor for Player
     *
     * Synopsis:
     *
     *      public Player(String a_name)
     *
     *          a_name --> Name of new player
     *
     * Description:
     *
     *      Sets the name of the player
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public Player(String a_name){
        this.name = a_name;
    }

    /**
     * Name:
     *
     *      getName - getter for players name
     *
     * Synopsis:
     *
     *      public String getName()
     *
     * Description:
     *
     *      Gets the string containing players name
     *
     * Returns:
     *
     *      String with players name
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Name:
     *
     *      setName - setter for players name
     *
     * Synopsis:
     *
     *      public void setName(String a_name)
     *
     *          a_name --> the new name to give to the player
     *
     * Description:
     *
     *      Allows the users name to be changed after its created
     *
     * Returns:
     *
     *      Nothing
     *
     * Author:
     *      Mark Bonadies
     *
     */
    public void setName(String a_name)
    {
        this.name = a_name;
    }

}
