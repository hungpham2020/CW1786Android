package com.example.comp1786cw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText destination;
    EditText date;
    EditText description;
    EditText duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.inputName);
        destination = findViewById(R.id.inputEditDestination);
        date = findViewById(R.id.inputEditDate);
        description = findViewById(R.id.inputEditDescription);
        duration = findViewById(R.id.inputEditDuration);
        RadioButton rdBtnYes = findViewById(R.id.rdBtnEditYes);
        RadioButton rdBtnNo = findViewById(R.id.rdBtnEditNo);

        date.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DatePickerFragment dlg = new DatePickerFragment();
                dlg.setDateInput(date);
                dlg.show(getSupportFragmentManager(), "datePicker");
            }
        });

        AtomicBoolean riskAssessment = new AtomicBoolean(true);

        rdBtnYes.setOnClickListener(v -> {
            riskAssessment.set(true);
        });

        rdBtnNo.setOnClickListener(v -> {
            riskAssessment.set(false);
        });

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnViewAll = findViewById(R.id.btnViewAll);
        Button btnReset = findViewById(R.id.btnResetData);

        btnAdd.setOnClickListener(view -> {
            if(validation(name.getText().toString(), destination.getText().toString(), date.getText().toString(),
                    duration.getText().toString())){
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                dbHelper.insertTrip(name.getText().toString(), destination.getText().toString(), date.getText().toString(), description.getText().toString(),
                        Integer.parseInt(duration.getText().toString()), riskAssessment.get());
                name.setText("");
                destination.setText("");
                date.setText("");
                description.setText("");
                duration.setText("");
                rdBtnYes.setChecked(true);
                rdBtnNo.setChecked(false);
                Toast.makeText(this, "Add Trip successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Form has some errors, check again", Toast.LENGTH_LONG).show();
            }
        });

        btnViewAll.setOnClickListener(v -> {
            openViewAll();
        });

        btnReset.setOnClickListener(v -> {
            DatabaseHelper helper = new DatabaseHelper(this);
            helper.ResetData();
            Toast.makeText(this, "Reset All data in database successfully", Toast.LENGTH_SHORT).show();
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public EditText getDateInput() {
            return dateInput;
        }

        public void setDateInput(EditText dateInput) {
            this.dateInput = dateInput;
        }

        EditText dateInput;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateInput.setText((String.valueOf(dayOfMonth + "/" + month + "/" + year)));
        }
    }

    private void openViewAll() {
        Intent intent = new Intent(MainActivity.this, ViewAllTrips.class);
        startActivity(intent);
    }

    private boolean validation(String nameVal, String destinationVal, String dateVal, String durationVal){
        boolean isPass = true;
        name = findViewById(R.id.inputName);
        destination = findViewById(R.id.inputEditDestination);
        date = findViewById(R.id.inputEditDate);
        duration = findViewById(R.id.inputEditDuration);

        if(nameVal.isEmpty()){
            isPass = false;
            name.setError("Name is required");
        }
        if(destinationVal.isEmpty()){
            isPass = false;
            destination.setError("Destination is required");
        }
        if(dateVal.isEmpty()){
            isPass = false;
            date.setError("Date is required");
        }
        if(!durationVal.isEmpty()){
            if(!isStringInt(durationVal)){
                isPass = false;
                duration.setError("Duration must be number");
            }
        } else {
            isPass = false;
            duration.setError("Duration is required");
        }
        return isPass;
    }

    public boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
}