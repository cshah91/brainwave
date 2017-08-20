package nksystems.brainwave;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

/**
 * This class is used for displaying the loading screen at the start of the app
 *
 * @author Nemi Shah
 * @version 1.0
 * @date 24-04-2017
 */
public class LoadingActivity extends AppCompatActivity {

    LottieAnimationView animView;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getSupportActionBar().hide();

        animView = (LottieAnimationView) findViewById(R.id.animation_view);
        animView.setImageAssetsFolder("assets");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
                Intent intent = new Intent(LoadingActivity.this, HomeMenuActivity.class);
                intent.putExtra("active_activity", "");
                finish();
                startActivity(intent);
            }
        }, 3000);
    }
}
