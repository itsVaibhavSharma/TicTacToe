package com.targetappcraft.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;


public class SettingsActivity extends AppCompatActivity {


    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";

    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;

    private int revealX;
    private static final String PREFS_NAME = "vibration";
    private static final String PREF_VIBRATION = "TicVib";
    private int revealY;
    private   boolean Vibration, music;
    private boolean isChecked;
    private String[] Randomfirst;
    private View decorView;
    private SoundPool soundPool;
    private int sound1;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_ACHIEVEMENT_UI = 9003;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Vibration = preferences.getBoolean(PREF_VIBRATION, true);






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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Intent intent = getIntent();






        signincheck();

        //sign in btns
        Button btnsignin = findViewById(R.id.btnsignin);
        Button btnsignout = findViewById(R.id.btnsignout);
        ImageView loginimg = findViewById(R.id.loginimg);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;

                GoogleSignInClient signInClient = GoogleSignIn.getClient(SettingsActivity.this, signInOptions);
                signInClient
                        .silentSignIn()
                        .addOnCompleteListener(
                                SettingsActivity.this,
                                new OnCompleteListener<GoogleSignInAccount>() {
                                    @Override
                                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<GoogleSignInAccount> task) {

                                        if (task.isSuccessful()) {
                                            // The signed in account is stored in the task's result.

                                            SharedPreferences gpg = getApplicationContext().getSharedPreferences("gpg", MODE_PRIVATE);
                                            SharedPreferences.Editor gpged = gpg.edit();

                                            gpged.putBoolean("gpgb", true);        // Saving integer
                                            gpged.apply();


                                            GoogleSignInAccount signedInAccount = task.getResult();
                                            btnsignout.setVisibility(View.VISIBLE);
                                            btnsignin.setVisibility(View.GONE);
                                            loginimg.setImageResource(R.drawable.ic_logout);
                                            Toast.makeText(SettingsActivity.this, "You are signed-in with Google. Your achievements and scores will be saved automatically.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            startSignInIntent();
             }

                                    }


                                });
            }


        });

        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });





        Switch swit = findViewById(R.id.swith2);
        swit.setChecked(Vibration);
        swit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);

                Boolean sfxb = sfx.getBoolean("sfx", true);

                if (sfxb){

                }
                if (Vibration){

                    isChecked=false;
                    SharedPreferences.Editor editor=getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean(PREF_VIBRATION, isChecked);
                    editor.apply();
                }else {
                    isChecked=true;

                    SharedPreferences.Editor editor=getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean(PREF_VIBRATION, isChecked);
                    editor.apply();
                }
            }
        });









        SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);

        Boolean sfxb = sfx.getBoolean("sfx", true);




        Switch sfxs = findViewById(R.id.swith3);
        sfxs.setChecked(sfxb);

        sfxs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sfxb){
                    isChecked=false;

                    SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
                    SharedPreferences.Editor sfxeditor = sfx.edit();

                    sfxeditor.putBoolean("sfx", false);        // Saving integer
                    sfxeditor.apply();

                }else {
                    isChecked=true;
                    SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);
                    SharedPreferences.Editor sfxeditor = sfx.edit();

                    sfxeditor.putBoolean("sfx", true);        // Saving integer
                    sfxeditor.apply();


                    Boolean sfxb = sfx.getBoolean("sfx", true);

                    if (sfxb){soundPool.play(sound1, 1, 1, 0, 0, 1); }

                }
            }
        });




        SharedPreferences music = getApplicationContext().getSharedPreferences("musicpref", MODE_PRIVATE);
        SharedPreferences.Editor musiced = music.edit();


        Boolean musicb = music.getBoolean("music", true);

        Switch musics = findViewById(R.id.swith4);
        musics.setChecked(musicb);

        musics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);

                Boolean sfxb = sfx.getBoolean("sfx", true);

                if (sfxb){

                }
                if (musicb){
                    isChecked=false;

                    SharedPreferences music = getApplicationContext().getSharedPreferences("musicpref", MODE_PRIVATE);
                    SharedPreferences.Editor musiced = music.edit();

                    musiced.putBoolean("music", false);        // Saving integer
                    musiced.apply();



                }else {
                    isChecked=true;
                    SharedPreferences music = getApplicationContext().getSharedPreferences("musicpref", MODE_PRIVATE);
                    SharedPreferences.Editor musiced = music.edit();

                    musiced.putBoolean("music", true);        // Saving integer
                    musiced.apply();


                }
            }
        });



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savedValue = sharedPreferences.getInt("key", 0);


        RelativeLayout r6 = findViewById(R.id.r6);
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);

                Boolean sfxb = sfx.getBoolean("sfx", true);

                if (sfxb){soundPool.play(sound1, 1, 1, 0, 0, 1); }
                rating();
            }
        });



        RelativeLayout backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    unRevealActivity();
                }
            }
        });







        RelativeLayout r7 = findViewById(R.id.r7);
        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sfx = getApplicationContext().getSharedPreferences("sfxpref", MODE_PRIVATE);

                Boolean sfxb = sfx.getBoolean("sfx", true);

                if (sfxb){soundPool.play(sound1, 1, 1, 0, 0, 1); }
                feedbacks();
            }
        });



        rootLayout = findViewById(R.id.rootlay);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(
                        EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(
                        EXTRA_CIRCULAR_REVEAL_Y)) {

            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);

            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            ViewTreeObserver viewTreeObserver =
                    rootLayout.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override

                            public void onGlobalLayout() {
                                revealActivity(
                                        revealX, revealY);

                                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });
            }
        }
        else {

            rootLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void revealActivity(int x, int y) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

// create the animator for this view (the start radius is zero)

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(
                    new AccelerateInterpolator());

// make the view visible and start the animation

            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        }
        else {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void unRevealActivity() {



        float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
        Animator circularReveal = ViewAnimationUtils.
                createCircularReveal(

                        rootLayout, revealX, revealY, finalRadius, 0);
        circularReveal.setDuration(400);
        circularReveal.addListener(
                new AnimatorListenerAdapter() {
                    @Override

                    public void onAnimationEnd(Animator animation) {

                        rootLayout.setVisibility(View.INVISIBLE);

                        //finish Activity.
                        finish();

                    }
                });
        circularReveal.start();

    }

    private void rating(){
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    private void feedbacks(){
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={"targetappcraft@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Tic Tac Toe Feedbacks");
        intent.putExtra(Intent.EXTRA_CC,"targetappcraft@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    @Override
    public void onBackPressed()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            unRevealActivity();
        }
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


    private void signincheck(){
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // Already signed in.
            //Toast.makeText(this, "already signed in", Toast.LENGTH_SHORT).show();
            ImageView loginimg = findViewById(R.id.loginimg);
            // The signed in account is stored in the 'account' variable.
            GoogleSignInAccount signedInAccount = account;
            Button btnsignout = findViewById(R.id.btnsignout);
            Button btnsignin = findViewById(R.id.btnsignin);
            loginimg.setImageResource(R.drawable.ic_logout);

            btnsignout.setVisibility(View.VISIBLE);
            btnsignin.setVisibility(View.GONE);


        } else {
            Button btnsignin = findViewById(R.id.btnsignin);
            Button btnsignout = findViewById(R.id.btnsignout);
            ImageView loginimg = findViewById(R.id.loginimg);


            btnsignout.setVisibility(View.GONE);
            loginimg.setImageResource(R.drawable.ic_login);
            btnsignin.setVisibility(View.VISIBLE);
        }
    }



    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
                Button btnsignin = findViewById(R.id.btnsignin);
                Button btnsignout = findViewById(R.id.btnsignout);
                ImageView loginimg = findViewById(R.id.loginimg);


                loginimg.setImageResource(R.drawable.ic_logout);
                btnsignout.setVisibility(View.VISIBLE);
                btnsignin.setVisibility(View.GONE);
                Toast.makeText(this, "You are signed-in with Google. Your achievements and scores will be saved automatically.", Toast.LENGTH_SHORT).show();


                SharedPreferences gpg = getApplicationContext().getSharedPreferences("gpg", MODE_PRIVATE);
                SharedPreferences.Editor gpged = gpg.edit();

                gpged.putBoolean("gpgb", true);        // Saving integer
                gpged.apply();



            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = "There was an issue with sign in. Please try again later";
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }


    private void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {

                        Toast.makeText(SettingsActivity.this, "You have successfully signed out.", Toast.LENGTH_SHORT).show();
                        Button btnsignin = findViewById(R.id.btnsignin);
                        Button btnsignout = findViewById(R.id.btnsignout);
                        btnsignout.setVisibility(View.GONE);
                        btnsignin.setVisibility(View.VISIBLE);
                        ImageView loginimg = findViewById(R.id.loginimg);

                        loginimg.setImageResource(R.drawable.ic_login);
                        SharedPreferences gpg = getApplicationContext().getSharedPreferences("gpg", MODE_PRIVATE);
                        SharedPreferences.Editor gpged = gpg.edit();

                        gpged.putBoolean("gpgb", false);        // Saving integer
                        gpged.apply();


                        Boolean gpgb = gpg.getBoolean("gpgb", true);
                    }

                });
    }

}
