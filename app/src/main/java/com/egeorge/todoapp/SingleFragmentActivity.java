package com.egeorge.todoapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

/**
 * SingleFragmentActivity creates a single Fragment
 * extends AppCompatActivity
 */
public abstract class SingleFragmentActivity extends AppCompatActivity
{
   protected abstract Fragment createFragment();

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // Set ActionBar with up enabled
      setContentView(R.layout.activity_fragment);
      setSupportActionBar(findViewById(R.id.toolbar));
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      FragmentManager fm = getSupportFragmentManager();
      Fragment fragment = fm.findFragmentById(R.id.fragment_container);

      if (fragment == null) {
         fragment = createFragment();
         fm.beginTransaction()
               .add(R.id.fragment_container, fragment)
               .commit();
      }
   }
}
