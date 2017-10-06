package com.example.mark.baseapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Name:
 *
 *      ConnectFourType - Get values needed to start connect four game
 *
 * Description:
 *
 *      Used to get parameters to play a game of connect four. Mainly the opponent type.
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class ConnectFourType extends AppCompatActivity {


    private int opponentType = -1;
    private String folderPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_four_type);

        File folder = new File( Environment.getExternalStorageDirectory() +
                File.separator + "ConnectFour");

        showToast(folder + "");
        folderPath = folder.getPath();
        showToast(folderPath);

        startGame();

    }

    /**
     * Name:
     *
     *      startGame - Spinner for opponent selection an button to start game
     *
     * Synopsis:
     *
     *      private void startGame()
     *
     * Description:
     *
     *      This fills the spinner with two opponent types and has a button to start the game
     *      assuming an opponent has been selected.
     *
     * Author:
     *      Mark Bonadies
     *
     */
    private void startGame() {

        List<String> list = new ArrayList<>();
        list.add("Select Opponent");
        list.add("Computer");
        list.add("Pass and Play");

        final Spinner opponent = (Spinner) findViewById(R.id.spnCFOpponent);

        opponent.setPadding(20,0,0,0);

        ArrayAdapter<String> aa = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        opponent.setAdapter(aa);


        opponent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String text = opponent.getSelectedItem().toString();

                if (position == 1)
                {
                    // 0 is the opponent computer player
                    opponentType = 0;
                }
                else if (position == 2)
                {
                    // 1 is the opponent human player
                    opponentType = 1;
                }
                else {
                    opponentType = -1;
                }
                //showToast(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Button newGame = (Button) findViewById(R.id.btnCFNewGame);

        newGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent connect = new Intent(ConnectFourType.this, ConnectFour.class);
                if (opponentType == 0)
                {
                    showToast("Opponent Computer");
                    connect.putExtra("opp", 0);
                    startActivity(connect);
                }
                else if (opponentType == 1)
                {
                    showToast("Opponent Human");
                    connect.putExtra("opp", 1);
                    startActivity(connect);
                }
                else
                {
                    showToast("Opponent not selected!");
                }

            }
        });

    }

    /**/
    /**

         NAME

            showToast - Displays on screen messages

         SYNOPSIS

            private void showToast(CharSequence a_msg);

                a_msg   --> The message to be displayed

         Description

            This function is used to display a_msg on the android device.

         RETURNS

            Nothing

         AUTHOR

            Mark Bonadies

         DATE

            12:30PM 3/20/2017

     */
    /**/
    private void showToast(CharSequence a_msg){
        Toast.makeText(this, a_msg, Toast.LENGTH_SHORT).show();
    }

}
