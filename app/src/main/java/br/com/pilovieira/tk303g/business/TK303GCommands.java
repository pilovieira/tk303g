package br.com.pilovieira.tk303g.business;

import android.content.Context;

import br.com.pilovieira.tk303g.persist.Prefs;

public class TK303GCommands {

    private Context context;
    private Prefs prefs;

    public TK303GCommands(Context context) {
        this.context = context;
        this.prefs = new Prefs(context);
    }

    private String getPassword() {
        return prefs.getPassword();
    }

    private String go(String command) {
        return command.replace("#", getPassword());
    }

    public String getLocation() {
        return String.format("tel:%s", prefs.getTrackerNumber());
    }

    public String lockVehicle() {
        return go("stop#");
    }

    public String unlockVehicle() {
        return go("resume#");
    }

    public String begin() {
        return go("begin#");
    }

    public String monitor() {
        return go("monitor#");
    }

    public String tracker() {
        return go("tracker#");
    }

    public String check() {
        return go("check#");
    }

    public String reset() {
        return go("reset#");
    }

    public String changePassword(String oldPass, String newPass) {
        return go(String.format("password%s %s", oldPass, newPass));
    }

    public String authorizeNumber(String number) {
        return go(String.format("admin# %s", number));
    }

    public String deleteNumber(String number) {
        return go(String.format("noadmin# %s", number));
    }

    public String verifyImei() {
        return go("imei#");
    }

    public String timeZone(String timezone) {
        return go(String.format("time zone# %s", timezone));
    }

    public String setAPNName(String apn) {
        return go(String.format("apn# %s", apn));
    }

    public String setAPNUserPass(String user, String pass) {
        return go(String.format("up# %s %s", user, pass));
    }

    public String setIpAndPort(String ip, String port) {
        return go(String.format("adminip# %s %s", ip, port));
    }

    public String activateAcc() {
        return go("acc#");
    }

    public String cancelAcc() {
        return go("noacc#");
    }

    public String activateLowBattery() {
        return go("lowbattery# on");
    }

    public String cancelLowBattery() {
        return go("lowbattery# off");
    }

    public String activateExtPower() {
        return go("extpower# on");
    }

    public String cancelExtPower() {
        return go("extpower# off");
    }

    public String activateGpsSignalAlert() {
        return go("gpssignal# on");
    }

    public String cancelGpsSignalAlert() {
        return go("gpssignal# off");
    }

    public String activateMoveAlarm(String meters) {
        return go(String.format("move# %s", meters));
    }

    public String cancelMoveAlarm() {
        return go("nomove#");
    }

    public String activateSpeedAlarm(String speed) {
        return go(String.format("speed# %s", speed));
    }

    public String cancelSpeedAlarm() {
        return go("nospeed#");
    }

}
