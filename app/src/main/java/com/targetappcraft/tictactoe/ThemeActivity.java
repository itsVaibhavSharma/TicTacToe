package com.targetappcraft.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;
import com.tapadoo.alerter.Alerter;
import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;
import com.targetappcraft.tictactoe.ads.ondismis;

import java.util.Timer;
import java.util.TimerTask;


//import com.anjlab.android.iab.v3.BillingProcessor;
//import com.anjlab.android.iab.v3.TransactionDetails;

public class ThemeActivity extends AppCompatActivity {


  //  private com.facebook.ads.InterstitialAd interstitialAd;
  //  public final String TAG = "INTERSTITIAL AD";
    private BillingProcessor bp;
    private View decorView;

//    private BillingProcessor bp;
//    private boolean readytopurchase = false;
//    private TransactionDetails purchasetransactiondetails = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //NAVIGATION HIDE
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystembars());
            }
        });

        //banner load
        admob.setbanner(findViewById(R.id.banner_container), ThemeActivity.this);
        //buttonclicksound



        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();

        //  sfxeditor.putBoolean("sfx", true);        // Saving integer
        //  sfxeditor.apply();


        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {
        }

        RelativeLayout backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ThemeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });






        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();
        int coins = coinsandgems.getInt("coins", 0);
        int gems = coinsandgems.getInt("gems", 0);
        TextView coinstext = findViewById(R.id.coinsamounttext);
        TextView gemstext = findViewById(R.id.gemsamounttext);

        coinstext.setText(String.valueOf(coins));
        gemstext.setText(String.valueOf(gems));


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



        Dialog dialog = new Dialog(this);

        ImageView themeimg1 = findViewById(R.id.themeimg1);
        ImageView themeimg2 = findViewById(R.id.themeimg2);
        ImageView themeimg3 = findViewById(R.id.themeimg3);
        ImageView themeimg4 = findViewById(R.id.themeimg4);
        ImageView themeimg5 = findViewById(R.id.themeimg5);
        ImageView themeimg6 = findViewById(R.id.themeimg6);
        ImageView themeimg7 = findViewById(R.id.themeimg7);
        ImageView themeimg8 = findViewById(R.id.themeimg8);
        ImageView themeimg9 = findViewById(R.id.themeimg9);
