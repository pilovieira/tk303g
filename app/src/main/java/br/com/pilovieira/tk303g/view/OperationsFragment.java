package br.com.pilovieira.tk303g.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import br.com.pilovieira.tk303g.persist.Prefs;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperationsFragment extends Fragment {

    private TK303GCommands commands;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new TK303GCommands(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operations, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnGetLocation)
    public void locationAction(View view) {
        Prefs prefs = new Prefs(getContext());
        String number = prefs.getTrackerNumber();
        if (number.isEmpty()) {
            Snackbar.make(view, R.string.msg_configure_tracker_number, Snackbar.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(commands.getLocation()));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(view, R.string.please_add_call_permission, Snackbar.LENGTH_SHORT).show();
            return;
        }

        startActivity(intent);
    }

    @OnClick(R.id.btnLockVehicle)
    public void lockAction() {
        new SMSEmitter(getContext()).emit(getString(R.string.lock_vehicle), commands.lockVehicle());
    }

    @OnClick(R.id.btnUnlockVehicle)
    public void unlockAction() {
        new SMSEmitter(getContext()).emit(getString(R.string.unlock_vehicle), commands.unlockVehicle());
    }

}
