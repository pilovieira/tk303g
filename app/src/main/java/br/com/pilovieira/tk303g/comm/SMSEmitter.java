package br.com.pilovieira.tk303g.comm;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.log.ServerLogManager;
import br.com.pilovieira.tk303g.persist.Prefs;

public class SMSEmitter {

    private Context context;
    private Prefs prefs;

    public SMSEmitter(Context context) {
        this.context = context;
        this.prefs = new Prefs(context);
    }

    public void emit(String commandName, String command) {
        try {
            emit(command);

            log(commandName, command);
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

	private void emit(String message) {
        if (prefs.getTrackerNumber().isEmpty())
            throw new RuntimeException(context.getString(R.string.msg_configure_tracker_number));
        if (prefs.getPassword().isEmpty())
            throw new RuntimeException(context.getString(R.string.msg_configure_password));

        SmsManager.getDefault().sendTextMessage(prefs.getTrackerNumber(), null, message, null, null);
	}

    private void log(String commandName, String command) {
        String log = context.getString(R.string.message_command_confirmation, commandName, command.replace(prefs.getPassword(), "%PASS%"));
        new ServerLogManager(context).log(context.getString(R.string.command), log);

        log = context.getString(R.string.message_confirmation, commandName);
        Toast.makeText(context, log, Toast.LENGTH_SHORT).show();
    }

}
