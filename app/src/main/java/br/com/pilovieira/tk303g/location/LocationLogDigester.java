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
        if (line.contains("speed:"))
            speed = line.trim().split(":")[1];
        if (line.contains("T:"))
            time = line.trim().split("T:")[1];
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
}
