package com.example.groceryapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.Model.Products;
import com.example.groceryapp.Prevalent.Prevalent;
import com.example.groceryapp.Productdetailsactivity;
import com.example.groceryapp.R;
import com.example.groceryapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.content.Intent.getIntent;

public class HomeFragment extends Fragment {
private DatabaseReference Productsref;
  RecyclerView.LayoutManager layoutManager;
    private HomeViewModel homeViewModel;
  private RecyclerView recyclerView;






    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);

Productsref=FirebaseDatabase.getInstance().getReference().child("Products");
   recyclerView=view.findViewById(R.id.recycler_menu);
   recyclerView.setHasFixedSize(true);
   layoutManager=new LinearLayoutManager(getActivity());
   recyclerView.setLayoutManager(layoutManager);


return view;
    }

    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<Products>  options=new FirebaseRecyclerOptions.Builder<Products>()
               .setQuery(Productsref, Products.class).build();

    FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Products products) {
           productViewHolder.txtProductName.setText(products.getPname());
            productViewHolder.txtProductDescription.setText(products.getDescription());
            productViewHolder.txtProductPrice.setText("Price="+products.getPrice() +"Rs");
            Picasso.get().load(products.getImage()).into(productViewHolder.imageView);


            productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                        Intent intent=new Intent(getActivity(),Productdetailsactivity.class);
                        intent.putExtra("pid",products.getPid());
                        startActivity(intent);





                }
            });

        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
           ProductViewHolder holder=new ProductViewHolder(view);
           return holder;
        }
    };
   recyclerView.setAdapter(adapter);
   adapter.startListening();

}

}