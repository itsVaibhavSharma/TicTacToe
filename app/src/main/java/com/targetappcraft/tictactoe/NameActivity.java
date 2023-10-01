package com.targetappcraft.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;
import java.util.Timer;
import java.util.TimerTask;

public class NameActivity extends AppCompatActivity {

    public TextInputEditText plyr1;
    public CharSequence player1 = "1";
    public CharSequence player2 = "2";
    private int length;
    public boolean selectedsingleplayer = true;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        //banner load
        admob.setbanner(findViewById(R.id.banner_container), NameActivity.this);

        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(NameActivity.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(NameActivity.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(NameActivity.this);

        }
        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();


        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {
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
//NAVIGATION END
        plyr1 = (TextInputEditText) findViewById(R.id.playerone);
        plyr1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                length = plyr1.getText().length();


            }
        }, 0, 2);//put here time 1000 milliseconds = 1 second
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (length > 1){

                    Button ds = findViewById(R.id.button2);
                    ds.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(NameActivity.this, ChooseActivity.class);
                            CharSequence[] players = {player1, player2};
                            i.putExtra("playersnames", players);
                            i.putExtra("selectedsingleplayer", selectedsingleplayer);
                            startActivity(i);
                        }
                    });

                }



            }
        }, 0, 20);//put here time 1000 milliseconds = 1 second
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NameActivity.this, MainActivity.class);
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
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }


}











