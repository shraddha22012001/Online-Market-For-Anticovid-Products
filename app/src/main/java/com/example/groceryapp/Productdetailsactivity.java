package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.groceryapp.Model.Products;
import com.example.groceryapp.Prevalent.Prevalent;
import com.example.groceryapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.appcompat.widget.AppCompatButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ImageView;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Productdetailsactivity extends AppCompatActivity {
private Button addToCartBtn;
private ImageView productImage;
private ElegantNumberButton numberButton;
private TextView productDescription,productName,productPrice;
private String productId="",state="Normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetailsactivity);

        productId=getIntent().getStringExtra("pid");
       
        productImage=(ImageView) findViewById(R.id.product_image_details);
        productName=(TextView) findViewById(R.id.product_name_details);
        productDescription=(TextView) findViewById(R.id.product_description_details);
        productPrice=(TextView)findViewById(R.id.product_price_details);
        numberButton=(ElegantNumberButton)findViewById(R.id.number_btn);
addToCartBtn=(Button)findViewById(R.id.pd_add_to_cart_btn);
        getProductDetails(productId);
        
addToCartBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        if(state.equals("Order Placed") || state.equals("Order Shipped")){
            Toast.makeText(Productdetailsactivity.this,"ypu can purchase more items ,once your order is shipped...",Toast.LENGTH_LONG).show();
        }
        else {
            addingToCartList();
        }
    }
});
    }
    protected void onStart(){
        super.onStart();
    }

    private void addingToCartList() {

        String saveCurrentTime,saveCurrentDate;
        Calendar calforDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM,dd,yyyy");
        saveCurrentDate=currentDate.format(calforDate.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calforDate.getTime());

        final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");
        cartListRef.child("User View").child(Prevalent.CurrentOnlineUser.getPhone()).child("Products")
                .child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    cartListRef.child("Admin View").child(Prevalent.CurrentOnlineUser.getPhone()).child("Products")
                            .child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Productdetailsactivity.this,"Added To Cart",Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }
                    });

                }
            }
        });



    }
    private void getProductDetails(String productId) {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Products products=snapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                    
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                
            }
        });
    }
    private void CheckOrderState(){
        DatabaseReference orderRef;
        orderRef=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.CurrentOnlineUser.getPhone());
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String shippingState=snapshot.child("state").getValue().toString();
                    if(shippingState.equals("shipped")){

                        state="Order Shipped";
                       }
                    else  if(shippingState.equals("not shipped")){
                        state="Order Placed";
                       }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }







        });
    }
}