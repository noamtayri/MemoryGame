package memorygame.com.memorygame.Model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class Record {
    private int id, recordPoints;
    private String name, address, level;
    private double latitude, longitude;

    //testing constructor
    public Record(){

    }

    public Record(String name, double latitude, double longitude, int recordPoints, String address, String level) {

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recordPoints = recordPoints;
        this.address = address;
        this.level = level;
    }

    public Record(int id, String name, double latitude, double longitude, int recordPoints, String address, String level) {

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recordPoints = recordPoints;
        this.address = address;
        this.level = level;
    }

    public int getId() {
        return id; }

    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public int getRecordPoints() {
        return recordPoints;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecordPoints(int recordPoints) {
        this.recordPoints = recordPoints;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
