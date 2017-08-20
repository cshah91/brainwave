package nksystems.brainwave;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class is used for creating the navigation drawer and ViewStubs for each menu item
 *
 * @author Nemi Shah
 * @version 1.0
 * @date 24-04-2017
 */
public class HomeMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewStub contentAbout, contentCounselling, contentPari, contentProducts, contentTips;
    LinearLayout root;
    DatabaseReference mReference;
    StorageReference sReference;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    Dialog commonDialog;
    String selectedDate;

    int[] bachImages = {R.drawable.bach_flowers_1, R.drawable.bach_flowers_2, R.drawable.bach_flowers_4, R.drawable.bach_flowers_7, R.drawable.bach_flowers_8, R.drawable.bach_flowers_9};
    CarouselView carouselView;
    ImageListener imageListener;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(bachImages[position]);
            }
        };

        // Navigation Drawer Menu: About Brainwave
        contentAbout = (ViewStub) findViewById(R.id.contentAbout);
        contentAbout.setLayoutResource(R.layout.activity_card_view);
        contentAbout.inflate();

        // Navigation Drawer Menu: Counselling Services
        contentCounselling = (ViewStub) findViewById(R.id.contentCounselling);
        contentCounselling.setLayoutResource(R.layout.activity_counselling_services);
        contentCounselling.inflate();

        // Navigation Drawer Menu: Pari Herbal Products
        contentPari = (ViewStub) findViewById(R.id.contentPari);
        contentPari.setLayoutResource(R.layout.activity_pari_herbal_products);
        contentPari.inflate();

        // Settings Menu: Products
        contentProducts = (ViewStub) findViewById(R.id.contentProducts);
        contentProducts.setLayoutResource(R.layout.activity_product_names);
        contentProducts.inflate();

        // Settings Menu: Tips
        contentTips = (ViewStub) findViewById(R.id.contentTips);
        contentTips.setLayoutResource(R.layout.activity_tips);
        contentTips.inflate();

        // Used for returning to home page from another activity with selected menu item
        String activeStub = getIntent().getStringExtra("active_activity");
        switch (activeStub) {
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

        root = (LinearLayout) findViewById(R.id.rootLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Open the About Brainwave page by default
        actionAboutBrainwave();
    }

    /**
     * Close drawer when phone's back key pressed
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_menu, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will automatically
     * handle clicks on the Home/Up button, so long as you specify a parent
     * activity in AndroidManifest.xml.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_products) {
            switchContent(contentProducts);
            actionsProductsList();
        } else if (id == R.id.menu_tips) {
            switchContent(contentTips);
        }

        return true;
    }

    /**
     * Handles navigation view item clicks.
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        if (id == R.id.nav_counseling) {
            switchContent(contentCounselling);
            actionsCounsellingServices();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_herbal) {
            switchContent(contentPari);
            actionsPariHerbalProducts();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_about) {
            switchContent(contentAbout);
            actionAboutBrainwave();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_close) {
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    /**
     * Used for switching content between created ViewStubs
     *
     * @param v
     */
    public void switchContent(View v) {
        contentCounselling.setVisibility(View.GONE);
        contentPari.setVisibility(View.GONE);
        contentAbout.setVisibility(View.GONE);
        contentProducts.setVisibility(View.GONE);
        contentTips.setVisibility(View.GONE);
        v.setVisibility(View.VISIBLE);
    }

    /**
     *
     */
    public void actionsCounsellingServices() {
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(bachImages.length);

        CardView cvAbout = (CardView) findViewById(R.id.cvAbout);
        cvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(HomeMenuActivity.this, AboutServicesActivity.class);
                startActivity(intent);
            }
        });

        final Button btnPackage1 = (Button) findViewById(R.id.btnPackage1);
        final Button btnPackage2 = (Button) findViewById(R.id.btnPackage2);

        btnPackage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(HomeMenuActivity.this, CounsellingFormActivity.class);
                intent.putExtra("amount", "500");
                intent.putExtra("packagetype", "2");
                startActivity(intent);
            }
        });

        btnPackage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(HomeMenuActivity.this, CounsellingFormActivity.class);
                intent.putExtra("amount", "2000");
                intent.putExtra("packagetype", "1");
                startActivity(intent);
            }
        });

        ImageButton btnTelephone = (ImageButton) findViewById(R.id.btnTelephone);
        ImageButton btnSkype = (ImageButton) findViewById(R.id.btnSkype);
        ImageButton btnPersonal = (ImageButton) findViewById(R.id.btnPersonal);

        btnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(HomeMenuActivity.this);
                dialog.setContentView(R.layout.dialog_appointment_form);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(1, dialog, 1);
                Button btnAppointment = (Button) dialog.findViewById(R.id.btnCounselSetAppointment);
                btnAppointment.setText("Set Telephonic Appointment");
                dialog.show();
            }
        });

        btnSkype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(HomeMenuActivity.this);
                dialog.setContentView(R.layout.dialog_appointment_form);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(1, dialog, 2);
                Button btnAppointment = (Button) dialog.findViewById(R.id.btnCounselSetAppointment);
                btnAppointment.setText("Set Skype Appointment");
                dialog.show();
            }
        });

        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(HomeMenuActivity.this);
                dialog.setContentView(R.layout.dialog_appointment_form);
                setDateTimePicker(dialog);
                // Third parameter for setAppointment is type of appointment
                // Telephonic: 1, Skype: 2, Personal: 3
                setAppointment(1, dialog, 3);
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
                Intent intent = new Intent(HomeMenuActivity.this, MedicinesActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * @param dialog
     */
    public void setDateTimePicker(Dialog dialog) {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeMenuActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Get day of the week for the selected date
                        Calendar cal = new GregorianCalendar(year, month, day);
                        int week = cal.get(Calendar.DAY_OF_WEEK);

                        if (week == Calendar.SATURDAY || week == Calendar.SUNDAY || week == Calendar.WEDNESDAY) {
                            EditText txtDate = (EditText) commonDialog.findViewById(R.id.txtDueDate);
                            txtDate.setText(day + "-" + (month + 1) + "-" + year);
                            selectedDate = txtDate.getText().toString();
                        } else {
                            String message = "Appointments available only for Wednesday, Saturday and Sunday. " +
                                    "Please select another date.";
                            Toast.makeText(HomeMenuActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                }, mYear, mMonth, mDay);
                calendar.add(Calendar.DATE, 1);
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(HomeMenuActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        EditText txtTime = (EditText) commonDialog.findViewById(R.id.txtDueTime);
                        if (selectedDate != null && selectedDate.length() > 0) {
                            String date[] = selectedDate.split("-");
                            Calendar cal = new GregorianCalendar(Integer.parseInt(date[2]), (Integer.parseInt(date[1]) - 1),
                                    Integer.parseInt(date[0]));
                            int week = cal.get(Calendar.DAY_OF_WEEK);
                            String min = "";
                            if (minute >= 0 && minute < 10)
                                min = "0" + minute;
                            else
                                min = "" + minute;
                            if (week == Calendar.WEDNESDAY) {
                                if (hour >= 9 && hour <= 12) {
                                    txtTime.setText((hour > 12 ? (hour - 12) : hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                } else {
                                    txtTime.setText("");
                                    Toast.makeText(HomeMenuActivity.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            } else if (week == Calendar.SATURDAY) {
                                if ((hour >= 16 && hour <= 18) || (hour >= 21 && hour <= 23)) {
                                    txtTime.setText((hour > 12 ? (hour - 12) : hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                } else {
                                    txtTime.setText("");
                                    Toast.makeText(HomeMenuActivity.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            } else if (week == Calendar.SUNDAY) {
                                if (hour >= 10 && hour <= 15) {
                                    txtTime.setText((hour > 12 ? (hour - 12) : hour) + ":" + min + (hour > 11 ? " PM" : " AM"));
                                } else {
                                    txtTime.setText("");
                                    Toast.makeText(HomeMenuActivity.this, "Selected time is not available. Please see the appointment schedule " +
                                            "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                txtTime.setText("");
                                Toast.makeText(HomeMenuActivity.this, "Selected time is not available. Please see the appointment schedule " +
                                        "time slots mentioned below.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            txtTime.setText("");
                            Toast.makeText(HomeMenuActivity.this, "Please select a date before setting time.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
    }

    /**
     * @param id
     * @param dialog
     * @param type
     */
    public void setAppointment(final int id, Dialog dialog, final int type) {
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

                if (txtName.getText().equals("") || txtName.getText().length() == 0) {
                    Toast.makeText(HomeMenuActivity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    txtName.requestFocus();
                } else if (txtContact.getText().equals("") || txtContact.getText().length() == 0) {
                    Toast.makeText(HomeMenuActivity.this, "Please enter your contact number.", Toast.LENGTH_SHORT).show();
                    txtContact.requestFocus();
                } else if (txtEmail.getText().equals("") || txtEmail.getText().length() == 0) {
                    Toast.makeText(HomeMenuActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    txtEmail.requestFocus();
                } else if (txtDate.getText().equals("") || txtDate.getText().length() == 0) {
                    Toast.makeText(HomeMenuActivity.this, "Please select a date of appointment.", Toast.LENGTH_SHORT).show();
                    txtDate.requestFocus();
                } else if (txtTime.getText().equals("") || txtTime.getText().length() == 0) {
                    Toast.makeText(HomeMenuActivity.this, "Please select a time for your appointment", Toast.LENGTH_SHORT).show();
                    txtTime.requestFocus();
                } else if (txtProblem.getText().equals("") || txtProblem.getText().length() == 0) {
                    Toast.makeText(HomeMenuActivity.this, "Briefly describe your problem here.", Toast.LENGTH_SHORT).show();
                    txtProblem.requestFocus();
                } else {
                    String appointmentType = "";

                    String message = "Hello, \n\n I would like to schedule an appointment with you. Below are my details: \n\n Name: " + txtName.getText().toString() +
                            "\n Email: " + txtEmail.getText().toString() + "\n Contact Number: " + txtContact.getText().toString() +
                            "\n Date of Appointment: " + txtDate.getText().toString() + "\n Time of Appointment: " + txtTime.getText().toString() +
                            "\n Problem in brief: " + txtProblem.getText().toString() + "\n\n Thank you for your time. \n\n Regards,\n " + txtName.getText().toString();

                    String userMessage = "Dear " + txtName.getText().toString() + ", \n\n Thank you for setting up an appointment with us. Below is your appointment details: \n\n Name: " + txtName.getText().toString() +
                            "\n Email: " + txtEmail.getText().toString() + "\n Contact Number: " + txtContact.getText().toString() +
                            "\n Date of Appointment: " + txtDate.getText().toString() + "\n Time of Appointment: " + txtTime.getText().toString() +
                            "\n Problem in brief: " + txtProblem.getText().toString() + "\n\n Thank you for your time. \n\n Regards,\n Brainwave Support Team";

                    if (type == 1) {
                        appointmentType = "Telephonic";
                    } else if (type == 2) {
                        appointmentType = "Skype";
                    } else if (type == 3) {
                        appointmentType = "Personal";
                    }

                    String to = txtEmail.getText().toString();
                    String subject = "";
                    if (id == 1) {
                        subject = "Counselling Services - " + appointmentType + ": Appointment";
                    } else if (id == 2) {
                        subject = "Flower Therapy - " + appointmentType + ": Appointment";
                    } else if (id == 3) {
                        subject = "Handwriting & Signature Analysis - " + appointmentType + ": Appointment";
                    }

                    // Send mail to admin with appointment details
                    new AsyncSendMail().execute(getResources().getString(R.string.adminEmail), subject, message, getResources().getString(R.string.appointmentMailFrom));
                    // Send mail to user with appointment details
                    new AsyncSendMail().execute(to, subject, userMessage, getResources().getString(R.string.appointmentMailFrom));
                }
            }
        });
    }

    /**
     *
     */
    public void actionsPariHerbalProducts() {
        final GridView productslist;
        final ProgressBar progress;
        productslist = (GridView) findViewById(R.id.gvBooksList);
        progress = (ProgressBar) findViewById(R.id.progress);

        FirebaseApp.initializeApp(this);
        mReference = FirebaseDatabase.getInstance().getReference("products");
        sReference = FirebaseStorage.getInstance().getReference("imagethumbnails");
        final CustomGridItem item = new CustomGridItem(this, sReference, mReference, "orderid");
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
                TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                String title = tvTitle.getText().toString();
                TextView tvProductDescription = (TextView) findViewById(R.id.tvAuthor);
                String description = tvProductDescription.getText().toString();

                Intent intent = new Intent(HomeMenuActivity.this, ProductDescriptionActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("shortDescription", description);
                finish();
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    public void actionAboutBrainwave() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recViewAboutBW);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Returns content for About Brainwave page
     * @return
     */
    private List<Brainwave> getDataSet() {
        List<Brainwave> results = new ArrayList<Brainwave>();
        String title = getResources().getString(R.string.bw_name);
        String content = getResources().getString(R.string.bw_intro);
        Brainwave obj = new Brainwave(title, content);
        results.add(obj);

        title = getResources().getString(R.string.bw_history_title);
        content = getResources().getString(R.string.bw_history_info1) + "\n\n" +
                getResources().getString(R.string.bw_history_info2) + "\n\n" +
                getResources().getString(R.string.bw_history_info3);
        Brainwave obj1 = new Brainwave(title, content);
        results.add(obj1);

        title = getResources().getString(R.string.bw_founder_title);
        content = getResources().getString(R.string.bw_founder_info1) + "\n\n" +
                getResources().getString(R.string.bw_founder_info2) + "\n\n" +
                getResources().getString(R.string.bw_founder_info3) + "\n\n" +
                getResources().getString(R.string.bw_founder_info4);
        Brainwave obj2 = new Brainwave(title, content);
        results.add(obj2);

        title = getResources().getString(R.string.bw_slogan_title);
        content = getResources().getString(R.string.bw_slogan_info);
        Brainwave obj3 = new Brainwave(title, content);
        results.add(obj3);

        title = getResources().getString(R.string.bw_mission_title);
        content = getResources().getString(R.string.bw_mission_info);
        Brainwave obj4 = new Brainwave(title, content);
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
        Brainwave obj5 = new Brainwave(title, content);
        results.add(obj5);
        return results;
    }

    /**
     *
     */
    public void actionsProductsList() {
        final List<String> productList = new ArrayList();
        final ProgressBar pd_progress = (ProgressBar) findViewById(R.id.pd_progress);

        FirebaseApp.initializeApp(this);
        mReference = FirebaseDatabase.getInstance().getReference("products");
        mReference.orderByChild("orderid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 1;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if ((Boolean) data.child("active").getValue()) {
                        productList.add(i + ". " + data.getKey());
                        i++;
                    }
                }

                ArrayAdapter adapter = new ArrayAdapter(HomeMenuActivity.this, R.layout.item_product_name, productList);
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

    /**
     * Send appointment mails
     */
    private class AsyncSendMail extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                GMailSender sender = new GMailSender(getResources().getString(R.string.gmailUsername), getResources().getString(R.string.gmailPassword));
                sender.sendMail(params[1],
                        params[2],
                        params[3],
                        params[0]);
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            commonDialog.hide();
            Toast.makeText(HomeMenuActivity.this, "Your appointment details have been sent your email. Thank you !", Toast.LENGTH_LONG).show();
        }
    }
}