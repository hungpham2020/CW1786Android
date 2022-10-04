package com.example.comp1786cw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import entites.Expenses;
import entites.Trip;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TripSaver";
    private static final String TABLE_TRIP = "Trips";
    private static final String TABLE_EXPENSE = "Expenses";

    private static final String EXPENSES_ID = "expense_id";
    private static final String EXPENSES_TYPE = "expense_type";
    private static final String EXPENSES_AMOUNT = "expense_amount";
    private static final String EXPENSES_DATE = "expense_date";
    private static final String EXPENSES_COMMENT = "expense_comment";
    private static final String EXPENSES_TRIP_ID = "expense_tripId";

    public static final String TRIP_ID = "trip_id";
    public static final String NAME = "name";
    public static final String DESTINATION = "destination";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final String DURATION = "duration";
    public static final String RISK_ASSESSMENT = "risk_assessment";

    private SQLiteDatabase database;

    private static final String TABLE_TRIP_CREATE = String.format(
      "CREATE TABLE %s (" +
      "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
      "   %s TEXT, " +
      "   %s TEXT, " +
      "   %s TEXT, " +
      "   %s TEXT, " +
      "   %s INTEGER, " +
      "   %s NUMERIC)",
      TABLE_TRIP, TRIP_ID, NAME, DESTINATION, DATE, DESCRIPTION, DURATION, RISK_ASSESSMENT);

    private static final String TABLE_EXPENSE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s INTEGER, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s INTEGER, " +
