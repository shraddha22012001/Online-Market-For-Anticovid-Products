package com.example.groceryapp.ui.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.ConfirmFinalOrderActivity;
import com.example.groceryapp.Model.Cart;
import com.example.groceryapp.Model.Products;
import com.example.groceryapp.Prevalent.Prevalent;
import com.example.groceryapp.Productdetailsactivity;
import com.example.groceryapp.R;
import com.example.groceryapp.ViewHolder.CartViewHolder;
import com.example.groceryapp.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GalleryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextprocessButton;
    private TextView txttotalAmount,txtmsg1;
    private int  overalltotalprice=0;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView=root.findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        nextprocessButton=(Button)root.findViewById(R.id.next_processe_btn);

        txttotalAmount=(TextView)root.findViewById(R.id.total_price);

        txtmsg1=(TextView)root.findViewById(R.id.msg1);

   nextprocessButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           txttotalAmount.setText("Total Price ="+String.valueOf(overalltotalprice));
           Intent intent=new Intent(getActivity(), ConfirmFinalOrderActivity.class);
           intent.putExtra("Total Price",String.valueOf(overalltotalprice));
           startActivity(intent);
       }
   });

        return root;
    }

    public void onStart()
    {
        super.onStart();
        CheckOrderState();

        final DatabaseReference cartlistRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartlistRef.child("User View").child(Prevalent.CurrentOnlineUser.getPhone())
                        .child("Products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.txtProductNmae.setText("Product "+ cart.getPname());
                cartViewHolder.txtProductPrice.setText("Price " +cart.getPrice());
                cartViewHolder.txtProductQuantity.setText("Quantity " +cart.getQuantity());
                int oneTypeProductPrice=(Integer.valueOf(cart.getPrice()))*Integer.valueOf(cart.getQuantity());
                overalltotalprice=overalltotalprice+oneTypeProductPrice;
                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                          "Edit","Remove"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("cart Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(which==0) {
                                    Intent intent=new Intent(getActivity(), Productdetailsactivity.class);
                                    intent.putExtra("pid",cart.getPid());

                                    startActivity(intent);
                                }
                                if(which==1){
                                    cartlistRef.child("User View").child(Prevalent.CurrentOnlineUser.getPhone()).child("Products").child(cart.getPid())
                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getActivity(),"Item removed successfully",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                    cartlistRef.child("Admin View").child(Prevalent.CurrentOnlineUser.getPhone()).child("Products").child(cart.getPid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder cartViewHolder=new CartViewHolder(view);
                return cartViewHolder;

            }
        };
                recyclerView.setAdapter(adapter);
                adapter.startListening();

    }
    private void CheckOrderState(){
        DatabaseReference orderRef;
        orderRef=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.CurrentOnlineUser.getPhone());
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String shippingState=snapshot.child("state").getValue().toString();
                    String username=snapshot.child("name").getValue().toString();
                    if(shippingState.equals("shipped")){
                        txttotalAmount.setText("Dear "+ username +"\nYour order is shipped successfully.. ");
                        recyclerView.setVisibility(View.GONE);
                        txtmsg1.setVisibility(View.VISIBLE);
                        txtmsg1.setText("Congratulations ,Your final order has been Shipped Successfully...soon you will recieve your order at your door step...");
                        nextprocessButton.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"You can Purcess more Products once you Recieved your first  order Successfully..",Toast.LENGTH_SHORT).show();
                    }
                    else  if(shippingState.equals("not shipped")){
                        txttotalAmount.setText("Shipping State= Not shipped");
                        recyclerView.setVisibility(View.GONE);
                        txtmsg1.setVisibility(View.VISIBLE);
                        nextprocessButton.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"You can Purcess more Products once you Recieved your first  order Successfully..",Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }







        });
    }

}