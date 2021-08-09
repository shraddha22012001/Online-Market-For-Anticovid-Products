package com.example.groceryapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.Interface.ItemClickListner;
import com.example.groceryapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;
    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.product_img);
        txtProductName=(TextView) itemView.findViewById(R.id.product_nm);
        txtProductDescription=(TextView) itemView.findViewById(R.id.product_descrip);
        txtProductPrice=(TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner=listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v ,getAdapterPosition(),false);
    }
}
