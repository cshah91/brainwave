package nksystems.brainwave;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
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
import android.widget.Toast;

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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Calendar.SATURDAY;

public class activity_home_menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewStub contentAbout,contentCounselling,contentFlower,contentPlay,contentHandwriting,contentAstrology, contentPari,
    contentProducts, contentTips;
    LinearLayout root;
    ViewStub stub1, stub2, stub3, stubf1, stubf2, stubf3, stubh1, stubh2, stubh3;
    DatabaseReference mReference;
    StorageReference sReference;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    Dialog commonDialog;
    String selectedDate;

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
            case "herbalProducts":
                switchContent(contentPari);
                actionsPariHerbalProducts();
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
        contentPari.setVisibility(View.GONE);
        contentPlay.setVisibility(View.GONE);
        contentHandwriting.setVisibility(View.GONE);
        contentAstrology.setVisibility(View.GONE);
        contentAbout.setVisibility(View.GONE);

        v.setVisibility(View.VISIBLE);

    }

    public void actionsCounsellingServices(){

        CardView cvAbout = (CardView) findViewById(R.id.cvAbout);
        cvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(activity_home_menu.this,activity_about_services.class);
                startActivity(intent);
            }
        });

        final Button btnPackage1 = (Button) findViewById(R.id.btnPackage1);
        final Button btnPackage2 = (Button) findViewById(R.id.btnPackage2);

        btnPackage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(activity_home_menu.this,activity_counselling_form.class);
                intent.putExtra("amount","500");
                intent.putExtra("packagetype","2");
                startActivity(intent);
            }
        });

        btnPackage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(activity_home_menu.this,activity_counselling_form.class);
                intent.putExtra("amount","2000");
                intent.putExtra("packagetype","1");
                startActivity(intent);
            }
        });

        ImageButton btnTelephone = (ImageButton) findViewById(R.id.btnTelephone);
        ImageButton btnSkype = (ImageButton) findViewById(R.id.btnSkype);
        ImageButton btnPersonal = (ImageButton) findViewById(R.id.btnPersonal);

        btnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this);
                dialog.setContentView(R.layout.contact_expand_item_layout);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(1,dialog,1);
                Button btnAppointment = (Button) dialog.findViewById(R.id.btnCounselSetAppointment);
                btnAppointment.setText("Set Telephonic Appointment");
                dialog.show();
            }
        });

        btnSkype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this);
                dialog.setContentView(R.layout.contact_expand_item_layout);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(1,dialog,2);
                Button btnAppointment = (Button) dialog.findViewById(R.id.btnCounselSetAppointment);
                btnAppointment.setText("Set Skype Appointment");
                dialog.show();
            }
        });

        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_home_menu.this);
                dialog.setContentView(R.layout.contact_expand_item_layout);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(1,dialog,3);
                Button btnAppointment = (Button) dialog.findViewById(R.id.btnCounselSetAppointment);
                btnAppointment.setText("Set Personal Appointment");
                dialog.show();
            }
        });

        CardView cvMedicines = (CardView) findViewById(R.id.cvMedicines);
        cvMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(activity_home_menu.this,activity_medicines.class);
                startActivity(intent);
            }
        });
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
                        // Get day of the week for the selected date
                        Calendar cal = new GregorianCalendar(year, month, day);
                        int week = cal.get(Calendar.DAY_OF_WEEK);

                        if(week == Calendar.SATURDAY || week == Calendar.SUNDAY || week == Calendar.WEDNESDAY){
                            EditText txtDate = (EditText) commonDialog.findViewById(R.id.txtDueDate);
                            txtDate.setText(day + "-" + (month+1) + "-" + year);
                            selectedDate = txtDate.getText().toString();
                        }
                        else{
                            String message = "Appointments available only for Wednesday, Saturday and Sunday. " +
                                    "Please select another date.";
                            Toast.makeText(activity_home_menu.this,message,Toast.LENGTH_LONG).show();
                        }
                    }
                },mYear,mMonth,mDay);
                calendar.add(Calendar.DATE,1);
                Date currentDate = calendar.getTime();
                datePickerDialog.getDatePicker().setMinDate(currentDate.getTime());
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
                        if(selectedDate != null && selectedDate.length() > 0){
                            String date[] = selectedDate.split("-");
                            Calendar cal = new GregorianCalendar(Integer.parseInt(date[2]), (Integer.parseInt(date[1])-1),
                                    Integer.parseInt(date[0]));
                            int week = cal.get(Calendar.DAY_OF_WEEK);
                            String min="";
                            if(minute >= 0 && minute < 10)
                                min = "0"+minute;
                            else
                                min = ""+minute;
                            if(week == Calendar.WEDNESDAY){
                                if(hour >= 9 && hour <= 12){
                                    txtTime.setText((hour > 12 ? (hour-12):hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                }
                                else{
                                    txtTime.setText("");
                                    Toast.makeText(activity_home_menu.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(week == Calendar.SATURDAY){
                                if((hour >= 16 && hour <= 18) || (hour >= 21 && hour <= 23)){
                                    txtTime.setText((hour > 12 ? (hour-12):hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                }
                                else{
                                    txtTime.setText("");
                                    Toast.makeText(activity_home_menu.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(week == Calendar.SUNDAY){
                                if(hour >= 10 && hour <= 15){
                                    txtTime.setText((hour > 12 ? (hour-12):hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                }
                                else{
                                    txtTime.setText("");
                                    Toast.makeText(activity_home_menu.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                txtTime.setText("");
                                Toast.makeText(activity_home_menu.this, "Selected time is not available. Please see the appointment schedule " +
                                        "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            txtTime.setText("");
                            Toast.makeText(activity_home_menu.this, "Please select a date before setting time.", Toast.LENGTH_LONG).show();
                        }
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
                txtName = (EditText) commonDialog.findViewById(R.id.txtCounselName);
                txtEmail = (EditText) commonDialog.findViewById(R.id.txtCounselEmail);
                txtContact = (EditText) commonDialog.findViewById(R.id.txtCounselNumber);
                txtDate = (EditText) commonDialog.findViewById(R.id.txtDueDate);
                txtTime = (EditText) commonDialog.findViewById(R.id.txtDueTime);
                txtProblem = (EditText) commonDialog.findViewById(R.id.txtProblem);

                if(txtName.getText().equals("") || txtName.getText().length() == 0){
                    Toast.makeText(activity_home_menu.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    txtName.requestFocus();
                }
                else if(txtContact.getText().equals("") || txtContact.getText().length() == 0){
                    Toast.makeText(activity_home_menu.this, "Please enter your contact number.", Toast.LENGTH_SHORT).show();
                    txtContact.requestFocus();
                }
                else if(txtEmail.getText().equals("") || txtEmail.getText().length() == 0){
                    Toast.makeText(activity_home_menu.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    txtEmail.requestFocus();
                }
                else if(txtDate.getText().equals("") || txtDate.getText().length() == 0){
                    Toast.makeText(activity_home_menu.this, "Please select a date of appointment.", Toast.LENGTH_SHORT).show();
                    txtDate.requestFocus();
                }
                else if(txtTime.getText().equals("") || txtTime.getText().length() == 0){
                    Toast.makeText(activity_home_menu.this, "Please select a time for your appointment", Toast.LENGTH_SHORT).show();
                    txtTime.requestFocus();
                }
                else if(txtProblem.getText().equals("") || txtProblem.getText().length() == 0){
                    Toast.makeText(activity_home_menu.this, "Briefly describe your problem here.", Toast.LENGTH_SHORT).show();
                    txtProblem.requestFocus();
                }
                else{
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
                                "\n Problem in brief: " + txtProblem.getText().toString() + "\n\n Thank you for your time. \n\n Regards,\n " + txtName.getText().toString();
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
                TextView tvProductDescription = (TextView) findViewById(R.id.tvAuthor);
                String description = tvProductDescription.getText().toString();

                Intent intent=new Intent(activity_home_menu.this,activity_product_info.class);
                intent.putExtra("title",title);
                intent.putExtra("shortDescription",description);
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

        title = getResources().getString(R.string.bw_more_about);
        content = getResources().getString(R.string.bw_facilitate) + "\n\n" +
                getResources().getString(R.string.bw_bullet_point_1) + "\n" +
                getResources().getString(R.string.bw_bullet_point_2) + "\n" +
                getResources().getString(R.string.bw_bullet_point_3) + "\n" +
                getResources().getString(R.string.bw_bullet_point_4) + "\n" +
                getResources().getString(R.string.bw_bullet_point_5) + "\n" +
                getResources().getString(R.string.bw_bullet_point_6) + "\n" +
                getResources().getString(R.string.bw_bullet_point_7) + "\n" +
                getResources().getString(R.string.bw_bullet_point_8) + "\n" +
                getResources().getString(R.string.bw_bullet_point_9) + "\n" +
                getResources().getString(R.string.bw_bullet_point_10) + "\n";
        AboutBrainwaveObject obj5 = new AboutBrainwaveObject(title, content);
        results.add(obj5);

        return results;
    }

    public void actionsProductsList(){

        final List<String> productList = new ArrayList();
        final ProgressBar pd_progress = (ProgressBar) findViewById(R.id.pd_progress);

        FirebaseApp.initializeApp(this);

        mReference= FirebaseDatabase.getInstance().getReference("products");
        mReference.orderByChild("orderid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 1;
                for (DataSnapshot data :dataSnapshot.getChildren()){
                    if((Boolean) data.child("active").getValue()){
                        productList.add(i + ". " + data.getKey());
                        i++;
                    }
                }

                ArrayAdapter adapter = new ArrayAdapter(activity_home_menu.this,R.layout.product_names_list_item,productList);
                ListView listView = (ListView) findViewById(R.id.list_view_products);
                listView.setAdapter(adapter);
                pd_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void actionTips(){

    }
}
