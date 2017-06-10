package nksystems.brainwave;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Charmy on 27/05/2017.
 */

public class activity_about_services extends AppCompatActivity implements View.OnClickListener {

    CardView cvService1,cvService2,cvService3,cvService4,cvService5,cvService6,cvService7,cvService8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.about_counselling_services_layout);

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

    @Override
    public void onClick(View v) {
        Dialog dialog = new Dialog(activity_about_services.this, R.style.custom_dialog_services_style);
        dialog.setContentView(R.layout.dialog_services_layout);
        TextView viewTitle = (TextView) dialog.findViewById(R.id.viewServiceTitle);
        switch (v.getId()){
            case R.id.cvService1:
                viewTitle.setText(getResources().getString(R.string.service1));
                dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                dialog.show();
                break;
            case R.id.cvService2:
                viewTitle.setText(getResources().getString(R.string.service2));
                dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                dialog.show();
                break;
            case R.id.cvService3:
                viewTitle.setText(getResources().getString(R.string.service3));
                dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                dialog.show();
                break;
            case R.id.cvService4:
                viewTitle.setText(getResources().getString(R.string.service4));
                dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                dialog.show();
                break;
            case R.id.cvService5:
                viewTitle.setText(getResources().getString(R.string.service5));
                dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                dialog.show();
                break;
            case R.id.cvService6:
                viewTitle.setText(getResources().getString(R.string.service6));
                dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                dialog.show();
                break;
            case R.id.cvService7:
                viewTitle.setText(getResources().getString(R.string.service7));
                dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                dialog.show();
                break;
            case R.id.cvService8:
                Intent intent = new Intent(activity_about_services.this, WorkshopsActivity.class);
                finish();
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(activity_about_services.this,activity_home_menu.class);
                intent.putExtra("active_activity","contentCounselling");
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(activity_about_services.this,activity_home_menu.class);
        intent.putExtra("active_activity","contentCounselling");
        finish();
        startActivity(intent);
    }
}
