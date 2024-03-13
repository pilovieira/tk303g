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

}
