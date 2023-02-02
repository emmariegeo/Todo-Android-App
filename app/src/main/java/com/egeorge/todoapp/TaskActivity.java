package com.egeorge.todoapp;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import java.util.Objects;
import java.util.UUID;

/**
 * TaskActivity holds a TaskFragment
 */
public class TaskActivity extends SingleFragmentActivity
{
   public static final String EXTRA_TASK_ID =
         "com.egeorge.todoapp.task_id";
   /**
    * Creates intent with extra on which taskId to use
    *
    * @param packageContext Context
    * @param taskId UUID
    * @return Intent
    */
   public static Intent newIntent(Context packageContext, UUID taskId)
   {
      Intent intent = new Intent(packageContext, TaskActivity.class);
      intent.putExtra(EXTRA_TASK_ID, taskId);
      return intent;
   }

   @Override
   protected Fragment createFragment() {
      // Set action bar title to empty
      Objects.requireNonNull(getSupportActionBar()).setTitle("");
      return new TaskFragment();
   }
}
