package memorygame.com.memorygame.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

public class Record {
    private LatLng location;
    private String username;
    private Date recordDate;
    private String theRecord___;

    Calendar cal = Calendar.getInstance();

    //testing constructor
    public Record() {
        this.username = "abc";
        this.recordDate = cal.getTime();
        this.location = new LatLng(Math.random() * 100, Math.random() * 100);
        this.theRecord___ = "Taps: medium in 25 seconds";
    }

    public Record(LatLng location,String username, Date recordDate, String theRecord) {
        this.location = location;
        this.username = username;
        this.recordDate = recordDate;
        this.theRecord___ = theRecord;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getUsername() {
        return username;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public String getTheRecord___() {
        return theRecord___;
    }
}
