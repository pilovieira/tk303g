package br.com.pilovieira.tk303g.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import br.com.pilovieira.tk303g.persist.Prefs;

public class LanguageSetter {

    private static Locale BRAZIL = new Locale("pt", "br");

    public static void refreshLanguage(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        int language = new Prefs(context).getLanguage();
        switch (language) {
            case 0:
                configuration.locale = null;
                break;
            case 1:
                configuration.locale = Locale.ENGLISH;
                break;
            case 2:
                configuration.locale = BRAZIL;
                break;
        }

        resources.updateConfiguration(configuration, null);
    }

}
