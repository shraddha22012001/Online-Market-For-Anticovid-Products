package com.example.groceryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.groceryapp.Model.AdminOrders;
import com.example.groceryapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference orderFref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        orderFref = FirebaseDatabase.getInstance().getReference().child("Orders");
        orderList = findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(orderFref, AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrderViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrderViewHolder adminOrderViewHolder, int i, @NonNull AdminOrders adminOrders) {

                        adminOrderViewHolder.userName.setText("Name:" + adminOrders.getName());
                        adminOrderViewHolder.userPhoneNumber.setText("Phone:" + adminOrders.getPhone());
                        adminOrderViewHolder.userTotalPrice.setText("Total Amount:" + adminOrders.getTotalamount());
                        adminOrderViewHolder.userDateTime.setText("Order at:" + adminOrders.getDate() + " " + adminOrders.getTime());
                        adminOrderViewHolder.userShippingAddress.setText("Shipping Address:" + adminOrders.getAddress() + " ," + adminOrders.getCity());
                        adminOrderViewHolder.ShowOrderBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uID = getRef(i).getKey();

                                Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });


                        adminOrderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Have You Shipped This Products ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0) {
                                            String uID = getRef(i).getKey();
                                            RemoveOrder(uID);
                                        } else {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrderViewHolder(view);
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }


    public static class AdminOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button ShowOrderBtn;

        public AdminOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_PhoneNumber);
            userTotalPrice = itemView.findViewById(R.id.Order_total_price);
            userDateTime = itemView.findViewById(R.id.Order_date_time);
            userShippingAddress = itemView.findViewById(R.id.Order_address_city);
            ShowOrderBtn = itemView.findViewById(R.id.show_orders_btn);

        }
    }

    private void RemoveOrder(String uID) {


        orderFref.child(uID).removeValue();
        final DatabaseReference cartlistRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        cartlistRef.child("User View").child(uID).removeValue();
        cartlistRef.child("Admin View").child(uID).removeValue();
    }
}

