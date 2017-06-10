package nksystems.brainwave;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class WorkshopsActivity extends AppCompatActivity implements View.OnClickListener{

    CardView cvWorkshop1,cvWorkshop2,cvWorkshop3,cvWorkshop4,cvWorkshop5,cvWorkshop6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_workshops);

        cvWorkshop1 = (CardView) findViewById(R.id.cvWorkshop1);
        cvWorkshop2 = (CardView) findViewById(R.id.cvWorkshop2);
        cvWorkshop3 = (CardView) findViewById(R.id.cvWorkshop3);
        cvWorkshop4 = (CardView) findViewById(R.id.cvWorkshop4);
        cvWorkshop5 = (CardView) findViewById(R.id.cvWorkshop5);
        cvWorkshop6 = (CardView) findViewById(R.id.cvWorkshop6);

        cvWorkshop1.setOnClickListener(this);
        cvWorkshop2.setOnClickListener(this);
        cvWorkshop3.setOnClickListener(this);
        cvWorkshop4.setOnClickListener(this);
        cvWorkshop5.setOnClickListener(this);
        cvWorkshop6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Dialog dialog = new Dialog(WorkshopsActivity.this, R.style.custom_dialog_services_style);
        dialog.setContentView(R.layout.dialog_services_layout);
        TextView viewTitle = (TextView) dialog.findViewById(R.id.viewServiceTitle);
        TextView textView1 = (TextView) dialog.findViewById(R.id.viewWkBullets1);
        TextView textView2 = (TextView) dialog.findViewById(R.id.viewWkBullets2);
        TextView textView31 = (TextView) dialog.findViewById(R.id.viewWkBullets31);
        TextView textView32 = (TextView) dialog.findViewById(R.id.viewWkBullets32);
        TextView textView61 = (TextView) dialog.findViewById(R.id.viewWkBullets61);
        TextView textView62 = (TextView) dialog.findViewById(R.id.viewWkBullets62);

        TextView wkContent11 = (TextView) dialog.findViewById(R.id.wkContent11);
        TextView wkContent12 = (TextView) dialog.findViewById(R.id.wkContent12);
        TextView wkContent21 = (TextView) dialog.findViewById(R.id.wkContent21);
        TextView wkContent22 = (TextView) dialog.findViewById(R.id.wkContent22);
        TextView wkContent31 = (TextView) dialog.findViewById(R.id.wkContent31);
        TextView wkContent32 = (TextView) dialog.findViewById(R.id.wkContent32);
        TextView wkContent33 = (TextView) dialog.findViewById(R.id.wkContent33);
        TextView wkContent61 = (TextView) dialog.findViewById(R.id.wkContent61);
        TextView wkContent62 = (TextView) dialog.findViewById(R.id.wkContent62);

        switch (v.getId()){
            case R.id.cvWorkshop1:
                viewTitle.setText(getResources().getString(R.string.workshopTitle1));
                CharSequence cs_bull_1_1 = getResources().getString(R.string.wk_bull_1_1);
                SpannableString sp_bull_1_1 = new SpannableString(cs_bull_1_1);
                sp_bull_1_1.setSpan(new BulletSpan(15), 0, cs_bull_1_1.length(), 0);

                CharSequence cs_bull_1_2 = getResources().getString(R.string.wk_bull_1_2);
                SpannableString sp_bull_1_2 = new SpannableString(cs_bull_1_2);
                sp_bull_1_2.setSpan(new BulletSpan(15), 0, cs_bull_1_2.length(), 0);

                CharSequence cs_bull_1_3 = getResources().getString(R.string.wk_bull_1_3);
                SpannableString sp_bull_1_3 = new SpannableString(cs_bull_1_3);
                sp_bull_1_3.setSpan(new BulletSpan(15), 0, cs_bull_1_3.length(), 0);

                CharSequence cs_bull_1_4 = getResources().getString(R.string.wk_bull_1_4);
                SpannableString sp_bull_1_4 = new SpannableString(cs_bull_1_4);
                sp_bull_1_4.setSpan(new BulletSpan(15), 0, cs_bull_1_4.length(), 0);

                CharSequence cs_bull_1_5 = getResources().getString(R.string.wk_bull_1_5);
                SpannableString sp_bull_1_5 = new SpannableString(cs_bull_1_5);
                sp_bull_1_5.setSpan(new BulletSpan(15), 0, cs_bull_1_5.length(), 0);

                CharSequence cs_bull_1_6 = getResources().getString(R.string.wk_bull_1_6);
                SpannableString sp_bull_1_6 = new SpannableString(cs_bull_1_6);
                sp_bull_1_6.setSpan(new BulletSpan(15), 0, cs_bull_1_6.length(), 0);

                CharSequence cs_bull_1_7 = getResources().getString(R.string.wk_bull_1_7);
                SpannableString sp_bull_1_7 = new SpannableString(cs_bull_1_7);
                sp_bull_1_7.setSpan(new BulletSpan(15), 0, cs_bull_1_7.length(), 0);

                textView1.setText(TextUtils.concat(sp_bull_1_1, "\n" , sp_bull_1_2, "\n", sp_bull_1_3, "\n", sp_bull_1_4,
                        "\n", sp_bull_1_5, "\n", sp_bull_1_6, "\n", sp_bull_1_7));
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.GONE);
                textView31.setVisibility(View.GONE);
                textView32.setVisibility(View.GONE);
                textView61.setVisibility(View.GONE);
                textView62.setVisibility(View.GONE);

                wkContent11.setVisibility(View.VISIBLE);
                wkContent12.setVisibility(View.VISIBLE);
                wkContent21.setVisibility(View.GONE);
                wkContent22.setVisibility(View.GONE);
                wkContent31.setVisibility(View.GONE);
                wkContent32.setVisibility(View.GONE);
                wkContent33.setVisibility(View.GONE);
                wkContent61.setVisibility(View.GONE);
                wkContent62.setVisibility(View.GONE);
                break;
            case R.id.cvWorkshop2:
                viewTitle.setText(getResources().getString(R.string.workshopTitle2));
                CharSequence cs_bull_2_1 = getResources().getString(R.string.wk_bull_2_1);
                SpannableString sp_bull_2_1 = new SpannableString(cs_bull_2_1);
                sp_bull_2_1.setSpan(new BulletSpan(15), 0, cs_bull_2_1.length(), 0);

                CharSequence cs_bull_2_2 = getResources().getString(R.string.wk_bull_2_2);
                SpannableString sp_bull_2_2 = new SpannableString(cs_bull_2_2);
                sp_bull_2_2.setSpan(new BulletSpan(15), 0, cs_bull_2_2.length(), 0);

                CharSequence cs_bull_2_3 = getResources().getString(R.string.wk_bull_2_3);
                SpannableString sp_bull_2_3 = new SpannableString(cs_bull_2_3);
                sp_bull_2_3.setSpan(new BulletSpan(15), 0, cs_bull_2_3.length(), 0);

                CharSequence cs_bull_2_4 = getResources().getString(R.string.wk_bull_2_4);
                SpannableString sp_bull_2_4 = new SpannableString(cs_bull_2_4);
                sp_bull_2_4.setSpan(new BulletSpan(15), 0, cs_bull_2_4.length(), 0);

                CharSequence cs_bull_2_5 = getResources().getString(R.string.wk_bull_2_5);
                SpannableString sp_bull_2_5 = new SpannableString(cs_bull_2_5);
                sp_bull_2_5.setSpan(new BulletSpan(15), 0, cs_bull_2_5.length(), 0);

                CharSequence cs_bull_2_6 = getResources().getString(R.string.wk_bull_2_6);
                SpannableString sp_bull_2_6 = new SpannableString(cs_bull_2_6);
                sp_bull_2_6.setSpan(new BulletSpan(15), 0, cs_bull_2_6.length(), 0);

                textView2.setText(TextUtils.concat(sp_bull_2_1, "\n" , sp_bull_2_2, "\n", sp_bull_2_3, "\n", sp_bull_2_4,
                        "\n", sp_bull_2_5, "\n", sp_bull_2_6));
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.VISIBLE);
                textView31.setVisibility(View.GONE);
                textView32.setVisibility(View.GONE);
                textView61.setVisibility(View.GONE);
                textView62.setVisibility(View.GONE);

                wkContent11.setVisibility(View.GONE);
                wkContent12.setVisibility(View.GONE);
                wkContent21.setVisibility(View.VISIBLE);
                wkContent22.setVisibility(View.VISIBLE);
                wkContent31.setVisibility(View.GONE);
                wkContent32.setVisibility(View.GONE);
                wkContent33.setVisibility(View.GONE);
                wkContent61.setVisibility(View.GONE);
                wkContent62.setVisibility(View.GONE);
                break;
            case R.id.cvWorkshop3:
                viewTitle.setText(getResources().getString(R.string.workshopTitle3));
                CharSequence cs_bull_3_1_1 = getResources().getString(R.string.wk_bull_3_1_1);
                SpannableString sp_bull_3_1_1 = new SpannableString(cs_bull_3_1_1);
                sp_bull_3_1_1.setSpan(new BulletSpan(15), 0, cs_bull_3_1_1.length(), 0);

                CharSequence cs_bull_3_1_2 = getResources().getString(R.string.wk_bull_3_1_2);
                SpannableString sp_bull_3_1_2 = new SpannableString(cs_bull_3_1_2);
                sp_bull_3_1_2.setSpan(new BulletSpan(15), 0, cs_bull_3_1_2.length(), 0);

                CharSequence cs_bull_3_1_3 = getResources().getString(R.string.wk_bull_3_1_3);
                SpannableString sp_bull_3_1_3 = new SpannableString(cs_bull_3_1_3);
                sp_bull_3_1_3.setSpan(new BulletSpan(15), 0, cs_bull_3_1_3.length(), 0);

                CharSequence cs_bull_3_1_4 = getResources().getString(R.string.wk_bull_3_1_4);
                SpannableString sp_bull_3_1_4 = new SpannableString(cs_bull_3_1_4);
                sp_bull_3_1_4.setSpan(new BulletSpan(15), 0, cs_bull_3_1_4.length(), 0);

                CharSequence cs_bull_3_1_5 = getResources().getString(R.string.wk_bull_3_1_5);
                SpannableString sp_bull_3_1_5 = new SpannableString(cs_bull_3_1_5);
                sp_bull_3_1_5.setSpan(new BulletSpan(15), 0, cs_bull_3_1_5.length(), 0);

                CharSequence cs_bull_3_1_6 = getResources().getString(R.string.wk_bull_3_1_6);
                SpannableString sp_bull_3_1_6 = new SpannableString(cs_bull_3_1_6);
                sp_bull_3_1_6.setSpan(new BulletSpan(15), 0, cs_bull_3_1_6.length(), 0);

                textView31.setText(TextUtils.concat(sp_bull_3_1_1, "\n" , sp_bull_3_1_2, "\n", sp_bull_3_1_3, "\n",
                        sp_bull_3_1_4, "\n", sp_bull_3_1_5, "\n", sp_bull_3_1_6));

                CharSequence cs_bull_3_2_1 = getResources().getString(R.string.wk_bull_3_2_1);
                SpannableString sp_bull_3_2_1 = new SpannableString(cs_bull_3_2_1);
                sp_bull_3_2_1.setSpan(new BulletSpan(15), 0, cs_bull_3_2_1.length(), 0);

                CharSequence cs_bull_3_2_2 = getResources().getString(R.string.wk_bull_3_2_2);
                SpannableString sp_bull_3_2_2 = new SpannableString(cs_bull_3_2_2);
                sp_bull_3_2_2.setSpan(new BulletSpan(15), 0, cs_bull_3_2_2.length(), 0);

                CharSequence cs_bull_3_2_3 = getResources().getString(R.string.wk_bull_3_2_3);
                SpannableString sp_bull_3_2_3 = new SpannableString(cs_bull_3_2_3);
                sp_bull_3_2_3.setSpan(new BulletSpan(15), 0, cs_bull_3_2_3.length(), 0);

                CharSequence cs_bull_3_2_4 = getResources().getString(R.string.wk_bull_3_2_4);
                SpannableString sp_bull_3_2_4 = new SpannableString(cs_bull_3_2_4);
                sp_bull_3_2_4.setSpan(new BulletSpan(15), 0, cs_bull_3_2_4.length(), 0);

                CharSequence cs_bull_3_2_5 = getResources().getString(R.string.wk_bull_3_2_5);
                SpannableString sp_bull_3_2_5 = new SpannableString(cs_bull_3_2_5);
                sp_bull_3_2_5.setSpan(new BulletSpan(15), 0, cs_bull_3_2_5.length(), 0);

                CharSequence cs_bull_3_2_6 = getResources().getString(R.string.wk_bull_3_2_6);
                SpannableString sp_bull_3_2_6 = new SpannableString(cs_bull_3_2_6);
                sp_bull_3_2_6.setSpan(new BulletSpan(15), 0, cs_bull_3_2_6.length(), 0);

                CharSequence cs_bull_3_2_7 = getResources().getString(R.string.wk_bull_3_2_7);
                SpannableString sp_bull_3_2_7 = new SpannableString(cs_bull_3_2_7);
                sp_bull_3_2_7.setSpan(new BulletSpan(15), 0, cs_bull_3_2_7.length(), 0);

                CharSequence cs_bull_3_2_8 = getResources().getString(R.string.wk_bull_3_2_8);
                SpannableString sp_bull_3_2_8 = new SpannableString(cs_bull_3_2_8);
                sp_bull_3_2_8.setSpan(new BulletSpan(15), 0, cs_bull_3_2_8.length(), 0);

                CharSequence cs_bull_3_2_9 = getResources().getString(R.string.wk_bull_3_2_9);
                SpannableString sp_bull_3_2_9 = new SpannableString(cs_bull_3_2_9);
                sp_bull_3_2_9.setSpan(new BulletSpan(15), 0, cs_bull_3_2_9.length(), 0);

                textView32.setText(TextUtils.concat(sp_bull_3_2_1, "\n" , sp_bull_3_2_2, "\n", sp_bull_3_2_3, "\n",
                        sp_bull_3_2_4, "\n", sp_bull_3_2_5, "\n", sp_bull_3_2_6, "\n", sp_bull_3_2_7, "\n", sp_bull_3_2_8,
                        "\n", sp_bull_3_2_9));
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView31.setVisibility(View.VISIBLE);
                textView32.setVisibility(View.VISIBLE);
                textView61.setVisibility(View.GONE);
                textView62.setVisibility(View.GONE);

                wkContent11.setVisibility(View.GONE);
                wkContent12.setVisibility(View.GONE);
                wkContent21.setVisibility(View.GONE);
                wkContent22.setVisibility(View.GONE);
                wkContent31.setVisibility(View.VISIBLE);
                wkContent32.setVisibility(View.VISIBLE);
                wkContent33.setVisibility(View.VISIBLE);
                wkContent61.setVisibility(View.GONE);
                wkContent62.setVisibility(View.GONE);
                break;
            case R.id.cvWorkshop4:
                viewTitle.setText(getResources().getString(R.string.workshopTitle4));
                break;
            case R.id.cvWorkshop5:
                viewTitle.setText(getResources().getString(R.string.workshopTitle5));
                break;
            case R.id.cvWorkshop6:
                viewTitle.setText(getResources().getString(R.string.workshopTitle6));
                CharSequence cs_bull_6_1_1 = getResources().getString(R.string.wk_bull_6_1_1);
                SpannableString sp_bull_6_1_1 = new SpannableString(cs_bull_6_1_1);
                sp_bull_6_1_1.setSpan(new BulletSpan(15), 0, cs_bull_6_1_1.length(), 0);

                CharSequence cs_bull_6_1_2 = getResources().getString(R.string.wk_bull_6_1_2);
                SpannableString sp_bull_6_1_2 = new SpannableString(cs_bull_6_1_2);
                sp_bull_6_1_2.setSpan(new BulletSpan(15), 0, cs_bull_6_1_2.length(), 0);

                CharSequence cs_bull_6_1_3 = getResources().getString(R.string.wk_bull_6_1_3);
                SpannableString sp_bull_6_1_3 = new SpannableString(cs_bull_6_1_3);
                sp_bull_6_1_3.setSpan(new BulletSpan(15), 0, cs_bull_6_1_3.length(), 0);

                CharSequence cs_bull_6_1_4 = getResources().getString(R.string.wk_bull_6_1_4);
                SpannableString sp_bull_6_1_4 = new SpannableString(cs_bull_6_1_4);
                sp_bull_6_1_4.setSpan(new BulletSpan(15), 0, cs_bull_6_1_4.length(), 0);

                CharSequence cs_bull_6_1_5 = getResources().getString(R.string.wk_bull_6_1_5);
                SpannableString sp_bull_6_1_5 = new SpannableString(cs_bull_6_1_5);
                sp_bull_6_1_5.setSpan(new BulletSpan(15), 0, cs_bull_6_1_5.length(), 0);

                textView61.setText(TextUtils.concat(sp_bull_6_1_1, "\n" , sp_bull_6_1_2, "\n", sp_bull_6_1_3, "\n",
                        sp_bull_6_1_4, "\n", sp_bull_6_1_5));

                CharSequence cs_bull_6_2_1 = getResources().getString(R.string.wk_bull_6_2_1);
                SpannableString sp_bull_6_2_1 = new SpannableString(cs_bull_6_2_1);
                sp_bull_6_2_1.setSpan(new BulletSpan(15), 0, cs_bull_6_2_1.length(), 0);

                CharSequence cs_bull_6_2_2 = getResources().getString(R.string.wk_bull_6_2_2);
                SpannableString sp_bull_6_2_2 = new SpannableString(cs_bull_6_2_2);
                sp_bull_6_2_2.setSpan(new BulletSpan(15), 0, cs_bull_6_2_2.length(), 0);

                CharSequence cs_bull_6_2_3 = getResources().getString(R.string.wk_bull_6_2_3);
                SpannableString sp_bull_6_2_3 = new SpannableString(cs_bull_6_2_3);
                sp_bull_6_2_3.setSpan(new BulletSpan(15), 0, cs_bull_6_2_3.length(), 0);

                CharSequence cs_bull_6_2_4 = getResources().getString(R.string.wk_bull_6_2_4);
                SpannableString sp_bull_6_2_4 = new SpannableString(cs_bull_6_2_4);
                sp_bull_6_2_4.setSpan(new BulletSpan(15), 0, cs_bull_6_2_4.length(), 0);

                textView62.setText(TextUtils.concat(sp_bull_6_2_1, "\n" , sp_bull_6_2_2, "\n", sp_bull_6_2_3, "\n",
                        sp_bull_6_2_4));
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView31.setVisibility(View.GONE);
                textView32.setVisibility(View.GONE);
                textView61.setVisibility(View.VISIBLE);
                textView62.setVisibility(View.VISIBLE);

                wkContent11.setVisibility(View.GONE);
                wkContent12.setVisibility(View.GONE);
                wkContent21.setVisibility(View.GONE);
                wkContent22.setVisibility(View.GONE);
                wkContent31.setVisibility(View.GONE);
                wkContent32.setVisibility(View.GONE);
                wkContent33.setVisibility(View.GONE);
                wkContent61.setVisibility(View.VISIBLE);
                wkContent62.setVisibility(View.VISIBLE);
                break;
        }
        dialog.findViewById(R.id.llWorkshops).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.viewServiceContent).setVisibility(View.GONE);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(WorkshopsActivity.this,activity_about_services.class);
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(WorkshopsActivity.this,activity_about_services.class);
        finish();
        startActivity(intent);
    }
}
