package com.example.groceryapp.ViewHolder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.Interface.ItemClickListner;
import com.example.groceryapp.R;

public  class CartViewHolder  extends RecyclerView.ViewHolder implements OnClickListener {
    public TextView txtProductNmae,txtProductPrice,txtProductQuantity;
    private ItemClickListner itemClickListner;



    public CartViewHolder(@NonNull View itemView) {

        super(itemView);
        txtProductNmae=itemView.findViewById(R.id.cart_product_name);
        txtProductPrice=itemView.findViewById(R.id.cart_product_pprice);
        txtProductQuantity=itemView.findViewById(R.id.cart_product_quantity);

    }


    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner=itemClickListner;
    }
}
