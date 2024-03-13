package br.com.pilovieira.tk303g.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import br.com.pilovieira.tk303g.utils.LanguageSetter;

public class ConfigsFragment extends Fragment {

    private TK303GCommands commands;
    private SMSEmitter emitter;

    private Button btnChangePassword;
    private Button btnAuthorize;
    private Button btnRemoveAuth;
    private Button btnTimeZone;
    private Button btnSetApnName;
    private Button btnSetApnUserPass;
    private Button btnSetIpAndPort;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(getContext());
        commands = new TK303GCommands(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configs, container, false);

        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnAuthorize = view.findViewById(R.id.btnAuthorize);
        btnRemoveAuth = view.findViewById(R.id.btnRemoveAuth);
        btnTimeZone = view.findViewById(R.id.btnTimeZone);
        btnSetApnName = view.findViewById(R.id.btnSetApnName);
        btnSetApnUserPass = view.findViewById(R.id.btnSetApnUserPass);
        btnSetIpAndPort = view.findViewById(R.id.btnSetIpAndPort);

        btnChangePassword.setOnClickListener(v -> btnChangePasswordClicked());
        btnAuthorize.setOnClickListener(v -> btnAuthorizeClicked());
        btnRemoveAuth.setOnClickListener(v -> mountBtnDeleteNumber());
        btnTimeZone.setOnClickListener(v -> btnVerifyImeiAction());
        btnSetApnName.setOnClickListener(v -> btnSetApnNameClicked());
        btnSetApnUserPass.setOnClickListener(v -> btnSetApnUserPassClicked());
        btnSetIpAndPort.setOnClickListener(v -> btnSetIpAndPortClicked());

        view.findViewById(R.id.btnTimeZone).setOnClickListener(v -> btnTimeZoneClicked());
        view.findViewById(R.id.btnRestart).setOnClickListener(v -> restartAction());
        view.findViewById(R.id.btnBegin).setOnClickListener(v -> beginAction());

        return view;
    }

    public void btnChangePasswordClicked() {
        ListenerProvider.openDialogTwoParam(this, btnChangePassword, R.string.old_password, R.string.new_password, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String oldPass, String newPass) {
                emitter.emit(btnChangePassword.getText().toString(), commands.changePassword(oldPass, newPass));
            }
        });
    }

    public void btnAuthorizeClicked() {
        ListenerProvider.openDialogOneParam(this, btnAuthorize, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnAuthorize.getText().toString(), commands.authorizeNumber(number));
            }
        });
    }

    public void mountBtnDeleteNumber() {
        ListenerProvider.openDialogOneParam(this, btnRemoveAuth, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnRemoveAuth.getText().toString(), commands.deleteNumber(number));
            }
        });
    }

    public void btnVerifyImeiAction() {
        emitter.emit(getString(R.string.verify_imei), commands.verifyImei());
    }

    public void btnSetApnNameClicked() {
        ListenerProvider.openDialogOneParam(this, btnSetApnName, R.string.apn_name, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String name) {
                emitter.emit(btnSetApnName.getText().toString(), commands.setAPNName(name));
            }
        });
    }

    public void btnSetApnUserPassClicked() {
        ListenerProvider.openDialogTwoParam(this, btnSetApnUserPass, R.string.user, R.string.pass, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String user, String pass) {
                emitter.emit(btnSetApnUserPass.getText().toString(), commands.setAPNUserPass(user, pass));
            }
        });
    }

    public void btnSetIpAndPortClicked() {
        ListenerProvider.openDialogTwoParam(this, btnSetIpAndPort, R.string.ip, R.string.port, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String ip, String port) {
                emitter.emit(btnSetIpAndPort.getText().toString(), commands.setIpAndPort(ip, port));
            }
        });
    }

    public void btnTimeZoneClicked() {
        ListenerProvider.openDialogOneParam(this, btnTimeZone, R.string.time_zone, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String timezone) {
                emitter.emit(btnTimeZone.getText().toString(), commands.timeZone(timezone));
            }
        });
    }

    public void restartAction() {
        emitter.emit(getString(R.string.restart_tracker), commands.reset());
    }

    public void beginAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.are_you_sure);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                emitter.emit(getString(R.string.factory_reset_begin), commands.begin());
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

}
