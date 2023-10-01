package com.targetappcraft.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;
import com.tapadoo.alerter.Alerter;
import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;
import com.targetappcraft.tictactoe.ads.ondismis;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StoryScene extends AppCompatActivity {


    boolean easy = true;
    boolean medium;
    boolean hard;
    boolean impossible;

    boolean player1ax;
    Random r = new Random();

    int flag = 0, ax = 10, zero = 1, sensorflag = 0, win = 0, i, game = 1, prevrow, prevcol;
    int summ = 0, ctrflag = 0, night = 0, resetchecker = 1, currentgamedonechecker = 0;
    int score1 = 0, score2 = 0, drawchecker = 0;
    static int[][] tracker = new int[3][3];
    int[] sum = new int[8];
    static int[][] buttonpressed = new int[3][3];
    boolean selectedsingleplayer;
    private int savedValue;
    CharSequence player1 = "Player 1";
    CharSequence player2 = "Player 2";
    private boolean Vibration;
    private static final String PREFS_NAME = "vibration";
    private View decorView;
    private static final String PREF_VIBRATION = "TicVib";
    Dialog dialog;

    private BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_scene);


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

        checkinternet();

        //banner load
        admob.setbanner(findViewById(R.id.banner_container), StoryScene.this);

        //buttonclicksound



        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();


        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {

        }







        SharedPreferences losses = getApplicationContext().getSharedPreferences("loss", MODE_PRIVATE);
        SharedPreferences.Editor losseseditor = losses.edit();
        int loss = losses.getInt("loss", 0);


        if (loss > 2) {
            losseseditor.putInt("loss", 0);
            losseseditor.apply();
            easy = false;
            medium = true;
            hard = false;
            impossible = false;
        }

        if (loss > 4) {
            losseseditor.putInt("loss", 0);
            losseseditor.apply();
            easy = true;
            medium = false;
            hard = false;
            impossible = false;
        }


        dialog = new Dialog(this);


        int level = getIntent().getIntExtra("level", 0);


        TextView leveltext = findViewById(R.id.level);

        leveltext.setText("LEVEL " + level);


        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();



        int coins = coinsandgems.getInt("coins", 0);
        int gems = coinsandgems.getInt("gems", 0);


        TextView coinstext = findViewById(R.id.coinsamounttext);
        TextView gemstext = findViewById(R.id.gemsamounttext);

        coinstext.setText(String.valueOf(coins));
        gemstext.setText(String.valueOf(gems));

    Button skipthislevel = findViewById(R.id.skipthislevel);



        Boolean already = getIntent().getBooleanExtra("already",false);
        if (already){

            skipthislevel.setEnabled(false);
            skipthislevel.setAlpha(0.6f);
        }

        skipthislevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                skipdialog();





                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(StoryScene.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(StoryScene.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(StoryScene.this);

                }
                //ads=rewadrded
                //   revvid();
            }
        });


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                Vibration = preferences.getBoolean(PREF_VIBRATION, true);


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                savedValue = sharedPreferences.getInt("key", 0);


            }
        }, 0, 2);//put here time 1000 milliseconds = 1 second

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        final ImageButton imageButton = findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                myRotation.setRepeatCount(0);
                imageButton.startAnimation(myRotation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presentActivity(v);

                    }
                }, 900);
            }
        });

        CharSequence[] players = getIntent().getCharSequenceArrayExtra("playersnames");

        player1 = players[0];
        player2 = players[1];


        player1ax = getIntent().getBooleanExtra("player1ax", true);
        selectedsingleplayer = getIntent().getBooleanExtra("selectedsingleplayer", true);
        if (player1ax) {
            ax = 1;
            zero = 10;
        }
        TextView textName = findViewById(R.id.NameText);
        TextView textName2 = findViewById(R.id.NameText2);


        textName.setText(player1);

        if (!selectedsingleplayer) {
            textName2.setText(player2);
            //Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_SHORT).show();
        }

        RelativeLayout coinsrv = findViewById(R.id.coins);
        RelativeLayout gemsrv = findViewById(R.id.gems);

        coinsrv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){


                diaogbuycoins();

            }
        });





        gemsrv.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){

                dialogbuygolds();


            }
        });






        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjPxLfOjUt5czuaUlq5pTE0A/+xvhxmMtLVGD0eOnYZ3QWP97dPgENCnXjMWmHQXnOQoAzHzNnow0BCyokA9yuN2FxBHsss37h80kYtt+xovRwOmBubBw1nftGyxxXwmKmmZby78u0723OBORO2OAGk0rhf4UD0qBGznARVQL1YvmvZWvFFQnX3HpmJKvf4E3bqbpseMxzpoFULymKUY8MY5DtAJLw+Nj7AIJBwt8tbO1kuo7+ooDZKeLu4guabwnE+/XReuUGmXBnPqncrncgbxMI7/LYBY9TtdKxWvGuisWlcqOCT++Vcf22uKxCsaNveoni9NgfpStiS7Se9zbnQIDAQAB", null, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {



                if (productId.contains("gold4500")){

                    bp.consumePurchase("gold4500");
                    editorcoinsandgems.putInt("coins", coins + 4500);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));


                }

                if (productId.contains("gold30k")){

                    bp.consumePurchase("gold30k");

                    editorcoinsandgems.putInt("coins", coins + 30000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));


                } if (productId.contains("golds200k")){

                    bp.consumePurchase("golds200k");
                    editorcoinsandgems.putInt("coins", coins + 200000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));

                } if (productId.contains("gold500k")){

                    bp.consumePurchase("gold500k");

                    editorcoinsandgems.putInt("coins", coins + 500000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));

                } if (productId.contains("gold2m")){

                    bp.consumePurchase("gold2m");

                    editorcoinsandgems.putInt("coins", coins + 2000000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));


                } if (productId.contains("gold10m")){

                    bp.consumePurchase("gold10m");
                    editorcoinsandgems.putInt("coins", coins + 1000000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));

                } if (productId.contains("gem100")){

                    bp.consumePurchase("gem100");

                    editorcoinsandgems.putInt("gems", gems+ 100);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));


                } if (productId.contains("gem1000")){

                    bp.consumePurchase("gem1000");
                    editorcoinsandgems.putInt("gems", gems+ 1000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));

                } if (productId.contains("gem3000")){

                    bp.consumePurchase("gem3000");
                    editorcoinsandgems.putInt("gems", gems+ 3000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));

                } if (productId.contains("gem12k")){

                    bp.consumePurchase("gem12k");
                    editorcoinsandgems.putInt("gems", gems+ 12000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));



                } if (productId.contains("gem30k")){

                    bp.consumePurchase("gem30k");
                    editorcoinsandgems.putInt("gems", gems+ 30000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));


                }
                if (productId.contains("gem100k")){

                    bp.consumePurchase("gem100k");
                    editorcoinsandgems.putInt("gems", gems+ 100000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));

                }


            }

            @Override
            public void onPurchaseHistoryRestored() {

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {

            }

            @Override
            public void onBillingInitialized() {

            }
        });



        ScheduledExecutorService scheduledExecutorService2=
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService2.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // ads=inters


                        if (adsunit.mRewardedIntAd == null) {
                            admob.loadintrev(StoryScene.this);

                        }
                        if (adsunit.mRewardedAd == null) {
                            admob.loadrew(StoryScene.this);

                        }
                        if (adsunit.mInterstitial == null) {
                            admob.loadint(StoryScene.this);

                        }
                    }

                });
            }
        }, 5, 5, TimeUnit.SECONDS);




    }  // on create end














    private void skipdialog() {

        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();


        //  int wincoins = getIntent().getIntExtra("coins",0);
        //  int wingems = getIntent().getIntExtra("gems",0);

        int coins = coinsandgems.getInt("coins", 0);
        int gems = coinsandgems.getInt("gems", 0);

        //  editorcoinsandgems.putInt("coins", coins + wincoins);
        //  editorcoinsandgems.putInt("gems", gems + wingems);
        //  editorcoinsandgems.apply();
        dialog.setContentView(R.layout.skip_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        Button btnnxt = dialog.findViewById(R.id.btnskip);
        Button btnskipgem = dialog.findViewById(R.id.skipgem);
        Button btnskipad = dialog.findViewById(R.id.skipad);

        TextView coins2 = dialog.findViewById(R.id.wincoins);
        TextView gems2 = dialog.findViewById(R.id.wingems);
        Boolean already = getIntent().getBooleanExtra("already",false);

        int level2 = getIntent().getIntExtra("level",0);


        if (level2 < 10) {
            //editorcoinsandgems.putInt("gems", gems - 5);
            coins2.setText("5");
            if (gems<=5){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
            //  editorcoinsandgems.apply();
        } else if (level2 >=10 && level2 <20){
            //editorcoinsandgems.putInt("gems", gems - 8);
            coins2.setText("8");
            if (gems<=8){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=20 && level2 <30){
            // editorcoinsandgems.putInt("gems", gems - 10);
            coins2.setText("10");
            if (gems<=10){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=30 && level2 <40){
            // editorcoinsandgems.putInt("gems", gems - 15);
            coins2.setText("15");
            if (gems<=15){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=40 && level2 <50){
            //  editorcoinsandgems.putInt("gems", gems - 20);
            coins2.setText("20");
            if (gems<=20){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }

        }else if (level2 >=50 && level2 <100){
            // editorcoinsandgems.putInt("gems", gems - 25);
            coins2.setText("25");
            if (gems<=25){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=100 && level2 <125){
            //editorcoinsandgems.putInt("gems", gems - 35);
            coins2.setText("35");
            if (gems<=35){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=125 && level2 <150){
            //editorcoinsandgems.putInt("gems", gems - 45);
            coins2.setText("45");
            if (gems<=45){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=125 && level2 <150){
            // editorcoinsandgems.putInt("gems", gems - 55);
            coins2.setText("55");
            if (gems<=55){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=150 && level2 <175){
            //  editorcoinsandgems.putInt("gems", gems - 65);
            coins2.setText("65");
            if (gems<=65){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }else if (level2 >=175){
            //  editorcoinsandgems.putInt("gems", gems - 75);
            coins2.setText("75");
            if (gems<=75){

                btnskipgem.setEnabled(false);
                btnskipgem.setAlpha(0.6f);
            }
        }






        //coins2.setText("+ " + wincoins + " Golds");




        //gems2.setText("+ " + wingems + " Gems");

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);
                dialog.dismiss();

            }
        });



        btnskipgem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(StoryScene.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(StoryScene.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(StoryScene.this);

                }
//ads=inters
                new admob(new ondismis() {
                    @Override
                    public void ondismis() {
                        if (adsunit.mRewardedIntAd == null) {
                            admob.loadintrev(StoryScene.this);

                        }
                        if (adsunit.mRewardedAd == null) {
                            admob.loadrew(StoryScene.this);

                        }
                        if (adsunit.mInterstitial == null) {
                            admob.loadint(StoryScene.this);

                        }
                        //   Toast.makeText(MainActivity.this, "Action called", Toast.LENGTH_SHORT).show();
                    }
                }).showIntCall(StoryScene.this, true);


                if (level2 < 10) {

                    editorcoinsandgems.putInt("gems", gems - 5);
                    editorcoinsandgems.apply();

                    skiplevel();
                } else if (level2 >=10 && level2 <20){
                    editorcoinsandgems.putInt("gems", gems - 7);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=20 && level2 <30){
                    editorcoinsandgems.putInt("gems", gems - 10);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=30 && level2 <40){
                    editorcoinsandgems.putInt("gems", gems - 15);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=40 && level2 <50){
                    editorcoinsandgems.putInt("gems", gems - 20);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=50 && level2 <100){
                    editorcoinsandgems.putInt("gems", gems - 25);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=100 && level2 <125){
                    editorcoinsandgems.putInt("gems", gems - 35);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=125 && level2 <150){
                    editorcoinsandgems.putInt("gems", gems - 45);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=125 && level2 <150){
                    editorcoinsandgems.putInt("gems", gems - 55);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=150 && level2 <175){
                    editorcoinsandgems.putInt("gems", gems - 65);
                    editorcoinsandgems.apply();

                    skiplevel();
                }else if (level2 >=175){
                    editorcoinsandgems.putInt("gems", gems - 75);
                    editorcoinsandgems.apply();

                    skiplevel();
                }
            }


        });

        btnskipad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revvid();
            }
        });
        dialog.show();





    }


    private void diaogbuycoins() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.iapdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        // Button btnskip = dialog.findViewById(R.id.btnskip);

        ConstraintLayout coinslo = dialog.findViewById(R.id.coinslo);
        ConstraintLayout gemslo = dialog.findViewById(R.id.gemslo);
        coinslo.setVisibility(View.VISIBLE);
        gemslo.setVisibility(View.GONE);


        ImageView coinsicon = dialog.findViewById(R.id.coinsicon);
        ImageView gemsicon = dialog.findViewById(R.id.gemsicon);

        coinsicon.setBackgroundColor(getResources().getColor(R.color.grey_200));
        gemsicon.setBackgroundColor(getResources().getColor(R.color.white));

        // TextView coinst = dialog.findViewById(R.id.wincoins);
        // coinst.setText("- " + gold + " Golds");

        Button cbuy1 = dialog.findViewById(R.id.cbuy1);
        Button cbuy2 = dialog.findViewById(R.id.cbuy2);
        Button cbuy3 = dialog.findViewById(R.id.cbuy3);
        Button cbuy4 = dialog.findViewById(R.id.cbuy4);
        Button cbuy5 = dialog.findViewById(R.id.cbuy5);
        Button cbuy6 = dialog.findViewById(R.id.cbuy6);

        Button gbuy1 = dialog.findViewById(R.id.gbuy1);
        Button gbuy2 = dialog.findViewById(R.id.gbuy2);
        Button gbuy3 = dialog.findViewById(R.id.gbuy3);
        Button gbuy4 = dialog.findViewById(R.id.gbuy4);
        Button gbuy5 = dialog.findViewById(R.id.gbuy5);
        Button gbuy6 = dialog.findViewById(R.id.gbuy6);
        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });


        gemsicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coinslo.setVisibility(View.GONE);
                gemslo.setVisibility(View.VISIBLE);


                ImageView coinsicon = dialog.findViewById(R.id.coinsicon);
                ImageView gemsicon = dialog.findViewById(R.id.gemsicon);

                coinsicon.setBackgroundColor(getResources().getColor(R.color.white));
                gemsicon.setBackgroundColor(getResources().getColor(R.color.grey_200));
            }
        });

        coinsicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coinslo.setVisibility(View.VISIBLE);
                gemslo.setVisibility(View.GONE);


                coinsicon.setBackgroundColor(getResources().getColor(R.color.grey_200));
                gemsicon.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        cbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold4500");

            }
        });

        cbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold30k");

            }
        });

        cbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "golds200k");

            }
        });

        cbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold500k");

            }
        });

        cbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold2m");

            }
        });

        cbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(StoryScene.this, "gold10m");


            }
        });


        gbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem100");

            }
        });

        gbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem1000");

            }
        });

        gbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem3000");

            }
        });

        gbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem12k");

            }
        });

        gbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem30k");

            }
        });

        gbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem100k");

            }
        });


        dialog.show();


    }






    private void dialogbuygolds() {

        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.iapdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        // Button btnskip = dialog.findViewById(R.id.btnskip);

        ConstraintLayout coinslo = dialog.findViewById(R.id.coinslo);
        ConstraintLayout gemslo = dialog.findViewById(R.id.gemslo);
        coinslo.setVisibility(View.GONE);
        gemslo.setVisibility(View.VISIBLE);


        ImageView coinsicon = dialog.findViewById(R.id.coinsicon);
        ImageView gemsicon = dialog.findViewById(R.id.gemsicon);

        coinsicon.setBackgroundColor(getResources().getColor(R.color.white));
        gemsicon.setBackgroundColor(getResources().getColor(R.color.grey_200));

        // TextView coinst = dialog.findViewById(R.id.wincoins);
        // coinst.setText("- " + gold + " Golds");

        Button cbuy1 = dialog.findViewById(R.id.cbuy1);
        Button cbuy2 = dialog.findViewById(R.id.cbuy2);
        Button cbuy3 = dialog.findViewById(R.id.cbuy3);
        Button cbuy4 = dialog.findViewById(R.id.cbuy4);
        Button cbuy5 = dialog.findViewById(R.id.cbuy5);
        Button cbuy6 = dialog.findViewById(R.id.cbuy6);

        Button gbuy1 = dialog.findViewById(R.id.gbuy1);
        Button gbuy2 = dialog.findViewById(R.id.gbuy2);
        Button gbuy3 = dialog.findViewById(R.id.gbuy3);
        Button gbuy4 = dialog.findViewById(R.id.gbuy4);
        Button gbuy5 = dialog.findViewById(R.id.gbuy5);
        Button gbuy6 = dialog.findViewById(R.id.gbuy6);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });


        coinsicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coinslo.setVisibility(View.VISIBLE);
                gemslo.setVisibility(View.GONE);



                coinsicon.setBackgroundColor(getResources().getColor(R.color.grey_200));
                gemsicon.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        gemsicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coinslo.setVisibility(View.GONE);
                gemslo.setVisibility(View.VISIBLE);


                ImageView coinsicon = dialog.findViewById(R.id.coinsicon);
                ImageView gemsicon = dialog.findViewById(R.id.gemsicon);

                coinsicon.setBackgroundColor(getResources().getColor(R.color.white));
                gemsicon.setBackgroundColor(getResources().getColor(R.color.grey_200));
            }
        });

        cbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold4500");

            }
        });

        cbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold30k");

            }
        });

        cbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "golds200k");

            }
        });

        cbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold500k");

            }
        });

        cbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold2m");

            }
        });

        cbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gold10m");





            }
        });



        gbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem100");

            }
        });

        gbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem1000");

            }
        });

        gbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem3000");

            }
        });

        gbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem12k");

            }
        });

        gbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem30k");

            }
        });

        gbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(StoryScene.this, "gem100k");

            }
        });





        dialog.show();










    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }



    }


    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }

        super.onDestroy();
    }

















    private void skiplevel() {
        int level2 = getIntent().getIntExtra("level",0);


        SharedPreferences winpref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = winpref.edit();
        editor.putInt("win1", level2);        // Saving integer
        editor.apply();
        Intent intent = new Intent(StoryScene.this, story.class);
        intent.putExtra("next", true);
        startActivity(intent);
        finish();

    }


    private void win() {

        // Toast.makeText(this, String.valueOf(level2), Toast.LENGTH_SHORT).show();

        Boolean already = getIntent().getBooleanExtra("already",false);

        int level2 = getIntent().getIntExtra("level",0);


        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();


        int wincoins = getIntent().getIntExtra("coins",0);
        int wingems = getIntent().getIntExtra("gems",0);

        int coins = coinsandgems.getInt("coins", 0);
        int gems = coinsandgems.getInt("gems", 0);



        intersload();
        //   fbinter();


        if (already == true){

            winalreadydialog();

        } else{



            SharedPreferences winpref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = winpref.edit();
            editor.putInt("win1", level2);        // Saving integer
            editor.apply();


            editorcoinsandgems.putInt("coins", coins + wincoins);
            editorcoinsandgems.putInt("gems", gems + wingems);
            editorcoinsandgems.apply();

            windialog();

            // int win1=winpref.getInt("key_name2", 0);             // getting Integer
        }
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(StoryScene.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(StoryScene.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(StoryScene.this);

        }
//ads=inters
        new admob(new ondismis() {
            @Override
            public void ondismis() {
                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(StoryScene.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(StoryScene.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(StoryScene.this);

                }
                //   Toast.makeText(MainActivity.this, "Action called", Toast.LENGTH_SHORT).show();
            }
        }).showIntCall(StoryScene.this, true);

/*
        interstitialAd = new com.facebook.ads.InterstitialAd(StoryScene.this, "750024865598460_751535525447394");
fbinter();
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .build());

 */
    }

    private void winalreadydialog() {

        dialog.setContentView(R.layout.winalready_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        Button btnnxt = dialog.findViewById(R.id.btnnxt);


        intersshow();

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // soundPool.play(sound1, 1, 1, 0, 0, 1);



                doreset();
                dialog.dismiss();
                Intent intent = new Intent(StoryScene.this, story.class);
                startActivity(intent);
                finish();

            }
        });


        btnnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // soundPool.play(sound1, 1, 1, 0, 0, 1);

                finish();
                dialog.dismiss();
                doreset();

                Intent intent = new Intent(StoryScene.this, story.class);
                intent.putExtra("next", true);
                startActivity(intent);


            }
        });

        dialog.show();
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(StoryScene.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(StoryScene.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(StoryScene.this);

        }
        //ads=inters
        new admob(new ondismis() {
            @Override
            public void ondismis() {
                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(StoryScene.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(StoryScene.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(StoryScene.this);

                }
                //   Toast.makeText(MainActivity.this, "Action called", Toast.LENGTH_SHORT).show();
            }
        }).showIntCall(StoryScene.this, true);
        /*
        if (interstitialAd.isAdLoaded()){
            interstitialAd.show();} else {
            Appodeal.show(StoryScene.this, Appodeal.INTERSTITIAL);
        }

         */
    }



    private void lose(){

        Boolean already = getIntent().getBooleanExtra("already",false);



        if (already == true){
            losealreadydialog();

        } else {
            SharedPreferences losses = getApplicationContext().getSharedPreferences("loss", MODE_PRIVATE);
            SharedPreferences.Editor losseseditor = losses.edit();
            int loss = losses.getInt("loss", 0);

            losseseditor.putInt("loss", loss + 1);
            losseseditor.apply();
            losedialog();
        }
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(StoryScene.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(StoryScene.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(StoryScene.this);

        }
        //ads=inters
        intersload();
    }

    private void losealreadydialog() {
        dialog.setContentView(R.layout.lossalready_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        Button btnskip = dialog.findViewById(R.id.btnskip);
        Button btnretry = dialog.findViewById(R.id.btnretry);


        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);


                doreset();
                dialog.dismiss();
                Intent intent = new Intent(StoryScene.this, story.class);
                startActivity(intent);
                finish();

            }
        });


        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);


                dialog.dismiss();

                doreset();
                Intent intent = new Intent(StoryScene.this, story.class);
                //intent.putExtra("next", true);
                startActivity(intent);
                finish();
            }
        });

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);

                playmore();

                dialog.dismiss();

                // show ad
            }
        });


        dialog.show();
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(StoryScene.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(StoryScene.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(StoryScene.this);

        }
        //ads=inters
        intersshow();
    }

    private void draw(){

        Boolean already = getIntent().getBooleanExtra("already",false);

        if (already == true){
            drawalreadydialog();
        } else {
            SharedPreferences losses = getApplicationContext().getSharedPreferences("loss", MODE_PRIVATE);
            SharedPreferences.Editor losseseditor = losses.edit();
            int loss = losses.getInt("loss", 0);
            losseseditor.putInt("loss", loss + 1);
            losseseditor.apply();
            drawdialog();
        }
        //ads=inters
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(StoryScene.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(StoryScene.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(StoryScene.this);

        }
        intersload();
    }

    private void drawalreadydialog() {

        dialog.setContentView(R.layout.drawalready_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        Button btnskip = dialog.findViewById(R.id.btnskip);
        Button btnretry = dialog.findViewById(R.id.btnretry);


        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //soundPool.play(sound1, 1, 1, 0, 0, 1);


                doreset();
                dialog.dismiss();
                Intent intent = new Intent(StoryScene.this, story.class);
                startActivity(intent);
                finish();

            }
        });


        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);



                doreset();
                dialog.dismiss();

                Intent intent = new Intent(StoryScene.this, story.class);
                // intent.putExtra("next", true);
                startActivity(intent);
                finish();
            }
        });

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);



                playmore();
                dialog.dismiss();

                //show ad

            }
        });


        dialog.show();
        intersshow();
    }


    private void windialog(){

        int wincoins = getIntent().getIntExtra("coins",0);
        int wingems = getIntent().getIntExtra("gems",0);

        dialog.setContentView(R.layout.win_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        Button btnnxt = dialog.findViewById(R.id.btnskip);

        TextView coins = dialog.findViewById(R.id.wincoins);
        TextView gems = dialog.findViewById(R.id.wingems);

        coins.setText("+ " + wincoins + " Golds");
        gems.setText("+ " + wingems + " Gems");

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);



                doreset();
                dialog.dismiss();
                Intent intent = new Intent(StoryScene.this, story.class);
                startActivity(intent);
                finish();

            }
        });


        btnnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // soundPool.play(sound1, 1, 1, 0, 0, 1);

                doreset();
                finish();
                dialog.dismiss();

                //ads=inters destroy krna hai


                // interstitialAd.destroy();
                Intent intent = new Intent(StoryScene.this, story.class);
                intent.putExtra("next", true);
                startActivity(intent);


            }
        });

        dialog.show();
        intersshow();

    }


    private void losedialog(){

        dialog.setContentView(R.layout.loss_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        Button btnskip = dialog.findViewById(R.id.btnskip);
        Button btnretry = dialog.findViewById(R.id.btnretry);


        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // soundPool.play(sound1, 1, 1, 0, 0, 1);


                doreset();
                dialog.dismiss();
                Intent intent = new Intent(StoryScene.this, story.class);
                startActivity(intent);

            }
        });


        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revvid();
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);

