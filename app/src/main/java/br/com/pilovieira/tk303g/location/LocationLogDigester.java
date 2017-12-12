package br.com.pilovieira.tk303g.location;

import java.io.BufferedReader;
import java.io.StringReader;

import br.com.pilovieira.tk303g.log.ServerLog;

public class LocationLogDigester {

    private ServerLog log;
    private String lat;
    private String lng;
    private String speed;
    private String time;
    private String power;
    private String door;
    private String acc;

    public LocationLogDigester(ServerLog log) {
        this.log = log;
        digest();
    }

    private void digest() {
        try {
            StringReader sr = new StringReader(log.getMessage());
            BufferedReader br = new BufferedReader(sr);

            String line = br.readLine();
            while (line != null) {
                try {
                    processLine(line);
                } catch (Exception ex) {}
                line = br.readLine();
            }
            br.close();
            sr.close();
        } catch (Exception ex) {}
    }

    private void processLine(String line) {
        if (line == null)
            return;
        if (line.contains("lat:"))
            lat = line.trim().split(" ")[0].split(":")[1];
        if (line.contains("lon:"))
            lng = line.trim().split("lon:")[1];
        if (line.contains("long:"))
            lng = line.trim().split("long:")[1];
        if (line.contains("speed:"))
            speed = line.trim().split(":")[1];
        if (line.contains("T:"))
            time = line.trim().split("T:")[1];
        if (line.contains("Pwr:") && line.contains("Door:") && line.contains("ACC:")) {
            String[] split = line.trim().split(" "); //Pwr: ON Door: OFF ACC: OFF
            power = split[1];
            door = split[3];
            acc = split[5];
        }
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getSpeed() {
        return speed;
    }

    public String getTime() {
        return time;
    }

    public String getPower() {
        return power;
    }

    public String getDoor() {
        return door;
    }

    public String getAcc() {
        return acc;
    }
}
