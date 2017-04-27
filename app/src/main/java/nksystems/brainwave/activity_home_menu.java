package nksystems.brainwave;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class activity_home_menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewStub contentAbout,contentCounselling,contentFlower,contentPlay,contentHandwriting,contentAstrology, contentPari;
    LinearLayout root;
    /*ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;*/
    ViewStub stub1, stub2, stub3, stubf1, stubf2, stubf3, stubh1, stubh2, stubh3;

    protected DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        contentAbout=(ViewStub)findViewById(R.id.contentAbout);
        contentAbout.setLayoutResource(R.layout.about_brainwave_layout);
        contentAbout.inflate();

        contentCounselling=(ViewStub)findViewById(R.id.contentCounselling);
        contentCounselling.setLayoutResource(R.layout.counselling_services_layout);
        contentCounselling.inflate();

        contentFlower=(ViewStub)findViewById(R.id.contentFlower);
        contentFlower.setLayoutResource(R.layout.flower_therapy_layout);
        contentFlower.inflate();

        contentPari=(ViewStub)findViewById(R.id.contentPari);
        contentPari.setLayoutResource(R.layout.pari_herbal_product_layout);
        contentPari.inflate();

        contentPlay=(ViewStub)findViewById(R.id.contentPlay);

        contentHandwriting=(ViewStub)findViewById(R.id.contentHandwriting);
        contentHandwriting.setLayoutResource(R.layout.handwriting_layout);
        contentHandwriting.inflate();

        contentAstrology=(ViewStub)findViewById(R.id.contentAstrology);

        root=(LinearLayout)findViewById(R.id.rootLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.nav_counseling) {
            switchContent(contentCounselling);
            actionsCounsellingServices();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_flower) {
            switchContent(contentFlower);
            actionsFlowerTherapy();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_play) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_herbal) {
            switchContent(contentPari);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_handwriting) {
            switchContent(contentHandwriting);
            actionsHandwritingServices();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_astro) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(id == R.id.nav_about){
            switchContent(contentAbout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(id==R.id.nav_close){
            drawer.closeDrawer(GravityCompat.START);
        }


        return true;
    }

    public void switchContent(View v){

        contentCounselling.setVisibility(View.GONE);
        contentFlower.setVisibility(View.GONE);
        contentPari.setVisibility(View.GONE);
        contentPlay.setVisibility(View.GONE);
        contentHandwriting.setVisibility(View.GONE);
        contentAstrology.setVisibility(View.GONE);
        contentAbout.setVisibility(View.GONE);

        v.setVisibility(View.VISIBLE);

    }

    //FOR COUNSELLING SERVICES
    /*private void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Schedule an Appointment");
        listDataHeader.add("Counselling Services");
        listDataHeader.add("Counselling Services + Medicines");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }*/

    public void actionsCounsellingServices(){

            /*expandableListView = (ExpandableListView) findViewById(R.id.expandContact);

            // preparing list data
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expandableListView.setAdapter(listAdapter);*/

        stub1 = (ViewStub) findViewById(R.id.vscSchedule);
        stub2 = (ViewStub) findViewById(R.id.vscCounselling);
        stub3 = (ViewStub) findViewById(R.id.vscCounsellingMeds);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgCounselling);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                if(radioButton.getId() == R.id.rbcSchedule){
                    stub1.inflate();
                    Spinner spinner = (Spinner) findViewById(R.id.spnContacttype);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity_home_menu.this,
                            R.array.contact_type_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    findViewById(R.id.vscCounselling).setVisibility(View.GONE);
                    findViewById(R.id.vscCounsellingMeds).setVisibility(View.GONE);

                    setDateTimePicker();
                    setAppointment(1);
                }
                else if(radioButton.getId() == R.id.rbcCounselling){
                    stub2.inflate();
                    stub1.setVisibility(View.GONE);
                    stub3.setVisibility(View.GONE);
                }
                else if(radioButton.getId() == R.id.rbcCounsellingMeds){
                    stub3.inflate();
                    stub1.setVisibility(View.GONE);
                    stub2.setVisibility(View.GONE);
                }
            }
        });

        Button btnAboutCounselling = (Button) findViewById(R.id.btnAboutCounselling);
        btnAboutCounselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this, android.R.style.Theme_Dialog);
                dialog.setContentView(R.layout.about_counselling_services_layout);
                dialog.show();
            }
        });

            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCounselling);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(activity_home_menu.this, android.R.style.Theme_Dialog);
                    dialog.setContentView(R.layout.about_counselling_services_layout);
                    dialog.show();
                }
            });*/
    }

    public void actionsFlowerTherapy(){

        stubf1 = (ViewStub) findViewById(R.id.vsfSchedule);
        stubf2 = (ViewStub) findViewById(R.id.vsfCounselling);
        stubf3 = (ViewStub) findViewById(R.id.vsfCounsellingMeds);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgFlower);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                if(radioButton.getId() == R.id.rbfSchedule){
                    stubf1.inflate();
                    findViewById(R.id.vsfCounselling).setVisibility(View.GONE);
                    findViewById(R.id.vsfCounsellingMeds).setVisibility(View.GONE);

                    setDateTimePicker();
                    setAppointment(2);
                }
                else if(radioButton.getId() == R.id.rbfCounselling){
                    stubf2.inflate();
                    stubf1.setVisibility(View.GONE);
                    stubf3.setVisibility(View.GONE);
                }
                else if(radioButton.getId() == R.id.rbfCounsellingMeds){
                    stubf3.inflate();
                    stubf1.setVisibility(View.GONE);
                    stubf2.setVisibility(View.GONE);
                }
            }
        });

        Button btnAboutFlower = (Button) findViewById(R.id.btnAboutFlower);
        btnAboutFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this, android.R.style.Theme_Dialog);
                dialog.setContentView(R.layout.about_flower_therapy_layout);
                dialog.show();
            }
        });

            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabFlowerTherapy);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(activity_home_menu.this, android.R.style.Theme_Dialog);
                    dialog.setContentView(R.layout.about_counselling_services_layout);
                    dialog.show();
                }
            });*/
    }

    public void actionsHandwritingServices(){

            /*expandableListView = (ExpandableListView) findViewById(R.id.expandContact);

            // preparing list data
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expandableListView.setAdapter(listAdapter);*/

        stubh1 = (ViewStub) findViewById(R.id.vshSchedule);
        stubh2 = (ViewStub) findViewById(R.id.vshCounselling);
        stubh3 = (ViewStub) findViewById(R.id.vshCounsellingMeds);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgHand);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                if(radioButton.getId() == R.id.rbhSchedule){
                    stubh1.inflate();
                    findViewById(R.id.vshCounselling).setVisibility(View.GONE);
                    findViewById(R.id.vshCounsellingMeds).setVisibility(View.GONE);

                    setDateTimePicker();
                    setAppointment(3);
                }
                else if(radioButton.getId() == R.id.rbhCounselling){
                    stubh2.inflate();
                    stubh1.setVisibility(View.GONE);
                    stubh3.setVisibility(View.GONE);
                }
                else if(radioButton.getId() == R.id.rbhCounsellingMeds){
                    stubh3.inflate();
                    stubh1.setVisibility(View.GONE);
                    stubh2.setVisibility(View.GONE);
                }
            }
        });

        Button btnAboutHand = (Button) findViewById(R.id.btnAboutHand);
        btnAboutHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this, android.R.style.Theme_Dialog);
                dialog.setContentView(R.layout.about_handwriting_layout);
                dialog.show();
            }
        });

            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabHandwriting);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(activity_home_menu.this, android.R.style.Theme_Dialog);
                    dialog.setContentView(R.layout.about_handwriting_layout);
                    dialog.show();
                }
            });*/
    }

    public void setDateTimePicker(){
        ImageButton btnDate, btnTime;

        btnDate = (ImageButton) findViewById(R.id.btnDate);
        btnTime = (ImageButton) findViewById(R.id.btnTime);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear, mMonth, mDay;
                //Get Current Date
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_home_menu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        EditText txtDate = (EditText) findViewById(R.id.txtDueDate);
                        txtDate.setText(day + "-" + (month+1) + "-" + year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour, mMinute;
                //Get Current Time
                final Calendar calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_home_menu.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        EditText txtTime = (EditText) findViewById(R.id.txtDueTime);
                        txtTime.setText(hour + ":" + minute);
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
    }

    public void setAppointment(final int id){
        Button btnCounselSetAppointment = (Button) findViewById(R.id.btnCounselSetAppointment);
        btnCounselSetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtName, txtEmail, txtContact, txtDate, txtTime;
                Spinner spnContactType;
                txtName = (EditText) findViewById(R.id.txtCounselName);
                txtEmail = (EditText) findViewById(R.id.txtCounselEmail);
                txtContact = (EditText) findViewById(R.id.txtCounselNumber);
                txtDate = (EditText) findViewById(R.id.txtDueDate);
                txtTime = (EditText) findViewById(R.id.txtDueTime);
                spnContactType = (Spinner) findViewById(R.id.spnContacttype);

                String to = "charmyshah91@gmail.com";
                String subject = "";
                if(id == 1)
                    subject = "Counselling Services - " + spnContactType.getSelectedItem().toString() + ": Appointment";
                else if(id == 2)
                    subject = "Flower Therapy - " + spnContactType.getSelectedItem().toString() + ": Appointment";
                else if(id == 3)
                    subject = "Handwriting & Signature Analysis - " + spnContactType.getSelectedItem().toString() + ": Appointment";
                String message = "Hello, \n\n I would like to schedule an appointment with you. Below are my details: \n\n Name: " + txtName.getText().toString() +
                        "\n Email: " + txtEmail.getText().toString() + "\n Contact Number: " + txtContact.getText().toString() +
                        "\n Date of Appointment: " + txtDate.getText().toString() + "\n Time of Appointment: " + txtTime.getText().toString() +
                        ". \n\n Thank you for your time. \n\n Regards,\n " + txtName.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an email client:"));
            }
        });
    }
}
