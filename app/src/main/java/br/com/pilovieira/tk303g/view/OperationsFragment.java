package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.CommonOperations;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperationsFragment extends Fragment {

    private TK303GCommands commands;
    private CommonOperations common;
    private SMSEmitter emitter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new TK303GCommands(getContext());
        common = new CommonOperations(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operations, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnGetLocation)
    public void locationAction(View view) {
        common.locationAction(view);
    }

    @OnClick(R.id.btnLockVehicle)
    public void lockAction() {
        common.lockAction();
    }

    @OnClick(R.id.btnUnlockVehicle)
    public void unlockAction() {
        common.unlockAction();
    }

    @OnClick(R.id.btnMonitor)
    public void monitorAction() {
        emitter.emit(getString(R.string.monitor), commands.monitor());
    }

    @OnClick(R.id.btnTracker)
    public void trackerAction() {
        emitter.emit(getString(R.string.tracker), commands.tracker());
    }

    @OnClick(R.id.btnGprsMode)
    public void gprsModeAction() {
        emitter.emit(getString(R.string.change_to_gprs), commands.gprs());
    }

    @OnClick(R.id.btnSmsMode)
    public void smsModeAction() {
        emitter.emit(getString(R.string.change_to_sms), commands.sms());
    }

    @OnClick(R.id.btnCheckStatus)
    public void checkStatusAction() {
        emitter.emit(getString(R.string.check_status), commands.check());
    }

}
