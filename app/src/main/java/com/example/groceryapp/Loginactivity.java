package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceryapp.Admin.AdmincategoryActivity;
import com.example.groceryapp.Model.Users;
import com.example.groceryapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class Loginactivity extends AppCompatActivity {

  private EditText InputPhoneNumber,InputPassword;
  private Button LogintoButton;
  private ProgressDialog loadingBar;
private TextView AdminLink,NotAdminLink,ForgetPasswordLink;
  private String parentDbName="Users";
    private CheckBox CheckBoxRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);



        ForgetPasswordLink=(TextView) findViewById(R.id.forget_password_link) ;
        LogintoButton=(Button) findViewById(R.id.login_btn);
        InputPhoneNumber =(EditText) findViewById(R.id.login_phone_number_input);
        InputPassword =(EditText) findViewById(R.id.login_password_input);
        loadingBar =new ProgressDialog(this);
        AdminLink=(TextView) findViewById(R.id.admin_panel_link) ;
        NotAdminLink=(TextView) findViewById(R.id.not_admin_panel_link);
       CheckBoxRememberMe=(CheckBox)findViewById(R.id.remember_me_chkb);
        Paper.init(this);
        LogintoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
            }
        });
        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loginactivity.this,ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
                finish();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogintoButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogintoButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });
    }
      private void LoginUser()
      {
          String phone=InputPhoneNumber.getText().toString();
          String password=InputPassword.getText().toString();

          if(TextUtils.isEmpty(phone))
      {
          Toast.makeText(this,"Please Enter Your Phone number..",Toast.LENGTH_SHORT).show();
      }

      else if(TextUtils.isEmpty(password))
      {
          Toast.makeText(this,"Please Enter Your Password..",Toast.LENGTH_SHORT).show();
      }
      else
          {
              loadingBar.setTitle("Login Account");
              loadingBar.setMessage("please wait ,While we are checking the credentials");
              loadingBar.setCanceledOnTouchOutside(false);
              loadingBar.show();
              AllowAccessToAccount(phone,password);


          }
      }

      private void AllowAccessToAccount(final String phone,final String password) {

          if (CheckBoxRememberMe.isChecked()) {
              Paper.book().write(Prevalent.UserPhoneKey, phone);
              Paper.book().write(Prevalent.UserpasswordKey, password);
          }

              final DatabaseReference RootRef;
              RootRef = FirebaseDatabase.getInstance().getReference();

              RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if (snapshot.child(parentDbName).child(phone).exists()) {
                          Users userData = snapshot.child(parentDbName).child(phone).getValue(Users.class);
                          if (userData.getPhone().equals(phone)) {
                              if (userData.getPassword().equals(password)) {
                                  if (parentDbName.equals("Admins")) {
                                      Toast.makeText(Loginactivity.this, "Welcome Admin ", Toast.LENGTH_SHORT).show();
                                      loadingBar.dismiss();

                                      Intent intent = new Intent(Loginactivity.this, AdmincategoryActivity.class);
                                      startActivity(intent);
                                      finish();
                                  }
                                  else if (parentDbName.equals("Users")) {
                                      Toast.makeText(Loginactivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                                      loadingBar.dismiss();

                                      Intent intent = new Intent(Loginactivity.this,HomeActivity.class);
                                      Prevalent.CurrentOnlineUser=userData;
                                      startActivity(intent);
                                      finish();
                                  }
                              }
                              else {
                                  Toast.makeText(Loginactivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                                  loadingBar.dismiss();
                              }
                          }
                      } else {
                          Toast.makeText(Loginactivity.this, "account with this " + phone + "do not exist", Toast.LENGTH_SHORT).show();
                          loadingBar.dismiss();
                      }
                  }
                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });
          }
}
