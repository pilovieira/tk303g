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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operations, container, false);

        ListenerProvider.locationListener(view.findViewById(R.id.btn_get_location));
        ListenerProvider.lock(view.findViewById(R.id.btn_lock_vehicle));
        ListenerProvider.unlock(view.findViewById(R.id.btn_unlock_vehicle));

        return view;
    }

}
