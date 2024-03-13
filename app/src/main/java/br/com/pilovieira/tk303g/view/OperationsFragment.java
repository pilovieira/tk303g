package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.CommonOperations;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import br.com.pilovieira.tk303g.utils.LanguageSetter;

public class OperationsFragment extends Fragment {

    private TK303GCommands commands;
    private CommonOperations common;
    private SMSEmitter emitter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(getContext());
        commands = new TK303GCommands(getContext());
        common = new CommonOperations(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operations, container, false);

        view.findViewById(R.id.btnGetLocation).setOnClickListener(v -> locationAction(v));
        view.findViewById(R.id.btnLockVehicle).setOnClickListener(v -> lockAction());
        view.findViewById(R.id.btnUnlockVehicle).setOnClickListener(v -> unlockAction());
        view.findViewById(R.id.btnMonitor).setOnClickListener(v -> monitorAction());
        view.findViewById(R.id.btnTracker).setOnClickListener(v -> trackerAction());
        view.findViewById(R.id.btnGprsMode).setOnClickListener(v -> gprsModeAction());
        view.findViewById(R.id.btnSmsMode).setOnClickListener(v -> smsModeAction());
        view.findViewById(R.id.btnCheckStatus).setOnClickListener(v -> checkStatusAction());
        view.findViewById(R.id.btnActivateAutoTrack).setOnClickListener(v -> activateAutoTrackAction());
        view.findViewById(R.id.btnCancelAutoTrack).setOnClickListener(v -> cancelAutoTrackAction());

        view.findViewById(R.id.btnActivateAcc).setOnClickListener(v -> activateAccAction());
        view.findViewById(R.id.btnCancelAcc).setOnClickListener(v -> cancelAccAction());
        view.findViewById(R.id.btnActivateLowBattery).setOnClickListener(v -> activateLowBatteryAction());
        view.findViewById(R.id.btnCancelLowBattery).setOnClickListener(v -> cancelLowBatteryAction());
        view.findViewById(R.id.btnActivateExtPower).setOnClickListener(v -> activateExtPowerAction());
        view.findViewById(R.id.btnCancelExtPower).setOnClickListener(v -> cancelExtPowerAction());
        view.findViewById(R.id.btnActivateGpsSignalAlert).setOnClickListener(v -> activateGpsSignalAction());
        view.findViewById(R.id.btnCancelGpsSignalAlert).setOnClickListener(v -> cancelGpsSignalAlertAction());
        view.findViewById(R.id.btnActivateOverSpeed).setOnClickListener(v -> activateOverSpeedAction());
        view.findViewById(R.id.btnCancelOverSpeed).setOnClickListener(v -> cancelOverSpeedAction());
        view.findViewById(R.id.btnActivateMove).setOnClickListener(v -> activateMoveAction());
        view.findViewById(R.id.btnCancelMove).setOnClickListener(v -> cancelMoveAction());

        return view;
    }

    public void locationAction(View view) {
        common.locationAction();
    }

    public void lockAction() {
        common.lockAction();
    }

    public void unlockAction() {
        common.unlockAction();
    }

    public void monitorAction() {
        emitter.emit(getString(R.string.monitor), commands.monitor());
    }

    public void trackerAction() {
        emitter.emit(getString(R.string.tracker), commands.tracker());
    }

    public void gprsModeAction() {
        emitter.emit(getString(R.string.change_to_gprs), commands.gprs());
    }

    public void smsModeAction() {
        emitter.emit(getString(R.string.change_to_sms), commands.sms());
    }

    public void checkStatusAction() {
        emitter.emit(getString(R.string.check_status), commands.check());
    }

    public void activateAutoTrackAction() {
        ListenerProvider.openDialogTwoParam(this, getString(R.string.activate_auto_track), R.string.interval_minutes, R.string.times_3_digits, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String intervalMinutes, String times) {
                emitter.emit(getString(R.string.activate_auto_track), commands.activateAutoTrack(intervalMinutes, times));
            }
        });
    }

    public void cancelAutoTrackAction() {
        emitter.emit(getString(R.string.cancel_auto_track), commands.cancelAutoTrack());
    }

    public void activateAccAction() {
        emitter.emit(activateMessage(R.string.acc), commands.activateAcc());
    }

    public void cancelAccAction() {
        emitter.emit(cancelMessage(R.string.acc), commands.cancelAcc());
    }

    public void activateLowBatteryAction() {
        emitter.emit(activateMessage(R.string.low_battery), commands.activateLowBattery());
    }

    public void cancelLowBatteryAction() {
        emitter.emit(cancelMessage(R.string.low_battery), commands.cancelLowBattery());
    }

    public void activateExtPowerAction() {
        emitter.emit(activateMessage(R.string.ext_power), commands.activateExtPower());
    }

    public void cancelExtPowerAction() {
        emitter.emit(cancelMessage(R.string.ext_power), commands.cancelExtPower());
    }

    public void activateGpsSignalAction() {
        emitter.emit(activateMessage(R.string.gps_signal), commands.activateGpsSignalAlert());
    }

    public void cancelGpsSignalAlertAction() {
        emitter.emit(cancelMessage(R.string.gps_signal), commands.cancelGpsSignalAlert());
    }

    public void activateOverSpeedAction() {
        ListenerProvider.openDialogOneParam(this, getString(R.string.overspeed), R.string.speed3Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String speed) {
                emitter.emit(activateMessage(R.string.overspeed), commands.activateSpeedAlarm(speed));
            }
        });
    }

    public void cancelOverSpeedAction() {
        emitter.emit(cancelMessage(R.string.overspeed), commands.cancelSpeedAlarm());
    }

    public void activateMoveAction() {
        ListenerProvider.openDialogOneParam(this, getString(R.string.move), R.string.meters4Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String meters) {
                emitter.emit(activateMessage(R.string.move), commands.activateMoveAlarm(meters));
            }
        });
    }

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
