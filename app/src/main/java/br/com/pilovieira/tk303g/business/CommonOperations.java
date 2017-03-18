package br.com.pilovieira.tk303g.business;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import br.com.pilovieira.tk303g.persist.Prefs;

public class CommonOperations {

    private Context context;
    private TK303GCommands commands;

    public CommonOperations(Context context){
        this.context = context;
        this.commands = new TK303GCommands(context);
    }

    public void locationAction(View view) {
        Prefs prefs = new Prefs(context);
        String number = prefs.getTrackerNumber();
        if (number.isEmpty()) {
            Snackbar.make(view, R.string.msg_configure_tracker_number, Snackbar.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(commands.getLocation()));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(view, R.string.please_add_call_permission, Snackbar.LENGTH_SHORT).show();
            return;
        }

        context.startActivity(intent);
    }

    public void lockAction() {
        new SMSEmitter(context).emit(context.getString(R.string.lock_vehicle), commands.lockVehicle());
    }

    public void unlockAction() {
        new SMSEmitter(context).emit(context.getString(R.string.unlock_vehicle), commands.unlockVehicle());
    }

}
