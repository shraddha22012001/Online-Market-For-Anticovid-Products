package com.example.groceryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groceryapp.Model.Cart;
import com.example.groceryapp.R;
import com.example.groceryapp.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductsActivity extends AppCompatActivity {
private RecyclerView ProductsList;
RecyclerView.LayoutManager layoutManager;
private DatabaseReference cartListRef;
private String userID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userID=getIntent().getStringExtra("uid");

        ProductsList=findViewById(R.id.Product_list);
        ProductsList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        ProductsList.setLayoutManager(layoutManager);

       cartListRef= FirebaseDatabase.getInstance().getReference()
               .child("Cart List").child("Admin View").child(userID).child("Products");
    }

    protected void onStart(){
        super.onStart();


        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef,Cart.class)
                .build();

         FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
    @Override
    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
        cartViewHolder.txtProductNmae.setText("Product "+ cart.getPname());
        cartViewHolder.txtProductPrice.setText("Price " +cart.getPrice());
        cartViewHolder.txtProductQuantity.setText("Quantity " +cart.getQuantity());

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
        CartViewHolder cartViewHolder=new CartViewHolder(view);
        return cartViewHolder;
    }
};
                ProductsList.setAdapter(adapter);
                adapter.startListening();
    }
}