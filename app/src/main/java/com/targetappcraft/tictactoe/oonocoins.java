package com.targetappcraft.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;

import java.util.Random;

public class oonocoins extends AppCompatActivity {



    public boolean selectedsingleplayer = true;
    CharSequence player2 = "Player 2";

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oonocoins);

        Button minus = findViewById(R.id.minus);
        Button plus = findViewById(R.id.plus);
        TextView goldtext = findViewById(R.id.goldstext);
        minus.getBackground().setAlpha(100);


        // AudienceNetworkAds.initialize(this);



        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();



        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {
        }


        //banner load
        admob.setbanner(findViewById(R.id.banner_container), oonocoins.this);
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(oonocoins.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(oonocoins.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(oonocoins.this);

        }


        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);



        SharedPreferences themes = getApplicationContext().getSharedPreferences("themes", MODE_PRIVATE);
        boolean themea1 = themes.getBoolean("themea1", true);
        boolean themea2 = themes.getBoolean("themea2", false);
        boolean themea3 = themes.getBoolean("themea3", false);
        boolean themea4 = themes.getBoolean("themea4", false);
        boolean themea5 = themes.getBoolean("themea5", false);
        boolean themea6 = themes.getBoolean("themea6", false);
        boolean themea7 = themes.getBoolean("themea7", false);
        boolean themea8 = themes.getBoolean("themea8", false);
        boolean themea9 = themes.getBoolean("themea9", false);
        boolean themea10 = themes.getBoolean("themea10", false);
        boolean themea11 = themes.getBoolean("themea11", false);



        ImageView img12 = findViewById(R.id.imageView12);
        if (themea1){
            img12.setImageResource(R.drawable.tic);
        } else if(themea2){
            img12.setImageResource(R.drawable.tic1);


        } else if (themea3){
            img12.setImageResource(R.drawable.tic2);

        } else if (themea4){
            img12.setImageResource(R.drawable.tic3);

        } else if (themea5){
            img12.setImageResource(R.drawable.tic4);

        } else if (themea6){
            img12.setImageResource(R.drawable.tic5);

        }else if (themea7){
            img12.setImageResource(R.drawable.tic6);

        }else if (themea8){
            img12.setImageResource(R.drawable.tic7);

        } else if(themea9){
            img12.setImageResource(R.drawable.tic8);

        }else if (themea10){
            img12.setImageResource(R.drawable.tic9);

        } else if(themea11){
            img12.setImageResource(R.drawable.tic10);

        }














        int coins = coinsandgems.getInt("coins", 0);

        Button play = findViewById(R.id.playbtn);

        SharedPreferences playcoin = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor playcoineditor = playcoin.edit();
        playcoineditor.putInt("playcoinamount", 500);        // Saving integer
        playcoineditor.apply();


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int gold = playcoin.getInt("playcoinamount", 500);



                if (gold == 500){
                    playcoineditor.putInt("playcoinamount", 2000);        // Saving integer
                    playcoineditor.apply();

                    String goldk = "2000";

                    goldtext.setText(goldk + " GOLD");
                    minus.getBackground().setAlpha(255);

                    if (coins < 2000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }

                }
                if (gold == 2000){

                    playcoineditor.putInt("playcoinamount", 5000);        // Saving integer
                    playcoineditor.apply();

                    String goldk = "5 K";
                    goldtext.setText(goldk + " GOLD");
                    if (coins < 5000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }
                if (gold == 5000){

                    playcoineditor.putInt("playcoinamount", 10000);        // Saving integer
                    playcoineditor.apply();
                    String goldk = "10 K";

                    goldtext.setText(goldk + " GOLD");
                    if (coins < 10000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }
                if (gold == 10000){

                    playcoineditor.putInt("playcoinamount", 25000);        // Saving integer
                    playcoineditor.apply();
                    String goldk = "25 K";

                    goldtext.setText(goldk + " GOLD");
                    if (coins < 25000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }

                if (gold == 25000){

                    playcoineditor.putInt("playcoinamount", 50000);        // Saving integer
                    playcoineditor.apply();
                    String goldk = "50 K";

                    goldtext.setText(goldk + " GOLD");
                    plus.getBackground().setAlpha(100);
                    if (coins < 50000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gold = playcoin.getInt("playcoinamount", 500);




                if (gold == 2000){

                    playcoineditor.putInt("playcoinamount", 500);        // Saving integer
                    playcoineditor.apply();

                    String goldk = "500";
                    goldtext.setText(goldk + " GOLD");
                    minus.getBackground().setAlpha(100);
                    if (coins < 500){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }
                if (gold == 5000){

                    playcoineditor.putInt("playcoinamount", 2000);        // Saving integer
                    playcoineditor.apply();
                    String goldk = "2000";

                    goldtext.setText(goldk + " GOLD");
                    if (coins < 2000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }
                if (gold == 10000){

                    playcoineditor.putInt("playcoinamount", 5000);        // Saving integer
                    playcoineditor.apply();
                    String goldk = "5 K";

                    goldtext.setText(goldk + " GOLD");
                    if (coins < 5000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }

                if (gold == 25000){

                    playcoineditor.putInt("playcoinamount", 10000);        // Saving integer
                    playcoineditor.apply();
                    String goldk = "10 K";

                    goldtext.setText(goldk + " GOLD");
                    if (coins < 10000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }


                if (gold == 50000){
                    playcoineditor.putInt("playcoinamount", 25000);        // Saving integer
                    playcoineditor.apply();

                    String goldk = "25 K";

                    goldtext.setText(goldk + " GOLD");
                    plus.getBackground().setAlpha(255);
                    if (coins < 25000){
                        play.getBackground().setAlpha(100);
                    } else {
                        play.getBackground().setAlpha(255);

                    }
                }

            }
        });



        ImageView imageView11 = findViewById(R.id.imageView11);

        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(oonocoins.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });









        if (coins < 500){
            play.getBackground().setAlpha(100);
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gold = playcoin.getInt("playcoinamount", 500);


                if (gold > coins){

                } else {

                    String player1 = "Thor";

                    Intent intent = new Intent(oonocoins.this, oneononechoose.class);
                    CharSequence[] players = {player1, player2};
                    intent.putExtra("playersnames", players);
                    intent.putExtra("selectedsingleplayer", selectedsingleplayer);
                    intent.putExtra("gold", gold);

                    startActivity(intent);
                    finish();
                }
            }
        });







        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystembars());
            }
        });




    }
    //NAVIGATION END
    private int hideSystembars(){
        return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
//NAVIGATION END

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(oonocoins.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}