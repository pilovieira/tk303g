package br.com.pilovieira.tk303g.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.H06Commands;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import br.com.pilovieira.tk303g.persist.Prefs;

public class ConfigurationsFragment extends Fragment {

    private H06Commands commands = new H06Commands();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configurations, container, false);

        resolveTrackerNumberEditText(view);

        mountBtnSetLanguage(view);
        ListenerProvider.emitCommandListener(view, R.id.btn_factory_reset, commands.factoryReset());
        mountBtnConfigureAPN(view);
        mountBtnConfigureIp(view);
        mountBtnConfigureProductRegistration(view);
        mountBtnChangePass(view);

        mountBtnPairNumber(view);
        mountBtnDeleteNumber(view);
        ListenerProvider.emitCommandListener(view, R.id.btn_delete_all_numbers, commands.deleteAllPairedNumbers());
        ListenerProvider.emitCommandListener(view, R.id.btn_view_paired_numbers, commands.viewPairedNumbers());

        //ListenerProvider.emitCommandListener(view, R.id.btn_clear_virtual_fence, commands.clearVirtualFences());
        mountBtnOverSpeedAlarm(view);

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

    private void mountBtnSetLanguage(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_set_language);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setSingleChoiceItems(new String[]{getString(R.string.chinese), getString(R.string.english)}, 0, null);
                builder.setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListView lw = ((AlertDialog)dialogInterface).getListView();
                        if (lw.getCheckedItemPosition() != -1) {
                            String command = new H06Commands().trackerLanguage(lw.getCheckedItemPosition());
                            new SMSEmitter(view.getContext()).emit(btn.getText().toString(), command);
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
    }

    private void mountBtnPairNumber(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_pair_number);

        ListenerProvider.openDialogOneParam(this, btn, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String param1Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.pairNumber(param1Value));
            }
        });
    }

    private void mountBtnDeleteNumber(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_delete_number);

        ListenerProvider.openDialogOneParam(this, btn, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String param1Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.deleteNumber(param1Value));
            }
        });
    }

    private void mountBtnConfigureAPN(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_set_apn);

        ListenerProvider.openDialogThreeParam(this, btn, R.string.operator, R.string.username, R.string.password, new ListenerProvider.CommandThreeParam() {
            @Override
            public void apply(String param1Value, String param2Value, String param3Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.configureAPN(param1Value, param2Value, param3Value));
            }
        });
    }

    private void mountBtnConfigureIp(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_set_ip);

        ListenerProvider.openDialogTwoParam(this, btn, R.string.ip, R.string.port, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String param1Value, String param2Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.configureIp(param1Value, param2Value));
            }
        });
    }

    private void mountBtnConfigureProductRegistration(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_product_registration);

        ListenerProvider.openDialogThreeParam(this, btn, R.string.password, R.string.phone_number, R.string.car_station_id, new ListenerProvider.CommandThreeParam() {
            @Override
            public void apply(String param1Value, String param2Value, String param3Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.configureProductRegistration(param1Value, param2Value, param3Value));
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

    private void mountBtnOverSpeedAlarm(View view) {
        final Button btn = (Button) view.findViewById(R.id.btn_set_over_speed_alarm);

        ListenerProvider.openDialogOneParam(this, btn, R.string.over_speed_range, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String param1Value) {
                new SMSEmitter(btn.getContext()).emit(btn.getText().toString(), commands.setOverSpeedAlarm(param1Value));
            }
        });
    }

}
