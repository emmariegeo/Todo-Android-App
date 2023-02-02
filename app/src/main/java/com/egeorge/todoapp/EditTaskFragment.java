package com.egeorge.todoapp;

import static com.egeorge.todoapp.TaskActivity.EXTRA_TASK_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.text.Editable;
import android.text.TextWatcher;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 */
public class EditTaskFragment extends Fragment
{
   private static final String DIALOG_DATE = "DialogDate";
   // Request key
   private static final String REQUEST_DATE = "Deadline";
   // Model
   private Task mTask;
   private Task taskCopy;
   private TaskList sTaskList;
   private boolean isNewTask = false;
   // View elements
   private TextView mTitleEditTextView;
   private Button mDateEdit_btn;
   private Button mUpdateTask_btn;
   private Spinner mPriority_spinner;
   private TextView mDescriptionEditTextView;
   /**
    * onCreate gets the intent and extra and sets mTask with TaskId
    *
    * @param savedInstanceState Bundle
    */
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      UUID taskId = (UUID) requireActivity().getIntent().getSerializableExtra(EXTRA_TASK_ID);
      sTaskList = TaskList.get(getActivity());
      mTask = sTaskList.getTask(taskId);
      setHasOptionsMenu(true);
   }
   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.menu.fragment_edit_task, menu);
   }
   /**
    * OnCreateView sets the textViews with Course attributes
    *
    * @param inflater           LayoutInflater
    * @param container          ViewGroup
    * @param savedInstanceState Bundle
    * @return View
    */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   {
      View v = inflater.inflate(R.layout.fragment_edit_task, container, false);
      // Select elements by ID
      mTitleEditTextView = v.findViewById(R.id.editText_Task_Title);
      mDateEdit_btn = v.findViewById(R.id.pickDate_btn);
      mUpdateTask_btn = v.findViewById(R.id.updateTask_btn);
      mPriority_spinner = v.findViewById(R.id.priority_spinner);
      mDescriptionEditTextView = v.findViewById(R.id.editText_Description);

      // Check for proper title format before enabling update button
      mTitleEditTextView.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(
               CharSequence s, int start, int count, int after) {
            // Left blank intentionally
         }
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count)
         {
            if (s.length() == 0)
            {
               mTitleEditTextView.requestFocus();
               mTitleEditTextView.setError("Title required");
            }
         }

         @Override
         public void afterTextChanged(Editable s) {
            validateTask(mTitleEditTextView.getText());
         }
      });

      // UPDATE VIEWS
      // If creating a task, update view to create
      if (mTask == null) {
         mTask = new Task();
         isNewTask = true;
         mUpdateTask_btn.setText(R.string.createtask);
         mUpdateTask_btn.setEnabled(false);
      } else {
         // If updating a task, display current title and description
         mTitleEditTextView.setText(mTask.getTitle());
         mDescriptionEditTextView.setText(mTask.getDescription());
      }
      // Clone mTask in case task update is canceled.
      taskCopy = mTask.clone();
      // Display current deadline on date button
      String deadlineString = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(mTask.getDeadline());
      mDateEdit_btn.setText(deadlineString);

      // When date button is clicked, start DatePickerFragment dialog
      mDateEdit_btn.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v) {
            FragmentManager manager = getParentFragmentManager();
            // Send current mTask deadline to DatePicker dialog
            DatePickerFragment dialog = DatePickerFragment.newInstance(mTask.getDeadline());
            // Update deadline when received
            manager.setFragmentResultListener(REQUEST_DATE, getViewLifecycleOwner(), (requestKey, result) -> {
               // Update button to display date from DatePicker
               if (requestKey.equals(REQUEST_DATE)) {
                  Date date = (Date) result.getSerializable(DatePickerFragment.ARG_DATE);
                  taskCopy.setDeadline(date);
                  updateDate();
               }
            });
            dialog.show(manager, DIALOG_DATE);
         }
      });

      // When updateTask is clicked, the taskCopy is updated with all correct properties
      mUpdateTask_btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            taskCopy.setTitle(mTitleEditTextView.getText().toString());
            taskCopy.setDescription(mDescriptionEditTextView.getText().toString());
            taskCopy.setPriority(Integer.parseInt((String) mPriority_spinner.getSelectedItem()));
            updateTask();
         }
      });
      return v;
   }

   /**
    * Method to update the date displayed in the mDateEdit_btn
    */
   private void updateDate() {
      String deadlineString = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(taskCopy.getDeadline());
      mDateEdit_btn.setText(deadlineString);
   }

   /**
    * Method tries to update mTask properties and start mTask TaskActivity
    */
   private void updateTask() {
      try {
         mTask.setDeadline(taskCopy.getDeadline());
         mTask.setTitle(taskCopy.getTitle());
         mTask.setCompleted(taskCopy.isCompleted());
         mTask.setDescription(taskCopy.getDescription());
         mTask.setPriority(taskCopy.getPriority());
         // Toast for new task or updated tast
         if (isNewTask) {
            sTaskList.addTask(mTask);
            Toast.makeText(getActivity(),
                  "Task Created", Toast.LENGTH_SHORT).show();
         } else {
            Toast.makeText(getActivity(),
                  "Task Updated", Toast.LENGTH_SHORT).show();
         }
         Intent intent = TaskActivity.newIntent(getActivity(), mTask.getId());
         startActivity(intent);
      } catch (NullPointerException exception) {
         // If task can't be created, toast invalid task details
         Toast.makeText(getActivity(),
               "Invalid Task Details", Toast.LENGTH_SHORT).show();
      }
   }

   /**
    * Enables mUpdateTask_btn if Task has a valid title.
    * @param title CharSequence
    */
   private void validateTask(CharSequence title) {
      mUpdateTask_btn.setEnabled(title.length() > 0);
   }
}