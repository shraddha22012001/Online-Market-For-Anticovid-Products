package com.example.groceryapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.groceryapp.MainActivity;
import com.example.groceryapp.R;

public class AdmincategoryActivity extends AppCompatActivity {
    private ImageView masks,sanitizershandwash,Boost_immunity,thermomertes,disinfectant,doctors_corner,pulse_oximeter,ppe_kits,gloves;
    private Button AdminlogoutButton,CheckOrdersButton,MaintainProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admincategory);

        masks=(ImageView)findViewById(R.id.masks);
       sanitizershandwash=(ImageView)findViewById(R.id.sanitizers_handwash);
       Boost_immunity=(ImageView)findViewById(R.id.Boost_immunity);
       thermomertes=(ImageView)findViewById(R.id.thermomertes);
        disinfectant=(ImageView)findViewById(R.id.disinfectant);
       doctors_corner=(ImageView)findViewById(R.id.doctors_corner);
       pulse_oximeter=(ImageView)findViewById(R.id.pulse_oximeter);
        ppe_kits=(ImageView)findViewById(R.id.ppe_kits);
        gloves=(ImageView)findViewById(R.id.gloves);
       AdminlogoutButton=(Button) findViewById(R.id.admin_logout_btn);
        CheckOrdersButton=(Button) findViewById(R.id.check_orders_btn);
        MaintainProductsBtn=(Button) findViewById(R.id.maintain_btn);

        AdminlogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this, AdminNewOrdersActivity.class);

                startActivity(intent);
            }
        });

        MaintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(AdmincategoryActivity.this, AdminHomeProductsActivity.class);
                startActivity(intent);
            }
        });

        masks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this, AddNewProductActivity.class);
              intent.putExtra("Category","masks");
              startActivity(intent);
            }
        });
       sanitizershandwash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category","sanitizers and handwash");
                startActivity(intent);
            }
        });
        Boost_immunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category","Boost your immunity");
                startActivity(intent);
            }
        });
       thermomertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category","thermomertes");
                startActivity(intent);
            }
        });


        disinfectant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category","disinfectant");
                startActivity(intent);
            }
        });
        doctors_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category","doctor's corner");
                startActivity(intent);
            }
        });
       pulse_oximeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category"," pulse oximeter");
                startActivity(intent);
            }
        });
       ppe_kits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category","ppe kits");
                startActivity(intent);
            }
        });
        gloves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmincategoryActivity.this,AddNewProductActivity.class);
                intent.putExtra("Category","gloves");
                startActivity(intent);
            }
        });
    }
}