package memorygame.com.memorygame.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

public class Record {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String recordDate;
    private String recordDesc;

    Calendar cal = Calendar.getInstance();

    //testing constructor
    public Record() {
        this.name = "abc";
        this.recordDate = cal.getTime().toString();
        this.latitude = Math.random() * 50;
        this.longitude = Math.random() * 50;
        this.recordDesc = "medium in 25 seconds";
    }

    public Record(int id, String name, LatLng location, Date recordDate, String recordDesc) {

        this.id = id;
        this.name = name;
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.recordDate = recordDate.toString();
        this.recordDesc = recordDesc;
    }

    public Record(int id, String name, double latitude, double longitude, String recordDesc, String recordDate) {

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recordDate = recordDate;
        this.recordDesc = this.recordDesc;
    }

    public int getId() {
        return id; }

    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public String getRecordDesc() {
        return recordDesc;
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

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }
}
