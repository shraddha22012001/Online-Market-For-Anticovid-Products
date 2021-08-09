package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceryapp.Prevalent.Prevalent;
import com.example.groceryapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
 private EditText nameEditText,phoneEditText,CityEditText,AddressEditText;
 private Button ConfirmOrderBtn;
   private String totalAmount= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        ConfirmOrderBtn=(Button)findViewById(R.id.confirm_order_btn);
       totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(ConfirmFinalOrderActivity.this,"Total Price="+totalAmount+" Rs",Toast.LENGTH_SHORT).show();

        nameEditText=(EditText)findViewById(R.id.shipment_name);
        phoneEditText=(EditText)findViewById(R.id.shipment_phone);
        CityEditText=(EditText)findViewById(R.id.shipment_City);
       AddressEditText=(EditText)findViewById(R.id.shipment_address);
   ConfirmOrderBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           check();
       }
   });
    }
    private void check(){
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(ConfirmFinalOrderActivity.this,"please provide your name...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(ConfirmFinalOrderActivity.this,"please provide your phone number...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(CityEditText.getText().toString())){
            Toast.makeText(ConfirmFinalOrderActivity.this,"please provide your city name...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(AddressEditText.getText().toString())){
            Toast.makeText(ConfirmFinalOrderActivity.this,"please provide your address...",Toast.LENGTH_SHORT).show();
        }
        else

        {
            confirmOrder();

        }
    }
    private void confirmOrder()
    {
        String saveCurrentTime,saveCurrentDate;


        Calendar calforDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM,dd,yyyy");
        saveCurrentDate=currentDate.format(calforDate.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calforDate.getTime());
        final DatabaseReference orderref=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.CurrentOnlineUser.getPhone());
        HashMap<String,Object> orderMap=new HashMap<>();
        orderMap.put("totalamount",totalAmount);
        orderMap.put("name",nameEditText.getText().toString());
        orderMap.put("phone",phoneEditText.getText().toString());
        orderMap.put("address",AddressEditText.getText().toString());
        orderMap.put("city",CityEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("state","not shipped");

        orderref.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(Prevalent.CurrentOnlineUser.getPhone()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful())
                               {
                                   Toast.makeText(ConfirmFinalOrderActivity.this,"Yourfinal order has been placed sucessfully...",Toast.LENGTH_SHORT).show();
                                   onBackPressed();
                        }
                     }
              });
            }
        });
    }
}