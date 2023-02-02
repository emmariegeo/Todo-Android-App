package com.egeorge.todoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskList
{
   private static TaskList sTaskList;
   private final ArrayList<Task> mTasks;

   /**
    * Public accessor for sTaskList
    * If no sTaskList, instantiate sTaskList
    * @param context Context
    * @return TaskList
    */
   public static TaskList get(Context context)
   {
      if (sTaskList == null)
      {
         sTaskList = new TaskList(context);
      }
      return sTaskList;
   }

   /**
    * Add a new task to mTasks
    * @param newTask Task
    */
   public void addTask(Task newTask) {
      mTasks.add(newTask);
   }

   /**
    * Accessor for mTasks
    * @return mTasks List of Task objects
    */
   public List<Task> getTasks()
   {
      return mTasks;
   }


   /**
    * Access Task object with matching task ID
    * @param id UUID
    * @return Task
    */
   public Task getTask(UUID id)
   {
      for (Task task : mTasks)
      {
         if (task.getId().equals(id))
         {
            return task;
         }
      }
      return null;
   }
   /**
    * Private constructor to instantiate mTasks
    * @param context Context
    */
   private TaskList(Context context)
   {
      mTasks = new ArrayList<>();
      mTasks.add(new Task(
            "Sample Task 1", new Date(), "Sample task description",4));
      mTasks.add(new Task(
            "Sample Task 2",new Date(), "Sample task description", 5));
      mTasks.add(new Task(
            "Sample Task 3", new Date(), "Sample task description", 1));
   }

   /**
    * Sort Task objects in mTasks by priority
    */
   public void sortByPriority() {
      mTasks.sort(new PriorityComparator());
   }

   /**
    * Sort Task objects in mTasks by deadline
    */
   public void sortByDeadline() {
      mTasks.sort(new DeadlineComparator());
   }

   /**
    * Sort Task objects in mTasks by title
    */
   public void sortByTitle() {
      mTasks.sort(new TitleComparator());
   }

   /**
    * Get list of incomplete tasks
    * @return List of incomplete Task objects
    */
   public List<Task> getIncompleteTasks() {
      ArrayList<Task> incompleteTasks = new ArrayList<>();
      for (Task task: mTasks) {
         if (!task.isCompleted()) {
            incompleteTasks.add(task);
         }
      }
      return incompleteTasks;
   }

   /**
    * Clear completed tasks from mTasks
    */
   public void clearCompletedTasks() {
      mTasks.removeIf(Task::isCompleted);
   }

   /**
    * Comparator to compare Task objects by priority (int)
    */
   static class PriorityComparator implements Comparator<Task> {
      @Override
      public int compare(Task task1, Task task2)
      {
         if (task1.getPriority() == task2.getPriority()) {
            return 0;
         } else if (task1.getPriority() > task2.getPriority()) {
            return 1;
         }
         return -1;
      }
   }
   /**
    * Comparator to compare Task objects by deadline (date)
    */
   static class DeadlineComparator implements Comparator<Task> {
      @Override
      public int compare(Task task1, Task task2)
      {
         if (task1.getDeadline().equals(task2.getDeadline())) {
            return 0;
         } else if (task1.getDeadline().after(task2.getDeadline())) {
            return 1;
         }
         return -1;
      }
   }

   /**
    * Comparator to compare Task objects by title (string)
    */
   static class TitleComparator implements Comparator<Task> {
      @Override
      public int compare(Task task1, Task task2)
      {
         return task1.getTitle().toUpperCase().compareTo(task2.getTitle().toUpperCase());
      }
   }
}
