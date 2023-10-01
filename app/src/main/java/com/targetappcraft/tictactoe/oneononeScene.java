package com.targetappcraft.tictactoe;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import android.os.CountDownTimer;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;
import com.tapadoo.alerter.Alerter;
import com.targetappcraft.tictactoe.ads.admob;
import com.targetappcraft.tictactoe.ads.adsunit;
import com.targetappcraft.tictactoe.ads.ondismis;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class oneononeScene extends AppCompatActivity {

    private boolean shouldBlockTouches = false;
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
    //  private RewardedVideoAd mRewardedVideoAd;
    private static final String PREF_VIBRATION = "TicVib";
    Dialog dialog;
    private BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneonone_scene);


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
        admob.setbanner(findViewById(R.id.banner_container), oneononeScene.this);
        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
        SharedPreferences.Editor sfxeditor = sfx.edit();

        Boolean sfxb = sfx.getBoolean("sfx", true);
        if (sfxb) {

        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();


        int gold = getIntent().getIntExtra("gold", 0);

        int loss = getIntent().getIntExtra("loss", 0);
        SharedPreferences losses = getApplicationContext().getSharedPreferences("loss", MODE_PRIVATE);
        SharedPreferences.Editor losseseditor = losses.edit();

        if (loss > 3 && gold < 5000) {
            easy = true;
            medium = false;
            hard = false;
            impossible = false;
            losseseditor.putInt("loss", 0);
            losseseditor.apply();
            editor.putInt("key", 1);
            editor.apply();

        } else if (loss > 5 && gold > 5000 && gold < 20000) {
            easy = false;
            medium = true;
            hard = false;
            impossible = false;
            losseseditor.putInt("loss", 0);
            losseseditor.apply();
            editor.putInt("key", 2);
            editor.apply();

        } else {
            if (gold < 5000) {
                easy = true;
                medium = false;
                hard = false;
                impossible = false;
                editor.putInt("key", 1);
                editor.apply();
            }
            if (gold == 5000) {
                easy = false;
                medium = true;
                hard = false;
                impossible = false;
                editor.putInt("key", 2);
                editor.apply();
            }

            if (gold == 10000) {
                easy = false;
                medium = false;
                hard = true;
                impossible = false;
                editor.putInt("key", 3);
                editor.apply();
            }

            if (gold > 10000) {
                easy = false;
                medium = false;
                hard = false;
                impossible = true;
                editor.putInt("key", 4);
                editor.apply();
            }
        }

        ScheduledExecutorService scheduledExecutorService1 =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService1.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // ads=inters


                        if (adsunit.mRewardedIntAd == null) {
                            admob.loadintrev(oneononeScene.this);

                        }
                        if (adsunit.mRewardedAd == null) {
                            admob.loadrew(oneononeScene.this);

                        }
                        if (adsunit.mInterstitial == null) {
                            admob.loadint(oneononeScene.this);

                        }
                    }

                });
            }
        }, 5, 5, TimeUnit.SECONDS);

        ScheduledExecutorService scheduledExecutorService2 =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService2.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //ads=inters
                        intersload();
                    }

                });
            }
        }, 5, 5, TimeUnit.SECONDS);

        dialog = new Dialog(this);


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

        SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
        SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();

        int coins = coinsandgems.getInt("coins", 0);
        int gems = coinsandgems.getInt("gems", 0);


        TextView coinstext = findViewById(R.id.coinsamounttext);
        TextView gemstext = findViewById(R.id.gemsamounttext);

        coinstext.setText(String.valueOf(coins));
        gemstext.setText(String.valueOf(gems));


        TextView timer = findViewById(R.id.timer);
        TextView starttimer = findViewById(R.id.starttimer);
        TextView wintext = findViewById(R.id.wintext);
        TextView losetext = findViewById(R.id.losetext);
        TextView drawtext = findViewById(R.id.drawtext);
        ConstraintLayout toastlayout = findViewById(R.id.toastlayout);
        ImageView crown = findViewById(R.id.crown);


        wintext.setText("Win = " + "+ " + gold + " Golds");
        losetext.setText("Lose = " + "- " + gold + " Golds");
        drawtext.setText("Draw = " + "- " + gold / 2 + " Golds");


        SharedPreferences crownpref = getApplicationContext().getSharedPreferences("crownamnt", MODE_PRIVATE);
        SharedPreferences.Editor crowneditor = crownpref.edit();
        Boolean timeupb = crownpref.getBoolean("timeupb", true);
        crowneditor.putBoolean("timeupb", true);
        crowneditor.apply();


        starttimer.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(31000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText(" " + millisUntilFinished / 1000 + " SECONDS");
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        // lose dikhana h...
                        // time up
                        SharedPreferences crownpref = getApplicationContext().getSharedPreferences("crownamnt", MODE_PRIVATE);
                        SharedPreferences.Editor crowneditor = crownpref.edit();
                        Boolean timeupb = crownpref.getBoolean("timeupb", true);

                        if (timeupb) {
                            timer.setText("TIME UP!");

                            timeupdialog();

                            intersload();
                        }


                    }

                }.start();
            }
        }, 5000);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                toastlayout.setVisibility(View.GONE);
                starttimer.setVisibility(View.GONE);
            }
        }, 4000);


        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                starttimer.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                starttimer.setTextSize(40);
                starttimer.setText("START!");
                Random r = new Random();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                int i1 = r.nextInt(3 - 1) + 1;
                if (i1 == 1) {


                } else {
                    cputest();
                }
            }

        }.start();
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                toastlayout.setVisibility(View.GONE);
            }
        }, 6500);


        CardView scene = findViewById(R.id.scene);


        // SharedPreferences crownpref = getApplicationContext().getSharedPreferences("crownamnt", MODE_PRIVATE);
        //SharedPreferences.Editor crowneditor = crownpref.edit();
        crowneditor.putBoolean("crownpref", false);
        crowneditor.apply();

        crown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean crownamnt = crownpref.getBoolean("crownamnt", false);  // getting boolean


                if (crownamnt == true) {
                    toastlayout.setVisibility(View.GONE);
                    crowneditor.putBoolean("crownamnt", false);
                    crowneditor.apply();

                } else {
                    toastlayout.setVisibility(View.VISIBLE);
                    crowneditor.putBoolean("crownamnt", true);
                    crowneditor.apply();
                }

            }
        });


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
        RelativeLayout gemsrv = findViewById(R.id.gems);

        gemsrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogbuygolds();


            }
        });


        intersload();

        ImageView exitbtn = findViewById(R.id.exitbtn);
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(oneononeScene.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.sceneback_layout);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);

                dialog.show();

                Button exit = dialog.findViewById(R.id.yes_button);
                final Button dismiss = dialog.findViewById(R.id.no_button);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doreset();
                        finish();
                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        Button winthis = findViewById(R.id.winthis);
        ImageView gemimg = findViewById(R.id.gemimg);
        TextView gemamnt = findViewById(R.id.gemamnt);
        if (gold == 500) {
            if (gems > 50 - 1) {
                gemamnt.setText("50");

            } else {
                winthis.getBackground().setAlpha(100);
                gemimg.setImageAlpha(100);
                //  gemamnt.setAlpha(50f);

            }
        }


        if (gold == 2000) {
            if (gems > 200 - 1) {
                gemamnt.setText("200");

            } else {
                winthis.getBackground().setAlpha(100);
                gemimg.setImageAlpha(100);
            }
        }

        if (gold == 5000) {
            if (gems > 500 - 1) {
                gemamnt.setText("500");


            } else {
                winthis.getBackground().setAlpha(100);
                gemimg.setImageAlpha(100);
            }
        }
        if (gold == 10000) {
            if (gems > 1000 - 1) {
                gemamnt.setText("1000");


            } else {
                winthis.getBackground().setAlpha(100);
                gemimg.setImageAlpha(100);
            }
        }
        if (gold == 25000) {
            if (gems > 2500 - 1) {
                gemamnt.setText("2500");


            } else {
                winthis.getBackground().setAlpha(100);
                gemimg.setImageAlpha(100);
            }
        }

        if (gold == 50000) {
            if (gems > 5000 - 1) {
                gemamnt.setText("5000");


            } else {
                winthis.getBackground().setAlpha(100);
                gemimg.setImageAlpha(100);
            }
        }


        winthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //makeiteasy();


                if (gold == 500) {
                    if (gems > 50 - 1) {
                        win();
                        editorcoinsandgems.putInt("gems", gems - 50);
                        editorcoinsandgems.apply();

                    } else {
                        Toast.makeText(oneononeScene.this, "Not Enough Gems!", Toast.LENGTH_SHORT).show();
                    }
                }


                if (gold == 2000) {
                    if (gems > 200 - 1) {
                        win();
                        editorcoinsandgems.putInt("gems", gems - 200);
                        editorcoinsandgems.apply();


                    } else {
                        Toast.makeText(oneononeScene.this, "Not Enough Gems!", Toast.LENGTH_SHORT).show();
                    }
                }

                if (gold == 5000) {
                    if (gems > 500 - 1) {
                        win();
                        editorcoinsandgems.putInt("gems", gems - 500);
                        editorcoinsandgems.apply();


                    } else {
                        Toast.makeText(oneononeScene.this, "Not Enough Gems!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (gold == 10000) {
                    if (gems > 1000 - 1) {
                        win();
                        editorcoinsandgems.putInt("gems", gems - 1000);
                        editorcoinsandgems.apply();


                    } else {
                        Toast.makeText(oneononeScene.this, "Not Enough Gems!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (gold == 25000) {
                    if (gems > 2500 - 1) {
                        win();
                        editorcoinsandgems.putInt("gems", gems - 2500);
                        editorcoinsandgems.apply();


                    } else {
                        Toast.makeText(oneononeScene.this, "Not Enough Gems!", Toast.LENGTH_SHORT).show();
                    }
                }

                if (gold == 50000) {
                    if (gems > 5000 - 1) {
                        win();
                        editorcoinsandgems.putInt("gems", gems - 5000);
                        editorcoinsandgems.apply();


                    } else {
                        Toast.makeText(oneononeScene.this, "Not Enough Gems!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        List<String> list = new ArrayList<String>();
        list.add("Liam Smith");
        list.add("Noah Smith");
        list.add("Oliver Smith");
        list.add("William Smith");
        list.add("Elijah Smith");
        list.add("James Smith");
        list.add("Benjamin Smith");
        list.add("Lucas Smith");
        list.add("Mason Smith");
        list.add("Ethan Smith");
        list.add("Alexander Smith");
        list.add("Henry Smith");
        list.add("Jacob Smith");
        list.add("Michael Smith");
        list.add("Daniel Johnson");
        list.add("Logan Johnson");
        list.add("Jackson Johnson");
        list.add("Sebastian Johnson");
        list.add("Jack Johnson");
        list.add("Aiden Johnson");
        list.add("Owen Johnson");
        list.add("Samuel Johnson");
        list.add("Matthew Johnson");
        list.add("Joseph Johnson");
        list.add("Levi Johnson");
        list.add("Mateo Johnson");
        list.add("David Johnson");
        list.add("John Johnson");
        list.add("Wyatt Johnson");
        list.add("Carter Johnson");
        list.add("Julian Johnson");
        list.add("Luke Johnson");
        list.add("Grayson Johnson");
        list.add("Isaac Johnson");
        list.add("Jayden Johnson");
        list.add("Theodore Williams");
        list.add("Gabriel Williams");
        list.add("Anthony Williams");
        list.add("Dylan Williams");
        list.add("Leo Williams");
        list.add("Lincoln Williams");
        list.add("Jaxon Williams");
        list.add("Asher Williams");
        list.add("Christopher Williams");
        list.add("Josiah Williams");
        list.add("Andrew Williams");
        list.add("Thomas Williams");
        list.add("Joshua Williams");
        list.add("Ezra Williams");
        list.add("Hudson Williams");
        list.add("Charles Brown");
        list.add("Caleb Brown");
        list.add("Isaiah Brown");
        list.add("Ryan Brown");
        list.add("Nathan Brown");
        list.add("Adrian Brown");
        list.add("Christian Brown");
        list.add("Maverick Brown");
        list.add("Colton Brown");
        list.add("Elias Brown");
        list.add("Aaron Brown");
        list.add("Eli Brown");
        list.add("Landon Brown");
        list.add("Jonathan Brown");
        list.add("Nolan Brown");
        list.add("Hunter Brown");
        list.add("Cameron Brown");
        list.add("Connor Brown");
        list.add("Santiago Brown");
        list.add("Jeremiah Brown");
        list.add("Ezekiel Brown");
        list.add("Angel Brown");
        list.add("Roman Brown");
        list.add("Easton Brown");
        list.add("Miles Jones");
        list.add("Robert Jones");
        list.add("Jameson Jones");
        list.add("Nicholas Jones");
        list.add("Greyson Jones");
        list.add("Cooper Jones");
        list.add("Ian  Jones");
        list.add("Carson Jones");
        list.add("Axel Jones");
        list.add("Jaxson Jones");
        list.add("Dominic Jones");
        list.add("Leonardo Jones");
        list.add("Luca Jones");
        list.add("Austin");
        list.add("Jordan");
        list.add("Adam");
        list.add("Xavier");
        list.add("Jose");
        list.add("Jace");
        list.add("Everett");
        list.add("Declan");
        list.add("Evan");
        list.add("Kayden");
        list.add("Parker");
        list.add("Wesley");
        list.add("Kai");
        list.add("Brayden");
        list.add("Bryson");
        list.add("Weston");
        list.add("Jason");
        list.add("Emmett");
        list.add("Sawyer");
        list.add("Silas");
        list.add("Bennett Jones");
        list.add("Brooks Jones");
        list.add("MicahJones");
        list.add("Damian Jones");
        list.add("Harrison Jones");
        list.add("Waylon Jones");
        list.add("Ayden Jones");
        list.add("Vincent Jones");
        list.add("Ryder Jones");
        list.add("Kingston Jones");
        list.add("Rowan Jones");
        list.add("George Jones");
        list.add("Luis Jones");
        list.add("Chase Jones");
        list.add("Cole Jones");
        list.add("Nathani Jonesel");
        list.add("Zachary Jones");
        list.add("Ashton Jones");
        list.add("Braxton Jones");
        list.add("Gavin Jones");
        list.add("Tyler Jones");
        list.add("Diego Jones");
        list.add("Bentley Jones");
        list.add("Amir");
        list.add("Beau");
        list.add("Gael Miller");
        list.add("Carlos Miller");
        list.add("Ryker Miller");
        list.add("Jasper Miller");
        list.add("Max Miller");
        list.add("Juan Miller");
        list.add("Ivan Miller");
        list.add("Brandon Miller");
        list.add("Jonah Miller");
        list.add("Giovanni Miller");
        list.add("Kaiden Miller");
        list.add("Myles Miller");
        list.add("Calvin Miller");
        list.add("Lorenzo Miller");
        list.add("Maxwell Miller");
        list.add("Jayce Miller");
        list.add("Kevin Miller");
        list.add("Legend Miller");
        list.add("Tristan Miller");
        list.add("Jesus Miller");
        list.add("Jude Miller");
        list.add("Zion Miller");
        list.add("Justin Miller");
        list.add("Maddox Miller");
        list.add("Abel Miller");
        list.add("King Miller");
        list.add("Camden Miller");
        list.add("Elliott Miller");
        list.add("Malachi Miller");
        list.add("Milo Miller");
        list.add("Emmanuel");
        list.add("Karter");
        list.add("Rhett");
        list.add("Alex");
        list.add("August Gonzalez");
        list.add("River");
        list.add("XanderGonzalez");
        list.add("Antonio");
        list.add("Brody");
        list.add("Finn Gonzalez");
        list.add("Elliot");
        list.add("Dean");
        list.add("Emiliano Gonzalez");
        list.add("Eric");
        list.add("Miguel");
        list.add("Arthur Gonzalez");
        list.add("Matteo");
        list.add("Graham");
        list.add("Alan");
        list.add("Nicolas");
        list.add("Blake");
        list.add("Thiago");
        list.add("Adriel");
        list.add("Victor");
        list.add("Joel");
        list.add("Timothy");
        list.add("Hayden Gonzalez");
        list.add("Judah");
        list.add("Abraham");
        list.add("Edward");
        list.add("Messiah Gonzalez");
        list.add("Zayden");
        list.add("Theo Gonzalez");
        list.add("Tucker");
        list.add("Grant");
        list.add("Richard");
        list.add("Alejandro");
        list.add("Steven");
        list.add("Jesse");
        list.add("Dawson");
        list.add("Bryce");
        list.add("Avery Gonzalez");
        list.add("Oscar");
        list.add("Patrick");
        list.add("Archer");
        list.add("Barrett");
        list.add("Leon Gonzalez");
        list.add("Colt");
        list.add("Charlie");
        list.add("Peter");
        list.add("Kaleb");
        list.add("Lukas");
        list.add("Beckett");
        list.add("Jeremy");
        list.add("Preston");
        list.add("Enzo");
        list.add("Luka");
        list.add("Andres");
        list.add("Marcus");
        list.add("Felix");
        list.add("Mark");
        list.add("Ace Gonzalez");
        list.add("Brantley");
        list.add("Atlas");
        list.add("Remington");
        list.add("Maximus");
        list.add("Matias");
        list.add("Walker");
        list.add("Kyrie");
        list.add("Griffin");
        list.add("Kenneth");
        list.add("Israel");
        list.add("Javier");
        list.add("Kyler");
        list.add("Jax");
        list.add("Amari Gonzalez");
        list.add("Zane");
        list.add("Emilio");
        list.add("Knox");
        list.add("Adonis");
        list.add("Aidan");
        list.add("Kaden");
        list.add("Paul");
        list.add("Omar");
        list.add("Brian Gonzalez");
        list.add("Louis");
        list.add("Caden");
        list.add("Maximiliano");
        list.add("Holden");
        list.add("Paxton");
        list.add("Nash");
        list.add("Bradley");
        list.add("Bryan");
        list.add("Simon Wilson");
        list.add("Phoenix");
        list.add("Lane Wilson");
        list.add("Josue");
        list.add("Colin Wilson");
        list.add("Rafael");
        list.add("Kyle");
        list.add("Riley");
        list.add("Jorge");
        list.add("Beckham");
        list.add("Cayden");
        list.add("Jaden Wilson");
        list.add("Emerson");
        list.add("Ronan Wilson");
        list.add("Karson");
        list.add("Arlo Wilson");
        list.add("TobiasWilson");
        list.add("Brady Wilson");
        list.add("Clayton");
        list.add("Francisco");
        list.add("Zander");
        list.add("Erick");
        list.add("Walter Wilson");
        list.add("Daxton");
        list.add("Cash Wilson");
        list.add("Martin Wilson");
        list.add("Damien Wilson");
        list.add("Dallas");
        list.add("Cody Wilson");
        list.add("Chance");
        list.add("Jensen");
        list.add("Finley");
        list.add("Jett");
        list.add("Corbin");
        list.add("Kash");
        list.add("Reid Wilson");
        list.add("Kameron Wilson");
        list.add("Andre");
        list.add("Gunner Wilson");
        list.add("Jake Wilson");
        list.add("Hayes");
        list.add("Manuel Wilson");
        list.add("Prince");
        list.add("Bodhi Wilson");
        list.add("Cohen");
        list.add("Sean");
        list.add("Khalil");
        list.add("Hendrix");
        list.add("Derek");
        list.add("Cristian Wilson");
        list.add("Cruz");
        list.add("Kairo");
        list.add("Dante");
        list.add("Atticus Wilson");
        list.add("Killian");
        list.add("Stephen");
        list.add("Orion");
        list.add("MalakaiWilson");
        list.add("Ali");
        list.add("Eduardo");
        list.add("Fernando");
        list.add("AndWilson erson");
        list.add("Angelo");
        list.add("Spencer");
        list.add("Gideon");
        list.add("Mario");
        list.add("Titus");
        list.add("Travis");
        list.add("Rylan");
        list.add("Kayson Wilson");
        list.add("Ricardo");
        list.add("Tanner");
        list.add("Malcolm");
        list.add("Raymond Wilson");
        list.add("Odin");
        list.add("Cesar");
        list.add("Lennox");
        list.add("Joaquin");
        list.add("Kane");
        list.add("Wade");
        list.add("Muhammad Wilson");
        list.add("Iker");
        list.add("Jaylen Wilson");
        list.add("Crew");
        list.add("Zayn Wilson");
        list.add("Hector");
        list.add("Ellis");
        list.add("Leonel Wilson");
        list.add("Cairo");
        list.add("Garrett");
        list.add("Romeo");
        list.add("Dakota");
        list.add("Edwin");
        list.add("Warren Wilson");
        list.add("Julius");
        list.add("Major");
        list.add("Donovan");
        list.add("Caiden");
        list.add("Tyson Wilson");
        list.add("Nico");
        list.add("Sergio");
        list.add("Nasir");
        list.add("Rory");
        list.add("Devin");
        list.add("Jaiden Wilson");
        list.add("Jared");
        list.add("Kason");
        list.add("Malik");
        list.add("Jeffrey Wilson");
        list.add("Ismael");
        list.add("Elian");
        list.add("Marshall");
        list.add("Lawson");
        list.add("Desmond");
        list.add("Winston");
        list.add("Nehemiah Wilson");
        list.add("Ari");
        list.add("Conner");
        list.add("Jay");
        list.add("Kade");
        list.add("Andy Wilson");
        list.add("Johnny");
        list.add("Jayceon");
        list.add("Marco");
        list.add("Seth");
        list.add("Ibrahim");
        list.add("Raiden");
        list.add("Collin");
        list.add("Edgar");
        list.add("Erik");
        list.add("Troy");
        list.add("Clark");
        list.add("Jaxton");
        list.add("Johnathan");
        list.add("Gregory");
        list.add("Russell");
        list.add("Royce");
        list.add("Fabian");
        list.add("Ezequiel");
        list.add("Noel");
        list.add("Pablo");
        list.add("Cade");
        list.add("Pedro");
        list.add("Sullivan");
        list.add("Trevor");
        list.add("Reed");
        list.add("Quinn");
        list.add("Frank");
        list.add("Harvey");
        list.add("Princeton");
        list.add("Zayne");
        list.add("Matthias");
        list.add("Conor");
        list.add("Sterling");
        list.add("Dax");
        list.add("Grady");
        list.add("Cyrus");
        list.add("Gage");
        list.add("Leland Wilson");
        list.add("Solomon");
        list.add("Emanuel");
        list.add("Niko");
        list.add("Ruben");
        list.add("Kasen");
        list.add("Mathias");
        list.add("Kashton");
        list.add("Franklin");
        list.add("Remy");
        list.add("Shane");
        list.add("Kendrick");
        list.add("Shawn Wilson");
        list.add("Otto");
        list.add("Armani Lee");
        list.add("Keegan");
        list.add("Finnegan");
        list.add("Memphis");
        list.add("Bowen Lee");
        list.add("Dominick");
        list.add("Kolton");
        list.add("Jamison");
        list.add("Allen");
        list.add("Philip");
        list.add("Tate");
        list.add("Peyton");
        list.add("Jase Lee");
        list.add("Oakley");
        list.add("Rhys Lee");
        list.add("Kyson");
        list.add("Adan");
        list.add("Esteban");
        list.add("Dalton");
        list.add("Gianni");
        list.add("Callum");
        list.add("Sage");
        list.add("Alexis");
        list.add("Milan Lee");
        list.add("Moises");
        list.add("Jonas");
        list.add("Uriel");
        list.add("Colson");
        list.add("Marcos");
        list.add("Zaiden");
        list.add("Hank");
        list.add("Damon");
        list.add("Hugo");
        list.add("Ronin");
        list.add("Royal");
        list.add("Kamden Lee");
        list.add("Dexter");
        list.add("Luciano");
        list.add("Alonzo");
        list.add("Augustus");
        list.add("Kamari");
        list.add("Eden");
        list.add("Roberto Lee");
        list.add("Baker");
        list.add("Bruce");
        list.add("Kian");
        list.add("Albert Lee");
        list.add("Frederick");
        list.add("Mohamed");
        list.add("Abram");
        list.add("Omari");
        list.add("Porter");
        list.add("Enrique");
        list.add("Alijah");
        list.add("Francis Lee");
        list.add("Leonidas");
        list.add("Zachariah");
        list.add("Landen");
        list.add("Wilder");
        list.add("Apollo");
        list.add("Santino");
        list.add("Tatum");
        list.add("Pierce");
        list.add("Forrest Lee");
        list.add("Corey");
        list.add("Derrick");
        list.add("Isaias");
        list.add("Kaison");
        list.add("Kieran");
        list.add("Arjun");
        list.add("Gunnar");
        list.add("Rocco");
        list.add("Emmitt Lee");
        list.add("Abdiel");
        list.add("Braylen");
        list.add("Maximilian");
        list.add("Skyler");
        list.add("PhillipLee");
        list.add("Benson");
        list.add("Cannon");
        list.add("Deacon");
        list.add("Dorian");
        list.add("Asa");
        list.add("Moses");
        list.add("Ayaan");
        list.add("Jayson");
        list.add("Raul");
        list.add("Briggs");
        list.add("Armando Lee");
        list.add("Nikolai");
        list.add("Cassius");
        list.add("Drew");
        list.add("Rodrigo");
        list.add("Raphael");
        list.add("Danny Lee");
        list.add("Conrad");
        list.add("Moshe");
        list.add("Zyaire Lee");
        list.add("Julio");
        list.add("Casey");
        list.add("Ronald");
        list.add("Scott Lee");
        list.add("Callan");
        list.add("Roland");
        list.add("Saul Lee");
        list.add("Jalen");
        list.add("Brycen");
        list.add("Ryland");
        list.add("Lawrence");
        list.add("Davis");
        list.add("Rowen");
        list.add("Zain");
        list.add("Ermias");
        list.add("Jaime");
        list.add("Duke");
        list.add("Stetson");
        list.add("Alec");
        list.add("Yusuf");
        list.add("Case");
        list.add("Trenton Lee");
        list.add("Callen");
        list.add("Ariel");
        list.add("Jasiah");
        list.add("Soren Lee");
        list.add("Dennis");
        list.add("Donald");
        list.add("Keith");
        list.add("Izaiah");
        list.add("Lewis");
        list.add("Kylan");
        list.add("Kobe");
        list.add("Makai");
        list.add("Rayan");
        list.add("Ford");
        list.add("Zaire");
        list.add("Landyn Lee");
        list.add("Roy Lee");
        list.add("Bo");
        list.add("Chris");
        list.add("Jamari");
        list.add("Ares");
        list.add("Mohammad");
        list.add("Darius");
        list.add("Drake");
        list.add("Tripp");
        list.add("Marcelo Lee");
        list.add("Samson");
        list.add("Dustin");
        list.add("Layton");
        list.add("Gerardo");
        list.add("Johan");
        list.add("Kaysen");
        list.add("KeatonLee");
        list.add("Reece");
        list.add("Chandler Lee");
        list.add("Lucca");
        list.add("Mack");
        list.add("Baylor");
        list.add("Kannon");
        list.add("Marvin");
        list.add("Huxley");
        list.add("Nixon");
        list.add("Tony");
        list.add("Cason");
        list.add("Mauricio");
        list.add("Quentin Lee");
        list.add("Edison");
        list.add("Quincy");
        list.add("Ahmed");
        list.add("Finnley Thompson");
        list.add("Justice");
        list.add("Taylor Thompson");
        list.add("Gustavo");
        list.add("Brock");
        list.add("Ahmad");
        list.add("Kyree");
        list.add("Arturo");
        list.add("Nikolas");
        list.add("Boston");
        list.add("Sincere");
        list.add("Alessandro");
        list.add("Braylon");
        list.add("Colby Thompson");
        list.add("Leonard");
        list.add("Ridge");
        list.add("Trey Thompson");
        list.add("Aden");
        list.add("Leandro");
        list.add("Sam Thompson");
        list.add("Uriah");
        list.add("Ty");
        list.add("Sylas");
        list.add("Axton");
        list.add("Issac");
        list.add("Fletcher");
        list.add("Julien");
        list.add("Wells");
        list.add("Alden");
        list.add("Vihaan");
        list.add("Jamir");
        list.add("Valentino Thompson");
        list.add("Shepherd");
        list.add("Keanu");
        list.add("Hezekiah");
        list.add("Lionel");
        list.add("Kohen");
        list.add("Zaid Thompson");
        list.add("Alberto");
        list.add("Neil");
        list.add("Denver");
        list.add("Aarav");
        list.add("Brendan");
        list.add("Dillon Thompson");
        list.add("Koda");
        list.add("Sutton");
        list.add("Kingsley Thompson");
        list.add("Sonny");
        list.add("Alfredo");
        list.add("Wilson");
        list.add("Harry");
        list.add("Jaziel Thompson");
        list.add("Salvador");
        list.add("Cullen");
        list.add("Hamza");
        list.add("Dariel");
        list.add("Rex");
        list.add("Zeke Thompson");
        list.add("Mohammed");
        list.add("Nelson");
        list.add("Boone");
        list.add("Ricky");
        list.add("Santana");
        list.add("Cayson");
        list.add("Lance");
        list.add("Raylan Thompson");
        list.add("Lucian");
        list.add("Eliel");
        list.add("Alvin");
        list.add("Jagger");
        list.add("Braden");
        list.add("Curtis");
        list.add("Mathew");
        list.add("Jimmy");
        list.add("Kareem Thompson");
        list.add("Archie");
        list.add("Amos Thompson");
        list.add("Quinton");
        list.add("Yosef");
        list.add("Bodie");
        list.add("Jerry");
        list.add("Langston");
        list.add("Axl Thompson");
        list.add("Stanley");
        list.add("Clay");
        list.add("Douglas");
        list.add("Layne");
        list.add("Titan");
        list.add("Tomas Thompson");
        list.add("Houston");
        list.add("Darren Thompson");
        list.add("Lachlan");
        list.add("Kase");
        list.add("Korbin");
        list.add("Leighton Thompson");
        list.add("Joziah");
        list.add("Samir");
        list.add("Watson");
        list.add("Colten");
        list.add("Roger");
        list.add("Shiloh");
        list.add("Tommy");
        list.add("Mitchell");
        list.add("Azariah");
        list.add("Noe");
        list.add("Talon");
        list.add("Deandre");
        list.add("Lochlan");
        list.add("Joe");
        list.add("Carmelo Thompson");
        list.add("Otis Thompson");
        list.add("Randy Thompson");
        list.add("Byron");
        list.add("Chaim");
        list.add("Lennon");
        list.add("Devon");
        list.add("Nathanael");
        list.add("Bruno");
        list.add("Aryan Clark");
        list.add("Flynn");
        list.add("Vicente");
        list.add("Brixton Clark");
        list.add("Kyro");
        list.add("Brennan");
        list.add("Casen");
        list.add("Kenzo");
        list.add("Orlando Clark");
        list.add("Castiel");
        list.add("Rayden");
        list.add("Ben Clark");
        list.add("Grey");
        list.add("Jedidiah");
        list.add("Tadeo");
        list.add("Morgan Clark");
        list.add("Augustine");
        list.add("Mekhi");
        list.add("Abdullah");
        list.add("Ramon");
        list.add("Saint Clark");
        list.add("Emery");
        list.add("Maurice");
        list.add("Jefferson");
        list.add("Maximo");
        list.add("Koa");
        list.add("Ray");
        list.add("Jamie Clark");
        list.add("Eddie");
        list.add("Guillermo");
        list.add("Onyx");
        list.add("Thaddeus");
        list.add("Wayne");
        list.add("Hassan");
        list.add("Alonso Clark");
        list.add("Dash");
        list.add("Elisha");
        list.add("Jaxxon");
        list.add("Rohan");
        list.add("Carl");
        list.add("Kelvin");
        list.add("Jon");
        list.add("Larry");
        list.add("Reese");
        list.add("Aldo");
        list.add("Marcel");
        list.add("Melvin");
        list.add("Yousef");
        list.add("Aron");
        list.add("Kace");
        list.add("Vincenzo Clark");
        list.add("Kellan");
        list.add("Miller");
        list.add("Jakob");
        list.add("Reign");
        list.add("Kellen");
        list.add("Kristopher");
        list.add("Ernesto");
        list.add("Briar");
        list.add("Gary");
        list.add("Trace");
        list.add("Joey");
        list.add("Clyde");
        list.add("Enoch");
        list.add("Jaxx");
        list.add("Crosby Clark");
        list.add("Magnus");
        list.add("Fisher");
        list.add("Jadiel");
        list.add("Bronson");
        list.add("Eugene");
        list.add("Lee");
        list.add("BreckenClark");
        list.add("Atreus");
        list.add("Madden");
        list.add("Khari");
        list.add("Caspian");
        list.add("Ishaan");
        list.add("Kristian");
        list.add("Westley");
        list.add("Hugh");
        list.add("Kamryn Clark");
        list.add("Musa");
        list.add("Rey");
        list.add("Thatcher");
        list.add("Alfred");
        list.add("Emory");
        list.add("Kye");
        list.add("Reyansh");
        list.add("Yahir");
        list.add("Cain Clark");
        list.add("Mordechai");
        list.add("Zayd");
        list.add("Demetrius");
        list.add("Harley");
        list.add("Felipe");
        list.add("Louie");
        list.add("Branson");
        list.add("Graysen");
        list.add("Allan");
        list.add("Kole");
        list.add("Harold");
        list.add("Alvaro");
        list.add("Harlan");
        list.add("Amias");
        list.add("Brett");
        list.add("Khalid");
        list.add("Misael");
        list.add("Westin");
        list.add("Zechariah");
        list.add("Aydin Clark");
        list.add("Kaiser");
        list.add("Lian");
        list.add("Bryant");
        list.add("Junior");
        list.add("Legacy");
        list.add("Ulises");
        list.add("Bellamy");
        list.add("Brayan");
        list.add("Kody");
        list.add("Ledger");
        list.add("Eliseo");
        list.add("Gordon");
        list.add("London");
        list.add("Rocky");
        list.add("Valentin Clark");
        list.add("Terry");
        list.add("Damari");
        list.add("Trent");
        list.add("Bentlee");
        list.add("Canaan");
        list.add("Gatlin");
        list.add("Kiaan");
        list.add("Franco Clark");
        list.add("Eithan");
        list.add("Idris");
        list.add("Krew");
        list.add("Yehuda");
        list.add("Marlon");
        list.add("Rodney");
        list.add("Creed");
        list.add("Salvatore");
        list.add("Stefan");
        list.add("Tristen");
        list.add("Adrien");
        list.add("Jamal");
        list.add("Judson Clark");
        list.add("Camilo");
        list.add("Kenny");
        list.add("Nova");
        list.add("Robin");
        list.add("Rudy");
        list.add("Van");
        list.add("Bjorn");
        list.add("Brodie");
        list.add("Mac Clark");
        list.add("Jacoby");
        list.add("Sekani");
        list.add("Vivaan");
        list.add("Blaine");
        list.add("Ira");
        list.add("Ameer");
        list.add("Dominik");
        list.add("Alaric");
        list.add("Dane");
        list.add("Jeremias");
        list.add("Kyng Clark");
        list.add("Reginald");
        list.add("Bobby");
        list.add("Kabir");
        list.add("Jairo");
        list.add("Alexzander");
        list.add("Benicio");
        list.add("Vance");
        list.add("Wallace");
        list.add("Zavier");
        list.add("BillyClark");
        list.add("Callahan");
        list.add("Dakari");
        list.add("Gerald");
        list.add("Turner");
        list.add("Bear");
        list.add("Jabari");
        list.add("Cory");
        list.add("Fox");
        list.add("Harlem");
        list.add("Jakari");
        list.add("Jeffery");
        list.add("Maxton");
        list.add("Ronnie");
        list.add("Yisroel");
        list.add("Zakai");
        list.add("Bridger");
        list.add("Remi");
        list.add("Arian Clark");
        list.add("Blaze");
        list.add("Forest");
        list.add("Genesis");
        list.add("Jerome");
        list.add("Reuben");
        list.add("Wesson");
        list.add("Anders Clark");
        list.add("Banks");
        list.add("Calum");
        list.add("Dayton");
        list.add("Kylen");
        list.add("Dangelo");
        list.add("Emir");
        list.add("Malakhi");
        list.add("Salem");
        list.add("Blaise");
        list.add("Tru");
        list.add("Boden");
        list.add("Kolten");
        list.add("Kylo");
        list.add("Aries");
        list.add("Henrik");
        list.add("Kalel");
        list.add("Landry Clark");
        list.add("Marcellus");
        list.add("Zahir");
        list.add("Lyle");
        list.add("Dario");
        list.add("Rene");
        list.add("Terrance");
        list.add("Xzavier");
        list.add("Alfonso");
        list.add("Darian Clark");
        list.add("Kylian");
        list.add("Maison");
        list.add("Foster");
        list.add("Keenan");
        list.add("Yahya");
        list.add("Heath");
        list.add("Javion");
        list.add("Jericho");
        list.add("Aziel");
        list.add("Darwin");
        list.add("Marquis");
        list.add("Mylo Clark");
        list.add("Ambrose");
        list.add("Anakin");
        list.add("Jordy");
        list.add("Juelz");
        list.add("Toby");
        list.add("Yael");
        list.add("Azrael");
        list.add("Brentley");
        list.add("Tristian");
        list.add("Bode");
        list.add("Jovanni");
        list.add("Santos");
        list.add("Alistair");
        list.add("Braydon");
        list.add("Kamdyn");
        list.add("Marc");
        list.add("Mayson Clark");
        list.add("Niklaus");
        list.add("Simeon");
        list.add("Colter");
        list.add("Davion");
        list.add("Leroy");
        list.add("Ayan");
        list.add("Dilan");
        list.add("Ephraim");
        list.add("Anson");
        list.add("Merrick");
        list.add("Wes Clark");
        list.add("Will");
        list.add("Jaxen");
        list.add("Maxim");
        list.add("Howard");
        list.add("Jad");
        list.add("Jesiah");
        list.add("Ignacio");
        list.add("Zyon");
        list.add("Ahmir");
        list.add("Jair");
        list.add("Mustafa");
        list.add("Jermaine Clark");
        list.add("Yadiel");
        list.add("Aayan");
        list.add("Dhruv Sharma");
        list.add("Seven Rogers");
        list.add("Stone");
        list.add("Rome");

        Random rand = new Random();
        String random = list.get(rand.nextInt(list.size()));


        TextView player2 = findViewById(R.id.player2);
        player2.setText(" " + random + " ");

        SharedPreferences playername = getApplicationContext().getSharedPreferences("playername", MODE_PRIVATE);

        String name = playername.getString("playernametext", "Guest");

        TextView playerone = findViewById(R.id.playerone);
        playerone.setText(" " + name + " ");


        if (gold < 2001) {

            final int mini = 1;
            final int maxi = 10;
            final int random2 = new Random().nextInt((maxi - mini) + 1) + mini;

            if (random2 == 5) {
                SharedPreferences opleftpref = getApplicationContext().getSharedPreferences("opleftpref", MODE_PRIVATE);
                SharedPreferences.Editor opleftprefed = opleftpref.edit();


                boolean opleft = opleftpref.getBoolean("opleft", false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        opleftprefed.putBoolean("opleft", true);
                        opleftprefed.apply();
                        win();
                    }
                }, 3000);

            }

        }


    }
            private void cputest(){
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


            private void win() {

                // Toast.makeText(this, String.valueOf(level2), Toast.LENGTH_SHORT).show();

                SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
                SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();

                SharedPreferences crownpref = getApplicationContext().getSharedPreferences("crownamnt", MODE_PRIVATE);
                SharedPreferences.Editor crowneditor = crownpref.edit();
                crowneditor.putBoolean("timeupb", false);
                crowneditor.apply();


                int coins = coinsandgems.getInt("coins", 0);
                SharedPreferences playcoin = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
                int gold = playcoin.getInt("playcoinamount", 500);
                editorcoinsandgems.putInt("coins", coins + gold);
                editorcoinsandgems.apply();

                SharedPreferences wincount = getApplicationContext().getSharedPreferences("wincount", MODE_PRIVATE);
                SharedPreferences.Editor wincounted = wincount.edit();
                int winc = wincount.getInt("winc", 0);

                wincounted.putInt("winc", winc + 1);
                wincounted.apply();

                boolean winc1b = wincount.getBoolean("winc1b", false);
                boolean winc1b1 = wincount.getBoolean("winc1b1", false);

                if (!winc1b) {

                    int winc1 = wincount.getInt("winc1", 0);

                    wincounted.putInt("winc1", winc1 + 1);
                    wincounted.apply();


                    winc1 = wincount.getInt("winc1", 0);
                    if (winc1 > 14) {
                        wincounted.putInt("winc1", 0);
                        wincounted.putBoolean("winc1b", true);
                        wincounted.apply();


                        SharedPreferences acvpref = getApplicationContext().getSharedPreferences("acvpref", MODE_PRIVATE);
                        SharedPreferences.Editor acved = acvpref.edit();
                        boolean acvos1 = acvpref.getBoolean("acvos1", false);
                        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(oneononeScene.this);
                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                            if (!acvos1) {
                                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                        .unlock(getString(R.string.my_achievement_id28));
                                Alerter.create(oneononeScene.this)
                                        .setTitle("Achievement Unlocked!")
                                        .setText("Alpha Player!")
                                        .setIcon(R.drawable.achievement)
                                        .setIconColorFilter(0) // Optional - Removes white tint
                                        .setBackgroundColorRes(R.color.colorAccent)
                                        .show();
                                acved.putBoolean("acvos1", true);
                                acved.apply();
                            }
                        }
                    }
                }
                int winc1 = wincount.getInt("winc1", 0);


                if (winc1 == 1) {
                    if (!winc1b1) {
                        wincounted.putBoolean("winc1b1", true);
                        wincounted.apply();

                        SharedPreferences acvpref = getApplicationContext().getSharedPreferences("acvpref", MODE_PRIVATE);
                        SharedPreferences.Editor acved = acvpref.edit();
                        boolean acvos2 = acvpref.getBoolean("acvos2", false);
                        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(oneononeScene.this);
                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                            if (!acvos2) {
                                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                        .unlock(getString(R.string.my_achievement_id29));
                                Alerter.create(oneononeScene.this)
                                        .setTitle("Achievement Unlocked!")
                                        .setText("1 on 1 Beginner")
                                        .setIcon(R.drawable.achievement)
                                        .setIconColorFilter(0) // Optional - Removes white tint
                                        .setBackgroundColorRes(R.color.colorAccent)
                                        .show();
                                acved.putBoolean("acvos2", true);
                                acved.apply();
                            }
                        }
                    }
                }

                editorcoinsandgems.putInt("coins", coins + gold);
                // editorcoinsandgems.putInt("gems", gems + wingems);
                editorcoinsandgems.apply();

                windialog();


                // int win1=winpref.getInt("key_name2", 0);             // getting Integer
                intersload();
            }


            private void timeupdialog () {
                SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
                SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();


                int gold = getIntent().getIntExtra("gold", 0);


                dialog.setContentView(R.layout.oneononetimeup_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCancelable(false);
                ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                Button btnskip = dialog.findViewById(R.id.btnskip);

                TextView coinst = dialog.findViewById(R.id.wincoins);
                coinst.setText("- " + gold + " Golds");


                imageViewclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        doreset();

                        dialog.dismiss();
                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


                btnskip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        doreset();
                        dialog.dismiss();

                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


                dialog.show();

                intersshow();
            }


            private void lose () {
                SharedPreferences crownpref = getApplicationContext().getSharedPreferences("crownamnt", MODE_PRIVATE);
                SharedPreferences.Editor crowneditor = crownpref.edit();
                crowneditor.putBoolean("timeupb", false);
                crowneditor.apply();
                // Toast.makeText(this, "loose", Toast.LENGTH_SHORT).show();
                int loss = getIntent().getIntExtra("loss", 0);
                SharedPreferences losses = getApplicationContext().getSharedPreferences("loss", MODE_PRIVATE);
                SharedPreferences.Editor losseseditor = losses.edit();
                losseseditor.putInt("loss", loss + 1);

                losseseditor.apply();

                SharedPreferences wincount = getApplicationContext().getSharedPreferences("wincount", MODE_PRIVATE);
                SharedPreferences.Editor wincounted = wincount.edit();

                boolean winc1b = wincount.getBoolean("winc1b", false);

                if (!winc1b) {
                    int winc1 = wincount.getInt("winc1", 0);

                    wincounted.putInt("winc1", 0);
                    wincounted.apply();
                }
                losedialog();

                intersload();

            }

            private void draw () {

                int loss = getIntent().getIntExtra("loss", 0);
                SharedPreferences losses = getApplicationContext().getSharedPreferences("loss", MODE_PRIVATE);
                SharedPreferences.Editor losseseditor = losses.edit();
                losseseditor.putInt("loss", loss + 1);
                losseseditor.apply();


                SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
                SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();


                int coins = coinsandgems.getInt("coins", 0);

                int gold = getIntent().getIntExtra("gold", 0);


                editorcoinsandgems.putInt("coins", coins + gold);
                // editorcoinsandgems.putInt("gems", gems + wingems);
                editorcoinsandgems.apply();
                drawdialog();

                intersload();
                //  fbinter();
            }

            private void windialog () {

                // int wincoins = getIntent().getIntExtra("coins",0);
                // int wingems = getIntent().getIntExtra("gems",0);
                SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
                SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();


                int gold = getIntent().getIntExtra("gold", 0);


                dialog.setContentView(R.layout.oneononewin);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCancelable(false);
                ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                Button btnnxt = dialog.findViewById(R.id.btnskip);

                TextView leveltextdialog = dialog.findViewById(R.id.leveltextdialog);
                TextView textview5 = dialog.findViewById(R.id.textView5);

                TextView coinst = dialog.findViewById(R.id.wincoins);
                //TextView gems = dialog.findViewById(R.id.wingems);

                coinst.setText("+ " + gold * 2 + " Golds");
                // gems.setText("+ " + wingems + " Gems");



                SharedPreferences opleftpref = getApplicationContext().getSharedPreferences("opleftpref", MODE_PRIVATE);
                SharedPreferences.Editor opleftprefed = opleftpref.edit();


                boolean opleft = opleftpref.getBoolean("opleft", false);
                if (opleft) {
                    leveltextdialog.setText("OPPONENT LEFT!");
                    textview5.setText("You Got the Winning Amount!");
                    // boolean opleft = opleftpref.getBoolean("opleft", false);
                    opleftprefed.putBoolean("opleft", false);
                    opleftprefed.apply();            }





                imageViewclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  soundPool.play(sound1, 1, 1, 0, 0, 1);

                        doreset();

                        dialog.dismiss();
                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        //finish();

                    }
                });


                btnnxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(oneononeScene.this, "yeah touvh", Toast.LENGTH_SHORT).show();
                        //  soundPool.play(sound1, 1, 1, 0, 0, 1);
                        doreset();
                        dialog.dismiss();

                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        //finish();

                    }
                });

                dialog.show();

                intersshow();

            }


            private void losedialog () {
                SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
                SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();


                //   int coins = coinsandgems.getInt("coins", 0);

                int gold = getIntent().getIntExtra("gold", 0);


                dialog.setContentView(R.layout.oneononeloss_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCancelable(false);
                ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                Button btnskip = dialog.findViewById(R.id.btnskip);

                TextView coinst = dialog.findViewById(R.id.wincoins);
                coinst.setText("- " + gold + " Golds");


                imageViewclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // soundPool.play(sound1, 1, 1, 0, 0, 1);

                        doreset();

                        dialog.dismiss();
                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                });


                btnskip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  soundPool.play(sound1, 1, 1, 0, 0, 1);

                        doreset();
                        dialog.dismiss();

                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                });


                dialog.show();
                intersshow();
            }

            private void drawdialog () {
                SharedPreferences coinsandgems = getApplicationContext().getSharedPreferences("coinsandgems", MODE_PRIVATE);
                SharedPreferences.Editor editorcoinsandgems = coinsandgems.edit();
                int coins = coinsandgems.getInt("coins", 0);


                //   int coins = coinsandgems.getInt("coins", 0);

                int gold = getIntent().getIntExtra("gold", 0);


                dialog.setContentView(R.layout.oneononedraw_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCancelable(false);
                ImageView imageViewclose = dialog.findViewById(R.id.imageViewClose);
                Button btnskip = dialog.findViewById(R.id.btnskip);
                TextView coinst = dialog.findViewById(R.id.wincoins);
                coinst.setText("No gold lost");
                //   editorcoinsandgems.putInt("coins", coins + gold/2);
                //editorcoinsandgems.putInt("gems", 90);        // Saving integer
                //  editorcoinsandgems.apply();    already done

                //  coinst.setText("- " + gold / 2 + " Golds");


                imageViewclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   soundPool.play(sound1, 1, 1, 0, 0, 1);


                        doreset();

                        dialog.dismiss();
                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                });


                btnskip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // soundPool.play(sound1, 1, 1, 0, 0, 1);

                        doreset();
                        dialog.dismiss();

                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                });


                dialog.show();
                intersshow();
            }


            public void kzz (View view){


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


            public void kzo (View view){

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

            public void kzt (View view){
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

            public void koz (View v){
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

            public void koo (View v){
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

            public void kot (View v){
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

            public void ktz (View v){
                if (win == 0 && buttonpressed[2][0] == 0) {
                    if (flag % 2 == 0) tracker[2][0] = ax;
                    else tracker[2][0] = zero;

                    // Toast.makeText(this, "vb u r great", Toast.LENGTH_SHORT).show();
                    printBoard();
                    winchecker();
                    cpuplay();
                    ++buttonpressed[2][0];
                    flag++;
                }
            }

            public void kto (View v){
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

            public void ktt (View v){
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

            public void cpuplay () {
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

            public boolean ifcpuwin () {
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


            public boolean ifopowin () {
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

            public boolean emptycentre () {
                if (impossible || hard) {
                    if (tracker[1][1] == 0) {
                        tracker[1][1] = zero;
                        buttonpressed[1][1]++;
                        return true;
                    }
                }
                return false;
            }

            public boolean emptycorner () {


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

            public void emptyany () {

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

            public int rand () {
                return r.nextInt(3);
            }

            public void printBoard () {

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


                if (themea1) {

//abhiyaha
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


                } else if (themea2) {

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


                } else if (themea3) {
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

                } else if (themea4) {
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

                } else if (themea5) {
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
                } else if (themea6) {
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
                } else if (themea7) {
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
                } else if (themea8) {
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
                } else if (themea9) {
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
                } else if (themea10) {
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
                } else if (themea11) {
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
            public void winchecker () {
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

                        if (Vibration) {
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
                            if (wins[0]) {
                                q1.setTextColor(getResources().getColor(R.color.cotextred));
                                wins[0] = false;
                                returns[0] = true;
                            }

                            final boolean finalReturns = returns[0];
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalReturns) {
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
                            if (wins[0]) {
                                q1.setTextColor(getResources().getColor(R.color.cotextred));
                                wins[0] = false;
                                returns[0] = true;
                            }

                            final boolean finalReturns = returns[0];
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalReturns) {
                                        q1.setTextColor(getResources().getColor(R.color.cotext));
                                        wins[0] = false;
                                        returns[0] = true;
                                    }
                                }
                            }, 500);


                            q1.setText("" + score2);
                            // showDialog("" + player2 + " won!", "" + score2, "" + player1, "" + score1);

                            // retry ya skip ya make it easy wala dialog lao
                            Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show();



                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    playmore();
                                    lose();
                                }
                            }, 300);

                        }
                        if ((sum[i] == 30) && (ax == 10)) {


                            score1++;
                            final TextView q1 = (TextView) findViewById(R.id.p1score);
                            wins[0] = true;
                            returns[0] = false;
                            if (wins[0]) {
                                q1.setTextColor(getResources().getColor(R.color.cotextred));
                                wins[0] = false;
                                returns[0] = true;
                            }

                            final boolean finalReturns = returns[0];
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalReturns) {
                                        q1.setTextColor(getResources().getColor(R.color.cotext));
                                        wins[0] = false;
                                        returns[0] = true;
                                    }
                                }
                            }, 300);


                            q1.setText("" + score1);
                            //    showDialog("" + player1 + " won!", "" + score1, "" + player2, "" + score2);
                            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_SHORT).show();


                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    playmore();
                                    win();                            }
                            }, 500);

                        }
                        if ((sum[i] == 30) && (zero == 10)) {


                            score2++;
                            final TextView q1 = (TextView) findViewById(R.id.p2score);
                            wins[0] = true;
                            returns[0] = false;
                            if (wins[0]) {
                                q1.setTextColor(getResources().getColor(R.color.cotextred));
                                wins[0] = false;
                                returns[0] = true;
                            }

                            final boolean finalReturns = returns[0];
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalReturns) {
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
                    Toast.makeText(this, "DRAW!", Toast.LENGTH_SHORT).show();


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
            private void playmore () {
                int gold = getIntent().getIntExtra("gold", 0);

                SharedPreferences losses = getApplicationContext().getSharedPreferences("loss", MODE_PRIVATE);
                SharedPreferences.Editor losseseditor = losses.edit();
                int loss = losses.getInt("loss", 0);

                if (loss > 3 && gold < 5000) {
                    easy = true;
                    medium = false;
                    hard = false;
                    impossible = false;
                    losseseditor.putInt("loss", 0);
                    losseseditor.apply();

                } else if (loss > 5 && gold > 5000 && gold < 20000) {
                    easy = false;
                    medium = true;
                    hard = false;
                    impossible = false;
                    losseseditor.putInt("loss", 0);
                    losseseditor.apply();

                } else {
                    if (gold < 5000) {
                        easy = true;
                        medium = false;
                        hard = false;
                        impossible = false;
                    }
                    if (gold == 5000) {
                        easy = false;
                        medium = true;
                        hard = false;
                        impossible = false;
                    }

                    if (gold == 10000) {
                        easy = false;
                        medium = false;
                        hard = true;
                        impossible = false;
                    }

                    if (gold > 10000) {
                        easy = false;
                        medium = false;
                        hard = false;
                        impossible = true;
                    }

                }



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
                }            //reset ends


            }


            public void doreset () {

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
                TextView qqq = (TextView) findViewById(R.id.p1score);
                qqq.setText("" + score1);
                TextView qqqq = (TextView) findViewById(R.id.p2score);
                qqqq.setText("" + score2);

                //  Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_SHORT).show();


            }


            private void showExitDialog () {
                final Dialog dialog = new Dialog(oneononeScene.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.sceneback_layout);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);

                dialog.show();

                Button exit = dialog.findViewById(R.id.yes_button);
                final Button dismiss = dialog.findViewById(R.id.no_button);

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doreset();
                        //finish();
                        Intent intent = new Intent(oneononeScene.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
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

                //do u want to go back wala new dialog jisme yes pr story act m jaye
                showExitDialog();
            }

            //NAVIGATION HIDE
            @Override
            public void onWindowFocusChanged ( boolean hasFocus){
                super.onWindowFocusChanged(hasFocus);
                if (hasFocus) {
                    decorView.setSystemUiVisibility(hideSystembars());
                }
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

            private void checkinternet () {
                ConnectivityManager connMgr = (ConnectivityManager) oneononeScene.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {

                } else {

                    Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(oneononeScene.this, Nointernet.class);
                    startActivity(intent);
                    //oneononeScene.this finish();

                }

            }


            private void dialogbuygolds () {

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
                        bp.purchase(oneononeScene.this, "gold4500");

                    }
                });

                cbuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gold30k");

                    }
                });

                cbuy3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "golds200k");

                    }
                });

                cbuy4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gold500k");

                    }
                });

                cbuy5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gold2m");

                    }
                });

                cbuy6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bp.purchase(oneononeScene.this, "gold10m");


                    }
                });


                gbuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gem100");

                    }
                });

                gbuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gem1000");

                    }
                });

                gbuy3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gem3000");

                    }
                });

                gbuy4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gem12k");

                    }
                });

                gbuy5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gem30k");

                    }
                });

                gbuy6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bp.purchase(oneononeScene.this, "gem100k");

                    }
                });


                dialog.show();


            }

            @Override
            protected void onActivityResult ( int requestCode, int resultCode, Intent data){
                if (!bp.handleActivityResult(requestCode, resultCode, data)) {
                    super.onActivityResult(requestCode, resultCode, data);
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


            @Override
            protected void onPause () {
                super.onPause();

            }





            private void intersload() {
                if (adsunit.mRewardedIntAd == null) {
                    admob.loadintrev(oneononeScene.this);

                }
                if (adsunit.mRewardedAd == null) {
                    admob.loadrew(oneononeScene.this);

                }
                if (adsunit.mInterstitial == null) {
                    admob.loadint(oneononeScene.this);

                }
            }
                private void intersshow() {
                    new admob(new ondismis() {
                        @Override
                        public void ondismis() {

                            //   Toast.makeText(MainActivity.this, "Action called", Toast.LENGTH_SHORT).show();
                        }
                    }).showIntCall(oneononeScene.this, true);


                    //ads=inters show hone ka code
                    if (adsunit.mRewardedIntAd == null) {
                        admob.loadintrev(oneononeScene.this);

                    }
                    if (adsunit.mRewardedAd == null) {
                        admob.loadrew(oneononeScene.this);

                    }
                    if (adsunit.mInterstitial == null) {
                        admob.loadint(oneononeScene.this);

                    }
                }










}
