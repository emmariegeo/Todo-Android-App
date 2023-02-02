package com.egeorge.todoapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

// Calendar & Date Utils
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * DatePicker dialog for selecting Deadline
 */
public class DatePickerFragment extends DialogFragment
{
   public static final String ARG_DATE = "date";
   private static final String REQUEST_DATE = "Deadline";
   private DatePicker mDatePicker;

   public static DatePickerFragment newInstance(Date date) {
      Bundle args = new Bundle();
      args.putSerializable(ARG_DATE, date);

      DatePickerFragment fragment = new DatePickerFragment();
      fragment.setArguments(args);
      return fragment;
   }
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
      // Instantiate date on DatePicker with current deadline
      Date date = (Date) requireArguments().getSerializable(ARG_DATE);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int year = calendar.get(Calendar.YEAR);
      int month = calendar.get(Calendar.MONTH);
      int day = calendar.get(Calendar.DAY_OF_MONTH);

      // select DatePicker in view
      mDatePicker = v.findViewById(R.id.dialog_date_datepicker);
      mDatePicker.init(year, month, day, null);

      return new AlertDialog.Builder(requireActivity()).setView(v).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            int year = mDatePicker.getYear();
            int month = mDatePicker.getMonth();
            int day = mDatePicker.getDayOfMonth();
            Date date = new GregorianCalendar(year, month, day).getTime();
            sendResult(date);
         }
      }).create();
   }
   // Send Date Result to EditTaskFragment
   private void sendResult(Date date) {
      Bundle args = new Bundle();
      args.putSerializable(ARG_DATE, date);

      getParentFragmentManager().setFragmentResult(REQUEST_DATE,args);
   }
}