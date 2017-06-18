package nksystems.brainwave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class activity_order_confirmation extends AppCompatActivity {

    String orderType, packageType, productName, productDescription;
    String isMedication;
    TextView tvOrderTitle, tvOrderDescription, tvOrderMedicine, tvOrderShipping, tvOrderPrice, tvOrderTax, tvOrderTotal;
    TextView labelMedicine, labelShipping;
    ImageView imgPlaceholder;
    int taxPercent = 10;
    double originalAmount, calculatedTax, medicineCharge, totalAmount, shippingCharge = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_order_confirmation);

        tvOrderTitle = (TextView) findViewById(R.id.tvOrderTitle);
        tvOrderDescription = (TextView) findViewById(R.id.tvOrderDescription);
        imgPlaceholder = (ImageView) findViewById(R.id.imgPlaceholder);
        tvOrderMedicine = (TextView) findViewById(R.id.tvOrderMedicine);
        tvOrderShipping = (TextView) findViewById(R.id.tvOrderShipping);
        tvOrderPrice = (TextView) findViewById(R.id.tvOrderPrice);
        tvOrderTax = (TextView) findViewById(R.id.tvOrderTax);
        tvOrderTotal = (TextView) findViewById(R.id.tvOrderTotal);

        labelMedicine = (TextView) findViewById(R.id.label_medicine);
        labelShipping = (TextView) findViewById(R.id.label_shipping);

        orderType = getIntent().getStringExtra("orderType");

        if(orderType.equals("service")){
            imgPlaceholder.setVisibility(View.GONE);
            packageType = getIntent().getStringExtra("packageType");
            isMedication = getIntent().getStringExtra("medication");
            if(packageType.equals("1")){
                tvOrderTitle.setText("Counselling Services - Long Session");
                tvOrderDescription.setText("Long Session Description text");
            }
            else{
                tvOrderTitle.setText("Counselling Services - Brief Session");
                tvOrderDescription.setText("Brief session description text");
            }

            if(isMedication.equals("true")){
                tvOrderMedicine.setVisibility(View.VISIBLE);
                tvOrderShipping.setVisibility(View.VISIBLE);
                labelMedicine.setVisibility(View.VISIBLE);
                labelShipping.setVisibility(View.VISIBLE);
                medicineCharge = Double.parseDouble(getIntent().getStringExtra("medicineCharge"));
                tvOrderMedicine.setText(""+medicineCharge);
                shippingCharge = 50;
            }
            else{
                tvOrderMedicine.setVisibility(View.GONE);
                tvOrderShipping.setVisibility(View.GONE);
                labelMedicine.setVisibility(View.GONE);
                labelShipping.setVisibility(View.GONE);
            }
        }
        else{
            imgPlaceholder.setVisibility(View.VISIBLE);
            tvOrderMedicine.setVisibility(View.GONE);
            tvOrderShipping.setVisibility(View.VISIBLE);
            labelMedicine.setVisibility(View.GONE);
            labelShipping.setVisibility(View.VISIBLE);
            productName = getIntent().getStringExtra("productName");
            productDescription = getIntent().getStringExtra("productDescription");
            tvOrderTitle.setText(productName);
            tvOrderDescription.setText(productDescription);
            shippingCharge = 50;
        }

        originalAmount = Double.parseDouble(getIntent().getStringExtra("originalAmount"));

        calculatedTax = originalAmount * taxPercent / 100;

        totalAmount = originalAmount + calculatedTax + medicineCharge + shippingCharge;

        tvOrderPrice.setText(""+originalAmount);
        tvOrderTax.setText(""+calculatedTax);
        tvOrderShipping.setText(""+shippingCharge);
        tvOrderTotal.setText(""+totalAmount);
    }

    public void checkout(View view){
        finish();
        Intent intent = new Intent(activity_order_confirmation.this, activity_purchase_services.class);
        intent.putExtra("amount",totalAmount);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                if(orderType.equals("service")){
                    Intent intent=new Intent(activity_order_confirmation.this,activity_counselling_form.class);
                    intent.putExtra("packagetype",packageType);
                    intent.putExtra("amount",""+(int)originalAmount);
                    finish();
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(activity_order_confirmation.this,activity_product_info.class);
                    intent.putExtra("title",productName);
                    intent.putExtra("shortDescription",productDescription);
                    finish();
                    startActivity(intent);
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(orderType.equals("service")){
            Intent intent=new Intent(activity_order_confirmation.this,activity_counselling_form.class);
            intent.putExtra("packagetype",packageType);
            intent.putExtra("amount",""+(int)originalAmount);
            finish();
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(activity_order_confirmation.this,activity_product_info.class);
            intent.putExtra("title",productName);
            intent.putExtra("shortDescription",productDescription);
            finish();
            startActivity(intent);
        }
    }
}
