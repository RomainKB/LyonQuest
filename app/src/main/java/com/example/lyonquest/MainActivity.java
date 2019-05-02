package com.example.lyonquest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Utils.onActivityCreateSetTheme(this);
      setContentView(R.layout.activity_main);
      BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);

      bottomNavigationView.setOnNavigationItemSelectedListener
              (new BottomNavigationView.OnNavigationItemSelectedListener() {
                  @Override
                  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                      Fragment selectedFragment = null;
                      switch (item.getItemId()) {
                          case R.id.navigation_route:
                              selectedFragment = FragmentRoutes.newInstance();
                              break;
                          case R.id.navigation_add:
                              selectedFragment = FragmentAddRoutes.newInstance();
                              break;
                          case R.id.navigation_parameters:
                              selectedFragment = FragmentSettings.newInstance();
                              break;
                      }
                      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                      transaction.replace(R.id.frame_layout, selectedFragment);
                      transaction.commit();
                      return true;
                  }
              });

      //Manually displaying the first fragment - one time only
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.frame_layout, FragmentRoutes.newInstance());
      transaction.commit();

      //Used to select an item programmatically
      //bottomNavigationView.getMenu().getItem(2).setChecked(true);
  }

}
