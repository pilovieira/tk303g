package br.com.pilovieira.tk303g.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import br.com.pilovieira.tk303g.R;

public class BrowserUtil{

  private static final String URL_PROD = "https://appsfuncionais.web.app";

  public static void launchMoreApps(Activity activity){
    launchBrowser(activity, URL_PROD + "/google-play-apps");
  }

  public static void launchPrivacyPolicy(Activity activity){
    launchBrowser(activity, URL_PROD + "/privacy-policy");
  }

  public static void launchBrowser(Activity activity, String url){
    if(activity == null) return;

    try{
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(url));
      activity.startActivity(intent);
    } catch(ActivityNotFoundException ex){
      Toast.makeText(activity, activity.getString(R.string.need_browser_message), Toast.LENGTH_SHORT).show();
    }
  }

}
