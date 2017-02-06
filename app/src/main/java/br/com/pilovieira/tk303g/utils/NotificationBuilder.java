package br.com.pilovieira.tk303g.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import br.com.pilovieira.tk303g.R;


public class NotificationBuilder {

    private Context context;
    private NotificationManager manager;

    private int id;
    private int icon;
    private String title;
    private String text;
    private Intent result;

    public NotificationBuilder(Context context) {
        this.context = context;
        this.text = context.getString(R.string.notification_default_text);
        this.icon = R.drawable.ic_notification;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setResult(Intent result) {
        this.result = result;
    }

    public void build(){
        NotificationCompat.Builder builder = makeBuilder();
        PendingIntent pendingIntent = makeResultPendingIntent();

        builder.setContentIntent(pendingIntent);
        manager.notify(id, builder.build());
    }

    private PendingIntent makeResultPendingIntent() {
        return result == null ? null : PendingIntent.getActivity(context, id, result, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private NotificationCompat.Builder makeBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        /*if (PrefsManager.isNotificationUseSound(context))
            builder.setSound(PrefsManager.getNotificationRingtone(context));

        if (PrefsManager.isNotificationVibrate(context))
            builder.setDefaults(Notification.DEFAULT_VIBRATE);*/

        builder.setDefaults(Notification.DEFAULT_ALL);

        return builder;
    }
}