//                    "   CONSTRAINT fk_trips " +
                    "   FOREIGN KEY(" + EXPENSES_TRIP_ID +  ") " +
                    "   REFERENCES " + TABLE_TRIP + "(" + TRIP_ID + "))",
            TABLE_EXPENSE, EXPENSES_ID, EXPENSES_TYPE, EXPENSES_AMOUNT, EXPENSES_DATE, EXPENSES_COMMENT, EXPENSES_TRIP_ID);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TRIP_CREATE);
        db.execSQL(TABLE_EXPENSE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);

        Log.v(this.getClass().getName(), TABLE_TRIP + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    public long insertTrip(String name, String destination, String date, String description, int duration, boolean riskAssessment) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME, name);
        rowValues.put(DESTINATION, destination);
        rowValues.put(DATE, date);
        rowValues.put(DESCRIPTION, description);
        rowValues.put(DURATION, duration);
        rowValues.put(RISK_ASSESSMENT, riskAssessment);

        return database.insertOrThrow(TABLE_TRIP, null, rowValues);
    }

        public long insertExpense(int tripId, String expenseType, String expenseAmount, String expenseDate, String expenseComment) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(EXPENSES_TRIP_ID, tripId);
        rowValues.put(EXPENSES_TYPE, expenseType);
        rowValues.put(EXPENSES_AMOUNT, expenseAmount);
        rowValues.put(EXPENSES_DATE, expenseDate);
        rowValues.put(EXPENSES_COMMENT, expenseComment);

        return database.insertOrThrow(TABLE_EXPENSE, null, rowValues);
    }

    public ArrayList<String> getExpenses(int tripId){
        String query = "SELECT b.expense_id, b.expense_date, a.name, b.expense_type, b.expense_amount FROM " + TABLE_TRIP +
                " a INNER JOIN " + TABLE_EXPENSE + " b ON a.trip_id = b.expense_tripId WHERE a.trip_id=?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(tripId)});
        ArrayList<String> results = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int expense_id = cursor.getInt(0);
            String expense_date = cursor.getString(1);
            String tripName = cursor.getString(2);
            String expense_type = cursor.getString(3);
            int expense_amount = cursor.getInt(4);

            Expenses ex = new Expenses();
            ex.setExpensesType(expense_type);
            ex.setTripName(tripName);
            ex.setExpensesDate(expense_date);
            ex.setId(expense_id);
            ex.setExpensesAmount(expense_amount);

            results.add(ex.toString());
            cursor.moveToNext();
        }
        return results;
    }

    public ArrayList<String> getTrips() {
        Cursor cursor = database.query(TABLE_TRIP, new String[] { TRIP_ID, NAME, DESTINATION, DATE, DESCRIPTION, DURATION, RISK_ASSESSMENT },
                null, null, null, null, TRIP_ID);

        ArrayList<String> results = new ArrayList<String>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String destination = cursor.getString(2);
            String date = cursor.getString(3);
            String des = cursor.getString(4);
            int duration = cursor.getInt(5);
            int riskAssessment = cursor.getInt(6);

            Trip trip = new Trip();
            trip.setId(id);
            trip.setName(name);
            trip.setDestination(destination);
            trip.setDate(date);
            trip.setDescription(des);
            trip.setDuration(duration);
            trip.setRiskAssessment(riskAssessment == 0 ? false : true);

            results.add(trip.toString());

            cursor.moveToNext();
        }

        return results;
    }

    public void ResetData(){
        database.execSQL("DELETE FROM " + TABLE_TRIP);
        database.execSQL("DELETE FROM " + TABLE_EXPENSE);
    }

    public void DeleteTrip(int id){
        database.execSQL("DELETE FROM " + TABLE_TRIP + " WHERE " + TRIP_ID + " = " + id);
    }

    public Trip getTripDetail(int tripId) {
        String[] conditionArgs = new String[]{};
        Cursor cursor = database.rawQuery("SELECT " +
                TRIP_ID + ", " +
                NAME + ", " +
                DESTINATION + ", " +
                DATE + ", " +
                DESCRIPTION + ", " +
                DURATION + ", " +
                RISK_ASSESSMENT + " FROM " +
                TABLE_TRIP + " WHERE " + TRIP_ID + " = " + tripId, conditionArgs);

        cursor.moveToFirst();
        Trip trip = null;
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String destination = cursor.getString(2);
            String date = cursor.getString(3);
            String des = cursor.getString(4);
            int duration = cursor.getInt(5);
            int riskAssessment = cursor.getInt(6);

            trip = new Trip();
            trip.setId(id);
            trip.setName(name);
            trip.setDestination(destination);
            trip.setDate(date);
            trip.setDescription(des);
            trip.setDuration(duration);
            trip.setRiskAssessment(riskAssessment == 0 ? false : true);

            cursor.moveToNext();
        }
        return trip;
    }

    public void EditTrip(int id, String name, String destination, String date, String description, int duration, boolean riskAssessment){
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(DESTINATION, destination);
        cv.put(DATE, date);
        cv.put(DESCRIPTION, description);
        cv.put(DURATION, duration);
        cv.put(RISK_ASSESSMENT, riskAssessment);

        database.update(TABLE_TRIP, cv, TRIP_ID + " = " + id, null);
    }

    public ArrayList<String> SearchTrip(String name){
        Cursor cursor = database.query(TABLE_TRIP, new String[] { TRIP_ID, NAME, DESTINATION, DATE, DESCRIPTION, DURATION, RISK_ASSESSMENT },
                NAME + " LIKE " + "'%" + name + "%'", null, null, null,  TRIP_ID);
        cursor.moveToFirst();
        ArrayList<String> results = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String nameDb = cursor.getString(1);
            String destination = cursor.getString(2);
            String date = cursor.getString(3);
            String des = cursor.getString(4);
            int duration = cursor.getInt(5);
            int riskAssessment = cursor.getInt(6);

            Trip trip = new Trip();
            trip.setId(id);
            trip.setName(nameDb);
            trip.setDestination(destination);
            trip.setDate(date);
            trip.setDescription(des);
            trip.setDuration(duration);
            trip.setRiskAssessment(riskAssessment == 0 ? false : true);

            results.add(trip.toString());

            cursor.moveToNext();
        }
        return results;
    }
}
