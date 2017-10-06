package com.example.mark.baseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Name:
 *
 *      GameSelection - Activity Controller
 *
 * Description:
 *
 *      Interface for the user to select a game. Then launches the activity associated with that
 *      game.
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class GameSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        setTitle("Game Selection");

        Intent passed = getIntent();

        final TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        if (passed.getStringExtra("user") == null){
            tvUsername.setText( tvUsername.getText() + ": No User");
        }else{
            tvUsername.setText( tvUsername.getText() + ": "+ passed.getStringExtra("user"));
        }


        final Button bConnectFour = (Button) findViewById(R.id.bConnectFour);
        final Button bCheckers = (Button) findViewById(R.id.bCheckers);
        final Button bChess = (Button) findViewById(R.id.bChess);

        bCheckers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showToast("Checkers Launched");
                Intent checkers = new Intent(GameSelection.this, Checkers.class);
                startActivity(checkers);

            }
        });

        bChess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast("Chess Launched");
                Intent chess = new Intent(GameSelection.this, Chess.class);
                startActivity(chess);

            }
        });

        bConnectFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showToast("Connect Four Launched");
                Intent connectFour = new Intent(GameSelection.this, ConnectFourType.class);
                startActivity(connectFour);

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
