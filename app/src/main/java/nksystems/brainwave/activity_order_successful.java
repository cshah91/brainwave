/*
 * Copyright (c) 2017. NKSystems
 *
 * Created on : 02-07-2017
 * Author     : Charmy Shah
 *
 * All rights reserved
 */

package nksystems.brainwave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class activity_order_successful extends AppCompatActivity {

    String orderNo;
    Boolean isProduct;
    TextView txtShipment, txtOrderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_order_successful);

        txtShipment = (TextView) findViewById(R.id.txtShipment);
        txtOrderNo = (TextView) findViewById(R.id.txtOrderNo);
        isProduct = Boolean.parseBoolean(getIntent().getStringExtra("isProduct"));
        orderNo = Integer.toString(getIntent().getIntExtra("orderNo",0));

        txtShipment.setVisibility(isProduct ? View.VISIBLE : View.GONE);
        txtOrderNo.setText("#"+orderNo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(activity_order_successful.this,activity_home_menu.class);
                intent.putExtra("active_activity","");
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(activity_order_successful.this,activity_home_menu.class);
        intent.putExtra("active_activity","");
        finish();
        startActivity(intent);
    }
}
