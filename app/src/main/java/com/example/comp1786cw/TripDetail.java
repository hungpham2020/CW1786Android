package com.example.comp1786cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import entites.Trip;

public class TripDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        Button btnAdd = findViewById(R.id.btnAddEx);
        Button btnViewAll = findViewById(R.id.btnViewAllExpense);
        TextView txtName = findViewById(R.id.txtName);
        TextView txtDestination = findViewById(R.id.txtDestination);
        TextView txtDate = findViewById(R.id.txtDate);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtDuration = findViewById(R.id.txtDuration);
        TextView txtRiskAssessment = findViewById(R.id.txtRiskAssessment);

        Trip trip = dbHelper.getTripDetail(id);

        txtName.setText("Name: " + trip.getName());
        txtDestination.setText("Destination: " + trip.getDestination());
        txtDate.setText("Date: " + trip.getDate());
        txtDescription.setText("Description: " + trip.getDescription());
        txtDuration.setText("Duration: " + String.valueOf(trip.getDuration()));
        txtRiskAssessment.setText("Requires Risk Assessment: " + (trip.isRiskAssessment() ? "Yes" : "No"));

        btnAdd.setOnClickListener(v -> {
            Intent addIntent = new Intent(TripDetail.this, AddExpense.class);
            addIntent.putExtra("tripId", trip.getId());
            addIntent.putExtra("tripName", trip.getName());
            startActivity(addIntent);
        });

        btnViewAll.setOnClickListener(v -> {
            Intent viewAllIntent = new Intent(TripDetail.this, ViewAllExpenses.class);
            viewAllIntent.putExtra("tripId", trip.getId());
            viewAllIntent.putExtra("tripName", trip.getName());
            startActivity(viewAllIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}