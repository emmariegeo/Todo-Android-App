package com.egeorge.todoapp;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

/**
 * create an instance of this fragment.
 */
public class FilterFragment extends DialogFragment
{
   // Bundle and Request Keys
   private static final String REQUEST_FILTER = "ListFilter";
   public static final String ARG_SORT = "SortType";
   public static final String ARG_COMPLETED = "ShowCompleted";

   /**
    * Start FilterFragment instance with given sortType and showCompleted args
    * @param sortType String representing current sort filter
    * @param showCompleted boolean true if completed tasks are displayed
    * @return FilterFragment
    */
   public static FilterFragment newInstance(String sortType, boolean showCompleted) {
      Bundle args = new Bundle();
      args.putString(ARG_SORT, sortType);
      args.putBoolean(ARG_COMPLETED, showCompleted);

      FilterFragment fragment = new FilterFragment();
      fragment.setArguments(args);
      return fragment;
   }

   /**
    * Create filter dialog with bundle
    * @param savedInstanceState Bundle
    * @return Dialog with filter settings
    */
   @NonNull
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      View v = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_filter, null);
      // Instantiate filter settings with bundled args
      String sortType = "Title";
      boolean showCompleted = false;
      if (getArguments() != null)
      {
         showCompleted = getArguments().getBoolean(ARG_COMPLETED);
         sortType = getArguments().getString(ARG_SORT);
      }
      // Select view elements by ID
      RadioGroup mRadioGroup = v.findViewById(R.id.sort_radio_group);
      SwitchCompat mSwitchCompleted = v.findViewById(R.id.switch_show_completed);
      Button mDeleteCompleted_btn = v.findViewById(R.id.deleteCompleted_btn);
      // Set Delete Completed Tasks button listener
      mDeleteCompleted_btn.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            TaskList taskList = TaskList.get(getActivity());
            // Clear completed tasks from sTaskList
            taskList.clearCompletedTasks();
            Toast.makeText(getActivity(),
                  "Completed tasks have been deleted", Toast.LENGTH_SHORT).show();
         }
      });
      // Instantiate filter radio group and completed tasks switch to match current settings
      switch (sortType)
      {
         case "Priority":
            mRadioGroup.check(R.id.priority_radio_btn);
            break;
         case "Deadline":
            mRadioGroup.check(R.id.deadline_radio_btn);
            break;
         case "Title":
            mRadioGroup.check(R.id.title_radio_btn);
            break;
      }
      mSwitchCompleted.setChecked(showCompleted);
      // Return the Dialog and set ok button
      return new AlertDialog.Builder(requireActivity()).setView(v).setTitle(R.string.filterTask).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            String sortSelected = "";
            if (mRadioGroup.getCheckedRadioButtonId()==R.id.priority_radio_btn) {
               sortSelected = "Priority";
            } else if (mRadioGroup.getCheckedRadioButtonId()==R.id.deadline_radio_btn) {
               sortSelected = "Deadline";
            } else if (mRadioGroup.getCheckedRadioButtonId()==R.id.title_radio_btn) {
               sortSelected = "Title";
            }
            boolean showCompleted = mSwitchCompleted.isChecked();
            sendResult(sortSelected, showCompleted);
         }
      }).create();
   }
   // Send Date Result to EditTaskFragment
   private void sendResult(String sortType, boolean showCompleted) {
      Bundle args = new Bundle();
      args.putString(ARG_SORT, sortType);
      args.putBoolean(ARG_COMPLETED, showCompleted);
      getParentFragmentManager().setFragmentResult(REQUEST_FILTER,args);
   }
}