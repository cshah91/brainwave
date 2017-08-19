/*
 * Copyright (c) 2017. NKSystems
 *
 * Created on : 28-07-2017
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_shipping_address extends AppCompatActivity implements View.OnClickListener{

    EditText txtAddress, txtCity, txtState, txtPincode;
    String address, city, state, pincode, productName, orderType, shortDescription, productPrice;
    Button proceed;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(orderType.equalsIgnoreCase("product")){
            Intent intent=new Intent(activity_shipping_address.this,activity_home_menu.class);
            intent.putExtra("active_activity","herbalProducts");
            finish();
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(activity_shipping_address.this,activity_medicines.class);
            finish();
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                if(orderType.equalsIgnoreCase("product")){
                    Intent intent=new Intent(activity_shipping_address.this,activity_home_menu.class);
                    intent.putExtra("active_activity","herbalProducts");
                    finish();
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(activity_shipping_address.this,activity_medicines.class);
                    finish();
                    startActivity(intent);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_shipping_address);

        txtAddress = (EditText) findViewById(R.id.txtCounselAddress);
        txtCity = (EditText) findViewById(R.id.txtAddressCity);
        txtState = (EditText) findViewById(R.id.txtAddressState);
        txtPincode = (EditText) findViewById(R.id.txtAddressPincode);
        proceed = (Button) findViewById(R.id.btnProceed);

        address = getIntent().getStringExtra("address");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        pincode = getIntent().getStringExtra("pincode");

        productName = getIntent().getStringExtra("productName");
        orderType = getIntent().getStringExtra("orderType");
        shortDescription = getIntent().getStringExtra("productDescription");
        productPrice = getIntent().getStringExtra("originalAmount");

        if(address != null && address.length() > 0)
            txtAddress.setText(address);

        if(city != null && city.length() > 0)
            txtCity.setText(city);

        if(state != null && state.length() > 0)
            txtState.setText(state);

        if(pincode != null && pincode.length() > 0)
            txtPincode.setText(pincode);

        proceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(txtAddress.getText().equals("") || txtAddress.getText().length() == 0){
            Toast.makeText(activity_shipping_address.this, "Please enter your shipping address.", Toast.LENGTH_SHORT).show();
            txtAddress.requestFocus();
        }
        else if(txtCity.getText().equals("") || txtCity.getText().length() == 0){
            Toast.makeText(activity_shipping_address.this, "Please enter your city.", Toast.LENGTH_SHORT).show();
            txtCity.requestFocus();
        }
        else if(txtState.getText().equals("") || txtState.getText().length() == 0){
            Toast.makeText(activity_shipping_address.this, "Please enter your state.", Toast.LENGTH_SHORT).show();
            txtState.requestFocus();
        }
        else if(txtPincode.getText().equals("") || txtPincode.getText().length() == 0){
            Toast.makeText(activity_shipping_address.this, "Please enter your pincode.", Toast.LENGTH_SHORT).show();
            txtPincode.requestFocus();
        }
        else{
            Intent intent = new Intent(activity_shipping_address.this, activity_order_confirmation.class);
            intent.putExtra("address", txtAddress.getText().toString());
            intent.putExtra("city", txtCity.getText().toString());
            intent.putExtra("state", txtState.getText().toString());
            intent.putExtra("pincode", txtPincode.getText().toString());
            intent.putExtra("productName",productName);
            intent.putExtra("orderType",orderType);
            intent.putExtra("productDescription",shortDescription);
            intent.putExtra("originalAmount",productPrice);
            finish();
            startActivity(intent);
        }
    }
}
