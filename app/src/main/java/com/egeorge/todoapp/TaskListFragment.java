package com.egeorge.todoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

/**
 * TaskListFragment displays a recyclerview of Task objects
 */
public class TaskListFragment extends Fragment
{
   // Request Keys
   private static final String REQUEST_FILTER = "ListFilter";
   private static final String DIALOG_FILTER = "DialogFilter";
   // View Elements
   private RecyclerView mTaskRecyclerView;
   private TaskAdapter mAdapter;
   private FloatingActionButton addTaskBtn;

   private String sortType = "Priority";
   private boolean showCompleted = false;

   /**
    * Create fragment and set options menu
    * @param savedInstanceState Bundle
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setHasOptionsMenu(true);
   }

   /**
    * Display correct options menu
    * @param menu Menu
    * @param inflater Menu Inflater
    */
   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.menu.fragment_task_list, menu);
   }

   /**
    * Set actions for when option items are selected
    * @param item MenuItem
    * @return boolean
    */
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // If filter task selected, start a new FilterFragment dialog
      if (item.getItemId() == R.id.menu_item_filter_task)
      {
         FragmentManager manager = getParentFragmentManager();
         FilterFragment dialog = FilterFragment.newInstance(sortType,showCompleted);
         manager.setFragmentResultListener(REQUEST_FILTER, getViewLifecycleOwner(), (requestKey, result) -> {
            // Get results and update list accordingly
            if (requestKey.equals(REQUEST_FILTER)) {
               sortType = result.getString(FilterFragment.ARG_SORT);
               showCompleted = result.getBoolean(FilterFragment.ARG_COMPLETED);
               TaskList taskList = TaskList.get(getActivity());
               // Sort based off sort selected
               switch (sortType)
               {
                  case "Priority":
                     taskList.sortByPriority();
                     break;
                  case "Deadline":
                     taskList.sortByDeadline();
                     break;
                  case "Title":
                     taskList.sortByTitle();
                     break;
               }
               updateUI();
            }
         });
         dialog.show(manager, DIALOG_FILTER);
      }
      return super.onOptionsItemSelected(item);
   }

   /**
    * Create view
    * @param inflater LayoutInflater
    * @param container ViewGroup
    * @param savedInstanceState Bundle
    * @return View
    */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   {
      View v = inflater.inflate(R.layout.fragment_task_list, container, false);

      // Select view elements by id
      mTaskRecyclerView = v.findViewById(R.id.task_recycler_view);
      addTaskBtn = v.findViewById(R.id.add_task_btn);
      mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

      // Start EditTaskActivity to create new task when Add Task button clicked
      addTaskBtn.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            Task mTask = new Task();
            Intent intent = EditTaskActivity.newIntent(getActivity(), mTask.getId());
            startActivity(intent);
         }
      });
      updateUI();
      return v;
   }

   /**
    * Resume activity and update UI
    */
   @Override
   public void onResume() {
      super.onResume();
      updateUI();
   }

   /**
    * Update UI display with Task objects
    */
   private void updateUI()
   {
      TaskList taskList = TaskList.get(getActivity());
      List<Task> tasks;
      // If showCompleted is true, show all tasks in taskList
      if (showCompleted) {
         tasks = taskList.getTasks();
      } else {
         // Otherwise, show only incomplete tasks
         tasks = taskList.getIncompleteTasks();
      }
      // update mAdapter with new tasks
      mAdapter = new TaskAdapter(tasks);
      mTaskRecyclerView.setAdapter(mAdapter);
   }

   /**
    * TaskHolder holds a Task in taskList
    */
   private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener
   {
      // Model
      private Task mTask;
      // View Elements
      private final TextView mTitleTextView;
      private final TextView mDateTextView;
      private final CheckBox mCompletedCheckBox;

      public TaskHolder(View itemView)
      {
         super(itemView);
         // Select view elements by ID
         mTitleTextView =
               itemView.findViewById(R.id.list_item_task_title_textview);
         mDateTextView =
               itemView.findViewById(R.id.list_item_task_date_textview);
         mCompletedCheckBox =
               itemView.findViewById(R.id.list_item_task_completed_checkbox);

         // When checkbox is clicked, change mTask completion status
         mCompletedCheckBox.setOnClickListener(new View.OnClickListener()
         {
            @Override
            public void onClick(View view)
            {
               if (mTask.isCompleted()) {
                  mTask.setCompleted(false);
               } else {
                  mTask.setCompleted(true);
                  Toast.makeText(getActivity(),
                        mTask.getTitle()+" is complete!", Toast.LENGTH_SHORT).show();
                  updateUI();
               }
            }
         });
         itemView.setOnClickListener(this);
      }

      /**
       * Bind a task to the TaskHolder
       * @param task Task
       */
      public void bindTask(Task task)
      {
         mTask = task;
         mTitleTextView.setText(mTask.getTitle());
         String deadlineString = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(mTask.getDeadline());
         mDateTextView.setText(deadlineString);
         mCompletedCheckBox.setChecked(mTask.isCompleted());
      }

      /**
       * Starts a TaskActivity intent with the Task clicked
       * @param v View
       */
      @Override
      public void onClick(View v)
      {
         Intent intent = TaskActivity.newIntent(getActivity(), mTask.getId());
         startActivity(intent);
      }
   }

   /**
    * TaskAdapter displays the view based on position in list
    */
   private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>
   {
      private final List<Task> mTasks;

      /**
       * TaskAdapter updates view
       * @param tasks List of Task
       */
      public TaskAdapter(List<Task> tasks)
      {
         mTasks = tasks;
      }

      /**
       * Creates TaskHolder
       * @param parent ViewGroup
       * @param viewType int
       * @return TaskHolder
       */
      @Override
      public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType)
      {
         LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
         View view = layoutInflater.inflate(R.layout.list_item_task, parent, false);
         return new TaskHolder(view);
      }

      /**
       * Binds TaskHolder to view
       * @param holder TaskHolder
       * @param position int
       */
      @Override
      public void onBindViewHolder(TaskHolder holder, int position)
      {
         Task task = mTasks.get(position);
         holder.bindTask(task);
      }

      /**
       * Get item count of mTasks
       * @return int
       */
      @Override
      public int getItemCount()
      {
         return mTasks.size();
      }
   }
}