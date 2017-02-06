package br.com.pilovieira.tk303g.business;

import android.content.Context;

import br.com.pilovieira.tk303g.R;

public class MessageIdentifier {

    private Context context;

    public MessageIdentifier(Context context){
        this.context = context;
    }

    public String identify(String message) {
        if (message.contains("maps.google.com"))
            return context.getString(R.string.location);
        return context.getString(R.string.info);
    }
}
