package com.trf.trfinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>{

    private Context mCtx;
    private List<Product> productList;


    public ProductRecyclerViewAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;

    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_products,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.ViewHolder holder, int position) {
        final Product product =productList.get(position);
        holder.setProductName(product.getName());
        holder.setProductDesc(product.getDesc());
        holder.setProductCount(product.getAvailable_count());
        holder.btnTakeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, product.getName()+" Take Out", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public  void filterList(ArrayList<Product> filteredProducts) {
        this.productList = filteredProducts;
        //assigning the filterlist using this method
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDesc,productCount;
        View view;
        Button btnTakeOut;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            btnTakeOut = itemView.findViewById(R.id.product_takeout);
        }
        public void setProductName(String name){
            productName = view.findViewById(R.id.product_name);
            productName.setText(name);
        }
        public void setProductDesc(String desc){
            productDesc = view.findViewById(R.id.product_desc);
            productDesc.setText(desc);
        }
        public void setProductCount(int count){
            productCount = view.findViewById(R.id.product_count);
            productCount.setText(String.valueOf(count));
        }
    }
}
