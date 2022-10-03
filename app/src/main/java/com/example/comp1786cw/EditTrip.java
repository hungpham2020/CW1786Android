package com.example.comp1786cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;

import entites.Trip;

public class EditTrip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        EditText inputName = findViewById(R.id.inputEditName);
        EditText inputDestination = findViewById(R.id.inputEditDestination);
        EditText inputDate = findViewById(R.id.inputEditDate);
        EditText inputDescription = findViewById(R.id.inputEditDescription);
        EditText inputDuration = findViewById(R.id.inputEditDuration);
        RadioButton rdBtnYes = findViewById(R.id.rdBtnEditYes);
        RadioButton rdBtnNo = findViewById(R.id.rdBtnEditNo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        Trip trip = dbHelper.getTripDetail(id);

        inputName.setText(trip.getName());
        inputDestination.setText(trip.getDestination());
        inputDate.setText(trip.getDate());
        inputDescription.setText(trip.getDescription());
//        inputDuration.setText(trip.getDuration());
        if(trip.isRiskAssessment()){
            rdBtnYes.setChecked(true);
            rdBtnNo.setChecked(false);
        }
        else{
            rdBtnYes.setChecked(false);
            rdBtnNo.setChecked(true);
        }
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