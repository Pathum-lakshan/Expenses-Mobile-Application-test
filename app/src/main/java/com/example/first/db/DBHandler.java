package com.example.first.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.first.model.Customers;
import com.example.first.util.DbBitmapUtility;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION =1;
    private static final String DB_NAME ="Customer";
    private static final String TABLE_NAME ="details";
    private static final String CUSTOMER_ID ="Customer_id";
    private static final String CUSTOMER_NAME ="Customer_name";
    private static final String CUSTOMER_CONTACT ="Customer_contact";
    private static final String CUSTOMER_EMAIL ="Customer_email";
    private static final String CUSTOMER_ADDRESS ="Customer_address";
    private static final String CUSTOMER_GENDER ="Customer_gmail";
    private static final String CUSTOMER_IMAGE ="Customer_image";
    private static final String CUSTOMER_IMAGE_PATH ="Customer_image_path";


    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" "+
                "("+CUSTOMER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CUSTOMER_NAME+" VARCHAR,"+
                CUSTOMER_ADDRESS+" VARCHAR,"+
                CUSTOMER_GENDER+" VARCHAR,"+
                CUSTOMER_IMAGE+" BLOB,"+
                CUSTOMER_IMAGE_PATH+" VARCHAR,"+
                CUSTOMER_CONTACT+"  VARCHAR,"+
                CUSTOMER_EMAIL+"  TEXT"+");");
    }
    public ArrayList<Customers> getAllCustomer() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME+";", null);

        ArrayList<Customers> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {

                courseModalArrayList.add(new Customers(

                        cursorCourses.getInt(0),cursorCourses.getString(1),
                        cursorCourses.getString(2),cursorCourses.getString(7),
                        cursorCourses.getString(6),cursorCourses.getString(3),
                        cursorCourses.getBlob(4),cursorCourses.getString(5)

                        ));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void setCustomerDetail(Customers customer){

        SQLiteDatabase sqLiteDatabase =getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CUSTOMER_NAME,customer.getName());
        contentValues.put(CUSTOMER_CONTACT,customer.getContact());
        contentValues.put(CUSTOMER_EMAIL,customer.getEmail());
        contentValues.put(CUSTOMER_ADDRESS,customer.getAddress());
        contentValues.put(CUSTOMER_GENDER,customer.getGender());
        contentValues.put(CUSTOMER_IMAGE,customer.getPhto());
        contentValues.put(CUSTOMER_IMAGE_PATH,customer.getPath());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

    public boolean deleteCustomer (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean b = db.delete(TABLE_NAME, CUSTOMER_ID + "=" + id, null) > 0;
        db.close();
        return  b;
    }

    public void getCustomer(){

    }

    public boolean updateCustomer(Customers customers){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CUSTOMER_NAME,customers.getName());
        contentValues.put(CUSTOMER_ADDRESS,customers.getAddress());
        contentValues.put(CUSTOMER_EMAIL,customers.getEmail());
        contentValues.put(CUSTOMER_CONTACT,customers.getContact());
        contentValues.put(CUSTOMER_GENDER,customers.getGender());
        contentValues.put(CUSTOMER_IMAGE,customers.getPhto());
        contentValues.put(CUSTOMER_IMAGE_PATH,customers.getPath());

        boolean b = db.update(TABLE_NAME, contentValues, "Customer_id=?", new String[]{String.valueOf(customers.getId())}) > 0;
        db.close();
        return b;
    }

    public long getRowCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();

        return count;
    }




}
