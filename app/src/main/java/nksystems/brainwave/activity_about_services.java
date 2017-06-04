package nksystems.brainwave;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import static nksystems.brainwave.R.id.textView;

/**
 * Created by Charmy on 27/05/2017.
 */

public class activity_about_services extends AppCompatActivity {

    String[] services;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.about_counselling_services_layout);

        final GridView gvServices = (GridView) findViewById(R.id.gvServices);
        services = getResources().getStringArray(R.array.services_list);
        gvServices.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, services){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view=convertView;
                if(view==null){
                    LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.custom_grid_services_layout,null);
                    TextView txtItem = (TextView) view.findViewById(R.id.grid_item_label);
                    txtItem.setText(services[position]);

                    if(position==0||position==3||position==4||position==7){
                        view.setBackgroundColor(Color.BLUE);
                        txtItem.setTextColor(Color.WHITE);
                    }else{
                        view.setBackgroundColor(Color.WHITE);
                        txtItem.setTextColor(Color.BLUE);
                    }
                }else{
                    TextView txtItem = (TextView) view.findViewById(R.id.grid_item_label);
                    txtItem.setText(services[position]);
                    if(position==0||position==3||position==4||position==7){
                        view.setBackgroundColor(Color.BLUE);
                        txtItem.setTextColor(Color.WHITE);
                    }else{
                        view.setBackgroundColor(Color.WHITE);
                        txtItem.setTextColor(Color.BLUE);
                    }
                }

                view.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,450));
                //view.setBackground(getResources().getDrawable(R.drawable.rectangle));

                return view;
            }
        });

        gvServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtItem = (TextView) view.findViewById(R.id.grid_item_label);
                Dialog dialog = new Dialog(activity_about_services.this, R.style.custom_dialog_services_style);
                dialog.setContentView(R.layout.dialog_services_layout);
                TextView viewTitle = (TextView) dialog.findViewById(R.id.viewServiceTitle);

                if(position == 7){
                    viewTitle.setText("Workshop Details");
                    dialog.findViewById(R.id.llWorkshops).setVisibility(View.VISIBLE);
                    dialog.findViewById(R.id.viewServiceContent).setVisibility(View.GONE);

                    TextView textView1 = (TextView) dialog.findViewById(R.id.viewWkBullets1);
                    TextView textView2 = (TextView) dialog.findViewById(R.id.viewWkBullets2);
                    TextView textView31 = (TextView) dialog.findViewById(R.id.viewWkBullets31);
                    TextView textView32 = (TextView) dialog.findViewById(R.id.viewWkBullets32);
                    TextView textView61 = (TextView) dialog.findViewById(R.id.viewWkBullets61);
                    TextView textView62 = (TextView) dialog.findViewById(R.id.viewWkBullets62);

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
                }
                else{
                    viewTitle.setText(txtItem.getText());
                    dialog.findViewById(R.id.llWorkshops).setVisibility(View.GONE);
                    dialog.findViewById(R.id.viewServiceContent).setVisibility(View.VISIBLE);
                }
                dialog.show();
            }
        });
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
