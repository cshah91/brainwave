package nksystems.brainwave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_medicines extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mReference;

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

        mReference= FirebaseDatabase.getInstance().getReference("medicines");
        mReference.orderByChild("orderid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data :dataSnapshot.getChildren()){
                    if((Boolean) data.child("active").getValue()){
                        medicineList.add(data.getKey());
                    }
                }

                ArrayAdapter adapter = new ArrayAdapter(activity_medicines.this,R.layout.activity_medicines_item_layout,R.id.medItem,medicineList);
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity_medicines.this, activity_order_confirmation.class);
        intent.putExtra("orderType","product");
        /*intent.putExtra("originalAmount", ""+originalAmount);
        intent.putExtra("name",txtQuestName.getText().toString());
        intent.putExtra("email", txtQuestEmail.getText().toString());
        intent.putExtra("address", txtAddress.getText().toString());
        intent.putExtra("city", txtCity.getText().toString());
        intent.putExtra("state", txtState.getText().toString());
        intent.putExtra("pincode", txtPincode.getText().toString());*/
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(activity_medicines.this,activity_home_menu.class);
                intent.putExtra("active_activity","contentCounselling");
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(activity_medicines.this,activity_home_menu.class);
        intent.putExtra("active_activity","contentCounselling");
        finish();
        startActivity(intent);
    }

    public void buyMedicine(View view){
        Toast.makeText(this, "Summary page will open", Toast.LENGTH_SHORT).show();
    }
}
