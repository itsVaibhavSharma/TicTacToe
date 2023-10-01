package com.targetappcraft.tictactoe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import hotchemi.android.rate.AppRate;
import com.tapadoo.alerter.Alerter;
import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;
public class MainActivity extends AppCompatActivity {
    //private FirebaseAnalytics mFirebaseAnalytics;
    private View decorView;
    public boolean selectedsingleplayer = true;
    public CharSequence player1 = "1";
    public CharSequence player2 = "2";
    private BillingProcessor bp;
    private static final long START_TIME_IN_MILLIS = 600000;//86400000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private Button mButtonReset;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_LEADERBOARD_UI = 9004;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //banner load
        admob.setbanner(findViewById(R.id.banner_container), MainActivity.this);
        ScheduledExecutorService scheduledExecutorService1 =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService1.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // ads=inters
                     //   intersload();


                        if (adsunit.mRewardedIntAd == null) {
                            admob.loadintrev(MainActivity.this);

                        }
                        if (adsunit.mRewardedAd == null) {
                            admob.loadrew(MainActivity.this);

                        }
                        if (adsunit.mInterstitial == null) {
                            admob.loadint(MainActivity.this);

                        }
                    }

                });
            }
        }, 5, 5, TimeUnit.SECONDS);

        //google play games
        //signin
        mGoogleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());


        ImageButton achievement = findViewById(R.id.achievements);
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                    showAchievements();
                } else {

                    gpgsignin();
                }


            }
        });


        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);


        ImageButton leaderboard = findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                    showLeaderboard();
                } else {

                    gpgsignin();
                }

            }
        });


        SharedPreferences acvpref = getApplicationContext().getSharedPreferences("acvpref", MODE_PRIVATE);
        SharedPreferences.Editor acved = acvpref.edit();

        SharedPreferences wincount = getApplicationContext().getSharedPreferences("wincount", MODE_PRIVATE);
        SharedPreferences.Editor wincounted = wincount.edit();
        int winc = wincount.getInt("winc", 0);

        boolean acv11 = acvpref.getBoolean("acv11", false);
        boolean acv12 = acvpref.getBoolean("acv12", false);
        boolean acv13 = acvpref.getBoolean("acv13", false);
        boolean acv14 = acvpref.getBoolean("acv14", false);

        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            if (winc == 10) {
                if (!acv11) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id3));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("1 on 1 Intermediate - I")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();

                    //Toast.makeText(this, "New Achievement Unlocked - Win 10 Games ", Toast.LENGTH_SHORT).show();
                    acved.putBoolean("acv11", true);
                    acved.apply();
                }
            }
            if (winc == 100) {
                if (!acv12) {

                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id4));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("1 on 1 Intermediate - II")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    //  Toast.makeText(this, "New Achievement Unlocked - Win 100 Games ", Toast.LENGTH_SHORT).show();
                    acved.putBoolean("acv12", true);
                    acved.apply();
                }
            }
            if (winc == 500) {

                if (!acv13) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id5));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("1 on 1 Intermediate - III")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    //   Toast.makeText(this, "New Achievement Unlocked - Win 500 Games ", Toast.LENGTH_SHORT).show();
                    acved.putBoolean("acv13", true);
                    acved.apply();
                }
            }
            if (winc == 1000) {


                if (!acv14) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id6));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("1 on 1 Expert")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv14", true);
                    acved.apply();
                }
            }


            boolean acv15 = acvpref.getBoolean("acv15", false);

            SharedPreferences scenepref = getApplicationContext().getSharedPreferences("scenepref", MODE_PRIVATE);
            SharedPreferences.Editor sceneprefed = scenepref.edit();
            int scene = scenepref.getInt("scene", 0);
            if (scene == 10) {
                if (!acv15) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id7));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("AI Beater")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();

                    acved.putBoolean("acv15", true);
                    acved.apply();
                }

            }
        }
        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();


        //God Mode
        //  editorcoinsandgems.putInt("coins", 25000000);
        //  editorcoinsandgems.putInt("gems", 10);
        //  editorcoinsandgems.apply();

        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();


        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {

        }
        int coins = coinsandgems.getInt("coins", 0);
        int gems = coinsandgems.getInt("gems", 0);

        boolean acv1 = acvpref.getBoolean("acv1", false);
        boolean acv2 = acvpref.getBoolean("acv2", false);
        boolean acv3 = acvpref.getBoolean("acv3", false);
        boolean acv4 = acvpref.getBoolean("acv4", false);
        boolean acv5 = acvpref.getBoolean("acv5", false);
        boolean acv6 = acvpref.getBoolean("acv6", false);
        boolean acv7 = acvpref.getBoolean("acv7", false);
        boolean acv8 = acvpref.getBoolean("acv8", false);
        boolean acv9 = acvpref.getBoolean("acv9", false);
        boolean acv10 = acvpref.getBoolean("acv10", false);


        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {

            Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_id1), coins);


            if (coins == 5000) {
                if (!acv1) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id13));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Cash in Hands")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();

                    acved.putBoolean("acv1", true);
                    acved.apply();
                }
            }
            if (coins == 20000) {
                if (!acv2) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id14));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Pocket Money")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv2", true);
                    acved.apply();
                }
            }
            if (coins == 100000) {
                if (!acv3) {

                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id15));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Money in Wallet")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv3", true);
                    acved.apply();
                }
            }
            if (coins == 1000000) {

                if (!acv4) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id16));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Money in Bags")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv4", true);
                    acved.apply();
                }
            }
            if (coins == 2000000) {
                if (!acv5) {

                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id17));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Money in Bank")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv5", true);
                    acved.apply();
                }
            }

            if (gems == 200) {

                if (!acv6) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id18));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Gems in Hand")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv6", true);
                    acved.apply();
                }
            }
            if (gems == 500) {

                if (!acv7) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id19));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Gems in Pocket")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv7", true);
                    acved.apply();
                }
            }
            if (gems == 1000) {

                if (!acv8) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id20));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Gems in Bag")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv8", true);
                    acved.apply();
                }
            }
            if (gems == 5000) {

                if (!acv9) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id21));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Gems in Bucket")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv9", true);
                    acved.apply();
                }
            }
            if (gems == 10000) {

                if (!acv10) {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id22));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Gems in Locker")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                    acved.putBoolean("acv10", true);
                    acved.apply();
                }
            }

        }

        TextView coinstext = findViewById(R.id.coinsamounttext);
        TextView gemstext = findViewById(R.id.gemsamounttext);

        // in app purchase
        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjPxLfOjUt5czuaUlq5pTE0A/+xvhxmMtLVGD0eOnYZ3QWP97dPgENCnXjMWmHQXnOQoAzHzNnow0BCyokA9yuN2FxBHsss37h80kYtt+xovRwOmBubBw1nftGyxxXwmKmmZby78u0723OBORO2OAGk0rhf4UD0qBGznARVQL1YvmvZWvFFQnX3HpmJKvf4E3bqbpseMxzpoFULymKUY8MY5DtAJLw+Nj7AIJBwt8tbO1kuo7+ooDZKeLu4guabwnE+/XReuUGmXBnPqncrncgbxMI7/LYBY9TtdKxWvGuisWlcqOCT++Vcf22uKxCsaNveoni9NgfpStiS7Se9zbnQIDAQAB", null, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {


                if (productId.contains("gold4500")) {

                    bp.consumePurchase("gold4500");
                    editorcoinsandgems.putInt("coins", coins + 4500);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));


                }

                if (productId.contains("gold30k")) {

                    bp.consumePurchase("gold30k");

                    editorcoinsandgems.putInt("coins", coins + 30000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));


                }
                if (productId.contains("golds200k")) {

                    bp.consumePurchase("golds200k");
                    editorcoinsandgems.putInt("coins", coins + 200000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));

                }
                if (productId.contains("gold500k")) {

                    bp.consumePurchase("gold500k");

                    editorcoinsandgems.putInt("coins", coins + 500000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));

                }
                if (productId.contains("gold2m")) {

                    bp.consumePurchase("gold2m");

                    editorcoinsandgems.putInt("coins", coins + 2000000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));


                }
                if (productId.contains("gold10m")) {

                    bp.consumePurchase("gold10m");
                    editorcoinsandgems.putInt("coins", coins + 1000000);
                    editorcoinsandgems.apply();
                    coinstext.setText(String.valueOf(coins));

                }
                if (productId.contains("gem100")) {

                    bp.consumePurchase("gem100");

                    editorcoinsandgems.putInt("gems", gems + 100);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));


                }
                if (productId.contains("gem1000")) {

                    bp.consumePurchase("gem1000");
                    editorcoinsandgems.putInt("gems", gems + 1000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));

                }
                if (productId.contains("gem3000")) {

                    bp.consumePurchase("gem3000");
                    editorcoinsandgems.putInt("gems", gems + 3000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));

                }
                if (productId.contains("gem12k")) {

                    bp.consumePurchase("gem12k");
                    editorcoinsandgems.putInt("gems", gems + 12000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));


                }
                if (productId.contains("gem30k")) {

                    bp.consumePurchase("gem30k");
                    editorcoinsandgems.putInt("gems", gems + 30000);        // Saving integer
                    editorcoinsandgems.apply();
                    gemstext.setText(String.valueOf(gems));


                }
                if (productId.contains("gem100k")) {

                    bp.consumePurchase("gem100k");
                    editorcoinsandgems.putInt("gems", gems + 100000);        // Saving integer
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
        SharedPreferences playername = getApplicationContext().getSharedPreferences("playername", MODE_PRIVATE);

        String name = playername.getString("playernametext", "Guest");


        SharedPreferences winpref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        int level = winpref.getInt("win1", 0);

        SharedPreferences themes = getApplicationContext().getSharedPreferences("themes", MODE_PRIVATE);
        boolean themea3 = themes.getBoolean("themea3", false);
        boolean themea5 = themes.getBoolean("themea5", false);
        boolean themea7 = themes.getBoolean("themea7", false);
        boolean themea10 = themes.getBoolean("themea10", false);

        //first launch
        int firstlaunch = coinsandgems.getInt("firstlaunch", 0);
        if (firstlaunch == 0) {

            editorcoinsandgems.putInt("coins", 2500);
            editorcoinsandgems.putInt("gems", 90);        // Saving integer
            editorcoinsandgems.apply();
            namedialog();
        }

        editorcoinsandgems.putInt("firstlaunch", 1);        // Saving integer
        editorcoinsandgems.apply();

        coinstext.setText(String.valueOf(coins));
        gemstext.setText(String.valueOf(gems));

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

//review
        SharedPreferences ratepref = getApplicationContext().getSharedPreferences("ratepref", MODE_PRIVATE);
        SharedPreferences.Editor rateed = ratepref.edit();

        int rate = ratepref.getInt("rate", 0);

        if (rate >= 2) {
        } else {

            //rate now dialog
            AppRate.with(this)
                    .setInstallDays(0)
                    .setLaunchTimes(3)
                    .setRemindInterval(2)
                    .monitor();

            AppRate.showRateDialogIfMeetsConditions(this);


            if (themea3) {

                reviewprompt();
            }


            if (themea5) {
                reviewprompt();

            }


            if (themea7) {
                reviewprompt();

            }

            if (themea10) {
                reviewprompt();

            }

            if (level == 10) {
                reviewprompt();
                //Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                //      .unlock(getString(R.string.my_achievement_id1));

            }

            if (level == 20) {
                reviewprompt();

            }
            if (level == 50) {
                reviewprompt();


            }
            if (level == 100) {
                reviewprompt();

            }
            if (level == 120) {
                reviewprompt();

            }
            if (level == 150) {
                reviewprompt();

            }
            if (level == 200) {
                reviewprompt();

            }


        }

        Button Ai = findViewById(R.id.bt1);
        Ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, NameActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button ds = findViewById(R.id.bt2);
        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, TwoNameActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        Button bt3 = findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, story.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        Button mainbt1 = findViewById(R.id.mainbt1);

        Button mainbt2 = findViewById(R.id.mainbt2);

        Button mainbt3 = findViewById(R.id.mainbt3);

        Button mainbt4 = findViewById(R.id.mainbt4);

        Button mainbt5 = findViewById(R.id.mainbt5);

        player1 = name;

        mainbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //soundPool.pause(sound3StreamId);


                Intent intent = new Intent(MainActivity.this, oonocoins.class);
                startActivity(intent);
                // overridePendingTransition(0, 0);


            }
        });
        mainbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, story.class);
                startActivity(intent);


                //  overridePendingTransition(0, 0);

            }
        });
        mainbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
                CharSequence[] players = {player1, player2};
                intent.putExtra("playersnames", players);
                intent.putExtra("selectedsingleplayer", selectedsingleplayer);

                startActivity(intent);
                //overridePendingTransition(0, 0);

            }
        });
        mainbt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, TwoNameActivity.class);
                startActivity(intent);
                // overridePendingTransition(0, 0);
            }
        });
        mainbt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ThemeActivity.class);
                startActivity(intent);
                //  overridePendingTransition(0, 0);

            }
        });

        TextView playernametext = findViewById(R.id.playername);
        TextView leveltextn = findViewById(R.id.levelnumber);
        playernametext.setText(name);
        leveltextn.setText("Level " + String.valueOf(level));
        playernametext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namedialog();
            }
        });
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
        RelativeLayout vidimg = findViewById(R.id.adsvid);
        SharedPreferences vidad = getApplicationContext().getSharedPreferences("vidad", MODE_PRIVATE);
        int vid = vidad.getInt("vid", 0);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String todayString = year + "" + month + "" + day;

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PREF", MODE_PRIVATE);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            if (vid == 7) {
                Boolean vid7b = sfx.getBoolean("vid7", false);

                if (!vid7b) {

                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.my_achievement_id27));
                    Alerter.create(MainActivity.this)
                            .setTitle("Achievement Unlocked!")
                            .setText("Coins Bounty")
                            .setIcon(R.drawable.achievement)
                            .setIconColorFilter(0) // Optional - Removes white tint
                            .setBackgroundColorRes(R.color.colorAccent)
                            .show();
                }
                SharedPreferences vid7 = getApplicationContext().getSharedPreferences("vid7", MODE_PRIVATE);
                SharedPreferences.Editor vid7ed = vid7.edit();
                vid7ed.putBoolean("vid7", true);        // Saving integer
                vid7ed.apply();


            }

        }

        vidimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vid = vidad.getInt("vid", 0);
                if (vid == 8) {


                    Toast.makeText(MainActivity.this, "You have watched 7 videos. Please Come Back Tomorrow!", Toast.LENGTH_SHORT).show();
                    vidimg.setVisibility(View.GONE);

                } else {


                }

                Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.adslayout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCancelable(false);
                ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                Button updatebtn = dialog.findViewById(R.id.btnskip);


                dialog.show();
                imageViewclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        revvidshow();
                        dialog.dismiss();

                    }
                });


            }


        });

    } // oncreate ends here

    private void startTimer() {
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();


            }

            @Override
            public void onFinish() {
                mTextViewCountDown = findViewById(R.id.text_view_countdown);
                mButtonStartPause = findViewById(R.id.button_start_pause);
                mButtonReset = findViewById(R.id.button_reset);

                TextView adsLeft = findViewById(R.id.adsleft);
                RelativeLayout vidimg = findViewById(R.id.adsvid);

                mTimerRunning = false;
                updateButtons();
            }
        }.start();
        mTimerRunning = true;
        updateButtons();
    }

    private void updateCountDownText() {
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        if (mTimerRunning) {
            //mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mButtonStartPause.setText("Start");
            if (mTimeLeftInMillis < 1000) {
                // mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                // mButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
                //  mButtonReset.setVisibility(View.VISIBLE);
            } else {
                // mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStop() {
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateButtons();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }
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


                bp.purchase(MainActivity.this, "gold4500");

            }
        });

        cbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(MainActivity.this, "gold30k");

            }
        });

        cbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(MainActivity.this, "golds200k");

            }
        });

        cbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(MainActivity.this, "gold500k");

            }
        });

        cbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(MainActivity.this, "gold2m");

            }
        });

        cbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // bp.consumePurchase("gold4500");
                //bp.consumePurchase("gold30k");
                //bp.consumePurchase("gem100");
                //bp.consumePurchase("gem1000");
                //bp.consumePurchase("allthemesbuy");
                //bp.consumePurchase("testproduct");
                bp.purchase(MainActivity.this, "gold10m");


            }
        });


        gbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(MainActivity.this, "gem100");

            }
        });

        gbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(MainActivity.this, "gem1000");

            }
        });

        gbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bp.purchase(MainActivity.this, "gem3000");

            }
        });

        gbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem12k");

            }
        });

        gbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem30k");

            }
        });

        gbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem100k");

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

                bp.purchase(MainActivity.this, "gold4500");

            }
        });

        cbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gold30k");

            }
        });

        cbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "golds200k");

            }
        });

        cbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gold500k");

            }
        });

        cbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gold2m");

            }
        });

        cbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gold10m");


            }
        });


        gbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem100");

            }
        });

        gbuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem1000");

            }
        });

        gbuy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem3000");

            }
        });

        gbuy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem12k");

            }
        });

        gbuy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem30k");

            }
        });

        gbuy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(MainActivity.this, "gem100k");

            }
        });


        dialog.show();
    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data) {


            if (!bp.handleActivityResult(requestCode, resultCode, data)) {
                super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result.isSuccess()) {
                        // The signed in account is stored in the result.
                        GoogleSignInAccount signedInAccount = result.getSignInAccount();
                        Toast.makeText(this, "You are signed-in with Google. Your achievements and scores will be saved automatically.", Toast.LENGTH_SHORT).show();
                    } else {

                        SharedPreferences gpg = getApplicationContext().getSharedPreferences("gpg", MODE_PRIVATE);
                        SharedPreferences.Editor gpged = gpg.edit();

                        gpged.putBoolean("gpgb", false);        // Saving integer
                        gpged.apply();

                        String message = result.getStatus().getStatusMessage();
                        if (message == null || message.isEmpty()) {
                            message = "There was an issue with sign in. Please try again later";
                        }
                        new AlertDialog.Builder(this).setMessage(message)
                                .setNeutralButton(android.R.string.ok, null).show();
                    }
                }

                return;


            }
        }


            @Override
            public void onDestroy () {
                if (bp != null) {
                    bp.release();
                }

                super.onDestroy();


            }

            public void presentActivity (View view){
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
            public void onBackPressed () {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }

            //NAVIGATION HIDE
            @Override
            public void onWindowFocusChanged ( boolean hasFocus){
                super.onWindowFocusChanged(hasFocus);
                SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
                SharedPreferences.Editor sfxeditor = sfx.edit();

                //  sfxeditor.putBoolean("sfx", true);        // Saving integer
                //  sfxeditor.apply();


                Boolean sfxb = sfx.getBoolean("sfx", true);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String todayString = year + "" + month + "" + day;

                SharedPreferences preferences = getApplicationContext().getSharedPreferences("PREF", MODE_PRIVATE);
                SharedPreferences.Editor prefereditor = preferences.edit();

                SharedPreferences vidad = getApplicationContext().getSharedPreferences("vidad", MODE_PRIVATE);
                SharedPreferences.Editor vidads = vidad.edit();
                int vid = vidad.getInt("vid", 0);

                TextView adsLeft = findViewById(R.id.adsleft);
                RelativeLayout vidimg = findViewById(R.id.adsvid);
                if (vid == 0) {
                    adsLeft.setText("7");
                    vidimg.setVisibility(View.VISIBLE);

                }
                if (vid == 1) {
                    adsLeft.setText("6");
                    vidimg.setVisibility(View.VISIBLE);

                }
                if (vid == 2) {
                    adsLeft.setText("5");
                    vidimg.setVisibility(View.VISIBLE);

                }
                if (vid == 3) {
                    adsLeft.setText("4");
                    vidimg.setVisibility(View.VISIBLE);

                }
                if (vid == 4) {
                    adsLeft.setText("3");
                    vidimg.setVisibility(View.VISIBLE);

                }
                if (vid == 5) {
                    adsLeft.setText("2");
                    vidimg.setVisibility(View.VISIBLE);

                }
                if (vid == 6) {
                    adsLeft.setText("1");
                    vidimg.setVisibility(View.VISIBLE);

                }


                boolean currentDay = preferences.getBoolean(todayString, false);

                if (!currentDay) {

                    //Toast.makeText(this, "do stuff", Toast.LENGTH_SHORT).show();
                    if (vid == 7) {
                        vidimg.setVisibility(View.GONE);

                    }
                } else {

                    vidimg.setVisibility(View.VISIBLE);
                    vidads.putInt("vid", 0);
                    vidads.apply();

                }


                SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);


                decorView.setSystemUiVisibility(hideSystembars());
                int coins = coinsandgems.getInt("coins", 0);
                int gems = coinsandgems.getInt("gems", 0);


                TextView coinstext = findViewById(R.id.coinsamounttext);
                TextView gemstext = findViewById(R.id.gemsamounttext);

                coinstext.setText(String.valueOf(coins));
                gemstext.setText(String.valueOf(gems));


            }
            private int hideSystembars () {
                return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
//NAVIGATION END


            private void namedialog () {
                Dialog dialog = new Dialog(this);

                dialog.setContentView(R.layout.namedialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCancelable(false);
                ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                EditText edittext = dialog.findViewById(R.id.editText);
                Button updatebtn = dialog.findViewById(R.id.changebtn);


                dialog.show();
                imageViewclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences playername = getApplicationContext().getSharedPreferences("playername", MODE_PRIVATE);
                        SharedPreferences.Editor nameeditor = playername.edit();

                        if (edittext.length() < 1) {
                            nameeditor.putString("playernametext", "Guest");        // Saving integer
                            nameeditor.apply();
                        } else {
                            String value = edittext.getText().toString();

                            nameeditor.putString("playernametext", value);        // Saving integer
                            nameeditor.apply();
                        }

                        String name = playername.getString("playernametext", "Guest");


                        TextView playernametext = findViewById(R.id.playername);

                        playernametext.setText(name);

                        dialog.dismiss();


                    }
                });


            }
            private void reviewprompt () {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this,
                        R.style.AppTheme));

                alertDialogBuilder.setTitle("Rate this Game");
                alertDialogBuilder.setMessage("If you like this game, please rate it on play store. It encourage us to add more exciting features into our games.");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferences ratepref = getApplicationContext().getSharedPreferences("ratepref", MODE_PRIVATE);
                        SharedPreferences.Editor rateed = ratepref.edit();

                        int rate = ratepref.getInt("rate", 0);

                        rateed.putInt("rate", rate + 1);        // Saving integer
                        rateed.apply();

                        MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + MainActivity.this.getPackageName())));
                        dialog.cancel();
                    }
                });
                alertDialogBuilder.show();

            }

            @Override
            public void finish () {
                super.finish();
            }

            @Override
            protected void onPause () {
                super.onPause();


            }

            @Override
            protected void onResume () {
                super.onResume();


                SharedPreferences gpg = getApplicationContext().getSharedPreferences("gpg", MODE_PRIVATE);
                SharedPreferences.Editor gpged = gpg.edit();

                Boolean gpgb = gpg.getBoolean("gpgb", true);
                if (gpgb) {
                    signInSilently();
                } else {

                }
            }
                private void signInSilently () {
                    GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                    if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                        // Already signed in.
                        //Toast.makeText(this, "already signed in", Toast.LENGTH_SHORT).show();
                        // The signed in account is stored in the 'account' variable.
                        GoogleSignInAccount signedInAccount = account;
                        Games.getGamesClient(this, signedInAccount).setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

                    } else {
                        //  Toast.makeText(this, "not signed in before", Toast.LENGTH_SHORT).show();
                        // Haven't been signed-in before. Try the silent sign-in first.
                        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
                        signInClient
                                .silentSignIn()
                                .addOnCompleteListener(
                                        this,
                                        new OnCompleteListener<GoogleSignInAccount>() {
                                            @Override
                                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<GoogleSignInAccount> task) {

                                                if (task.isSuccessful()) {
                                                    // The signed in account is stored in the task's result.
                                                    GoogleSignInAccount signedInAccount = task.getResult();
                                                    Toast.makeText(MainActivity.this, "You are signed-in with Google. Your achievements and scores will be saved automatically.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    startSignInIntent();

                                                    // Player will need to sign-in explicitly using via UI.
                                                    // See [sign-in best practices](http://developers.google.com/games/services/checklist) for guidance on how and when to implement Interactive Sign-in,
                                                    // and [Performing Interactive Sign-in](http://developers.google.com/games/services/android/signin#performing_interactive_sign-in) for details on how to implement
                                                    // Interactive Sign-in.
                                                }

                                            }


                                        });
                    }

                }

                private void startSignInIntent () {
                    GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                            GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
                    Intent intent = signInClient.getSignInIntent();
                    startActivityForResult(intent, RC_SIGN_IN);
                }

                private void signOut () {
                    GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                            GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
                    signInClient.signOut().addOnCompleteListener(this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {


                                }

                            });
                }


                private void showAchievements () {
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .getAchievementsIntent()
                            .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                @Override
                                public void onSuccess(Intent intent) {
                                    startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                                }
                            });
                }

                //private static final int RC_LEADERBOARD_UI = 9004;

                private void showLeaderboard () {
                    Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .getLeaderboardIntent(getString(R.string.leaderboard_id1))
                            .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                @Override
                                public void onSuccess(Intent intent) {
                                    startActivityForResult(intent, RC_LEADERBOARD_UI);
                                }
                            });
                }

                private void gpgsignin () {
                    Dialog dialog = new Dialog(MainActivity.this);

                    dialog.setContentView(R.layout.gpgsignin);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.setCancelable(true);
                    ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                    Button signinbtn = dialog.findViewById(R.id.signinbtn);


                    dialog.show();
                    imageViewclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });

                    signinbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startSignInIntent();
                            dialog.dismiss();

                        }

                    });


                }

    private  void revvidshow() {


        TextView adsLeft = findViewById(R.id.adsleft);
        RelativeLayout vidimg = findViewById(R.id.adsvid);


        SharedPreferences vidad = getApplicationContext().getSharedPreferences("vidad", MODE_PRIVATE);
        SharedPreferences.Editor vidads = vidad.edit();

        int vid = vidad.getInt("vid", 0);
        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();
        TextView coinstext = findViewById(R.id.coinsamounttext);

        if (adsunit.mRewardedIntAd == null) {
            admob.loadintrev(MainActivity.this);

        }
        if (adsunit.mRewardedAd == null) {
            admob.loadrew(MainActivity.this);

        }
        if (adsunit.mInterstitial == null) {
            admob.loadint(MainActivity.this);

        }
        // ads=rewarded (rew show hone ka code) reward bhi hai
        if (adsunit.mRewardedAd != null) {
            adsunit.mRewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    //  ondismis.ondismis();
                    //rew
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();


                    int coins = coinsandgems.getInt("coins", 0);

                    editorcoinsandgems.putInt("coins", coins + 500);
                    editorcoinsandgems.apply();
                    coins = coinsandgems.getInt("coins", 0);

                    coinstext.setText(String.valueOf(coins));

                    int vid = vidad.getInt("vid", 0);

                    vidads.putInt("vid", vid + 1);
                    vidads.apply();


                    if (vid == 7) {

                        vidimg.setVisibility(View.GONE);
                        //adsLeft.setText("0");

                    }
                    if (adsunit.mRewardedIntAd == null) {
                        admob.loadintrev(MainActivity.this);

                    }
                    if (adsunit.mRewardedAd == null) {
                        admob.loadrew(MainActivity.this);

                    }
                    if (adsunit.mInterstitial == null) {
                        admob.loadint(MainActivity.this);

                    }
                }
            });


            adsunit.mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    //if(admob.isReload){
                    adsunit.mRewardedAd = null;
                    //  admob.loadintrev(activity);

                    if (adsunit.mRewardedIntAd == null) {
                        admob.loadintrev(MainActivity.this);

                    }
                    if (adsunit.mRewardedAd == null) {
                        admob.loadrew(MainActivity.this);

                    }
                    if (adsunit.mInterstitial == null) {
                        admob.loadint(MainActivity.this);

                    }
                    //  ondismis.ondismis();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);

                    if (adsunit.mRewardedIntAd != null) {
                        adsunit.mRewardedIntAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                int rewardAmount = rewardItem.getAmount();
                                String rewardType = rewardItem.getType();


                                int coins = coinsandgems.getInt("coins", 0);

                                editorcoinsandgems.putInt("coins", coins + 500);
                                editorcoinsandgems.apply();
                                coins = coinsandgems.getInt("coins", 0);

                                coinstext.setText(String.valueOf(coins));

                                int vid = vidad.getInt("vid", 0);

                                vidads.putInt("vid", vid + 1);
                                vidads.apply();


                                if (vid == 7) {

                                    vidimg.setVisibility(View.GONE);
                                    //adsLeft.setText("0");

                                }
                                if (adsunit.mRewardedIntAd == null) {
                                    admob.loadintrev(MainActivity.this);

                                }
                                if (adsunit.mRewardedAd == null) {
                                    admob.loadrew(MainActivity.this);

                                }
                                if (adsunit.mInterstitial == null) {
                                    admob.loadint(MainActivity.this);

                                }



                            }
                        });


                        adsunit.mRewardedIntAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                // if(isReload){
                                adsunit.mRewardedIntAd = null;
                                if (adsunit.mRewardedIntAd == null) {
                                    admob.loadintrev(MainActivity.this);

                                }
                                if (adsunit.mRewardedAd == null) {
                                    admob.loadrew(MainActivity.this);

                                }
                                if (adsunit.mInterstitial == null) {
                                    admob.loadint(MainActivity.this);

                                }
                                //     admob.loadintrev(activity);

                                // }
                                //    ondismis.ondismis();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                Toast.makeText(MainActivity.this, "Video Unavailable! Please try again later.", Toast.LENGTH_SHORT).show();
                                if (adsunit.mRewardedIntAd == null) {
                                    admob.loadintrev(MainActivity.this);

                                }
                                if (adsunit.mRewardedAd == null) {
                                    admob.loadrew(MainActivity.this);

                                }
                                if (adsunit.mInterstitial == null) {
                                    admob.loadint(MainActivity.this);

                                }
                                // adsunit.mInterstitial.show(activity);
                            }
                        });



                    }


                    //failed to show rew ad
                    if (adsunit.mRewardedIntAd == null) {
                        admob.loadintrev(MainActivity.this);

                    }
                    if (adsunit.mRewardedAd == null) {
                        admob.loadrew(MainActivity.this);

                    }
                    if (adsunit.mInterstitial == null) {
                        admob.loadint(MainActivity.this);

                    }                            }
            });
        }

    }

















}
