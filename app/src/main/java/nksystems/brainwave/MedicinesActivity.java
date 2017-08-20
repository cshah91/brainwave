package nksystems.brainwave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used for displaying the list of medicines for purchase
 *
 * @author Charmy Shah
 * @version 1.0
 * @date 08-07-2017
 */
public class MedicinesActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mReference;
    double originalAmount = 0;
    String shortDescription = "";
    Map<String, String> medicinesPriceMap = new HashMap<>();
    Map<String, String> medicinesDescriptionMap = new HashMap<>();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_medicines);

        final List<String> medicineList = new ArrayList();
        final ProgressBar pd_progress = (ProgressBar) findViewById(R.id.pd_progress);

        FirebaseApp.initializeApp(this);
        mReference = FirebaseDatabase.getInstance().getReference("medicines");
        mReference.orderByChild("orderid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if ((Boolean) data.child("active").getValue()) {
                        medicineList.add(data.getKey());
                        medicinesPriceMap.put(data.getKey(), data.child("price").getValue().toString());
                        medicinesDescriptionMap.put(data.getKey(), data.child("shortdesc").getValue().toString());
                    }
                }

                ArrayAdapter adapter = new ArrayAdapter(MedicinesActivity.this, R.layout.item_medicine, R.id.medItem, medicineList);
                ListView listView = (ListView) findViewById(R.id.list_view_medicines);
                listView.setAdapter(adapter);
                pd_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(MedicinesActivity.this, HomeMenuActivity.class);
                intent.putExtra("active_activity", "contentCounselling");
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MedicinesActivity.this, HomeMenuActivity.class);
        intent.putExtra("active_activity", "contentCounselling");
        finish();
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void buyMedicine(View view) {
        TextView txtMedicine = (TextView) view.findViewById(R.id.medItem);
        Intent intent = new Intent(MedicinesActivity.this, ShippingAddressActivity.class);
        intent.putExtra("orderType", "medicine");
        intent.putExtra("productName", txtMedicine.getText());
        for (Map.Entry<String, String> entry : medicinesPriceMap.entrySet()) {
            // Each medicine name should be unique else the code will fail
            if (entry.getKey().equals(txtMedicine.getText())) {
                originalAmount = Double.parseDouble(entry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : medicinesDescriptionMap.entrySet()) {
            // Each medicine name should be unique else the code will fail
            if (entry.getKey().equals(txtMedicine.getText())) {
                shortDescription = entry.getValue();
            }
        }

        intent.putExtra("originalAmount", "" + originalAmount);
        intent.putExtra("productDescription", shortDescription);
        finish();
        startActivity(intent);
    }
}
