package nksystems.brainwave;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class activity_home_menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewStub contentAbout,contentCounselling,contentFlower,contentPlay,contentHandwriting,contentAstrology, contentPari,
    contentProducts, contentTips;
    LinearLayout root;
    /*ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;*/
    ViewStub stub1, stub2, stub3, stubf1, stubf2, stubf3, stubh1, stubh2, stubh3;
    DatabaseReference mReference;
    StorageReference sReference;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    Dialog commonDialog;

    protected DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);



        contentAbout=(ViewStub)findViewById(R.id.contentAbout);
        contentAbout.setLayoutResource(R.layout.activity_card_view);
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

        contentProducts=(ViewStub)findViewById(R.id.contentProducts);
        contentProducts.setLayoutResource(R.layout.product_names_list_layout);
        contentProducts.inflate();

        contentTips=(ViewStub)findViewById(R.id.contentTips);
        contentTips.setLayoutResource(R.layout.tips_layout);
        contentTips.inflate();

        String activeStub=getIntent().getStringExtra("active_activity");
        switch (activeStub){
            case "contentCounselling":
                switchContent(contentCounselling);
                actionsCounsellingServices();
                break;
            default:
                switchContent(contentAbout);
                break;
        }

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

        actionAboutBrainwave();
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


        //noinspection SimplifiableIfStatement
        int id = item.getItemId();

        if(id == R.id.menu_products){
            switchContent(contentProducts);
            actionsProductsList();
        }
        else if(id == R.id.menu_tips){
            switchContent(contentTips);
            actionTips();
        }

        return true;
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
            actionsPariHerbalProducts();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_handwriting) {
            switchContent(contentHandwriting);
            actionsHandwritingServices();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_astro) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(id == R.id.nav_about){
            switchContent(contentAbout);
            actionAboutBrainwave();
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

    public void actionsCounsellingServices(){

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

                    //setDateTimePicker();
                    //setAppointment(1);
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
                Dialog dialog = new Dialog(activity_home_menu.this);
                dialog.setContentView(R.layout.about_counselling_services_layout);
                dialog.show();
                /*finish();
                startActivity(new Intent(activity_home_menu.this,activity_counselling_form.class));*/
            }
        });

        /*Button btnTelephone = (Button) findViewById(R.id.btnAboutCounselling);
        btnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this);
                dialog.setContentView(R.layout.contact_expand_item_layout);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(1,dialog,1);
                TextView viewProblem = (TextView) dialog.findViewById(R.id.viewProblem);
                EditText txtProblem = (EditText) dialog.findViewById(R.id.txtProblem);
                viewProblem.setVisibility(View.GONE);
                txtProblem.setVisibility(View.GONE);
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
                    Spinner spinner = (Spinner) findViewById(R.id.spnContacttype);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity_home_menu.this,
                            R.array.contact_type_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    findViewById(R.id.vsfCounselling).setVisibility(View.GONE);
                    findViewById(R.id.vsfCounsellingMeds).setVisibility(View.GONE);

                    //setDateTimePicker();
                    //setAppointment(2);
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

        /*Button btnTelephone = (Button) findViewById(R.id.btnAboutCounselling);
        btnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this);
                dialog.setContentView(R.layout.contact_expand_item_layout);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(2,dialog,1);
                TextView viewProblem = (TextView) dialog.findViewById(R.id.viewProblem);
                EditText txtProblem = (EditText) dialog.findViewById(R.id.txtProblem);
                viewProblem.setVisibility(View.GONE);
                txtProblem.setVisibility(View.GONE);
                dialog.show();
            }
        });*/
    }

    public void actionsHandwritingServices(){

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
                    Spinner spinner = (Spinner) findViewById(R.id.spnContacttype);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity_home_menu.this,
                            R.array.contact_type_list, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    findViewById(R.id.vshCounselling).setVisibility(View.GONE);
                    findViewById(R.id.vshCounsellingMeds).setVisibility(View.GONE);

                    //setDateTimePicker();
                    //setAppointment(3);
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

        /*Button btnTelephone = (Button) findViewById(R.id.btnAboutCounselling);
        btnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this);
                dialog.setContentView(R.layout.contact_expand_item_layout);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(3,dialog,1);
                TextView viewProblem = (TextView) dialog.findViewById(R.id.viewProblem);
                EditText txtProblem = (EditText) dialog.findViewById(R.id.txtProblem);
                viewProblem.setVisibility(View.GONE);
                txtProblem.setVisibility(View.GONE);
                dialog.show();
            }
        });*/
    }

    public void setDateTimePicker(Dialog dialog){
        ImageButton btnDate, btnTime;

        btnDate = (ImageButton) dialog.findViewById(R.id.btnDate);
        btnTime = (ImageButton) dialog.findViewById(R.id.btnTime);

        commonDialog = dialog;

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
                        EditText txtDate = (EditText) commonDialog.findViewById(R.id.txtDueDate);
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
                        EditText txtTime = (EditText) commonDialog.findViewById(R.id.txtDueTime);
                        txtTime.setText(hour + ":" + minute);
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
    }

    public void setAppointment(final int id, Dialog dialog, final int type){
        commonDialog = dialog;
        Button btnCounselSetAppointment = (Button) dialog.findViewById(R.id.btnCounselSetAppointment);
        btnCounselSetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtName, txtEmail, txtContact, txtDate, txtTime, txtProblem;
                Spinner spnContactType;
                txtName = (EditText) commonDialog.findViewById(R.id.txtCounselName);
                txtEmail = (EditText) commonDialog.findViewById(R.id.txtCounselEmail);
                txtContact = (EditText) commonDialog.findViewById(R.id.txtCounselNumber);
                txtDate = (EditText) commonDialog.findViewById(R.id.txtDueDate);
                txtTime = (EditText) commonDialog.findViewById(R.id.txtDueTime);
                spnContactType = (Spinner) findViewById(R.id.spnContacttype);
                txtProblem = (EditText) commonDialog.findViewById(R.id.txtProblem);

                String appointmentType = "";
                String message = "";
                if(type == 1){
                    appointmentType = "Telephonic";
                    message = "Hello, \n\n I would like to schedule an appointment with you. Below are my details: \n\n Name: " + txtName.getText().toString() +
                            "\n Email: " + txtEmail.getText().toString() + "\n Contact Number: " + txtContact.getText().toString() +
                            "\n Date of Appointment: " + txtDate.getText().toString() + "\n Time of Appointment: " + txtTime.getText().toString() +
                            "\n Problem in brief: " + txtProblem.getText().toString() + "\n\n Thank you for your time. \n\n Regards,\n " + txtName.getText().toString();
                }
                else if(type == 2){
                    appointmentType = "Skype";
                    message = "Hello, \n\n I would like to schedule an appointment with you. Below are my details: \n\n Name: " + txtName.getText().toString() +
                            "\n Email: " + txtEmail.getText().toString() + "\n Contact Number: " + txtContact.getText().toString() +
                            "\n Date of Appointment: " + txtDate.getText().toString() + "\n Time of Appointment: " + txtTime.getText().toString() +
                            "\n Problem in brief: " + txtProblem.getText().toString() + "\n\n Thank you for your time. \n\n Regards,\n " + txtName.getText().toString();
                }
                else if(type == 3){
                    appointmentType = "Personal";
                    message = "Hello, \n\n I would like to schedule an appointment with you. Below are my details: \n\n Name: " + txtName.getText().toString() +
                            "\n Email: " + txtEmail.getText().toString() + "\n Contact Number: " + txtContact.getText().toString() +
                            "\n Date of Appointment: " + txtDate.getText().toString() + "\n Time of Appointment: " + txtTime.getText().toString() +
                            "\n\n Thank you for your time. \n\n Regards,\n " + txtName.getText().toString();
                }

                String to = activity_home_menu.this.getResources().getString(R.string.emailTo);
                String subject = "";
                if(id == 1)
                    subject = "Counselling Services - " + appointmentType + ": Appointment";
                else if(id == 2)
                    subject = "Flower Therapy - " + appointmentType + ": Appointment";
                else if(id == 3)
                    subject = "Handwriting & Signature Analysis - " + appointmentType + ": Appointment";

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an email client:"));
            }
        });
    }

    public void actionsPariHerbalProducts(){
        final GridView productslist;
        final ProgressBar progress;
        productslist=(GridView)findViewById(R.id.gvBooksList);
        progress=(ProgressBar)findViewById(R.id.progress);

        FirebaseApp.initializeApp(this);

        mReference= FirebaseDatabase.getInstance().getReference("products");
        sReference= FirebaseStorage.getInstance().getReference("imagethumbnails");
        final CustomGridItem item= new CustomGridItem(this,sReference,mReference,"orderid");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                item.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
                productslist.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        productslist.setAdapter(item);
        productslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvTitle=(TextView)view.findViewById(R.id.tvTitle);
                String title=tvTitle.getText().toString();

                Intent intent=new Intent(activity_home_menu.this,activity_product_info.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }

    public void actionAboutBrainwave(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recViewAboutBW);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<AboutBrainwaveObject> getDataSet() {
        List<AboutBrainwaveObject> results = new ArrayList<AboutBrainwaveObject>();
        String title = getResources().getString(R.string.bw_name);
        String content = getResources().getString(R.string.bw_intro);
        AboutBrainwaveObject obj = new AboutBrainwaveObject(title, content);
        results.add(obj);

        title = getResources().getString(R.string.bw_history_title);
        content = getResources().getString(R.string.bw_history_info1) + "\n\n" +
                getResources().getString(R.string.bw_history_info2) + "\n\n" +
                getResources().getString(R.string.bw_history_info3);
        AboutBrainwaveObject obj1 = new AboutBrainwaveObject(title, content);
        results.add(obj1);

        title = getResources().getString(R.string.bw_founder_title);
        content = getResources().getString(R.string.bw_founder_info1) + "\n\n" +
                getResources().getString(R.string.bw_founder_info2) + "\n\n" +
                getResources().getString(R.string.bw_founder_info3) + "\n\n" +
                getResources().getString(R.string.bw_founder_info4);
        AboutBrainwaveObject obj2 = new AboutBrainwaveObject(title, content);
        results.add(obj2);

        title = getResources().getString(R.string.bw_slogan_title);
        content = getResources().getString(R.string.bw_slogan_info);
        AboutBrainwaveObject obj3 = new AboutBrainwaveObject(title, content);
        results.add(obj3);

        title = getResources().getString(R.string.bw_mission_title);
        content = getResources().getString(R.string.bw_mission_info);
        AboutBrainwaveObject obj4 = new AboutBrainwaveObject(title, content);
        results.add(obj4);

        return results;
    }

    public void actionsProductsList(){

        final List<String> productList = new ArrayList();

        FirebaseApp.initializeApp(this);

        mReference= FirebaseDatabase.getInstance().getReference("products");
        mReference.orderByChild("orderid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot data :dataSnapshot.getChildren()){
                    productList.add(data.getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(activity_home_menu.this,R.layout.product_names_list_item,productList);
                ListView listView = (ListView) findViewById(R.id.list_view_products);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void actionTips(){

    }
}