ImageView themeimg10 = findViewById(R.id.themeimg10);
        ImageView themeimg11 = findViewById(R.id.themeimg11);



        TextView themecost1 = findViewById(R.id.themecost1);
        TextView themecost2 = findViewById(R.id.themecost2);
        TextView themecost3 = findViewById(R.id.themecost3);
        TextView themecost4 = findViewById(R.id.themecost4);
        TextView themecost5 = findViewById(R.id.themecost5);
        TextView themecost6 = findViewById(R.id.themecost6);
        TextView themecost7 = findViewById(R.id.themecost7);
        TextView themecost8 = findViewById(R.id.themecost8);
        TextView themecost9 = findViewById(R.id.themecost9);
        TextView themecost10 = findViewById(R.id.themecost10);
        TextView themecost11 = findViewById(R.id.themecost11);


        Button purchasebtn1 = findViewById(R.id.purchasebtn1);
        Button purchasebtn2 = findViewById(R.id.purchasebtn2);
        Button purchasebtn3 = findViewById(R.id.purchasebtn3);
        Button purchasebtn4 = findViewById(R.id.purchasebtn4);
        Button purchasebtn5 = findViewById(R.id.purchasebtn5);
        Button purchasebtn6 = findViewById(R.id.purchasebtn6);
        Button purchasebtn7 = findViewById(R.id.purchasebtn7);
        Button purchasebtn8 = findViewById(R.id.purchasebtn8);
        Button purchasebtn9 = findViewById(R.id.purchasebtn9);
   Button purchasebtn10 = findViewById(R.id.purchasebtn10);
        Button purchasebtn11 = findViewById(R.id.purchasebtn11);
        Button buyall = findViewById(R.id.buyall);




        LinearLayout themelayout1 = findViewById(R.id.themelayout1);
        LinearLayout themelayout2 = findViewById(R.id.themelayout2);
        LinearLayout themelayout3 = findViewById(R.id.themelayout3);
        LinearLayout themelayout4 = findViewById(R.id.themelayout4);
        LinearLayout themelayout5 = findViewById(R.id.themelayout5);
        LinearLayout themelayout6 = findViewById(R.id.themelayout6);
        LinearLayout themelayout7 = findViewById(R.id.themelayout7);
        LinearLayout themelayout8 = findViewById(R.id.themelayout8);
        LinearLayout themelayout9 = findViewById(R.id.themelayout9);
        LinearLayout themelayout10 = findViewById(R.id.themelayout10);

        int cost1 = 0;
        int cost2 = 5000;
        int cost3 = 10000;
        int cost4 = 25000;
        int cost5 = 50000;
        int cost6 = 100000;
        int cost7 = 150000;
        int cost8 = 200000;
        int cost9 = 250000;
        int cost10 = 300000;
        int cost11 = 350000;


        boolean themeall = themes.getBoolean("themeall", false);

        if (themeall){

            themeseditor.putBoolean("themep2", true);
            themeseditor.putBoolean("themep3", true);
            themeseditor.putBoolean("themep4", true);
            themeseditor.putBoolean("themep5", true);
            themeseditor.putBoolean("themep6", true);
            themeseditor.putBoolean("themep7", true);
            themeseditor.putBoolean("themep8", true);
            themeseditor.putBoolean("themep9", true);
            themeseditor.putBoolean("themep10", true);
            themeseditor.putBoolean("themep11", true);



            themeseditor.apply();


        }
                bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjPxLfOjUt5czuaUlq5pTE0A/+xvhxmMtLVGD0eOnYZ3QWP97dPgENCnXjMWmHQXnOQoAzHzNnow0BCyokA9yuN2FxBHsss37h80kYtt+xovRwOmBubBw1nftGyxxXwmKmmZby78u0723OBORO2OAGk0rhf4UD0qBGznARVQL1YvmvZWvFFQnX3HpmJKvf4E3bqbpseMxzpoFULymKUY8MY5DtAJLw+Nj7AIJBwt8tbO1kuo7+ooDZKeLu4guabwnE+/XReuUGmXBnPqncrncgbxMI7/LYBY9TtdKxWvGuisWlcqOCT++Vcf22uKxCsaNveoni9NgfpStiS7Se9zbnQIDAQAB", null, new BillingProcessor.IBillingHandler() {
                @Override
                public void onProductPurchased(String productId, TransactionDetails details) {
                    if (productId.contains("allthemesbuy")) {


                        Toast.makeText(ThemeActivity.this, "Purchase Successful", Toast.LENGTH_SHORT).show();
                        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(ThemeActivity.this);
                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                            Games.getAchievementsClient(ThemeActivity.this, GoogleSignIn.getLastSignedInAccount(ThemeActivity.this))
                                    .unlock(getString(R.string.my_achievement_id1));
                            Alerter.create(ThemeActivity.this)
                                    .setTitle("Achievement Unlocked!")
                                    .setText("ReDesign the Game!")
                                    .setIcon(R.drawable.achievement)
                                    .setIconColorFilter(0) // Optional - Removes white tint
                                    .setBackgroundColorRes(R.color.colorAccent)
                                    .show();
                        }


                        SharedPreferences themes = getApplicationContext().getSharedPreferences("themes", MODE_PRIVATE);
                        SharedPreferences.Editor themeseditor = themes.edit();
                        themeseditor.putBoolean("themeall", true);
                        themeseditor.apply();


                        new Timer().scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {


                                Intent intent = new Intent(ThemeActivity.this, ThemeActivity.class);
                                finish();
                                startActivity(intent);

                            }
                        }, 0, 2000);//put here time 1000 milliseconds = 1 second


                    }



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







