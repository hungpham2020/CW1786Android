package com.example.comp1786cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ViewAllTrips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        ListView listView = findViewById(R.id.listView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        List<String> trips = dbHelper.getTrips();
        if(trips.size() == 0){
            trips.add("No Data");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, trips);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            final String[] options = {"Edit", "Delete", "View Detail"};
            String selected = trips.get(position);
            int selectedId = Integer.parseInt(selected.split(" - ")[0]);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(options, ((dialog, which) -> {
                if(options[which] == "Delete"){
                    dbHelper.DeleteTrip(selectedId);
                    List<String> newTrips = dbHelper.getTrips();
                    if(trips.size() == 0){
                        trips.add("No Data");
                    }

                    ArrayAdapter<String> newAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newTrips);

                    listView.setAdapter(newAdapter);

                    Toast.makeText(this, "Delete done", Toast.LENGTH_SHORT).show();
                }
                else if(options[which] == "View Detail"){
                    openDetails(selectedId);
                }
                else if(options[which] == "Edit"){
                    openEdit(selectedId);
                }
            }));
            builder.show();
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

    private void openDetails(int id) {
        Intent intent = new Intent(ViewAllTrips.this, TripDetail.class);
        intent.putExtra("Id", id);
        startActivity(intent);
    }

    private void openEdit(int id) {
        Intent editIntent = new Intent(ViewAllTrips.this, EditTrip.class);
        editIntent.putExtra("Id", id);
        startActivity(editIntent);
    }
}