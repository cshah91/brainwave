package nksystems.brainwave;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;

public class activity_loading extends AppCompatActivity {

    LottieAnimationView animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getSupportActionBar().hide();

        animView=(LottieAnimationView)findViewById(R.id.animation_view);
        animView.setImageAssetsFolder("assets");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
                Intent intent=new Intent(activity_loading.this,activity_home_menu.class);
                intent.putExtra("active_activity","");
                finish();
                startActivity(intent);
            }
        }, 3000);


    }
}
