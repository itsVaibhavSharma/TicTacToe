package com.targetappcraft.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;
import java.util.Timer;
import java.util.TimerTask;

public class ChooseActivity extends AppCompatActivity {

    CharSequence player1 = "Player 1";
    CharSequence player2 = "Player 2";
    public boolean selectedsingleplayer;
    private View decorView;
    boolean player1ax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        //NAVIGATION HIDE
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystembars());
            }
        });
        checkinternet();
//banner load
        admob.setbanner(findViewById(R.id.banner_container), ChooseActivity.this);
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(ChooseActivity.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(ChooseActivity.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(ChooseActivity.this);

        }

        // sfx sound
        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();

        //  sfxeditor.putBoolean("sfx", true);        // Saving integer
        //  sfxeditor.apply();


        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {
       //bg sound if any
        }


        ImageView imageView11 = findViewById(R.id.imageView11);

        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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


        //game code
        CharSequence[] players = getIntent().getCharSequenceArrayExtra("playersnames");
        player1 = players[0];
        player2 = players[1];
        selectedsingleplayer = getIntent().getBooleanExtra("selectedsingleplayer", true);
        final ImageView imageView = findViewById(R.id.imageView);
        final ImageView imageView2 = findViewById(R.id.imageView2);
        imageView.setColorFilter(getApplicationContext().getResources().getColor(R.color.tint2));
        imageView2.setColorFilter(getApplicationContext().getResources().getColor(R.color.tint2));
        final RadioButton r1 = findViewById(R.id.player1o);
        final RadioButton r2 = findViewById(R.id.player1x);
        final int textColor = Color.parseColor("#e5e9ea");
        final int textColorBlue = Color.parseColor("#3b7df8");

        //theme
        if (themea1){



        } else if(themea2){
            imageView.setImageResource(R.drawable.x1);
            imageView2.setImageResource(R.drawable.o1);

        } else if (themea3){
            imageView.setImageResource(R.drawable.x2);
            imageView2.setImageResource(R.drawable.o2);
        } else if (themea4){
            imageView.setImageResource(R.drawable.x3);
            imageView2.setImageResource(R.drawable.o3);
        } else if (themea5){
            imageView.setImageResource(R.drawable.x4);
            imageView2.setImageResource(R.drawable.o4);
        } else if (themea6){
            imageView.setImageResource(R.drawable.x5);
            imageView2.setImageResource(R.drawable.o5);
        }else if (themea7){
            imageView.setImageResource(R.drawable.x6);
            imageView2.setImageResource(R.drawable.o6);
        }else if (themea8){
            imageView.setImageResource(R.drawable.x7);
            imageView2.setImageResource(R.drawable.o7);
        } else if(themea9){
            imageView.setImageResource(R.drawable.x8);
            imageView2.setImageResource(R.drawable.o8);
        }else if (themea10){
            imageView.setImageResource(R.drawable.x9);
            imageView2.setImageResource(R.drawable.o9);
        } else if(themea11){
            imageView.setImageResource(R.drawable.x10);
            imageView2.setImageResource(R.drawable.o10);
        }

        //game code
        r1.post(new Runnable() {
            @Override
            public void run() {
                if (r1.isChecked()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        r1.setButtonTintList(ColorStateList.valueOf(textColorBlue));
                    }


                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        r1.setButtonTintList(ColorStateList.valueOf(textColor));
                    }
                }
                r1.postDelayed(this, 10);
            }
        });

        r2.post(new Runnable() {
            @Override
            public void run() {
                if (r2.isChecked()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        r2.setButtonTintList(ColorStateList.valueOf(textColorBlue));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        r2.setButtonTintList(ColorStateList.valueOf(textColor));
                    }

                }
                r2.postDelayed(this, 10);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2.setChecked(false);
                r1.setChecked(true);

                imageView2.setColorFilter(getApplicationContext().getResources().getColor(R.color.transparent));
                player1ax = false;

                if (themea1){
                    imageView2.setImageResource(R.drawable.oo);
                    imageView.setImageResource(R.drawable.xxsh);

                } else if(themea2){
                    imageView2.setImageResource(R.drawable.o1);
                    imageView.setImageResource(R.drawable.x1);


                } else if (themea3){
                    imageView2.setImageResource(R.drawable.o2);
                    imageView.setImageResource(R.drawable.x2);
                } else if (themea4){
                    imageView2.setImageResource(R.drawable.o3);
                    imageView.setImageResource(R.drawable.x3);
                } else if (themea5){
                    imageView2.setImageResource(R.drawable.o4);
                    imageView.setImageResource(R.drawable.x4);
                } else if (themea6){
                    imageView2.setImageResource(R.drawable.o5);
                    imageView.setImageResource(R.drawable.x5);
                }else if (themea7){
                    imageView2.setImageResource(R.drawable.o6);
                    imageView.setImageResource(R.drawable.x6);
                }else if (themea8){
                    imageView2.setImageResource(R.drawable.o7);
                    imageView.setImageResource(R.drawable.x7);
                } else if(themea9){
                    imageView2.setImageResource(R.drawable.o8);
                    imageView.setImageResource(R.drawable.x8);
                }else if (themea10){
                    imageView2.setImageResource(R.drawable.o9);
                    imageView.setImageResource(R.drawable.x9);
                } else if(themea11){
                    imageView2.setImageResource(R.drawable.o10);
                    imageView.setImageResource(R.drawable.x10);
                }
                imageView.setColorFilter(getApplicationContext().getResources().getColor(R.color.tint2));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setChecked(false);
                r2.setChecked(true);
                player1ax = true;
                imageView.setColorFilter(getApplicationContext().getResources().getColor(R.color.transparent));

                if (themea1){
                    imageView2.setImageResource(R.drawable.ooh);
                    imageView.setImageResource(R.drawable.xxs);

                } else if(themea2){
                    imageView2.setImageResource(R.drawable.o1);
                    imageView.setImageResource(R.drawable.x1);


                } else if (themea3){
                    imageView2.setImageResource(R.drawable.o2);
                    imageView.setImageResource(R.drawable.x2);
                } else if (themea4){
                    imageView2.setImageResource(R.drawable.o3);
                    imageView.setImageResource(R.drawable.x3);
                } else if (themea5){
                    imageView2.setImageResource(R.drawable.o4);
                    imageView.setImageResource(R.drawable.x4);
                } else if (themea6){
                    imageView2.setImageResource(R.drawable.o5);
                    imageView.setImageResource(R.drawable.x5);
                }else if (themea7){
                    imageView2.setImageResource(R.drawable.o6);
                    imageView.setImageResource(R.drawable.x6);
                }else if (themea8){
                    imageView2.setImageResource(R.drawable.o7);
                    imageView.setImageResource(R.drawable.x7);
                } else if(themea9){
                    imageView2.setImageResource(R.drawable.o8);
                    imageView.setImageResource(R.drawable.x8);
                }else if (themea10){
                    imageView2.setImageResource(R.drawable.o9);
                    imageView.setImageResource(R.drawable.x9);
                } else if(themea11){
                    imageView2.setImageResource(R.drawable.o10);
                    imageView.setImageResource(R.drawable.x10);
                }

                imageView2.setColorFilter(getApplicationContext().getResources().getColor(R.color.tint2));

            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2.setChecked(false);
                player1ax = false;
                if (themea1){
                    imageView2.setImageResource(R.drawable.oo);
                    imageView.setImageResource(R.drawable.xxsh);

                } else if(themea2){
                    imageView2.setImageResource(R.drawable.o1);
                    imageView.setImageResource(R.drawable.x1);


                } else if (themea3){
                    imageView2.setImageResource(R.drawable.o2);
                    imageView.setImageResource(R.drawable.x2);
                } else if (themea4){
                    imageView2.setImageResource(R.drawable.o3);
                    imageView.setImageResource(R.drawable.x3);
                } else if (themea5){
                    imageView2.setImageResource(R.drawable.o4);
                    imageView.setImageResource(R.drawable.x4);
                } else if (themea6){
                    imageView2.setImageResource(R.drawable.o5);
                    imageView.setImageResource(R.drawable.x5);
                }else if (themea7){
                    imageView2.setImageResource(R.drawable.o6);
                    imageView.setImageResource(R.drawable.x6);
                }else if (themea8){
                    imageView2.setImageResource(R.drawable.o7);
                    imageView.setImageResource(R.drawable.x7);
                } else if(themea9){
                    imageView2.setImageResource(R.drawable.o8);
                    imageView.setImageResource(R.drawable.x8);
                }else if (themea10){
                    imageView2.setImageResource(R.drawable.o9);
                    imageView.setImageResource(R.drawable.x9);
                } else if(themea11){
                    imageView2.setImageResource(R.drawable.o10);
                    imageView.setImageResource(R.drawable.x10);
                }


                imageView2.setColorFilter(getApplicationContext().getResources().getColor(R.color.transparent));
                imageView.setColorFilter(getApplicationContext().getResources().getColor(R.color.tint2));


            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setChecked(false);
                player1ax = true;
                if (themea1){
                    imageView2.setImageResource(R.drawable.ooh);
                    imageView.setImageResource(R.drawable.xxs);

                } else if(themea2){
                    imageView2.setImageResource(R.drawable.o1);
                    imageView.setImageResource(R.drawable.x1);


                } else if (themea3){
                    imageView2.setImageResource(R.drawable.o2);
                    imageView.setImageResource(R.drawable.x2);
                } else if (themea4){
                    imageView2.setImageResource(R.drawable.o3);
                    imageView.setImageResource(R.drawable.x3);
                } else if (themea5){
                    imageView2.setImageResource(R.drawable.o4);
                    imageView.setImageResource(R.drawable.x4);
                } else if (themea6){
                    imageView2.setImageResource(R.drawable.o5);
                    imageView.setImageResource(R.drawable.x5);
                }else if (themea7){
                    imageView2.setImageResource(R.drawable.o6);
                    imageView.setImageResource(R.drawable.x6);
                }else if (themea8){
                    imageView2.setImageResource(R.drawable.o7);
                    imageView.setImageResource(R.drawable.x7);
                } else if(themea9){
                    imageView2.setImageResource(R.drawable.o8);
                    imageView.setImageResource(R.drawable.x8);
                }else if (themea10){
                    imageView2.setImageResource(R.drawable.o9);
                    imageView.setImageResource(R.drawable.x9);
                } else if(themea11){
                    imageView2.setImageResource(R.drawable.o10);
                    imageView.setImageResource(R.drawable.x10);
                }


                imageView.setColorFilter(getApplicationContext().getResources().getColor(R.color.transparent));
                imageView2.setColorFilter(getApplicationContext().getResources().getColor(R.color.tint2));

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                if (r1.isChecked() || r2.isChecked()) {

                    Button ds = findViewById(R.id.button);
                    ds.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CharSequence[] players1 = getIntent().getCharSequenceArrayExtra("playersnames");
                            player1 = players1[0];
                            player2 = players1[1];


                            Intent i = new Intent(ChooseActivity.this, SceneActivity.class);
                            CharSequence[] players = {player1, player2};
                            i.putExtra("playersnames", players);
                            i.putExtra("player1ax", player1ax);
                            i.putExtra("selectedsingleplayer", selectedsingleplayer);
                            startActivity(i);
                        }
                    });
                }
            }


        }, 0, 20);//put here time 1000 milliseconds = 1 second



    }  //oncreate ends here

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
        startActivity(intent);
    }
    //NAVIGATION HIDE
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystembars());
        }
    }
    private int hideSystembars() {
        return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
    private void checkinternet() {
        ConnectivityManager connMgr = (ConnectivityManager) ChooseActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {

        } else {

            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChooseActivity.this, Nointernet.class);
            startActivity(intent);
            ChooseActivity.this.finish();

        }

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
