package com.egeorge.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * SignUp Activity prompts user to create an account
 */
public class SignUpActivity extends AppCompatActivity
{

   private Button signUpBtn;
   private EditText mEditNameView;
   private EditText mEditEmailView;
   private EditText mEditPasswordView;
   private EditText mEditPasswordConfirmView;


   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sign_up);
      setSupportActionBar(findViewById(R.id.toolbar_signup));
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle("");

      // Select view elements by ID
      signUpBtn = findViewById(R.id.signUp_btn);
      mEditNameView = findViewById(R.id.editText_PersonName);
      mEditEmailView = findViewById(R.id.editText_EmailAddress);
      mEditPasswordView = findViewById(R.id.editText_Password);
      mEditPasswordConfirmView = findViewById(R.id.editText_PasswordConfirm);

      // Instantiate sign up button as false
      signUpBtn.setEnabled(false);
      // Validate name input (Check not empty)
      mEditNameView.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(
               CharSequence s, int start, int count, int after) {
            // This space intentionally left blank
         }
         @Override
         public void onTextChanged(
               CharSequence s, int start, int before, int count) {
            if (s.length() == 0)
            {
               mEditNameView.requestFocus();
               mEditNameView.setError("Name required");
            }
         }
         @Override
         public void afterTextChanged(Editable s) {
            validateSignUp(mEditNameView.getText(),mEditEmailView.getText(),mEditPasswordView.getText(),mEditPasswordConfirmView.getText());
         }
      });
      // Validate email input (Check not empty)
      mEditEmailView.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(
               CharSequence s, int start, int count, int after) {
            // This space intentionally left blank
         }
         @Override
         public void onTextChanged(
               CharSequence s, int start, int before, int count) {
            if (s.length() == 0)
            {
               mEditEmailView.requestFocus();
               mEditEmailView.setError("Email required");
            }
         }
         @Override
         public void afterTextChanged(Editable s) {
            validateSignUp(mEditNameView.getText(),mEditEmailView.getText(),mEditPasswordView.getText(),mEditPasswordConfirmView.getText());
         }
      });
      // Validate password input (Check not empty and matches confirm password)
      mEditPasswordView.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(
               CharSequence s, int start, int count, int after) {
            // Left blank intentionally
         }
         @Override
         public void onTextChanged(
               CharSequence s, int start, int before, int count) {
            if (s.length() == 0)
            {
               mEditPasswordView.requestFocus();
               mEditPasswordView.setError("Password required");
            }
         }
         @Override
         public void afterTextChanged(Editable s) {
            validateSignUp(mEditNameView.getText(),mEditEmailView.getText(),mEditPasswordView.getText(),mEditPasswordConfirmView.getText());
         }
      });
      // Validate confirm password input (Check if matches password)
      mEditPasswordConfirmView.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(
               CharSequence s, int start, int count, int after) {
            // Left blank intentionally
         }
         @Override
         public void onTextChanged(
               CharSequence s, int start, int before, int count) {
            if (s.length() == 0)
            {
               mEditPasswordConfirmView.requestFocus();
               mEditPasswordConfirmView.setError("Confirm Password");
            }
         }
         @Override
         public void afterTextChanged(Editable s) {
            validateSignUp(mEditNameView.getText(),mEditEmailView.getText(),mEditPasswordView.getText(),mEditPasswordConfirmView.getText());
         }
      });
      // On sign up click, toast created account and go to TaskListActivity
      signUpBtn.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            Toast.makeText(SignUpActivity.this,
                  "Your account has been successfully created", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
            startActivity(intent);
         }
      });
   }

   /**
    * Validate sign up credentials and enable sign up button if valid
    * @param name CharSequence
    * @param email CharSequence
    * @param pass1 CharSequence
    * @param pass2 CharSequence
    */
   private void validateSignUp(CharSequence name, CharSequence email, CharSequence pass1, CharSequence pass2) {
      if (email.length() > 0 && pass1.length() > 0 && name.length() > 0 && passwordMatch(pass1, pass2)) {
         signUpBtn.setEnabled(true);
      } else {
         signUpBtn.setEnabled(false);
      }
   }

   /**
    * Check if passwords match
    * @param pass1 CharSequence
    * @param pass2 CharSequence
    * @return boolean true if passwords match
    */
   private boolean passwordMatch(CharSequence pass1, CharSequence pass2) {
      return pass1.toString().equals(pass2.toString());
   }
}