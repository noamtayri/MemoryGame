package memorygame.com.memorygame.Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import memorygame.com.memorygame.Model.Record;

public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "records";

    // Contacts table name
    private static final String TABLE_RECORD_DETAIL = "recordsDetails";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //region Override From SQLite
    //creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STUDENT_DETAIL_TABLE = "CREATE TABLE " + TABLE_RECORD_DETAIL + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_LATITUDE + " REAL,"
                + KEY_LONGITUDE + " REAL,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT " + ")";

        db.execSQL(CREATE_STUDENT_DETAIL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD_DETAIL);

        // Create tables again
        onCreate(db);
    }
    //endregion

    //region CRUD Methods
    /**
     * CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Student Information
    void addNewRecord(Record newRec) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, newRec.getName());
        values.put(KEY_LATITUDE, newRec.getLocation().latitude);
        values.put(KEY_LONGITUDE, newRec.getLocation().longitude);
        values.put(KEY_DESCRIPTION, newRec.getRecordDesc());
        values.put(KEY_DATE, newRec.getRecordDate());


        // Inserting Row
        db.insert(TABLE_RECORD_DETAIL, null, values);
        db.close(); // Closing database connection
    }

    public void deleteRecord(int delID){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RECORD_DETAIL, KEY_ID + "=" + delID, null);
        db.close();
    }

    public Record getRecord(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORD_DETAIL,new String[]{KEY_ID,KEY_NAME,KEY_LATITUDE,KEY_LONGITUDE,KEY_DESCRIPTION,KEY_DATE},KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        return new Record(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getDouble(3),cursor.getString(4),cursor.getString(5));

    }

    // Getting All Records
    public List<Record> getAllRecords() {
        List<Record> recordList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECORD_DETAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Record rec = new Record();
                rec.setId(Integer.parseInt(cursor.getString(0)));
                rec.setName(cursor.getString(1));
                rec.setLatitude(Double.parseDouble(cursor.getString(2)));
                rec.setLongitude(Double.parseDouble(cursor.getString(3)));
                rec.setRecordDesc(cursor.getString(4));
                rec.setRecordDate(cursor.getString(5));

                // Adding contact to list
                recordList.add(rec);

            } while (cursor.moveToNext());
        }

        // return contact list
        return recordList;
    }

    //endregion
}
