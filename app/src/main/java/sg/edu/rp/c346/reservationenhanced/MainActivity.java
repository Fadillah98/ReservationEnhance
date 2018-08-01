package sg.edu.rp.c346.reservationenhanced;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText name, mobile, size, etDay, etTime;
    CheckBox smokingArea;
    Button reserve, reset;
    int theYear, theMonth, theDay, theHour, theMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etCustName);
        mobile = findViewById(R.id.etCustNumber);
        size = findViewById(R.id.etSize);
        smokingArea = findViewById(R.id.checkSmoke);
        reserve = findViewById(R.id.btnBook);
        reset = findViewById(R.id.btnReset);
        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);

        Calendar now = Calendar.getInstance();
        theYear = now.get(Calendar.YEAR);
        theMonth = now.get(Calendar.MONTH);
        theDay = now.get(Calendar.DAY_OF_MONTH);
        theHour = now.get(Calendar.HOUR_OF_DAY);
        theMinute = now.get(Calendar.MINUTE);

        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDay.setText("Date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                        theYear = year;
                        theMonth = month;
                        theDay = dayOfMonth;
                    }
                };

                Calendar now = Calendar.getInstance();
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, theYear, theMonth, theDay);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText("Time: " + hourOfDay + ":" + minute);
                        theHour = hourOfDay;
                        theMinute = minute;
                    }
                };

                Calendar now = Calendar.getInstance();
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, theHour, theMinute, true);
                myTimeDialog.show();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etName = name.getText().toString().trim();
                String etMobile = mobile.getText().toString().trim();
                String etSize = size.getText().toString().trim();
                String day = etDay.getText().toString();
                String time = etTime.getText().toString();
                Boolean check = smokingArea.isChecked();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");

                if (etName.length() != 0 && etMobile.length() != 0 && etSize.length() != 0){
                    if (check){
                        myBuilder.setMessage("New Reservation\nName: " + etName + "\nSmoking: Yes\nSize: " + etSize + "\n" + day + "\n" + time);
                    } else {
                        myBuilder.setMessage("New Reservation\nName: " + etName + "\nSmoking: No\nSize: " + etSize + "\n" + day + "\n" + time);
                    }
                } else {
                    myBuilder.setMessage("Please input all your info!");
                }
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Confirm", null);
                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name.setText(null);
                mobile.setText(null);
                size.setText(null);
                smokingArea.setChecked(false);
                etTime.setText(null);
                etDay.setText(null);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        String etName = name.getText().toString().trim();
        String etMobile = mobile.getText().toString().trim();
        String etSize = size.getText().toString().trim();
        String day = etDay.getText().toString();
        String time = etTime.getText().toString();
        Boolean check = smokingArea.isChecked();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("name", etName);
        prefEdit.putString("mobile", etMobile);
        prefEdit.putString("size", etSize);
        prefEdit.putString("day", day);
        prefEdit.putString("time", time);
        prefEdit.putBoolean("smoke", check);
        prefEdit.commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String etname = prefs.getString("name", "");
        String etmobile = prefs.getString("mobile", "");
        String etsize = prefs.getString("size", "");
        String time = prefs.getString("time", "");
        String day = prefs.getString("day", "");
        Boolean check = prefs.getBoolean("smoke", false);

        name.setText(etname);
        mobile.setText(etmobile);
        size.setText(etsize);
        etTime.setText(time);
        etDay.setText(day);
        smokingArea.setChecked(check);

    }
}
