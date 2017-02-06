package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.business.ListenerProvider;

public class OperationsFragment extends Fragment {

    private TK303GCommands commands;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commands = new TK303GCommands(getContext());
        View view = inflater.inflate(R.layout.fragment_operations, container, false);

        ListenerProvider.emitCommandListener(view, R.id.btn_get_location, commands.getLocation());
        ListenerProvider.emitCommandListener(view, R.id.btn_lock_vehicle, commands.lockVehicle());
        ListenerProvider.emitCommandListener(view, R.id.btn_unlock_vehicle, commands.unlockVehicle());

        return view;
    }

}
