package com.quocngay.carparkbooking.model;

import android.util.Log;

import com.quocngay.carparkbooking.dbcontext.DbContext;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ninhh on 5/24/2017.
 */

public class GaraModel extends RealmObject {
    public static String KEY_SERVER_ID = "id";
    public static String KEY_SERVER_NAME = "name";
    public static String KEY_SERVER_ADDRESS = "address";
    public static String KEY_SERVER_PICTURE = "picture";
    public static String KEY_SERVER_TOTAL = "total_slot";
    public static String KEY_SERVER_BUSY = "busy_slot";
    public static String KEY_SERVER_BOOKING = "booking_slot";
    public static String KEY_SERVER_LOCATION_X = "location_x";
    public static String KEY_SERVER_LOCATION_Y = "location_y";
    public static String KEY_SERVER_LOCATION_Z = "location_z";
    private static String TAG = GaraModel.class.getSimpleName();
    
    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private String picture;
    private int totalSlot;
    private int busySlot;
    private int bookedSlot;
    private String locationX;
    private String locationY;
    private String locationZ;

    public static GaraModel create(int id, String name, String address, String picture, int totalSlot, int busySlot, int bookedSlot,
                     String locationX, String locationY, String locationZ) {
        GaraModel garaModel = new GaraModel();
        garaModel.id = id;
        garaModel.name = name;
        garaModel.address = address;
        garaModel.picture = picture;
        garaModel.totalSlot = totalSlot;
        garaModel.busySlot = busySlot;
        garaModel.bookedSlot = bookedSlot;
        garaModel.locationX = locationX;
        garaModel.locationY = locationY;
        garaModel.locationZ = locationZ;
        return garaModel;
    }

    public static GaraModel createwithoutId(String name, String address, String picture, int totalSlot, int busySlot, int bookedSlot,
                                   String locationX, String locationY, String locationZ) {
        GaraModel garaModel = new GaraModel();
        DbContext dbContext = DbContext.getInst();
        garaModel.id = dbContext.getMaxGaraModelId() + 1;
        garaModel.name = name;
        garaModel.address = address;
        garaModel.picture = picture;
        garaModel.totalSlot = totalSlot;
        garaModel.busySlot = busySlot;
        garaModel.bookedSlot = bookedSlot;
        garaModel.locationX = locationX;
        garaModel.locationY = locationY;
        garaModel.locationZ = locationZ;
        return garaModel;
    }

    public static GaraModel createByJson(JSONObject obj) {
        GaraModel garaModel = new GaraModel();
        if(obj != null) {
            try{
                garaModel.id = obj.getInt(KEY_SERVER_ID);
                garaModel.name = obj.getString(KEY_SERVER_NAME);
                garaModel.address = obj.getString(KEY_SERVER_ADDRESS);
                garaModel.picture = obj.getString(KEY_SERVER_PICTURE);
                garaModel.totalSlot = obj.getInt(KEY_SERVER_TOTAL);
                garaModel.busySlot = obj.getInt(KEY_SERVER_BUSY);
                garaModel.bookedSlot = obj.getInt(KEY_SERVER_BOOKING);
                garaModel.locationX = obj.getString(KEY_SERVER_LOCATION_X);
                garaModel.locationY = obj.getString(KEY_SERVER_LOCATION_Y);
                garaModel.locationZ = obj.getString(KEY_SERVER_LOCATION_Z);
            } catch(JSONException ex) {
                Log.e(TAG, "Error while get json attribute: " + ex.getMessage());
            }
        }
        return garaModel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setTotalSlot(int totalSlot) {
        this.totalSlot = totalSlot;
    }

    public void setBusySlot(int busySlot) {
        this.busySlot = busySlot;
    }

    public void setBookedSlot(int bookedSlot) {
        this.bookedSlot = bookedSlot;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public void setLocationZ(String locationZ) {
        this.locationZ = locationZ;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPicture() {
        return picture;
    }

    public int getTotalSlot() {
        return totalSlot;
    }

    public int getBusySlot() {
        return busySlot;
    }

    public int getBookedSlot() {
        return bookedSlot;
    }

    public String getLocationX() {
        return locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public String getLocationZ() {
        return locationZ;
    }
}
