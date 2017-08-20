package nksystems.brainwave;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class is used for displaying the different services being offered with its contents
 *
 * @author  Charmy Shah
 * @date    27-05-2017
 * @version 1.0
 */
public class AboutServicesActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cvService1, cvService2, cvService3, cvService4, cvService5, cvService6, cvService7, cvService8;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about_services);

        cvService1 = (CardView) findViewById(R.id.cvService1);
        cvService2 = (CardView) findViewById(R.id.cvService2);
        cvService3 = (CardView) findViewById(R.id.cvService3);
        cvService4 = (CardView) findViewById(R.id.cvService4);
        cvService5 = (CardView) findViewById(R.id.cvService5);
        cvService6 = (CardView) findViewById(R.id.cvService6);
        cvService7 = (CardView) findViewById(R.id.cvService7);
        cvService8 = (CardView) findViewById(R.id.cvService8);

        cvService1.setOnClickListener(this);
        cvService2.setOnClickListener(this);
        cvService3.setOnClickListener(this);
        cvService4.setOnClickListener(this);
        cvService5.setOnClickListener(this);
        cvService6.setOnClickListener(this);
        cvService7.setOnClickListener(this);
        cvService8.setOnClickListener(this);
    }

    /**
     * Show dialog with service title and its description
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        boolean showDialog = true;
        Dialog dialog = new Dialog(AboutServicesActivity.this, R.style.custom_dialog_services_style);
        dialog.setContentView(R.layout.dialog_counselling_service);
        TextView viewTitle = (TextView) dialog.findViewById(R.id.viewServiceTitle);

        LinearLayout llSvContent2 = (LinearLayout) dialog.findViewById(R.id.llSvContent2);
        LinearLayout llSvContent3 = (LinearLayout) dialog.findViewById(R.id.llSvContent3);
        LinearLayout llSvContent4 = (LinearLayout) dialog.findViewById(R.id.llSvContent4);
        LinearLayout llSvContent5 = (LinearLayout) dialog.findViewById(R.id.llSvContent5);
        LinearLayout llSvContent6 = (LinearLayout) dialog.findViewById(R.id.llSvContent6);

        TextView viewSvBullets22 = (TextView) dialog.findViewById(R.id.viewSvBullets22);
        TextView viewSvBullets43 = (TextView) dialog.findViewById(R.id.viewSvBullets43);
        TextView viewSvBullets44 = (TextView) dialog.findViewById(R.id.viewSvBullets44);
        TextView viewSvBullets55 = (TextView) dialog.findViewById(R.id.viewSvBullets55);
        TextView viewSvBullets56 = (TextView) dialog.findViewById(R.id.viewSvBullets56);
        TextView viewSvBullets64 = (TextView) dialog.findViewById(R.id.viewSvBullets64);
        TextView viewSvBullets67 = (TextView) dialog.findViewById(R.id.viewSvBullets67);
        TextView viewSvBullets68 = (TextView) dialog.findViewById(R.id.viewSvBullets68);

        switch (v.getId()) {
            case R.id.cvService1:
                viewTitle.setText(getResources().getString(R.string.service1));
                break;
            case R.id.cvService2:
                viewTitle.setText(getResources().getString(R.string.service2));

                CharSequence cs_bull_2_2_1 = getResources().getString(R.string.sv_bull_2_2_1);
                SpannableString sp_bull_2_2_1 = new SpannableString(cs_bull_2_2_1);
                sp_bull_2_2_1.setSpan(new BulletSpan(15), 0, cs_bull_2_2_1.length(), 0);

                CharSequence cs_bull_2_2_2 = getResources().getString(R.string.sv_bull_2_2_2);
                SpannableString sp_bull_2_2_2 = new SpannableString(cs_bull_2_2_2);
                sp_bull_2_2_2.setSpan(new BulletSpan(15), 0, cs_bull_2_2_2.length(), 0);

                CharSequence cs_bull_2_2_3 = getResources().getString(R.string.sv_bull_2_2_3);
                SpannableString sp_bull_2_2_3 = new SpannableString(cs_bull_2_2_3);
                sp_bull_2_2_3.setSpan(new BulletSpan(15), 0, cs_bull_2_2_3.length(), 0);

                CharSequence cs_bull_2_2_4 = getResources().getString(R.string.sv_bull_2_2_4);
                SpannableString sp_bull_2_2_4 = new SpannableString(cs_bull_2_2_4);
                sp_bull_2_2_4.setSpan(new BulletSpan(15), 0, cs_bull_2_2_4.length(), 0);

                viewSvBullets22.setText(TextUtils.concat(sp_bull_2_2_1, "\n", sp_bull_2_2_2, "\n", sp_bull_2_2_3, "\n",
                        sp_bull_2_2_4));
                llSvContent2.setVisibility(View.VISIBLE);

                break;
            case R.id.cvService3:
                viewTitle.setText(getResources().getString(R.string.service3));
                llSvContent3.setVisibility(View.VISIBLE);
                break;
            case R.id.cvService4:
                viewTitle.setText(getResources().getString(R.string.service4));

                CharSequence cs_bull_4_3_1 = getResources().getString(R.string.sv_bull_4_3_1);
                SpannableString sp_bull_4_3_1 = new SpannableString(cs_bull_4_3_1);
                sp_bull_4_3_1.setSpan(new BulletSpan(15), 0, cs_bull_4_3_1.length(), 0);

                CharSequence cs_bull_4_3_2 = getResources().getString(R.string.sv_bull_4_3_2);
                SpannableString sp_bull_4_3_2 = new SpannableString(cs_bull_4_3_2);
                sp_bull_4_3_2.setSpan(new BulletSpan(15), 0, cs_bull_4_3_2.length(), 0);

                CharSequence cs_bull_4_3_3 = getResources().getString(R.string.sv_bull_4_3_3);
                SpannableString sp_bull_4_3_3 = new SpannableString(cs_bull_4_3_3);
                sp_bull_4_3_3.setSpan(new BulletSpan(15), 0, cs_bull_4_3_3.length(), 0);

                CharSequence cs_bull_4_3_4 = getResources().getString(R.string.sv_bull_4_3_4);
                SpannableString sp_bull_4_3_4 = new SpannableString(cs_bull_4_3_4);
                sp_bull_4_3_4.setSpan(new BulletSpan(15), 0, cs_bull_4_3_4.length(), 0);

                viewSvBullets43.setText(TextUtils.concat(sp_bull_4_3_1, "\n", sp_bull_4_3_2, "\n", sp_bull_4_3_3, "\n",
                        sp_bull_4_3_4));

                CharSequence cs_bull_4_4_1 = getResources().getString(R.string.sv_bull_4_4_1);
                SpannableString sp_bull_4_4_1 = new SpannableString(cs_bull_4_4_1);
                sp_bull_4_4_1.setSpan(new BulletSpan(15), 0, cs_bull_4_4_1.length(), 0);

                CharSequence cs_bull_4_4_2 = getResources().getString(R.string.sv_bull_4_4_2);
                SpannableString sp_bull_4_4_2 = new SpannableString(cs_bull_4_4_2);
                sp_bull_4_4_2.setSpan(new BulletSpan(15), 0, cs_bull_4_4_2.length(), 0);

                CharSequence cs_bull_4_4_3 = getResources().getString(R.string.sv_bull_4_4_3);
                SpannableString sp_bull_4_4_3 = new SpannableString(cs_bull_4_4_3);
                sp_bull_4_4_3.setSpan(new BulletSpan(15), 0, cs_bull_4_4_3.length(), 0);

                CharSequence cs_bull_4_4_4 = getResources().getString(R.string.sv_bull_4_4_4);
                SpannableString sp_bull_4_4_4 = new SpannableString(cs_bull_4_4_4);
                sp_bull_4_4_4.setSpan(new BulletSpan(15), 0, cs_bull_4_4_4.length(), 0);

                CharSequence cs_bull_4_4_5 = getResources().getString(R.string.sv_bull_4_4_5);
                SpannableString sp_bull_4_4_5 = new SpannableString(cs_bull_4_4_5);
                sp_bull_4_4_5.setSpan(new BulletSpan(15), 0, cs_bull_4_4_5.length(), 0);

                CharSequence cs_bull_4_4_6 = getResources().getString(R.string.sv_bull_4_4_6);
                SpannableString sp_bull_4_4_6 = new SpannableString(cs_bull_4_4_6);
                sp_bull_4_4_6.setSpan(new BulletSpan(15), 0, cs_bull_4_4_6.length(), 0);

                viewSvBullets44.setText(TextUtils.concat(sp_bull_4_4_1, "\n", sp_bull_4_4_2, "\n", sp_bull_4_4_3, "\n",
                        sp_bull_4_4_4, "\n", sp_bull_4_4_5, "\n", sp_bull_4_4_6));

                llSvContent4.setVisibility(View.VISIBLE);
                break;
            case R.id.cvService5:
                viewTitle.setText(getResources().getString(R.string.service5));

                CharSequence cs_bull_5_5_1 = getResources().getString(R.string.sv_bull_5_5_1);
                SpannableString sp_bull_5_5_1 = new SpannableString(cs_bull_5_5_1);
                sp_bull_5_5_1.setSpan(new BulletSpan(15), 0, cs_bull_5_5_1.length(), 0);

                CharSequence cs_bull_5_5_2 = getResources().getString(R.string.sv_bull_5_5_2);
                SpannableString sp_bull_5_5_2 = new SpannableString(cs_bull_5_5_2);
                sp_bull_5_5_2.setSpan(new BulletSpan(15), 0, cs_bull_5_5_2.length(), 0);

                CharSequence cs_bull_5_5_3 = getResources().getString(R.string.sv_bull_5_5_3);
                SpannableString sp_bull_5_5_3 = new SpannableString(cs_bull_5_5_3);
                sp_bull_5_5_3.setSpan(new BulletSpan(15), 0, cs_bull_5_5_3.length(), 0);

                CharSequence cs_bull_5_5_4 = getResources().getString(R.string.sv_bull_5_5_4);
                SpannableString sp_bull_5_5_4 = new SpannableString(cs_bull_5_5_4);
                sp_bull_5_5_4.setSpan(new BulletSpan(15), 0, cs_bull_5_5_4.length(), 0);

                CharSequence cs_bull_5_5_5 = getResources().getString(R.string.sv_bull_5_5_5);
                SpannableString sp_bull_5_5_5 = new SpannableString(cs_bull_5_5_5);
                sp_bull_5_5_5.setSpan(new BulletSpan(15), 0, cs_bull_5_5_5.length(), 0);

                CharSequence cs_bull_5_5_6 = getResources().getString(R.string.sv_bull_5_5_6);
                SpannableString sp_bull_5_5_6 = new SpannableString(cs_bull_5_5_6);
                sp_bull_5_5_6.setSpan(new BulletSpan(15), 0, cs_bull_5_5_6.length(), 0);

                CharSequence cs_bull_5_5_7 = getResources().getString(R.string.sv_bull_5_5_7);
                SpannableString sp_bull_5_5_7 = new SpannableString(cs_bull_5_5_7);
                sp_bull_5_5_7.setSpan(new BulletSpan(15), 0, cs_bull_5_5_7.length(), 0);

                CharSequence cs_bull_5_5_8 = getResources().getString(R.string.sv_bull_5_5_8);
                SpannableString sp_bull_5_5_8 = new SpannableString(cs_bull_5_5_8);
                sp_bull_5_5_8.setSpan(new BulletSpan(15), 0, cs_bull_5_5_8.length(), 0);

                CharSequence cs_bull_5_5_9 = getResources().getString(R.string.sv_bull_5_5_9);
                SpannableString sp_bull_5_5_9 = new SpannableString(cs_bull_5_5_9);
                sp_bull_5_5_9.setSpan(new BulletSpan(15), 0, cs_bull_5_5_9.length(), 0);

                CharSequence cs_bull_5_5_10 = getResources().getString(R.string.sv_bull_5_5_10);
                SpannableString sp_bull_5_5_10 = new SpannableString(cs_bull_5_5_10);
                sp_bull_5_5_10.setSpan(new BulletSpan(15), 0, cs_bull_5_5_10.length(), 0);

                CharSequence cs_bull_5_5_11 = getResources().getString(R.string.sv_bull_5_5_11);
                SpannableString sp_bull_5_5_11 = new SpannableString(cs_bull_5_5_11);
                sp_bull_5_5_11.setSpan(new BulletSpan(15), 0, cs_bull_5_5_11.length(), 0);

                CharSequence cs_bull_5_5_12 = getResources().getString(R.string.sv_bull_5_5_12);
                SpannableString sp_bull_5_5_12 = new SpannableString(cs_bull_5_5_12);
                sp_bull_5_5_12.setSpan(new BulletSpan(15), 0, cs_bull_5_5_12.length(), 0);

                CharSequence cs_bull_5_5_13 = getResources().getString(R.string.sv_bull_5_5_13);
                SpannableString sp_bull_5_5_13 = new SpannableString(cs_bull_5_5_13);
                sp_bull_5_5_13.setSpan(new BulletSpan(15), 0, cs_bull_5_5_13.length(), 0);

                CharSequence cs_bull_5_5_14 = getResources().getString(R.string.sv_bull_5_5_14);
                SpannableString sp_bull_5_5_14 = new SpannableString(cs_bull_5_5_14);
                sp_bull_5_5_14.setSpan(new BulletSpan(15), 0, cs_bull_5_5_14.length(), 0);

                CharSequence cs_bull_5_5_15 = getResources().getString(R.string.sv_bull_5_5_15);
                SpannableString sp_bull_5_5_15 = new SpannableString(cs_bull_5_5_15);
                sp_bull_5_5_15.setSpan(new BulletSpan(15), 0, cs_bull_5_5_15.length(), 0);

                viewSvBullets55.setText(TextUtils.concat(sp_bull_5_5_1, "\n", sp_bull_5_5_2, "\n", sp_bull_5_5_3, "\n",
                        sp_bull_5_5_4, "\n", sp_bull_5_5_5, "\n", sp_bull_5_5_6, "\n", sp_bull_5_5_7, "\n",
                        sp_bull_5_5_8, "\n", sp_bull_5_5_9, "\n", sp_bull_5_5_10, "\n", sp_bull_5_5_11, "\n",
                        sp_bull_5_5_12, "\n", sp_bull_5_5_13, "\n", sp_bull_5_5_14, "\n", sp_bull_5_5_15));

                CharSequence cs_bull_5_6_1 = getResources().getString(R.string.sv_bull_5_6_1);
                SpannableString sp_bull_5_6_1 = new SpannableString(cs_bull_5_6_1);
                sp_bull_5_6_1.setSpan(new BulletSpan(15), 0, cs_bull_5_6_1.length(), 0);

                CharSequence cs_bull_5_6_2 = getResources().getString(R.string.sv_bull_5_6_2);
                SpannableString sp_bull_5_6_2 = new SpannableString(cs_bull_5_6_2);
                sp_bull_5_6_2.setSpan(new BulletSpan(15), 0, cs_bull_5_6_2.length(), 0);

                CharSequence cs_bull_5_6_3 = getResources().getString(R.string.sv_bull_5_6_3);
                SpannableString sp_bull_5_6_3 = new SpannableString(cs_bull_5_6_3);
                sp_bull_5_6_3.setSpan(new BulletSpan(15), 0, cs_bull_5_6_3.length(), 0);

                viewSvBullets56.setText(TextUtils.concat(sp_bull_5_6_1, "\n", sp_bull_5_6_2, "\n", sp_bull_5_6_3));

                llSvContent5.setVisibility(View.VISIBLE);
                break;
            case R.id.cvService6:
                viewTitle.setText(getResources().getString(R.string.service6));

                CharSequence cs_bull_6_4_1 = getResources().getString(R.string.sv_bull_6_4_1);
                SpannableString sp_bull_6_4_1 = new SpannableString(cs_bull_6_4_1);
                sp_bull_6_4_1.setSpan(new BulletSpan(15), 0, cs_bull_6_4_1.length(), 0);

                CharSequence cs_bull_6_4_2 = getResources().getString(R.string.sv_bull_6_4_2);
                SpannableString sp_bull_6_4_2 = new SpannableString(cs_bull_6_4_2);
                sp_bull_6_4_2.setSpan(new BulletSpan(15), 0, cs_bull_6_4_2.length(), 0);

                CharSequence cs_bull_6_4_3 = getResources().getString(R.string.sv_bull_6_4_3);
                SpannableString sp_bull_6_4_3 = new SpannableString(cs_bull_6_4_3);
                sp_bull_6_4_3.setSpan(new BulletSpan(15), 0, cs_bull_6_4_3.length(), 0);

                CharSequence cs_bull_6_4_4 = getResources().getString(R.string.sv_bull_6_4_4);
                SpannableString sp_bull_6_4_4 = new SpannableString(cs_bull_6_4_4);
                sp_bull_6_4_4.setSpan(new BulletSpan(15), 0, cs_bull_6_4_4.length(), 0);

                CharSequence cs_bull_6_4_5 = getResources().getString(R.string.sv_bull_6_4_5);
                SpannableString sp_bull_6_4_5 = new SpannableString(cs_bull_6_4_5);
                sp_bull_6_4_5.setSpan(new BulletSpan(15), 0, cs_bull_6_4_5.length(), 0);

                CharSequence cs_bull_6_4_6 = getResources().getString(R.string.sv_bull_6_4_6);
                SpannableString sp_bull_6_4_6 = new SpannableString(cs_bull_6_4_6);
                sp_bull_6_4_6.setSpan(new BulletSpan(15), 0, cs_bull_6_4_6.length(), 0);

                CharSequence cs_bull_6_4_7 = getResources().getString(R.string.sv_bull_6_4_7);
                SpannableString sp_bull_6_4_7 = new SpannableString(cs_bull_6_4_7);
                sp_bull_6_4_7.setSpan(new BulletSpan(15), 0, cs_bull_6_4_7.length(), 0);

                CharSequence cs_bull_6_4_8 = getResources().getString(R.string.sv_bull_6_4_8);
                SpannableString sp_bull_6_4_8 = new SpannableString(cs_bull_6_4_8);
                sp_bull_6_4_8.setSpan(new BulletSpan(15), 0, cs_bull_6_4_8.length(), 0);

                CharSequence cs_bull_6_4_9 = getResources().getString(R.string.sv_bull_6_4_9);
                SpannableString sp_bull_6_4_9 = new SpannableString(cs_bull_6_4_9);
                sp_bull_6_4_9.setSpan(new BulletSpan(15), 0, cs_bull_6_4_9.length(), 0);

                CharSequence cs_bull_6_4_10 = getResources().getString(R.string.sv_bull_6_4_10);
                SpannableString sp_bull_6_4_10 = new SpannableString(cs_bull_6_4_10);
                sp_bull_6_4_10.setSpan(new BulletSpan(15), 0, cs_bull_6_4_10.length(), 0);

                viewSvBullets64.setText(TextUtils.concat(sp_bull_6_4_1, "\n", sp_bull_6_4_2, "\n", sp_bull_6_4_3, "\n",
                        sp_bull_6_4_4, "\n", sp_bull_6_4_5, "\n", sp_bull_6_4_6, "\n", sp_bull_6_4_7, "\n",
                        sp_bull_6_4_8, "\n", sp_bull_6_4_9, "\n", sp_bull_6_4_10));

                CharSequence cs_bull_6_7_1 = getResources().getString(R.string.sv_bull_6_7_1);
                SpannableString sp_bull_6_7_1 = new SpannableString(cs_bull_6_7_1);
                sp_bull_6_7_1.setSpan(new BulletSpan(15), 0, cs_bull_6_7_1.length(), 0);

                CharSequence cs_bull_6_7_2 = getResources().getString(R.string.sv_bull_6_7_2);
                SpannableString sp_bull_6_7_2 = new SpannableString(cs_bull_6_7_2);
                sp_bull_6_7_2.setSpan(new BulletSpan(15), 0, cs_bull_6_7_2.length(), 0);

                CharSequence cs_bull_6_7_3 = getResources().getString(R.string.sv_bull_6_7_3);
                SpannableString sp_bull_6_7_3 = new SpannableString(cs_bull_6_7_3);
                sp_bull_6_7_3.setSpan(new BulletSpan(15), 0, cs_bull_6_7_3.length(), 0);

                CharSequence cs_bull_6_7_4 = getResources().getString(R.string.sv_bull_6_7_4);
                SpannableString sp_bull_6_7_4 = new SpannableString(cs_bull_6_7_4);
                sp_bull_6_7_4.setSpan(new BulletSpan(15), 0, cs_bull_6_7_4.length(), 0);

                CharSequence cs_bull_6_7_5 = getResources().getString(R.string.sv_bull_6_7_5);
                SpannableString sp_bull_6_7_5 = new SpannableString(cs_bull_6_7_5);
                sp_bull_6_7_5.setSpan(new BulletSpan(15), 0, cs_bull_6_7_5.length(), 0);

                CharSequence cs_bull_6_7_6 = getResources().getString(R.string.sv_bull_6_7_6);
                SpannableString sp_bull_6_7_6 = new SpannableString(cs_bull_6_7_6);
                sp_bull_6_7_6.setSpan(new BulletSpan(15), 0, cs_bull_6_7_6.length(), 0);

                CharSequence cs_bull_6_7_7 = getResources().getString(R.string.sv_bull_6_7_7);
                SpannableString sp_bull_6_7_7 = new SpannableString(cs_bull_6_7_7);
                sp_bull_6_7_7.setSpan(new BulletSpan(15), 0, cs_bull_6_7_7.length(), 0);

                CharSequence cs_bull_6_7_8 = getResources().getString(R.string.sv_bull_6_7_8);
                SpannableString sp_bull_6_7_8 = new SpannableString(cs_bull_6_7_8);
                sp_bull_6_7_8.setSpan(new BulletSpan(15), 0, cs_bull_6_7_8.length(), 0);

                CharSequence cs_bull_6_7_9 = getResources().getString(R.string.sv_bull_6_7_9);
                SpannableString sp_bull_6_7_9 = new SpannableString(cs_bull_6_7_9);
                sp_bull_6_7_9.setSpan(new BulletSpan(15), 0, cs_bull_6_7_9.length(), 0);

                CharSequence cs_bull_6_7_10 = getResources().getString(R.string.sv_bull_6_7_10);
                SpannableString sp_bull_6_7_10 = new SpannableString(cs_bull_6_7_10);
                sp_bull_6_7_10.setSpan(new BulletSpan(15), 0, cs_bull_6_7_10.length(), 0);

                CharSequence cs_bull_6_7_11 = getResources().getString(R.string.sv_bull_6_7_11);
                SpannableString sp_bull_6_7_11 = new SpannableString(cs_bull_6_7_11);
                sp_bull_6_7_11.setSpan(new BulletSpan(15), 0, cs_bull_6_7_11.length(), 0);

                CharSequence cs_bull_6_7_12 = getResources().getString(R.string.sv_bull_6_7_12);
                SpannableString sp_bull_6_7_12 = new SpannableString(cs_bull_6_7_12);
                sp_bull_6_7_12.setSpan(new BulletSpan(15), 0, cs_bull_6_7_12.length(), 0);

                CharSequence cs_bull_6_7_13 = getResources().getString(R.string.sv_bull_6_7_13);
                SpannableString sp_bull_6_7_13 = new SpannableString(cs_bull_6_7_13);
                sp_bull_6_7_13.setSpan(new BulletSpan(15), 0, cs_bull_6_7_13.length(), 0);

                CharSequence cs_bull_6_7_14 = getResources().getString(R.string.sv_bull_6_7_14);
                SpannableString sp_bull_6_7_14 = new SpannableString(cs_bull_6_7_14);
                sp_bull_6_7_14.setSpan(new BulletSpan(15), 0, cs_bull_6_7_14.length(), 0);

                CharSequence cs_bull_6_7_15 = getResources().getString(R.string.sv_bull_6_7_15);
                SpannableString sp_bull_6_7_15 = new SpannableString(cs_bull_6_7_15);
                sp_bull_6_7_15.setSpan(new BulletSpan(15), 0, cs_bull_6_7_15.length(), 0);

                CharSequence cs_bull_6_7_16 = getResources().getString(R.string.sv_bull_6_7_16);
                SpannableString sp_bull_6_7_16 = new SpannableString(cs_bull_6_7_16);
                sp_bull_6_7_16.setSpan(new BulletSpan(15), 0, cs_bull_6_7_16.length(), 0);

                CharSequence cs_bull_6_7_17 = getResources().getString(R.string.sv_bull_6_7_17);
                SpannableString sp_bull_6_7_17 = new SpannableString(cs_bull_6_7_17);
                sp_bull_6_7_17.setSpan(new BulletSpan(15), 0, cs_bull_6_7_17.length(), 0);

                viewSvBullets67.setText(TextUtils.concat(sp_bull_6_7_1, "\n", sp_bull_6_7_2, "\n", sp_bull_6_7_3, "\n",
                        sp_bull_6_7_4, "\n", sp_bull_6_7_5, "\n", sp_bull_6_7_6, "\n", sp_bull_6_7_7, "\n",
                        sp_bull_6_7_8, "\n", sp_bull_6_7_9, "\n", sp_bull_6_7_10, "\n", sp_bull_6_7_11, "\n",
                        sp_bull_6_7_12, "\n", sp_bull_6_7_13, "\n", sp_bull_6_7_14, "\n", sp_bull_6_7_15, "\n",
                        sp_bull_6_7_16, "\n", sp_bull_6_7_17));

                CharSequence cs_bull_6_8_1 = getResources().getString(R.string.sv_bull_6_8_1);
                SpannableString sp_bull_6_8_1 = new SpannableString(cs_bull_6_8_1);
                sp_bull_6_8_1.setSpan(new BulletSpan(15), 0, cs_bull_6_8_1.length(), 0);

                CharSequence cs_bull_6_8_2 = getResources().getString(R.string.sv_bull_6_8_2);
                SpannableString sp_bull_6_8_2 = new SpannableString(cs_bull_6_8_2);
                sp_bull_6_8_2.setSpan(new BulletSpan(15), 0, cs_bull_6_8_2.length(), 0);

                CharSequence cs_bull_6_8_3 = getResources().getString(R.string.sv_bull_6_8_3);
                SpannableString sp_bull_6_8_3 = new SpannableString(cs_bull_6_8_3);
                sp_bull_6_8_3.setSpan(new BulletSpan(15), 0, cs_bull_6_8_3.length(), 0);

                viewSvBullets68.setText(TextUtils.concat(sp_bull_6_8_1, "\n", sp_bull_6_8_2, "\n", sp_bull_6_8_3));

                llSvContent6.setVisibility(View.VISIBLE);
                break;
            case R.id.cvService7:
                viewTitle.setText(getResources().getString(R.string.service7));
                break;
            case R.id.cvService8:
                showDialog = false;
                Intent intent = new Intent(AboutServicesActivity.this, WorkshopsActivity.class);
                finish();
                startActivity(intent);
                break;
            default:
                break;
        }

        if (showDialog) {
            dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
            dialog.findViewById(R.id.llServices).setVisibility(View.VISIBLE);
            dialog.show();
        }
    }

    /**
     * Go back to the counselling page on clicking the back arrow
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AboutServicesActivity.this, HomeMenuActivity.class);
                intent.putExtra("active_activity", "contentCounselling");
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    /**
     * Go back to the counselling page on clicking the phone's back key
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AboutServicesActivity.this, HomeMenuActivity.class);
        intent.putExtra("active_activity", "contentCounselling");
        finish();
        startActivity(intent);
    }
}
