package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.pilovieira.tk303g.R;

public class UserManualFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_manual, container, false);
        TextView textUserManual = (TextView) view.findViewById(R.id.text_user_manual);

        textUserManual.setText(getString(R.string.user_manual_literal));

        return view;
    }

}
