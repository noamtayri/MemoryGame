package memorygame.com.memorygame.Model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class Record {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private int recordPoints;

    Calendar cal = Calendar.getInstance();

    //testing constructor
    public Record(){

    }

    public Record(Context context) {
        this.name = "abc";
        this.latitude = Math.random() * 100;
        this.longitude = Math.random() * 100;
        this.recordPoints = (int)(Math.random() * 50);
        this.address = "raanana";
    }

    public Record(String name, double latitude, double longitude, int recordPoints, String address) {

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recordPoints = recordPoints;
        this.address = address;
    }

    public Record(int id, String name, double latitude, double longitude, int recordPoints, String address) {

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recordPoints = recordPoints;
        this.address = address;
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
}
