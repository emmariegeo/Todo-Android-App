package com.egeorge.todoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Locale;
import java.util.UUID;

/**
 * TaskFragment displays a Task's details
 */
public class TaskFragment extends Fragment
{
   // Model
   private Task mTask;
   // View Elements
   private TextView mTitleTextView;
   private TextView mDateTextView;
   private TextView mDescriptionTextView;
   private TextView mPriorityTextView;

   /**
    * onCreate gets the activity's intent and extra and sets mTask with taskId
    * @param savedInstanceState Bundle
    */
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      UUID taskId = (UUID) requireActivity().getIntent().getSerializableExtra(TaskActivity.EXTRA_TASK_ID);
      mTask = TaskList.get(getActivity()).getTask(taskId);
      setHasOptionsMenu(true);
   }

   /**
    * Display TaskFragment's option menu
    * @param menu Menu
    * @param inflater MenuInflater
    */
   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.menu.fragment_task, menu);
   }

   /**
    * Set option item onclick
    * @param item MenuItem
    * @return boolean
    */
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // when edit task item is clicked, start EditTaskActivity
      if (item.getItemId() == R.id.menu_item_edit_task)
      {
         Intent intent = EditTaskActivity.newIntent(getActivity(), mTask.getId());
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   /**
    * OnCreateView sets the textViews with Task attributes
    * @param inflater LayoutInflater
    * @param container ViewGroup
    * @param savedInstanceState Bundle
    * @return View
    */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   {
      View v = inflater.inflate(R.layout.fragment_task, container, false);
      // Select view elements by ID
      mTitleTextView = v.findViewById(R.id.task_title_label);
      mDateTextView = v.findViewById(R.id.task_date_label);
      mDescriptionTextView = v.findViewById(R.id.task_description_label);
      mPriorityTextView = v.findViewById(R.id.task_priority_label);
      // Update view elements
      mTitleTextView.setText(mTask.getTitle());
      String deadlineString = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(mTask.getDeadline());
      mDateTextView.setText(deadlineString);
      mDescriptionTextView.setText(mTask.getDescription());
      mPriorityTextView.setText(String.valueOf(mTask.getPriority()));
      return v;
   }
}