/*
                int level2 = getIntent().getIntExtra("level",0);

                doreset();
                dialog.dismiss();

                SharedPreferences winpref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = winpref.edit();
                editor.putInt("win1", level2);        // Saving integer
                editor.apply();
                Intent intent = new Intent(StoryScene.this, story.class);
                intent.putExtra("next", true);
                startActivity(intent);
                finish();

 */
                //doreset();

                //   interstitialAd.destroy();
                //   revvid();
                dialog.dismiss();
            }
        });

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // soundPool.play(sound1, 1, 1, 0, 0, 1);

                // Toast.makeText(StoryScene.this, "Retry btn presed", Toast.LENGTH_SHORT).show();
                //doreset();
                //dialog.dismiss();
                // interstitialAd.destroy();
                playmore();
                dialog.dismiss();

                // show ad

            }
        });


        dialog.show();
        //  intersshow();
    }

    private void drawdialog(){

        dialog.setContentView(R.layout.draw_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
        Button btnskip = dialog.findViewById(R.id.btnskip);
        Button btnretry = dialog.findViewById(R.id.btnretry);


        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   soundPool.play(sound1, 1, 1, 0, 0, 1);


                doreset();
                dialog.dismiss();
                Intent intent = new Intent(StoryScene.this, story.class);
                startActivity(intent);
                finish();

            }
        });


        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   soundPool.play(sound1, 1, 1, 0, 0, 1);
                revvid();

