package br.com.pilovieira.tk303g.utils;

import android.os.Bundle;

import androidx.annotation.RequiresPermission;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdUtil{

  @RequiresPermission("android.permission.INTERNET")
  public static void buildAds(AdView adView) {
    Bundle extras = new Bundle();
    extras.putString("max_ad_content_rating", "G");

    AdRequest adRequest = new AdRequest.Builder()
        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
        .build();

    adView.loadAd(adRequest);
  }

}
