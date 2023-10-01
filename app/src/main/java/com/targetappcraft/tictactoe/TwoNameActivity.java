package com.targetappcraft.tictactoe;

import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;
import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;


public class TwoNameActivity extends AppCompatActivity {

    public TextInputEditText plyr1;
    public TextInputEditText plyr2;

    public CharSequence player1 = "Player 1";
    public CharSequence player2 = "Player 2";
    public boolean selectedsingleplayer = false;
   // private AdView mAdView;
    private View decorView;

    private SoundPool soundPool;
    private int sound1;
    private int sound3StreamId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_name);


        //banner ad
        SharedPreferences fbads = getApplicationContext().getSharedPreferences("fbads", MODE_PRIVATE);
        SharedPreferences.Editor fbedit = fbads.edit();
        boolean adsenabled = fbads.getBoolean("adsenabled", true);

        //banner load
        admob.setbanner(findViewById(R.id.banner_container), TwoNameActivity.this);

        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(TwoNameActivity.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(TwoNameActivity.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(TwoNameActivity.this);

        }

        //buttonclicksound
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }


        RelativeLayout backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TwoNameActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();

        //  sfxeditor.putBoolean("sfx", true);        // Saving integer
        //  sfxeditor.apply();


        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {
            sound1 = soundPool.load(this, R.raw.clicksound, 1);
        }



        //ads=banner
        /*
        Appodeal.initialize(this, "3e4266809d5560eab8d5b8b2a1f6eb9876a4972321412b1d",Appodeal.INTERSTITIAL|Appodeal.REWARDED_VIDEO|Appodeal.BANNER);
        com.facebook.ads.AdView adView1 = new com.facebook.ads.AdView(TwoNameActivity.this, "750024865598460_751536375447309", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView1);

        // Request an ad
        //banner
        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
                // Called when banner is loaded
                Appodeal.show(TwoNameActivity.this, Appodeal.BANNER_BOTTOM);
            }
            @Override
            public void onBannerFailedToLoad() {
                // Called when banner failed to load

                adView1.loadAd();

            }
            @Override
            public void onBannerShown() {
                // Called when banner is shown
                if (adView1 != null) {
                    adView1.destroy();
                }
            }
            @Override
            public void onBannerShowFailed() {

                adView1.loadAd();
                // Called when banner show failed
            }
            @Override
            public void onBannerClicked() {
                // Called when banner is clicked
            }
            @Override
            public void onBannerExpired() {
                // Called when banner is expired

            }
        });



         */




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


        SharedPreferences themes = getApplicationContext().getSharedPreferences("themes", MODE_PRIVATE);
        SharedPreferences.Editor themeseditor = themes.edit();
        final boolean[] themep1 = {themes.getBoolean("themep1", true)};
        boolean themea1 = themes.getBoolean("themea1", true);

        final boolean[] themep2 = {themes.getBoolean("themep2", false)};
        boolean themea2 = themes.getBoolean("themea2", false);

        final boolean[] themep3 = {themes.getBoolean("themep3", false)};
        boolean themea3 = themes.getBoolean("themea3", false);


        final boolean[] themep4 = {themes.getBoolean("themep4", false)};
        boolean themea4 = themes.getBoolean("themea4", false);

        final boolean[] themep5 = {themes.getBoolean("themep5", false)};
        boolean themea5 = themes.getBoolean("themea5", false);

        final boolean[] themep6 = {themes.getBoolean("themep6", false)};
        boolean themea6 = themes.getBoolean("themea6", false);


        final boolean[] themep7 = {themes.getBoolean("themep7", false)};
        boolean themea7 = themes.getBoolean("themea7", false);

        final boolean[] themep8 = {themes.getBoolean("themep8", false)};
        boolean themea8 = themes.getBoolean("themea8", false);

        final boolean[] themep9 = {themes.getBoolean("themep9", false)};
        boolean themea9 = themes.getBoolean("themea9", false);

        final boolean[] themep10 = {themes.getBoolean("themep10", false)};
        boolean themea10 = themes.getBoolean("themea10", false);

        final boolean[] themep11 = {themes.getBoolean("themep11", false)};
        boolean themea11 = themes.getBoolean("themea11", false);



        ImageView img4 = findViewById(R.id.imageView5);
        ImageView img7 = findViewById(R.id.imageView4);

        if (themea1){

            img4.setImageResource(R.drawable.o);
            img7.setImageResource(R.drawable.x);

        } else if(themea2){
            img4.setImageResource(R.drawable.o1);
            img7.setImageResource(R.drawable.x1);

        } else if (themea3){
            img4.setImageResource(R.drawable.o2);
            img7.setImageResource(R.drawable.x2);
        } else if (themea4){
            img4.setImageResource(R.drawable.o3);
            img7.setImageResource(R.drawable.x3);
        } else if (themea5){
            img4.setImageResource(R.drawable.o4);
            img7.setImageResource(R.drawable.x4);
        } else if (themea6){
            img4.setImageResource(R.drawable.o5);
            img7.setImageResource(R.drawable.x5);
        }else if (themea7){
            img4.setImageResource(R.drawable.o6);
            img7.setImageResource(R.drawable.x6);
        }else if (themea8){
            img4.setImageResource(R.drawable.o7);
            img7.setImageResource(R.drawable.x7);
        } else if(themea9){
            img4.setImageResource(R.drawable.o8);
            img7.setImageResource(R.drawable.x8);
        }else if (themea10){
            img4.setImageResource(R.drawable.o9);
            img7.setImageResource(R.drawable.x9);
        } else if(themea11){
            img4.setImageResource(R.drawable.o10);
            img7.setImageResource(R.drawable.x10);
        }








/*
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-4215768049368739/9664957639");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


 */



        final   Button button = findViewById(R.id.button2);
        final ImageView imageView = findViewById(R.id.imageView4);
       SoftKeyboardStateHelper.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                imageView.setVisibility(View.GONE);

                button.setVisibility(View.GONE);

            }

            @Override
            public void onSoftKeyboardClosed() {
                imageView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        };

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(getApplicationContext(), findViewById(R.id.parent));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);


       final InputMethodManager imm = (InputMethodManager) getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);


/*
       final Handler handler = new Handler();
      final   int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something

                if (imm.isAcceptingText()) {
                    Toast.makeText(getApplicationContext(), "ISSSS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "NIIIII", Toast.LENGTH_SHORT).show();
                }
                handler.postDelayed(this, delay);
            }
        }, delay);

*/


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        plyr1 = (TextInputEditText) findViewById(R.id.playerone1);
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

        plyr2 = (TextInputEditText) findViewById(R.id.playerone2);
        plyr2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player2 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        Button ds = findViewById(R.id.button2);
        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sound1, 1, 1, 0, 0, 1);
                Intent i = new Intent(TwoNameActivity.this, ChooseActivity.class);
                i.putExtra("selectedsingleplayer", selectedsingleplayer);
                CharSequence[] players = {player1, player2};
                i.putExtra("playersnames", players);

                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TwoNameActivity.this, MainActivity.class);
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