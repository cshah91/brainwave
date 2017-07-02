package nksystems.brainwave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class activity_order_successful extends AppCompatActivity {

    String totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);

        totalAmount = getIntent().getStringExtra("amount");
        Toast.makeText(activity_order_successful.this, "Your order of " + totalAmount + " has been placed successfully.", Toast.LENGTH_LONG).show();
    }
}
