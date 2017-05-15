package nksystems.brainwave;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import nksystems.brainwave.util.IabHelper;
import nksystems.brainwave.util.IabResult;
import nksystems.brainwave.util.Inventory;
import nksystems.brainwave.util.Purchase;

/**
 * Created by Charmy on 13/05/2017.
 */

public class activity_purchase_services extends AppCompatActivity {

    private static final String TAG = "Purchase Services";
    IabHelper mHelper;
    static final String ITEM_SKU = "android.test.purchased";
    private Button clickButton;
    private Button buyButton;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener;
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_services_layout);
        String base64EncodedPublicKey = getResources().getString(R.string.license_key);
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
        };
    }

    public void consumeItem() {
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
    }
}
