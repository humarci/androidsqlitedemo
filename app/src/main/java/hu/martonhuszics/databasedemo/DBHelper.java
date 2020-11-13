package hu.martonhuszics.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACT_TABLE_NAME = "contacts";
    public static final String CONTACT_COLUMN_ID = "id";
    public static final String CONTACT_COLUMN_NAME = "name";
    public static final String CONTACT_COLUMN_EMAIL = "email";
    public static final String CONTACT_COLUMN_STREET = "street";
    public static final String CONTACT_COLUMN_CITY = "place";
    public static final String CONTACT_COLUMN_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CONTACT_TABLE_NAME + "(" +
                CONTACT_COLUMN_ID + " integer primary key, " +
                CONTACT_COLUMN_NAME + " text, " +
                CONTACT_COLUMN_EMAIL + " text, " +
                CONTACT_COLUMN_STREET + " text, " +
                CONTACT_COLUMN_CITY + " text, " +
                CONTACT_COLUMN_PHONE + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME);
    }

    public boolean insertContact (String name, String email, String street, String city, String phone) {
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_COLUMN_NAME, name);
        contentValues.put(CONTACT_COLUMN_EMAIL, email);
        contentValues.put(CONTACT_COLUMN_STREET, street);
        contentValues.put(CONTACT_COLUMN_CITY, city);
        contentValues.put(CONTACT_COLUMN_PHONE, phone);
        db.insert(CONTACT_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CONTACT_TABLE_NAME + " where id="+id+"", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACT_TABLE_NAME);
        return  numRows;
    }

    public boolean updateContact (Integer id, String name, String email, String street, String city, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_COLUMN_NAME, name);
        contentValues.put(CONTACT_COLUMN_EMAIL, email);
        contentValues.put(CONTACT_COLUMN_STREET, street);
        contentValues.put(CONTACT_COLUMN_CITY, city);
        contentValues.put(CONTACT_COLUMN_PHONE, phone);
        db.update(CONTACT_TABLE_NAME, contentValues, "id = ?", new String[] { Integer.toString(id)} );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CONTACT_TABLE_NAME, "id = ?", new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllContacts() {
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CONTACT_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(CONTACT_COLUMN_NAME)));
            res.moveToNext();
        }

        return arrayList;
    }

}
