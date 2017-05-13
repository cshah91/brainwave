package nksystems.brainwave;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Charmy on 13/05/2017.
 */

public class activity_counselling_form extends AppCompatActivity{

    CheckBox chkMedication;
    TextView txtTotal;
    int originalAmount;
    int newAmount;
    int medicationAmount;

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

        txtTotal = (TextView) findViewById(R.id.viewTotal);

        //originalAmount = Integer.parseInt(getIntent().getStringExtra("amount"));
        originalAmount = Integer.parseInt("500");
        medicationAmount = Integer.parseInt(getResources().getString(R.string.medication_amount));

        txtTotal.setText("Total: " + originalAmount + " INR");

        chkMedication = (CheckBox) findViewById(R.id.chkMedication);
        chkMedication.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    newAmount = originalAmount + medicationAmount;
                }
                else{
                    newAmount = originalAmount;
                }

                txtTotal.setText("Total: " + newAmount + " INR");
            }
        });
    }
}