/*
                int level2 = getIntent().getIntExtra("level",0);

                doreset();
                dialog.dismiss();

                SharedPreferences winpref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = winpref.edit();
                editor.putInt("win1", level2);        // Saving integer
                editor.apply();
                Intent intent = new Intent(StoryScene.this, story.class);
                intent.putExtra("next", true);
                startActivity(intent);
                finish();

 */
            }
        });

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  soundPool.play(sound1, 1, 1, 0, 0, 1);



                // doreset();
                playmore();
                dialog.dismiss();

                //show ad


            }
        });


        dialog.show();
        // intersshow();
    }





    public void resetforad(){

        if ((drawchecker > 0) || (win > 0)) {
            game++;
            //    TextView qq = (TextView) findViewById(R.id.gamenumber);
            //  qq.setText("" + game);

            for (int i = 0; i < 8; i++)
                sum[i] = 0;

            drawchecker = 0;


            ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;
            q1 = (ImageView) findViewById(R.id.tzz);
            q2 = (ImageView) findViewById(R.id.tzo);
            q3 = (ImageView) findViewById(R.id.tzt);
            q4 = (ImageView) findViewById(R.id.toz);
            q5 = (ImageView) findViewById(R.id.too);
            q6 = (ImageView) findViewById(R.id.tot);
            q7 = (ImageView) findViewById(R.id.ttz);
            q8 = (ImageView) findViewById(R.id.tto);
            q9 = (ImageView) findViewById(R.id.ttt);
            q1.setImageDrawable(null);
            q2.setImageDrawable(null);
            q3.setImageDrawable(null);
            q4.setImageDrawable(null);
            q5.setImageDrawable(null);
            q6.setImageDrawable(null);
            q7.setImageDrawable(null);
            q8.setImageDrawable(null);
            q9.setImageDrawable(null);

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    buttonpressed[i][j] = 0;

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    tracker[i][j] = 0;

//ToTo som ja dal prec

            if (!selectedsingleplayer) {
                if ((game + 1) % 2 == 0) {
                    Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "" + player2 + "\'s turn", Toast.LENGTH_SHORT).show();
            }

            win = 0;
            summ = 0;
            ctrflag = 0;
            flag = (game + 1) % 2;
            currentgamedonechecker = 0;

            if (selectedsingleplayer && (game % 2 == 0))
                cpuplay();
        }

    }








    //make it easy

    public void makeiteasy() {
        SharedPreferences mke = getApplicationContext().getSharedPreferences("mke", MODE_PRIVATE);

        Boolean mkeb = mke.getBoolean("mkeb", false);

        if (!mkeb){
            GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(StoryScene.this);
            if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                Games.getAchievementsClient(StoryScene.this, GoogleSignIn.getLastSignedInAccount(StoryScene.this))
                        .unlock(getString(R.string.my_achievement_id26));
                Alerter.create(StoryScene.this)
                        .setTitle("Achievement Unlocked!")
                        .setText("Difficulty Switching Wizard!")
                        .setIcon(R.drawable.achievement)
                        .setIconColorFilter(0) // Optional - Removes white tint
                        .setBackgroundColorRes(R.color.colorAccent)
                        .show();        }}
        SharedPreferences.Editor mekd = mke.edit();
        mekd.putBoolean("mkeb", true);        // Saving integer
        mekd.apply();

        resetforad();
        easy = true;
        medium = false;
        hard = false;
        impossible = false;


    }


















    public void kzz(View view) {


        if (win == 0 && buttonpressed[0][0] == 0) {
            if (flag % 2 == 0)
                tracker[0][0] = ax;
            else
                tracker[0][0] = zero;

            printBoard();
            winchecker();
            cpuplay();
            flag++;
            buttonpressed[0][0]++;
        }
    }


    public void kzo(View view) {

        if (win == 0 && buttonpressed[0][1] == 0) {
            if (flag % 2 == 0) tracker[0][1] = ax;
            else tracker[0][1] = zero;

            printBoard();
            winchecker();
            cpuplay();
            buttonpressed[0][1]++;
            flag++;
        }
    }

    public void kzt(View view) {
        if (win == 0 && buttonpressed[0][2] == 0) {
            if (flag % 2 == 0) tracker[0][2] = ax;
            else tracker[0][2] = zero;

            printBoard();
            winchecker();
            cpuplay();
            buttonpressed[0][2]++;
            flag++;
        }
    }

    public void koz(View v) {
        if (win == 0 && buttonpressed[1][0] == 0) {
            if (flag % 2 == 0) tracker[1][0] = ax;
            else tracker[1][0] = zero;

            printBoard();
            winchecker();
            cpuplay();

            ++buttonpressed[1][0];
            flag++;
        }
    }

    public void koo(View v) {
        if (win == 0 && buttonpressed[1][1] == 0) {
            if (flag % 2 == 0) tracker[1][1] = ax;
            else tracker[1][1] = zero;
            printBoard();
            winchecker();
            cpuplay();
            ++buttonpressed[1][1];
            flag++;
        }
    }

    public void kot(View v) {
        if (win == 0 && buttonpressed[1][2] == 0) {
            if (flag % 2 == 0) tracker[1][2] = ax;
            else tracker[1][2] = zero;

            printBoard();
            winchecker();
            cpuplay();
            ++buttonpressed[1][2];
            flag++;
        }
    }

    public void ktz(View v) {
        if (win == 0 && buttonpressed[2][0] == 0) {
            if (flag % 2 == 0) tracker[2][0] = ax;
            else tracker[2][0] = zero;

            printBoard();
            winchecker();
            cpuplay();
            ++buttonpressed[2][0];
            flag++;
        }
    }

    public void kto(View v) {
        if (win == 0 && buttonpressed[2][1] == 0) {
            if (flag % 2 == 0) tracker[2][1] = ax;
            else tracker[2][1] = zero;
            printBoard();
            winchecker();
            cpuplay();
            ++buttonpressed[2][1];
            flag++;
        }
    }

    public void ktt(View v) {
        if (win == 0 && buttonpressed[2][2] == 0) {
            if (flag % 2 == 0) tracker[2][2] = ax;
            else tracker[2][2] = zero;

            printBoard();
            winchecker();
            cpuplay();
            ++buttonpressed[2][2];
            flag++;
        }
    }

    public void cpuplay() {
        if ((selectedsingleplayer) && (win == 0)) {



            final Handler handler = new Handler();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {

                            //add code to be executed after a pause
                            if (ifcpuwin()) ;
                            else if (ifopowin()) ;
                            else if (emptycentre()) ;
                            else if (emptycorner()) ;
                            else emptyany();

                            printBoard();
                            winchecker();

                            flag++;
                        }
                    });
                }
            }, 110);

            return;
        }
    }

    public boolean ifcpuwin() {
        if (!easy) {
            for (i = 0; i < 8; i++) {
                if (sum[i] == 2 * zero) {
                    if (i == 0) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[0][x] == 0)
                                tracker[0][x] = zero;
                    }

                    if (i == 1) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[1][x] == 0)
                                tracker[1][x] = zero;
                    }
                    if (i == 2) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[2][x] == 0)
                                tracker[2][x] = zero;
                    }

                    if (i == 3) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[x][0] == 0)
                                tracker[x][0] = zero;
                    }

                    if (i == 4) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][1] == 0)
                                tracker[x][1] = zero;
                    }

                    if (i == 5) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][2] == 0)
                                tracker[x][2] = zero;
                    }
                    if (i == 6) {

                        for (int y = 0; y < 3; y++)
                            for (int x = 0; x < 3; x++)
                                if (x == y)
                                    if (tracker[x][y] == 0)
                                        tracker[x][y] = zero;
                    }
                    if (i == 7) {
                        if (tracker[0][2] == 0)
                            tracker[0][2] = zero;
                        else if (tracker[1][1] == 0)
                            tracker[1][1] = zero;
                        else tracker[2][0] = zero;

                    }
                    return true;
                }
            }
        }
        return false;
    }


    public boolean ifopowin() {
        if ((!easy) || (!medium)) {

            for (i = 0; i < 8; i++) {
                if (sum[i] == 2 * ax) {
                    if (i == 0) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[0][x] == 0) {
                                tracker[0][x] = zero;
                                buttonpressed[0][x]++;
                            }
                    }

                    if (i == 1) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[1][x] == 0) {
                                tracker[1][x] = zero;
                                buttonpressed[1][x]++;
                            }
                    }
                    if (i == 2) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[2][x] == 0) {
                                tracker[2][x] = zero;
                                buttonpressed[2][x]++;
                            }
                    }

                    if (i == 3) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[x][0] == 0) {
                                tracker[x][0] = zero;
                                buttonpressed[x][0]++;
                            }
                    }

                    if (i == 4) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][1] == 0) {
                                tracker[x][1] = zero;
                                buttonpressed[x][1]++;
                            }
                    }

                    if (i == 5) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][2] == 0) {
                                tracker[x][2] = zero;
                                buttonpressed[x][2]++;
                            }
                    }
                    if (i == 6) {

                        for (int y = 0; y < 3; y++)
                            for (int x = 0; x < 3; x++)
                                if (x == y)
                                    if (tracker[x][y] == 0) {
                                        tracker[x][y] = zero;
                                        buttonpressed[x][y]++;
                                    }


                    }
                    if (i == 7) {
                        if (tracker[0][2] == 0) {
                            tracker[0][2] = zero;
                            buttonpressed[0][2]++;
                        } else if (tracker[1][1] == 0) {
                            tracker[1][1] = zero;
                            buttonpressed[1][1]++;
                        } else {
                            tracker[2][0] = zero;
                            buttonpressed[2][0]++;
                        }


                    }
                    return true;
                }
            }

        }
        return false;
    }

    public boolean emptycentre() {
        if (impossible || hard) {
            if (tracker[1][1] == 0) {
                tracker[1][1] = zero;
                buttonpressed[1][1]++;
                return true;
            }
        }
        return false;
    }

    public boolean emptycorner() {


        if (hard || impossible)
            if (((tracker[0][0] + tracker[2][2]) == 2 * ax) || ((tracker[0][2] + tracker[2][0]) == 2 * ax)) {
                for (int k = 0; k < 3; k++)
                    for (int j = 0; j < 3; j++)
                        if ((k + j) % 2 == 1) {
                            if (tracker[k][j] == 0)
                                tracker[k][j] = zero;
                            buttonpressed[k][j]++;
                            return true;
                        }
            }


        if (impossible)
            if (sum[6] == zero || sum[7] == zero) {
                if (sum[6] == zero) {
                    if ((sum[0] + sum[3]) > (sum[2] + sum[5])) {
                        tracker[0][0] = zero;
                        buttonpressed[0][0]++;
                    } else {
                        tracker[2][2] = zero;
                        buttonpressed[2][2]++;
                    }
                    return true;
                }

                if (sum[7] == zero) {
                    if ((sum[0] + sum[5]) > (sum[3] + sum[2])) {
                        tracker[0][2] = zero;
                        buttonpressed[0][2]++;
                    } else {
                        tracker[2][0] = zero;
                        buttonpressed[2][0]++;
                    }
                    return true;
                }

            }


        for (int i = 0; i < 3; i++) {
            if (tracker[0][i] == ax) {
                if (tracker[0][0] == 0) {
                    tracker[0][0] = zero;
                    buttonpressed[0][0]++;
                    return true;
                }
                if (tracker[0][2] == 0) {
                    tracker[0][2] = zero;
                    buttonpressed[0][2]++;
                    return true;
                }
            }
        }

        for (int i = 0; i < 3; i++) {

            if (tracker[2][i] == ax) {
                if (tracker[2][0] == 0) {
                    tracker[2][0] = zero;
                    buttonpressed[2][0]++;
                    return true;
                }
                if (tracker[2][2] == 0) {
                    tracker[2][2] = zero;
                    buttonpressed[2][2]++;
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (tracker[i][0] == ax) {
                if (tracker[0][0] == 0) {
                    tracker[0][0] = zero;
                    buttonpressed[0][0]++;
                    return true;
                }
                if (tracker[2][0] == 0) {
                    tracker[2][0] = zero;
                    buttonpressed[2][0]++;
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (tracker[i][2] == ax) {
                if (tracker[0][2] == 0) {
                    tracker[0][2] = zero;
                    buttonpressed[0][2]++;
                    return true;
                }
                if (tracker[2][2] == 0) {
                    tracker[2][2] = zero;
                    buttonpressed[2][2]++;
                    return true;
                }
            }
        }
        return false;

    }

    public void emptyany() {

        if (ctrflag == 0)
            while (true) {
                int x = rand();
                int y = rand();

                if (tracker[x][y] == 0) {
                    tracker[x][y] = zero;
                    buttonpressed[x][y]++;
                    return;

                }
            }

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                if (tracker[x][y] == 0) {
                    tracker[x][y] = zero;
                    buttonpressed[x][y]++;
                    return;
                }


    }

    public int rand() {
        return r.nextInt(3);
    }

    public void printBoard() {

        ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;

        q1 = (ImageView) findViewById(R.id.tzz);
        q2 = (ImageView) findViewById(R.id.tzo);
        q3 = (ImageView) findViewById(R.id.tzt);
        q4 = (ImageView) findViewById(R.id.toz);
        q5 = (ImageView) findViewById(R.id.too);
        q6 = (ImageView) findViewById(R.id.tot);
        q7 = (ImageView) findViewById(R.id.ttz);
        q8 = (ImageView) findViewById(R.id.tto);
        q9 = (ImageView) findViewById(R.id.ttt);









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




        if (themea1){


            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.xscene);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.oscene);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.xscene);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.oscene);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.xscene);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.oscene);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.xscene);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.oscene);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.xscene);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.oscene);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.xscene);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.oscene);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.xscene);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.oscene);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.xscene);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.oscene);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.xscene);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.oscene);






        } else if(themea2){

            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x1);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o1);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x1);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o1);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x1);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o1);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x1);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o1);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x1);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o1);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x1);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o1);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x1);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o1);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x1);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o1);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x1);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o1);



        } else if (themea3){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x2);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o2);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x2);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o2);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x2);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o2);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x2);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o2);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x2);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o2);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x2);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o2);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x2);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o2);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x2);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o2);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x2);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o2);

        } else if (themea4){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x3);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o3);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x3);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o3);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x3);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o3);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x3);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o3);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x3);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o3);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x3);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o3);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x3);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o3);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x3);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o3);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x3);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o3);

        } else if (themea5){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x4);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o4);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x4);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o4);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x4);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o4);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x4);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o4);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x4);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o4);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x4);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o4);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x4);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o4);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x4);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o4);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x4);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o4);
        } else if (themea6){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x5);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o5);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x5);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o5);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x5);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o5);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x5);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o5);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x5);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o5);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x5);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o5);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x5);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o5);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x5);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o5);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x5);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o5);
        }else if (themea7){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x6);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o6);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x6);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o6);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x6);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o6);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x6);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o6);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x6);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o6);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x6);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o6);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x6);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o6);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x6);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o6);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x6);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o6);
        }else if (themea8){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x7);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o7);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x7);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o7);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x7);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o7);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x7);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o7);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x7);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o7);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x7);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o7);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x7);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o7);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x7);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o7);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x7);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o7);
        } else if(themea9){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x8);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o8);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x8);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o8);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x8);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o8);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x8);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o8);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x8);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o8);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x8);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o8);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x8);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o8);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x8);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o8);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x8);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o8);
        }else if (themea10){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x9);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o9);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x9);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o9);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x9);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o9);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x9);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o9);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x9);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o9);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x9);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o9);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x9);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o9);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x9);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o9);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x9);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o9);
        } else if(themea11){
            if (tracker[0][0] == 1) q1.setImageResource(R.drawable.x10);
            if (tracker[0][0] == 10) q1.setImageResource(R.drawable.o10);


            if (tracker[0][1] == 1) q2.setImageResource(R.drawable.x10);
            if (tracker[0][1] == 10) q2.setImageResource(R.drawable.o10);


            if (tracker[0][2] == 1) q3.setImageResource(R.drawable.x10);
            if (tracker[0][2] == 10) q3.setImageResource(R.drawable.o10);


            if (tracker[1][0] == 1) q4.setImageResource(R.drawable.x10);
            if (tracker[1][0] == 10) q4.setImageResource(R.drawable.o10);

            if (tracker[1][1] == 1) q5.setImageResource(R.drawable.x10);
            if (tracker[1][1] == 10) q5.setImageResource(R.drawable.o10);


            if (tracker[1][2] == 1) q6.setImageResource(R.drawable.x10);
            if (tracker[1][2] == 10) q6.setImageResource(R.drawable.o10);

            if (tracker[2][0] == 1) q7.setImageResource(R.drawable.x10);
            if (tracker[2][0] == 10) q7.setImageResource(R.drawable.o10);


            if (tracker[2][1] == 1) q8.setImageResource(R.drawable.x10);
            if (tracker[2][1] == 10) q8.setImageResource(R.drawable.o10);

            if (tracker[2][2] == 1) q9.setImageResource(R.drawable.x10);
            if (tracker[2][2] == 10) q9.setImageResource(R.drawable.o10);
        }










        resetchecker++;
    }

    //
    public void showDialog(String whoWon, String scoreWon, String whoLose, String scoreLose) {

        final Dialog dialog = new Dialog(StoryScene.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
//      TextView playerOneScore = dialog.findViewById(R.id.player_one_score);
//        TextView playerTwoScore = dialog.findViewById(R.id.player_two_score);
        TextView titleText = dialog.findViewById(R.id.title_text);
        dialog.setCancelable(false);
        dialog.show();

        titleText.setText(whoWon);
//        playerOneScore.setText(whoWon+" Score -> "+scoreWon);
//        playerTwoScore.setText(whoLose+"Score -> "+scoreLose);

        Button resetButton = dialog.findViewById(R.id.reset_button);
        Button playAgainButton = dialog.findViewById(R.id.play_again_button);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                doreset();
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                playmore();
            }
        });
    }

    public void winchecker() {
        ctrflag++;
        sum[0] = tracker[0][0] + tracker[0][1] + tracker[0][2];
        sum[1] = tracker[1][0] + tracker[1][1] + tracker[1][2];
        sum[2] = tracker[2][0] + tracker[2][1] + tracker[2][2];
        sum[3] = tracker[0][0] + tracker[1][0] + tracker[2][0];
        sum[4] = tracker[0][1] + tracker[1][1] + tracker[2][1];
        sum[5] = tracker[0][2] + tracker[1][2] + tracker[2][2];
        sum[6] = tracker[0][0] + tracker[1][1] + tracker[2][2];
        sum[7] = tracker[0][2] + tracker[1][1] + tracker[2][0];
        final boolean[] wins = new boolean[1];
        final boolean[] returns = new boolean[1];

        currentgamedonechecker++;
        resetchecker++;




        for (int i = 0; i < 8; i++)
            if (sum[i] == 3 || sum[i] == 30) {

                if(Vibration) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
                    v.vibrate(400);
                }
                win++;
                if ((sum[i] == 3) && (ax == 1)) {
                    score1++;
                    final TextView q1 = (TextView) findViewById(R.id.p1score);
                    wins[0] = true;
                    returns[0] = false;
                    if (wins[0]){
                        q1.setTextColor(getResources().getColor(R.color.cotextred));
                        wins[0] = false;
                        returns[0] = true;
                    }

                    final boolean finalReturns = returns[0];
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (finalReturns){
                                q1.setTextColor(getResources().getColor(R.color.cotext));
                                wins[0] = false;
                                returns[0] = true;
                            }
                        }
                    }, 300);


                    q1.setText("" + score1);
                    // showDialog("" + player1 + " won!", "" + score1, "" + player2, "" + score2);

                    // player jeet gya next level lao
                    //Toast.makeText(this,player1 + "You won!", Toast.LENGTH_SHORT).show();


                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            playmore();
                            win();
                        }
                    }, 500);

                }
                if ((sum[i] == 3) && (zero == 1)) {
                    score2++;
                    final TextView q1 = (TextView) findViewById(R.id.p2score);
                    wins[0] = true;
                    returns[0] = false;
                    if (wins[0]){
                        q1.setTextColor(getResources().getColor(R.color.cotextred));
                        wins[0] = false;
                        returns[0] = true;
                    }

                    final boolean finalReturns = returns[0];
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (finalReturns){
                                q1.setTextColor(getResources().getColor(R.color.cotext));
                                wins[0] = false;
                                returns[0] = true;
                            }
                        }
                    }, 500);



                    q1.setText("" + score2);
                    // showDialog("" + player2 + " won!", "" + score2, "" + player1, "" + score1);

                    // retry ya skip ya make it easy wala dialog lao
                    Toast.makeText(this,"You Lose!", Toast.LENGTH_SHORT).show();



                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            playmore();
                            lose();
                        }
                    }, 300);

                }
                if ((sum[i] == 30) && (ax == 10)) {


                    score1++;
                    final   TextView q1 = (TextView) findViewById(R.id.p1score);
                    wins[0] = true;
                    returns[0] = false;
                    if (wins[0]){
                        q1.setTextColor(getResources().getColor(R.color.cotextred));
                        wins[0] = false;
                        returns[0] = true;
                    }

                    final boolean finalReturns = returns[0];
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (finalReturns){
                                q1.setTextColor(getResources().getColor(R.color.cotext));
                                wins[0] = false;
                                returns[0] = true;
                            }
                        }
                    }, 300);


                    q1.setText("" + score1);
                    //    showDialog("" + player1 + " won!", "" + score1, "" + player2, "" + score2);
                    Toast.makeText(getApplicationContext(),"You won!", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            playmore();
                            win();
                        }
                    }, 500);

                }
                if ((sum[i] == 30) && (zero == 10)) {


                    score2++;
                    final TextView q1 = (TextView) findViewById(R.id.p2score);
                    wins[0] = true;
                    returns[0] = false;
                    if (wins[0]){
                        q1.setTextColor(getResources().getColor(R.color.cotextred));
                        wins[0] = false;
                        returns[0] = true;
                    }

                    final boolean finalReturns = returns[0];
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (finalReturns){
                                q1.setTextColor(getResources().getColor(R.color.cotext));
                                wins[0] = false;
                                returns[0] = true;
                            }
                        }
                    }, 300);


                    q1.setText("" + score2);
                    // to asi netreba
                    //Toast.makeText(this, " AI won!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            playmore();
                            lose();
                        }
                    }, 500);


                    //  showDialog("" + player2 + " won!", "" + score2, "" + player1, "" + score1);

                }

            }

        if ((ctrflag == 9) && (win == 0)) {
            //  showDialog("This is a draw !", "" + score1, "" + "AI", "" + score2);

            // draw ho gya retry ya skip wala dialog lao
            Toast.makeText(this,"DRAW!",Toast.LENGTH_SHORT).show();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playmore();
                    draw();
                }
            }, 900);
            drawchecker++;
        }


    }  //end winchecker()

    private void playmore() {


        int level2 = getIntent().getIntExtra("level",0);
        SharedPreferences mketpref = getApplicationContext().getSharedPreferences("mketpref", MODE_PRIVATE);
        SharedPreferences.Editor mketprefed = mketpref.edit();

        //   commit changes


        boolean mket= mketpref.getBoolean("mket", false);  // getting boolean
        //  playmore();
        if (mket){
            easy = true;
            medium = false;
            hard = false;
            impossible = false;
            mketprefed.putBoolean("mket", false);           // Saving boolean - true/false
            mketprefed.apply(); //

        } else {


            if (level2 < 5) {
                easy = true;
                medium = false;
                hard = false;
                impossible = false;
            }

            if (level2 > 4 && level2 < 20) {
                easy = false;
                medium = true;
                hard = false;
                impossible = false;
            }

            if (level2 > 19 && level2 < 100) {
                easy = false;
                medium = false;
                hard = true;
                impossible = false;
            }

            if (level2 > 99 && level2 < 200) {
                easy = false;
                medium = false;
                hard = false;
                impossible = true;
            }
        }
/*


        int max = 4;
        int min = 1;

        if (savedValue == 0 || savedValue == 5) {
            int random = new Random().nextInt(max - min + 1) + min;


            if (random == 1){
                easy = true;
                medium = false;
                hard = false;
                impossible = false;

            }
            if (random == 2){
                easy = false;
                medium = true;
                hard = false;
                impossible = false;

            }
            if (random == 3){
                easy = false;
                medium = false;
                hard = true;
                impossible = false;

            }
            if (random == 4){
                easy = false;
                medium = false;
                hard = false;
                impossible = true;
            }
        }
        if (savedValue == 1){

            easy = true;
            medium = false;
            hard = false;
            impossible = false;        }
        if (savedValue == 2){

            easy = false;
            medium = true;
            hard = false;
            impossible = false;        }
        if (savedValue == 3){

            easy = false;
            medium = false;
            hard = true;
            impossible = false;        }
        if (savedValue == 4){

            easy = false;
            medium = false;
            hard = false;
            impossible = true;        }



 */


        // yaha se neeche tk reset krne ka code hai
        //reset start

        if ((drawchecker > 0) || (win > 0)) {
            game++;
            //    TextView qq = (TextView) findViewById(R.id.gamenumber);
            //  qq.setText("" + game);

            for (int i = 0; i < 8; i++)
                sum[i] = 0;

            drawchecker = 0;


            ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;
            q1 = (ImageView) findViewById(R.id.tzz);
            q2 = (ImageView) findViewById(R.id.tzo);
            q3 = (ImageView) findViewById(R.id.tzt);
            q4 = (ImageView) findViewById(R.id.toz);
            q5 = (ImageView) findViewById(R.id.too);
            q6 = (ImageView) findViewById(R.id.tot);
            q7 = (ImageView) findViewById(R.id.ttz);
            q8 = (ImageView) findViewById(R.id.tto);
            q9 = (ImageView) findViewById(R.id.ttt);
            q1.setImageDrawable(null);
            q2.setImageDrawable(null);
            q3.setImageDrawable(null);
            q4.setImageDrawable(null);
            q5.setImageDrawable(null);
            q6.setImageDrawable(null);
            q7.setImageDrawable(null);
            q8.setImageDrawable(null);
            q9.setImageDrawable(null);

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    buttonpressed[i][j] = 0;

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    tracker[i][j] = 0;

//ToTo som ja dal prec

            if (!selectedsingleplayer) {
                if ((game + 1) % 2 == 0) {
                    Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + player2 + "\'s turn", Toast.LENGTH_SHORT).show();
                }}

            win = 0;
            summ = 0;
            ctrflag = 0;
            flag = (game + 1) % 2;
            currentgamedonechecker = 0;

            if (selectedsingleplayer && (game % 2 == 0)){
                cpuplay();
            }
        }
        //reset ends

    }


    public void doreset() {

        //  TextView qq = (TextView) findViewById(R.id.gamenumber);
        // qq.setText("" + 1);


        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                tracker[i][j] = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttonpressed[i][j] = 0;

        ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;

        q1 = (ImageView) findViewById(R.id.tzz);
        q2 = (ImageView) findViewById(R.id.tzo);
        q3 = (ImageView) findViewById(R.id.tzt);
        q4 = (ImageView) findViewById(R.id.toz);
        q5 = (ImageView) findViewById(R.id.too);
        q6 = (ImageView) findViewById(R.id.tot);
        q7 = (ImageView) findViewById(R.id.ttz);
        q8 = (ImageView) findViewById(R.id.tto);
        q9 = (ImageView) findViewById(R.id.ttt);
        q1.setImageDrawable(null);
        q2.setImageDrawable(null);
        q3.setImageDrawable(null);
        q4.setImageDrawable(null);
        q5.setImageDrawable(null);
        q6.setImageDrawable(null);
        q7.setImageDrawable(null);
        q8.setImageDrawable(null);
        q9.setImageDrawable(null);


        win = 0;
        drawchecker = 0;
        summ = 0;
        resetchecker = 0;
        ctrflag = 0;
        score1 = 0;
        score2 = 0;
        game = 1;
        flag = 0;
        currentgamedonechecker = 0;
        //TextView qqq = (TextView) findViewById(R.id.p1score);
        //qqq.setText("" + score1);
        //TextView qqqq = (TextView) findViewById(R.id.p2score);
        //qqqq.setText("" + score2);

        //  Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_SHORT).show();


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    private void showExitDialog() {
        final Dialog dialog = new Dialog(StoryScene.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sceneback_layout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);

        dialog.show();


        Button exit = dialog.findViewById(R.id.yes_button);
        final Button dismiss = dialog.findViewById(R.id.no_button);

        TextView backtext = dialog.findViewById(R.id.backtext);

        backtext.setText("Do you really want to go back?");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doreset();
                finish();
                Intent intent = new Intent(StoryScene.this, story.class);
                startActivity(intent);
                //ads=inters show
                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(StoryScene.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(StoryScene.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(StoryScene.this);

                }
                new admob(new ondismis() {
                    @Override
                    public void ondismis() {
                        if (adsunit.mRewardedIntAd == null) {
                            admob.loadintrev(StoryScene.this);

                        }
                        if (adsunit.mRewardedAd == null) {
                            admob.loadrew(StoryScene.this);

                        }
                        if (adsunit.mInterstitial == null) {
                            admob.loadint(StoryScene.this);

                        }
                        //   Toast.makeText(MainActivity.this, "Action called", Toast.LENGTH_SHORT).show();
                    }
                }).showIntCall(StoryScene.this, true);
                /*
                if (interstitialAd.isAdLoaded()){
                    interstitialAd.show();} else {
                    Appodeal.show(StoryScene.this, Appodeal.INTERSTITIAL);
                }

                 */
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.

                makeSceneTransitionAnimation(this, view, "transition");

        int revealX = (int) (view.getX() + view.getWidth() / 2);

        int revealY = (int) (view.getY() + view.getHeight() / 2);
        Intent intent =
                new Intent(this, SettingsActivity.class);
        intent.putExtra(SettingsActivity.
                EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(SettingsActivity.
                EXTRA_CIRCULAR_REVEAL_Y, revealY);
        ActivityCompat.
                startActivity(this, intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {

        //do u want to go back wala new dialog jisme yes pr story act m jaye
        showExitDialog();
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.exit) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("EXIT", true);
//            doreset();
//            startActivity(intent);
//        }
//
//        if (id == R.id.daynightmode) {
//
//            if (night % 2 == 0) {
//                View view = this.getWindow().getDecorView();
//                view.setBackgroundColor(Color.parseColor("#000000"));
//                item.setTitle("Day Mode");
//            } else {
//                View view = this.getWindow().getDecorView();
//                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                item.setTitle("Night Mode");
//            }
//            night++;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
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
    public void prepareAd(){
        //   mInterstitialAd = new InterstitialAd(this);
        // mInterstitialAd.setAdUnitId("ca-app-pub-4215768049368739/2163895655");
        // mInterstitialAd.loadAd(new AdRequest.Builder().build());
//ca-app-pub-3829495737796874/3177733073

    }
/*
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }





 */

    private void checkinternet() {
        ConnectivityManager connMgr = (ConnectivityManager) StoryScene.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {

        } else {

            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StoryScene.this, Nointernet.class);
            startActivity(intent);
            StoryScene.this.finish();

        }

    }

    public void fbinter(){

        //ads=inters
        /*
            interstitialAd = new com.facebook.ads.InterstitialAd(StoryScene.this, "750024865598460_751535525447394");
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad displayed callback
                    Log.e(TAG, "Interstitial ad displayed.");

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
                    Log.e(TAG, "Interstitial ad dismissed.");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
                    Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                    // Show the ad


                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                    Log.d(TAG, "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                    Log.d(TAG, "Interstitial ad impression logged!");
                }
            };
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());


         */


        //  if (interstitialAd.isAdLoaded()){
        //    interstitialAd.show();
        //}
    }




    public void revvid(){
        if (adsunit.mRewardedAd != null) {
            adsunit.mRewardedAd.show(StoryScene.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    skiplevel();
                    SharedPreferences skip = getApplicationContext().getSharedPreferences("skip", MODE_PRIVATE);

                    Boolean skipb = skip.getBoolean("skipb", false);

                    if (!skipb){
                        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(StoryScene.this);
                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                            Games.getAchievementsClient(StoryScene.this, GoogleSignIn.getLastSignedInAccount(StoryScene.this))
                                    .unlock(getString(R.string.my_achievement_id25));
                            Alerter.create(StoryScene.this)
                                    .setTitle("Achievement Unlocked!")
                                    .setText("Destroying a Tough Level")
                                    .setIcon(R.drawable.achievement)
                                    .setIconColorFilter(0) // Optional - Removes white tint
                                    .setBackgroundColorRes(R.color.colorAccent)
                                    .show();
                        }
                    }
                    SharedPreferences.Editor skiped = skip.edit();
                    skiped.putBoolean("skipb", true);        // Saving integer
                    skiped.apply();
                    // Toast.makeText(StoryScene.this, "rewarded vid reward granted", Toast.LENGTH_SHORT).show();
                    if (adsunit.mRewardedIntAd == null) {
                        admob.loadintrev(StoryScene.this);

                    }
                    if (adsunit.mRewardedAd == null) {
                        admob.loadrew(StoryScene.this);

                    }
                    if (adsunit.mInterstitial == null) {
                        admob.loadint(StoryScene.this);

                    }
                }
            });


            adsunit.mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    //if(admob.isReload){
                    adsunit.mRewardedAd = null;
                    if (adsunit.mRewardedIntAd == null) {
                        admob.loadintrev(StoryScene.this);

                    }
                    if (adsunit.mRewardedAd == null) {
                        admob.loadrew(StoryScene.this);

                    }
                    if (adsunit.mInterstitial == null) {
                        admob.loadint(StoryScene.this);

                    }
                    // }
                    //  ondismis.ondismis();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);


                    //revIntershow  try krna hai
                    //        Toast.makeText(StoryScene.this, "Video Unavailable! Please try again later.", Toast.LENGTH_SHORT).show();

                    if (adsunit.mRewardedIntAd != null) {
                        adsunit.mRewardedIntAd.show(StoryScene.this, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                {
                                    skiplevel();
                                    SharedPreferences skip = getApplicationContext().getSharedPreferences("skip", MODE_PRIVATE);

                                    Boolean skipb = skip.getBoolean("skipb", false);

                                    if (!skipb){
                                        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(StoryScene.this);
                                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                                            Games.getAchievementsClient(StoryScene.this, GoogleSignIn.getLastSignedInAccount(StoryScene.this))
                                                    .unlock(getString(R.string.my_achievement_id25));
                                            Alerter.create(StoryScene.this)
                                                    .setTitle("Achievement Unlocked!")
                                                    .setText("Destroying a Tough Level")
                                                    .setIcon(R.drawable.achievement)
                                                    .setIconColorFilter(0) // Optional - Removes white tint
                                                    .setBackgroundColorRes(R.color.colorAccent)
                                                    .show();
                                        }
                                    }
                                    SharedPreferences.Editor skiped = skip.edit();
                                    skiped.putBoolean("skipb", true);        // Saving integer
                                    skiped.apply();
                                    //    Toast.makeText(StoryScene.this, "Inyterstitial rewarded vid reward granted", Toast.LENGTH_SHORT).show();
                                    if (adsunit.mRewardedIntAd == null) {
                                        admob.loadintrev(StoryScene.this);

                                    }
                                    if (adsunit.mRewardedAd == null) {
                                        admob.loadrew(StoryScene.this);

                                    }
                                    if (adsunit.mInterstitial == null) {
                                        admob.loadint(StoryScene.this);

                                    }

                                }                                }
                        });


                        adsunit.mRewardedIntAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                // if(isReload){
                                adsunit.mRewardedIntAd = null;
                                if (adsunit.mRewardedIntAd == null) {
                                    admob.loadintrev(StoryScene.this);

                                }
                                if (adsunit.mRewardedAd == null) {
                                    admob.loadrew(StoryScene.this);

                                }
                                if (adsunit.mInterstitial == null) {
                                    admob.loadint(StoryScene.this);

                                }
                                //     admob.loadintrev(activity);

                                // }
                                //    ondismis.ondismis();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                //    Toast.makeText(StoryScene.this, "Video Unavailable! Please try again later.", Toast.LENGTH_SHORT).show();
                                if (adsunit.mRewardedIntAd == null) {
                                    admob.loadintrev(StoryScene.this);

                                }
                                if (adsunit.mRewardedAd == null) {
                                    admob.loadrew(StoryScene.this);

                                }
                                if (adsunit.mInterstitial == null) {
                                    admob.loadint(StoryScene.this);

                                }
                                // adsunit.mInterstitial.show(activity);
                            }
                        });



                    } else {
                        //Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        // ondismis.ondismis();
                        //  Toast.makeText(StoryScene.this, "Video Unavailable! Please try again later.", Toast.LENGTH_SHORT).show();
                        if (adsunit.mRewardedIntAd == null) {
                            admob.loadintrev(StoryScene.this);

                        }
                        if (adsunit.mRewardedAd == null) {
                            admob.loadrew(StoryScene.this);

                        }
                        if (adsunit.mInterstitial == null) {
                            admob.loadint(StoryScene.this);

                        }

                    }






                }
            });
        }


