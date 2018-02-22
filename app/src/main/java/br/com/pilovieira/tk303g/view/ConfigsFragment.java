package br.com.pilovieira.tk303g.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.comm.SMSEmitter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigsFragment extends Fragment {

    private TK303GCommands commands;
    private SMSEmitter emitter;

    @BindView(R.id.btnChangePassword) Button btnChangePassword;
    @BindView(R.id.btnAuthorize) Button btnAuthorize;
    @BindView(R.id.btnRemoveAuth) Button btnRemoveAuth;
    @BindView(R.id.btnTimeZone) Button btnTimeZone;
    @BindView(R.id.btnSetApnName) Button btnSetApnName;
    @BindView(R.id.btnSetApnUserPass) Button btnSetApnUserPass;
    @BindView(R.id.btnSetIpAndPort) Button btnSetIpAndPort;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new TK303GCommands(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configs, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnChangePassword)
    public void btnChangePasswordClicked() {
        ListenerProvider.openDialogTwoParam(this, btnChangePassword, R.string.old_password, R.string.new_password, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String oldPass, String newPass) {
                emitter.emit(btnChangePassword.getText().toString(), commands.changePassword(oldPass, newPass));
            }
        });
    }

    @OnClick(R.id.btnAuthorize)
    public void btnAuthorizeClicked() {
        ListenerProvider.openDialogOneParam(this, btnAuthorize, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnAuthorize.getText().toString(), commands.authorizeNumber(number));
            }
        });
    }

    @OnClick(R.id.btnRemoveAuth)
    public void mountBtnDeleteNumber() {
        ListenerProvider.openDialogOneParam(this, btnRemoveAuth, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnRemoveAuth.getText().toString(), commands.deleteNumber(number));
            }
        });
    }

    @OnClick(R.id.btnVerifyImei)
    public void btnVerifyImeiAction() {
        emitter.emit(getString(R.string.verify_imei), commands.verifyImei());
    }

    @OnClick(R.id.btnSetApnName)
    public void btnSetApnNameClicked() {
        ListenerProvider.openDialogOneParam(this, btnSetApnName, R.string.apn_name, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String name) {
                emitter.emit(btnSetApnName.getText().toString(), commands.setAPNName(name));
            }
        });
    }

    @OnClick(R.id.btnSetApnUserPass)
    public void btnSetApnUserPassClicked() {
        ListenerProvider.openDialogTwoParam(this, btnSetApnUserPass, R.string.user, R.string.pass, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String user, String pass) {
                emitter.emit(btnSetApnUserPass.getText().toString(), commands.setAPNUserPass(user, pass));
            }
        });
    }

    @OnClick(R.id.btnSetIpAndPort)
    public void btnSetIpAndPortClicked() {
        ListenerProvider.openDialogTwoParam(this, btnSetIpAndPort, R.string.ip, R.string.port, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String ip, String port) {
                emitter.emit(btnSetIpAndPort.getText().toString(), commands.setIpAndPort(ip, port));
            }
        });
    }

    @OnClick(R.id.btnTimeZone)
    public void btnTimeZoneClicked() {
        ListenerProvider.openDialogOneParam(this, btnTimeZone, R.string.time_zone, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String timezone) {
                emitter.emit(btnTimeZone.getText().toString(), commands.timeZone(timezone));
            }
        });
    }

    @OnClick(R.id.btnRestart)
    public void restartAction() {
        emitter.emit(getString(R.string.restart_tracker), commands.reset());
    }

    @OnClick(R.id.btnBegin)
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
