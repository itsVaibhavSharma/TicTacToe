package com.targetappcraft.tictactoe.ads;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class admob {

    static ondismis ondismis;

    public admob(com.targetappcraft.tictactoe.ads.ondismis ondismis) {
        this.ondismis = ondismis;
    }

  //  public admob(){}


    public static void setbanner(LinearLayout banner, Context context) {
        if (adsunit.isads) {

            MobileAds.initialize(context, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            AdView adView = new AdView(context);
            banner.addView(adView);
            adView.setAdUnitId(adsunit.BANNER);

            // AdSize adSize = getAdSize();
            adView.setAdSize(AdSize.BANNER);


            AdRequest adRequest =
                    new AdRequest.Builder()
                            .build();

            // Start loading the ad in the background.
            adView.loadAd(adRequest);


        }
    }



        public static void loadint(Context context){

            MobileAds.initialize(context, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(context,adsunit.Interstitial, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            adsunit.mInterstitial = interstitialAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            adsunit.mInterstitial = null;
                        }
                    });

        }

        public void showIntCall(Activity activity, boolean isIntReload){

            if (adsunit.mInterstitial != null) {
                adsunit.mInterstitial.show(activity);

                adsunit.mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();

                        if(isIntReload) {
                            adsunit.mInterstitial = null;
                            admob.loadint(activity);

                        }

                        ondismis.ondismis();
                        }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        ondismis.ondismis();

                    }
                });



            } else {
                //Log.d("TAG", "The interstitial ad wasn't ready yet.");
                ondismis.ondismis();

            }

        }


// rewardedInt ad
    public static void loadintrev(Context context) {

        if (adsunit.isads) {

            MobileAds.initialize(context, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            RewardedInterstitialAd.load(context, adsunit.IntRewarded,
                    new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(RewardedInterstitialAd ad) {
                          //  Log.d(TAG, "Ad was loaded.");
                            adsunit.mRewardedIntAd = ad;
                        }
                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                           // Log.d(TAG, loadAdError.toString());
                            adsunit.mRewardedIntAd = null;
                        }
                    });





        }



    }


    public void showIntRevCall(Activity activity, boolean isReload){

        if (adsunit.mRewardedIntAd != null) {
            adsunit.mRewardedIntAd.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    ondismis.ondismis();
                }
            });


            adsunit.mRewardedIntAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    if(isReload){
                        adsunit.mRewardedIntAd = null;
                        admob.loadintrev(activity);

                    }
                    ondismis.ondismis();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);

                   // adsunit.mInterstitial.show(activity);
                    }
            });



        } else {
            //Log.d("TAG", "The interstitial ad wasn't ready yet.");
            ondismis.ondismis();

        }

    }
 // REWARDED AD CODE

    public static void loadrew(Context context) {

        if (adsunit.isads) {

            MobileAds.initialize(context, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            RewardedAd.load(context, adsunit.Rewarded,
                    new AdRequest.Builder().build(),  new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(RewardedAd ad) {
                            //  Log.d(TAG, "Ad was loaded.");
                            adsunit.mRewardedAd = ad;
                        }
                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            // Log.d(TAG, loadAdError.toString());
                            adsunit.mRewardedAd = null;
                        }
                    });





        }



    }


    public void showRewCall(Activity activity, boolean isReload){

        if (adsunit.mRewardedAd != null) {
            adsunit.mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    //  ondismis.ondismis();
                    //rew
                }
            });


            adsunit.mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    if(isReload){
                        adsunit.mRewardedAd = null;
                      //  admob.loadintrev(activity);

                    }
                  //  ondismis.ondismis();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);

                    //failed to show rew ad
                    // adsunit.mInterstitial.show(activity);
                }
            });



        } else {
            //Log.d("TAG", "The interstitial ad wasn't ready yet.");
            ondismis.ondismis();

        }

    }




}
