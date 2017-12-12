package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmOperationsFragment extends Fragment {

    private TK303GCommands commands;
    private SMSEmitter emitter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new TK303GCommands(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_operations, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnActivateAcc)
    public void activateAccAction() {
        emitter.emit(getString(R.string.activate_acc_alarm), commands.activateAcc());
    }

    @OnClick(R.id.btnCancelAcc)
    public void cancelAccAction() {
        emitter.emit(getString(R.string.cancel_acc_alarm), commands.cancelAcc());
    }

    @OnClick(R.id.btnActivateLowBattery)
    public void activateLowBatteryAction() {
        emitter.emit(getString(R.string.activate_low_battery_alarm), commands.activateLowBattery());
    }

    @OnClick(R.id.btnCancelLowBattery)
    public void cancelLowBatteryAction() {
        emitter.emit(getString(R.string.cancel_low_battery_alarm), commands.cancelLowBattery());
    }

    @OnClick(R.id.btnActivateExtPower)
    public void activateExtPowerAction() {
        emitter.emit(getString(R.string.activate_ext_power_alarm), commands.activateExtPower());
    }

    @OnClick(R.id.btnCancelExtPower)
    public void cancelExtPowerAction() {
        emitter.emit(getString(R.string.cancel_ext_power_alarm), commands.cancelExtPower());
    }

    @OnClick(R.id.btnActivateGpsSignalAlert)
    public void activateGpsSignalAction() {
        emitter.emit(getString(R.string.activate_gps_signal_alert), commands.activateGpsSignalAlert());
    }

    @OnClick(R.id.btnCancelGpsSignalAlert)
    public void cancelGpsSignalAlertAction() {
        emitter.emit(getString(R.string.cancel_gps_signal_alert), commands.cancelGpsSignalAlert());
    }

    @OnClick(R.id.btnActivateOverSpeed)
    public void activateOverSpeedAction() {
        ListenerProvider.openDialogOneParam(this, getString(R.string.activate_overspeed_alarm), R.string.speed3Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String speed) {
                emitter.emit(getString(R.string.activate_overspeed_alarm), commands.activateSpeedAlarm(speed));
            }
        });
    }

    @OnClick(R.id.btnCancelOverSpeed)
    public void cancelOverSpeedAction() {
        emitter.emit(getString(R.string.cancel_overspeed_alarm), commands.cancelSpeedAlarm());
    }

    @OnClick(R.id.btnActivateMove)
    public void activateMoveAction() {
        ListenerProvider.openDialogOneParam(this, getString(R.string.activate_move_alarm), R.string.meters4Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String meters) {
                emitter.emit(getString(R.string.activate_move_alarm), commands.activateMoveAlarm(meters));
            }
        });
    }

    @OnClick(R.id.btnCancelMove)
    public void cancelMoveAction() {
        emitter.emit(getString(R.string.cancel_move_alarm), commands.cancelMoveAlarm());
    }

}
