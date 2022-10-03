package com.example.comp1786cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        TextView txtHeader = findViewById(R.id.txtHeader);
        EditText inputAmount = findViewById(R.id.inputAmount);
        EditText inputDate = findViewById(R.id.inputEditDate);
        EditText inputComment = findViewById(R.id.inputComment);
        Button btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        int tripId = intent.getIntExtra("tripId", 0);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        txtHeader.setText("Add Expense for Trip: " + tripName);

        AutoCompleteTextView selectType = findViewById(R.id.slxType);

        ArrayAdapter<String> optionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, ExpenseType);

        selectType.setAdapter(optionAdapter);
        selectType.setOnTouchListener((v, event) -> {
            selectType.showDropDown();
            return true;
        });

        inputDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                MainActivity.DatePickerFragment dlg = new MainActivity.DatePickerFragment();
                dlg.setDateInput(inputDate);
                dlg.show(getSupportFragmentManager(), "datePicker");
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnSave.setOnClickListener(v -> {
            if(validate(selectType.getText().toString(), inputAmount.getText().toString(),
                    inputDate.getText().toString())){
                dbHelper.insertExpense(tripId, selectType.getText().toString(), inputAmount.getText().toString(),
                        inputDate.getText().toString(),
                        inputComment.getText().toString());
                this.finish();
                Toast.makeText(this, "Add Expense successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Form has some errors, check again", Toast.LENGTH_LONG).show();
            }
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

    private static final String[] ExpenseType = new String[] {
            "Travel", "Foods", "Hired room", "Others"
    };

    private boolean validate(String selectType, String amount, String date){
        boolean isPass = true;
        String message = "";
        TextView errors = findViewById(R.id.txtError);
        if (selectType.isEmpty()){
            isPass = false;
            message += "\t- Expenses Type is required \n";
        }
        if(amount.isEmpty()){
            isPass = false;
            message += "\t- Expense Amount is required \n";
        }
        if(date.isEmpty()){
            isPass = false;
            message += "\t- Expense Date is required \n";
        }
        if(!message.isEmpty()){
            errors.setText("Error Sumaries:" + "\n" + message);
        }
        if(isPass){
            errors.setText("");
        }
        return isPass;
    }
}