package nksystems.brainwave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class activity_loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent intent=new Intent(this,activity_home_menu.class);
        intent.putExtra("active_activity","");
        finish();
        startActivity(intent);
    }
}
