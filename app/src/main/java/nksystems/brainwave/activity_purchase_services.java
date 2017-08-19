package nksystems.brainwave;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.api.AndroidPay;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.Card;
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
import com.braintreepayments.api.models.CardBuilder;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.cardform.view.CardForm;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentMethodTokenizationType;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.payUMoney.sdk.SdkConstants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import nksystems.brainwave.util.IabHelper;
import nksystems.brainwave.util.IabResult;
import nksystems.brainwave.util.Inventory;
import nksystems.brainwave.util.Purchase;

/**
 * Created by Charmy on 13/05/2017.
 */

public class activity_purchase_services extends AppCompatActivity
        implements PaymentMethodNonceCreatedListener,BraintreeCancelListener,BraintreeErrorListener{

    private static final String TAG = "Purchase Services";
    IabHelper mHelper;
    static final String ITEM_SKU = "android.test.purchased";
    private Button clickButton;
    private Button buyButton;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener;
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;
    BraintreeFragment braintreeFragment;
    String tokenizationKey = "sandbox_6w3f54dk_jvrhmcgz2n7fydvy";
    private static final int REQUEST_CODE = 1;
    SupportWalletFragment walletFragment;
    GoogleApiClient mGoogleApiClient;

    private static final String TAG_SUCCESS = "success";
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    /************Braintree & Paypal Integration Start************/
    private static String request_url = "http://192.168.0.104:81/braintree/request";
    private static String response_url = "http://192.168.0.104:81/braintree/response";
    String clientToken;
    DropInRequest dropInRequest;
    String nonce, deviceData;
    /************Braintree & Paypal Integration End************/

    /************PayU Money Integration Start************/
    /*private static String request_url = "http://192.168.0.104:81/payu/request";*/
    /*private static String request_url = "https://test.payu.in/_payment";
    private static String response_url = "http://192.168.0.104:81/payu/response";
    PayUmoneySdkInitilizer.PaymentParam.Builder builder;
    String hashRequest, hashResponse;*/
    /************PayU Money Integration End************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.purchase_services_layout);

        buyButton=(Button)findViewById(R.id.buyButton);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*String base64EncodedPublicKey = getResources().getString(R.string.license_key);
        buyButton = (Button)findViewById(R.id.buyButton);
        clickButton = (Button)findViewById(R.id.clickButton);
        clickButton.setEnabled(false);

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result)
                                       {
                                           if (!result.isSuccess()) {
                                               Log.d(TAG, "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d(TAG, "In-app Billing is set up OK");
                                           }
                                       }
                                   });

        mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result,
                                              Purchase purchase)
            {
                if (result.isFailure()) {
                    // Handle error
                    return;
                }
                else if (purchase.getSku().equals(ITEM_SKU)) {
                    consumeItem();
                    buyButton.setEnabled(false);
                }

            }
        };

        mConsumeFinishedListener =
                new IabHelper.OnConsumeFinishedListener() {
                    public void onConsumeFinished(Purchase purchase,
                                                  IabResult result) {

                        if (result.isSuccess()) {
                            clickButton.setEnabled(true);
                        } else {
                            // handle error
                        }
                    }
                };

        mReceivedInventoryListener
                = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result,
                                                 Inventory inventory) {


                if (result.isFailure()) {
                    // Handle failure
                } else {
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                                mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        e.printStackTrace();
                    }
                }
            }
        };*/
    }

    public void buyClick(View view) {

        /************PayU Money Integration Start************/
        /*// for Live mode - setIsDebug(false)
        builder  = new
                PayUmoneySdkInitilizer.PaymentParam.Builder().setMerchantId("5824090").
                setKey("JBZaLc").setIsDebug(true).setAmount(10).setTnxId("0nf7" + System.currentTimeMillis()).
                setPhone("8779383423").setProductName("product_name").setFirstName("Charmy").setEmail("charmyshah91@gmail.com").
                setsUrl("https://www.PayUmoney.com/mobileapp/PayUmoney/success.php").
                setfUrl("https://www.PayUmoney.com/mobileapp/PayUmoney/failure.php").
                setUdf1("").setUdf2("").setUdf3("").setUdf4("").setUdf5("");

        new AsyncRequest().execute();
        new AsyncResponse().execute();*/
        /************PayU Money Integration End************/

        /************Braintree & Paypal Integration Start************/
        new AsyncRequest().execute();

        dropInRequest = new DropInRequest()
                .tokenizationKey(tokenizationKey)
                .collectDeviceData(true);

        startActivityForResult(dropInRequest.getIntent(activity_purchase_services.this), REQUEST_CODE);
        /************Braintree & Paypal Integration End************/
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /************PayU Money Integration Start************/
        /*if(requestCode ==  PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Log.i(TAG, "Success - Payment ID : " + data.getStringExtra(SdkConstants.PAYMENT_ID));
                String paymentId = data.getStringExtra(SdkConstants.PAYMENT_ID);}
            else if (resultCode == RESULT_CANCELED)
                Log.i(TAG, "cancelled");
            else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED)
                Log.i(TAG, "failure");
            else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK)
                Log.i(TAG, "User returned without login");
        }*/
        /************PayU Money Integration End************/

        /************Braintree & Paypal Integration Start************/
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                deviceData = result.getDeviceData();
                nonce = result.getPaymentMethodNonce().getNonce();

                try {
                    braintreeFragment = BraintreeFragment.newInstance(activity_purchase_services.this, tokenizationKey);
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

    /*public void consumeItem() {
        try {
            mHelper.queryInventoryAsync(mReceivedInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void buttonClicked (View view)
    {
        clickButton.setEnabled(false);
        buyButton.setEnabled(true);
    }

    public void buyClick(View view) {
        try {
            mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001,
                    mPurchaseFinishedListener, "mypurchasetoken");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            if (mHelper != null) mHelper.dispose();
            mHelper = null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/

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
                    Toast.makeText(activity_purchase_services.this, "Error: " + expirationMonthError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /************Braintree & Paypal Integration End************/

    private class AsyncRequest extends AsyncTask<String, String, String>{

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

            /************PayU Money Integration Start************/
            /*PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();
            paymentParam.setMerchantHash(s);

            PayUmoneySdkInitilizer.startPaymentActivityForResult(activity_purchase_services.this, paymentParam);*/
            /************PayU Money Integration End************/
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
                url = new URL("http://192.168.0.107:81/braintree/nonce");

                data = URLEncoder.encode("payment_method_nonce", "UTF-8") + "=" +
                        URLEncoder.encode(nonce, "UTF-8");

                data += "&" + URLEncoder.encode("device_data", "UTF-8") + "=" +
                        URLEncoder.encode(deviceData, "UTF-8");

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(activity_purchase_services.this, "Result: " + s, Toast.LENGTH_LONG).show();
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
}
