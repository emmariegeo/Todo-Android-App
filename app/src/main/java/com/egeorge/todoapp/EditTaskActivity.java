package com.egeorge.todoapp;

import static com.egeorge.todoapp.TaskActivity.EXTRA_TASK_ID;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;


import java.util.Objects;
import java.util.UUID;

public class EditTaskActivity extends SingleFragmentActivity
{
   /**
    * Creates an intent with extra on which TaskId to use
    *
    * @param packageContext Context
    * @param taskId UUID
    * @return Intent
    */
   public static Intent newIntent(Context packageContext, UUID taskId)
   {
      Intent intent = new Intent(packageContext, EditTaskActivity.class);
      intent.putExtra(EXTRA_TASK_ID, taskId);
      return intent;
   }

   @Override
   protected Fragment createFragment() {
      Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Task");
      return new EditTaskFragment();
   }
}
