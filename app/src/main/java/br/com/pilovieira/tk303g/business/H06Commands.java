package br.com.pilovieira.tk303g.business;

public class H06Commands {

    public String getLocation() {
        return "GOOGLE";
    }

    public String lockVehicle() {
        return "DO";
    }

    public String unlockVehicle() {
        return "TO";
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

    public String changePassword(String oldPass, String newPass) {
        return String.format("MP%s%s", oldPass, newPass);
    }

    public String pairNumber(String number) {
        return String.format("SPN%s", number);
    }

    public String deleteNumber(String number) {
        return String.format("DSPN%s", number);
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
