package br.com.pilovieira.tk303g.business;

import android.content.Context;

import br.com.pilovieira.tk303g.persist.Prefs;

public class TK303GCommands {

    private Context context;

    public TK303GCommands(Context context) {
        this.context = context;
    }

    private String getPassword() {
        return new Prefs(context).getPassword();
    }

    private String go(String command) {
        return command.replace("#", getPassword());
    }

    public String getLocation() {
        return "GOOGLE";
    }

    public String lockVehicle() {
        return go("stop#");
    }

    public String unlockVehicle() {
        return go("resume#");
    }

    public String changePassword(String oldPass, String newPass) {
        return go(String.format("password%s%s", oldPass, newPass));
    }

    public String authorizeNumber(String number) {
        return go(String.format("admin# %s", number));
    }

    public String deleteNumber(String number) {
        return go(String.format("noadmin# %s", number));
    }

    public String firmwareVersion() {
        return "HGT";
    }

    public String trackerInfo() {
        return "CX";
    }

    public String gpsRestart() {
        return "GPSCQ";
    }

    public String trackerRestart() {
        return "SBCQ";
    }

    public String trackerLanguage(int index) {
        return new String[]{"LC", "LE"}[index];
    }

    public String factoryReset() {
        return "RST";
    }

    public String configureAPN(String operator, String user, String pass) {
        return String.format("APN:%s,%s,%s,", operator, user, pass);
    }
    public String configureIp(String ip, String port) {
        return String.format("IP:1# %s#%s#", ip, port);
    }

    public String configureProductRegistration(String pass, String number, String carId) {
        return String.format("ZC%s # %s # %s", pass, number, carId);
    }

    public String deleteAllPairedNumbers() {
        return "DSPN";
    }

    public String viewPairedNumbers() {
        return "FSPN";
    }

    public String clearVirtualFences() {
        return "DRC";
    }

    public String setOverSpeedAlarm(String speed) {
        return String.format("OD%s", speed);
    }
}
