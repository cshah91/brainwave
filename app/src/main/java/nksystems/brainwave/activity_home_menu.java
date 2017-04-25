package nksystems.brainwave;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class activity_home_menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

ViewStub contentAbout,contentCounselling,contentFlower,contentPlay,contentHandwriting,contentAstrology, contentPari;
LinearLayout root;
    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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

        contentPari=(ViewStub)findViewById(R.id.contentPari);

        contentPlay=(ViewStub)findViewById(R.id.contentPlay);

        contentHandwriting=(ViewStub)findViewById(R.id.contentHandwriting);

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
            /*Intent intent = new Intent(this, activity_counselling_services.class);
            startActivity(intent);*/

            /*stub.invalidate();

            stub.setLayoutResource(R.layout.counselling_services_layout);
            stub.inflate();*/
            switchContent(contentCounselling);
            expandableListView = (ExpandableListView) findViewById(R.id.expandContact);

            // preparing list data
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expandableListView.setAdapter(listAdapter);

            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_flower) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_play) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_herbal) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_handwriting) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_astro) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(id == R.id.nav_about){
            switchContent(contentAbout);
            drawer.closeDrawer(Gravity.START);
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
    private void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Schedule an Appointment");
        listDataHeader.add("Counselling Services");
        listDataHeader.add("Counselling Services + Medicines");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}
