package br.com.pilovieira.tk303g.log;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.utils.LanguageSetter;

public class InfoFragment extends Fragment {

    private View view;
    private ListView logList;
    private Button btnClear;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_info, container, false);

        logList = view.findViewById(R.id.listLog);
        btnClear = view.findViewById(R.id.btnLogClear);
        btnClear.setOnClickListener(v -> logClearClick());

        mountLogsList();

        return view;
    }

    private void mountLogsList() {
        List<ServerLog> logs = new ServerLogManager(getContext()).getLogs();
        logList.setAdapter(new LogListAdapter(getContext(), logs));
    }

    public void logClearClick() {
        new ServerLogManager(view.getContext()).clearLogs();
        mountLogsList();
    }

}
