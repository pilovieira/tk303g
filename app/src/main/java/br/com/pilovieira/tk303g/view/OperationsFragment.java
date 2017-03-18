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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new TK303GCommands(getContext());
        common = new CommonOperations(getContext());
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
        new SMSEmitter(getContext()).emit(getString(R.string.monitor), commands.monitor());
    }

    @OnClick(R.id.btnTracker)
    public void trackerAction() {
        new SMSEmitter(getContext()).emit(getString(R.string.tracker), commands.tracker());
    }

}
