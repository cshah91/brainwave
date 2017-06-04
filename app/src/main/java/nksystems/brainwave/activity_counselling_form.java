package nksystems.brainwave;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wallet.Wallet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Charmy on 13/05/2017.
 */

public class activity_counselling_form extends AppCompatActivity{

    CheckBox chkMedication;
    TextView txtTotal;
    int originalAmount, newAmount, medicationAmount;
    String packagetype, selectedDate;
    Button btnSubmit;
    IInAppBillingService mService;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(activity_counselling_form.this,activity_home_menu.class);
        intent.putExtra("active_activity","contentCounselling");
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(activity_counselling_form.this,activity_home_menu.class);
                intent.putExtra("active_activity","contentCounselling");
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.counselling_form_layout);
        setDateTimePicker();

        packagetype = getIntent().getStringExtra("packagetype");

        // Package Type - 1 = Long Personal Session
        // Package Type - 2 = Short Telephonic Session
        if(packagetype.equals("1")){
            findViewById(R.id.viewQuestProblem).setVisibility(View.VISIBLE);
            findViewById(R.id.viewQuestProblemDetailed).setVisibility(View.GONE);
            findViewById(R.id.txtQuestProblem).setVisibility(View.VISIBLE);
            findViewById(R.id.txtQuestProblemDetailed).setVisibility(View.GONE);
            findViewById(R.id.viewCounselDateTime).setVisibility(View.VISIBLE);
            findViewById(R.id.linearCounselDate).setVisibility(View.VISIBLE);
            findViewById(R.id.linearCounselTime).setVisibility(View.VISIBLE);
            findViewById(R.id.tblCounselAppointment).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.viewQuestProblemDetailed).setVisibility(View.VISIBLE);
            findViewById(R.id.viewQuestProblem).setVisibility(View.GONE);
            findViewById(R.id.txtQuestProblem).setVisibility(View.GONE);
            findViewById(R.id.txtQuestProblemDetailed).setVisibility(View.VISIBLE);
            findViewById(R.id.viewCounselDateTime).setVisibility(View.GONE);
            findViewById(R.id.linearCounselDate).setVisibility(View.GONE);
            findViewById(R.id.linearCounselTime).setVisibility(View.GONE);
            findViewById(R.id.tblCounselAppointment).setVisibility(View.GONE);
        }

        txtTotal = (TextView) findViewById(R.id.viewTotal);

        originalAmount = Integer.parseInt(getIntent().getStringExtra("amount"));
        medicationAmount = Integer.parseInt(getResources().getString(R.string.medication_amount));

        txtTotal.setText("Total: " + originalAmount + " INR");

        chkMedication = (CheckBox) findViewById(R.id.chkMedication);
        chkMedication.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    newAmount = originalAmount + medicationAmount;
                    if(packagetype.equals("2"))
                        findViewById(R.id.linearCounselAddress).setVisibility(View.VISIBLE);
                    else
                        findViewById(R.id.linearCounselAddress).setVisibility(View.GONE);
                }
                else{
                    newAmount = originalAmount;
                    findViewById(R.id.linearCounselAddress).setVisibility(View.GONE);
                }

                txtTotal.setText("Total: " + newAmount + " INR");
            }
        });

        btnSubmit = (Button) findViewById(R.id.btnQuestSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtQuestName = (EditText) findViewById(R.id.txtQuestName);
                EditText txtQuestEmail = (EditText) findViewById(R.id.txtQuestEmail);
                EditText txtQuestProblem = (EditText) findViewById(R.id.txtQuestProblem);
                EditText txtQuestProblemDetailed = (EditText) findViewById(R.id.txtQuestProblemDetailed);
                EditText txtCounselDueDate = (EditText) findViewById(R.id.txtCounselDueDate);
                EditText txtCounselDueTime = (EditText) findViewById(R.id.txtCounselDueTime);
                EditText txtAddress = (EditText) findViewById(R.id.txtCounselAddress);
                EditText txtCity = (EditText) findViewById(R.id.txtAddressCity);
                EditText txtState = (EditText) findViewById(R.id.txtAddressState);
                EditText txtPincode = (EditText) findViewById(R.id.txtAddressPincode);
                CheckBox chkMedication = (CheckBox) findViewById(R.id.chkMedication);

                // Package Type - 1 = Long Personal Session
                // Package Type - 2 = Short Telephonic Session
                if(txtQuestName.getText().equals("") || txtQuestName.getText().length() == 0){
                    Toast.makeText(activity_counselling_form.this, "Please enter your name.",Toast.LENGTH_LONG).show();
                    txtQuestName.requestFocus();
                }
                else if(txtQuestEmail.getText().equals("") || txtQuestEmail.getText().length() == 0){
                    Toast.makeText(activity_counselling_form.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    txtQuestEmail.requestFocus();
                }
                else if(packagetype.equals("1") && (txtQuestProblem.getText().equals("") || txtQuestProblem.getText().length() == 0)){
                    Toast.makeText(activity_counselling_form.this, "Briefly explain your problem here.", Toast.LENGTH_SHORT).show();
                    txtQuestProblem.requestFocus();
                }
                else if(packagetype.equals("1") && (txtCounselDueDate.getText().equals("") || txtCounselDueDate.getText().length() == 0)){
                    Toast.makeText(activity_counselling_form.this, "Please select a date of appointment.", Toast.LENGTH_SHORT).show();
                    txtCounselDueDate.requestFocus();
                }
                else if(packagetype.equals("1") && (txtCounselDueTime.getText().equals("") || txtCounselDueTime.getText().length() == 0)){
                    Toast.makeText(activity_counselling_form.this, "Please select a time of appointment.", Toast.LENGTH_SHORT).show();
                    txtCounselDueTime.requestFocus();
                }
                else if(packagetype.equals("2") && (txtQuestProblemDetailed.getText().equals("") || txtQuestProblemDetailed.getText().length() == 0)){
                    Toast.makeText(activity_counselling_form.this, "Please provide a detailed explanation of your problem.", Toast.LENGTH_SHORT).show();
                    txtQuestProblemDetailed.requestFocus();
                }
                else if(packagetype.equals("2") && chkMedication.isChecked()){
                    if(txtAddress.getText().equals("") || txtAddress.getText().length() == 0){
                        Toast.makeText(activity_counselling_form.this, "Please enter your shipping address.", Toast.LENGTH_SHORT).show();
                        txtAddress.requestFocus();
                    }
                    else if(txtCity.getText().equals("") || txtCity.getText().length() == 0){
                        Toast.makeText(activity_counselling_form.this, "Please enter your city.", Toast.LENGTH_SHORT).show();
                        txtCity.requestFocus();
                    }
                    else if(txtState.getText().equals("") || txtState.getText().length() == 0){
                        Toast.makeText(activity_counselling_form.this, "Please enter your state.", Toast.LENGTH_SHORT).show();
                        txtState.requestFocus();
                    }
                    else if(txtPincode.getText().equals("") || txtPincode.getText().length() == 0){
                        Toast.makeText(activity_counselling_form.this, "Please enter your pincode.", Toast.LENGTH_SHORT).show();
                        txtPincode.requestFocus();
                    }
                    else{
                        finish();
                        startActivity(new Intent(activity_counselling_form.this,activity_purchase_services.class));
                    }
                }
                else{
                    finish();
                    startActivity(new Intent(activity_counselling_form.this,activity_purchase_services.class));
                }
            }
        });
    }

    public void setDateTimePicker(){
        ImageButton btnCounselDate, btnCounselTime;

        btnCounselDate = (ImageButton) findViewById(R.id.btnCounselDate);
        btnCounselTime = (ImageButton) findViewById(R.id.btnCounselTime);

        btnCounselDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                //Get Current Date
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_counselling_form.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Get day of the week for the selected date
                        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                        int week = cal.get(Calendar.DAY_OF_WEEK);

                        if(week == Calendar.SATURDAY || week == Calendar.SUNDAY || week == Calendar.WEDNESDAY){
                            EditText txtDate = (EditText) findViewById(R.id.txtCounselDueDate);
                            txtDate.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                            selectedDate = txtDate.getText().toString();
                        }
                        else{
                            String message = "Appointments available only for Wednesday, Saturday and Sunday. " +
                                    "Please select another date.";
                            Toast.makeText(activity_counselling_form.this,message,Toast.LENGTH_LONG).show();
                        }
                    }
                },mYear,mMonth,mDay);
                Date currentDate = calendar.getTime();
                datePickerDialog.getDatePicker().setMinDate(currentDate.getTime());
                datePickerDialog.show();
            }
        });

        btnCounselTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHour, mMinute;
                //Get Current Time
                final Calendar calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_counselling_form.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        EditText txtTime = (EditText) findViewById(R.id.txtCounselDueTime);
                        if(selectedDate != null && selectedDate.length() > 0){
                            String date[] = selectedDate.split("-");
                            Calendar cal = new GregorianCalendar(Integer.parseInt(date[2]), (Integer.parseInt(date[1])-1),
                                    Integer.parseInt(date[0]));
                            int week = cal.get(Calendar.DAY_OF_WEEK);
                            String min="";
                            if(minute >= 0 && minute < 10)
                                min = "0"+minute;
                            else
                                min = ""+minute;
                            if(week == Calendar.WEDNESDAY){
                                if(hour >= 9 && hour <= 12){
                                    txtTime.setText((hour > 12 ? (hour-12):hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                }
                                else{
                                    txtTime.setText("");
                                    Toast.makeText(activity_counselling_form.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(week == Calendar.SATURDAY){
                                if((hour >= 16 && hour <= 18) || (hour >= 21 && hour <= 23)){
                                    txtTime.setText((hour > 12 ? (hour-12):hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                }
                                else{
                                    txtTime.setText("");
                                    Toast.makeText(activity_counselling_form.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(week == Calendar.SUNDAY){
                                if(hour >= 10 && hour <= 15){
                                    txtTime.setText((hour > 12 ? (hour-12):hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                }
                                else{
                                    txtTime.setText("");
                                    Toast.makeText(activity_counselling_form.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                txtTime.setText("");
                                Toast.makeText(activity_counselling_form.this, "Selected time is not available. Please see the appointment schedule " +
                                        "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            txtTime.setText("");
                            Toast.makeText(activity_counselling_form.this, "Please select a date before setting time.", Toast.LENGTH_LONG).show();
                        }
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
    }
}
