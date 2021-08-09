package com.example.groceryapp.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {

    private Button registerSellerBtn, loginSellerBtn;
    private EditText nameInput, phoneInput, emailInput, passwordInput, addressInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        registerSellerBtn = (Button) findViewById(R.id.register_seler_btn);
        loginSellerBtn = (Button) findViewById(R.id.login_seler_btn);
        nameInput = (EditText) findViewById(R.id.seler_name);
        phoneInput = (EditText) findViewById(R.id.seler_phone);
        emailInput = (EditText) findViewById(R.id.seler_email);
        passwordInput = (EditText) findViewById(R.id.seler_password);
        addressInput = (EditText) findViewById(R.id.seler_address);
        loadingBar =new ProgressDialog(this);

        registerSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });


        loginSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerSeller() {
        String name = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String address = addressInput.getText().toString();

        if (!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals("")) {
            loadingBar.setTitle("Creating Seller Account");
            loadingBar.setMessage("please wait ,While we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
           mAuth.createUserWithEmailAndPassword(email,password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful())
                          {
                              final DatabaseReference rootRef;
                              rootRef= FirebaseDatabase.getInstance().getReference();

                              String  sid=mAuth.getCurrentUser().getUid();


                              HashMap<String,Object> sellerMap=new HashMap<>();
                              sellerMap.put("sid",sid);
                              sellerMap.put("phone",phone);
                              sellerMap.put("email",email);
                              sellerMap.put("address",address);
                              sellerMap.put("password",password);
                              sellerMap.put("name",name);

                              rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task)
                                          {
                                              loadingBar.dismiss();
                                              Toast.makeText(SellerRegistrationActivity.this,"You are Registered Successfully....",Toast.LENGTH_SHORT).show();


                                          }
                                      });

                          }
                       }
                   });
        }
        else {
            Toast.makeText(SellerRegistrationActivity.this,"Please complete Registration Form",Toast.LENGTH_SHORT).show();
        }
    }
}