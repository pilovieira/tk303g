package br.com.pilovieira.tk303g.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.persist.Prefs;
import br.com.pilovieira.tk303g.utils.LanguageSetter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ParametersFragment extends Fragment {

    private Prefs prefs;

    @BindView(R.id.textTrackerNumber) EditText textTrackerNumber;
    @BindView(R.id.textPassword) EditText textPassword;
    @BindView(R.id.spinnerLanguage) Spinner spinnerLanguage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(getContext());
        prefs = new Prefs(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters, container, false);
        ButterKnife.bind(this, view);

        setTextTrackerNumber();
        setTextPassword();
        configureSpinnerLanguage();

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

}
