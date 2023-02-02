package com.egeorge.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * LoginActivity is the launch activity and prompts users to log in
 */
public class LoginActivity extends AppCompatActivity
{
   private Button loginBtn;
   private Button forgotPassBtn;
   private Button signUpBtn;
   private EditText mEditEmailView;
   private EditText mEditPasswordView;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      // Select view elements by ID
      loginBtn = findViewById(R.id.login_btn);
      loginBtn.setEnabled(false);
      forgotPassBtn = findViewById(R.id.forgotPass_btn);
      signUpBtn = findViewById(R.id.signUp_btn);
      mEditEmailView = findViewById(R.id.editText_EmailAddress);
      mEditPasswordView = findViewById(R.id.editText_Password);
      // Only enable login button when email is not empty
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
            validateLogin(mEditEmailView.getText(),mEditPasswordView.getText());
         }
      });
      // Only enable login button when password is not empty
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
            validateLogin(mEditEmailView.getText(),mEditPasswordView.getText());
         }
      });

      // When login Button clicked, proceed to list of tasks
      loginBtn.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
            startActivity(intent);
         }
      });

      // When sign up button is clicked, move to sign up screen
      signUpBtn.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
         }
      });

      // Set forgot password button to toast on click
      forgotPassBtn.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            Toast.makeText(LoginActivity.this,
                  "Password recovery is not available at this time.", Toast.LENGTH_SHORT).show();
         }
      });
   }
   // Validate the login credentials (Checking not empty only)
   private void validateLogin(CharSequence email, CharSequence pass) {
      if (email.length() > 0 && pass.length() > 0) {
         loginBtn.setEnabled(true);
      } else {
         loginBtn.setEnabled(false);
      }
   }
}