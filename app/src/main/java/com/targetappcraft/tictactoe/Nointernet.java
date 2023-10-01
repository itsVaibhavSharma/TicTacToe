package com.targetappcraft.tictactoe;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Nointernet extends AppCompatActivity {
    private  Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nointernet);
        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();

        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {
        }

        //themes
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

        ImageView img4 = findViewById(R.id.imageView4);
        ImageView img7 = findViewById(R.id.imageView7);

        if (themea1){

            img4.setImageDrawable(getResources().getDrawable(R.drawable.o));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x));

        } else if(themea2){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o1));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x1));

        } else if (themea3){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o2));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x2));
        } else if (themea4){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o3));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x3));
        } else if (themea5){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o4));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x4));
        } else if (themea6){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o5));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x5));
        }else if (themea7){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o6));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x6));
        }else if (themea8){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o7));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x7));
        } else if(themea9){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o8));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x8));
        }else if (themea10){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o9));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x9));
        } else if(themea11){
            img4.setImageDrawable(getResources().getDrawable(R.drawable.o10));
            img7.setImageDrawable(getResources().getDrawable(R.drawable.x10));
        }








        btn = (Button)findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkinternet();


            }
        });

    }

    private void checkinternet() {
        ConnectivityManager connMgr = (ConnectivityManager) Nointernet.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            Toast.makeText(this, "Welcome Back Online!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Nointernet.this,SplashActivity.class);
            startActivity(intent);
            Nointernet.this.finish();
        } else{



        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        checkinternet();

    }


    @Override
    protected void onResume() {
        super.onResume();

        checkinternet();

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        checkinternet();

    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        checkinternet();




    }
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        showExitDialog();
    }
    private void showExitDialog() {
        final Dialog dialog = new Dialog(Nointernet.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout_exit);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);

        dialog.show();

        Button exit = dialog.findViewById(R.id.yes_button);
        final Button dismiss = dialog.findViewById(R.id.no_button);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                // Intent intent = new Intent(FrontSignup.this, MainActivity.class);
                // startActivity(intent);
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}