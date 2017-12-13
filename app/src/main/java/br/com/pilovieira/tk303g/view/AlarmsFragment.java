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

public class AlarmsFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_alarms, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnActivateAcc)
    public void activateAccAction() {
        emitter.emit(activateMessage(R.string.acc), commands.activateAcc());
    }

    @OnClick(R.id.btnCancelAcc)
    public void cancelAccAction() {
        emitter.emit(cancelMessage(R.string.acc), commands.cancelAcc());
    }

    @OnClick(R.id.btnActivateLowBattery)
    public void activateLowBatteryAction() {
        emitter.emit(activateMessage(R.string.low_battery), commands.activateLowBattery());
    }

    @OnClick(R.id.btnCancelLowBattery)
    public void cancelLowBatteryAction() {
        emitter.emit(cancelMessage(R.string.low_battery), commands.cancelLowBattery());
    }

    @OnClick(R.id.btnActivateExtPower)
    public void activateExtPowerAction() {
        emitter.emit(activateMessage(R.string.ext_power), commands.activateExtPower());
    }

    @OnClick(R.id.btnCancelExtPower)
    public void cancelExtPowerAction() {
        emitter.emit(cancelMessage(R.string.ext_power), commands.cancelExtPower());
    }

    @OnClick(R.id.btnActivateGpsSignalAlert)
    public void activateGpsSignalAction() {
        emitter.emit(activateMessage(R.string.gps_signal), commands.activateGpsSignalAlert());
    }

    @OnClick(R.id.btnCancelGpsSignalAlert)
    public void cancelGpsSignalAlertAction() {
        emitter.emit(cancelMessage(R.string.gps_signal), commands.cancelGpsSignalAlert());
    }

    @OnClick(R.id.btnActivateOverSpeed)
    public void activateOverSpeedAction() {
        ListenerProvider.openDialogOneParam(this, getString(R.string.overspeed), R.string.speed3Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String speed) {
                emitter.emit(activateMessage(R.string.overspeed), commands.activateSpeedAlarm(speed));
            }
        });
    }

    @OnClick(R.id.btnCancelOverSpeed)
    public void cancelOverSpeedAction() {
        emitter.emit(cancelMessage(R.string.overspeed), commands.cancelSpeedAlarm());
    }

    @OnClick(R.id.btnActivateMove)
    public void activateMoveAction() {
        ListenerProvider.openDialogOneParam(this, getString(R.string.move), R.string.meters4Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String meters) {
                emitter.emit(activateMessage(R.string.move), commands.activateMoveAlarm(meters));
            }
        });
    }

    @OnClick(R.id.btnCancelMove)
    public void cancelMoveAction() {
        emitter.emit(cancelMessage(R.string.move), commands.cancelMoveAlarm());
    }

    private String activateMessage(int id) {
        return getString(R.string.activate) + " " + getString(id) + " " + getString(R.string.alarm);
    }

    private String cancelMessage(int id) {
        return getString(R.string.cancel) + " " + getString(id) + " " + getString(R.string.alarm);
    }

}
