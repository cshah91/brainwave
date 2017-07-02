package nksystems.brainwave;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.BraintreeError;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PaymentMethodNonce;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static nksystems.brainwave.activity_purchase_services.CONNECTION_TIMEOUT;
import static nksystems.brainwave.activity_purchase_services.READ_TIMEOUT;

public class activity_order_confirmation extends AppCompatActivity
        implements PaymentMethodNonceCreatedListener,BraintreeCancelListener,BraintreeErrorListener {

    String orderType, packageType, productName, productDescription;
    String isMedication;
    TextView tvOrderTitle, tvOrderDescription, tvOrderMedicine, tvOrderShipping, tvOrderPrice, tvOrderTax, tvOrderTotal;
    TextView labelMedicine, labelShipping;
    ImageView imgPlaceholder;
    int taxPercent = 10;
    double originalAmount, calculatedTax, medicineCharge, totalAmount, shippingCharge = 0;

    private static String request_url = "http://192.168.0.105:81/braintree/request";
    private static String response_url = "http://192.168.0.105:81/braintree/response";
    private static String nonce_url = "http://192.168.0.105:81/braintree/nonce";
    String clientToken;
    DropInRequest dropInRequest;
    String nonce, deviceData;
    private static final int REQUEST_CODE = 1;
    BraintreeFragment braintreeFragment;
    String tokenizationKey = "sandbox_6w3f54dk_jvrhmcgz2n7fydvy";

    String name, email, briefProblem, detailedProblem, date, time, address, city, state, pincode;

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


            name =activity_order_confirmation.this.getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            briefProblem = getIntent().getStringExtra("briefProblem");
            detailedProblem = getIntent().getStringExtra("detailedProblem");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            address = getIntent().getStringExtra("address");
            city = getIntent().getStringExtra("city");
            state = getIntent().getStringExtra("state");
            pincode = getIntent().getStringExtra("pincode");
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
        new AsyncRequest().execute();

        dropInRequest = new DropInRequest()
                .tokenizationKey(tokenizationKey)
                .collectDeviceData(true);

        startActivityForResult(dropInRequest.getIntent(activity_order_confirmation.this), REQUEST_CODE);
        /*finish();
        Intent intent = new Intent(activity_order_confirmation.this, activity_purchase_services.class);
        intent.putExtra("amount",totalAmount);
        startActivity(intent);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /************Braintree & Paypal Integration Start************/
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                deviceData = result.getDeviceData();
                nonce = result.getPaymentMethodNonce().getNonce();

                try {
                    braintreeFragment = BraintreeFragment.newInstance(activity_order_confirmation.this, tokenizationKey);
                    // use the result to update your ui and send payment method nonce to your server
                    DataCollector.collectDeviceData(braintreeFragment, new BraintreeResponseListener<String>() {
                        @Override
                        public void onResponse(String s) {
                            // send deviceData and nonce to your server
                            new AsyncPost().execute();
                        }
                    });
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            }
            else if(resultCode == RESULT_CANCELED){
                // the user cancelled
            }
            else{
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
        /************Braintree & Paypal Integration End************/
    }

    /************Braintree & Paypal Integration Start************/
    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        // Send this nonce to your server
    }

    @Override
    public void onCancel(int requestCode) {
        // Use this to handle a canceled activity, if the given requestCode is important.
        // You may want to use this callback to hide loading indicators, and prepare your UI for input
    }

    @Override
    public void onError(Exception error) {
        if(error instanceof ErrorWithResponse){
            ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;
            BraintreeError cardErrors = ((ErrorWithResponse) error).errorFor("creditCard");
            if(cardErrors != null){
                // There is an issue with the credit card
                BraintreeError expirationMonthError = cardErrors.errorFor("expirationMonth");
                if(expirationMonthError != null){
                    // There is an issue with the expiration month.
                    Toast.makeText(activity_order_confirmation.this, "Error: " + expirationMonthError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /************Braintree & Paypal Integration End************/

    private class AsyncRequest extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL(request_url);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }

            try {

                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            /************Braintree & Paypal Integration Start************/
            clientToken = s;
            /************Braintree & Paypal Integration End************/
        }
    }

    private class AsyncPost extends AsyncTask<String, String, String>{

        HttpURLConnection conn;
        URL url = null;
        JSONObject postDataParams = new JSONObject();
        String data  = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL(nonce_url);

                data = URLEncoder.encode("payment_method_nonce", "UTF-8") + "=" +
                        URLEncoder.encode(nonce, "UTF-8");

                data += "&" + URLEncoder.encode("device_data", "UTF-8") + "=" +
                        URLEncoder.encode(deviceData, "UTF-8");

                data += "&" + URLEncoder.encode("total_amount", "UTF-8") + "=" +
                        URLEncoder.encode(""+totalAmount, "UTF-8");

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }

            try {

                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();

                /*conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);*/
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.flush();

                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
            catch(Exception e){
                e.printStackTrace();
                return e.toString();
            }
            finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);
            Log.i("Purchase", status);
            Toast.makeText(activity_order_confirmation.this, "Result: " + status, Toast.LENGTH_LONG).show();
            if(status != null && status.equals("submitted_for_settlement")){
                finish();
                Intent intent = new Intent(activity_order_confirmation.this, activity_order_successful.class);
                intent.putExtra("amount",totalAmount);
                startActivity(intent);
            }
        }
    }

    private class AsyncResponse extends AsyncTask<String, String, String>{

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL(response_url);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }

            try {

                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
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
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    intent.putExtra("briefProblem",briefProblem);
                    intent.putExtra("detailedProblem",detailedProblem);
                    intent.putExtra("date",date);
                    intent.putExtra("time",time);
                    intent.putExtra("address",address);
                    intent.putExtra("city",city);
                    intent.putExtra("state",state);
                    intent.putExtra("pincode",pincode);
                    intent.putExtra("isMedication", isMedication);
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
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("briefProblem",briefProblem);
            intent.putExtra("detailedProblem",detailedProblem);
            intent.putExtra("date",date);
            intent.putExtra("time",time);
            intent.putExtra("address",address);
            intent.putExtra("city",city);
            intent.putExtra("state",state);
            intent.putExtra("pincode",pincode);
            intent.putExtra("isMedication", isMedication);
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
