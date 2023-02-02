package com.egeorge.todoapp;

import androidx.fragment.app.Fragment;

import java.util.Objects;

/**
 * TaskListActivity holds a TaskListFragment
 */
public class TaskListActivity extends SingleFragmentActivity
{
   @Override
   protected Fragment createFragment() {
      Objects.requireNonNull(getSupportActionBar()).setTitle("Tasks");
      return new TaskListFragment();
   }
}