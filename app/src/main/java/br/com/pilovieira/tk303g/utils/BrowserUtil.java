package br.com.pilovieira.tk303g.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import br.com.pilovieira.tk303g.R;

public class BrowserUtil{

  private static final String URL_PROD = "https://appsfuncionais.web.app";
  private static final String SUPPORT_EMAIL = "appsfuncionais@gmail.com";

  public static void launchMoreApps(Activity activity){
    launchBrowser(activity, URL_PROD + "/google-play-apps");
  }

  public static void launchPrivacyPolicy(Activity activity){
    launchBrowser(activity, URL_PROD + "/privacy-policy");
  }

  public static void launchMailToSupport(Activity activity, String appName, String versionName, String email){
    Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
    selectorIntent.setData(Uri.parse("mailto:"));

    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{SUPPORT_EMAIL});
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App " + appName);

    String body = "Dados para facilitar atendimento:\n"
            + "app: " + appName + "\n"
            + "version: " + versionName + "\n"
            + "account: " + email
            + "\n\n"
            + "Qual sua dúvida?:\n\n\n";

    emailIntent.putExtra(Intent.EXTRA_TEXT, body);
    emailIntent.setSelector(selectorIntent);

    activity.startActivity(Intent.createChooser(emailIntent, activity.getString(R.string.send_email_using)));
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
