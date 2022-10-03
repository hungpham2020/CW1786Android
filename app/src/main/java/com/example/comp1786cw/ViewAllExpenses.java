package com.example.comp1786cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ViewAllExpenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_expenses);
        TextView header = findViewById(R.id.txtViewAllHeader);
        ListView listView = findViewById(R.id.listviewExpenses);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int tripId = intent.getIntExtra("tripId", 0);
        String tripName = intent.getStringExtra("tripName");
        header.setText("Expenses list for Trip: " + tripName);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<String> list = dbHelper.getExpenses(tripId);
        if(list.size() == 0){
            list.add("No Data");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
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