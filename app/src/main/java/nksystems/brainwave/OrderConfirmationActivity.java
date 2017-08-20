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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class is used for displaying the confirmation page before making a purchase
 *
 * @author Charmy Shah
 * @version 1.0
 * @date 18-06-2017
 */
public class OrderConfirmationActivity extends AppCompatActivity
        implements PaymentMethodNonceCreatedListener, BraintreeCancelListener, BraintreeErrorListener {

    String orderType, packageType, productName, productDescription, serviceType, serviceProblem;
    String isMedication = "false";
    TextView tvOrderTitle, tvOrderDescription, tvOrderMedicine, tvOrderShipping, tvOrderPrice, tvOrderTax, tvOrderTotal;
    TextView labelMedicine, labelShipping;
    ImageView imgPlaceholder;
    int taxPercent = 10;
    double originalAmount, calculatedTax, medicineCharge, totalAmount, shippingCharge = 0;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private static String request_url = "http://192.168.0.107:81/braintree/request";
    private static String nonce_url = "http://192.168.0.107:81/braintree/nonce";
    String clientToken;
    DropInRequest dropInRequest;
    String nonce, deviceData;
    private static final int REQUEST_CODE = 1;
    BraintreeFragment braintreeFragment;
    String tokenizationKey = "sandbox_6w3f54dk_jvrhmcgz2n7fydvy";

    String name, email, briefProblem, detailedProblem, date, time, address, city, state, pincode;
    DatabaseReference mReference, ordersReference;
    int currentOrderId;
    boolean stat = false;
    boolean val = false;

    /**
     * @param savedInstanceState
     */
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
        serviceType = "NA";

        if (orderType.equals("service")) {
            imgPlaceholder.setVisibility(View.GONE);
            packageType = getIntent().getStringExtra("packageType");
            isMedication = getIntent().getStringExtra("medication");
            if (packageType.equals("1")) {
                serviceType = "long";
                productName = "Counselling Services - Long Session";
                tvOrderTitle.setText(productName);
                tvOrderDescription.setText("Long Session Description text");
            } else {
                serviceType = "short";
                productName = "Counselling Services - Brief Session";
                tvOrderTitle.setText(productName);
                tvOrderDescription.setText("Brief session description text");
            }

            if (isMedication.equals("true")) {
                tvOrderMedicine.setVisibility(View.VISIBLE);
                tvOrderShipping.setVisibility(View.VISIBLE);
                labelMedicine.setVisibility(View.VISIBLE);
                labelShipping.setVisibility(View.VISIBLE);
                medicineCharge = Double.parseDouble(getIntent().getStringExtra("medicineCharge"));
                tvOrderMedicine.setText("" + medicineCharge);
                shippingCharge = 50;
            } else {
                tvOrderMedicine.setVisibility(View.GONE);
                tvOrderShipping.setVisibility(View.GONE);
                labelMedicine.setVisibility(View.GONE);
                labelShipping.setVisibility(View.GONE);
            }

            name = OrderConfirmationActivity.this.getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            briefProblem = getIntent().getStringExtra("briefProblem");
            detailedProblem = getIntent().getStringExtra("detailedProblem");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            address = getIntent().getStringExtra("address");
            city = getIntent().getStringExtra("city");
            state = getIntent().getStringExtra("state");
            pincode = getIntent().getStringExtra("pincode");
        } else if (orderType.equals("product")) {
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
        } else {
            imgPlaceholder.setVisibility(View.GONE);
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

        tvOrderPrice.setText("" + originalAmount);
        tvOrderTax.setText("" + calculatedTax);
        tvOrderShipping.setText("" + shippingCharge);
        tvOrderTotal.setText("" + totalAmount);
    }

    /**
     * @param view
     */
    public void checkout(View view) {
        new AsyncRequest().execute();

        dropInRequest = new DropInRequest()
                .tokenizationKey(tokenizationKey)
                .collectDeviceData(true);

        startActivityForResult(dropInRequest.getIntent(OrderConfirmationActivity.this), REQUEST_CODE);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /************Braintree & Paypal Integration Start************/
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                deviceData = result.getDeviceData();
                nonce = result.getPaymentMethodNonce().getNonce();

                try {
                    braintreeFragment = BraintreeFragment.newInstance(OrderConfirmationActivity.this, tokenizationKey);
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
            } else if (resultCode == RESULT_CANCELED) {
                // the user cancelled
            } else {
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

    /**
     * @param requestCode
     */
    @Override
    public void onCancel(int requestCode) {
        // Use this to handle a canceled activity, if the given requestCode is important.
        // You may want to use this callback to hide loading indicators, and prepare your UI for input
    }

    /**
     * @param error
     */
    @Override
    public void onError(Exception error) {
        if (error instanceof ErrorWithResponse) {
            ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;
            BraintreeError cardErrors = ((ErrorWithResponse) error).errorFor("creditCard");
            if (cardErrors != null) {
                // There is an issue with the credit card
                BraintreeError expirationMonthError = cardErrors.errorFor("expirationMonth");
                if (expirationMonthError != null) {
                    // There is an issue with the expiration month.
                    Toast.makeText(OrderConfirmationActivity.this, "Error: " + expirationMonthError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /************Braintree & Paypal Integration End************/

    private class AsyncRequest extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        /**
         * @param params
         * @return
         */
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

        /**
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            /************Braintree & Paypal Integration Start************/
            clientToken = s;
            /************Braintree & Paypal Integration End************/
        }
    }

    private class AsyncPost extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;
        String data = null;

        /**
         * @param params
         * @return
         */
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
                        URLEncoder.encode("" + totalAmount, "UTF-8");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }

            try {
                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
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
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        /**
         * @param status
         */
        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);
            Log.i("Status", status);
            if (status != null && status.equals("submitted_for_settlement")) {
                if (updateOrderDetails()) ;
            } else if (status != null && status.equals("processor_declined")) {
                Toast.makeText(OrderConfirmationActivity.this, "There was a problem processing your card; please double check your payment information and try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (orderType.equals("service")) {
                    Intent intent = new Intent(OrderConfirmationActivity.this, CounsellingFormActivity.class);
                    intent.putExtra("packagetype", packageType);
                    intent.putExtra("amount", "" + (int) originalAmount);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("briefProblem", briefProblem);
                    intent.putExtra("detailedProblem", detailedProblem);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("address", address);
                    intent.putExtra("city", city);
                    intent.putExtra("state", state);
                    intent.putExtra("pincode", pincode);
                    intent.putExtra("isMedication", isMedication);
                    finish();
                    startActivity(intent);
                } else if (orderType.equals("product")) {
                    Intent intent = new Intent(OrderConfirmationActivity.this, ProductDescriptionActivity.class);
                    intent.putExtra("title", productName);
                    intent.putExtra("shortDescription", productDescription);
                    finish();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OrderConfirmationActivity.this, MedicinesActivity.class);
                    finish();
                    startActivity(intent);
                }
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
        if (orderType.equals("service")) {
            Intent intent = new Intent(OrderConfirmationActivity.this, CounsellingFormActivity.class);
            intent.putExtra("packagetype", packageType);
            intent.putExtra("amount", "" + (int) originalAmount);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("briefProblem", briefProblem);
            intent.putExtra("detailedProblem", detailedProblem);
            intent.putExtra("date", date);
            intent.putExtra("time", time);
            intent.putExtra("address", address);
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            intent.putExtra("pincode", pincode);
            intent.putExtra("isMedication", isMedication);
            finish();
            startActivity(intent);
        } else if (orderType.equals("product")) {
            Intent intent = new Intent(OrderConfirmationActivity.this, ProductDescriptionActivity.class);
            intent.putExtra("title", productName);
            intent.putExtra("shortDescription", productDescription);
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(OrderConfirmationActivity.this, MedicinesActivity.class);
            finish();
            startActivity(intent);
        }
    }

    /**
     * @return
     */
    private boolean updateOrderDetails() {
        mReference = FirebaseDatabase.getInstance().getReference("currentorderid");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentOrderId = Integer.parseInt(dataSnapshot.getValue().toString());
                currentOrderId += 1;

                stat = stat ? false : true;
                if (callDatabase()) {
                    sendInvoiceMail(currentOrderId);
                    val = true;
                    finish();
                    Toast.makeText(OrderConfirmationActivity.this, "Your payment has been done successfully.", Toast.LENGTH_LONG).show();
                    if (orderType.equalsIgnoreCase("service")) {
                        Intent intent = new Intent(OrderConfirmationActivity.this, HomeMenuActivity.class);
                        intent.putExtra("active_activity", "contentCounselling");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(OrderConfirmationActivity.this, OrderSuccessfulActivity.class);
                        intent.putExtra("orderNo", currentOrderId);
                        intent.putExtra("isProduct", (isMedication.equals("true") || orderType.equals("product")) ? "true" : "false");
                        startActivity(intent);
                    }
                }
            }

            /**
             * @param databaseError
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ordersReference = FirebaseDatabase.getInstance().getReference("orders");
        return val;
    }

    /**
     * @return
     */
    private boolean callDatabase() {
        if (stat) {
            switch (serviceType) {
                case "long":
                    serviceProblem = briefProblem;
                    break;
                case "short":
                    serviceProblem = detailedProblem;
                    break;
                default:
                    serviceProblem = "NA";
                    break;
            }

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());

            Order order = new Order(address, city, state, pincode, email, name, "" + shippingCharge, "" + calculatedTax, "" + medicineCharge
                    , productName, "" + originalAmount, "", formattedDate, "" + totalAmount, orderType, "" + taxPercent, serviceType, serviceProblem);

            ordersReference.child("" + currentOrderId).setValue(order);
            mReference.setValue("" + currentOrderId);
            return true;
        }
        return false;
    }

    /**
     * @param orderNo
     */
    private void sendInvoiceMail(int orderNo) {
        String subject = "Brainwave Order No. " + orderNo;
        String message = "";
        if (orderType.equalsIgnoreCase("service")) {
            switch (serviceType) {
                case "long":
                    message = "Hello, \n\nAn order has been placed by " + email + " for the following service. " +
                            "\n\nService Type: " + serviceType +
                            "\nName: " + name +
                            "\nEmail: " + email +
                            "\nDate of Appointment: " + date +
                            "\nTime of Appointment: " + time +
                            "\nProblem in brief: " + serviceProblem +
                            "\n\nRegards,\n " + name;
                    break;
                case "short":
                    message = "Hello, \n\nAn order has been placed by " + email + " for the following service. " +
                            "\n\nService Type: " + serviceType +
                            "\nName: " + name +
                            "\nEmail: " + email +
                            "\nProblem in detail: " + serviceProblem +
                            "\n\nRegards,\n " + name;
                    break;
            }
            String userMessage = "Dear " + name + ", \n\n Thank you for using Brainwave. Your order has been placed successfully.\n\n Regards,\n Brainwave Team";
            // Send mail to admin with order invoice details
            new AsyncSendMail().execute(getResources().getString(R.string.adminEmail), subject, message, getResources().getString(R.string.appointmentMailFrom));
            // Send mail to user with order number
            new AsyncSendMail().execute(email, subject, userMessage, getResources().getString(R.string.appointmentMailFrom));
        } else {
            message = "Hello, \n\n An order has been placed for the following product. " +
                    "\n\n Product Name: " + productName +
                    "\n Product Description: " + productDescription +
                    "\n Name: " + name +
                    "\n Email: " + email +
                    "\n Total Amount: " + totalAmount +
                    "\n\n Regards,\n " + name;
            // Send mail to admin with order invoice details
            new AsyncSendMail().execute(getResources().getString(R.string.adminEmail), subject, message, getResources().getString(R.string.appointmentMailFrom));
        }
    }

    private class AsyncSendMail extends AsyncTask<String, String, String> {

        /**
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
            try {
                GMailSender sender = new GMailSender(getResources().getString(R.string.gmailUsername), getResources().getString(R.string.gmailPassword));
                sender.sendMail(params[1],
                        params[2],
                        params[3],
                        params[0]);
            } catch (Exception e) {

            }
            return null;
        }

        /**
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(OrderConfirmationActivity.this, "An invoice of your order has been sent to your mail. Thank you !", Toast.LENGTH_LONG).show();
        }
    }
}