//ads=rewarded prize bhi hai
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(StoryScene.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(StoryScene.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(StoryScene.this);

        }
        /*
        Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
            @Override
            public void onRewardedVideoLoaded(boolean isPrecache) {
                // Called when rewarded video is loaded
            }
            @Override
            public void onRewardedVideoFailedToLoad() {

                // Called when rewarded video failed to load
            }
            @Override
            public void onRewardedVideoShown() {
                // Called when rewarded video is shown
            }
            @Override
            public void onRewardedVideoShowFailed() {
                Toast.makeText(StoryScene.this, "Video Unavailable! Please try again later.", Toast.LENGTH_SHORT).show();

                // Called when rewarded video show failed
            }
            @Override
            public void onRewardedVideoClicked() {
                // Called when rewarded video is clicked
            }
            @Override
            public void onRewardedVideoFinished(double amount, String name) {

                skiplevel();
                SharedPreferences skip = getApplicationContext().getSharedPreferences("skip", MODE_PRIVATE);

                Boolean skipb = skip.getBoolean("skipb", false);

                if (!skipb){
                    GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(StoryScene.this);
                    if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                        Games.getAchievementsClient(StoryScene.this, GoogleSignIn.getLastSignedInAccount(StoryScene.this))
                                .unlock(getString(R.string.my_achievement_id25));
                        Alerter.create(StoryScene.this)
                                .setTitle("Achievement Unlocked!")
                                .setText("Destroying a Tough Level")
                                .setIcon(R.drawable.achievement)
                                .setIconColorFilter(0) // Optional - Removes white tint
                                .setBackgroundColorRes(R.color.colorAccent)
                                .show();
                    }
                }
                SharedPreferences.Editor skiped = skip.edit();
                skiped.putBoolean("skipb", true);        // Saving integer
                skiped.apply();


            }
            @Override
            public void onRewardedVideoClosed(boolean finished) {

                // Called when rewarded video is closed
            }
            @Override
            public void onRewardedVideoExpired() {
                // Called when rewarded video is expired
            }
        });



         */
//neeche purana hai

/*

        rewardedVideoAd = new com.facebook.ads.RewardedVideoAd(this, "750024865598460_813466435920969");
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {

                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());


                        Appodeal.show(StoryScene.this, Appodeal.REWARDED_VIDEO);
            }

            @Override
            public void onAdLoaded(Ad ad) {

                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");

                rewardedVideoAd.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {

                skiplevel();
                SharedPreferences skip = getApplicationContext().getSharedPreferences("skip", MODE_PRIVATE);

                Boolean skipb = skip.getBoolean("skipb", false);

                if (!skipb){
                    GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(StoryScene.this);
                    if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                        Games.getAchievementsClient(StoryScene.this, GoogleSignIn.getLastSignedInAccount(StoryScene.this))
                                .unlock(getString(R.string.my_achievement_id25));
                        Alerter.create(StoryScene.this)
                                .setTitle("Achievement Unlocked!")
                                .setText("Destroying a Tough Level")
                                .setIcon(R.drawable.achievement)
                                .setIconColorFilter(0) // Optional - Removes white tint
                                .setBackgroundColorRes(R.color.colorAccent)
                                .show();
                    }
                }
                SharedPreferences.Editor skiped = skip.edit();
                skiped.putBoolean("skipb", true);        // Saving integer
                skiped.apply();

            }

            @Override
            public void onRewardedVideoClosed() {

                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());



 */
    }

    @Override
    protected void onPause() {
        super.onPause();

 }

    @Override
    protected void onResume() {
        super.onResume();
    }






    private void intersload() {
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(StoryScene.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(StoryScene.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(StoryScene.this);

        }
//ads=inters  load hone ka code
        /*
        AdRequest adRequest = new AdRequest.Builder().build();

        com.google.android.gms.ads.interstitial.InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


         */
    }



    private void intersshow() {

        new admob(new ondismis() {
            @Override
            public void ondismis() {
                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(StoryScene.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(StoryScene.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(StoryScene.this);

                }
            }
        }).showIntCall(StoryScene.this, true);

    }












}