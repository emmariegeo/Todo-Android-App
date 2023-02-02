package com.egeorge.todoapp;

import java.util.Date;
import java.util.UUID;

/**
 * A Task object holds Task data
 */
public class Task implements Cloneable
{
   private final UUID mId = UUID.randomUUID();
   private Date mDeadline;
   private boolean mCompleted;
   private String mTitle;
   private String mDescription;
   private int mPriority;
   /**
    * Constructor
    * @param mTitle String (Required)
    * @param mDeadline Date
    * @param mDescription String
    * @param mPriority int (range 1-5)
    * */
   public Task(String mTitle, Date mDeadline, String mDescription, int mPriority)
   {
      this.mCompleted = false;
      this.mTitle = mTitle;
      this.mDeadline = mDeadline;
      this.mDescription = mDescription;
      this.mPriority = mPriority;
   }

   /**
    * Empty constructor
    */
   public Task() {
      this.mCompleted = false;
      this.mTitle = "Your Title";
      this.mDeadline = new Date();
      this.mDescription = "Your Description";
      this.mPriority = 5;
   }

   // ACCESSORS

   /**
    * Access task id
    * @return UUID
    */
   public UUID getId() { return mId; }

   /**
    * access task deadline
    * @return Date
    */
   public Date getDeadline()
   {
      return mDeadline;
   }

   /**
    * access task completion status
    * @return boolean true if completed false if incomplete
    */
   public boolean isCompleted()
   {
      return mCompleted;
   }

   /**
    * access task priority level
    * @return int priority
    */
   public int getPriority()
   {
      return mPriority;
   }

   /**
    * access task description
    * @return String description
    */
   public String getDescription()
   {
      return mDescription;
   }

   /**
    * access task title
    * @return String title
    */
   public String getTitle()
   {
      return this.mTitle;
   }

   // MUTATORS
   /**
    * Set completion status
    * @param mCompleted boolean
    */
   public void setCompleted(boolean mCompleted)
   {
      this.mCompleted = mCompleted;
   }

   /**
    * Set task title
    * @param mTitle String
    */
   public void setTitle(String mTitle) { this.mTitle = mTitle; }

   /**
    * Set task deadline
    * @param mDeadline Date
    */
   public void setDeadline(Date mDeadline)
   {
      this.mDeadline = mDeadline;
   }

   /**
    * Set task description
    * @param mDescription String
    */
   public void setDescription(String mDescription)
   {
      this.mDescription = mDescription;
   }

   /**
    * Set task priority level
    * @param mPriority int
    */
   public void setPriority(int mPriority)
   {
      this.mPriority = mPriority;
   }

   /**
    * Clones a task object with a new id
    * @return Task
    */
   public Task clone() {
      return new Task(this.mTitle,this.mDeadline,this.mDescription,this.mPriority);
   }
}
