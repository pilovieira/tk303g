package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import br.com.pilovieira.tk303g.BuildConfig;
import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.persist.Prefs;
import br.com.pilovieira.tk303g.utils.BrowserUtil;
import br.com.pilovieira.tk303g.utils.LanguageSetter;

public class ParametersFragment extends Fragment {

    private Prefs prefs;
    private boolean loadFragment;

    private EditText textTrackerNumber;
    private EditText textPassword;
    private Spinner spinnerLanguage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(getContext());
        prefs = new Prefs(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters, container, false);

        textTrackerNumber = view.findViewById(R.id.textTrackerNumber);
        textPassword = view.findViewById(R.id.textPassword);
        spinnerLanguage = view.findViewById(R.id.spinnerLanguage);

        FragmentActivity activity = getActivity();
        if (activity != null){
            view.findViewById(R.id.btnPrivacyPolicy).setOnClickListener(v -> BrowserUtil.launchPrivacyPolicy(activity));
            view.findViewById(R.id.btnMoreApps).setOnClickListener(v -> BrowserUtil.launchMoreApps(activity));
            view.findViewById(R.id.btnContact).setOnClickListener(v -> BrowserUtil.launchMailToSupport(activity, getString(R.string.app_name), BuildConfig.VERSION_NAME, prefs.getTrackerNumber()));
        }

        setTextTrackerNumber();
        setTextPassword();
        configureSpinnerLanguage();
        loadFragment = true;

        return view;
    }

    private void setTextTrackerNumber() {
        textTrackerNumber.setText(prefs.getTrackerNumber());
        textTrackerNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                prefs.setTrackerNumber(((EditText)view).getText().toString());
                return false;
            }
        });
    }

    private void setTextPassword() {
        textPassword.setText(prefs.getPassword());
        textPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                prefs.setPassword(((EditText)view).getText().toString());
                return false;
            }
        });
    }

    private void configureSpinnerLanguage() {
        spinnerLanguage.setSelection(prefs.getLanguage());
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.setLanguage(position);

                if (loadFragment)
                    loadFragment = false;
                else
                    Toast.makeText(view.getContext(), R.string.please_restart_app, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

}
