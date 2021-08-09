package com.example.groceryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.groceryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {

private Button applyChangesBtn,DeleteProductBtn;
private EditText name,price,description;
private ImageView imageView;
    private String productId="";
    private DatabaseReference productsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        productId=getIntent().getStringExtra("pid");
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products").child(productId);



        applyChangesBtn=(Button)findViewById(R.id.Apply_changes_btn);
        DeleteProductBtn=(Button)findViewById(R.id.Delete_product_btn);
        name=(EditText)findViewById(R.id.Admin_product_nm);
        description=(EditText)findViewById(R.id.Admin_product_descrip);
        price=(EditText)findViewById(R.id.Admin_product_price);
        imageView=(ImageView) findViewById(R.id.Admin_product_img);


        displaysepecificproductinfo();

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyChanges();

            }
        });

        DeleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();

            }
        });
    }

    private void deleteProduct() {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainProductsActivity.this,"Product removed Successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void ApplyChanges() {

        String pName=name.getText().toString();
        String pPrice=price.getText().toString();
        String pDescription=description.getText().toString();


        if(pName.equals("")){
            Toast.makeText(this,"Please enter Product Name",Toast.LENGTH_SHORT).show();

        }
        else if(pPrice.equals("")){
            Toast.makeText(this,"Please enter Product Price",Toast.LENGTH_SHORT).show();

        }
        else if(pDescription.equals("")){
            Toast.makeText(this,"Please enter Product Description",Toast.LENGTH_SHORT).show();

        }
        else {
            HashMap<String,Object> productMap=new HashMap<>();
            productMap.put("pid",productId);
            productMap.put("description",pDescription);
            productMap.put("price",pPrice);
            productMap.put("pname",pName);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful()){
                     Toast.makeText(AdminMaintainProductsActivity.this,"Changes applied successfully",Toast.LENGTH_SHORT).show();
                      finish();
                  }
                }
            });
        }
    }

    private void displaysepecificproductinfo() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String pName=snapshot.child("pname").getValue().toString();
                    String pDescription=snapshot.child("description").getValue().toString();
                    String pPrice=snapshot.child("price").getValue().toString();
                    String pImage=snapshot.child("image").getValue().toString();


                    name.setText(pName);
                    description.setText(pDescription);
                    price.setText(pPrice);
                    Picasso.get().load(pImage).into(imageView);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}