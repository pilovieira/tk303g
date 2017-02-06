package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import br.com.pilovieira.tk303g.persist.Prefs;

public class MainConfigsFragment extends Fragment {

    private TK303GCommands commands;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commands = new TK303GCommands(getContext());
        View view = inflater.inflate(R.layout.fragment_main_configs, container, false);

        resolveTrackerNumberEditText(view);
        resolvePasswordEditText(view);

        mountBtnChangePass(view);
        mountBtnAuthorizeNumber(view);
        mountBtnDeleteNumber(view);

        return view;
    }

    private void resolveTrackerNumberEditText(View view) {
        final Prefs prefs = new Prefs(view.getContext());
        final EditText textView = (EditText) view.findViewById(R.id.text_tracker_number);
        textView.setText(prefs.getTrackerNumber());

        textView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                prefs.setTrackerNumber(((EditText)view).getText().toString());
                return false;
            }
        });
    }

    private void resolvePasswordEditText(View view) {
        final Prefs prefs = new Prefs(view.getContext());
        final EditText textView = (EditText) view.findViewById(R.id.text_password);
        textView.setText(prefs.getPassword());

        textView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                prefs.setPassword(((EditText)view).getText().toString());
                return false;
            }
        });
    }

    private void mountBtnChangePass(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_change_password);

        ListenerProvider.openDialogTwoParam(this, btn, R.string.old_password, R.string.new_password, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String param1Value, String param2Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.changePassword(param1Value, param2Value));
            }
        });
    }

    private void mountBtnAuthorizeNumber(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_authorize);

        ListenerProvider.openDialogOneParam(this, btn, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String param1Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.authorizeNumber(param1Value));
            }
        });
    }

    private void mountBtnDeleteNumber(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_remove_authorize);

        ListenerProvider.openDialogOneParam(this, btn, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String param1Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.deleteNumber(param1Value));
            }
        });
    }

}
