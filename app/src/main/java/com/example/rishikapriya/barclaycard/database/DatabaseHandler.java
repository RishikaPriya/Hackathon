package com.example.rishikapriya.barclaycard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishikapriya on 25/11/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notificationManager";

    private static final String TABLE_NOTIFICATION = "notification";

    // Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LINK = "link";
    private static final String KEY_VALIDITY_DATE = "validity_date";
    private static final String KEY_CATEGORY = "category";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_NOTIFICATION + "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " TEXT , "
                + KEY_DESCRIPTION + " TEXT , " + KEY_LINK + " TEXT, "
                + KEY_VALIDITY_DATE + " TEXT , " + KEY_CATEGORY + " TEXT "+")";
        CommonUtils.debug("create_query",CREATE_NOTIFICATION_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        onCreate(db);
    }

    public void addNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, notification.getTitle());
        values.put(KEY_DESCRIPTION, notification.getDescription());
        values.put(KEY_LINK, notification.getLink());
        values.put(KEY_VALIDITY_DATE, notification.getValidity_date());
        values.put(KEY_CATEGORY, notification.getCategory());

        db.insertWithOnConflict(TABLE_NOTIFICATION, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public Notification getNotification(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[] { KEY_ID,
                        KEY_TITLE, KEY_DESCRIPTION, KEY_LINK, KEY_VALIDITY_DATE, KEY_CATEGORY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Notification notification = new Notification(cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return notification;
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notificationList = new ArrayList<Notification>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5));

                notificationList.add(notification);
            } while (cursor.moveToNext());
        }

        return notificationList;
    }

    public List<Notification> getAllNotificationsForCategory(String category) {
        List<Notification> notificationList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION  + " WHERE " + KEY_CATEGORY + " = '" + category + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5));

                notificationList.add(notification);
            } while (cursor.moveToNext());
        }

        return notificationList;
    }

    public int getNotificationCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, notification.getTitle());
        values.put(KEY_DESCRIPTION, notification.getDescription());
        values.put(KEY_LINK, notification.getLink());
        values.put(KEY_VALIDITY_DATE, notification.getValidity_date());
        values.put(KEY_CATEGORY, notification.getCategory());

        return db.update(TABLE_NOTIFICATION, values, KEY_TITLE + " = ?",
                new String[] { notification.getTitle() });

    }

    public void deleteNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATION, KEY_TITLE + " = ?",
                new String[] { notification.getTitle() });
        db.close();
    }

    public List<String> getDistinctCategory(){
        List<String> categoryList = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT "+ KEY_CATEGORY +" FROM " + TABLE_NOTIFICATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(0);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        return categoryList;
    }
}
