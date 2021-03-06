package com.example.puza.mobileui.ui;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puza.mobileui.R;
import com.example.puza.mobileui.fragments.CartFragment;
import com.example.puza.mobileui.fragments.FeaturedSortItemFragment;
import com.example.puza.mobileui.fragments.HomeFragment;
import com.example.puza.mobileui.fragments.LatestFragment;
import com.example.puza.mobileui.fragments.MoreFragment;
import com.example.puza.mobileui.fragments.NameFragment;
import com.example.puza.mobileui.fragments.OfferFragment;
import com.example.puza.mobileui.fragments.ProductFragment;
import com.example.puza.mobileui.helper.BottomNavigationHelper;

public class
MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    FragmentTransaction transaction;

    Toolbar toolbar;
    TextView toolbarTitle;

    Dialog myDialog;
    Button alert, close;
    ImageView searchIcon;
    EditText search;

    private DrawerLayout mDrawerLayout;
    //ActionBarDrawerToggle actionBarDrawerToggle;

//    ImageView imageView;
    ImageView deleteIcon;
    ImageView notificationIcon;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbarName);

        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        search = (EditText) findViewById(R.id.search);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setVisibility(View.VISIBLE);
            }
        });

        setUpBottomNavigation();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        imageView = (ImageView)findViewById(R.id.navigationMenu);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDrawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

        notificationIcon = (ImageView)findViewById(R.id.notificationIcon);
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Notification icon", Toast.LENGTH_SHORT).show();

            }
        });
    }

//    @Override
//    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onPostCreate(savedInstanceState, persistentState);
//
//        actionBarDrawerToggle.syncState();
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else if (mSelectedItem == homeItem.getItemId()) {
            onBackPressed();

        }

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
    }

    private void setUpBottomNavigation() {
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationHelper.removeShiftMode(mBottomNav);
        if (mBottomNav != null) {

            // Select first menu item by default and show Fragment accordingly.
            Menu menu = mBottomNav.getMenu();
            selectFragment(menu.getItem(0));
            mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectFragment(item);
                    return true;
                }
            });
        }

    }

    private void selectFragment(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.menu_home:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new HomeFragment());
                toolbarTitle.setText("Home");
                break;
            case R.id.menu_user:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new ProductFragment());
                toolbarTitle.setText("Users");
                break;
            case R.id.menu_offer:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new FeaturedSortItemFragment());
                toolbarTitle.setText("Offers");
                break;
            case R.id.menu_cart:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new CartFragment());
                toolbarTitle.setText("My cart");
                break;
            case R.id.menu_more:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new MoreFragment());
                toolbarTitle.setText("More");
                break;
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        int id = item.getItemId();

//        name,latest.basket,shopoffers,art_handicraft,furniturehome_lifestyles,travel_trecking,electronics,education_learning,nav_logout
//                others,e,d,c,b
        if( id == R.id.name){
            fragment = new NameFragment();

        }else if (id == R.id.latest){
            fragment = new LatestFragment();

        }else if (id == R.id.basket){

        }else if (id == R.id.shop){

        }else if (id == R.id.offers){

        }else if (id == R.id.art_handicraft){

        }else if (id == R.id.furniture){

        }else if (id == R.id.home_lifestyles){

        }else if (id == R.id.travel_trecking){

        }else if (id == R.id.electronics){

        }else if (id == R.id.education_learning){

        }else if (id == R.id.nav_logout){

        }else if (id == R.id.others){

        }else if (id == R.id.e){

        }else if (id == R.id.d){

        }else if (id == R.id.c){

        }else if (id == R.id.b){

        }

        if (fragment!= null){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, fragment);
            ft.commit();
        }

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}