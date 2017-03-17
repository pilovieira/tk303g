package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class AdvancedConfigsFragment extends Fragment {

    private TK303GCommands commands;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new TK303GCommands(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_configs, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnBegin)
    public void beginAction() {
        new SMSEmitter(getContext()).emit(getString(R.string.lock_vehicle), commands.begin());
    }

/*    private void mountBtnConfigureProductRegistration(View view) {
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
    }*/

}
