package com.example.mark.baseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


/**
 * Name:
 *
 *      LoadScreen - Simple main screen
 *
 * Description:
 *
 *      Just an application load screen seen once when application is started up.
 *
 * Author:
 *      Mark Bonadies
 *
 */
public class LoadScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        ImageView logo = (ImageView) findViewById(R.id.startLogo);
        logo.setImageResource(R.drawable.logo);
        logo.setVisibility(View.VISIBLE);
        logo.setScaleType(ImageView.ScaleType.FIT_XY);

        logo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent main = new Intent(LoadScreen.this, GameSelection.class);
                startActivity(main);

                // Prevents user from going back to this page
                finish();
            }

        });

    }
}
