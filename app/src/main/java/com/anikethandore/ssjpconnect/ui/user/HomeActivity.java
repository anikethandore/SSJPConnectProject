package com.anikethandore.ssjpconnect.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.anikethandore.ssjpconnect.R;
import com.anikethandore.ssjpconnect.ui.HomeFragment;
import com.anikethandore.ssjpconnect.ui.MoreFragment;
import com.anikethandore.ssjpconnect.ui.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SmoothBottomBar bottomNavigationView;

    //darwer view
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //navigation hooks
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);

        replace(new HomeFragment());
      bottomNavigationView=findViewById(R.id.bottomBar);
      bottomNavigationView.setOnItemSelectedListener(new OnItemSelectedListener() {
          @Override
          public boolean onItemSelect(int i) {

              switch (i){
                  case 0:
                      replace(new HomeFragment());
                      break;

                  case 1:
                      replace(new ProfileFragment());
                      break;

                  case 2:
                      replace(new MoreFragment());
                      break;
              }

              return true;
          }
      });


      toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);
      drawerLayout.addDrawerListener(toggle);
      toggle.syncState();

      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
      navigationView.setNavigationItemSelectedListener(this);


    }

    private void replace(Fragment fragment) {
       FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.frameSpace,fragment);
       transaction.commit();
    }

    /*Do not touch this lies of code */

    @Override
    protected void onStart() {
        super.onStart();
        
        checkUser();
    }



    private void checkUser() {
        SharedPreferences sharedPreferences=getSharedPreferences("userLoginData",MODE_PRIVATE);
        Boolean counter=sharedPreferences.getBoolean("logincounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
        String email=sharedPreferences.getString("email",String.valueOf(MODE_PRIVATE));
        String uidtxt=sharedPreferences.getString("uid",String.valueOf(MODE_PRIVATE));

        if (counter){
            /*userNametxt.setText(uidtxt);
            userEmailtxt.setText(email);*/
        }
        else {
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
        }
    }

    //back button pressed
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            HomeActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notification:
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_changepass:
                Toast.makeText(this, "change password", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_aboutus:
                Toast.makeText(this, "about us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }
}