buyall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // bp = new BillingProcessor(ThemeActivity.this,getResources().getString(R.string.play_license), ThemeActivity.this);

        //bp.initialize();

        bp.purchase(ThemeActivity.this, "allthemesbuy");


    }
});










        RelativeLayout coinsrv = findViewById(R.id.coins);
        RelativeLayout gemsrv = findViewById(R.id.gems);

        coinsrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                diaogbuycoins();

            }
        });





        gemsrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogbuygolds();


            }
        });





        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(ThemeActivity.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(ThemeActivity.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(ThemeActivity.this);

        }













        if (themep1[0]) {
            themecost1.setText("PURCHASED");
            if (themea1) {
                purchasebtn1.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn1.setTextColor(getResources().getColor(R.color.black));

                purchasebtn1.setText("APPLIED");



            } else {
                purchasebtn1.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn1.setTextColor(getResources().getColor(R.color.white));

                purchasebtn1.setText("APPLY");

            }

        } else {
            purchasebtn1.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn1.setTextColor(getResources().getColor(R.color.white));

            purchasebtn1.setText("PURCHASE");
            if (coins < cost1 - 1) {
                purchasebtn1.getBackground().setAlpha(100);
            }
        }

        if (themep2[0]) {
            themecost2.setText("PURCHASED");
            if (themea2) {
                purchasebtn2.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn2.setTextColor(getResources().getColor(R.color.black));

                purchasebtn2.setText("APPLIED");


            } else {
                purchasebtn2.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn2.setTextColor(getResources().getColor(R.color.white));

                purchasebtn2.setText("APPLY");
            }

        } else {
            purchasebtn2.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn2.setTextColor(getResources().getColor(R.color.white));

            purchasebtn2.setText("PURCHASE");
            if (coins < cost2 - 1) {
                purchasebtn2.getBackground().setAlpha(100);
            }
        }

        if (themep3[0]) {
            themecost1.setText("PURCHASED");
            if (themea3) {
                purchasebtn3.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn3.setTextColor(getResources().getColor(R.color.black));

                purchasebtn3.setText("APPLIED");


            } else {
                purchasebtn3.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn3.setTextColor(getResources().getColor(R.color.white));

                purchasebtn3.setText("APPLY");
            }

        } else {
            purchasebtn3.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn3.setTextColor(getResources().getColor(R.color.white));

            purchasebtn3.setText("PURCHASE");
            if (coins < cost3 - 1) {
                purchasebtn3.getBackground().setAlpha(100);
            }
        }


        if (themep4[0]) {
            themecost1.setText("PURCHASED");
            if (themea4) {
                purchasebtn4.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn4.setTextColor(getResources().getColor(R.color.black));

                purchasebtn4.setText("APPLIED");


            } else {
                purchasebtn4.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn4.setTextColor(getResources().getColor(R.color.white));

                purchasebtn4.setText("APPLY");
            }

        } else {
            purchasebtn4.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn4.setTextColor(getResources().getColor(R.color.white));

            purchasebtn4.setText("PURCHASE");
            if (coins < cost4 - 1) {
                purchasebtn4.getBackground().setAlpha(100);
            }
        }


        if (themep5[0]) {
            themecost5.setText("PURCHASED");
            if (themea5) {
                purchasebtn5.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn5.setTextColor(getResources().getColor(R.color.black));

                purchasebtn5.setText("APPLIED");


            } else {
                purchasebtn5.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn5.setTextColor(getResources().getColor(R.color.white));

                purchasebtn5.setText("APPLY");
            }

        } else {
            purchasebtn5.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn5.setTextColor(getResources().getColor(R.color.white));

            purchasebtn5.setText("PURCHASE");
            if (coins < cost5 - 1) {
                purchasebtn5.getBackground().setAlpha(100);
            }
        }

        if (themep6[0]) {
            themecost6.setText("PURCHASED");
            if (themea6) {
                purchasebtn6.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn6.setTextColor(getResources().getColor(R.color.black));

                purchasebtn6.setText("APPLIED");


            } else {
                purchasebtn6.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn6.setTextColor(getResources().getColor(R.color.white));

                purchasebtn6.setText("APPLY");

            }

        } else {
            purchasebtn6.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn6.setTextColor(getResources().getColor(R.color.white));

            purchasebtn6.setText("PURCHASE");
            if (coins < cost6 - 1) {
                purchasebtn6.getBackground().setAlpha(100);
            }
        }

        if (themep7[0]) {
            themecost7.setText("PURCHASED");
            if (themea7) {
                purchasebtn7.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn7.setTextColor(getResources().getColor(R.color.black));

                purchasebtn7.setText("APPLIED");


            } else {
                purchasebtn7.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn7.setTextColor(getResources().getColor(R.color.white));

                purchasebtn7.setText("APPLY");
            }

        } else {
            purchasebtn7.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn7.setTextColor(getResources().getColor(R.color.white));

            purchasebtn7.setText("PURCHASE");
            if (coins < cost7 - 1) {
                purchasebtn7.getBackground().setAlpha(100);
            }
        }

        if (themep8[0]) {
            themecost8.setText("PURCHASED");
            if (themea8) {
                purchasebtn8.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn8.setTextColor(getResources().getColor(R.color.black));

                purchasebtn8.setText("APPLIED");


            } else {
                purchasebtn8.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn8.setTextColor(getResources().getColor(R.color.white));

                purchasebtn8.setText("APPLY");
            }

        } else {
            purchasebtn8.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn8.setTextColor(getResources().getColor(R.color.white));

            purchasebtn8.setText("PURCHASE");
            if (coins < cost8 - 1) {
                purchasebtn8.getBackground().setAlpha(100);
            }
        }

        if (themep9[0]) {
            themecost9.setText("PURCHASED");
            if (themea9) {
                purchasebtn9.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn9.setTextColor(getResources().getColor(R.color.black));

                purchasebtn9.setText("APPLIED");


            } else {
                purchasebtn9.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn9.setTextColor(getResources().getColor(R.color.white));

                purchasebtn9.setText("APPLY");
            }

        } else {
            purchasebtn9.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn9.setTextColor(getResources().getColor(R.color.white));

            purchasebtn9.setText("PURCHASE");
            if (coins < cost9 - 1) {
                purchasebtn9.getBackground().setAlpha(100);
            }
        }

        if (themep10[0]) {
            themecost10.setText("PURCHASED");
            if (themea10) {
                purchasebtn10.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn10.setTextColor(getResources().getColor(R.color.black));

                purchasebtn10.setText("APPLIED");


            } else {
                purchasebtn10.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn10.setTextColor(getResources().getColor(R.color.white));

                purchasebtn10.setText("APPLY");
            }

        } else {
            purchasebtn10.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn10.setTextColor(getResources().getColor(R.color.white));

            purchasebtn10.setText("PURCHASE");
            if (coins < cost10 - 1) {
                purchasebtn10.getBackground().setAlpha(100);
            }
        }


        if (themep11[0]) {
            themecost11.setText("PURCHASED");
            if (themea11) {
                purchasebtn11.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn11.setTextColor(getResources().getColor(R.color.black));

                purchasebtn11.setText("APPLIED");


            } else {
                purchasebtn11.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn11.setTextColor(getResources().getColor(R.color.white));

                purchasebtn11.setText("APPLY");
            }

        } else {
            purchasebtn11.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn11.setTextColor(getResources().getColor(R.color.white));

            purchasebtn11.setText("PURCHASE");
            if (coins < cost11 - 1) {
                purchasebtn11.getBackground().setAlpha(100);
            }
        }


        purchasebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep1 = {themes.getBoolean("themep1", true)};
                boolean themea1 = themes.getBoolean("themea1", true);


                if (themep1[0]) {
                    if (!themea1) {

                        themeseditor.putBoolean("themea1", true);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn1.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn1.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn1.setText("APPLIED");
                        fbinter();
                buttons();
                    }
                } else {
                    if (coins > cost1 - 1) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost1));


                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep1", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost1);
                                themecost1.setText("PURCHASED");
                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));
                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));

                                purchasebtn1.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn1.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();

                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });

        purchasebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep2 = {themes.getBoolean("themep2", false)};
                boolean themea2 = themes.getBoolean("themea2", false);


                if (themep2[0]) {
                    if (!themea2) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", true);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn2.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn2.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn2.setText("APPLIED");
                buttons();
                        fbinter();
                    }
                } else {
                    if (coins > cost2 - 2) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic1));
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost2));


                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep2", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                themecost2.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost2);
                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);
                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));

                                purchasebtn2.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn2.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
     SharedPreferences acvpref = getApplicationContext().getSharedPreferences("acvpref", MODE_PRIVATE);
            SharedPreferences.Editor acved = acvpref.edit();
            boolean acvt1 = acvpref.getBoolean("acvt1", false);
            if (!acvt1) {
                if (themep1[0] & themep2[0] & themep3[0] & themep4[0] & themep5[0] & themep6[0] & themep7[0] & themep8[0] & themep9[0] & themep10[0] & themep11[0]) {

                    acved.putBoolean("acvt1", true);
                    acved.apply();
                    GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(ThemeActivity.this);
                    if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                        Games.getAchievementsClient(ThemeActivity.this, GoogleSignIn.getLastSignedInAccount(ThemeActivity.this))
                                .unlock(getString(R.string.my_achievement_id23));
                        Alerter.create(ThemeActivity.this)
                                .setTitle("Achievement Unlocked!")
                                .setText("Change Skin")
                                .setIcon(R.drawable.achievement)
                                .setIconColorFilter(0) // Optional - Removes white tint
                                .setBackgroundColorRes(R.color.colorAccent)
                                .show();
                    }
                }
            }
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });



        purchasebtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep3 = {themes.getBoolean("themep3", false)};
                boolean themea3 = themes.getBoolean("themea3", false);


                if (themep3[0]) {
                    if (!themea3) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", true);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn3.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn3.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn3.setText("APPLIED");
                        fbinter();
                buttons();
                    }
                } else {
                    if (coins > cost3 - 3) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost3));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic2));
                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep3", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost3);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));
                                themecost4.setText("PURCHASED");
                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));

                                purchasebtn3.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn3.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });

        purchasebtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep4 = {themes.getBoolean("themep4", false)};
                boolean themea4 = themes.getBoolean("themea4", false);


                if (themep4[0]) {
                    if (!themea4) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", true);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn4.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn4.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn4.setText("APPLIED");
                buttons(); fbinter();
                    }
                } else {
                    if (coins > cost4 - 4) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost4));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic3));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep4", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost4);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));
                                themecost5.setText("PURCHASED");
                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));

                                purchasebtn4.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn4.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });


        purchasebtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep5 = {themes.getBoolean("themep5", false)};
                boolean themea5 = themes.getBoolean("themea5", false);


                if (themep5[0]) {
                    if (!themea5) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", true);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn5.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn5.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn5.setText("APPLIED");
                buttons(); fbinter();
                    }
                } else {
                    if (coins > cost5 - 5) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost5));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic4));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();

                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep5", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost5);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));
                                themecost6.setText("PURCHASED");
                                purchasebtn5.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn5.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });


        purchasebtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep6 = {themes.getBoolean("themep6", false)};
                boolean themea6 = themes.getBoolean("themea6", false);


                if (themep6[0]) {
                    if (!themea6) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", true);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn6.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn6.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn6.setText("APPLIED");
                buttons();
                        fbinter();
                    }
                } else {
                    if (coins > cost6 - 6) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost6));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic5));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep6", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost6);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));
                                themecost6.setText("PURCHASED");
                                purchasebtn6.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn6.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });




        purchasebtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep7 = {themes.getBoolean("themep7", false)};
                boolean themea7 = themes.getBoolean("themea7", false);


                if (themep7[0]) {
                    if (!themea7) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", true);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn7.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn7.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn7.setText("APPLIED");
                buttons();
                        fbinter();
                    }
                } else {
                    if (coins > cost7 - 7) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost7));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic6));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep7", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost7);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));
                                themecost7.setText("PURCHASED");
                                purchasebtn7.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn7.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();

                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });


        purchasebtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep8 = {themes.getBoolean("themep8", false)};
                boolean themea8 = themes.getBoolean("themea8", false);


                if (themep8[0]) {
                    if (!themea8) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", true);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn8.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn8.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn8.setText("APPLIED");
                buttons();
                        fbinter();
                    }
                } else {
                    if (coins > cost8 - 8) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost8));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic7));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep8", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost8);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));
                                themecost8.setText("PURCHASED");
                                purchasebtn8.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn8.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });

        purchasebtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep9 = {themes.getBoolean("themep9", false)};
                boolean themea9 = themes.getBoolean("themea9", false);


                if (themep9[0]) {
                    if (!themea9) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", true);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", false);

                        themeseditor.apply();
                        purchasebtn9.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn9.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn9.setText("APPLIED");
                buttons();
                        fbinter();
                    }
                } else {
                    if (coins > cost9 - 9) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost9));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic8));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep9", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost9);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));
                                themecost9.setText("PURCHASED");
                                purchasebtn9.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn9.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });




        purchasebtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep10 = {themes.getBoolean("themep10", false)};
                boolean themea10 = themes.getBoolean("themea10", false);


                if (themep10[0]) {
                    if (!themea10) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", true);
                        themeseditor.putBoolean("themea11", false);
                        themeseditor.apply();
                        purchasebtn10.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn10.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn10.setText("APPLIED");
                buttons();
                        fbinter();
                    }
                } else {
                    if (coins > cost10 - 10) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost10));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic9));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep10", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost10);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));
                                themecost10.setText("PURCHASED");
                                purchasebtn10.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn10.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();
                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });

        purchasebtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean[] themep11 = {themes.getBoolean("themep11", false)};
                boolean themea11 = themes.getBoolean("themea11", false);


                if (themep11[0]) {
                    if (!themea11) {

                        themeseditor.putBoolean("themea1", false);
                        themeseditor.putBoolean("themea2", false);
                        themeseditor.putBoolean("themea3", false);
                        themeseditor.putBoolean("themea4", false);
                        themeseditor.putBoolean("themea5", false);
                        themeseditor.putBoolean("themea6", false);
                        themeseditor.putBoolean("themea7", false);
                        themeseditor.putBoolean("themea8", false);
                        themeseditor.putBoolean("themea9", false);
                        themeseditor.putBoolean("themea10", false);
                        themeseditor.putBoolean("themea11", true);
                        themeseditor.apply();
                        purchasebtn11.setBackground(getResources().getDrawable(R.drawable.backwhite));
                        purchasebtn11.setTextColor(getResources().getColor(R.color.black));
                        purchasebtn11.setText("APPLIED");
                        buttons();
                        fbinter();
                    }
                } else {
                    if (coins > cost11 - 11) {

                        dialog.setContentView(R.layout.themebuy_layout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                        Button btnbuy = dialog.findViewById(R.id.btnbuy);
                        TextView themecost = dialog.findViewById(R.id.themecostdialog);
                        themecost.setText(String.valueOf(cost11));
                        ImageView playimage = dialog.findViewById(R.id.playimage);
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.tic10));

                        imageViewclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                buttons();
                            }
                        });

                        btnbuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                themeseditor.putBoolean("themep11", true);
                                themeseditor.apply();
                                btnbuy.setText("PURCHASED");
                                editorcoinsandgems.putInt("coins", coins - cost11);

                                editorcoinsandgems.apply();
                                int coins = coinsandgems.getInt("coins", 0);
                                coinstext.setText(String.valueOf(coins));

                                btnbuy.setBackground(getResources().getDrawable(R.drawable.back));
                                themecost11.setText("PURCHASED");
                                purchasebtn11.setBackground(getResources().getDrawable(R.drawable.back));
                                purchasebtn11.setText("APPLY");
                                dialog.dismiss();
                                buttons();
                                fbinter();

                                SharedPreferences acvpref = getApplicationContext().getSharedPreferences("acvpref", MODE_PRIVATE);
                                SharedPreferences.Editor acved = acvpref.edit();
                                boolean acvt1 = acvpref.getBoolean("acvt1", false);
                                if (!acvt1) {
                                    if (themep1[0] & themep2[0] & themep3[0] & themep4[0] & themep5[0] & themep6[0] & themep7[0] & themep8[0] & themep9[0] & themep10[0] & themep11[0]) {
                                        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(ThemeActivity.this);
                                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                                            Games.getAchievementsClient(ThemeActivity.this, GoogleSignIn.getLastSignedInAccount(ThemeActivity.this))
                                                    .unlock(getString(R.string.my_achievement_id24));
                                            acved.putBoolean("acvt1", true);
                                            acved.apply();
                                            Alerter.create(ThemeActivity.this)
                                                    .setTitle("Achievement Unlocked!")
                                                    .setText("ReDesign the Game!")
                                                    .setIcon(R.drawable.achievement)
                                                    .setIconColorFilter(0) // Optional - Removes white tint
                                                    .setBackgroundColorRes(R.color.colorAccent)
                                                    .show();
                                        }
                                    }
                                }

                            }
                        });


                        dialog.show();
                    } else {

                    }


                }


            }
        });






    } // on create end




    private void buttons(){

        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();
        int coins = coinsandgems.getInt("coins", 0);
        int gems = coinsandgems.getInt("gems", 0);
        TextView coinstext = findViewById(R.id.coinsamounttext);
        TextView gemstext = findViewById(R.id.gemsamounttext);

        coinstext.setText(String.valueOf(coins));
        gemstext.setText(String.valueOf(gems));


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

        Dialog dialog = new Dialog(this);

        ImageView themeimg1 = findViewById(R.id.themeimg1);
        ImageView themeimg2 = findViewById(R.id.themeimg2);
        ImageView themeimg3 = findViewById(R.id.themeimg3);
        ImageView themeimg4 = findViewById(R.id.themeimg4);
        ImageView themeimg5 = findViewById(R.id.themeimg5);
        ImageView themeimg6 = findViewById(R.id.themeimg6);
        ImageView themeimg7 = findViewById(R.id.themeimg7);
        ImageView themeimg8 = findViewById(R.id.themeimg8);
        ImageView themeimg9 = findViewById(R.id.themeimg9);
ImageView themeimg10 = findViewById(R.id.themeimg10);
        ImageView themeimg11 = findViewById(R.id.themeimg11);

        TextView themecost1 = findViewById(R.id.themecost1);
        TextView themecost2 = findViewById(R.id.themecost2);
        TextView themecost3 = findViewById(R.id.themecost3);
        TextView themecost4 = findViewById(R.id.themecost4);
        TextView themecost5 = findViewById(R.id.themecost5);
        TextView themecost6 = findViewById(R.id.themecost6);
        TextView themecost7 = findViewById(R.id.themecost7);
        TextView themecost8 = findViewById(R.id.themecost8);
        TextView themecost9 = findViewById(R.id.themecost9);
        TextView themecost10 = findViewById(R.id.themecost10);
        TextView themecost11 = findViewById(R.id.themecost11);


        Button purchasebtn1 = findViewById(R.id.purchasebtn1);
        Button purchasebtn2 = findViewById(R.id.purchasebtn2);
        Button purchasebtn3 = findViewById(R.id.purchasebtn3);
        Button purchasebtn4 = findViewById(R.id.purchasebtn4);
        Button purchasebtn5 = findViewById(R.id.purchasebtn5);
        Button purchasebtn6 = findViewById(R.id.purchasebtn6);
        Button purchasebtn7 = findViewById(R.id.purchasebtn7);
        Button purchasebtn8 = findViewById(R.id.purchasebtn8);
        Button purchasebtn9 = findViewById(R.id.purchasebtn9);
   Button purchasebtn10 = findViewById(R.id.purchasebtn10);
        Button purchasebtn11 = findViewById(R.id.purchasebtn11);
        
        int cost1 = 0;
        int cost2 = 5000;
        int cost3 = 10000;
        int cost4 = 25000;
        int cost5 = 50000;
        int cost6 = 100000;
        int cost7 = 150000;
        int cost8 = 200000;
        int cost9 = 250000;
        int cost10 = 300000;
        int cost11 = 350000;

        if (themep1[0]) {
            themecost1.setText("PURCHASED");
            if (themea1) {
                purchasebtn1.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn1.setTextColor(getResources().getColor(R.color.black));

                purchasebtn1.setText("APPLIED");

            } else {
                purchasebtn1.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn1.setTextColor(getResources().getColor(R.color.white));

                purchasebtn1.setText("APPLY");
            }

        } else {
            purchasebtn1.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn1.setTextColor(getResources().getColor(R.color.white));

            purchasebtn1.setText("PURCHASE");
            if (coins < cost1 - 1) {
                purchasebtn1.getBackground().setAlpha(100);
            }
        }

        if (themep2[0]) {
            themecost2.setText("PURCHASED");
            if (themea2) {
                purchasebtn2.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn2.setTextColor(getResources().getColor(R.color.black));

                purchasebtn2.setText("APPLIED");

            } else {
                purchasebtn2.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn2.setTextColor(getResources().getColor(R.color.white));

                purchasebtn2.setText("APPLY");
            }

        } else {
            purchasebtn2.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn2.setTextColor(getResources().getColor(R.color.white));

            purchasebtn2.setText("PURCHASE");
            if (coins < cost2 - 1) {
                purchasebtn2.getBackground().setAlpha(100);
            }
        }

        if (themep3[0]) {
            themecost3.setText("PURCHASED");
            if (themea3) {
                purchasebtn3.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn3.setTextColor(getResources().getColor(R.color.black));

                purchasebtn3.setText("APPLIED");

            } else {
                purchasebtn3.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn3.setTextColor(getResources().getColor(R.color.white));

                purchasebtn3.setText("APPLY");
            }

        } else {
            purchasebtn3.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn3.setTextColor(getResources().getColor(R.color.white));

            purchasebtn3.setText("PURCHASE");
            if (coins < cost3 - 1) {
                purchasebtn3.getBackground().setAlpha(100);
            }
        }


        if (themep4[0]) {
            themecost4.setText("PURCHASED");
            if (themea4) {
                purchasebtn4.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn4.setTextColor(getResources().getColor(R.color.black));

                purchasebtn4.setText("APPLIED");

            } else {
                purchasebtn4.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn4.setTextColor(getResources().getColor(R.color.white));

                purchasebtn4.setText("APPLY");
            }

        } else {
            purchasebtn4.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn4.setTextColor(getResources().getColor(R.color.white));

            purchasebtn4.setText("PURCHASE");
            if (coins < cost4 - 1) {
                purchasebtn5.getBackground().setAlpha(100);
            }
        }


        if (themep5[0]) {
            themecost5.setText("PURCHASED");
            if (themea5) {
                purchasebtn5.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn5.setTextColor(getResources().getColor(R.color.black));

                purchasebtn5.setText("APPLIED");

            } else {
                purchasebtn5.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn5.setTextColor(getResources().getColor(R.color.white));

                purchasebtn5.setText("APPLY");
            }

        } else {
            purchasebtn5.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn5.setTextColor(getResources().getColor(R.color.white));

            purchasebtn5.setText("PURCHASE");
            if (coins < cost5 - 1) {
                purchasebtn5.getBackground().setAlpha(100);
            }
        }

        if (themep6[0]) {
            themecost6.setText("PURCHASED");
            if (themea6) {
                purchasebtn6.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn6.setTextColor(getResources().getColor(R.color.black));

                purchasebtn6.setText("APPLIED");

            } else {
                purchasebtn6.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn6.setTextColor(getResources().getColor(R.color.white));

                purchasebtn6.setText("APPLY");

            }

        } else {
            purchasebtn6.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn6.setTextColor(getResources().getColor(R.color.white));

            purchasebtn6.setText("PURCHASE");
            if (coins < cost6 - 1) {
                purchasebtn6.getBackground().setAlpha(100);
            }
        }

        if (themep7[0]) {
            themecost7.setText("PURCHASED");
            if (themea7) {
                purchasebtn7.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn7.setTextColor(getResources().getColor(R.color.black));

                purchasebtn7.setText("APPLIED");

            } else {
                purchasebtn7.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn7.setTextColor(getResources().getColor(R.color.white));

                purchasebtn7.setText("APPLY");
            }

        } else {
            purchasebtn7.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn7.setTextColor(getResources().getColor(R.color.white));

            purchasebtn7.setText("PURCHASE");
            if (coins < cost7 - 1) {
                purchasebtn7.getBackground().setAlpha(100);
            }
        }

        if (themep8[0]) {
            themecost8.setText("PURCHASED");
            if (themea8) {
                purchasebtn8.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn8.setTextColor(getResources().getColor(R.color.black));

                purchasebtn8.setText("APPLIED");

            } else {
                purchasebtn8.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn8.setTextColor(getResources().getColor(R.color.white));

                purchasebtn8.setText("APPLY");
            }

        } else {
            purchasebtn8.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn8.setTextColor(getResources().getColor(R.color.white));

            purchasebtn8.setText("PURCHASE");
            if (coins < cost8 - 1) {
                purchasebtn8.getBackground().setAlpha(100);
            }
        }

        if (themep9[0]) {
            themecost9.setText("PURCHASED");
            if (themea9) {
                purchasebtn9.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn9.setTextColor(getResources().getColor(R.color.black));

                purchasebtn9.setText("APPLIED");

            } else {
                purchasebtn9.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn9.setTextColor(getResources().getColor(R.color.white));

                purchasebtn9.setText("APPLY");
            }

        } else {
            purchasebtn9.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn9.setTextColor(getResources().getColor(R.color.white));

            purchasebtn9.setText("PURCHASE");
            if (coins < cost9 - 1) {
                purchasebtn9.getBackground().setAlpha(100);
            }
        }

        if (themep10[0]) {
            themecost10.setText("PURCHASED");
            if (themea10) {
                purchasebtn10.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn10.setTextColor(getResources().getColor(R.color.black));

                purchasebtn10.setText("APPLIED");

            } else {
                purchasebtn10.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn10.setTextColor(getResources().getColor(R.color.white));

                purchasebtn10.setText("APPLY");
            }

        } else {
            purchasebtn10.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn10.setTextColor(getResources().getColor(R.color.white));

            purchasebtn10.setText("PURCHASE");
            if (coins < cost10 - 1) {
                purchasebtn10.getBackground().setAlpha(100);
            }
        }
        if (themep11[0]) {
            themecost11.setText("PURCHASED");
            if (themea11) {
                purchasebtn11.setBackground(getResources().getDrawable(R.drawable.backwhite));
                purchasebtn11.setTextColor(getResources().getColor(R.color.black));

                purchasebtn11.setText("APPLIED");


            } else {
                purchasebtn11.setBackground(getResources().getDrawable(R.drawable.back));
                purchasebtn11.setTextColor(getResources().getColor(R.color.white));

                purchasebtn11.setText("APPLY");
            }

        } else {
            purchasebtn11.setBackground(getResources().getDrawable(R.drawable.backorange));
            purchasebtn11.setTextColor(getResources().getColor(R.color.white));

            purchasebtn11.setText("PURCHASE");
            if (coins < cost11 - 1) {
                purchasebtn11.getBackground().setAlpha(100);
            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThemeActivity.this,MainActivity.class);
        startActivity(intent);
        ThemeActivity.this.finish();
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
                bp.purchase(ThemeActivity.this, "gold4500");

            }
        });

        cbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gold30k");

            }
        });

        cbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "golds200k");

            }
        });

        cbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gold500k");

            }
        });

        cbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gold2m");

            }
        });

        cbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(ThemeActivity.this, "gold10m");


            }
        });


        gbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem100");

            }
        });

        gbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem1000");

            }
        });

        gbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem3000");

            }
        });

        gbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem12k");

            }
        });

        gbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem30k");

            }
        });

        gbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem100k");

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
                bp.purchase(ThemeActivity.this, "gold4500");

            }
        });

        cbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gold30k");

            }
        });

        cbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "golds200k");

            }
        });

        cbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gold500k");

            }
        });

        cbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gold2m");

            }
        });

        cbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gold10m");





            }
        });



        gbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem100");

            }
        });

        gbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem1000");

            }
        });

        gbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem3000");

            }
        });

        gbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem12k");

            }
        });

        gbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem30k");

            }
        });

        gbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(ThemeActivity.this, "gem100k");

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

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }






















    public void fbinter(){
        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(ThemeActivity.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(ThemeActivity.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(ThemeActivity.this);

        }
        new admob(new ondismis() {
            @Override
            public void ondismis() {
                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(ThemeActivity.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(ThemeActivity.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(ThemeActivity.this);

                }
                //   Toast.makeText(MainActivity.this, "Action called", Toast.LENGTH_SHORT).show();
            }
        }).showIntCall(ThemeActivity.this, true);
        //ads=inters
        /*
            interstitialAd = new com.facebook.ads.InterstitialAd(ThemeActivity.this, "750024865598460_751535525447394");
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
                    Appodeal.show(ThemeActivity.this, Appodeal.INTERSTITIAL);
                    Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                    // Show the ad

                    interstitialAd.show();
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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

            SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
            SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();
            int coins = coinsandgems.getInt("coins", 0);
            int gems = coinsandgems.getInt("gems", 0);
            TextView coinstext = findViewById(R.id.coinsamounttext);
            TextView gemstext = findViewById(R.id.gemsamounttext);

            coinstext.setText(String.valueOf(coins));
            gemstext.setText(String.valueOf(gems));


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

            boolean themeall = themes.getBoolean("themeall", false);

            if (themeall) {

                themeseditor.putBoolean("themep2", true);
                themeseditor.putBoolean("themep3", true);
                themeseditor.putBoolean("themep4", true);
                themeseditor.putBoolean("themep5", true);
                themeseditor.putBoolean("themep6", true);
                themeseditor.putBoolean("themep7", true);
                themeseditor.putBoolean("themep8", true);
                themeseditor.putBoolean("themep9", true);
                themeseditor.putBoolean("themep10", true);
                themeseditor.putBoolean("themep11", true);


                themeseditor.apply();


            }

            SharedPreferences acvpref = getApplicationContext().getSharedPreferences("acvpref", MODE_PRIVATE);
            SharedPreferences.Editor acved = acvpref.edit();
            boolean acvt1 = acvpref.getBoolean("acvt1", false);
            if (!acvt1) {
                if (themep1[0] & themep2[0] & themep3[0] & themep4[0] & themep5[0] & themep6[0] & themep7[0] & themep8[0] & themep9[0] & themep10[0] & themep11[0]) {
                    GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(ThemeActivity.this);
                    if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                        Games.getAchievementsClient(ThemeActivity.this, GoogleSignIn.getLastSignedInAccount(ThemeActivity.this))
                                .unlock(getString(R.string.my_achievement_id24));
                        acved.putBoolean("acvt1", true);
                        acved.apply();
                        Alerter.create(ThemeActivity.this)
                                .setTitle("Achievement Unlocked!")
                                .setText("ReDesign the Game!")
                                .setIcon(R.drawable.achievement)
                                .setIconColorFilter(0) // Optional - Removes white tint
                                .setBackgroundColorRes(R.color.colorAccent)
                                .show();
                    }
                }
            }
        }
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
}